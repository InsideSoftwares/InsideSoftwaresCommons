package br.com.insidesoftwares.exception;

import br.com.insidesoftwares.commons.dto.response.InsideSoftwaresResponse;
import br.com.insidesoftwares.commons.enums.InsideSoftwaresExceptionCode;
import br.com.insidesoftwares.commons.specification.LocaleUtils;
import br.com.insidesoftwares.exception.error.InsideSoftwaresException;
import br.com.insidesoftwares.exception.error.InsideSoftwaresNoContentException;
import br.com.insidesoftwares.exception.error.InsideSoftwaresNoRollbackException;
import br.com.insidesoftwares.exception.model.AttributeNotValid;
import br.com.insidesoftwares.exception.model.ExceptionResponse;
import br.com.insidesoftwares.exception.utils.ExceptionUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import jakarta.validation.ConstraintViolationException;

import java.nio.file.AccessDeniedException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@ControllerAdvice
@Log4j2
@RequiredArgsConstructor
public class ExceptionsHandler extends ResponseEntityExceptionHandler {

	private final LocaleUtils localeUtils;

	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(
			HttpMessageNotReadableException ex,
			HttpHeaders headers,
			HttpStatusCode status,
			WebRequest request
	) {
		log.error("handleSecurity - handleHttpMessageNotReadable: ", ex);

		String field = "", typesEnum = "";
		String patternField = "(\\[\\\"[\\w,\\s]+\\\"\\])";
		String patternType = "(\\[[\\w,\\s]+\\])";

		Pattern pattern = Pattern.compile(patternField);
		Matcher matcher = pattern.matcher(ex.getMessage());
		if(matcher.find()){
			field = matcher.group().replaceAll("([\\[\\\"\\]])","");
		}
		pattern = Pattern.compile(patternType);
		matcher = pattern.matcher(ex.getMessage());
		if(matcher.find()){
			typesEnum = matcher.group();
		}

		String message = localeUtils.getMessage(InsideSoftwaresExceptionCode.ATTRIBUTE_NOT_VALID.getCode(), field, typesEnum);

		return ResponseEntity.status(status).body(
				createResponse(InsideSoftwaresExceptionCode.ENUM_ERROR.getCode(), message)
		);
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(
			MethodArgumentNotValidException ex,
			HttpHeaders headers,
			HttpStatusCode status,
			WebRequest request
	) {
		log.error("handleSecurity - handleMethodArgumentNotValid: ", ex);

		List<AttributeNotValid> validationErrorsDTO = new ArrayList<>();

		ex.getBindingResult().getFieldErrors()
				.forEach(
						e -> validationErrorsDTO.add(
								new AttributeNotValid(
										e.getField(),
										localeUtils.getMessage(e.getDefaultMessage(), e.getField())
								)
						)
				);

		String message = localeUtils.getMessage(
				InsideSoftwaresExceptionCode.ATTRIBUTE_NOT_VALID.getCode(),
				((ServletWebRequest)request).getRequest().getRequestURI()
		);

		return ResponseEntity.status(status).body(
				createResponse(InsideSoftwaresExceptionCode.ATTRIBUTE_NOT_VALID.getCode(), message, validationErrorsDTO)
		);
	}

	@ExceptionHandler(ConstraintViolationException.class)
	@ResponseBody
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	protected InsideSoftwaresResponse<ExceptionResponse> handleConstraintViolationException(ConstraintViolationException exception) {
		log.error("handleSecurity - ConstraintViolationException: ", exception);

		List<AttributeNotValid> validationErrorsDTO = new ArrayList<>();

		exception.getConstraintViolations().forEach(
				e -> {
					String[] path = e.getPropertyPath().toString().split("\\.");
					List<String> attributes = new ArrayList<>();
					attributes.add(path[path.length-1]);
					attributes.addAll(
							ExceptionUtils.findValuesAnnotation(e.getConstraintDescriptor().getAnnotation())
					);

					validationErrorsDTO.add(
							new AttributeNotValid(
									attributes.get(0),
									localeUtils.getMessage(e.getMessage(), attributes.toArray(String[]::new))
							)
					);
				}
		);

		String message = localeUtils.getMessage(InsideSoftwaresExceptionCode.ATTRIBUTE_NOT_VALID.getCode());

		return createResponse(InsideSoftwaresExceptionCode.ATTRIBUTE_NOT_VALID.getCode(), message, validationErrorsDTO);
	}

	@ExceptionHandler(InsideSoftwaresException.class)
	@ResponseBody
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	protected InsideSoftwaresResponse<ExceptionResponse> handleInsideSoftwaresException(InsideSoftwaresException exception){
		log.error("handleSecurity - InsideSoftwaresException: ", exception);
		return createResponse(exception.getCode(), exception.getArgs());
	}

	@ExceptionHandler(InsideSoftwaresNoRollbackException.class)
	@ResponseBody
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	protected InsideSoftwaresResponse<ExceptionResponse> handleInsideSoftwaresNoRollbackException(InsideSoftwaresNoRollbackException exception){
		log.error("handleSecurity - InsideSoftwaresNoRollbackException: ", exception);
		return createResponse(exception.getCode());
	}

	@ExceptionHandler(InsideSoftwaresNoContentException.class)
	@ResponseBody
	@ResponseStatus(HttpStatus.NO_CONTENT)
	protected void handleInsideSoftwaresNoContentException(InsideSoftwaresNoContentException exception){
		log.error("handleSecurity - InsideSoftwaresNoContentException: ", exception);
	}

	@ExceptionHandler(AccessDeniedException.class)
	public ResponseEntity<String> handleAccessDeniedException(AccessDeniedException ex) {
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Acesso negado: " + ex.getMessage());
	}

	private InsideSoftwaresResponse<ExceptionResponse> createResponse(
			String code,
			String message
	){
		return createResponse(code, message, null);
	}
	private InsideSoftwaresResponse<ExceptionResponse> createResponse(
			String code,
			String message,
			List<AttributeNotValid> validationErrorsDTO
	){
		ExceptionResponse exceptionResponse = ExceptionResponse.builder().codeError(code)
				.message(message)
				.validationErrors(validationErrorsDTO).build();
		return InsideSoftwaresResponse.<ExceptionResponse>builder()
				.data(exceptionResponse)
				.build();
	}
	private InsideSoftwaresResponse<ExceptionResponse> createResponse(String code, String... args){
		ExceptionResponse exceptionResponse = ExceptionResponse.builder()
				.codeError(code)
				.message(localeUtils.getMessage(code, args))
				.build();
		return InsideSoftwaresResponse.<ExceptionResponse>builder()
				.data(exceptionResponse)
				.build();
	}
}
