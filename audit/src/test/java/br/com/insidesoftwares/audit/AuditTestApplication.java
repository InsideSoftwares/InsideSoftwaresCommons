package br.com.insidesoftwares.audit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({"br.com.insidesoftwares.audit"})
@SpringBootConfiguration
public class AuditTestApplication {

	public static void main(String[] args) {
		SpringApplication.run(AuditTestApplication.class, args);
	}

}
