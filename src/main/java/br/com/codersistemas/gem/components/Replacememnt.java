package br.com.codersistemas.gem.components;

import java.util.HashMap;
import java.util.Map;

public class Replacememnt {
	
	static Replacememnt instance = new Replacememnt();
	
	private Map<String, String> map = new HashMap<>();
	
	private Replacememnt() {
		super();
	}

	public static Replacememnt builder() {
		return instance;
	}

	public Replacememnt replace(String string, String string2) {
		map.put(string, string2);
		return instance;
	}

	public Replacememnt build() {
		return instance;
	}

	public Map<String, String> getReplaces() {
		return map;
	}

}