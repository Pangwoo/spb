package kr.co.itwillbs.oracle.item.controller;

import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ItemSearchDTO {
	private String type;
	private String itemNm;
	
	@PositiveOrZero(message = "min price must be greater than 0")
	private Integer minPrice;
	
	@PositiveOrZero(message = "max price must be greater than 0")
	private Integer maxPrice;
}
