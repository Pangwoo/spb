package kr.co.itwillbs.spring_demo3.test2;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MemberController {
	@Autowired
	private MemberService memberService;
	
	@GetMapping("/allMembers")
	public List<Member> getAllMembers() {
		// MemberService - getAllMembers() 메서드 호출
		List<Member> memberList = memberService.getAllMembers();
		
		return memberList;
		// => @ResponseBody 또는 @RestController 가 적용된 메서드에서 객체 리턴 시
		//    스프링부트에 내장된(정확히는 spring-boot-starter-web 추가 시 포함됨) 기본 JSON 라이브러리인
		//    Jackson 을 사용하여 객체를 JSON 문자열로 자동 변환하여 리턴
		//    (리턴되는 객체를 Jackson 라이브러리의 메세지 컨버터에 의해 JSON 문자열로 직렬화가 일어남)
	}

	@GetMapping("/member")
	public Member member(@RequestParam("id") Long id) {
		System.out.println("id : " + id);
		
		// MemberService - getMember() 메서드 호출
		// => 파라미터 : 아이디(id)   리턴타입 : Member 엔티티
		Member member = memberService.getMember(id);
		
		return member;
	}
	
	// GET 방식 - findMemberName 매핑
	// => 파라미터 name 전달됨
	@PostMapping("/findMemberName")
	public Member findMemberName(@RequestParam("name") String name) {
		// MemberService - findMemberName() 호출
		// => 파라미터 : 이름   리턴타입 : Member
		Member member = memberService.findMemberName(name);
		return member;
	}
	
	// POST 방식 - registMember 매핑
	// => 파라미터 name 전달됨
	@PostMapping("/registMember")
	public String registMember(@RequestParam("name") String name) {
		// MemberService - registMember() 호출
		// => 파라미터 : 이름   리턴타입 : 없음
		memberService.registMember(name);
		return name;
	}
	
	// POST 방식 - registMember 매핑
	// => 파라미터 name 전달됨
	@PostMapping("/removeMember")
	public String removeMember(@RequestParam("id") Long id) {
		// MemberService - removeMember() 호출
		// => 파라미터 : 이름   리턴타입 : 없음
		memberService.removeMember(id);
		return "";
	}
	
	// POST 방식 - modifyMember 매핑
	// => 파라미터 name 전달됨
	@PostMapping("/modifyMember")
	public String modifyMember(@RequestParam Map<String, String> params) {
		// MemberService - modifyMember() 호출
		// => 파라미터 : Map   리턴타입 : 없음
		memberService.modifyMember(params);
		return "";
	}
	
	
	
}















