package kr.co.itwillbs.oracle.item.controller;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.Arrays;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import jakarta.validation.Valid;
import kr.co.itwillbs.oracle.commons.constant.ItemCategory;
import kr.co.itwillbs.oracle.commons.constant.ItemSellStatus;
import kr.co.itwillbs.oracle.commons.util.EnumUtils;
import kr.co.itwillbs.oracle.commons.util.FieldUtils;
import kr.co.itwillbs.oracle.item.dto.ItemDTO;
import kr.co.itwillbs.oracle.item.dto.ItemSearchDTO;
import kr.co.itwillbs.oracle.item.entity.Item;
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
		
		// --------------------------------------------------------------------
		// 검색창 입력값 검증에 활용할 ItemSearchDTO 객체 생성 후 Model 에 저장
		model.addAttribute("itemSearchDTO", new ItemSearchDTO());
		// --------------------------------------------------------------------
		
		return "item/item_list";
	}
	
	// --------------------------------------------------------------------
	// AJAX 를 통해 목록 조회 요청 결과를 JSON 으로 리턴하기 위한 요청 매핑
	// => 응답데이터를 객체 상태 그대로 리턴하여 Jackson 라이브러리에 의해 자동으로 JSON 형태로 변환되어 리턴되며
	//    응답 데이터 그대로 리턴하기 위한 @ResponseBody 지정
	// => 메서드 리턴타입은 응답 데이터의 객체 타입을 그대로 지정(ex. List<ItemDTO> or Map<String, Object> 등)
	@ResponseBody
	@GetMapping("/listJson")
	public Map<String, Object> getItemListJson() {
		// 엔티티 객체를 직접 전달하는 것은 위험하므로 DTO 로 변환하여 리턴받기(목록 전체를 변환)
		List<ItemDTO> itemList = itemService.getItemListFromMapper();
		log.info(">>>>>>>>>>>>>>>>> itemList.size() : " + itemList.size());
		log.info(">>>>>>>>>>>>>>>>> itemList : " + itemList);
		
		Map<String, Object> map = new HashMap<>();
		map.put("itemList", itemList);
		
		// ---------------------------------------------------------------------------------------
		// 상품카테고리 및 상품판매상태에 대한 enum 클래스의 모든 상수를 가져와서 Map 객체에 추가
//		System.out.println(ItemCategory.values());
//		for(ItemCategory category : ItemCategory.values()) {
//			System.out.println(category + " : " + category.getLabel());
//		}
		
		// enum 클래스의 레이블 목록을 List 타입으로 변환
//		List<String> labels = Arrays.stream(ItemCategory.values())
//									.map(ItemCategory::getLabel)
//									.collect(Collectors.toList());
//		System.out.println(labels); // [전자제품, 의류, 식품, 도서, 아동]
		
		// enum 클래스의 상수(key) 목록과 레이블(value) 목록을 묶음으로 Map 타입으로 변환
//		Map<ItemCategory, String> itemCategoryMap = Arrays.stream(ItemCategory.values())
//															.collect(Collectors.toMap(
//																		category -> category, // 맵의 키(key) = 카테고리 enum 상수값 그대로 사용
//																		ItemCategory::getLabel)); // 맵의 값(value) = 카테고리 enum 상수의 레이블
//		System.out.println(itemCategoryMap); // {ELECTRONICS=전자제품, KIDS=아동, BOOKS=도서, CLOTHING=의류, FOOD=식품}
		// ---------------------------------------------------------------------------------------
		// ItemCategory 와 ItemSellStatus 등 복수개의 enum 을 공통으로 Map 으로 변환해주는 EnumUtils.convertAllEnumToMap() 메서드 활용
		// => 파라미터로 enum 클래스 전달 시 Class 타입에 맞게 .class 지정 필수!
		Map<String, String> itemCategories = EnumUtils.convertAllEnumToMap(ItemCategory.class);
		Map<String, String> itemSellStatuses = EnumUtils.convertAllEnumToMap(ItemSellStatus.class);
		
//		System.out.println("itemCategories : " + itemCategories);
//		System.out.println("itemSellStatuses : " + itemSellStatuses);
		
		// Map 객체에 enum 값 저장된 Map 객체 추가
		map.put("itemCategories", itemCategories);
		map.put("itemSellStatuses", itemSellStatuses);
		
		return map;
	}
	
	// AJAX 활용한 수정 요청 매핑("/items" - PATCH 방식)
	// => JSON 형식으로 전송되는 요청 데이터를 @RequestBody 활용하여 그대로 바인딩
	//    (스프링부트 기본 라이브러리 Jackson 을 통해 자동으로 JSON 데이터가 변환됨)
	@ResponseBody
	@PatchMapping
	public boolean updateJsonItem(@RequestBody Map<String, String> params) {
		System.out.println("params : " + params);
		
		// 컬럼명이 동적으로 전달되는데 이를 마이바티스에서 ${} 형태로 바인딩할 경우 SQL Injection 의 위험성이 있음
		// => 전달받은 파라미터의 컬럼명이 실제 존재하는 컬럼명일 경우에만 작업을 진행하고 아니면 예외 발생시켜 안전성 확보
		// => DB 에서 컬럼명을 조회하여 비교를 수행하거나 리플렉션(엔티티클래스 또는 DTO클래스의 필드를 추출)을 활용하여 비교 수행
		List<String> allowedFields = FieldUtils.getFieldNames(Item.class); // .class 필수! (엔티티 클래스 또는 DTO 클래스 모두 지정 가능)
		if(!allowedFields.contains(params.get("name"))) { // 파라미터 name 값이 필드 목록에 포함되어 있는지 확인
			// 포함되어 있지 않은 이름일 경우 예외 발생시키기
			throw new IllegalArgumentException("허용되지 않는 컬럼입니다!");
			// => 예외 발생 시 return true 값이 전달되지 않고 에러로 응답이 전송됨
		}
		
		// ItemService - updateItemForMapper() 메서드 호출하여 마이바티스를 통해 상품정보 업데이트 요청
		// => 요청 파라미터로 전달받은 컬럼명(Camel-case)을 Snake-Case 표기법으로 변환하여 다시 저장
		// => 주의! FieldUtils 클래스의 getFieldNames() 메서드를 활용하여 필드명 체크 완료 후 변환해야한다!
//		params.put("name", FieldUtils.camelCaseToSnakeCase(params.get("name")));
//		System.out.println("params : " + params);
//		itemService.updateItemForMapper(params);
		
		// ----------------------------------
		// ItemService - updateItem() 메서드 호출하여 JPA 를 통해 상품정보 업데이트 요청
		itemService.updateItem(params); 
		
		
		// boolean 타입을 @ResponseBody 로 그대로 리턴 시 JSON 형태 그대로 true/false 판별 가능함
		return true;
	}
	
	// ========================================================================================================================
	// 상품검색 요청 - "/items/search"(GET)
	@GetMapping("/search")
	public String searchItem() {
		
		
		
		return new String();
	}
	
	
	
	
}
















