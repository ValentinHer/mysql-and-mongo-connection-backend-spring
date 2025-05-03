package com.valentin.mysql_and_mongo_connection.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

	@Bean
	public OpenAPI api() {
		return new OpenAPI()
				.info(new Info()
							  .title("Mysql and Mongo API docs")
							  .version("1.0.0")
							  .description("API to manage users in MySql and MongoDB")
		);
	}
}
