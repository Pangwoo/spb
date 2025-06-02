package kr.co.itwillbs.oracle.item.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.itwillbs.oracle.item.entity.Item;

@Mapper
public interface ItemMapper {

	// 상품 전체 목록 조회
	List<Item> findAllItems();

}
