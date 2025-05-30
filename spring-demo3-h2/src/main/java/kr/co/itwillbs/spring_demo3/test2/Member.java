package kr.co.itwillbs.spring_demo3.test2;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

// JPA 가 관리하는 엔티티 클래스 정의
@Entity
// => 엔티티 이름은 기본적으로 클래스 이름을 사용하지만 다른 이름으로 사용할 경우
//    @Entity(name = "엔티티명") 형태로 설정 가능 => 주의! 테이블명과 다르다!
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Member {
	// => Member 클래스는 VO 클래스 역할을 겸하면서, 엔티티 클래스로 관리되므로 클래스명인 Member 와 동일한 이름의 테이블이 DB 에 자동으로 생성됨
	// 테이블 내의 필드(컬럼) 선언 = 자바의 필드(멤버변수) 형태로 선언
	@Id // 현재 필드(id)를 기본키(PK)로 지정(주의! @Id 어노테이션과 필드명 id 는 무관함 = 달라도 됨)
	@Column(name = "id", updatable = false)
	// => name = "id" : 테이블 내의 컬럼명을 id 로 설정(생략 시 멤버변수명과 동일한 이름의 컬럼이 생성됨)
	// => updatable = false : 해당 컬럼은 데이터 저장 후 변경 불가능한 컬럼으로 지정됨(기본값 true)
	@GeneratedValue(strategy = GenerationType.AUTO) // ID 컬럼값 자동 생성 방법을 AUTO 로 지정
	// => IDENTITY : MySQL 에서의 AUTO_INCREMENT 방식으로 사용, SEQUENCE : Oracle 의 방식을 사용
	private Long id; // 테이블 정의 시 id 컬럼명의 컬럼타입이 정수(MySQL 에서 int 등) 타입으로 지정됨
	// => int 타입보다 값의 범위가 크고(+- 922경), long 타입(기본타입) 지정 시 값이 없으면 0 으로 취급하는데 이 때 값이 없는것과 값이 0인 상황 구분 불가
	//    따라서, Long 타입(Wrapper 타입) 지정 시 값이 없으면 null 값으로 초기화 되므로 값이 없음을 판별하기 용이함
	
	@Column(name = "name", length = 16, nullable = false) 
	// 변수명 관계없이 컬럼명을 무조건 name 으로 설정, 컬럼 길이 16 으로 지정(생략 시 String 타입의 경우 varchar(255) 형태로 지정됨)
	// nullable = false 설정 시 해당 컬럼은 NOT NULL 제약조건 설정
	private String name;
	
	// ---------------------------------------------------------------
	// UPDATE 구문 사용을 위한 메서드 정의
	public void changeName(String name) {
		// 파라미터로 전달받은 값을 필드(멤버변수)에 저장하면 기존 값이 변경되고
		// 이 변경된 상태를 JPA 가 감지(= 변경 감지 = 더티 체킹(Dirty checking))하여 트랜잭션 커밋 시점에 DB 에 UPDATE 수행
		this.name = name;
	}
	
}
















