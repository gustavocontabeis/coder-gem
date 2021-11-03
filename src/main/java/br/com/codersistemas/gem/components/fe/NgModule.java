package br.com.codersistemas.gem.components.fe;

import br.com.codersistemas.gem.components.Replacememnt;
import br.com.codersistemas.gem.components.ResourceComponent;
import br.com.codersistemas.libs.dto.EntidadeDTO;

public class NgModule extends ResourceComponent {

	private EntidadeDTO entidade;

	public NgModule(EntidadeDTO entidade) {
		super(Replacememnt.builder().addClass(entidade.getClasse()).build());
		this.entidade = entidade;
	}

	@Override
	public String getResourceName() {
		return "usuario.module.ts";
	}
	
	@Override
	protected String printDepois(String content) {
		return content
				.replace("[usu-HyphenCase]", entidade.getNomeHyphenCase());
	}

}
