package br.com.codersistemas.gem.components.be;

import br.com.codersistemas.gem.components.IComponent;
import br.com.codersistemas.libs.dto.AtributoDTO;
import br.com.codersistemas.libs.dto.EntidadeDTO;
import br.com.codersistemas.libs.utils.StringUtil;

public class SQLInsertComponent implements IComponent {

	private EntidadeDTO entidadeDTO;

	public SQLInsertComponent(EntidadeDTO entidadeDTO) {
		this.entidadeDTO = entidadeDTO;
	}

	@Override
	public String print() {
		StringBuilder sb = new StringBuilder();
		sb.append("INSERT INTO public." + entidadeDTO.getTabela() + " (");

		String x = "";
		for (AtributoDTO atributo : entidadeDTO.getAtributos()) {
			if (atributo.isCollection())
				continue;
			x += atributo.getColuna() + ", ";
		}

		x = StringUtil.removeEnd(x, ", ") + ") values (";
		sb.append(x);

		x = "";
		for (AtributoDTO atributo : entidadeDTO.getAtributos()) {
			if (atributo.isCollection())
				continue;

			switch (atributo.getTipo()) {
			case "BOOLEAN":
				x += "FALSE, ";
				break;
			case "INTEGER":
				x += "1, ";
				break;
			case "LONG":
				x += "2, ";
				break;
			case "FLOAT":
				x += "3.5, ";
				break;
			case "DOUBLE":
				x += "4.5, ";
				break;
			case "DATE":
				x += "'2000-12-31', ";
				break;
			case "STRING":
				x += "'OK', ";
				break;
			}
		}

		x = StringUtil.removeEnd(x, ", ");
		x += ");";
		sb.append(x);


		return sb.toString();
	}

}
