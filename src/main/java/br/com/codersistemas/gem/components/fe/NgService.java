package br.com.codersistemas.gem.components.fe;

import br.com.codersistemas.gem.components.Replacememnt;
import br.com.codersistemas.gem.components.ResourceComponent;
import br.com.codersistemas.libs.dto.EntidadeDTO;

public class NgService extends ResourceComponent {

	public NgService(EntidadeDTO entidade) {
		super(Replacememnt.builder().addClass(entidade.getClasse()).build());
	}

	@Override
	public String getResourceName() {
		return "usuario.service.ts";
	}

}
