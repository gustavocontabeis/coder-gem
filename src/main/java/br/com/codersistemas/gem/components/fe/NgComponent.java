package br.com.codersistemas.gem.components.fe;

import br.com.codersistemas.gem.components.Component;
import br.com.codersistemas.gem.components.Replacememnt;

public class NgComponent extends Component {
	
	public NgComponent(Object obj, Replacememnt replacement) {
		super(replacement);
	}

	@Override
	protected String getResourceName() {
		return "usuario.component.ts";
	}

}
