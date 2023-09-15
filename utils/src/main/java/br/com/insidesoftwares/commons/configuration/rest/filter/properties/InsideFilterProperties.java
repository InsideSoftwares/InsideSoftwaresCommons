package br.com.insidesoftwares.commons.configuration.rest.filter.properties;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;

@Configuration
@Data
@RefreshScope
public class InsideFilterProperties {

    @Value("${server.servlet.context-path}")
    private String contextPath;

    @Value("${insidesoftwares.filter.show-request-body:false}")
    private boolean showRequestBody;

    @Value("${insidesoftwares.filter.show-request-headers:false}")
    private boolean showRequestHeaders;

    @Value("${insidesoftwares.filter.show-response-body:false}")
    private boolean showResponseBody;


    private static final String API = "/api";

    public String getURI(){
        return getContextPath()+API;
    }
}
