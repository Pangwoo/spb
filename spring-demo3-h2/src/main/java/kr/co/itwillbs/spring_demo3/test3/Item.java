package kr.co.itwillbs.spring_demo3.test3;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

// JPA 가 관리하는 엔티티 클래스 정의
@Entity
@Table(name = "item")
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Item {
	@Id
	@Column(name = "item_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id; // 상품 코드
	
	@Column(length = 50, nullable = false) // 길이 50자, NN 제약조건
	private String itemNm; // 상품명
	
	@Column(name = "price", nullable = false) // NN 제약조건
	private Integer price; // 가격
	
	@Column(nullable = false) // NN 제약조건
	private Integer stockQty; // 재고수량
	
	@Lob // 문자열 길이 255 를 초과할 경우 @Lob 어노테이션을 설정 시 자동으로 타입에 대한 설정이 추가됨(문자열 : clob, 바이너리 : blob)
	@Column(nullable = false) // 길이 50자, NN 제약조건
	private String itemDetail; // 상품상세설명
	
	private LocalDateTime regTime; // 상품등록일시

	// ------------------------------------------------------------------------
	// 파라미터 생성자 정의(PK 값 제외)
	@Builder // 생성자를 빌더 패턴을 통해 활용하도록 해주는 어노테이션(Lombok 에 포함됨)
	public Item(String itemNm, Integer price, Integer stockQty, String itemDetail, LocalDateTime regTime) {
		super();
		this.itemNm = itemNm;
		this.price = price;
		this.stockQty = stockQty;
		this.itemDetail = itemDetail;
		this.regTime = regTime;
	}
	
	
	
}
















