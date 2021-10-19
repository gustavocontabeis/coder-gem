package br.com.codersistemas.gem.components;

import br.com.codersistemas.gem.util.TypeScriptUtils;
import br.com.codersistemas.libs.dto.AtributoDTO;
import br.com.codersistemas.libs.dto.EntidadeDTO;

public class TSClass extends Component {
	
	StringBuilder sb = new StringBuilder();

	public TSClass(EntidadeDTO entidade) {
		super(null);
		sb.append("export class " + entidade.getNomeCapitalizado() + " {\n");
		for (AtributoDTO atributo : entidade.getAtributos()) {
			sb.append("\t"+atributo.getNome() + "!: " + TypeScriptUtils.toTypeScript(atributo.getClasse()) + ";\n");
		}
		sb.append("}\n");
	}

	@Override
	public String print() {
		return sb.toString();
	}

}

