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
	
	// Camel-case -> Snake-case 형태로 변환하는 메서드 정의
	public static String camelCaseToSnakeCase(String str) {
		// itemDetail -> item_detail
		// 소문자 뒤에 대문자가 올 경우 해당 문자 사이에 언더스코어(_)를 끼워넣고 모든 문자열을 소문자로 변환
		return str.replaceAll("([a-z])([A-Z]+)", "$1_$2").toLowerCase();
	}
	
	// 엔티티의 지정된 필드에 값을 동적으로 저장 => 리플렉션 활용
	// => 파라미터1) 엔티티 객체(어떤 엔티티가 올 지 알 수 없으므로 제네릭타입 활용)
	//    파라미터2) 외부로부터 전달받은 변경할 값에 해당하는 필드명
	//    파라미터3) 외부로부터 전달받은 변경할 값
	public static <T> void convertFieldValue(T entity, String fieldName, String value) {
		try {
			// 전달받은 필드명에 대응하는 엔티티 클래스의 필드를 Field 타입으로 리턴받기
			Field field = entity.getClass().getDeclaredField(fieldName);
			
			// private 으로 선언된 필드에 대해 접근 가능하도록 설정
			field.setAccessible(true);
			
			// --------------------------------------------------------------
			// 필드 타입에 따라 데이터타입을 변환하여 값을 저장
			Object convertedValue = null;
			
			if(value != null) {
				// 전달받은 필드명에 대응하는 필드의 데이터타입 확인
				Class<?> type = field.getType();
				
				if(type == String.class) { // String 타입일 경우 그대로 저장
					convertedValue = value;
				} else if(type == Integer.class) { // Integer 타입일 경우 String -> Integer 변환 후 저장
					convertedValue = Integer.parseInt(value);
				} else if(type == Long.class) { // Long 타입일 경우 String -> Long 변환 후 저장
					convertedValue = Long.parseLong(value);
				} else if(type == Boolean.class) { // Boolean 타입일 경우 String -> Boolean 변환 후 저장
					convertedValue = Boolean.parseBoolean(value);
				} else if(type.isEnum()) { // Enum 타입일 경우 String -> Enum 타입 값으로 변환
					// Enum.valueOf(Class<T> enumClass, String name)
					// => enumClass 지정을 위해 type 변수를 Class<Enum> 타입으로 변환하여 지정
					// => name 은 Enum 클래스에서 지정한 enum 상수이므로 value 값이 상수(ELECTRONICS, BOOK 등등)로 사용됨
					convertedValue = Enum.valueOf((Class<Enum>)type, value);
				} else {
					throw new IllegalArgumentException("지원되지 않는 데이터타입! - " + type.getName());
				}
			}
			
			// 변환된 값을 Field 객체에 저장 - Field 객체의 set() 메서드 활용
			// => 파라미터 : 엔티티, 저장할 값
			field.set(entity, convertedValue);
			
			// --------------------------------------------------------------
		} catch (NoSuchFieldException e) {
//			e.printStackTrace();
			throw new RuntimeException("존재하지 않는 필드 : " + fieldName, e);
		} catch (Exception e) {
//			e.printStackTrace();
			throw new RuntimeException("필드값 설정 실패! : " + e);
		}
	}
	
	
}























