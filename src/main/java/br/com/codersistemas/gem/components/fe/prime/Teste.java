package br.com.codersistemas.gem.components.fe.prime;

import java.lang.reflect.Field;
import java.util.List;

import br.com.codersistemas.codergemapi.domain.Pessoa;
import br.com.codersistemas.libs.dto.AtributoDTO;
import br.com.codersistemas.libs.utils.ReflectionUtils;
import br.com.codersistemas.libs.utils.StringUtil;

public class Teste {
	public static void main(String[] args) {
		
		List<AtributoDTO> atributos = ReflectionUtils.getAtributos(Pessoa.class);
		Field[] fields = ReflectionUtils.getFields(Pessoa.class);
		Component panel = new Panel().add("width=800").add("height=600");
		for (AtributoDTO dto : atributos) {
			panel.add(new FormGroup().add(new Label().add("id='"+dto.getNome()+"'").addText(dto.getRotulo())).add(new Input().add("id=nome")));
		}
		System.out.println(panel.print());
//		System.out.println(
//				
//				.add(new Button().add("id='salvar'"))
//				.add(new Button().add("id='excluir'"))
//				.add(new Button())
//				.print());
	}
}

