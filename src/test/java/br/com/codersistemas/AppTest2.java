package br.com.codersistemas;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.junit.Before;
import org.junit.Test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import br.com.codersistemas.codergemapi.domain.Aplicacao;
import br.com.codersistemas.codergemapi.domain.Atributo;
import br.com.codersistemas.codergemapi.domain.Entidade;
import br.com.codersistemas.gem.components.IComponent;
import br.com.codersistemas.gem.components.Replacememnt;
import br.com.codersistemas.gem.components.ResourceComponent;
import br.com.codersistemas.gem.components.TSClass;
import br.com.codersistemas.gem.components.be.ControllerComponent;
import br.com.codersistemas.gem.components.be.PojoComponent;
import br.com.codersistemas.gem.components.be.RespositoryComponent;
import br.com.codersistemas.gem.components.fe.NgComponent;
import br.com.codersistemas.gem.components.fe.NgComponentHtml;
import br.com.codersistemas.gem.components.fe.NgDialogHtml;
import br.com.codersistemas.gem.components.fe.NgFormularioHtml;
import br.com.codersistemas.gem.components.fe.NgService;
import br.com.codersistemas.gem.components.fe.NgTabelaHtml;
import br.com.codersistemas.libs.dto.AplicacaoDTO;
import br.com.codersistemas.libs.dto.AtributoDTO;
import br.com.codersistemas.libs.dto.ColumnDTO;
import br.com.codersistemas.libs.dto.EntidadeDTO;
import br.com.codersistemas.libs.utils.JPAUtil;
import br.com.codersistemas.libs.utils.MockUtils;
import br.com.codersistemas.libs.utils.ReflectionUtils;
import br.com.codersistemas.libs.utils.StringUtil;
import br.com.codersistemas.libs.utils.mock.Genero;
import br.com.codersistemas.libs.utils.mock.Pessoa;
import br.com.codersistemas.libs.utils.mock.PessoaFisica;

public class AppTest2 {
	
	
	private Class classe = null;
	
	private Replacememnt r;
	
	private AplicacaoDTO appDTO;
	private EntidadeDTO entidadeDTO;
	private String json;
	
	private Object obj;

	@Before
	public void antes() throws Exception{
		
		classe = PessoaFisica.class;
		
		r = Replacememnt.builder()
				.addClass(classe)
				.build();
		
		gerarAplicacaoDTO();
		
		obj = classe.newInstance();
		
	}

	//@Test
	public void gerarAplicacaoDTO() throws Exception {
		
		appDTO = gerarAplicacaoDTO("minha-app", classe);
		
		List<EntidadeDTO> entidades = appDTO.getEntidades();
		for (EntidadeDTO entidade : entidades) {
			entidade.setAplicacao(null);
			List<AtributoDTO> atributos = entidade.getAtributos();
			for (AtributoDTO atributo : atributos) {
				atributo.setEntidade(null);
			}
		}
		
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		json = gson.toJson(appDTO);
		
		appDTO = gerarAplicacaoDTO("minha-app", classe);

	}

	@Test
	public void gerarPojo(){
		PojoComponent component = new PojoComponent(obj);
		System.out.println(component.print());
	}

	@Test
	public void gerarSQLInserts(){
	}

	@Test
	public void gerarRepository(){
		ResourceComponent component = new RespositoryComponent(r);
		System.out.println(component.print());
	}

	@Test
	public void gerarService(){}

	@Test
	public void gerarSpecification(){
		gerarSpecification(entidadeDTO);
	}

	@Test
	public void gerarRestController(){
		ResourceComponent component = new ControllerComponent(r);
		System.out.println(component.print());
	}

	@Test
	public void gerarRestControllerTestes(){
		//gerar URL
		//Gerar body post
	}

	@Test
	public void gerarTSClass(){
		TSClass tsClass = new TSClass(obj); 
		System.err.println(tsClass.print());
	}
	
	@Test
	public void gerarNGService() throws Exception {
		NgService controller = new NgService(r);
		System.out.println(controller.print());
	}

	@Test
	public void gerarNGComponent() throws Exception {		
		NgComponent controller = new NgComponent(r);
		System.out.println(controller.print());
	}

	@Test
	public void gerarFormulario() throws Exception {
		NgComponentHtml ngHtmlCrud = new NgComponentHtml(obj, r);
		System.out.println(ngHtmlCrud.print());
	}

	@Test
	public void gerarTabela() throws Exception {
		IComponent ngHtmlCrud = new NgTabelaHtml(entidadeDTO);
		System.out.println(ngHtmlCrud.print());
	}

	@Test
	public void gerarCampos() throws Exception {
		IComponent ngHtmlCrud = new NgFormularioHtml(entidadeDTO);
		System.out.println(ngHtmlCrud.print());
	}

	@Test
	public void gerarDialog() throws Exception {
		NgDialogHtml obj = new NgDialogHtml(this.obj, r);
		obj.setHeaderText("OK");
		obj.setExibirDialog("exibirDialog");
		System.out.println(obj.print());
	}

	/**
	 * https://www.baeldung.com/java-with-jsoup
	 */
	@Test
	public void alterarFormulario() throws Exception {
		
		List<String> readAllLines = Files.readAllLines(Paths.get(this.getClass().getResource("post.component.html").toURI()), Charset.defaultCharset());
		StringBuilder sb = new StringBuilder();
		readAllLines.stream().forEach(s->sb.append(s+"\n"));
		String text = sb.toString();
		
		Document document = Jsoup.parse(text);
		Elements titulo = document.select(".titulo");
		text = text.replace(titulo.outerHtml(), "["+titulo.outerHtml()+"]");
		System.out.println(text);
	}
	
	@Test
	public void alterarFormularioXXX() throws Exception {
		
		
	}
	
	private void gerarSpecification(EntidadeDTO entidadeDTO) {
		
		List<AtributoDTO> atributos = entidadeDTO.getAtributos();
		for (AtributoDTO atributo : atributos) {
			if(atributo.isFk()) {
				System.out.println("Join<Object, Object> join" + StringUtil.capitalize(atributo.getNome()) + " = root.join(\"" + atributo.getNome() + "\");");
			}
		}
		
		System.out.println("List<Predicate> predicates = new ArrayList<>();");
		
		for (AtributoDTO atributo : atributos) {
			if(!atributo.isFk()) {
				System.out.println("predicates.add(cb.equal(" + atributo.getEntidade().getNomeInstancia() + ".get(\"" + atributo.getNome() + "\"), filter.get" + StringUtil.capitalize(atributo.getNome()) + "()));");
			}
		}
		
	}

	private AplicacaoDTO gerarAplicacaoDTO(String nomeAplicacao, Class<?>...classes) {
		
		AplicacaoDTO aplicacao = new AplicacaoDTO();
		aplicacao.setNome(nomeAplicacao);
		aplicacao.setEntidades(new ArrayList<EntidadeDTO>());
		
		for (Class<?> classe : classes) {
			
			EntidadeDTO entidade = new EntidadeDTO();
			entidade.setAtributos(new ArrayList<>());
			entidade.setNome(classe.getSimpleName());
			entidade.setNomeClasse(classe.getName());
			entidade.setNomeInstancia(StringUtil.uncapitalize(classe.getSimpleName()));
			entidade.setRotulo(StringUtil.label(classe.getSimpleName()));
			entidade.setTabela(StringUtil.toUnderlineCase(classe.getSimpleName()).toLowerCase());
			entidade.setAplicacao(aplicacao);
			entidade.setRestURI("/"+StringUtil.uncaplitalizePlural(entidade.getTabela().replace("_", "-")));
			aplicacao.getEntidades().add(entidade);
			
			Field[] fields = ReflectionUtils.getFields(classe);
			for (Field field : fields) {
				AtributoDTO atributo = new AtributoDTO();
				if(field.getType().isEnum()) {
					List<String> enumValues = new ArrayList<>();
					Class enummClass = field.getType();
					Object[] enumConstants = enummClass.getEnumConstants();
					for (Object object : enumConstants) {
						Enum e = (Enum) object;
						enumValues.add(e.name());
					}
					atributo.setEnum(true);
					atributo.setEnumaracao(enumValues.toArray(new String[enumValues.size()]));
				}
				
				atributo.setTipoClasse(field.getType().getName());
				atributo.setNome(field.getName());
				atributo.setRotulo(StringUtil.label(field.getName()));
				atributo.setCollection(field.getType().isArray() || field.getType() == List.class || field.getType() == Set.class || field.getType() == Map.class);
				atributo.setFk((!atributo.getTipoClasse().startsWith("java.") && !atributo.isEnum()) || atributo.isCollection());
				ColumnDTO columnDTO = JPAUtil.getDto(classe, field);
				
				if(StringUtil.isNotBlank(columnDTO.getName())){
					atributo.setColuna(columnDTO.getName());
				} else {
					atributo.setColuna(field.getName());
				}
				
				atributo.setPk(columnDTO.isPk());
				
				if(atributo.isPk() && !atributo.getColuna().startsWith("id_")) {
					atributo.setColuna("id_"+atributo.getColuna());
				}
				
				atributo.setObrigatorio(!columnDTO.isNullable());
				atributo.setPrecisao(columnDTO.getPrecision());
				atributo.setTamanho(columnDTO.getLength());
				atributo.setTipo(field.getType().getSimpleName().toUpperCase());
				atributo.setEntidade(entidade);
				
				
				Method getter = ReflectionUtils.getGetter(classe, field);
				if(getter != null) {
					Class tipoGenericoRetorno = ReflectionUtils.getTipoGenericoRetorno(getter);
					if(tipoGenericoRetorno != null) {
						atributo.setTipoClasseGenerica(tipoGenericoRetorno.getName());
						atributo.setTipoClasseGenericaNome(tipoGenericoRetorno.getSimpleName());
					}
				}
				
				if(atributo.isCollection()) {
					atributo.setColuna(null);
					atributo.setObrigatorio(false);
				}
				
				entidade.getAtributos().add(atributo);
			}
		}
		
		return aplicacao;
		
	}

}
