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

	private Elements pPanelElements = new Elements();

	public NgTabelaHtml(EntidadeDTO dto) {
		gerarCampos(dto);
	}

	private void gerarCampos(EntidadeDTO obj) {

		List<AtributoDTO> atributos = obj.getAtributos();

		Element pPanel = new Element("p-panel");
		pPanel.attr("header", obj.getRotulo());
		pPanelElements.add(pPanel);

		// <p-table [value]="pessoas" [paginator]="true" [rows]="10">
		Element pTable = new Element("p-table");
		pTable.attr("[value]", StringUtil.uncaplitalizePlural(obj.getNome()));
		pTable.attr("[paginator]", "true");
		pTable.attr("[rows]", "10");
		pTable.attr("[rowsPerPageOptions]", "[5,10,15,20,50,100]");
		pTable.attr("[lazy]", "true");
		pTable.attr("(onLazyLoad)", "consultarPaginado($event)");
		pTable.attr("responsiveLayout", "scroll");
		pTable.attr("[totalRecords]", "totalRecords");
		pTable.attr("sortMode", "multiple");
		pTable.attr("[showCurrentPageReport]", "true");
		pTable.attr("currentPageReportTemplate", "Exibindo {first} até {last} de {totalRecords} registros.");
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
			th.attr("pSortableColumn", atributo.getNomeInstancia());
			th.html(atributo.getRotulo());
			Element sortIcon = new Element("p-sortIcon");
			sortIcon.attr("field", atributo.getNomeInstancia());
			th.appendChild(sortIcon);
			Element columnFilter = new Element("p-columnFilter");
			columnFilter.attr("type", "text");
			columnFilter.attr("field", atributo.getNome());
			columnFilter.attr("display", "menu");
			th.appendChild(columnFilter);
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
			td.attr(" class", "horizontal-compact");
			String html = "";
			
			if(atributo.isFk()) {
				td.html("<a [routerLink]=\"['/"+atributo.getNome()+"/', "+obj.getNomeInstancia()+"."+atributo.getNome()+".id]\">{{" + atributo.getEntidade().getNomeInstancia() + "." + atributo.getNomeInstancia() + ".id}}</a>");
			} else if ("DATE".equals(atributo.getTipo())) {
				td.html("{{" + atributo.getEntidade().getNomeInstancia() + "." + atributo.getNome()
						+ " | date: 'dd/MM/yyyy hh:mm:ss'}}");
			} else if ("INTEGER".equals(atributo.getTipo()) || "LONG".equals(atributo.getTipo())) {
				td.attr("style", "text-align: right;");
				td.html("{{" + atributo.getEntidade().getNomeInstancia() + "." + atributo.getNome() + "}}");
			} else if ("FLOAT".equals(atributo.getTipo()) || "DOUBLE".equals(atributo.getTipo()) || "BIGDECIMAL".equals(atributo.getTipo())) {
				td.attr("style", "text-align: right;");
				td.html("{{" + atributo.getEntidade().getNomeInstancia() + "." + atributo.getNome() + "| currency:'BRL':true:'1.2-2'}}");
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
		String html = "<button pButton icon=\"pi pi-pencil\" [routerLink]=\"['/"+obj.getNomeHyphenCase()+"/"+obj.getNomeHyphenCase()+"-add/', "+obj.getNomeInstancia()+".id]\" title=\"Selecionar "+obj.getRotulo()+"\"></button>";
		
		for (AtributoDTO i : atributos) {
			if (!i.isCollection())
				continue;
			html += ("<button pButton icon=\"pi pi-list\" [routerLink]=\"['/"+ StringUtil.toHiphenCase(StringUtil.uncapitalize(i.getTipoClasseGenericaNome())) +"/"+obj.getNomeHyphenCase()+"/', "+obj.getNomeInstancia()+".id]\" title=\"Selecionar "+i.getRotulo()+"\"></button>");
		}
		tdActions.html(html);
		trBody.appendChild(tdActions);

	}

	@Override
	public String print() {
		return pPanelElements.html()
				.replace("=\"ok\"", "")
				.replace("routerlink", "routerLink")
				.replace("pbutton", "pButton");
	}

}
