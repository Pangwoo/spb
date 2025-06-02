package kr.co.itwillbs.oracle.item.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.validation.Valid;
import kr.co.itwillbs.oracle.item.dto.ItemDTO;
import kr.co.itwillbs.oracle.item.service.ItemService;
import lombok.extern.log4j.Log4j2;


@Log4j2
@Controller
@RequestMapping("/items")
public class ItemController {
	@Autowired
	private ItemService itemService;

	@GetMapping("/new")
	public String showRegistForm(Model model) {
		// ItemDTO 객체를 Model 객체에 추가(item)
		model.addAttribute("item", new ItemDTO());
		// enum 타입 ItemCategory 의 모든 값을 배열 형식으로 변환하여 Model 객체에 저장(뷰페이지에서 목록 표시 자동화를 위함) 
//		model.addAttribute("categories", ItemCategory.values()); // 리턴타입 : ItemCategory[](하나하나의 enum 타입이 됨)
//		model.addAttribute("sellStatuses", ItemSellStatus.values()); // 리턴타입 : ItemSellStatus[](하나하나의 enum 타입이 됨)
		// => DTO 클래스에서 values 값을 미리 저장하도록 처리 가능
		
		return "item/item_regist_form";
	}
	
	// POST 방식으로 요청되는 "/items" 요청 매핑
	// => 파라미터로 전달되는 값들을 ItemDTO 타입 객체로 바인딩
	// => 작업 완료 후 아이템 메인페이지("/items")로 리다이렉트
	// --------------------------------------------------------
	// ItemDTO 객체에 바인딩되는 파라미터들에 대한 Validation Check 수행을 위해
	// ItemDTO 타입 변수 선언부 앞에 @Valid 어노테이션 지정
	// => 이 때, 체크 결과를 뷰페이지에서 활용하기 위해서는 뷰페이지에서 사용할 DTO 객체의 이름을 @ModelAttribute 어노테이션에 명확하게 명시!
	// => 전달된 파라미터들이 ItemDTO 객체에 바인딩될 때 입력값 검증 수행하고 이 결과를 BindingResult 타입 객체에 전달함
	@PostMapping("")
	public String registItem(@ModelAttribute("item") @Valid ItemDTO itemDTO, BindingResult bindingResult) {
		System.out.println("itemDTO : " + itemDTO);
		
		// BindingResult 객체의 메서드를 호출하여 입력값 검증 결과 얻어올 수 있음
		System.out.println("bindResult.getAllError() : " + bindingResult.getAllErrors()); // 검증 결과 전체에 대한 정보 확인
		// => bindResult.getAllError() : [Field error in object 'item' on field 'itemNm': rejected value []; codes [NotBlank.item.itemNm,NotBlank.itemNm,NotBlank.java.lang.String,NotBlank]; arguments [org.springframework.context.support.DefaultMessageSourceResolvable: codes [item.itemNm,itemNm]; arguments []; default message [itemNm]]; default message [상품명은 필수 입력값입니다!], Field error in object 'item' on field 'category': rejected value [null]; codes [NotNull.item.category,NotNull.category,NotNull.kr.co.itwillbs.thymeleaf.item.ItemCategory,NotNull]; arguments [org.springframework.context.support.DefaultMessageSourceResolvable: codes [item.category,category]; arguments []; default message [category]]; default message [상품카테고리 선택은 필수입니다!], Field error in object 'item' on field 'itemDetail': rejected value []; codes [NotEmpty.item.itemDetail,NotEmpty.itemDetail,NotEmpty.java.lang.String,NotEmpty]; arguments [org.springframework.context.support.DefaultMessageSourceResolvable: codes [item.itemDetail,itemDetail]; arguments []; default message [itemDetail]]; default message [상품상세설명은 필수 입력값입니다!], Field error in object 'item' on field 'sellStatus': rejected value [null]; codes [NotNull.item.sellStatus,NotNull.sellStatus,NotNull.kr.co.itwillbs.thymeleaf.item.ItemSellStatus,NotNull]; arguments [org.springframework.context.support.DefaultMessageSourceResolvable: codes [item.sellStatus,sellStatus]; arguments []; default message [sellStatus]]; default message [상품판매상태 선택은 필수입니다!], Field error in object 'item' on field 'stockQty': rejected value [null]; codes [NotNull.item.stockQty,NotNull.stockQty,NotNull.java.lang.Integer,NotNull]; arguments [org.springframework.context.support.DefaultMessageSourceResolvable: codes [item.stockQty,stockQty]; arguments []; default message [stockQty]]; default message [가격은 필수 입력값입니다!], Field error in object 'item' on field 'price': rejected value [null]; codes [NotNull.item.price,NotNull.price,NotNull.java.lang.Integer,NotNull]; arguments [org.springframework.context.support.DefaultMessageSourceResolvable: codes [item.price,price]; arguments []; default message [price]]; default message [가격은 필수 입력값입니다!]]
		// => 오류가 발생한 모든 대상에 대한 정보가 다 출력됨
		System.out.println("bindResult.hasError() : " + bindingResult.hasErrors()); // 검증 오류 발생 여부 리턴(true/false)
		
		// 검증 결과 오류 발생했을 경우 다시 입력폼으로 포워딩
		if(bindingResult.hasErrors()) {
			// 이 때, Model 객체를 활용하여 ItemDTO 객체를 저장하지 않아도 자동으로 뷰페이지로 전송됨(오류 정보도 함께 전송됨)
			return "/item/item_regist_form";
		}
		// ----------------------------------------------------------------------------
		// ItemService - registItem() 메서드 호출하여 상품 정보 등록 요청
		itemService.registItem(itemDTO);
		
		// ----------------------------------------------------------------------------
		return "redirect:/items/list";
	}
	
	// GET 방식 "/list" 매핑
	@GetMapping("/list")
	public String getItemList(Model model) {
		// ItemService - getItemList() 메서드 호출하여 상품 목록 조회 요청
		// => 파라미터 : 없음   리턴타입 : List<Item>(itemList)
//		List<Item> itemList = itemService.getItemList();
		
		// 엔티티 객체를 직접 전달하는 것은 위험하므로 DTO 로 변환하여 리턴받기(목록 전체를 변환)
		List<ItemDTO> itemList = itemService.getItemList();
		log.info(">>>>>>>>>>>>>>>>> itemList.size() : " + itemList.size());
		log.info(">>>>>>>>>>>>>>>>> itemList : " + itemList);
		
		model.addAttribute("itemList", itemList);
		
		return "item/item_list";
	}
	
	// 마이바티스 연동을 통한 상품 목록 조회 - GET 방식 "/list2" 매핑
	@GetMapping("/list2")
	public String getItemListFromMapper(Model model) {
		// 엔티티 객체를 직접 전달하는 것은 위험하므로 DTO 로 변환하여 리턴받기(목록 전체를 변환)
		List<ItemDTO> itemList = itemService.getItemListFromMapper();
		log.info(">>>>>>>>>>>>>>>>> itemList.size() : " + itemList.size());
		log.info(">>>>>>>>>>>>>>>>> itemList : " + itemList);
		
		model.addAttribute("itemList", itemList);
		
		return "item/item_list";
	}
	
}
















