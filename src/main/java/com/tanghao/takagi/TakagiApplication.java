package com.tanghao.takagi;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class TakagiApplication {

	public static void main(String[] args) {
		SpringApplication.run(TakagiApplication.class, args);
		log.info(""" 
                
                --------------------------------------------------------------
                Application Takagi is running!
                Swagger-ui Url: http://127.0.0.1:8080/swagger-ui/index.html
                --------------------------------------------------------------
                
                """);
	}

}
