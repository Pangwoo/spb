package kr.co.itwillbs.oracle.member.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import kr.co.itwillbs.oracle.order.entity.Order;

@Entity
@Table(name = "members")
public class Member {
	
	// 인덱스를 관리하는 id 컬럼(사용자 로그인에 사용되는 아이디가 아님!)
	@Id @GeneratedValue
	private Long id;

	// 사용자 아이디를 PK 로 사용할 경우 변경의 어려움과 성능 이슈등의 문제가 발생할 수 있음
	// 따라서, 인덱스를 관리하는 id 컬럼은 별도로 생성하고 사용자 아이디 컬럼도 생성 
	@Column(nullable = false, unique = true, length = 16)
	private String userId; // 사용자 ID
	
	@Column(nullable = false, length = 16)
	private String passwd; // 패스워드
	
	@Column(nullable = false, length = 20)
	private String name; // 이름
	
	@Column(nullable = false)
	private String email; // 이메일주소
	
	// ----------------------------------------------------
	// 사용자(Member)는 주문(Order)과 1:N 관계
	// => 1:N 관계 표현을 위해서는 @OneToMany 어노테이션을 사용
	@OneToMany(mappedBy = "member") // @OneToMany 에서는 지연로딩(LAZY) 설정 불필요(기본전략으로 이미 LAZY 가 설정되어 있음)
	private List<Order> orders = new ArrayList<>();
	
}











