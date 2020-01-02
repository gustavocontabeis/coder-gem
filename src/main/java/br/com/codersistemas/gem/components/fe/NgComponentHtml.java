package br.com.codersistemas.gem.components.fe;

import java.lang.reflect.Field;

import org.jsoup.nodes.Document;

import br.com.codersistemas.gem.components.Replacememnt;
import br.com.codersistemas.gem.components.ResourceComponent;
import br.com.codersistemas.libs.utils.ReflectionUtils;
import br.com.codersistemas.libs.utils.StringUtil;

public class NgComponentHtml extends ResourceComponent {

	private String colunasTitulos;
	private String colunasConteudos;

	public NgComponentHtml(Object obj, Replacememnt replacement) {
		super(replacement);
		colunasTitulos = gerarColunasTitulos(obj);
		colunasConteudos = gerarColunasConteudos(obj);
		document.select(".coder-colunas-titulos").html(colunasTitulos);
		document.select(".coder-colunas-conteudo").html(colunasConteudos);
		content = document.outerHtml();
	}

	private String gerarColunasConteudos(Object obj) {
		Field[] fields = ReflectionUtils.getFields(obj.getClass());
		String x = "";
		for (Field field : fields) {
			x += "<td>{{pessoa."+ field.getName() +"}}</td>\n";
		}
		return x;
	}

	private String gerarColunasTitulos(Object obj) {
		Field[] fields = ReflectionUtils.getFields(obj.getClass());
		String x = "";
		for (Field field : fields) {
			x += "<th>"+ StringUtil.label(field.getName()) +"</th>\n";
		}
		return x;
	}

	@Override
	public String getResourceName() {
		return "usuario.component.html";
	}

}
