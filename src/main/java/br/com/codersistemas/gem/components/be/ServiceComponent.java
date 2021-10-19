package br.com.codersistemas.gem.components.be;

import br.com.codersistemas.gem.components.Replacememnt;
import br.com.codersistemas.gem.components.ResourceComponent;
import br.com.codersistemas.libs.dto.EntidadeDTO;

public class ServiceComponent extends ResourceComponent {

	private EntidadeDTO entidade;
	private String instancia;

	public ServiceComponent(EntidadeDTO entidade) {
		super(Replacememnt.builder().addClass(entidade.getClasse()).build());
		this.entidade = entidade;
	}

	@Override
	public String getResourceName() {
		return "UsuarioService.txt";
	}

	@Override
	protected String printDepois(String content) {
		
		content = declararPackage(content);
		content = declararRepositoryPadrao(content);
		content = declararRepositoriesDeFKs(content);
		content = declararMetodosDeFKs(content);
		
		return content; 
	}

	private String declararPackage(String content) {
		return content.replace("//[package]", entidade.getClasse().getPackage()+"").replace("domain", "service");
	}

	private String declararMetodosDeFKs(String content) {
		StringBuilder s2 = new StringBuilder();
		entidade.getAtributosFKs().forEach(i -> {
			String nomeMetodo = "findBy"+i.getNomeCapitalizado()+"Id";
			s2.append("\n");
			s2.append("	@Transactional(readOnly = true)\n");
			s2.append("	public Optional<List<"+entidade.getNomeCapitalizado()+">> "+nomeMetodo+"(Long id){\n");
			s2.append("		return "+instancia+"."+nomeMetodo+"(id);\n");
			s2.append("	}\n\r");
		});
		return content.replace("//[metodos]", s2);
	}

	private String declararRepositoriesDeFKs(String content) {
		StringBuilder sb = new StringBuilder();
		entidade.getAtributosFKs().stream().forEach(atributo -> {
			sb.append("\t@Autowired\n");
			sb.append("\tprivate "+atributo.getClasse().getSimpleName()+"Repository "+atributo.getNome()+"Repository;\n");
		});
		return content.replace("//[declaracoes]", sb.toString());
	}

	private String declararRepositoryPadrao(String content) {
		instancia = entidade.getNomeInstancia()+"Repository";
		String s = "\t@Autowired\n"
				+ "\tprivate " + entidade.getNomeCapitalizado()+"Repository "+entidade.getNomeInstancia()+"Repository;";
		return content.replace("//[declaracaoRepositoryPadrao]", s);
	}

}
