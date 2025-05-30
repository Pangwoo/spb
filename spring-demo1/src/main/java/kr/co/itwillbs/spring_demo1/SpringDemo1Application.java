package kr.co.itwillbs.spring_demo1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;


@RestController
@SpringBootApplication
public class SpringDemo1Application {

	public static void main(String[] args) {
		SpringApplication.run(SpringDemo1Application.class, args);
	}
	
	// SpringApplication 클래스도 컨트롤러 역할이 가능(단, 일반적으로 컨트롤러 클래스로 분리)
//	@GetMapping("/")
//	public String hello() {
//		return "Hello, World!";
//	}
	

}
