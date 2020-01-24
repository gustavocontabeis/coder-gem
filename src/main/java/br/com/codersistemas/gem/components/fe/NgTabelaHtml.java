package br.com.codersistemas.gem.components.fe;

import java.util.List;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import br.com.codersistemas.gem.components.IComponent;
import br.com.codersistemas.libs.dto.AtributoDTO;
import br.com.codersistemas.libs.dto.EntidadeDTO;
import br.com.codersistemas.libs.utils.StringUtil;

public class NgTabelaHtml implements IComponent {

	private Document container;

	public NgTabelaHtml(EntidadeDTO dto) {
		gerarCampos(dto);
	}

	private void gerarCampos(EntidadeDTO obj) {

		List<AtributoDTO> atributos = obj.getAtributos();

		Elements pPanelElements = new Elements();
		Element pPanel = new Element("p-panel");
		pPanel.attr("header", obj.getRotulo());
		pPanelElements.add(pPanel);

		// <p-table [value]="pessoas" [paginator]="true" [rows]="10">
		Element pTable = new Element("p-table");
		pTable.attr("[value]", StringUtil.uncaplitalizePlural(obj.getNome()));
		pTable.attr("[paginator]", "true");
		pTable.attr("[rows]", "10");
		pPanel.appendChild(pTable);

		// Elements pTableTemplates = new Elements();
		Element templateHeader = new Element("ng-template");
		templateHeader.attr("pTemplate", "header");
		pTable.appendChild(templateHeader);
		// pTableTemplates.add(templateHeader);

		Element tr = new Element("tr");
		for (AtributoDTO atributo : atributos) {
			if (atributo.isCollection())
				continue;
			Element th = new Element("th");
			th.html(atributo.getRotulo());
			tr.appendChild(th);
		}

		Element th = new Element("th");
		th.html("&nbsp;");
		tr.appendChild(th);

		templateHeader.appendChild(tr);

		Element templateBody = new Element("ng-template");
		templateBody.attr("pTemplate", "body");

		for (AtributoDTO atributo : atributos) {
			templateBody.attr("let-" + atributo.getEntidade().getNomeInstancia(), "ok");
			break;
		}
		pTable.appendChild(templateBody);

		Element trBody = new Element("tr");
		for (AtributoDTO atributo : atributos) {
			if (atributo.isCollection())
				continue;
			Element td = new Element("td");
			String html = "";
			if ("DATE".equals(atributo.getTipo())) {
				td.html("{{" + atributo.getEntidade().getNomeInstancia() + "." + atributo.getNome()
						+ " | date: 'dd/MM/yyyy hh:mm:ss'}}");
			} else if ("INTEGER".equals(atributo.getTipo()) || "LONG".equals(atributo.getTipo())) {
				td.attr("style", "text-align: right;");
				td.html("{{" + atributo.getEntidade().getNomeInstancia() + "." + atributo.getNome() + "}}");
			} else if ("FLOAT".equals(atributo.getTipo()) || "DOUBLE".equals(atributo.getTipo())) {
				td.attr("style", "text-align: right;");
				td.html("{{" + atributo.getEntidade().getNomeInstancia() + "." + atributo.getNome() + " | number: "
						+ atributo.getPrecisao() + " }}");
			} else if ("BOOLEAN".equals(atributo.getTipo())) {
				td.attr("style", "text-align: center;");
				td.html("{{" + atributo.getEntidade().getNomeInstancia() + "." + atributo.getNome()
						+ "?'Sim':'Não' }}");
			} else {
				td.html("{{" + atributo.getEntidade().getNomeInstancia() + "." + atributo.getNome() + "}}");
			}

			trBody.appendChild(td);
		}
		templateBody.appendChild(trBody);
		
		Element tdActions = new Element("td");
		String html = "<button type=\"button\" pButton icon=\"pi pi-pencil\" (click)=\"exibirModal(" + obj.getNomeInstancia() + ")\" title=\"Selecionar\"></button>";
		tdActions.html(html);
		trBody.appendChild(tdActions);

		System.out.println(pPanelElements.html().replace("=\"ok\"", ""));
	}

	@Override
	public String print() {
		return container.body().html();
	}

}
