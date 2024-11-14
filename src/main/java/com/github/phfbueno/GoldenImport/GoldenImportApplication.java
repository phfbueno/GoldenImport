package com.github.phfbueno.GoldenImport;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.github.phfbueno.GoldenImport")
public class GoldenImportApplication {

	public static void main(String[] args) {
		SpringApplication.run(GoldenImportApplication.class, args);
	}

}
