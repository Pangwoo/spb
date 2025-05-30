package kr.co.itwillbs.spring_demo3.test3;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.extern.log4j.Log4j2;

@Log4j2 // Log4j2 를 활용한 로그 출력을 간단하게 설정해주는 어노테이션(Lombok)
@Controller
@RequestMapping("/item")
public class ItemController {
	@Autowired
	private ItemService itemService;
	
	@GetMapping("")
	public String itemMain() {
//		return "/item_main.html"; // src/main/resources/static/item_main.html
		return "/item/item_main.html"; // src/main/resources/static/item/item_main.html
	}
	
	@GetMapping("/dummyData")
	public String dummyData() {
		// @Log4j2 어노테이션을 활용하여 로그 메세지 출력하려면 log.xxx() 메서드 활용
		log.info(">>>>>>>> 더미데이터 생성!");
		
		// ItemService - createDummyData() 메서드 호출하여 더미데이터 생성
		itemService.createDummyData();
		itemService.createDummyDataAll();
		
//		return "/item_main.html"; // src/main/resources/static/item_main.html
//		return "/item/item_main.html"; // src/main/resources/static/item/item_main.html
		
		// /item 서블릿 주소로 리다이렉트
		return "redirect:/item";
	}
	
	// 1. 전체 상품 조회
	@ResponseBody
	@GetMapping("/showAllItem")
	public List<Item> showAllItem() {
		// ItemService - getAllItem() 메서드 호출
		List<Item> itemList = itemService.getAllItem();
		return itemList;
	}
	
	// 2. 단일 상품 조회(id 기준)
	@ResponseBody
	@GetMapping("/showItem")
	public Item showItem(@RequestParam("id") Long id) {
		// ItemService - getItem() 메서드 호출
		Item item = itemService.getItem(id);
		return item;
	}
	
	// 3. 단일 상품 조회(상품명(itemNm) 기준)
	@ResponseBody
	@GetMapping("/showItemByItemName")
	public Item showItemByItemName(@RequestParam("itemNm") String itemNm) {
		// ItemService - getItemByItemNm() 메서드 호출
		Item item = itemService.getItemByItemNm(itemNm);
		
		// 만약, 상품명이 동일한 제품이 존재할 경우 리턴타입은 List<Item> 타입으로 설정
//		List<Item> itemList = itemService.getItemByItemNm2(itemNm);
		
		return item;
	}
	
	// 4. 상품 조회(상품명(itemNm) 또는 상품상세설명(itemDetail) 기준)
	@ResponseBody
	@GetMapping("/showItemByItemNameOrItemDetail")
	public List<Item> showItemByItemNameOrItemDetail(@RequestParam("itemNm") String itemNm, @RequestParam("itemDetail") String itemDetail) {
		List<Item> itemList = itemService.getItemByItemNmOrItemDetail(itemNm, itemDetail);
		return itemList;
	}
	
	// 5. 주어진 가격(price) 이하 금액에 해당하는 상품 목록 조회
	@ResponseBody
	@GetMapping("/showItemByPrice")
	public List<Item> showItemByPrice(@RequestParam("price") int price) {
		List<Item> itemList = itemService.getItemByPriceLessThanEqual(price);
		return itemList;
	}
	
	// 6. 주어진 가격(minPrice, maxPrice) 범위 내에 해당하는 상품 목록 조회
	@ResponseBody
	@GetMapping("/showItemByPrice2")
	public List<Item> showItemByPrice2(@RequestParam("minPrice") int minPrice, @RequestParam("maxPrice") int maxPrice) {
		List<Item> itemList = itemService.getItemByPriceBetween(minPrice, maxPrice);
		return itemList;
	}
	
	// 7. 단일 상품 조회(상품명(itemNm)을 포함)
	@ResponseBody
	@GetMapping("/showItemByItemName2")
	public List<Item> showItemByItemName2(@RequestParam("itemNm") String itemNm) {
		// 만약, 상품명이 동일한 제품이 존재할 경우 리턴타입은 List<Item> 타입으로 설정
		List<Item> itemList = itemService.getItemByItemNm2(itemNm);
		return itemList;
	}
	
}























