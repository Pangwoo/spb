package kr.co.itwillbs.thymeleaf.item;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
public class Item {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
	private String itenNm;
	
	@Column(nullable = false)
	private String itenDetail;
	
	private int price;
	private int stockQty;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private ItemCategory category;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private ItemSellStatus sellStatus;	
	
	private LocalDateTime regTime;
	private LocalDateTime updateTime;
	
	@Builder
	public Item(String itenNm, String itenDetail, int price, int stockQty, ItemCategory category,
			ItemSellStatus sellStatus) {
		super();
		this.itenNm = itenNm;
		this.itenDetail = itenDetail;
		this.price = price;
		this.stockQty = stockQty;
		this.category = category;
		this.sellStatus = sellStatus;
	}
	
	
}
