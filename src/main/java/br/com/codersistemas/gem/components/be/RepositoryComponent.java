package br.com.codersistemas.gem.components.be;

import br.com.codersistemas.gem.components.Replacememnt;
import br.com.codersistemas.gem.components.ResourceComponent;
import br.com.codersistemas.libs.dto.EntidadeDTO;

public class RepositoryComponent extends ResourceComponent {

	private EntidadeDTO entidade;

	public RepositoryComponent(EntidadeDTO entidade) {
		super(Replacememnt.builder().addClass(entidade.getClasse()).build());
		this.entidade = entidade;
	}

	@Override
	public String getResourceName() {
		return "UsuarioRepository.txt";
	}

	@Override
	protected String printDepois(String content) {
		StringBuilder sb = new StringBuilder();
		entidade.getAtributos()
		.stream()
		.filter(atributo -> atributo.isFk() && !atributo.isEnum() && !atributo.isCollection())
		.forEach(i -> {
			sb.append("	Optional<List<"+entidade.getNomeCapitalizado()+">> findBy"+i.getNomeCapitalizado()+"Id(Long id);\n\r".
					replace("", ""));
		});
		return content.replace("//[metodos]", sb);
	}
	
}
