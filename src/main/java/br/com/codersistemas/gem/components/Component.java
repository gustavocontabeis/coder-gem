package br.com.codersistemas.gem.components;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.parser.Parser;

public abstract class Component implements IComponent {
	
	protected List<Component> components;
	protected Replacememnt replacement;
	protected String content;
	private Document document;
	
	public Component(Replacememnt replacement) {
		super();
		this.replacement = replacement;
		content = getResourceAsString(getResourceName());
		content = content(content);
		document = getResourceAsDocument(getResourceName());
		document = document(document);
	}

	protected abstract String getResourceName();

	protected String content(String content) {
		return content;
	}

	protected Document document(Document document) {
		return document;
	}

	protected String getResourceAsString(String resourceName) {
		StringBuilder sb = new StringBuilder();
		try {
			List<String> readAllLines = Files.readAllLines(Paths.get(this.getClass().getResource(resourceName).toURI()), Charset.defaultCharset());
			for (String line : readAllLines) {
				Map<String, String> map = replacement.getReplaces();
				Set<Entry<String, String>> entrySet = map.entrySet();
				for (Entry<String, String> entry : entrySet) 
					line = line.replaceAll(entry.getKey(), entry.getValue());
				sb.append(line+"\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		return sb.toString();
	}
	
	@Override
	public String print() {
		return content;
	}
	
	protected Document getResourceAsDocument(String resourceName) {
		String resourceAsString = getResourceAsString(resourceName);
		return Jsoup.parse(resourceAsString, "", Parser.xmlParser());
	}
	
}
