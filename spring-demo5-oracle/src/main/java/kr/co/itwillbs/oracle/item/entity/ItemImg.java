package kr.co.itwillbs.oracle.item.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "item_img")
@Getter
@Setter
public class ItemImg {
	@Id
	@Column(name = "item_img_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String imgName; // 이미지 파일명
	private String originalImgName; // 원본 이미지 파일명
	private String imgLocation; // 이미지 파일 위치
	private String repImgYn; // 대표이미지 설정 여부(Y/N)
	
	// 1개 상품(Item)에 복수개의 이미지(ItemImg)가 연결되므로 1:n 관계(ItemImg 기준 n:1) 표현
	@ManyToOne
	@JoinColumn(name = "item_id") // 외래키(FK) 이름
	private Item item;
	
	// 상품 파일명 관련 정보를 별도로 저장할 메서드
	public void setItemImgFileInfo(String imgName, String originalImgName, String imgLocation) {
		this.imgName = imgName;
		this.originalImgName = originalImgName;
		this.imgLocation = imgLocation;
	}
	
}

















