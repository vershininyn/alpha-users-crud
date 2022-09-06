package dev.projects.alpha.userscrud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jms.activemq.ActiveMQAutoConfiguration;

@SpringBootApplication(exclude = ActiveMQAutoConfiguration.class)
public class UsersCrudApplication {
	public static void main(String[] args) {
		SpringApplication.run(UsersCrudApplication.class, args);
	}
}
