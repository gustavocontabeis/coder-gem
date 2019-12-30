package br.com.codersistemas.gem.components.be;

import br.com.codersistemas.gem.components.Replacememnt;
import br.com.codersistemas.gem.components.ResourceComponent;
import br.com.codersistemas.libs.utils.mock.Pessoa;

public class RespositoryComponent extends ResourceComponent {

	private Replacememnt replacement;

	public RespositoryComponent(Replacememnt replacement) {
		super(replacement);
		this.replacement = replacement;
	}

	@Override
	public String getResourceName() {
		return "UsuarioRepository.txt";
	}

}
