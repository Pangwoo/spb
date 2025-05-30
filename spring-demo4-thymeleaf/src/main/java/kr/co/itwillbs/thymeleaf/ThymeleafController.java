package kr.co.itwillbs.thymeleaf;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/thymeleaf")
public class ThymeleafController {
	
	// 단일 텍스트 데이터를 뷰페이지에서 출력하기
	@GetMapping("/test1")
	public String test1(Model model) {
		model.addAttribute("data", "타임리프 예제");
		
		// 스프링+JSP 와 마찬가지로 return 문 뒤에 포워딩 할 뷰페이지를 명시
		// => 포워딩 기준이 되는 경로는 src/main/resources/templates 디렉토리이다!
		return "thymeleaf_test1"; // 확장자명(.html) 생략 가능
	}
	
	// ---------------------------------------------------------------------------
	// 단일 객체를 뷰페이지에서 출력하기
	@GetMapping("/test2")
	public String test2(Model model) {
		// ItemDTO 객체 생성 - Builder 패턴 활용
		ItemDTO itemDTO = ItemDTO.builder()
				.itemNm("테스트 상품1")
				.itemDetail("테스트 상품1 상세설명")
				.price(10000)
				.stockQty(10)
				.regTime(LocalDateTime.now())
				.build();
		
		model.addAttribute("itemDTO", itemDTO);
		
		return "thymeleaf_test2";
	}
	
	// ---------------------------------------------------------------------------
	// 다중 객체(리스트)를 뷰페이지에서 출력하기
	@GetMapping("/test3")
	public String test3(Model model) {
		List<ItemDTO> itemList = new ArrayList<>();
		
		for(int i = 1; i <= 10; i++) {
			// ItemDTO 객체 생성 - Builder 패턴 활용
			ItemDTO itemDTO = ItemDTO.builder()
					.itemNm("테스트 상품" + i)
					.itemDetail("테스트 상품" + i + " 상세설명")
					.price(10000 * i)
					.stockQty(i)
					.regTime(LocalDateTime.now())
					.build();
			
			itemList.add(itemDTO);
		}
		
		model.addAttribute("itemList", itemList);
		
		return "thymeleaf_test3";
	}
	
	// ---------------------------------------------------------------------------
	// 조건문 연습
	@GetMapping("/test4")
	public String test4(Model model) {
		List<ItemDTO> itemList = new ArrayList<>();
		
		for(int i = 1; i <= 10; i++) {
			// ItemDTO 객체 생성 - Builder 패턴 활용
			ItemDTO itemDTO = ItemDTO.builder()
					.itemNm("테스트 상품" + i)
					.itemDetail("테스트 상품" + i + " 상세설명")
					.price(10000 * i)
					.stockQty(i)
					.regTime(LocalDateTime.now())
					.build();
			
			itemList.add(itemDTO);
		}
		
		model.addAttribute("itemList", itemList);
		
		return "thymeleaf_test4";
	}
	// ---------------------------------------------------------------------------
	// 하이퍼링크 연습
	@GetMapping("/test5")
	public String test5(Model model) {
		model.addAttribute("data", "데이터");
		
		return "thymeleaf_test5";
	}
	
	@GetMapping("/test5_2")
	public String test5_2(@RequestParam Map<String, String> params, Model model) {
		model.addAttribute("params", params);
		model.addAttribute("param1", params.get("param1"));
		model.addAttribute("param2", params.get("param2"));
		model.addAttribute("param3", params.get("param3"));
		
		return "thymeleaf_test5_2";
	}
	
	// ---------------------------------------------------------------------------
	// 타임리프 레이아웃 연습(build.gradle 파일에 레이아웃 관련 의존성 추가)
	@GetMapping("/test6")
	public String test6() {
		return "thymeleaf_test6_layout";
	}
	
	@GetMapping("/test6_2")
	public String test6_2() {
		return "thymeleaf_test6_2";
	}
	
	@GetMapping("/test6_3")
	public String test6_3() {
		return "thymeleaf_test6_3";
	}
	
	@GetMapping("/test7")
	public String test7() {
		return "thymeleaf_test7";
	}
	
	
}
























