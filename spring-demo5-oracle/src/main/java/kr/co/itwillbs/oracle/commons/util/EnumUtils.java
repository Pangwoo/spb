package kr.co.itwillbs.oracle.commons.util;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

import kr.co.itwillbs.oracle.commons.constant.EnumType;
import kr.co.itwillbs.oracle.commons.constant.ItemCategory;

public class EnumUtils {

//	public static Map<String, String> convertAllEnumToMap() {
//		Map<String, String> itemCategoryMap = Arrays.stream(ItemCategory.values())
//				.collect(Collectors.toMap(
//						ItemCategory::getCode, // 맵의 키(key) = 카테고리 enum 상수(문자열)
//						ItemCategory::getLabel)); // 맵의 값(value) = 카테고리 enum 상수의 레이블
//		return itemCategoryMap;
//	}
	// => 위의 메서드의 단점은 ItemCategory 클래스에 대한 값들만 처리됨
	//    이 때, ItemCategory 클래스를 명시하지 않고 관련된 다른 클래스(enum 포함)까지 모두 처리할 수 있도록 제네릭타입과 Class 클래스 활용 가능
	// --------------------------------------------------------------------------------------------------------------
	// 메서드 정의 시 파라미터로 enum 타입을 받아올 때 enum 타입을 직접 명시하지 않고 복수개의 enum 타입을 공통으로 처리할 수 있도록
	// 파라미터타입을 Class<T> 타입으로 명시하고 제네릭 T 타입을 메서드 한정 특정 타입으로 지정하기 위해
	// 리턴타입 앞에 제네릭타입 제약조건을 선언
	// <T extends Enum<T> & EnumType> : 제네릭타입 T 는 Enum 타입이면서 EnumType 부모를 상속한 클래스만 지정 가능하도록 제한 
	public static <T extends Enum<T> & EnumType> Map<String, String> convertAllEnumToMap(Class<T> enumClass) {
		Map<String, String> enumMap = Arrays.stream(enumClass.getEnumConstants()) // 해당 enum 클래스의 모든 상수를 T[] 배열로 리턴(ItemCategory.values() 와 동일)
													.collect(Collectors.toMap(
															T::getCode, // 맵의 키(key) = enum 클래스의 상수(문자열)
															T::getLabel)); // 맵의 값(value) = enum 클래스의 상수 레이블
		return enumMap;
	}
	
}













