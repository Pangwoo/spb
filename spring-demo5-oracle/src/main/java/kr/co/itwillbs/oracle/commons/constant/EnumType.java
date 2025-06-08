package kr.co.itwillbs.oracle.commons.constant;

// enum 클래스를 통해 getCode() 메서드와 getLabel() 메서드를 공통으로 관리하기 위한 인터페이스 정의
public interface EnumType {
	// 각 enum 클래스가 가진 메서드들을 업캐스팅 후에도 호출 가능하도록 공통메서드로 정의
	String getCode();
	String getLabel();
}
