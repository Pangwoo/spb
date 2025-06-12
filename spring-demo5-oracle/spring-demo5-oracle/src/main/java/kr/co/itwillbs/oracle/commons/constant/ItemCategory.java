package kr.co.itwillbs.oracle.commons.constant;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.fasterxml.jackson.annotation.JsonValue;

// @JsonFormat : Jackson 라이브러리를 통해 enum 클래스의 모든 필드들을 JSON 객체 형태로 직렬화하는 경우 사용
// => @JsonFormat(shape = Shape.XXX) 형태로 직렬화할 타입 지정(기본값 : String => 문자열 형태로 직렬화)
@JsonFormat(shape = Shape.OBJECT) // JSON 객체 형태로 직렬화
public enum ItemCategory implements EnumType { // enum 클래스 공통 상속 구조를 부여하기 위한 인터페이스 상속
	// enum 값을 상수명(고유문자열)(실제사용할값("화면에표시할값")) 형태로 생성
	ELECTRONICS("전자제품"),
	CLOTHING("의류"),
	FOOD("식품"),
	BOOKS("도서"),
	KIDS("아동");
	
	// -----------------------------------------------------------------------------
	// enum 상수값에 연결된 고유문자열을 관리할 변수 및 메서드 정의
	// 1) 화면에 표시할 값을 저장할 필드 선언
//	private final String label;

	// @JsonValue : enum 또는 클래스의 특정 값만 JSON 형식으로 직렬화하는 경우 사용
	// => 주로, 특정 상수에 대한 레이블을 사용하여 JSON 응답을 처리하고 싶을 경우 사용
	// => 멤버변수 또는 메서드에 적용(메서드에 적용 시 추가적인 작업 수행 가능)
//	@JsonValue
	private final String label;
	

	// 2) 생성자 정의(label 을 파라미터로 전달받아 초기화)
	// => enum 의 각 상수가 할당될 때 내부적으로 자동으로 생성자가 호출됨(외부에서 호출 불가 = private 선언) 
	private ItemCategory(String label) {
		this.label = label;
	}

	// 3) Getter 정의(label 리턴)
	// => 외부에서 label 값을 읽어올 수 있도록 하기 위함
	public String getLabel() {
		return label;
	}
	
	// -------------------------
	// 4) getCode() 메서드 정의 => 실제 enum 상수값 리턴(레이블이 아님!)
	@Override
	public String getCode() {
		// 현재 enum 객체의 name() 메서드 호출하면 자동으로 상수값 리턴
		return this.name();
	}
	
}















