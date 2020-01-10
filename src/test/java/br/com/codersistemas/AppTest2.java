package br.com.codersistemas;

import java.lang.reflect.Field;
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
import br.com.codersistemas.libs.utils.StringUtil;
import br.com.codersistemas.libs.utils.mock.Genero;
import br.com.codersistemas.libs.utils.mock.Pessoa;
import br.gov.caixa.pedes.sistemas.siarr.util.ReflectionUtils;

public class AppTest2 {
	
	private Pessoa rom;
	private Pessoa gi;
	private Pessoa gus;
	
	private Replacememnt r;
	private Aplicacao app;
	private Object obj;
	
	private AplicacaoDTO appDTO;
	private EntidadeDTO entidadeDTO;

	@Before
	public void antes(){
		
		//System.out.println("====================================================================");
		
		//gerarPessoa();
		
		//app = (Aplicacao) ReflectionUtils.createObjectWithValues();
		
//		r = Replacememnt.builder()
//				.addClass(obj.getClass())
//				.build();
		
		try {
			Gson gson = new GsonBuilder().create();
			String str = new String(Files.readAllBytes(Paths.get(this.getClass().getResource("pessoa.json").toURI())));
			System.out.println(str);
			appDTO = gson.fromJson(str, AplicacaoDTO.class);
			entidadeDTO = appDTO.getEntidades().get(0);
			System.out.println(appDTO);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}

	private void gerarPessoa() {
		rom = new Pessoa();
		rom.setAltura(1.7F);
		rom.setAtivo(true);
		rom.setFilhos(new ArrayList<Pessoa>());
		rom.setGenero(Genero.FEMININO);
		rom.setId(1L);
		rom.setMae(null);
		rom.setNome("Rom");
		rom.setSalario(1000.0);
		
		gi = new Pessoa();
		gi.setAltura(1.7F);
		gi.setAtivo(true);
		gi.setFilhos(new ArrayList<Pessoa>());
		gi.setGenero(Genero.MASCULINO);
		gi.setId(2L);
		gi.setNome("Gi");
		gi.setSalario(1000.0);
		
		gus = new Pessoa();
		gus.setAltura(1.7F);
		gus.setAtivo(true);
		gus.setFilhos(new ArrayList<Pessoa>());
		gus.setGenero(Genero.MASCULINO);
		gus.setId(2L);
		gus.setNome("Gus");
		gus.setSalario(1000.0);
		
		rom.getFilhos().add(gus);
		gus.setMae(rom);
		gi.setMae(rom);
		
		app = new Aplicacao();
		app.setId(1L);
		app.setNome("Aplicação");
		
		app.setEntidades(new ArrayList<>());
		app.getEntidades().add(new Entidade());
		app.getEntidades().iterator().next().setAtributos(new ArrayList<Atributo>());
		app.getEntidades().iterator().next().getAtributos().add(new Atributo());
		
		obj = rom;

	}
	
	@Test
	public void gerarJson() throws Exception {
		Gson gson = new GsonBuilder().create();
		Aplicacao app = (Aplicacao) MockUtils.create(this.obj);
		System.out.println(gson.toJson(app));
		//System.out.println(component.print());
	}

	@Test
	public void gerarAplicacaoDTO() throws Exception {
		
		AplicacaoDTO dto = gerarAplicacaoDTO("minha-app", br.gov.caixa.pedes.sistemas.siarr.domain.Contrato.class);
		
		List<EntidadeDTO> entidades = dto.getEntidades();
		for (EntidadeDTO entidade : entidades) {
			entidade.setAplicacao(null);
			List<AtributoDTO> atributos = entidade.getAtributos();
			for (AtributoDTO atributo : atributos) {
				atributo.setEntidade(null);
			}
		}
		
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		//Aplicacao app = (Aplicacao) MockUtils.create(dto);
		System.out.println(gson.toJson(dto));
		//System.out.println(component.print());
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
			entidade.setTabela(StringUtil.toUnderlineCase(classe.getSimpleName().toLowerCase()));
			entidade.setAplicacao(aplicacao);
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
				atributo.setFk(!atributo.getTipoClasse().startsWith("java.") && !atributo.isEnum());
				atributo.setNome(field.getName());
				atributo.setRotulo(StringUtil.label(field.getName()));
				atributo.setCollection(field.getType().isArray() || field.getType() == List.class || field.getType() == Set.class || field.getType() == Map.class);
				ColumnDTO columnDTO = JPAUtil.getDto(classe, field);
				
				if(StringUtil.isNotBlank(columnDTO.getName())){
					atributo.setColuna(columnDTO.getName());
				} else {
					atributo.setColuna(field.getName());
				}
				
				atributo.setPk(columnDTO.isPk());
				atributo.setObrigatorio(!columnDTO.isNullable());
				atributo.setPrecisao(columnDTO.getPrecision());
				atributo.setTamanho(columnDTO.getLength());
				atributo.setTipo(field.getType().getSimpleName().toUpperCase());
				atributo.setEntidade(entidade);
				entidade.getAtributos().add(atributo);
			}
		}
		
		return aplicacao;
		
	}

}
