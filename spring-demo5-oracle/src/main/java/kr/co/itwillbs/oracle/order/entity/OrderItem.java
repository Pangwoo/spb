package kr.co.itwillbs.oracle.order.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import kr.co.itwillbs.oracle.item.entity.Item;

@Entity
@Table(name = "order_items")
public class OrderItem {
	
	@Id @GeneratedValue
	private Long id;
	
	// ------------------------------------------------------
	// 하나의 주문(Order)에 여러개의 주문상품(OrderItem)이 존재
	@ManyToOne(fetch = FetchType.LAZY) // 지연로딩
	@JoinColumn(name = "order_id") // 외래키 설정
	private Order order;
	
	// 하나의 상품(Item)에 여러개의 주문상품(OrderItem)이 존재
	@ManyToOne(fetch = FetchType.LAZY) // 지연로딩
	@JoinColumn(name = "item_id") // 외래키 설정
	private Item item;
	// ------------------------------------------------------
	
	// 주문상품 수량
	private int orderQty;
	
	// 주문상품 가격(차후에 상품 가격이 변동되더라도 변동된 가격과 상관없이 실제 주문 시점의 상품 가격 - 실제 상품의 가격만 참조하면 안된다!)
	private int orderPrice;
}



















