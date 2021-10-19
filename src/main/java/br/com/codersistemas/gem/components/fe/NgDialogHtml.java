package br.com.codersistemas.gem.components.fe;

import br.com.codersistemas.gem.components.Replacememnt;
import br.com.codersistemas.gem.components.ResourceComponent;
import lombok.Data;

@Data
public class NgDialogHtml extends ResourceComponent {

	private String headerText, exibirDialog;

	public NgDialogHtml(Class classe) {
		super(Replacememnt.builder().addClass(classe).build());
	}

	@Override
	protected void printAntes() {
		content = gerarCampos();
	}

	private String gerarCampos() {
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
