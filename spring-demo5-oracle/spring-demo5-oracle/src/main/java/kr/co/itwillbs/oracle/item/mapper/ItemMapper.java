package kr.co.itwillbs.oracle.item.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import kr.co.itwillbs.oracle.item.dto.ItemSearchDTO;
import kr.co.itwillbs.oracle.item.entity.Item;

@Mapper
public interface ItemMapper {

	// 상품 전체 목록 조회
	List<Item> findAllItems();

	// 상품정보 수정
	void updateItem(Map<String, String> params);

	// 검색 기능을 활용한 상품 목록 조회
	List<Item> findItemsBySearchParams(ItemSearchDTO itemSearchDTO);

}
