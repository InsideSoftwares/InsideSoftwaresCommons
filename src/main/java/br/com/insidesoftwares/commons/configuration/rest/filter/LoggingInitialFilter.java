package br.com.insidesoftwares.commons.configuration.rest.filter;


import br.com.insidesoftwares.commons.utils.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.server.ServletServerHttpRequest;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import java.io.IOException;

@Slf4j
@Configuration
@Order(0)
public class LoggingInitialFilter implements Filter {

	@Value("server.server.context-path")
	private String contextPath;

	@Override
	public void doFilter(
			ServletRequest request,
			ServletResponse response,
			FilterChain chain
	) throws IOException, ServletException {

		final HttpServletRequest req = (HttpServletRequest) request;

		if(req.getRequestURI().contains(contextPath+"/api/v")) {
			log.info("""
							--------------------------------------------------------------
							Request Time: {}
							Request Method: {}
							Request URI: {}
							Content-Type: {}
							Headers: {}
							--------------------------------------------------------------""",
					DateUtils.returnDateCurrent(),
					req.getMethod(),
					req.getRequestURI(),
					req.getContentType(),
					new ServletServerHttpRequest(req).getHeaders()
			);
		}
		chain.doFilter(req, response);
	}
}
