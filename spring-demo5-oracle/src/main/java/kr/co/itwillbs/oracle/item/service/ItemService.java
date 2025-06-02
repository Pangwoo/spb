package kr.co.itwillbs.oracle.item.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.itwillbs.oracle.item.dto.ItemDTO;
import kr.co.itwillbs.oracle.item.entity.Item;
import kr.co.itwillbs.oracle.item.mapper.ItemMapper;
import kr.co.itwillbs.oracle.item.repository.ItemRepository;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class ItemService {
	// 자동 주입 필드에 final 사용하려면 생성자 주입 방식 활용
	private final ItemRepository itemRepository;
	// 마이바티스 연동에 사용할 ItemMapper 객체 주입을 위한 필드 추가
	private final ItemMapper itemMapper;
	
	// 생성자를 통해 ItemRepository, ItemMapper 객체 등 주입
	public ItemService(ItemRepository itemRepository, ItemMapper itemMapper) {
		this.itemRepository = itemRepository;
		this.itemMapper = itemMapper;
	}
	// ------------------------------------------------
	// 상품 정보 등록 요청
	public void registItem(ItemDTO itemDTO) {
		// ItemDTO -> Item 타입으로 변환
		Item item = itemDTO.toEntity();
		log.info(">>>>>>>>>>>>>>>>>>> item : " + item);
		
		// ItemRepository - save() 메서드 호출하여 INSERT 작업 수행
		itemRepository.save(item);
	}
	
	// 상품 목록 조회
	public List<ItemDTO> getItemList() {
		// ItemRepository - findAll() 메서드 호출하여 SELECT * 작업 수행
//		return itemRepository.findAll();
		
		List<Item> itemList = itemRepository.findAll();
		
		// List<Entity> 타입 객체를 List<DTO> 타입 객체로 변환하여 리턴하기
//		return itemList.stream() // Stream 타입 객체로 변환(리스트(컬렉션) 요소를 하나씩 처리해주는 객체 타입)
////				.map(item -> ItemDTO.fromEntity(item)) // List<Item> 객체의 갯수는 유지하되 타입만 List<ItemDTO> 타입으로 변환
//				.map(ItemDTO :: fromEntity) // 위의 문장을 메서드 레퍼런스 문법을 사용하여 축약
//				.collect(Collectors.toList());
		
		// 별도의 메서드로 모듈화하여 재사용 가능하도록 변경
		return toItemDTOList(itemList);
	}
	
	// List<Entity> -> List<DTO> 타입으로 변환하는 메서드 정의(모듈화) => DB 에서 조회 후 외부로 전달할 때
	private List<ItemDTO> toItemDTOList(List<Item> itemList) {
		return itemList.stream() // Stream 타입 객체로 변환(리스트(컬렉션) 요소를 하나씩 처리해주는 객체 타입)
//				.map(item -> ItemDTO.fromEntity(item)) // List<Item> 객체의 갯수는 유지하되 타입만 List<ItemDTO> 타입으로 변환
				.map(ItemDTO :: fromEntity) // 위의 문장을 메서드 레퍼런스 문법을 사용하여 축약
				.collect(Collectors.toList());
	}
	
	// List<DTO> -> List<Entity> 타입으로 변환하는 메서드 정의(모듈화) => 외부에서 전달받아 DB 작업을 수행할 때
	private List<Item> toItemList(List<ItemDTO> itemDTOList) {
		return itemDTOList.stream() // Stream 타입 객체로 변환(리스트(컬렉션) 요소를 하나씩 처리해주는 객체 타입)
//				.map(itemDTO -> itemDTO.toEntity()) // List<ItemDTO> 객체의 갯수는 유지하되 타입만 List<Item> 타입으로 변환
				.map(ItemDTO :: toEntity) // 위의 문장을 메서드 레퍼런스 문법을 사용하여 축약
				.collect(Collectors.toList());
	}
	
	
	// =================================================================================================
	// ======================================= MyBatis 연동 ============================================
	public List<ItemDTO> getItemListFromMapper() {
		List<Item> itemList = itemMapper.findAllItems();
		System.out.println("Mapper - itemList : " + itemList);
		
		// toItemDTOList() 메서드 활용하여 List<Item> -> List<ItemDTO> 로 변환하여 리턴
		return toItemDTOList(itemList);
	}

}











