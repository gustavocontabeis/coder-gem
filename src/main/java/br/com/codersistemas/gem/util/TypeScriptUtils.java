package br.com.codersistemas.gem.util;

import java.util.HashMap;
import java.util.Map;

public class TypeScriptUtils {
	
	private static Map<Class, String> map = new HashMap<>();
	
	static {
		map.put(java.lang.Boolean.class, "boolean");
		map.put(java.lang.Double.class, "number");
		map.put(java.lang.Enum.class, "string");
		map.put(java.lang.Float.class, "number");
		map.put(java.lang.Integer.class, "number");
		map.put(java.lang.Long.class, "number");
		map.put(java.lang.Number.class, "number");
		map.put(java.lang.Short.class, "number");
		map.put(java.lang.String.class, "string");
		map.put(java.util.Date.class, "string");
		map.put(java.util.ArrayList.class, "any[]");
		map.put(java.util.Enumeration.class, "string");
		map.put(java.util.HashMap.class, "any[]");
		map.put(java.util.HashSet.class, "any[]");
		map.put(java.util.List.class, "any[]");
		map.put(java.util.Set.class, "any[]");
		map.put(java.util.Map.class, "any[]");
		map.put(java.time.LocalDate.class, "string");
		map.put(java.time.LocalDateTime.class, "string");
	}

	
	public static String toTypeScript(Class<?> type) {
		if(map.containsKey(type)) 
			return map.get(type);
		if(type.isEnum()) 
			return "string";
		if(!type.getName().startsWith("java")) 
			return type.getSimpleName();
		return null;
	}

}
