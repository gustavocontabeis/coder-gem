package br.com.codersistemas.gem.components.fe;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import br.com.codersistemas.gem.components.Replacememnt;
import br.com.codersistemas.gem.components.ResourceComponent;
import br.com.codersistemas.libs.dto.AtributoDTO;
import br.com.codersistemas.libs.dto.EntidadeDTO;
import br.com.codersistemas.libs.utils.StringUtil;

public class NgComponentAdd extends ResourceComponent {
	
	private StringBuilder selectItemDeclaracoes = new StringBuilder();
	private StringBuilder selectItemOnInit = new StringBuilder();
	private StringBuilder construtor = new StringBuilder(", ");
	private StringBuilder fks = new StringBuilder();
	private StringBuilder fks2 = new StringBuilder();
	private StringBuilder buscaPorParametros = new StringBuilder();
	private EntidadeDTO entidadeDTO;
	
	public NgComponentAdd(EntidadeDTO entidadeDTO) {
		super(Replacememnt.builder().addClass(entidadeDTO.getClasse()).build());
		this.entidadeDTO = entidadeDTO;
		List<AtributoDTO> atributos = entidadeDTO.getAtributos();
		for (AtributoDTO atributo : atributos) {
			if(atributo.isEnum()) {
				selectItemDeclaracoes.append("  "+atributo.getNomeLista()+": SelectItem[] = [];\n");
				String[] enumaracao = atributo.getEnumaracao();
				selectItemOnInit.append("    this."+atributo.getNomeLista()+" = [");
				selectItemOnInit.append(String.format("{label: 'Selecione', value: null},\n"));
				for (String option : enumaracao) {
					selectItemOnInit.append(String.format("      {label: '%s', value: '%s'},\n", option, option));
				}
				selectItemOnInit.append("];");
			} else if(atributo.isFk()) {
				selectItemDeclaracoes.append("  "+atributo.getNomeLista()+": SelectItem[] = [];\n");
				selectItemOnInit.append("  this."+atributo.getNomeLista()+" = [");
				selectItemOnInit.append("];\n");
			}
		}
		montarConstrutor(atributos);
		montarFK(atributos);
		montarFK2(atributos);
		montarBuscaPorParametros(entidadeDTO, atributos);
		montarMetodoBuscaPorParametros(entidadeDTO, atributos);
	}

	private String montarInitObj() {
		StringBuilder sb = new StringBuilder();
		List<AtributoDTO> atributosFKs = entidadeDTO.getAtributosFKs();
		for (AtributoDTO atributoDTO : atributosFKs) {
			sb.append("    this.usuario."+atributoDTO.getNomeInstancia()+" = new "+atributoDTO.getClasse().getSimpleName()+"();\n");
		}
		return sb.toString();
	}

	private void montarConstrutor(List<AtributoDTO> atributos) {
		atributos.stream().filter(i->i.isFk()).forEach(i->{
			String nome = StringUtil.isNotBlank( i.getTipoClasseGenericaNome() ) ? i.getTipoClasseGenericaNome() : i.getClasse().getSimpleName();
			construtor.append("\n    private " + nome + "Service: " + nome + "Service, ");
		});
	}

	private void montarFK(List<AtributoDTO> atributos) {
		atributos.stream().filter(i->i.isFk()).forEach(i->{
			String nome = StringUtil.isNotBlank( i.getTipoClasseGenericaNome() ) ? i.getTipoClasseGenericaNome() : i.getClasse().getSimpleName();
			fks.append("    this.buscar"+nome+"();\n");
		});
	}

	private void montarFK2(List<AtributoDTO> atributos) {
		atributos.stream().filter(i->i.isFk()).forEach(i->{
			String nome = StringUtil.isNotBlank( i.getTipoClasseGenericaNome() ) ? i.getTipoClasseGenericaNome() : i.getClasse().getSimpleName();
			fks2.append("  buscar"+nome+"(){\n");
			fks2.append("    this."+nome+"Service.consultar().subscribe((resposta: any) => {\n");
			fks2.append("      const itens = resposta as "+nome+"[];\n");
			fks2.append("      itens.forEach(element => {\n");
			fks2.append("         this."+i.getNomeLista()+".push({label: element.id, value: element});\n");
			fks2.append("      });\n");
			fks2.append("      }, (error: any) => {\n");
			fks2.append("        console.log(error);\n");
			fks2.append("        alert(error.ok);\n");
			fks2.append("      }\n");
			fks2.append("    );\n");
			fks2.append("  }\n");
		});
	}

	private void montarBuscaPorParametros(EntidadeDTO entidadeDTO, List<AtributoDTO> atributos) {
		if(atributos.stream().filter(i->i.isFk()&& !i.isCollection()).collect(Collectors.counting()) > 0){
			buscaPorParametros = new StringBuilder("    this.activatedRoute.params.subscribe(params => {\r\n");
			
			atributos.stream().filter(i->i.isFk()&& !i.isCollection()).forEach(i->{
				String str = "";
				str += ("      if (params.id_"+ StringUtil.uncapitalize(i.getNome())+") {\r\n");
				str += ("        const id"+i.getNome()+" = params.id_"+StringUtil.uncapitalize(i.getNome())+" ? Number(params.id_"+StringUtil.uncapitalize(i.getNome())+") : null;\r\n");
				str += ("        this.buscar"+entidadeDTO.getNome()+"Por"+i.getNomeCapitalizado()+"(Number(id"+i.getNome()+"));\r\n");
				str += ("      } else {\r\n");
				str += ("        this.consultar();\r\n");
				str += ("      }\r\n");
				buscaPorParametros.append(str);
			});
			buscaPorParametros.append("    });\r\n");
			
		} else {
			buscaPorParametros = new StringBuilder();
			buscaPorParametros.append("    this.activatedRoute.params.subscribe(params => {\r\n");
			buscaPorParametros.append("      const id = params.id ? Number(params.id) : null;\r\n");
			buscaPorParametros.append("      console.log(id);\r\n");
			buscaPorParametros.append("      if (id != null) {\r\n");
			buscaPorParametros.append("      console.log('contem id: ' + id);\r\n");
			buscaPorParametros.append("        this.buscar(id);\r\n");
			buscaPorParametros.append("      }\r\n");
			buscaPorParametros.append("    });\r\n");
		}
	}
	
	private void montarMetodoBuscaPorParametros(EntidadeDTO entidadeDTO, List<AtributoDTO> atributos) {
		if(atributos.stream().filter(i->i.isFk()&& !i.isCollection()).collect(Collectors.counting()) > 0){
			atributos.stream().filter(atributo->atributo.isFk()&& !atributo.isCollection()).forEach(atributo->{
				StringBuilder sb = new StringBuilder();
				sb.append("\r\n");
				sb.append("  buscar"+entidadeDTO.getNome()+"Por"+atributo.getNomeCapitalizado()+"(id"+atributo.getNomeCapitalizado()+": number) {\r\n");
				sb.append("    this."+entidadeDTO.getNomeInstancia()+"Service.buscarPor"+atributo.getNomeCapitalizado()+"(id"+atributo.getNomeCapitalizado()+").subscribe((resposta: any) => {\r\n");
				sb.append("      this."+StringUtil.uncaplitalizePlural(entidadeDTO.getNomeInstancia())+" = resposta as "+entidadeDTO.getNomeCapitalizado()+"[];\r\n");
				sb.append("    }, (error: any) => {\r\n");
				sb.append("      console.log(error);\r\n");
				sb.append("      alert('erro "+atributo.getNomeCapitalizado()+".' + error);\r\n");
				sb.append("    });\r\n");
				sb.append("  }\r\n");
				fks2.append(sb.toString());
			});
		}
	}
	
	@Override
	protected String printDepois(String content) {
		return content
				.replace("//[declaracoes]", selectItemDeclaracoes.toString())
				.replace("//[ngOnInit]", selectItemOnInit.toString())
				.replace("//[construtor]", StringUtil.removeEnd(construtor.toString(), ", "))
				.replace("//[buscarFK]", fks.toString())
				.replace("//[buscarFK2]", fks2.toString())
				.replace("//[buscarPorParametros]", buscaPorParametros.toString())
				.replace("[usu-HyphenCase]", entidadeDTO.getNomeHyphenCase())
				.replace("//[inicializarOjbeto]", montarInitObj());
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
		return "usuario-add.component.ts";
	}

}
