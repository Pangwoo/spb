package kr.co.itwillbs.oracle.item.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import kr.co.itwillbs.oracle.commons.constant.ItemCategory;
import kr.co.itwillbs.oracle.commons.constant.ItemSellStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "items")
@NoArgsConstructor
@Getter
@Setter
@ToString
// 만약, @CreatedDate 나 @LastModifiedDate 등을 활용하여 JPA 감사(Auditing) 기능 사용 시
// 감사 기능이 동작하는 클래스(엔티티 또는 공통감사엔티티)에 @EntityListener 어노테이션 추가하여 감사 클래스를 지정하고
// 메인클래스(XXXApplication)나 스프링 설정 클래스에 @EnableJpaAuditing 어노테이션 추가 필수!
@EntityListeners(AuditingEntityListener.class)
public class Item {
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
	private String itemNm;
	
	@Column(nullable = false)
	private String itemDetail;
	
	private int price; // 상품 가격
	
	private int stockQty; // 상품 재고 수량
	
	// 엔티티를 통해 DB 에 enum 타입 데이터를 연결 시 String 타입으로 처리
	// => DB 의 enum 타입 사용 시 번호로 관리되는데 enum 상수 순서 변경 시 바뀌게 되므로 추천X
	@Enumerated(EnumType.STRING) // enum 타입 데이터를 String 으로 처리
	@Column(nullable = false)
	private ItemCategory category;
	
	@Enumerated(EnumType.STRING) // enum 타입 데이터를 String 으로 처리
	@Column(nullable = false)
	private ItemSellStatus sellStatus;
	
	// -----------------------------------------------------------------------------
	// JPA 에서 특정 날짜 및 시각(또는 다른 형식도 가능) 필드의 값을 자동으로 등록하려면
	// JPA 감사(Auditing) 기능을 활용하여 어노테이션 조합을 통해 자동 등록 필드로 설정
	// => 자동 등록될 필드가 위치한 클래스(엔티티)와 메인 클래스에 어노테이션 설정 추가 필요
	@CreatedDate // 엔티티 최초 저장 시점에 날짜 및 시각이 자동으로 등록되는 필드로 설정
	@Column(updatable = false) // 해당 필드는 수정 불가능한 필드로 설정
	private LocalDateTime regTime; // 상품등록일시
	
	@LastModifiedDate // 엔티티 생성 또는 변경 시점에 날짜 및 시각이 자동으로 갱신되는 필드로 설정
	private LocalDateTime updateTime; // 상품최종수정일시
	// -----------------------------------------------------------------------------
	// 주문상품과 1:N 관계 처리하는 필드 선언
	
	
	// -----------------------------------------------------------------------------
	// 상품(1) : 상품이미지(n)
	@OneToMany(mappedBy = "item")
	private List<ItemImg> itemImgs = new ArrayList<>();
	// -----------------------------------------------------------------------------
	
	// id 값을 제외한 필수 값에 대한 생성자 정의(날짜도 제외)
	@Builder
	public Item(String itemNm, String itemDetail, int price, int stockQty, ItemCategory category,
			ItemSellStatus sellStatus) {
		super();
		this.itemNm = itemNm;
		this.itemDetail = itemDetail;
		this.price = price;
		this.stockQty = stockQty;
		this.category = category;
		this.sellStatus = sellStatus;
	}
	
}









