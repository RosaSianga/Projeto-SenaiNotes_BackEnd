package br.com.senainotes;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(
        title = "API Senai Notes",
        version = "1.0",
        description = "API responsável por gerenciar os recursos de um sistema de anotações."
))
public class SenainotesApplication {

	public static void main(String[] args) {
		SpringApplication.run(SenainotesApplication.class, args);
	}

}
