package br.com.codersistemas.gem.components.fe.prime;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

public abstract class Component {
	
	protected List<String> atributos = new ArrayList<>();
	protected List<Component> components = new ArrayList<>();
	
	public abstract String getNome();
	
	Component add(String atributo) {
		atributos.add(atributo);
		return this;
	}
	
	Component add(Component component) {
		components.add(component);
		return this;
	}
	
	String print() {
		
		StringBuilder sb = new StringBuilder();
		
		sb.append("<"+getNome() + printAtributos() +">");
		sb.append(printComponents());
		sb.append("</"+getNome()+">");
		
		return sb.toString();
	}
	
	private Object printComponents() {
		String components = "";
		for (Component component : this.components) 
			components += (component.print());
		return components;
	}

	private String printAtributos() {
		String atributos = "";
		for(String atributo : this.atributos) 
			atributos += " " + atributo;
		return atributos;
	}

}