package br.com.codersistemas.gem.components.fe.prime;

import java.util.ArrayList;
import java.util.List;

import br.com.codersistemas.libs.utils.StringUtil;

public abstract class Component {
	
	protected String text;
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
	
	Component  addText(String text){
		this.text = text;
		return this;
	}

	String print() {
		
		StringBuilder sb = new StringBuilder();
		
		sb.append("<"+getNome() + printAtributos() +">");
		if(StringUtil.isNotBlank(text))
			sb.append(this.text);
		sb.append(printComponents());
		sb.append("</"+getNome()+">");
		
		return sb.toString();
	}
	
	protected Object printComponents() {
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