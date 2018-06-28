package com.taotao.utils;

import java.nio.MappedByteBuffer;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtils {
	//定义jackson对象
	private static final ObjectMapper MAPPER=new ObjectMapper();
	
	/**
	 * 将对象转化为json字符串
	 */
	public static String objectToJson(Object data){
		String jsonString;
		try {
			jsonString = MAPPER.writeValueAsString(data);
			return jsonString;
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 将json串转换成对象
	 */
	public static<T> T jsonToPojo(String jsonData,Class<T> beanType){
		try{
			T t=MAPPER.readValue(jsonData,beanType);
			return t;
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 将json串转为List,List是java对象
	 * 
	 */
	 public static <T>List<T> jsonToList(String jsonData, Class<T> beanType) {
	    	JavaType javaType = MAPPER.getTypeFactory().constructParametricType(List.class, beanType);
	    	try {
	    		List<T> list = MAPPER.readValue(jsonData, javaType);
	    		return list;
			} catch (Exception e) {
				e.printStackTrace();
			}
	    	
	    	return null;
	    }
}
