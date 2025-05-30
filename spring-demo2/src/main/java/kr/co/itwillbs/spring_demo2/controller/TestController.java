package kr.co.itwillbs.spring_demo2.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;


//@Controller
// 컨트롤러 클래스 내의 모든 메서드가 @ResponseBody 일 경우 해당 컨트롤러에 @RestController 어노테이션을 적용하여
// 모든 메서드에 @ResponseBody 를 자동으로 적용하도록 할 수 있다!
//@RestController
public class TestController {
	
//	@ResponseBody
	@GetMapping("/")
	public String hello() {
//		return "Hello, World!!!!!!!!";
		
		// src/main/resources/static 경로의 index.html 파일 렌더링하여 응답(@ResponseBody 또는 @RestController 가 적용되지 않아야 함)
		return "index.html";
	}
	
	// "/test" 서블릿 주소 매핑하는 test() 메서드 정의
	// => "test 주소 매핑 완료!" 문자열 응답
	@ResponseBody
	@GetMapping("/test")
	public String test() {
		return "test 주소 매핑 완료!";
	}
	
	// "/test2" 서블릿 주소 매핑하는 test() 메서드 정의
	@ResponseBody
	@GetMapping("/test2")
	public String test2() {
//		return "확인<br>\"테스트\"페이지";
		// 자바 15버전부터 텍스트블럭 기능으로 인해 "" 사이에 " 기호를 단순 문자열로써 추가 가능해졌음
		// """ 기호와 """ 기호 사이에 줄바꿈을 통해 원하는 문자열을 자유롭게 작성 가능해짐
		// => 주로, 자바에서 JSON 문자열을 직접 작성하여 그대로 응답할 때 사용
//		return """wr
//			확인<br>
//			"테스트"페이지	
//		""";
		
		
		//		return "{\"name\" : \"홍길동\"}";
//		return """
//			{
//					"name" : "홍길동",
//					"age" : 20
//			}
//		""";
		return "";
	}
	
	
	@ResponseBody
	@GetMapping("/test3")
	public String test3() {
		// formatted() 메서드 활용하여 문자열 포맷을 처리
		String name = "홍길동";
		int age = 20;
		double height = 180.1;
		
		// 기존 문자열 단순 결합 방식
//		String str = "이름 : " + name + ", 나이 : " + age + ", 키 : " + height + "cm";
//		return str;
		
		// String 클래스의 format() 메서드 활용하는 방식
//		String str = String.format("이름 : %s, 나이 : %d, 키 : %.1f cm", name, age, height);
//		return str;
		
		// String 클래스의 formatted() 메서드 활용하는 방식
		String str = "이름(formatted()) : %s<br>나이(formatted()) : %d<br>키(formatted()) : %.1f cm".formatted(name, age, height);
		return str;
	}
	
	
	@GetMapping("/test4")
	public String test4() {
		// @ResponseBody 또는 @RestController 가 적용되지 않은 컨트롤러 메서드에서
		// return 문에 String 타입으로 파일명 지정 시 뷰페이지를 렌더링하여 클라이언트에게 응답함
		// => 이 때, 일반적인 스프링프레임워크와 달리 prefix, suffix 값 등이 설정되어 있지 않으며
		//    기본 경로를 classpath:/static(src/main/resources/static) 또는 classpath:/public 경로로 지정되어 해당 경로에서 파일을 탐색함 
		return "index.html"; // src/main/resources/static/index.html 파일을 렌더링하여 응답
		// => 만약, 현재 컨트롤러가 @RestController 일 경우 index.html 파일을 찾지 않고 "index.html" 문자열을 그대로 응답데이터로 전송함
	}
	
	
	
}
















