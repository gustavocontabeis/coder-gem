package br.com.codersistemas.gem.components.fe;

import java.util.ArrayList;
import java.util.List;

import br.com.codersistemas.gem.components.Replacememnt;
import br.com.codersistemas.gem.components.ResourceComponent;
import br.com.codersistemas.libs.dto.AplicacaoDTO;
import br.com.codersistemas.libs.dto.AtributoDTO;
import br.com.codersistemas.libs.dto.EntidadeDTO;
import br.com.codersistemas.libs.utils.StringUtil;

public class NgComponent extends ResourceComponent {
	
	private StringBuilder selectItemDeclaracoes = new StringBuilder();
	private StringBuilder selectItemOnInit = new StringBuilder();
	private StringBuilder construtor = new StringBuilder(", ");
	
	public NgComponent(EntidadeDTO entidadeDTO) {
		super(Replacememnt.builder().addClass(entidadeDTO.getClasse()).build());
		
		List<AtributoDTO> atributos = entidadeDTO.getAtributos();
		for (AtributoDTO atributo : atributos) {
			if(atributo.isEnum()) {
				selectItemDeclaracoes.append(atributo.getNomeLista()+": SelectItem[] = [];\n");
				String[] enumaracao = atributo.getEnumaracao();
				selectItemOnInit.append("this."+atributo.getNomeLista()+" = [");
				selectItemOnInit.append(String.format("  {label: 'Selecione', value: null},"));
				for (String option : enumaracao) {
					selectItemOnInit.append(String.format("  {label: '%s', value: '%s'},", option, option));
				}
				selectItemOnInit.append("];");
			} else if(atributo.isFk()) {
				selectItemDeclaracoes.append(atributo.getNomeLista()+": SelectItem[] = [];\n");
				selectItemOnInit.append("this."+atributo.getNomeLista()+" = [");
				selectItemOnInit.append("];\n");
			}
		}
		
		atributos.stream().filter(i->i.isFk()).forEach(i->{
			construtor.append("private "+i.getNome()+"Service: "+i.getNomeCapitalizado()+"Service, ");
		});
	}
	
	@Override
	protected String printDepois(String content) {
		return content
				.replace("//[declaracoes]", selectItemDeclaracoes.toString())
				.replace("//[ngOnInit]", selectItemOnInit.toString())
				.replace("//[construtor]", StringUtil.removeEnd(construtor.toString(), ", "));
	}
	
	@Override
	protected void printAntes() {
		String[] split = content.split("\n");
		List<String> importacoes = new ArrayList<>();
		List<String> declaracoes = new ArrayList<>();
		List<String> contrutor = new ArrayList<>();
		List<String> metodos = new ArrayList<>();
		for (String string : split) {
			
		}
	}

	@Override
	public String getResourceName() {
		return "usuario.component.ts";
	}

}
