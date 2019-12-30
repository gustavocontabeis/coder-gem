package br.com.codersistemas.gem.components.fe;

import br.com.codersistemas.gem.components.Replacememnt;
import br.com.codersistemas.gem.components.ResourceComponent;

public class NgService extends ResourceComponent {

	public NgService(Replacememnt replacement) {
		super(replacement);
	}

	@Override
	public String getResourceName() {
		return "usuario.service.ts";
	}

}
