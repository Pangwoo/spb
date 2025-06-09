package kr.co.itwillbs.oracle.item.dto;

import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class ItemSearchDTO {
	private String type;
	private String itemNm;
	
	@PositiveOrZero(message = "최소 가격은 0 이상 입력이 가능합니다!")
	private Integer minPrice;
	@PositiveOrZero(message = "최대 가격은 0 이상 입력이 가능합니다!")
	private Integer maxPrice;
}
