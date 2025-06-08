package kr.co.itwillbs.oracle.commons.constant;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

import kr.co.itwillbs.oracle.commons.constant.*;

public class EnumUtils {
	public static <T extends Enum<T> & EnumType> Map<String, String> convertAllEnumToMap(Class<T> enumClass){
		Map<String, String> enumMap = Arrays.stream(enumClass.getEnumConstants())
												.collect(Collectors.toMap(T::getCode, T::getLabel));
		
		
		return enumMap;
	}
}
