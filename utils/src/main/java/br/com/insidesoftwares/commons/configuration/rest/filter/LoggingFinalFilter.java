package br.com.insidesoftwares.commons.configuration.rest.filter;


import br.com.insidesoftwares.commons.utils.DateUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.web.util.ContentCachingResponseWrapper;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Configuration
@Order(100)
public class LoggingFinalFilter implements Filter {

	@Value("${server.servlet.context-path}")
	private String contextPath;

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
		if(req.getRequestURI().contains(contextPath+"/api")) {
			log.info("""
							--------------------------------------------------------------
							Response Time: {}
							Response-Code: {}
							Content-Type: {}
							Headers: {}
							--------------------------------------------------------------""",
					DateUtils.returnDateCurrent(),
					servletResponse.getStatus(),
					servletResponse.getContentType(),
					new ServletServerHttpResponse(servletResponse).getHeaders()
			);
		}
		servletResponse.copyBodyToResponse();
	}
}
