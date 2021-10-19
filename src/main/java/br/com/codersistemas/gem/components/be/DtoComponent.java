package br.com.codersistemas.gem.components.be;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import br.com.codersistemas.gem.components.IComponent;
import br.com.codersistemas.libs.utils.JPAUtil;
import br.com.codersistemas.libs.utils.ReflectionUtils;
import br.com.codersistemas.libs.utils.StringUtil;

public class DtoComponent implements IComponent {

	String content;

	public DtoComponent(Class classe) {
		content = gerarPojo(classe);
	}

	public String gerarPojo(Class classe) {
		StringBuilder sb = new StringBuilder();
		sb.append(gerarClasse(classe));
		sb.append("\n");
		sb.append(gerarAtributos(classe));
		sb.append("\n");
		sb.append("}\n");
		return sb.toString();
	}

	@Override
	public String print() {
		return content;
	}
	
	public static String gerarClasse(Class classe) {
		StringBuilder sb = new StringBuilder();
		sb.append(classe.getPackage() + ";\n");
		sb.append("\n");
		sb.append("import java.io.Serializable;\n");
		sb.append("import java.util.*;\n");
		sb.append("import lombok.*;\n");
		sb.append("\n");
		sb.append("@Data\n");
		sb.append("@NoArgsConstructor\n");
		sb.append("@AllArgsConstructor\n");
		sb.append("@Builder\n");
		sb.append("public class " + classe.getSimpleName() + "DTO implements Serializable {\n");
		sb.append("	\n");
		sb.append("	private static final long serialVersionUID = 1L;");
		return sb.toString();
	}
	
	public static String gerarAtributos(Class classe) {

		Field[] fields = classe.getDeclaredFields();

		StringBuilder sb = new StringBuilder();

		for (Field field : fields) {

			String name = field.getName();
			if (!"serialVersionUID".equals(name)) {
				sb.append("\n");

				if(field.getType().isPrimitive()) {
					throw new RuntimeException("Nao utilize tipos primitivos");

				} else if (!field.getType().getName().startsWith("java")) {
					sb.append(String.format("\tprivate %sDTO %s;\n", ReflectionUtils.getTipo2(field), field.getName()));
				} else {
					sb.append(String.format("\tprivate %s %s;\n", ReflectionUtils.getTipo2(field), field.getName()));
					
				}
			}
		}
		return sb.toString();
	}



}
