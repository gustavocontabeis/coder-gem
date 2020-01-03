package br.com.codersistemas.gem.components.fe;

import br.com.codersistemas.gem.components.Replacememnt;
import br.com.codersistemas.gem.components.ResourceComponent;
import lombok.Data;

@Data
public class NgDialogHtml extends ResourceComponent {
	
	private String headerText, exibirDialog;
	private Object obj;

	public NgDialogHtml(Object obj, Replacememnt replacement) {
		super(replacement);
		this.obj = obj;
	}
	
	@Override
	protected void printAntes() {
		content = gerarCampos(obj);
	}

	private String gerarCampos(Object obj) {
		String content = this.content
				.replace("#{headerText}", headerText)
				.replace("#{exibirDialog}", exibirDialog);
		return content;
	}

	@Override
	public String getResourceName() {
		return "dialog-component.html";
	}

}
