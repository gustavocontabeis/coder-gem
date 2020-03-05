package br.com.codersistemas.gem.components.be;

import br.com.codersistemas.gem.components.Replacememnt;
import br.com.codersistemas.gem.components.ResourceComponent;
import br.com.codersistemas.libs.dto.EntidadeDTO;

public class RespositoryComponent extends ResourceComponent {

	public RespositoryComponent(EntidadeDTO entidade) {
		super(Replacememnt.builder().addClass(entidade.getClasse()).build());
	}

	@Override
	public String getResourceName() {
		return "UsuarioRepository.txt";
	}

}
