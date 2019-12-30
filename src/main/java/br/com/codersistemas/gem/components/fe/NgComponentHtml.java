package br.com.codersistemas.gem.components.fe;

import java.lang.reflect.Field;

import org.jsoup.nodes.Document;

import br.com.codersistemas.gem.components.Component;
import br.com.codersistemas.gem.components.Replacememnt;
import br.com.codersistemas.libs.utils.ReflectionUtils;
import br.com.codersistemas.libs.utils.StringUtil;

public class NgComponentHtml extends Component {

	private String colunasTitulos;
	private String colunasConteudos;
	
	@Override
	protected Document document(Document document) {
		gerarColunasConteudos(obj);
		document.select("p-table").select("ng-template").select(".coder-colunas-titulos").html(colunasTitulos);
		document.select("p-table").select("ng-template").select(".coder-colunas-conteudo").html(colunasConteudos);
		return document;
	}

	public NgComponentHtml(Object obj, Replacememnt replacement) {
		
		Document document = getResourceAsDocument("usuario.component.html");
		System.out.println("=======================================================");
		System.out.println(document.outerHtml());
		System.out.println("=======================================================");
		
		
		System.out.println("=======================================================");
		System.out.println(document.outerHtml());
		System.out.println("=======================================================");
		
		colunasTitulos = gerarColunasTitulos(obj);
		colunasConteudos = gerarColunasConteudos(obj);
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
	protected String getResourceName() {
		return "usuario.component.html";
	}

}
