package kr.co.itwillbs.thymeleaf.item;

import java.time.LocalDateTime;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class ItemDTO {
	private Long id; // 상품코드(컬럼명 : item_id)
	
	// Java Bean Validation 기능을 활용하여 입력값 검증 수행 => Validation 의존성(spring-boot-starter-validation) 추가 필요
	// => 다양한 어노테이션을 조합하여 검증 조건 설정. 컨트롤러에서 바인딩 시점에 체크 가능
	// => 기본 문법 : @XXX(message = "오류 발생 시 표시할 메세지") 
	//    (필요에 따라 value 속성을 통한 값 지정도 가능)
	// @NotEmpty : 문자열이 null 이거나 길이가 0인 문자열(널스트링)을 허용하지 않음
	// @NotBlank : 문자열이 null 이거나 길이가 0인 문자열(널스트링) 또는 공백만 포함한 문자열을 허용하지 않음
	// @NotNull : 객체값 null 을 허용하지 않음
	// @Positive : 0 제외 양수만 허용(x > 0)
	// @PositiveOrZero : 0 이상의 양수만 허용(x >= 0)
	// @Negative : 0 제외 음수만 허용(x < 0)
	// @NegativeOrZero : 0 이하의 음수만 허용(x <= 0)
	// @Min(value) : 최소값 제한(x >= value)
	// @Max(value) : 최대값 제한(x <= value)
	// ---------------------------------------------------------------------------------------------------------------------
	@NotBlank(message = "상품명은 필수 입력값입니다!")
	private String itemNm; // 상품명
	
	@NotEmpty(message = "상품상세설명은 필수 입력값입니다!")
	private String itemDetail; // 상품상세설명
	
	// int 타입보다 Integer 타입을 사용하면 사용자가 실수로 값을 입력하지 않았을 때 0 과 헷갈릴 일이 없다! 참조타입으로 기본값이 null 이기 때문
	// 숫자 데이터의 값 체크는 @NotEmpty 대신 @NotNull 을 통해 판별해야함
	@NotNull(message = "가격은 필수 입력값입니다!")
	@PositiveOrZero(message = "가격은 0원 이상 입력 가능합니다!")
	private Integer price; // 가격
	
	@NotNull(message = "수량은 필수 입력값입니다!")
	@Positive(message = "수량은 최소 1개 이상 입력 가능합니다!")
	@Max(value = 99999, message = "수량은 99999개를 초과할 수 없습니다!")
	private Integer stockQty; // 재고수량
	
	@NotNull(message = "상품카테고리 선택은 필수입니다!") // enum 타입 검증 시 널스트링("") 값 전달 시 null 값으로 취급
	private ItemCategory category; // 카테고리(enum)
	
	@NotNull(message = "상품판매상태 선택은 필수입니다!") // enum 타입 검증 시 널스트링("") 값 전달 시 null 값으로 취급
	private ItemSellStatus sellStatus; // 상품판매상태(enum)
	
	private LocalDateTime regTime; // 상품등록일시
	private LocalDateTime updateTime; // 상품최종수정일시
	
	// ItemCategory 와 ItemSellStatus enum 객체에 저장된 값들을 미리 배열에 저장해둘 수 있음
//	private ItemCategory[] categories = ItemCategory.values();
//	private ItemSellStatus[] sellStatuses = ItemSellStatus.values();
	// => 타임리프 템플릿 페이지에서 ${T(패키지명.enum타입명).values()} 형태로 직접 호출 가능
	
	// 파라미터 생성자 정의
	@Builder
	public ItemDTO(Long id, String itemNm, String itemDetail, Integer price,	Integer stockQty, ItemCategory category, ItemSellStatus sellStatus, 
			LocalDateTime regTime, LocalDateTime updateTime) {
		this.id = id;
		this.itemNm = itemNm;
		this.itemDetail = itemDetail;
		this.price = price;
		this.stockQty = stockQty;
		this.category = category;
		this.sellStatus = sellStatus;
		this.regTime = regTime;
		this.updateTime = updateTime;
	}
	
	// Entity 객체를 직접 외부로 전달하지 않고 DTO 객체를 통해 외부와 데이터를 주고 받기 위해서는
	// DTO <-> Entity 사이의 변환 메서드가 추가적으로 필요함
	// 1) Entity -> DTO 로 변환하는 fromEntity() 메서드 정의
	//    => 파라미터 : Entity 객체   리턴타입 : DTO 타입
	public static ItemDTO fromEntity(Item item) {
		return ItemDTO.builder()
				.id(item.getId())
				.itemNm(item.getItemNm())
				.itemDetail(item.getItemDetail())
				.price(item.getPrice())
				.stockQty(item.getStockQty())
				.category(item.getCategory())
				.sellStatus(item.getSellStatus())
				.regTime(item.getRegTime())
				.updateTime(item.getUpdateTime())
				.build();
	}
	
	// 2) DTO -> Entity 로 변환하는 toEntity() 메서드 정의
	//    => 파라미터 : 없음(현재 객체 DTO 사용)   리턴타입 : Entity 타입
	public Item toEntity() {
		return Item.builder()
				.itemNm(itemNm)
				.itemDetail(itemDetail)
				.price(price)
				.stockQty(stockQty)
				.category(category)
				.sellStatus(sellStatus)
				.build();
	}
	
}














