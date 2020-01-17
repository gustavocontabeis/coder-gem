package br.com.codersistemas.gem.components.fe;

import java.util.ArrayList;
import java.util.List;

import br.com.codersistemas.gem.components.Replacememnt;
import br.com.codersistemas.gem.components.ResourceComponent;
import br.com.codersistemas.libs.dto.AplicacaoDTO;
import br.com.codersistemas.libs.dto.AtributoDTO;
import br.com.codersistemas.libs.dto.EntidadeDTO;

public class NgComponent extends ResourceComponent {
	
	public NgComponent(AplicacaoDTO appDTO, EntidadeDTO entidadeDTO, Replacememnt replacement) {
		super(replacement);
		
		List<AtributoDTO> atributos = entidadeDTO.getAtributos();
		for (AtributoDTO atributo : atributos) {
			if(atributo.isEnum()) {
				System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
				System.out.println(atributo.getNomeLista()+": SelectItem[] = [];");
				System.out.println();
				System.out.println("this."+atributo.getNomeLista()+" = [");
				String[] enumaracao = atributo.getEnumaracao();
				System.out.println(String.format("  {label: 'Selecione', value: null},"));
				for (String option : enumaracao) {
					System.out.println(String.format("  {label: '%s', value: '%s'},", option, option));
				}
				System.out.println("];");
				System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
			} else if(atributo.isFk()) {
				System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
				System.out.println(atributo.getNomeLista()+": SelectItem[] = [];");
				System.out.println();
				System.out.println("this."+atributo.getNomeLista()+" = [");
				String[] enumaracao = atributo.getEnumaracao();
				System.out.println(String.format("  {label: 'Selecione', value: null},"));
				for (String option : enumaracao) {
					System.out.println(String.format("  {label: '%s', value: '%s'},", option, option));
				}
				System.out.println("];");
				System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
			}
		}
		
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
