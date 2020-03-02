package br.com.codersistemas.gem.components.fe;

import java.util.ArrayList;
import java.util.List;

import br.com.codersistemas.gem.components.Replacememnt;
import br.com.codersistemas.gem.components.ResourceComponent;
import br.com.codersistemas.libs.dto.AplicacaoDTO;
import br.com.codersistemas.libs.dto.AtributoDTO;
import br.com.codersistemas.libs.dto.EntidadeDTO;

public class NgComponent extends ResourceComponent {
	
	private StringBuilder declaracoes = new StringBuilder();
	private StringBuilder onInit = new StringBuilder();
	
	public NgComponent(EntidadeDTO entidadeDTO) {
		super(Replacememnt.builder().addClass(entidadeDTO.getClasse()).build());
		
		List<AtributoDTO> atributos = entidadeDTO.getAtributos();
		for (AtributoDTO atributo : atributos) {
			if(atributo.isEnum()) {
				//System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
				//System.out.println(atributo.getNomeLista()+": SelectItem[] = [];");
				declaracoes.append(atributo.getNomeLista()+": SelectItem[] = [];\n");
				//System.out.println();
				//System.out.println("this."+atributo.getNomeLista()+" = [");
				String[] enumaracao = atributo.getEnumaracao();
				//System.out.println(String.format("  {label: 'Selecione', value: null},"));
				//for (String option : enumaracao) {
				//	System.out.println(String.format("  {label: '%s', value: '%s'},", option, option));
				//}
				//System.out.println("];");
				
				onInit.append("this."+atributo.getNomeLista()+" = [");
				onInit.append(String.format("  {label: 'Selecione', value: null},"));
				for (String option : enumaracao) {
					onInit.append(String.format("  {label: '%s', value: '%s'},", option, option));
				}
				onInit.append("];");
				
//				System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
			} else if(atributo.isFk()) {
//				System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
//				System.out.println(atributo.getNomeLista()+": SelectItem[] = [];");
				declaracoes.append(atributo.getNomeLista()+": SelectItem[] = [];");
//				System.out.println();
//				System.out.println("this."+atributo.getNomeLista()+" = [");
//				System.out.println("];");
				onInit.append("this."+atributo.getNomeLista()+" = [");
				onInit.append("];");
//				System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
			}
		}
		
	}
	
	@Override
	protected String printDepois(String content) {
		return content.replace("//[declaracoes]", declaracoes.toString()).replace("//[ngOnInit]", onInit.toString());
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
