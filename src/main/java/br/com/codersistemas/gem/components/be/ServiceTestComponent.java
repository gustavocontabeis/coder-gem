package br.com.codersistemas.gem.components.be;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.List;

import br.com.codersistemas.gem.components.IComponent;
import br.com.codersistemas.libs.dto.AtributoDTO;
import br.com.codersistemas.libs.dto.EntidadeDTO;
import br.com.codersistemas.libs.utils.ReflectionUtils;

public class ServiceTestComponent implements IComponent {

	private EntidadeDTO entidadeDTO;

	public ServiceTestComponent(EntidadeDTO entidadeDTO) {
		super();
		this.entidadeDTO = entidadeDTO;
	}

	@Override
	public String print() {
		
		StringBuilder sb = new StringBuilder();

		sb.append("import static org.junit.Assert.*;\r\n");
		sb.append("import static org.mockito.Mockito.*;\r\n");
		sb.append("import java.time.LocalDateTime;\r\n");
		sb.append("import org.junit.Test;\r\n");
		sb.append("import org.junit.runner.RunWith;\r\n");
		sb.append("import org.mockito.InjectMocks;\r\n");
		sb.append("import org.mockito.Mock;\r\n");
		sb.append("import org.springframework.test.context.junit4.SpringRunner;\r\n");
		sb.append("\r\n");
		sb.append("@RunWith(SpringRunner.class)\r\n");
		sb.append("public class ImovelHistoricoServiceTest {\r\n");
		sb.append("\r\n");
		sb.append("	@InjectMocks\r\n");
		sb.append("	private ImovelHistoricoService service;\r\n");
		sb.append("	\r\n");

		List<AtributoDTO> atributos = entidadeDTO.getAtributos();
		for (AtributoDTO atributo : atributos) {
			if (atributo.isFk()) {
				sb.append("	@Mock\r\n");
				sb.append("	private " + atributo.getNome() + " " + atributo.getNomeInstancia() + ";\r\n");
			}
		}
		
		Method[] methods = ReflectionUtils.getMethods(entidadeDTO.getClasse());
		for (Method method : methods) {
			 if (Modifier.isPublic(method.getModifiers())) {
				 
					sb.append("	@Test\r\n");
					sb.append("	public void when_" + method.getName() + "_when_success() {\r\n");
					sb.append("		//when(authenticationFacade.getUsuarioLogado()).thenReturn(Usuario.builder().build());\r\n");
					sb.append("		//service." + method.getName() + "();\r\n");
					sb.append("	}\r\n");
			 }
		}

		sb.append("}\r\n");
		String str = sb.toString().replace("ImovelHistorico", entidadeDTO.getNome());
		System.out.println(str);

		return null;
	}

}
