package com.wbhz.flightsys.Factory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;


/**
 * 对象工程
 * @author Mr.t
 *
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public class ObjectFactory {
	private static Map<String , Object> objs = new HashMap<String , Object>();
	static{
		BufferedReader reader = null ;
		
		try {
			reader = new BufferedReader(new InputStreamReader(ObjectFactory.class.getClassLoader().getResourceAsStream("resource/object.properties")));
			String linedata = reader.readLine();
			
			while(null != linedata){
				String[] datas = linedata.split("=");
				String key = datas[0].trim();
				String className = datas[1].trim();
				//反射创建对象
				Class clazz = Class.forName(className);
				Object object = clazz.newInstance();
				objs.put(key, object);
				
				if (key.endsWith("Service")) {
					//获取类中所有的属性
					Field[] fields = clazz.getDeclaredFields();
					for (int i = 0; i < fields.length; i++) {
						Field field = fields[i];
						String attributeName  = field.getName();
						Class attributeClazz = field.getType();
						//拼凑set方法
						String setMethodName = "set"+attributeName.substring(0,1).toUpperCase()+attributeName.substring(1);
						Method method = clazz.getDeclaredMethod(setMethodName, attributeClazz);
						method.invoke(object, objs.get(attributeName));
					}
				}
				linedata = reader.readLine();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		
		
	}
	

	/**
	 * 找到key所对应的object
	 * @param key
	 * @return
	 */
	public static Object getObject(String key){
		return objs.get(key);
	} 

}
