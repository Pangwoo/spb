package kr.co.itwillbs.spring_demo3.test1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class TestController {
	@Autowired
	private TestService testService;
	
	// "/test" 서블릿 주소 매핑 - test()
	// => TestService - test() 메서드 호출
//	@GetMapping("/")
//	public String test() {
////		testService.test();
//		
//		// src/main/resources/static 경로에서 지정된 html 파일 탐색하여 렌더링
//		return "index.html";
		// => 별도로 매핑을 수행하지 않아도 스프링부트에서 기본 루트 요청 시
		//    자동으로 static 경로의 index.html 파일을 탐색함(따라서, 생략 가능)
//	}
	
	
}
