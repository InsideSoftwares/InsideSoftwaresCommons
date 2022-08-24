package br.com.insidesoftwares.commons.configuration.rest;


import br.com.insidesoftwares.commons.dto.response.InsideSoftwaresResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.time.Duration;
import java.time.LocalTime;
import java.time.temporal.ChronoField;
import java.util.Objects;

@Log4j2
public class InsideSoftwaresRestAdvice implements ResponseBodyAdvice<InsideSoftwaresResponse<Object>> {

	private static final String START_TIME_HEADER = "StartTime";

	private final String packageController;

	public InsideSoftwaresRestAdvice(String packageController) {
		this.packageController = packageController;
	}

	@Override
	public InsideSoftwaresResponse<Object> beforeBodyWrite(InsideSoftwaresResponse<Object> body, MethodParameter returnType,
														MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType,
														ServerHttpRequest request, ServerHttpResponse response) {

		if(Objects.nonNull(body)) {
			long startTime = getStartTime(response);
			long duration = System.currentTimeMillis() - startTime;
			Duration diff = Duration.ofMillis(duration);
			body.setDuration(String.format("%02d:%02d:%02d.%03d",
					diff.toHours(),
					diff.toMinutesPart(),
					diff.toSecondsPart(),
					diff.toMillis()));
		}

		return body;
	}

    @Override
    public boolean supports(MethodParameter methodParameter, Class<? extends HttpMessageConverter<?>> aClass) {
        return methodParameter.getContainingClass().getPackage().getName().contains(packageController);
    }

    private Long getStartTime(ServerHttpResponse response){
        if(response.getHeaders() != null && response.getHeaders().containsKey(START_TIME_HEADER) && response.getHeaders().get(START_TIME_HEADER) != null){

            return Long.parseLong(response.getHeaders().get(START_TIME_HEADER).toString().replaceAll("[^\\d]*",""));
        }
        return LocalTime.now().getLong(ChronoField.MILLI_OF_DAY);
    }

}
