package br.com.codersistemas.gem.components;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.parser.Parser;

public abstract class ResourceXMLComponent extends ResourceComponent {
	
	protected Document document;
	
	public ResourceXMLComponent(Replacememnt replacement) {
		super(replacement);
		this.replacement = replacement;
		generateDocument();
	}

	protected void generateDocument() {
		document = getResourceAsDocument(getResourceName());
	}

	protected Document getResourceAsDocument(String resourceName) {
		String resourceAsString = getResourceAsString(resourceName);
		return Jsoup.parse(resourceAsString, "", Parser.xmlParser());
	}
	
}
