package br.com.insidesoftwares.commons.configuration.rest.filter;


import br.com.insidesoftwares.commons.utils.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.server.ServletServerHttpRequest;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Slf4j
@Configuration
@Order(0)
public class LoggingInitialFilter implements Filter {

	@Override
	public void doFilter(
			ServletRequest request,
			ServletResponse response,
			FilterChain chain
	) throws IOException, ServletException {

		final HttpServletRequest req = (HttpServletRequest) request;
		log.info("""
				--------------------------------------------------------------
				Request Time: {}
				Request Method: {}
				Request URI: {}
				Content-Type: {}
				Headers: {}
				Body: {}
				--------------------------------------------------------------""",
				DateUtils.returnDateCurrent(),
				req.getMethod(),
				req.getRequestURI(),
				req.getContentType(),
				new ServletServerHttpRequest(req).getHeaders(),
				""
		);

		chain.doFilter(req, response);
	}
}
