package kr.co.itwillbs.oracle.commons.controller;

import org.springframework.beans.factory.annotation.Value;

public class GlobalModelAttribute {
	// 파일 업로드 처리에 사용할 경로를 properties 파일에서 가져오기
	// => 변수 선언부 앞(위)에 @Value("${프로퍼티 속성명}") 형태로 선언
	@Value("${uploadBaseLocation}")
	private String uploadBaseLocation;
}
