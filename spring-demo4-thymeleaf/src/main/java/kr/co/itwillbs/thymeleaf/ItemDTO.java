package kr.co.itwillbs.thymeleaf;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
//@AllArgsConstructor
@ToString
public class ItemDTO {
	private String itemNm; // 상품명
	private int price; // 가격
	private int stockQty; // 재고수량
	private String itemDetail; // 상품상세설명
	private LocalDateTime regTime; // 상품등록일시
	private LocalDateTime updateTime; // 상품최종수정일시
	
	@Builder
	public ItemDTO(String itemNm, int price, int stockQty, String itemDetail, LocalDateTime regTime,
			LocalDateTime updateTime) {
		super();
		this.itemNm = itemNm;
		this.price = price;
		this.stockQty = stockQty;
		this.itemDetail = itemDetail;
		this.regTime = regTime;
		this.updateTime = updateTime;
	}
	
	
}












