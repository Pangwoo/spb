package kr.co.itwillbs.oracle.commons.constant;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class GlobalModelAttribute {
	// 파일 업로드 처리에 사용할 경로를 properties 파일에서 가져오기
	// => 변수 선언부 앞(위)에 @Value("${프로퍼티 속성명}") 형태로 선언
	@Value("${uploadBaseLocation}")
	private String uploadBaseLocation;
	
	// 모든 요청 처리 메서드(view 렌더링 시점)의 Model 에 자동으로 해당 값을 추가
	@ModelAttribute("uploadBaseLocation")
	public String uploadBaseLocation() {
		return uploadBaseLocation;
	}
}
