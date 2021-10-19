package br.com.codersistemas.libs.utils.mock.dto;

import java.util.ArrayList;
import java.util.List;

public abstract class Component {
	
	protected List<Component> components;
	
	protected String content;
	
	public void add(Component child) {
		if(components == null)
			components = new ArrayList<>();
		components.add(child);
	}
	
	protected abstract String print();
}
