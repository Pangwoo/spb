package kr.co.itwillbs.oracle.commons.constant;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

@JsonFormat(shape = Shape.OBJECT) // JSON 객체 형태로 직렬화
public enum ItemSellStatus implements EnumType {
	AVAILABLE("판매중"),
	OUT_OF_STOCK("품절"),
	DISCONTINUED("단종");
	
//	@JsonValue
	private final String label;

	private ItemSellStatus(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}

	@Override
	public String getCode() {
		return this.name();
	}
	
}
