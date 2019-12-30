package br.com.codersistemas.gem.components.be;

import br.com.codersistemas.gem.components.Replacememnt;
import br.com.codersistemas.gem.components.ResourceComponent;

public class ControllerComponent extends ResourceComponent {

	private Replacememnt replacement;

	public ControllerComponent(Replacememnt replacement) {
		super(replacement);
		this.replacement = replacement;
	}

	@Override
	public String getResourceName() {
		return "UsuarioController.txt";
	}

}
