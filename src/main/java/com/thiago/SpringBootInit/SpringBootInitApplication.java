package com.thiago.SpringBootInit;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@OpenAPIDefinition(
		info = @Info(
				title = "Spring starter",
				version = "1.0",
				description = "Programa base para desenvolvimento com spring boot, data jpa, security e swagger"
		)
)
@SpringBootApplication(scanBasePackages = "com.thiago.SpringBootInit")
public class SpringBootInitApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootInitApplication.class, args);
	}

}

