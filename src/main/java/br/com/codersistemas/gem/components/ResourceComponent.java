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

public abstract class ResourceComponent implements IComponent, IResourceComponent {
	
	protected List<IComponent> components;
	protected Replacememnt replacement;
	protected String content;
	
	public ResourceComponent(Replacememnt replacement) {
		super();
		this.replacement = replacement;
		generateContent();
	}

	protected void generateContent() {
		content = getResourceAsString(getResourceName());
	}

	protected String getResourceAsString(String resourceName) {		
		if(resourceName != null) {
			StringBuilder sb = new StringBuilder();
			try {
				List<String> readAllLines = Files.readAllLines(Paths.get(this.getClass().getResource(resourceName).toURI()), Charset.defaultCharset());
				for (String line : readAllLines) {
					if(replacement != null) {
						Map<String, String> map = replacement.getReplaces();
						Set<Entry<String, String>> entrySet = map.entrySet();
						for (Entry<String, String> entry : entrySet) 
							line = line.replaceAll(entry.getKey(), entry.getValue());
					}
					sb.append(line+"\n");
				}
			} catch (IOException e) {
				e.printStackTrace();
			} catch (URISyntaxException e) {
				e.printStackTrace();
			}
			return sb.toString();
		} else {
			return null;
		}
	}
	
	@Override
	public String print() {
		printAntes();
		return printDepois(content);
	}
	
	protected void printAntes() {
		
	}

	protected String printDepois(String content) {
		return content;
	}

}
