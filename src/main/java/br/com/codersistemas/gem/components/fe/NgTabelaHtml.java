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
		
		//<p-table [value]="pessoas" [paginator]="true" [rows]="10">
		Element pTable = new Element("p-table");
		pTable.attr("[value]", StringUtil.uncaplitalizePlural(obj.getNome()));
		pTable.attr("[paginator]", "true");
		pTable.attr("[rows]", "10");
		pPanel.appendChild(pTable);
		
		//Elements pTableTemplates = new Elements();
		Element templateHeader = new Element("ng-template");
		templateHeader.attr("pTemplate", "header");
		pTable.appendChild(templateHeader);
		//pTableTemplates.add(templateHeader);
		
		Element tr = new Element("tr");
		for (AtributoDTO atributo : atributos) {
			Element th = new Element("th");
			th.html(atributo.getRotulo());
			tr.appendChild(th);
		}
		templateHeader.appendChild(tr);

		Element templateBody = new Element("ng-template");
		templateBody.attr("pTemplate", "body");
		pTable.appendChild(templateBody);
		
		Element tr2 = new Element("tr");
		for (AtributoDTO atributo : atributos) {
			Element td = new Element("td");
			String html = "";
			 if("DATE".equals(atributo.getTipo())) {
				 td.html("{{" + atributo.getEntidade().getNomeInstancia() + "."+atributo.getNome() + " | date: 'dd/MM/yyyy hh:mm:ss'}}");
	         } else if("INTEGER".equals(atributo.getTipo())
	            		|| "LONG".equals(atributo.getTipo())) {
				 td.attr("style", "text-align: right;");
				 td.html("{{" + atributo.getEntidade().getNomeInstancia() + "."+atributo.getNome() + "}}");
	         } else if("FLOAT".equals(atributo.getTipo())
	            		|| "DOUBLE".equals(atributo.getTipo())) {
				 td.attr("style", "text-align: right;");
				 td.html("{{" + atributo.getEntidade().getNomeInstancia() + "."+atributo.getNome()+" | number: " + atributo.getPrecisao() + " }}");
	         } else if("BOOLEAN".equals(atributo.getTipo())) {
				 td.attr("style", "text-align: center;");
				 td.html("{{" + atributo.getEntidade().getNomeInstancia() + "."+atributo.getNome()+"?'Sim':'Não' }}");
	         } else {
	        	 td.html("{{" + atributo.getEntidade().getNomeInstancia() + "."+atributo.getNome()+"}}");
	         }
			
			tr2.appendChild(td);
		}
		templateBody.appendChild(tr2);
		
		System.out.println(pPanelElements.html().replace("=\"ok\"", ""));
	}

	@Override
	public String print() {
		return container.body().html();
	}


}
