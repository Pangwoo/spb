package kr.co.itwillbs.spring_demo3.test2;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MemberService {
	@Autowired
	private MemberRepository memberRepository;

	public List<Member> getAllMembers() {
		// 전체 member 테이블 조회(SELECT * FROM member)
		// => MemberRepository - findAll() 메서드 호출
		return memberRepository.findAll();
		// => SELECT * FROM member 구문을 실행하는 메서드
		// => findAll() 메서드 리턴타입이 List<T> 타입인데, 
		//    MemberRepository 정의 시 extends JpaRepository<Member, Long> 형태로 상속을 표현했으므로
		//    제네릭타입 첫번째인 Member 클래스(= 엔티티 클래스)가 T 부분에 해당하는 타입이 된다!
		// => 따라서, 전체 레코드 조회 결과가 Member 엔티티의 집합인 List<Member> 타입으로 리턴됨
	}

	public Member getMember(Long id) {
		// member 테이블의 id 값이 일치하는 1개 레코드 조회(SELECT * FROM member WHERE id = ?)
		// => MemberRepository - findById() 메서드 호출하여 ID 값 기준으로 레코드 검색(주의! 컬럼명이 아닌 @Id 가 붙은 컬럼을 의미함)
//		return memberRepository.findById(id).get();
//		return memberRepository.findById(2L).get();
		// => findById() 메서드는 PK(@Id 로 설정된 컬럼)를 기준으로 조회를 수행하는 메서드이다.
		//    이 때, 파라미터로 id 값을 전달하는데 extends JpaRepository<Member, Long> 의 두번째 파라미터 타입(Long)으로 사용
		//    즉, Long 타입(1 -> 1L) 형태로 파라미터를 전달하면 
		//    SELECT * FROM member WHERE id = 전달된값; 구문으로 변환되어 실행됨
		// => 또한, findById() 메서드 리턴타입이 Optional<T> 이며 
		//    extends JpaRepository<Member, Long> 의 첫번째 파라미터 타입이 T 에 해당됨.
		//    이 때, java.util.Optional 타입은 Null 을 안전하게 처리하기 위한 Wrapper 클래스이며
		//    데이터가 없을 때 null 을 직접 다루지 않고 Optional.empty() 값(= 결국 비어있는값) 을 리턴
		//    따라서, NullPointerException 을 방지할 수 있으며 메서드 체이닝으로 ifPresent() = 값이 있음 등으로 처리 가능
		// => 최종적으로 findById() 로 조회된 Optional<T> 타입 객체에 .get() 메서드를 호출하면
		//    <T> 타입에 해당하는 객체를 얻을 수 있고 이 결과값을 isEmpty() 등으로 판별이 가능함.
//		Optional<Member> optionalMember = memberRepository.findById(id);
//		
//		if(optionalMember.isEmpty()) {
//			System.out.println("조회 결과 없음!");
//		} else if(optionalMember.isPresent()) {
//			System.out.println("조회 결과 있음!");
//			System.out.println("Member : " + optionalMember.get());
//			return optionalMember.get();
//		}
//		
//		return new Member();
		// ------------------------------------------------------------
		// Optional 객체의 orElseThrow() 메서드 활용하여 값이 없을 경우 NoSuchElementException 예외를 외부로 던질 수 있음
		// => 데이터가 존재할 경우 자동으로 get() 메서드가 호출되어 엔티티가 리턴됨
		return memberRepository.findById(id)
				.orElseThrow(() -> new NoSuchElementException("존재하지 않는 회원입니다!"));
		
	}

	// 이름으로 회원정보 탐색
	public Member findMemberName(String name) {
		// MemberRepository - findByXXX() 메서드를 통해 이름으로 탐색수행해야하지만
		return memberRepository.findByName(name)
				.orElseThrow(() -> new NoSuchElementException("존재하지 않는 회원입니다!"));
	}

	// 이름 저장
	// => 주의! INSERT, UPDATE, DELETE 작업 등의 메서드 상단에 @Transactional 어노테이션을 통해 트랜잭션 적용 필요
	@Transactional
	public void registMember(String name) {
		// INSERT 수행에 사용될 엔티티 객체 생성(전달받은 이름을 엔티티에 저장)
		// => PK 값으로 사용되는 id 값은 null 값 전달
		// => 주의! data.sql 파일 등을 통해 더미데이터 저장하지 말 것!
		Member member = new Member(null, name);
		// MemberRepository - save() 메서드 호출하여 INSERT 작업 요청
		// => 파라미터 : 엔티티 객체(Member 객체)
		memberRepository.save(member);
	}

	@Transactional
	public void removeMember(Long id) {
//		System.out.println("id : " + id);
		
		// 삭제할 id 에 대한 엔티티 조회
		Member member = memberRepository.findById(id)
							.orElseThrow(() -> new NoSuchElementException("존재하지 않는 회원입니다!"));
		System.out.println("Member : " + member);
		
		// MemberRepository - delete() 메서드 호출하여 레코드 삭제 요청
		// => 파라미터 : 삭제할 엔티티(조회가 선행되어야 함)
		memberRepository.delete(member);
	}

	@Transactional
	public void modifyMember(Map<String, String> params) {
		System.out.println("params : " + params);
		
		// 수정할 id 에 대한 엔티티 조회
		Member member = memberRepository.findById(Long.parseLong(params.get("id")))
							.orElseThrow(() -> new NoSuchElementException("존재하지 않는 회원입니다!"));
		System.out.println("Member : " + member);
		
		// UPDATE 수행을 위해서는 Repository 가 아닌 엔티티 클래스의 메서드 호출을 통해 엔티티 값을 변경해야함
		// => changeName() 등의 메서드를 정의하여 엔티티 값 변경 시 JPA 더티체킹에 의해 변경된 값을 감지하면 자동으로 DB UPDATE 수행됨
		member.changeName(params.get("name"));
	}
	
	

}















