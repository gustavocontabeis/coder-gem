package br.com.codersistemas.gem.components.be;

import br.com.codersistemas.gem.components.Replacememnt;
import br.com.codersistemas.gem.components.ResourceComponent;
import br.com.codersistemas.libs.dto.EntidadeDTO;

public class ControllerComponent extends ResourceComponent {

	public ControllerComponent(EntidadeDTO entidadeDTO) {
		super(Replacememnt.builder().addClass(entidadeDTO.getClasse()).build());
	}

	@Override
	public String getResourceName() {
		return "UsuarioController.txt";
	}
	
	@Override
	protected String printDepois(String content) {
		
		
		
		
		return super.printDepois(content);
	}

}
