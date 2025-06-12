package kr.co.itwillbs.oracle.order.entity;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import kr.co.itwillbs.oracle.member.entity.Member;

@Entity
@Table(name = "orders")
@EntityListeners(AuditingEntityListener.class)
public class Order {
	
	@Id @GeneratedValue
	private Long id;
	
	// ---------------------------
	// Order 와 Member 는 N:1 관계
	// Order 가 Member 의 member_id 컬럼을 참조하므로
	// 외래키(FK) 관리를 위해서 @JoinColumn 어노테이션을 사용하여 외래키 이름을 명확히 지정
	@ManyToOne(fetch = FetchType.LAZY) // 지연 로딩(연관된 엔티티가 실제로 사용될 때 조회 수행) - 성능 저하 예방
	@JoinColumn(name = "member_id") // 외래키 이름 지정
	private Member member;
	// ---------------------------
	// Order 와 OrderItem 은 1:N 관계
	@OneToMany(mappedBy = "order", cascade = CascadeType.ALL) // cascade 속성 설정(ALL 은 모든 cascade 옵션 다 사용)
	private List<OrderItem> orderItems;
	// ---------------------------
	
	@CreatedDate
	private LocalDateTime orderDate; // 주문일시
	
}










