package br.com.codersistemas.gem.components.fe;

import java.util.List;

import br.com.codersistemas.gem.components.Replacememnt;
import br.com.codersistemas.gem.components.ResourceComponent;
import br.com.codersistemas.libs.dto.AtributoDTO;
import br.com.codersistemas.libs.dto.EntidadeDTO;

public class NgService extends ResourceComponent {

	private EntidadeDTO entidade;

	public NgService(EntidadeDTO entidade) {
		super(Replacememnt.builder().addClass(entidade.getClasse()).build());
		this.entidade = entidade;
	}

	@Override
	public String getResourceName() {
		return "usuario.service.ts";
	}
	
	@Override
	protected String printDepois(String content) {
		
		StringBuilder sb = new StringBuilder();
		List<AtributoDTO> atributos = entidade.getAtributos();
		for (AtributoDTO atributo : atributos) {
			if(atributo.isFk() && !atributo.isCollection()) {
				String str = "\r\n";
				str += ("  buscarPorUsuario(idUsuario: number) {\r\n");
				str += ("    console.log('buscar por idUsuario', idUsuario);\r\n");
				str += ("    return this.httpClient.get(this.apiUrl + '/usuario/' + idUsuario);\r\n");
				str += ("  }\r\n");
				sb.append(str.replace("Usuario", atributo.getNomeCapitalizado()).replace("usuario", atributo.getNome()));
			}
		}
		
		return content
				.replace("//[metodos]", sb.toString())
				.replace("[usu-HyphenCase]", entidade.getNomeHyphenCase());
	}

}
