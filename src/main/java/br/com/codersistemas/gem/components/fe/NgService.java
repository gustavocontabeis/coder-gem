package br.com.codersistemas.gem.components.fe;

import br.com.codersistemas.gem.components.Component;
import br.com.codersistemas.gem.components.Replacememnt;

public class NgService extends Component {

	public NgService(Replacememnt replacement) {
		super(replacement);
	}

	@Override
	protected String getResourceName() {
		return "usuario.service.ts";
	}

}
