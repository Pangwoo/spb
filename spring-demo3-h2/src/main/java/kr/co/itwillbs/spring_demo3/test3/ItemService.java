package kr.co.itwillbs.spring_demo3.test3;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class ItemService {
	@Autowired
	private ItemRepository itemRepository;

	@Transactional
	public void createDummyData() {
		// INSERT 수행 시각 체크를 위해 작업 시작, 종료 시각 체크(데이터 10000건 이상 있어야 유의미함)
		long startTime = System.currentTimeMillis(); // 시작 시각 체크
		
		// 반복문을 사용하여 더미데이터 10개 생성
		for(int i = 1; i <= 10; i++) {
			// 빌더 패턴을 활용하여 Item 엔티티 객체 생성 및 값 저장
			Item item = Item.builder()
					.itemNm("테스트 상품 " + i)
					.price(new SecureRandom().nextInt(10000) * i)
					.stockQty(new SecureRandom().nextInt(100) * i)
					.itemDetail("상품 상세설명 " + i)
					.regTime(LocalDateTime.now())
					.build();
			
			// ItemRepository - save() 메서드 호출하여 데이터 추가 요청
			itemRepository.save(item);
		}
		
		long completeTime = System.currentTimeMillis(); // 종료 시각 체크
		
		log.info(">>>>>>>> save() 소요 시간 : " + (completeTime - startTime) + " ms");
	}

	@Transactional
	public void createDummyDataAll() {
		long startTime = System.currentTimeMillis(); // 시작 시각 체크
		
		// 자동으로 생성되는 엔티티 묶음을 관리할 List 객체 생성
		List<Item> itemList = new ArrayList<>();
		
		// 반복문을 사용하여 더미데이터 10개 생성
		for(int i = 1; i <= 10; i++) {
			// 빌더 패턴을 활용하여 Item 엔티티 객체 생성 및 값 저장
			Item item = Item.builder()
					.itemNm("테스트 상품 " + i)
					.price(new SecureRandom().nextInt(10000) * i)
					.stockQty(new SecureRandom().nextInt(100) * i)
					.itemDetail("상품 상세설명 " + i)
					.regTime(LocalDateTime.now())
					.build();

			// List<Item> 객체에 엔티티 추가
			itemList.add(item);
		}
		
		// ItemRepository - save() 메서드 호출하여 데이터 추가 요청하는 대신
		// saveAll() 메서드를 사용하여 한번에 INSERT 작업 수행할 수 있다!
		// => save() 메서드를 반복하는것보다 훨씬 작업 속도가 빠르다!
		itemRepository.saveAll(itemList);
		
		long completeTime = System.currentTimeMillis(); // 종료 시각 체크
		
		log.info(">>>>>>>> saveAll() 소요 시간 : " + (completeTime - startTime) + " ms");
		
	}

	// 1. 전체 상품 정보 조회
	public List<Item> getAllItem() {
		createDummyDataAll(); // 더미데이터 등록 요청
		// ------------------------------------------
		// ItemRepository - findAll() 메서드 호출하여 전체 레코드 조회
		return itemRepository.findAll();
	}

	// 2. 단일 상품 조회(id 기준)
	public Item getItem(Long id) {
		createDummyDataAll(); // 더미데이터 등록 요청
		// ------------------------------------------
		// ItemRepository - findById() 메서드 호출하여 단일 레코드 조회
		return itemRepository.findById(id)
				.orElseThrow(() -> new NoSuchElementException("상품이 존재하지 않습니다!(상품번호 : " + id + ")"));
	}

	
	// 3. 단일 상품 조회(상품명(itemNm) 기준)
	public Item getItemByItemNm(String itemNm) {
		createDummyDataAll(); // 더미데이터 등록 요청
		// ------------------------------------------
		// ItemRepository - findByItemNm() 메서드 호출하여 단일 레코드 조회 => ItemRepository 메서드 추가 정의 필요
		return itemRepository.findByItemNm(itemNm)
				.orElseThrow(() -> new NoSuchElementException("상품이 존재하지 않습니다!(상품명 : " + itemNm + ")"));
	}

	// 4. 상품 조회(상품명(itemNm) 또는 상품상세설명(itemDetail) 기준)
	public List<Item> getItemByItemNmOrItemDetail(String itemNm, String itemDetail) {
		createDummyDataAll(); // 더미데이터 등록 요청
		// ------------------------------------------
		return itemRepository.findByItemNmOrItemDetail(itemNm, itemDetail);
	}
	
	// 5. 주어진 가격(price) 이하 금액에 해당하는 상품 목록 조회
	public List<Item> getItemByPriceLessThanEqual(int price) {
		createDummyDataAll(); // 더미데이터 등록 요청
		// ------------------------------------------
		return itemRepository.findByPriceLessThanEqual(price);
	}
	
	// 6. 주어진 가격(minPrice, maxPrice) 범위 내에 해당하는 상품 목록 조회
	public List<Item> getItemByPriceBetween(int minPrice, int maxPrice) {
		createDummyDataAll(); // 더미데이터 등록 요청
		// ------------------------------------------
		return itemRepository.findByPriceBetween(minPrice, maxPrice);
	}

	public List<Item> getItemByItemNm2(String itemNm) {
		createDummyDataAll(); // 더미데이터 등록 요청
		// ------------------------------------------
		return itemRepository.findByItemNmContaining(itemNm);
	}
}















