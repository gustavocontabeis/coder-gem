package br.com.codersistemas.gem.components.fe;

import java.lang.reflect.Field;

import br.com.codersistemas.gem.components.Replacememnt;
import br.com.codersistemas.gem.components.ResourceComponent;
import br.com.codersistemas.libs.utils.ReflectionUtils;
import br.com.codersistemas.libs.utils.StringUtil;

public class NgFormularioHtml extends ResourceComponent {

	public NgFormularioHtml(Object obj, Replacememnt replacement) {
		super(replacement);
		content = gerarCampos(obj);
	}

	private String gerarCampos(Object obj) {
		Field[] fields = ReflectionUtils.getFields(obj.getClass());
		String x = "";
		String prefix = StringUtil.uncapitalize(obj.getClass().getSimpleName());
		for (Field field : fields) {
			x += content
					.replace("#{label}", StringUtil.label( field.getName()) )
					.replace("#{field}", prefix + "." + field.getName());
		}
		return x;
	}

	@Override
	public String getResourceName() {
		return "form-field.html";
	}

}
