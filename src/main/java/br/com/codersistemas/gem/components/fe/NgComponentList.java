package br.com.codersistemas.gem.components.fe;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import br.com.codersistemas.gem.components.Replacememnt;
import br.com.codersistemas.gem.components.ResourceComponent;
import br.com.codersistemas.libs.dto.AtributoDTO;
import br.com.codersistemas.libs.dto.EntidadeDTO;
import br.com.codersistemas.libs.utils.StringUtil;

public class NgComponentList extends ResourceComponent {
	
	private StringBuilder ngOnInit, ngConsultarPaginadoFKs;
	private StringBuilder construtor = new StringBuilder(", ");
	private StringBuilder fks2 = new StringBuilder();
	private StringBuilder buscaPorParametros = new StringBuilder();
	private EntidadeDTO entidadeDTO;
	
	public NgComponentList(EntidadeDTO entidadeDTO) {
		super(Replacememnt.builder().addClass(entidadeDTO.getClasse()).build());
		this.entidadeDTO = entidadeDTO;
		List<AtributoDTO> atributos = entidadeDTO.getAtributos();
		montarNgOnInit(entidadeDTO, atributos);
		montarConsultarPaginadoFKs(entidadeDTO, atributos);
		montarBuscaPorParametros(entidadeDTO, atributos);
		montarMetodoBuscaPorParametros(entidadeDTO, atributos);
	}

	private void montarConsultarPaginadoFKs(EntidadeDTO entidadeDTO, List<AtributoDTO> atributos) {
		ngConsultarPaginadoFKs = new StringBuilder();
		entidadeDTO.getAtributosFKs().forEach(i->{
			String code = "    if(this.usuario.xxx.id){\r\n"
					+ "      let it = new Item();\r\n"
					+ "      it.field = 'xxx.id';\r\n"
					+ "      it.matchMode = 'equals';\r\n"
					+ "      it.value = String(this.usuario.xxx.id);\r\n"
					+ "      this.filters.push(it);\r\n"
					+ "    }\r\n"
					+ "";
			ngConsultarPaginadoFKs.append(code.replace("xxx", i.getNomeInstancia()));
		});
	}

	private void montarNgOnInit(EntidadeDTO entidadeDTO, List<AtributoDTO> atributos) {
		ngOnInit = new StringBuilder();
		ngOnInit.append("    this.usuario = new Usuario();\n\t");
	}

	private void montarBuscaPorParametros(EntidadeDTO entidadeDTO, List<AtributoDTO> atributos) {
		if(atributos.stream().filter(i->i.isFk()&& !i.isCollection()).collect(Collectors.counting()) > 0){
			buscaPorParametros = new StringBuilder("    this.activatedRoute.params.subscribe(params => {\r\n");
			
			String str = "";
			str += ("      if (params.id) {\r\n");
			str += ("        const id = params.id ? Number(params.id) : null;\r\n");
			str += ("        this.usuario.id = Number(id);\r\n");
			str += ("      }\r\n");
			buscaPorParametros.append(str);
			
			atributos.stream().filter(i->i.isFk()&& !i.isCollection()).forEach(i->{
				String str2 = "";
				str2 += ("      if (params.id_"+ StringUtil.uncapitalize(i.getNome())+") {\r\n");
				str2 += ("        const id"+i.getNomeCapitalizado()+" = params.id_"+StringUtil.uncapitalize(i.getNome())+" ? Number(params.id_"+StringUtil.uncapitalize(i.getNome())+") : null;\r\n");
				str2 += ("        this.usuario."+i.getNome()+".id = Number(id"+i.getNomeCapitalizado()+");\r\n");
				str2 += ("      }\r\n");
				buscaPorParametros.append(str2);
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
				sb.append("    this."+entidadeDTO.getNomeInstancia()+"Service.buscarPor"+atributo.getNomeCapitalizado()+"(id"+atributo.getNomeCapitalizado()+").subscribe(resposta => {\r\n");
				sb.append("      this."+StringUtil.uncaplitalizePlural(entidadeDTO.getNomeInstancia())+" = resposta as "+entidadeDTO.getNomeCapitalizado()+"[];\r\n");
				sb.append("    }, error => {\r\n");
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
				.replace("//[construtor]", StringUtil.removeEnd(construtor.toString(), ", "))
				.replace("//[ngOnInit]", ngOnInit.toString())
				.replace("//[consultarPaginadoFKs]", ngConsultarPaginadoFKs.toString())
				.replace("//[buscarFK2]", fks2.toString())
				.replace("//[buscarPorParametros]", buscaPorParametros.toString())
				.replace("[usu-HyphenCase]", entidadeDTO.getNomeHyphenCase())
				.replace("//[inicializarOjbeto]", montarInitObj());
	}
	
	private String montarInitObj() {
		StringBuilder sb = new StringBuilder();
		List<AtributoDTO> atributosFKs = entidadeDTO.getAtributosFKs();
		for (AtributoDTO atributoDTO : atributosFKs) {
			sb.append("    this.usuario."+atributoDTO.getNomeInstancia()+" = new "+atributoDTO.getClasse().getSimpleName()+"();\n");
		}
		return sb.toString();
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
		return "usuario-list.component.ts";
	}

}
