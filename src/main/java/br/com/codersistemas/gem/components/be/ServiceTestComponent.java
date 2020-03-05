package br.com.codersistemas.gem.components.be;


import java.lang.reflect.Modifier;
import java.util.Arrays;

import br.com.codersistemas.gem.components.Replacememnt;
import br.com.codersistemas.gem.components.ResourceComponent;
import br.com.codersistemas.libs.utils.ReflectionUtils;

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
		ReflectionUtils.getAtributos(classe).stream().filter(field->!"log".equals(field.getNome())).forEach(field->{
			sb.append("\t@Mock\n");
			sb.append("\tprivate ");
			sb.append(field.getClasse().getSimpleName()+" ");
			sb.append(field.getNome()+";\n");
		});

		return content.replace("//[atributos]", sb.toString());
	}

	private String declararMetodosTeste(String content) {
		StringBuilder sb = new StringBuilder();
		Arrays.asList(ReflectionUtils.getMethods(classe)).stream().filter(method->!method.getName().startsWith("lambda$")&& Modifier.isPublic(method.getModifiers())).forEach(method->{
			sb.append("\t@Test\n");
			sb.append("\tprivate void when_"+method.getName()+"_then_success(){\n\twhen(null).thenReturn(null);}\n\n");
		});
		return content.replace("//[metodos]", sb.toString());
	}

	@Override
	public String getResourceName() {
		return "UsuarioServiceTest.txt";
	}

}
