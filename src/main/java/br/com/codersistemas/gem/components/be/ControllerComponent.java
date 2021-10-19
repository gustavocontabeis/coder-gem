package br.com.codersistemas.gem.components.be;

import java.util.List;

import br.com.codersistemas.gem.components.Replacememnt;
import br.com.codersistemas.gem.components.ResourceComponent;
import br.com.codersistemas.libs.dto.AtributoDTO;
import br.com.codersistemas.libs.dto.EntidadeDTO;

public class ControllerComponent extends ResourceComponent {

	private EntidadeDTO entidadeDTO;

	public ControllerComponent(EntidadeDTO entidadeDTO) {
		super(Replacememnt.builder().addClass(entidadeDTO.getClasse()).build());
		this.entidadeDTO = entidadeDTO;
	}

	@Override
	public String getResourceName() {
		return "UsuarioController.txt";
	}
	
	@Override
	protected String printDepois(String content) {
		
		StringBuilder sb = new StringBuilder();
		List<AtributoDTO> atributos = entidadeDTO.getAtributos();
		for (AtributoDTO atributo : atributos) {
			if(atributo.isFk() && !atributo.isCollection()) {
				StringBuilder sbMethod = new StringBuilder();
				sbMethod.append("\r\n");
				sbMethod.append("	@GetMapping(\"/entidade/{id}\")\r\n");
				sbMethod.append("	public ResponseEntity<List<Atributo>> buscarPorEntidade(@PathVariable(\"id\") Long id) {\r\n");
				sbMethod.append("		Optional<List<Atributo>> findById = atributoService.findByEntidadeId(id);\r\n");
				sbMethod.append("		if(!findById.isPresent()) {\r\n");
				sbMethod.append("			return ResponseEntity.ok(Collections.EMPTY_LIST);\r\n");
				sbMethod.append("		}else {\r\n");
				sbMethod.append("			findById.get().forEach(obj -> {\r\n");
				sbMethod.append("				ReflectionUtils.mapToBasicDTO(obj);\r\n");
				sbMethod.append("			});\r\n");
				sbMethod.append("		}\r\n");
				sbMethod.append("		return ResponseEntity.ok(findById.get());\r\n");
				sbMethod.append("	}\r\n");
				sb.append(sbMethod.toString()
						.replace("Atributo", entidadeDTO.getNomeCapitalizado())
						.replace("atributo", entidadeDTO.getNomeInstancia())
						.replace("Entidade", atributo.getNomeCapitalizado())
						.replace("entidade", atributo.getNome())
				);
			}
		}
		
		return content.replace("//[findByFK]", sb.toString());
	}

}
