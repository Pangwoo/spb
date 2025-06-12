package kr.co.itwillbs.oracle.item.repository;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import kr.co.itwillbs.oracle.item.entity.Item;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {

//	List<Item> findBySearchParams(ItemSearchDTO itemSearchDTO);

	// JPQL 을 활용한 Item 엔티티 조회
//	@Query("SELECT i FROM Item i "
//			+ "WHERE "
//			+ "i.itemNm LIKE %:itemNm%")
//	List<Item> findBySearchParams(@Param("itemNm") String itemNm, @Param("minPrice") Integer minPrice, @Param("maxPrice") Integer maxPrice);

}
