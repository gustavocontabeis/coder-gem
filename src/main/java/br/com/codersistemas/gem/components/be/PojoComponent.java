package br.com.codersistemas.gem.components.be;

import br.com.codersistemas.libs.utils.JPAUtil;

public class PojoComponent {
	
	String content;
	
	public PojoComponent(Object rom) {
		content = gerarPojo(rom.getClass());
	}

	public String gerarPojo(Class classe) {
		StringBuilder sb = new StringBuilder();
		sb.append(JPAUtil.gerarClasse(classe));
		sb.append("\n");
		sb.append(JPAUtil.gerarAtributos(classe));
		sb.append("\n");
		sb.append("}\n");
		sb.append("//Ajuste os tamanhos dos campos.\n");
		return sb.toString();
	}
	
	public String print() {
		return content;
	}

}
