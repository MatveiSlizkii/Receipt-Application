 package by.clevertec.checks.demoArtifact;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

 @SpringBootApplication
 @EnableJpaRepositories(basePackages = "by.clevertec.checks.demoArtifact.dao.api")
public class DemonameApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemonameApplication.class, args);
	}

}
