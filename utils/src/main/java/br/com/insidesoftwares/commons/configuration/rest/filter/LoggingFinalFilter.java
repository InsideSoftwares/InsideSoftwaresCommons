package br.com.insidesoftwares.commons.configuration.rest.filter;


import br.com.insidesoftwares.commons.utils.DateUtils;
import br.com.insidesoftwares.commons.utils.filter.FilterUtil;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;

@Slf4j
@Configuration
@Order(100)
@RequiredArgsConstructor
public class LoggingFinalFilter implements Filter {

	private final InsideFilterProperties insideFilterProperties;
	private final FilterUtil filterUtil;

	@Override
	public void doFilter(
			ServletRequest request,
			ServletResponse response,
			FilterChain chain
	) throws IOException, ServletException {

		HttpServletResponse res = (HttpServletResponse) response;
		final HttpServletRequest req = (HttpServletRequest) request;
		ContentCachingResponseWrapper servletResponse = new ContentCachingResponseWrapper(res);

		chain.doFilter(request, servletResponse);
		if(req.getRequestURI().contains(insideFilterProperties.getURI())) {
			String responseBody = getResponseBody(servletResponse);
			log.info("""
							--------------------------------------------------------------
							Response Time: {}
							Response-Code: {}
							Content-Type: {}
							Response: {}
							--------------------------------------------------------------""",
					DateUtils.returnDateCurrent(),
					servletResponse.getStatus(),
					servletResponse.getContentType(),
					responseBody
			);
		}
		servletResponse.copyBodyToResponse();
	}

	private String getResponseBody(ContentCachingResponseWrapper servletResponse) {
		String responseBody = "Body view not enabled";
		if(insideFilterProperties.isShowResponseBody()) {
			responseBody = filterUtil.formatBody(servletResponse.getContentInputStream());
		}
		return responseBody;
	}
}
