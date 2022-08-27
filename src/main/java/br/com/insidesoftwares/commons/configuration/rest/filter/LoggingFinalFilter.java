package br.com.insidesoftwares.commons.configuration.rest.filter;


import br.com.insidesoftwares.commons.utils.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.web.util.ContentCachingResponseWrapper;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static java.nio.charset.StandardCharsets.UTF_8;

@Slf4j
@Configuration
@Order(100)
public class LoggingFinalFilter implements Filter {

	@Override
	public void doFilter(
			ServletRequest request,
			ServletResponse response,
			FilterChain chain
	) throws IOException, ServletException {

		HttpServletResponse res = (HttpServletResponse) response;
		ContentCachingResponseWrapper servletResponse = new ContentCachingResponseWrapper(res);

		chain.doFilter(request, servletResponse);

		log.info("""
				--------------------------------------------------------------
				Response Time: {}
				Response-Code: {}
				Content-Type: {}
				Headers: {}
				Body: {}
				--------------------------------------------------------------""",
				DateUtils.returnDateCurrent(),
				servletResponse.getStatus(),
				servletResponse.getContentType(),
				new ServletServerHttpResponse(servletResponse).getHeaders(),
				IOUtils.toString(servletResponse.getContentInputStream(), UTF_8)
		);
		servletResponse.copyBodyToResponse();
	}

	// other methods
}
