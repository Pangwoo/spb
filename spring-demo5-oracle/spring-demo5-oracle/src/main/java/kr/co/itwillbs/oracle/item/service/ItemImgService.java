package kr.co.itwillbs.oracle.item.service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import kr.co.itwillbs.oracle.item.entity.Item;
import kr.co.itwillbs.oracle.item.entity.ItemImg;
import kr.co.itwillbs.oracle.item.repository.ItemImgRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ItemImgService {
	private final ItemImgRepository itemImgRepository;
	
	// 파일 업로드 처리에 사용할 경로를 properties 파일에서 가져오기
	// => 변수 선언부 앞(위)에 @Value("${프로퍼티 속성명}") 형태로 선언
	@Value("${uploadBaseLocation}")
	private String uploadBaseLocation;
	
	@Value("${itemImgLocation}")
	private String itemImgLocation;
	
//	public ItemImgService(ItemImgRepository itemImgRepository) {
//		this.itemImgRepository = itemImgRepository;
//	}
	// => @RequiredArgsConstructor 어노테이션으로 처리 가능
	// ----------------------------------------------
	// 상품 이미지 등록
	public void registItemImg(Item item, List<MultipartFile> itemImgFiles) throws Exception {
		// ItemImg 엔티티 목록을 저장할 List 객체 생성
		List<ItemImg> itemImgList = new ArrayList<>();
		
		int index = 0; // 이미지파일 접근 횟수를 카운팅 할 변수(대표이미지 설정에 활용)
		
		// 파일이 저장된 List 객체 반복
		for(MultipartFile file : itemImgFiles) {
			// 업로드 되는 원본 파일명 추출
			String originalFileName = file.getOriginalFilename();
			System.out.println("originalFileName : " + originalFileName);
			
			// 파일 이름 중복방지 대책
			// => 현재 시스템의 시각 정보를 Long 타입으로 환산한 값과 파일명 결합
			// => 또는 UUID.randomUUID() 활용한 UUID 값 사용 등등...
			String fileName = System.currentTimeMillis() + "_" + originalFileName;
			System.out.println("fileName : " + fileName);
			
			// 파일을 저장할 경로 생성하고 해당 경로를 실제 위치에 생성
			Path uploadDir = Paths.get(uploadBaseLocation, itemImgLocation).normalize();
			// Path 객체에 해당하는 디렉토리가 실제로 존재하지 않을 경우 디렉토리 생성
			if(!Files.exists(uploadDir)) {
				Files.createDirectories(uploadDir); // 하위경로를 포함한 디렉토리 생성
			}
			
			// 디렉토리와 파일명을 결합하여 새로운 Path 객체 생성
			// => 파라미터 : 경로를 관리하는 Path 객체를 문자열로 변환, 중복방지대책이 설정된 파일명 
			Path uploadPath = Paths.get(uploadDir.toString(), fileName);
			System.out.println("업로드 경로 : " + uploadPath.toString());
			
			// 파일을 실제 경로에 업로드
			file.transferTo(uploadPath);
			// ----------------------------------------------------------
			// ItemImg 엔티티 생성 및 파일 관련 정보 저장
			ItemImg itemImg = new ItemImg();
			itemImg.setItem(item); // Item 엔티티를 ItemImg 엔티티 객체에 저장
			itemImg.setItemImgFileInfo(fileName, originalFileName, itemImgLocation); // 이미지파일명, 원본파일명, 저장위치
			
			// 대표이미지 여부는 첫번째 이미지를 대표이미지로 설정하고 나머지는 대표이미지가 아님
			if(index == 0) {
				itemImg.setRepImgYn("Y");
			} else {
				itemImg.setRepImgYn("N");
			}
			
			// 생성된 ItemImg 엔티티를 리스트에 추가
			itemImgList.add(itemImg);
			
			// 인덱스값 1 증가
			index++;
			
		} // List 객체 반복 끝
		
		// ItemImgRepository - saveAll() 메서드 호출하여 상품 이미지 목록 한꺼번에 등록(INSERT)
		itemImgRepository.saveAll(itemImgList);
		
	}

}











