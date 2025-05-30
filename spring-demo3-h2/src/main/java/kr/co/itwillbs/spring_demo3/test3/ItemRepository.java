package kr.co.itwillbs.spring_demo3.test3;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> { // 엔티티 클래스인 Member 와 Member 클래스의 @Id 필드의 타입인 Long 지정

	// 3. 단일 상품 조회(상품명(itemNm) 기준)
	Optional<Item> findByItemNm(String itemNm);

	// 4. 상품 조회(상품명(itemNm) 또는 상품상세설명(itemDetail) 기준)
	List<Item> findByItemNmOrItemDetail(String itemNm, String itemDetail);

	// 5. 주어진 가격(price) 이하 금액에 해당하는 상품 목록 조회
	List<Item> findByPriceLessThanEqual(int price); 
	
	// 6. 주어진 가격(minPrice, maxPrice) 범위 내에 해당하는 상품 목록 조회
	List<Item> findByPriceBetween(int minPrice, int maxPrice);

	// SELECT * FROM item WHERE itemNm LIKE %?%
	List<Item> findByItemNmContaining(String itemNm);
}
