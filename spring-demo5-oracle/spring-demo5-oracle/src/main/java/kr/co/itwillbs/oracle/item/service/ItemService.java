package kr.co.itwillbs.oracle.item.service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.Valid;
import kr.co.itwillbs.oracle.commons.util.FieldUtils;
import kr.co.itwillbs.oracle.item.dto.ItemDTO;
import kr.co.itwillbs.oracle.item.dto.ItemSearchDTO;
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
	
	// 상품 이미지 작업에 사용될 ItemImgService 객체 주입
	private final ItemImgService itemImgService;
	
	// 생성자를 통해 ItemRepository, ItemMapper 객체 등 주입
	public ItemService(ItemRepository itemRepository, ItemMapper itemMapper, ItemImgService itemImgService) {
		this.itemRepository = itemRepository;
		this.itemMapper = itemMapper;
		this.itemImgService = itemImgService;
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
	
	// 상품 정보와 상품 이미지 정보 등록 요청
	@Transactional
	public Long registItem(ItemDTO itemDTO, List<MultipartFile> itemImgFiles) throws Exception {
		// ItemDTO -> Item 타입으로 변환
		Item item = itemDTO.toEntity();
		// ItemRepository - save() 메서드 호출하여 INSERT 작업 수행
		itemRepository.save(item);
		
		// ItemImgService - registItemImg() 메서드 호출하여 상품 이미지 등록 요청
		// => 파라미터 : Item 객체, 상품이미지 List 객체
		itemImgService.registItemImg(item, itemImgFiles);
		
		// 상품번호 리턴
		return item.getId();
	}
	// =====================================================================
	
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
	
	
	
	// 상품 업데이트 요청 - 마이바티스 활용
	@Transactional
	public void updateItemForMapper(Map<String, String> params) {
		itemMapper.updateItem(params);
	}
	
	
	// 상품 업데이트 요청 - JPA 활용
	// => 주의! JPA 업데이트 작업 시 @Transactional 어노테이션 필수!
	@Transactional
	public void updateItem(Map<String, String> params) {
		// Map 객체에 저장된 상품번호(id) 활용하여 DB 로부터 상품정보 조회
		Long id = Long.parseLong(params.get("id"));
		// ItemRepository - findById() 메서드 활용
		Item item = itemRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("상품 정보 없음 - " + id));
		
		// Item 엔티티에 Map 객체에 저장되어있는 속성값 꺼내서 저장
		// 단, 어떤 컬럼값을 변경할 지 알 수 없으므로 Map 객체의 name 속성값에 해당하는 필드에 value 속성값에 해당하는 값을 저장해야함
		// Map -> Entity 로 변환할 때 name 속성명과 엔티티 필드명을 자동으로 대응하기 위해 별도의 메서드 정의하여 활용
		// => 파라미터 : 엔티티 객체, 변경할 필드명, 변경할 데이터
		// => 엔티티 객체를 전달하여 값을 직접 변경하므로 별도로 리턴받을 필요 없음
		FieldUtils.convertFieldValue(item, params.get("name"), params.get("value"));
		// => 엔티티의 값이 변경되면 즉시 해당 변경 사항이 DB 에 반영됨(더티체킹)
	}
	
	
	// =====================================================================
	// 상품검색을 통한 상품 목록 조회 요청
	public List<ItemDTO> getItemList(ItemSearchDTO itemSearchDTO) {
		// 마이바티스 활용
		List<Item> itemList = itemMapper.findItemsBySearchParams(itemSearchDTO);

		// JPA - JPQL 활용
//		List<Item> itemList = itemRepository.findBySearchParams(itemSearchDTO);
//		List<Item> itemList = itemRepository.findBySearchParams(itemSearchDTO.getItemNm(), itemSearchDTO.getMinPrice(), itemSearchDTO.getMaxPrice());
		
		return toItemDTOList(itemList);
	}
	
	
	
	
	

}











