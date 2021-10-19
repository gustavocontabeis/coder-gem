package br.com.codersistemas.gem.components.fe;

import java.util.List;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import br.com.codersistemas.gem.components.ResourceComponent;
import br.com.codersistemas.libs.dto.AtributoDTO;
import br.com.codersistemas.libs.dto.EntidadeDTO;
import br.com.codersistemas.libs.utils.StringUtil;

public class NgFormularioHtml extends ResourceComponent {

	private Document container;

	public NgFormularioHtml(EntidadeDTO dto) {
		super(null);
		gerarCampos(dto);
	}

	/**
	 * https://www.tektutorialshub.com/angular/template-driven-form-validation-in-angular/#component-class
	 * @param entidade
	 */
	private void gerarCampos(EntidadeDTO entidade) {
		
		List<AtributoDTO> atributos = entidade.getAtributos();
		
		Elements elements = new Elements();
		
		Element divForm = new Element("form");
		String formName = entidade.getNomeInstancia()+"Form"; 
		divForm.attr("#"+formName, "ngForm");
		divForm.attr("(ngSubmit)", "onSubmit("+formName+")");
		
		Element divFluid = new Element("div");
		divFluid.addClass("p-fluid");
		
		divForm.appendChild(divFluid);
		
		elements.add(divForm);
		
		divFluid.attr("*ngIf", entidade.getNomeInstancia());
		
		for (AtributoDTO atributo : atributos) {
			
			//System.out.println(atributo);
			
			if(atributo.isCollection())
				continue;
			
			Element divField = divFluid.appendElement("div");
			divField.addClass("p-field");
			    
			Element label = divField.appendElement("label");
			label.attr("id", "label-"+atributo.getNome());
			label.attr("for", atributo.getNome());
			label.html(atributo.getRotulo());
			
            if(atributo.isFk()) {
            	Element input = divField.appendElement("p-dropdown");
            	input.attr("id", atributo.getNome());
            	input.attr("name", atributo.getNome());
            	input.attr("#"+atributo.getNome(), "ngModel");
            	input.attr("[options]", StringUtil.uncaplitalizePlural(atributo.getNome()));
            	input.attr("[(ngModel)]", atributo.getEntidade().getNomeInstancia()+"."+atributo.getNome());
               	input.attr("[required]", String.valueOf(atributo.isObrigatorio()));
            } else if(atributo.isEnum()) {
            	Element input = divField.appendElement("p-dropdown");
            	input.attr("id", atributo.getNome());
            	input.attr("name", atributo.getNome());
            	input.attr("#"+atributo.getNome(), "ngModel");
            	input.attr("[options]", StringUtil.uncaplitalizePlural(atributo.getNome()));
            	input.attr("[(ngModel)]", atributo.getEntidade().getNomeInstancia()+"."+atributo.getNome());
               	input.attr("[required]", String.valueOf(atributo.isObrigatorio()));
            } else if("DATE".equals(atributo.getTipo())) {
            	Element input = divField.appendElement("p-calendar");
            	input.attr("id", atributo.getNome());
            	input.attr("name", atributo.getNome());
            	input.attr("#"+atributo.getNome(), "ngModel");
            	input.attr("dateFormat", "dd/mm/yy");
            	input.attr("[(ngModel)]", atributo.getEntidade().getNomeInstancia()+"."+atributo.getNome());
               	input.attr("[required]", String.valueOf(atributo.isObrigatorio()));
            } else if("INTEGER".equals(atributo.getTipo())
            		|| "LONG".equals(atributo.getTipo())) {
            	Element input = divField.appendElement("input");
            	input.attr("id", atributo.getNome());
            	input.attr("name", atributo.getNome());
            	input.attr("#"+atributo.getNome(), "ngModel");
            	input.attr("pInputText", "ok");
            	input.attr("pKeyFilter", "int");
            	input.attr("placeholder", atributo.getRotulo());
            	input.attr("[(ngModel)]", atributo.getEntidade().getNomeInstancia()+"."+atributo.getNome());
            	input.attr("[required]", String.valueOf(atributo.isObrigatorio()));
            	input.attr("pattern", "^\\d+$");
            } else if("FLOAT".equals(atributo.getTipo())
            		|| "DOUBLE".equals(atributo.getTipo())) {
            	Element input = divField.appendElement("input");
            	input.attr("id", atributo.getNome());
            	input.attr("name", atributo.getNome());
            	input.attr("#"+atributo.getNome(), "ngModel");
            	input.attr("pInputText");
            	input.attr("pKeyFilter", "int");
            	input.attr("placeholder", atributo.getRotulo());
            	input.attr("[(ngModel)]", atributo.getEntidade().getNomeInstancia()+"."+atributo.getNome());
            	input.attr("[required]", String.valueOf(atributo.isObrigatorio()));
            	input.attr("pattern", "^[+-]?\\d+(\\,\\d+)?$");
            } else if("BOOLEAN".equals(atributo.getTipo())) {
            	Element input = divField.appendElement("input");
            	input.attr("id", atributo.getNome());
            	input.attr("name", atributo.getNome());
            	input.attr("#"+atributo.getNome(), "ngModel");
            	input.attr("binary", "true");
            	input.attr("[(ngModel)]", atributo.getEntidade().getNomeInstancia()+"."+atributo.getNome());
               	input.attr("[required]", String.valueOf(atributo.isObrigatorio()));
            } else {
            	Element input = divField.appendElement("input");
            	input.attr("id", atributo.getNome());
            	input.attr("name", atributo.getNome());
            	input.attr("#"+atributo.getNome(), "ngModel");
            	input.attr("pInputText", "ok");
            	input.attr("placeholder", atributo.getRotulo());
            	input.attr("[(ngModel)]", atributo.getEntidade().getNomeInstancia()+"."+atributo.getNome());
               	input.attr("[required]", String.valueOf(atributo.isObrigatorio()));
            }
           	
		}
		
		Element divControls = new Element("div");
		divControls.addClass("ui-dialog-buttonpane ui-helper-clearfix");
		
		Element buttonSalvar = new Element("button");
		buttonSalvar.attr("pButton", "ok");
		buttonSalvar.attr("icon", "pi pi-check");
		buttonSalvar.attr("(click)", "salvar()");
		buttonSalvar.attr("label", "Salvar");
		buttonSalvar.attr("style", "margin-right: 2px;");
		buttonSalvar.attr("[disabled]", "!"+formName+".valid");
		
		divControls.appendChild(buttonSalvar);
		
		Element buttonExcluir = new Element("button");
		buttonExcluir.attr("pButton", "ok");
		buttonExcluir.attr("icon", "pi pi-times");
		buttonExcluir.attr("(click)", "confirmarExcluir()");
		buttonExcluir.attr("label", "Excluir");
		buttonExcluir.addClass("ui-button-secondary");
		buttonExcluir.attr("style", "margin-right: 2px;");
		buttonExcluir.attr("*ngIf", entidade.getNomeInstancia()+".id");
		
		divControls.appendChild(buttonExcluir);
		
		elements.add(divControls);
		
		Element confirmDialog = new Element("p-confirmDialog");
		elements.add(confirmDialog);
		confirmDialog.attr("#confirmacaoDialog", "ok");
		confirmDialog.attr("header", "Confirmação");
		confirmDialog.attr("icon", "pi pi-exclamation-triangle");
		
		Element pFooter = new Element("p-footer");
		confirmDialog.appendChild(pFooter);
		
		Element buttonNao = new Element("button");
		buttonNao.attr("type", "button");
		buttonNao.attr("pButton", "ok");
		buttonNao.attr("icon", "pi pi-times");
		buttonNao.attr("label", "Não");
		buttonNao.attr("(click)", "confirmacaoDialog.reject()");
		pFooter.appendChild(buttonNao);
		
		Element buttonSim = new Element("button");
		buttonSim.attr("type", "button");
		buttonSim.attr("pButton", "ok");
		buttonSim.attr("icon", "pi pi-check");
		buttonSim.attr("label", "Sim");
		buttonSim.attr("(click)", "confirmacaoDialog.accept()");
		pFooter.appendChild(buttonSim);
		
		System.out.println(elements.outerHtml().replace("=\"ok\"", ""));
	}

	@Override
	public String getResourceName() {
		return "form-field.html";
	}
	
	@Override
	public String print() {
		return container.body().html();
	}


}
