package br.com.codersistemas.gem.components.fe;

import java.util.List;

import br.com.codersistemas.gem.components.Replacememnt;
import br.com.codersistemas.gem.components.ResourceComponent;
import br.com.codersistemas.libs.dto.AtributoDTO;
import br.com.codersistemas.libs.utils.ReflectionUtils;

public class NgFormularioHtml extends ResourceComponent {

	public NgFormularioHtml(Object obj, Replacememnt replacement) {
		super(replacement);
		content = gerarCampos(obj);
	}

	private String gerarCampos(Object obj) {
		List<AtributoDTO> atributos = ReflectionUtils.getAtributos(obj.getClass());
		String content = "";
		for (AtributoDTO atributo : atributos) {
			content += this.content
					.replace("#{label}", atributo.getRotulo())
					.replace("#{field}", atributo.getClasseInstancia() + "." + atributo.getNome());
		}
		return content;
	}

	@Override
	public String getResourceName() {
		return "form-field.html";
	}

}
