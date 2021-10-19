package br.com.codersistemas.gem.util;

import java.lang.reflect.Field;

public class PrintClasses {
    public static void main(String[] args) {
    	
    	Class classe = PrintClasses.class;
    	
		Field[] declaredFields = classe.getDeclaredFields();
		
		System.out.println("{");
		System.out.println("  {");
		System.out.printf("      'name:' '%s',\n", classe.getName());
		System.out.println("      'atributos:' [");
		for (Field field : declaredFields) {
			System.out.printf("         {'name:' '%s', 'type': '%s'},\n", field.getName(), field.getType());
		}
		System.out.println("      ]");
		System.out.println("}");
		
	}

}
