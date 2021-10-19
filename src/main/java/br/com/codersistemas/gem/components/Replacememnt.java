package br.com.codersistemas.gem.components;

import java.util.LinkedHashMap;
import java.util.Map;

import br.com.codersistemas.libs.utils.StringUtil;

public class Replacememnt {
	
	static Replacememnt instance = new Replacememnt();
	
	private Map<String, String> map = new LinkedHashMap<>();
	
	private Replacememnt() {
		super();
	}

	public static Replacememnt builder() {
		return instance;
	}

	private Replacememnt replace(String string, String string2) {
		map.put(string, string2);
		return instance;
	}

	public Replacememnt build() {
		return instance;
	}

	public Map<String, String> getReplaces() {
		return map;
	}

	public Replacememnt addClass(Class class1) {
		
		replace("Usuarios", StringUtil.caplitalizePlural(class1.getSimpleName()));
		replace("usuarios", StringUtil.uncaplitalizePlural(class1.getSimpleName()));
		replace("Usuario", StringUtil.caplitalizeSingular(class1.getSimpleName()));
		replace("usuario", StringUtil.uncaplitalizeSingular(class1.getSimpleName()));
		
		return instance;
	}

}