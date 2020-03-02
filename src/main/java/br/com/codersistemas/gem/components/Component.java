package br.com.codersistemas.gem.components;

import java.util.List;

import org.jsoup.nodes.Document;

public abstract class Component implements IComponent {
	
	protected List<IComponent> components;
	protected Replacememnt replacement;
	protected String content;
	
	public Component(Replacememnt replacement) {
		super();
		this.replacement = replacement;
		content = content(content);
	}

	protected String content(String content) {
		return content;
	}

	protected Document document(Document document) {
		return document;
	}

	@Override
	public String print() {
		return content;
	}
	
}
