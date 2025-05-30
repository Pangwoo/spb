package kr.co.itwillbs.spring_demo3.test2;

import java.util.Optional; 

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// 퍼시스턴스 계층 역할을 수행할 Repository 인터페이스 정의
// => JDBC API 와 연결되어 직접적인 DB 작업 수행을 담당
// => JpaRepository 인터페이스 상속받아 정의(제네릭타입은 <엔티티클래스타입, @Id 필드의 데이터타입>)
@Repository
public interface MemberRepository extends JpaRepository<Member, Long> { // 엔티티 클래스인 Member 와 Member 클래스의 @Id 필드의 타입인 Long 지정
	// SELECT * FROM member WHERE name = 'X' 문장 실행을 위한 쿼리 메서드 추가
	// => findByXXX() 형식의 메서드명을 사용하며, XXX 부분은 탐색할 컬럼명을 지정
	// => 이 때, 검색 결과가 하나일 경우 리턴타입을 Optional<T> 타입을 지정하고
	//    만약, 검색 결과가 복수개일 경우 리턴타입을 List<T> 타입을 지정
	Optional<Member> findByName(String name);
	
}
