package kr.co.itwillbs.spring_demo2.controller;

import java.util.Map;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/members") // 공통으로 요청되는 주소 부분을 매핑
public class RestApiController {
	// "/members/new"
	// "/members/list"
	// "/members/update"
//	@GetMapping("/members/new")
//	public String test1() {
//		return "/members/new 요청";
//	}
//	
//	@GetMapping("/members/list")
//	public String test2() {
//		return "/members/list 요청";
//	}
//	
//	@GetMapping("/members/update")
//	public String test3() {
//		return "/members/update 요청";
//	}
	// --------------------------------------
	// 요청주소에서 중복되는 부분 제거
	// 1) 컨트롤러 클래스 상단에 @RequestMapping 어노테이션을 활용하여 중복 주소 통합 매핑
	// 2) 컨트롤러 자체에서 공통 경로를 매핑하고 남은 경로를 추가로 매핑
//	@GetMapping("/new")
//	public String test1() {
//		return "/members/new 요청";
//	}
//	
//	@GetMapping("/list")
//	public String test2() {
//		return "/members/list 요청";
//	}
//	
//	@GetMapping("/update")
//	public String test3() {
//		return "/members/update 요청";
//	}
	// ==========================================================================================
	// RESTful 한 API 로 요청 주소 변경
	// [ 1. GET 방식 요청 - 데이터 조회(SELECT) 용도의 요청으로 사용 ]
	// 1) 회원 목록 조회(SELECT * FROM member) 를 요청하는 "/members" 주소 매핑(GET)
	@GetMapping("") // 이미 컨트롤러 자체에서 공통 경로 "/members" 를 매핑했으므로 널스트링으로 매핑
	public String getMemberList() {
		return "/members 요청";
	}
	// ----------------------------------
	// 2) 단일 회원 정보 조회(SELECT * FROM member WHERE id = ?) 를 요청하는 "/members/id값" 주소 매핑(GET)
	// => 목록 조회와 달리 /members/xxx 형식으로 회원 아이디 값이 함께 전달됨(ex. /members/1)
	//    이 때, 경로 뒤의 값을 경로 상에서 추출 가능(경로를 마치 파라미터처럼 활용)
	// => /경로/{추출할경로명} 형식으로 지정하고, 매핑 메서드에서 @PathVariable("추출할경로명") 지정하여 경로값을 파라미터로 바인딩
	@GetMapping("/{id}")
	public String getMember(@PathVariable("id") Long id) {
		System.out.println("id : " + id);
		return "/members/" + id + " 요청";
	}
	
	// ==================================================================
	// [ 2. POST 방식 요청 - 데이터 추가(INSERT) 용도의 요청으로 사용 ]
	// 회원 정보 추가(INSERT) 를 요청하는 "/members" 주소 매핑(POST)
	// => 위의 GET 방식 요청에 널스트링("") 매핑이 있지만, POST 방식으로 요청 시 서로 다른 요청으로 구별됨
	@PostMapping("")
	public String registMember(@RequestParam Map<String, String> params) {
		System.out.println("params : " + params);
		return "/members(POST) 요청";
	}
	
	// ==================================================================
	// [ 3. PUT 방식 요청 - 데이터 수정(UPDATE) 용도의 요청으로 사용 ]
	// 회원 정보 수정(UPDATE) 를 요청하는 "/members" 주소 매핑(POST 방식을 지정했지만 히든 필드 "_method" 를 자동으로 인식함)
	// => 주의! 히든 메서드 필터가 동작하도록 하기 위해 스프링부트 설정 파일(application.properties 등)에서
	//    spring.mvc.hiddenmethod.filter.enabled=true 설정 추가 필요
	// => 보통, PUT 메서드는 리소스 수정 목적으로 사용하지만, 해당 리소스가 없을 경우 새로 생성하는 작업도 수행할 수 있음
	//    이 때, 리소스에 대한 부분 변경(수정)만 작업해야할 경우 PUT 대신 PATCH 메서드를 사용함
	// => @PutMapping 어노테이션으로 PUT 방식 요청 매핑
//	@PutMapping("")
//	public String modifyMember(@RequestParam Map<String, String> params) {
	@PutMapping("/{id}")
	public String modifyMember(@PathVariable("id") Long id, @RequestParam Map<String, String> params) {
		System.out.println("id : " + id);
		System.out.println("params : " + params);
		return "/members(PUT) 요청";
	}
	
	// [ 4. DELETE 방식 요청 - 데이터 삭제(DELETE) 용도의 요청으로 사용 ]
	// 회원 정보 삭제(DELETE) 를 요청하는 "/members" 주소 매핑(POST 방식을 지정했지만 히든 필드 "_method" 를 자동으로 인식함)
	// => @DeleteMapping 어노테이션으로 PUT 방식 요청 매핑
	@DeleteMapping("/{id}")
	public String deleteMember(@PathVariable("id") Long id, @RequestParam Map<String, String> params) {
		System.out.println("id : " + id);
		System.out.println("params : " + params);
		return "/members(DELETE) 요청";
	}
	
	
	
}











