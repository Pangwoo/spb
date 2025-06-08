package kr.co.itwillbs.thymeleaf;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing // JPA Auditing(감사) 기능 활성화
public class SpringDemo4ThymeleafApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringDemo4ThymeleafApplication.class, args);
	}

}
