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
import br.com.codersistemas.libs.dto.AtributoDTO;
import br.com.codersistemas.libs.dto.EntidadeDTO;
import br.com.codersistemas.libs.utils.JPAUtil;
import br.com.codersistemas.libs.utils.ReflectionUtils;
import br.com.codersistemas.libs.utils.StringUtil;

public class AdapterComponent implements IComponent {

	String content;
	
	private EntidadeDTO entidadeDTO;

	public AdapterComponent(Class classe) {
		content = gerarPojo(classe);
	}

	public AdapterComponent(EntidadeDTO entidadeDTO) {
		this.entidadeDTO = entidadeDTO;
		content = gerarPojo(entidadeDTO.getClasse());
	}

	public String gerarPojo(Class classe) {
		StringBuilder sb = new StringBuilder();
		sb.append(gerarClasse(classe));
		sb.append("\n");
		sb.append("\n");
		sb.append(String.format("\tpublic %s toEntity(%sDTO dto){\n", this.entidadeDTO.getNome(), this.entidadeDTO.getNome()));
		sb.append(String.format("\t\tif(Objects.nonNull(dto)){\n"));
		sb.append(String.format("\t\t\treturn %s.builder()\n", entidadeDTO.getNomeCapitalizado()));
		sb.append(gerarAtributosToEntity(classe)+"\n");
		sb.append("\t\t}else{\n");
		sb.append("\t\t\treturn null;\n");
		sb.append("\t\t}\n");
		sb.append("\t}\n");
		sb.append("\n");
		sb.append(String.format("\tpublic %sDTO valueOf(%s entity){\n", this.entidadeDTO.getNome(), this.entidadeDTO.getNome()));
		sb.append(String.format("\t\tif(Objects.nonNull(entity)){\n"));
		sb.append(String.format("\t\t\treturn %sDTO.builder()\n", entidadeDTO.getNomeCapitalizado()));
		sb.append(gerarAtributosToDto()+"\n");
		sb.append("\t\t}else{\n");
		sb.append("\t\t\treturn null;\n");
		sb.append("\t\t}\n");
		sb.append("\t}\n");
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
		sb.append("public class " + classe.getSimpleName() + "Adapter implements Serializable {\n");
		sb.append("	\n");
		sb.append("	private static final long serialVersionUID = 1L;");
		return sb.toString();
	}
	
	public String gerarAtributosToEntity(Class classe) {
		StringBuilder sb = new StringBuilder();
		entidadeDTO.getAtributos().forEach(a->sb.append(atributoAdapterToEntity(a)));
		sb.append("\t\t\t.build();");
		return sb.toString();
	}

	public String gerarAtributosToDto() {
		StringBuilder sb = new StringBuilder();
		entidadeDTO.getAtributos().forEach(a->sb.append(atributoAdapterToDto(a)));
		sb.append("\t\t\t.build();");
		return sb.toString();
	}

	private String atributoAdapterToEntity(AtributoDTO a) {
		return String.format("\t\t\t.%s(dto.get%s())\n", a.getNome(), a.getNomeCapitalizado());
	}

	private String atributoAdapterToDto(AtributoDTO a) {
		return String.format("\t\t\t.%s(entity.get%s())\n", a.getNome(), a.getNomeCapitalizado());
	}


}
