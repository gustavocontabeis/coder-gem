package br.com.codersistemas.gem.components.be;

import br.com.codersistemas.gem.components.IComponent;
import br.com.codersistemas.libs.utils.JPAUtil;

public class PojoComponent implements IComponent {

	String content;

	public PojoComponent(Class classe) {
		content = gerarPojo(classe);
	}

	public String gerarPojo(Class classe) {
		StringBuilder sb = new StringBuilder();
		sb.append(JPAUtil.gerarClasse(classe));
		sb.append("\n");
		sb.append(JPAUtil.gerarAtributos(classe));
		sb.append("\n");
		sb.append("}\n");
		sb.append("//Ajuste os tamanhos dos campos, obrigatoriedade, etc.\n");
		return sb.toString();
	}

	@Override
	public String print() {
		return content;
	}

}
