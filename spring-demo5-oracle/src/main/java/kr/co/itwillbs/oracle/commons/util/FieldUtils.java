package kr.co.itwillbs.oracle.commons.util;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class FieldUtils {
	/*
	 * JVM은 클래스 정보를 클래스 로더를 통해 읽어와서 해당 정보를 JVM 메모리에 저장한다. 
	 * 그렇게 저장된 클래스에 대한 정보가 마치 거울에 투영된 모습과 닮아있어, 리플렉션(Reflection)이라는 이름을 가지게 되었다. 
	 * 리플렉션을 사용하면 생성자, 메소드, 필드 등 클래스에 대한 정보를 아주 자세히 알아낼 수 있다.
	 * 대표적으로 여러 라이브러리, 프레임워크에서 사용되는 어노테이션이 리플렉션을 사용한 예시이다. 
	 * 리플렉션을 사용하면 클래스와 메소드에 어떤 어노테이션이 붙어 있는지 확인할 수 있다. 
	 */
	
	// 리플렉션을 활용하여 각 클래스에 존재하는 필드(멤버변수) 목록을 리스트 형태로 리턴하는 메서드 정의
	public static List<String> getFieldNames(Class<?> clazz) {
		// 각 클래스의 필드 목록을 리플렉션을 통해 가져와서 스트림으로 변환하여 Field[] 배열 형태로 변환되면
		// 각 Field 객체의 getName() 메서드를 통해 필드명을 추출하고
		// 이를 다시 List 형태로 수집하여 리턴
		return Arrays.stream(clazz.getDeclaredFields()) // 해당 클래스의 필드 목록 가져와서 스트림으로 변환
					.map(Field::getName) // 스트림 내의 각 필드들의 이름을 추출
					.collect(Collectors.toList()); // 추출된 이름들을 List 형태로 변환
	}
	
	
}













