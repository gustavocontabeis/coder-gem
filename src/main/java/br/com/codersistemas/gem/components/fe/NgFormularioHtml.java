package br.com.codersistemas.gem.components.fe;

import java.util.List;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import br.com.codersistemas.gem.components.Replacememnt;
import br.com.codersistemas.gem.components.ResourceComponent;
import br.com.codersistemas.libs.dto.AtributoDTO;
import br.com.codersistemas.libs.utils.ReflectionUtils;
import br.com.codersistemas.libs.utils.StringUtil;

public class NgFormularioHtml extends ResourceComponent {

	private Document container;

	public NgFormularioHtml(Object obj, Replacememnt replacement) {
		super(replacement);
		gerarCampos(obj);
	}

	private void gerarCampos(Object obj) {
		
		List<AtributoDTO> atributos = ReflectionUtils.getAtributos(obj.getClass());
		
		Elements elements = new Elements();
		Element divFluid = new Element("div");
		divFluid.addClass("ui-g ui-fluid");
		elements.add(divFluid);
		
		divFluid.attr("*ngIf", StringUtil.uncapitalize(obj.getClass().getSimpleName()));
		
		for (AtributoDTO atributo : atributos) {
			
			System.out.println(atributo);
			
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
            
            if(atributo.getTipo().equals(java.util.Date.class)) {
            	Element input = divInput.appendElement("p-calendar");
            	input.attr("id", atributo.getNome());
            	input.attr("dateFormat", "dd/mm/yy");
            	input.attr("[(ngModel)]", atributo.getClasseInstancia()+"."+atributo.getNome());
            	if(atributo.isObrigatorio())
                	input.attr("required", atributo.getRotulo());
            } else if(atributo.getTipo().equals(java.lang.Integer.class)
            		|| atributo.getTipo().equals(java.lang.Long.class)) {
            	Element input = divInput.appendElement("input");
            	input.attr("id", atributo.getNome());
            	input.attr("pInputText");
            	input.attr("pKeyFilter", "int");
            	input.attr("placeholder", atributo.getRotulo());
            	input.attr("[(ngModel)]", atributo.getClasseInstancia()+"."+atributo.getNome());
            	if(atributo.isObrigatorio())
                	input.attr("required", atributo.getRotulo());
            } else if(atributo.getTipo().equals(java.lang.Boolean.class)) {
            	Element input = divInput.appendElement("input");
            	input.attr("id", atributo.getNome());
            	input.attr("pInputText");
            	input.attr("pKeyFilter", "int");
            	input.attr("placeholder", atributo.getRotulo());
            	input.attr("[(ngModel)]", atributo.getClasseInstancia()+"."+atributo.getNome());
            	if(atributo.isObrigatorio())
                	input.attr("required", atributo.getRotulo());
            } else {
            	Element input = divInput.appendElement("input");
            	input.attr("id", atributo.getNome());
            	input.attr("pInputText", "ok");
            	input.attr("placeholder", atributo.getRotulo());
            	input.attr("[(ngModel)]", atributo.getClasseInstancia()+"."+atributo.getNome());
            	if(atributo.isObrigatorio())
            		input.attr("required", atributo.getRotulo());
            }
					
		}
		
		System.out.println(elements.html().replace("=\"ok\"", ""));
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
