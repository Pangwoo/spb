package kr.co.itwillbs.oracle.item.dto;

import java.time.LocalDate;

import org.modelmapper.ModelMapper;

import kr.co.itwillbs.oracle.item.entity.ItemImg;

public class ItemImgDTO {
	private Long id;
	private String imgName; // 이미지 파일명
	private String originalImgName; // 원본 이미지 파일명
	private String imgLocation; // 이미지 파일 위치
	private String repImgYn; // 대표이미지 설정 여부(Y/N)
	
	// ----------------------------------------------------------
	// ItemImg <-> ItemImgDTO 간의 변환
	// => 직접 구현하지 않고 ModelMapper 객체 활용하여 간편하게 구현 가능
	//    (단, 두 클래스간의 필드명이 동일한 필드끼리만 자동으로 변환됨)
	// => ModelMapper 객체의 map() 메서드 활용
	private static ModelMapper modelMapper = new ModelMapper();
	
	// ItemImgDTO -> ItemImg 타입으로 변환하는 toEntity() 메서드 정의
	public ItemImg toEntity() {
		// 현재 객체가 ItemImg 객체이므로 첫번째 파라미터는 this, 변환할 대상 타입이 ItemImg 이므로 ItemImg.class 를 두번째 파라미터로 지정
		return modelMapper.map(this, ItemImg.class);
	}
	
	// ItemImg -> ItemImgDTO 타입으로 변환하는 of() 메서드 정의(fromEntity() 이름도 사용 가능)
	// => 단, ItemImg 객체만 존재하는 상태에서 ItemImgDTO 객체로 변환해야하므로 메서드 호출을 위해서는 static 메서드로 선언해야함
	// => 따라서, 메서드 내에서 참조하는 ModelMapper 객체도 static 타입으로 선언해야함 
	public static ItemImgDTO of(ItemImg itemImg) {
		// ItemImg 객체를 전달받아 변환하므로 첫번째 파라미터는 ItemImg 객체, 변환할 대상 타입이 ItemImgDTO 이므로 ItemImgDTO.class 를 두번째 파라미터로 지정
		return modelMapper.map(itemImg, ItemImgDTO.class);
	}
	
	
}














