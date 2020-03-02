package br.com.codersistemas.gem.components;

import org.jsoup.nodes.Document;

public abstract class XMLComponent extends Component {
	
	protected Document document;
	
	public XMLComponent(Replacememnt replacement) {
		super(replacement);
		this.replacement = replacement;
	}

}
