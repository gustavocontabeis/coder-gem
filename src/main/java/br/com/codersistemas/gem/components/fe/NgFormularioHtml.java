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
		divFluid.addClass("ui-g ui-fluid");
		
		divForm.appendChild(divFluid);
		
		elements.add(divForm);
		
		divFluid.attr("*ngIf", entidade.getNomeInstancia());
		
		for (AtributoDTO atributo : atributos) {
			
			//System.out.println(atributo);
			
			if(atributo.isCollection())
				continue;
			
			Element divContainer = divFluid.appendElement("div");
			divContainer.addClass("ui-g-12");
			    
            Element divLabel = divContainer.appendElement("div");
            divLabel.addClass("ui-g-4 label");
            
			Element label = divLabel.appendElement("label");
			label.attr("id", "label-"+atributo.getNome());
			label.attr("for", atributo.getNome());
			label.html(atributo.getRotulo());
			
            Element divInput = divContainer.appendElement("div");
            divInput.addClass("ui-g-8 input");
            
            
            //TODO incluir mask
            // <p-inputMask id="altura" [(ngModel)]="pessoa.altura" mask="99-9999"></p-inputMask>
            
            if(atributo.isFk()) {
            	Element input = divInput.appendElement("p-dropdown");
            	input.attr("id", atributo.getNome());
            	input.attr("name", atributo.getNome());
            	input.attr("#"+atributo.getNome(), "ngModel");
            	input.attr("[options]", StringUtil.uncaplitalizePlural(atributo.getNome()));
            	input.attr("[(ngModel)]", atributo.getEntidade().getNomeInstancia()+"."+atributo.getNome());
               	input.attr("[required]", String.valueOf(atributo.isObrigatorio()));
            } else if(atributo.isEnum()) {
            	Element input = divInput.appendElement("p-dropdown");
            	input.attr("id", atributo.getNome());
            	input.attr("name", atributo.getNome());
            	input.attr("#"+atributo.getNome(), "ngModel");
            	input.attr("[options]", StringUtil.uncaplitalizePlural(atributo.getNome()));
            	input.attr("[(ngModel)]", atributo.getEntidade().getNomeInstancia()+"."+atributo.getNome());
               	input.attr("[required]", String.valueOf(atributo.isObrigatorio()));
            } else if("DATE".equals(atributo.getTipo())) {
            	Element input = divInput.appendElement("p-calendar");
            	input.attr("id", atributo.getNome());
            	input.attr("name", atributo.getNome());
            	input.attr("#"+atributo.getNome(), "ngModel");
            	input.attr("dateFormat", "dd/mm/yy");
            	input.attr("[(ngModel)]", atributo.getEntidade().getNomeInstancia()+"."+atributo.getNome());
               	input.attr("[required]", String.valueOf(atributo.isObrigatorio()));
            } else if("INTEGER".equals(atributo.getTipo())
            		|| "LONG".equals(atributo.getTipo())) {
            	Element input = divInput.appendElement("input");
            	input.attr("id", atributo.getNome());
            	input.attr("name", atributo.getNome());
            	input.attr("#"+atributo.getNome(), "ngModel");
            	input.attr("pInputText");
            	input.attr("pKeyFilter", "int");
            	input.attr("placeholder", atributo.getRotulo());
            	input.attr("[(ngModel)]", atributo.getEntidade().getNomeInstancia()+"."+atributo.getNome());
            	input.attr("[required]", String.valueOf(atributo.isObrigatorio()));
            	input.attr("pattern", "^\\d+$");
            } else if("FLOAT".equals(atributo.getTipo())
            		|| "DOUBLE".equals(atributo.getTipo())) {
            	Element input = divInput.appendElement("input");
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
            	Element input = divInput.appendElement("input");
            	input.attr("id", atributo.getNome());
            	input.attr("name", atributo.getNome());
            	input.attr("#"+atributo.getNome(), "ngModel");
            	input.attr("binary", "true");
            	input.attr("[(ngModel)]", atributo.getEntidade().getNomeInstancia()+"."+atributo.getNome());
               	input.attr("[required]", String.valueOf(atributo.isObrigatorio()));
            } else {
            	Element input = divInput.appendElement("input");
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
		buttonSalvar.attr("[disabled]", "!"+formName+".valid");
		
		divControls.appendChild(buttonSalvar);
		
		Element buttonExcluir = new Element("button");
		buttonExcluir.attr("pButton", "ok");
		buttonExcluir.attr("icon", "pi pi-times");
		buttonExcluir.attr("(click)", "confirmarExcluir()");
		buttonExcluir.attr("label", "Excluir");
		buttonExcluir.addClass("ui-button-secondary");
		buttonExcluir.attr("*ngIf", entidade.getNomeInstancia()+".id");
		
		divControls.appendChild(buttonExcluir);
		
		elements.add(divControls);
		
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
