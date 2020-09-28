package br.com.codersistemas.gem.components.be;


import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.Parameter;
import java.util.Arrays;

import br.com.codersistemas.gem.components.Replacememnt;
import br.com.codersistemas.gem.components.ResourceComponent;
import br.com.codersistemas.libs.utils.ReflectionUtils;
import br.com.codersistemas.libs.utils.StringUtil;

public class ServiceTestComponent extends ResourceComponent {

	private Class classe;

	public ServiceTestComponent(Class classe) {
		super(Replacememnt.builder().addClass(classe).build());
		this.classe = classe;
	}

	@Override
	protected String printDepois(String content) {
		content = declararMocks(content);
		content = declararMetodosTeste(content);
		return content;
	}

	private String declararMocks(String content) {

		StringBuilder sb = new StringBuilder();
		Field[] fields = ReflectionUtils.getFields(classe);
		for (Field field : fields) {
			if(field.getType().isPrimitive() || "log".equals(field.getName())){
				continue;
			}
			sb.append("\t@Mock\n");
			sb.append("\tprivate ");
			sb.append(field.getType().getSimpleName()+" ");
			sb.append(StringUtil.uncapitalize(field.getType().getSimpleName())+";\n");
				
		}
		
		return content.replace("//[atributos]", sb.toString());
	}

	private String declararMetodosTeste(String content) {
		StringBuilder sb = new StringBuilder();
		Arrays.asList(ReflectionUtils.getMethods(classe)).stream().filter(method->!method.getName().startsWith("lambda$")&& Modifier.isPublic(method.getModifiers())).forEach(method->{
			sb.append("\t@Test\n");
			sb.append("\tprivate void when_"+method.getName()+"_then_success(){\n");
			Parameter[] parameters = method.getParameters();
			if(parameters.length > 0) {
				for (Parameter parameter : parameters) {
					sb.append("\t\t"+parameter.getType().getSimpleName()+" "+parameter.getName()+" = null;\n");
					sb.append("\t\t//when(null).thenReturn(null);\n");
					sb.append("\t\tservice."+method.getName()+"("+parameter.getName()+");\n");
				}
			}
			sb.append("\t}\n\n");
		});
		return content.replace("//[metodos]", sb.toString());
	}

	@Override
	public String getResourceName() {
		return "UsuarioServiceTest.txt";
	}

}
