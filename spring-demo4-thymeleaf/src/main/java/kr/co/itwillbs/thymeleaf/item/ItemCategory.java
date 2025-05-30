package kr.co.itwillbs.thymeleaf.item;

public enum ItemCategory {
	// enum 값을 상수명(고유문자열)(실제사용할값("화면에표시할값")) 형태로 생성
	ELECTRONICS("전자제품"),
	CLOTHING("의류"),
	FOOD("식품"),
	BOOKS("도서"),
	KIDS("아동");
	
	// enum 상수값에 연결된 고유문자열을 관리할 변수 및 메서드 정의
	// 1) 화면에 표시할 값을 저장할 필드 선언
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
}















