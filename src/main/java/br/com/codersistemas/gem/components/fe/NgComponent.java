package br.com.codersistemas.gem.components.fe;

import br.com.codersistemas.gem.components.Replacememnt;
import br.com.codersistemas.gem.components.ResourceComponent;

public class NgComponent extends ResourceComponent {
	
	public NgComponent(Replacememnt replacement) {
		super(replacement);
	}

	@Override
	public String getResourceName() {
		return "usuario.component.ts";
	}

}
