package kr.co.itwillbs.oracle;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class SpringDemo5OracleApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringDemo5OracleApplication.class, args);
	}

}
