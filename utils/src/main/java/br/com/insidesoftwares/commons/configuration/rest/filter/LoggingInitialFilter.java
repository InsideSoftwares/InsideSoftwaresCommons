package br.com.insidesoftwares.commons.configuration.rest.filter;


import br.com.insidesoftwares.commons.utils.DateUtils;
import br.com.insidesoftwares.commons.utils.filter.FilterUtil;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.server.ServletServerHttpRequest;

import java.io.IOException;
import java.util.stream.Collectors;

@Slf4j
@Configuration
@Order(0)
@RequiredArgsConstructor
public class LoggingInitialFilter implements Filter {

	private final InsideFilterProperties insideFilterProperties;
	private final FilterUtil filterUtil;

	@Override
	public void doFilter(
			ServletRequest request,
			ServletResponse response,
			FilterChain chain
	) throws IOException, ServletException {

		final MultiReadHttpServletRequest req = new MultiReadHttpServletRequest((HttpServletRequest) request);

		if(req.getRequestURI().contains(insideFilterProperties.getURI())) {
			String headers = getRequestHeaders(req);
			String body = getRequestBody(req);
			log.info("""
							--------------------------------------------------------------
							Request Time: {}
							Request Method: {}
							Request URI: {}
							Content-Type: {}
							Headers: {}
							Request Body: {}
							--------------------------------------------------------------""",
					DateUtils.returnDateCurrent(),
					req.getMethod(),
					createURI(req),
					req.getContentType(),
					headers,
					body
			);
		}

		chain.doFilter(req, response);
	}

	private String createURI(MultiReadHttpServletRequest servletRequest) {
		String queryParams = servletRequest.getParameterMap().entrySet().stream()
				.map(query -> "%s=%s".formatted(query.getKey(), String.join(",", query.getValue()))
		).collect(Collectors.joining("&"));

		if(!queryParams.isEmpty()){
			queryParams = "?%s".formatted(queryParams);
		}

		return "%s%s".formatted(servletRequest.getRequestURI(), queryParams);
	}

	private String getRequestHeaders(MultiReadHttpServletRequest servletRequest) {
		String resquestHeaders = "Headers view not enabled";
		if(insideFilterProperties.isShowRequestHeaders()) {
			resquestHeaders = filterUtil.formatHeadersView(new ServletServerHttpRequest(servletRequest).getHeaders());
		}
		return resquestHeaders;
	}

	private String getRequestBody(MultiReadHttpServletRequest servletRequest) throws IOException {
		String responseBody = "Body view not enabled";
		if(insideFilterProperties.isShowRequestBody()) {
			responseBody = filterUtil.formatBody(servletRequest.getInputStream());
		}
		return responseBody;
	}
}
