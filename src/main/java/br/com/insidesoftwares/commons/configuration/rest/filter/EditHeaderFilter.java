package br.com.insidesoftwares.commons.configuration.rest.filter;


import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Configuration
@Order(99)
public class EditHeaderFilter implements Filter {

	private static final String START_TIME_HEADER = "StartTime";

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        long startTime = System.currentTimeMillis();
        HttpServletResponse res = (HttpServletResponse) response;

        res.addHeader(START_TIME_HEADER, String.valueOf(startTime));

        chain.doFilter(request, res);
    }
}
