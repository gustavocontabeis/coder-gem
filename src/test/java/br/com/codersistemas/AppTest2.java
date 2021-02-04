package br.com.codersistemas;

import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.junit.Before;
import org.junit.Test;

import br.com.codersistemas.gem.components.IComponent;
import br.com.codersistemas.gem.components.ResourceComponent;
import br.com.codersistemas.gem.components.TSClass;
import br.com.codersistemas.gem.components.be.AdapterComponent;
import br.com.codersistemas.gem.components.be.ControllerComponent;
import br.com.codersistemas.gem.components.be.DtoComponent;
import br.com.codersistemas.gem.components.be.HQLComponent;
import br.com.codersistemas.gem.components.be.PojoComponent;
import br.com.codersistemas.gem.components.be.RespositoryComponent;
import br.com.codersistemas.gem.components.be.SQLInsertComponent;
import br.com.codersistemas.gem.components.be.ServiceTestComponent;
import br.com.codersistemas.gem.components.fe.NgCli;
import br.com.codersistemas.gem.components.fe.NgComponentAdd;
import br.com.codersistemas.gem.components.fe.NgComponentHtml;
import br.com.codersistemas.gem.components.fe.NgComponentList;
import br.com.codersistemas.gem.components.fe.NgDialogHtml;
import br.com.codersistemas.gem.components.fe.NgFormularioHtml;
import br.com.codersistemas.gem.components.fe.NgHtmlFormAdd;
import br.com.codersistemas.gem.components.fe.NgModule;
import br.com.codersistemas.gem.components.fe.NgService;
import br.com.codersistemas.gem.components.fe.NgTabelaHtml;
import br.com.codersistemas.libs.dto.AplicacaoDTO;
import br.com.codersistemas.libs.dto.AtributoDTO;
import br.com.codersistemas.libs.dto.EntidadeDTO;
import br.com.codersistemas.libs.utils.StringUtil;

public class AppTest2 {

	int indexEntidade;
	private Class classe = null;
	private Class[] classes;
	private AplicacaoDTO appDTO;
	private EntidadeDTO entidadeDTO;
	private String json;
	private String appName;

	@Before
	public void antes() throws Exception {

//		classes = new Class[] {
//				Aplicacao.class,
//				Entidade.class,
//				Atributo.class,
//				Pessoa.class
//			};
//		
//		indexEntidade = 2;
//
//		classe = classes[indexEntidade];
//		appName = "coder-gem-ui";
		
		//----------------------
		
		//Catalogo Musical
//		classes = new Class[] {
//				br.com.codersistemas.catalogomusical.domain.Banda.class,
//				br.com.codersistemas.catalogomusical.domain.Album.class,
//				br.com.codersistemas.catalogomusical.domain.Paiz.class,
//				br.com.codersistemas.catalogomusical.domain.Artista.class,
//				br.com.codersistemas.catalogomusical.domain.Instrumento.class,
//				br.com.codersistemas.catalogomusical.domain.Musica.class
//			};
//		appName = "catalogo-musical";
		
//XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
		
//		//Blog
//		classes = new Class[] {
//				br.com.codersistemas.entity.Departamento.class,
//				br.com.codersistemas.entity.Post.class,
//				br.com.codersistemas.entity.Usuario.class,
//			};
//		
//		indexEntidade = 0;
//
//		classe = classes[indexEntidade];
//		appName = "coder-blog-ui-cad";

//XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
		
		//Blog
		classes = new Class[] {
				br.com.codersistemas.brunapereiraapi.domain.Parametro.class
			};
		
		indexEntidade = 0;

		classe = classes[indexEntidade];
		appName = "coder-blog-ui-cad";

		gerarAplicacaoDTO();

	}

	public void gerarAplicacaoDTO() throws Exception {

//		appDTO = gerarAplicacaoDTO("coder-gem-ui", AplicacaoDTO.class, EntidadeDTO.class, AtributoDTO.class);
//
//		List<EntidadeDTO> entidades = appDTO.getEntidades();
//		for (EntidadeDTO entidade : entidades) {
//			entidade.setAplicacao(null);
//			List<AtributoDTO> atributos = entidade.getAtributos();
//			for (AtributoDTO atributo : atributos) {
//				atributo.setEntidade(null);
//			}
//		}

		//Gson gson = new GsonBuilder().setPrettyPrinting().create();
		//json = gson.toJson(appDTO);

		appDTO = new AplicacaoDTO(appName, classes);
		for (EntidadeDTO a : appDTO.getEntidades()) {
			entidadeDTO = a;
		}

		entidadeDTO = appDTO.getEntidades().get(indexEntidade);

	}

	@Test
	public void exibirAplicacaoDTO() throws Exception {
		System.out.println("getPacoteBackend: "+appDTO.getPacoteBackend());
		System.out.println("getNome: "+appDTO.getNome());
		for (EntidadeDTO entidadeDTO : appDTO.getEntidades()) {
			System.out.println("	getClasse: "+entidadeDTO.getClasse());
			System.out.println("	getNome: "+entidadeDTO.getNome());
			System.out.println("	getNomeCapitalizado: "+entidadeDTO.getNomeCapitalizado());
			System.out.println("	getNomeClasse: "+entidadeDTO.getNomeClasse());
			System.out.println("	getNomeInstancia: "+entidadeDTO.getNomeInstancia());
			System.out.println("	getRestURI: "+entidadeDTO.getRestURI());
			System.out.println("	getRotulo: "+entidadeDTO.getRotulo());
			System.out.println("	getTabela: "+entidadeDTO.getTabela());
			List<AtributoDTO> atributos = entidadeDTO.getAtributos();
			for (AtributoDTO atributo : atributos) {
				System.out.println("		getNome: "+atributo.getNome());
				System.out.println("		getColuna: "+atributo.getColuna());
				System.out.println("		getFkField: "+atributo.getFkField());
				System.out.println("		getNomeCapitalizado: "+atributo.getNomeCapitalizado());
				System.out.println("		getNomeInstancia: "+atributo.getNomeInstancia());
				System.out.println("		getNomeLista: "+atributo.getNomeLista());
				System.out.println("		getPrecisao: "+atributo.getPrecisao());
				System.out.println("		getRotulo: "+atributo.getRotulo());
				System.out.println("		getTamanho: "+atributo.getTamanho());
				System.out.println("		getTipo: "+atributo.getTipo());
				System.out.println("		getTipoClasse: "+atributo.getTipoClasse());
				System.out.println("		getTipoClasseGenerica: "+atributo.getTipoClasseGenerica());
				System.out.println("		getTipoClasseGenericaNome: "+atributo.getTipoClasseGenericaNome());
				System.out.println("		getClasse: "+atributo.getClasse());
				System.out.println("		getEnumaracao: "+atributo.getEnumaracao());
				System.out.println("		-------------------------");
			}
			System.out.println("	-----------------------------------------");
		}
	}

	@Test
	public void gerarJson() throws Exception {
		System.out.println(json);
	}

	@Test
	public void gerarPojo() {
		IComponent component = new PojoComponent(classe);
		System.out.println(component.print());
	}

	@Test
	public void gerarDDLPostgres() {
		//CREATE TABLE
		//CREATE SEQUENCE
		//CONSTRAINTS, FK, UNIQUE, ETC
	}

	@Test
	public void gerarDTO() {
		IComponent component = new DtoComponent(classe);
		System.out.println(component.print());
	}

	@Test
	public void gerarAdapter() {
		IComponent component = new AdapterComponent(entidadeDTO);
		System.out.println(component.print());
	}

	@Test
	public void gerarSQLInserts() {
		String print = new SQLInsertComponent(entidadeDTO).print();
		System.out.println(print);

	}

	@Test
	public void gerarRepository() {
		ResourceComponent component = new RespositoryComponent(entidadeDTO);
		System.out.println(component.print());
	}

	@Test
	public void gerarRepositoryTest() {

	}

	@Test
	public void gerarService() {
	}

	@Test
	public void gerarServiceTest() {
		IComponent component = new ServiceTestComponent(classe);
		System.out.println(component.print());
	}

	@Test
	public void gerarSpecification() {
		gerarSpecification(entidadeDTO);
	}

	@Test
	public void gerarRestController() {
		ResourceComponent component = new ControllerComponent(entidadeDTO);
		System.out.println(component.print());
	}

	@Test
	public void gerarRestControllerTestes() {
		// gerar URL
		// Gerar body post
	}

	@Test
	public void gerarAngularCliCode() {
		new NgCli(appDTO).print();
	}

	@Test
	public void gerarTSClass() {
		System.out.println(StringUtil.toUnderlineCase(entidadeDTO.getNome()).replace("_", "-") + ".ts");
		TSClass tsClass = new TSClass(entidadeDTO);
		System.out.println(tsClass.print());
	}

	@Test
	public void gerarNGModule() throws Exception {
		IComponent controller = new NgModule(entidadeDTO);
		System.out.println(controller.print());
	}

	@Test
	public void gerarNGService() throws Exception {
		NgService controller = new NgService(entidadeDTO);
		System.out.println(controller.print());
	}

	@Test
	public void gerarNGComponentAdd() throws Exception {
		IComponent controller = new NgComponentAdd(entidadeDTO);
		System.out.println(controller.print());
	}

	@Test
	public void gerarNGComponentList() throws Exception {
		IComponent controller = new NgComponentList(entidadeDTO);
		System.out.println(controller.print());
	}

	@Test
	public void gerarFormulario() throws Exception {
		NgComponentHtml ngHtmlCrud = new NgComponentHtml(classe);
		System.out.println(ngHtmlCrud.print());
	}

	@Test
	public void gerarFormularioAdd() throws Exception {
		IComponent ngHtmlCrud = new NgHtmlFormAdd(entidadeDTO);
		System.out.println(ngHtmlCrud.print());
	}

	@Test
	public void gerarTabela() throws Exception {
		IComponent ngHtmlCrud = new NgTabelaHtml(entidadeDTO);
		String print = ngHtmlCrud.print();
		System.out.println(print);
	}

	@Test
	public void gerarCampos() throws Exception {
		IComponent ngHtmlCrud = new NgFormularioHtml(entidadeDTO);
		System.out.println(ngHtmlCrud.print());
	}

	@Test
	public void gerarDialog() throws Exception {
		NgDialogHtml obj = new NgDialogHtml(classe);
		obj.setHeaderText("OK");
		obj.setExibirDialog("exibirDialog");
		System.out.println(obj.print());
	}

	/**
	 * https://www.baeldung.com/java-with-jsoup
	 */
	@Test
	public void alterarFormulario() throws Exception {

		List<String> readAllLines = Files.readAllLines(
				Paths.get(this.getClass().getResource("post.component.html").toURI()), Charset.defaultCharset());
		StringBuilder sb = new StringBuilder();
		readAllLines.stream().forEach(s -> sb.append(s + "\n"));
		String text = sb.toString();

		Document document = Jsoup.parse(text);
		Elements titulo = document.select(".titulo");
		text = text.replace(titulo.outerHtml(), "[" + titulo.outerHtml() + "]");
		System.out.println(text);
	}

	@Test
	public void gerarHQL() throws Exception {

		String str = "Contrato contato = new Contrato();\n";
		str += "Empreendimento empreendimento = contato.empreendimento\n";
		str += "Unidade unidade = empreendimento.unidade\n";
		str += "\n";
		str += "unidade.cidade\n";
		str += "contato.diasAtraso;";

		IComponent component = new HQLComponent(str);
		System.out.println(component.print());
		System.out.println("-----------------------------");

		// String s = "Empreendimento empreendimento = contato.empreendimento";
		// String s = "Empreendimento empreendimento = contato.empreendimento";
		// System.out.println(s.matches("\\w*.? \\w*.? = \\w*.?\\.\\w*.?"));

	}

	private void gerarSpecification(EntidadeDTO entidadeDTO) {

		System.out.println("");
		List<AtributoDTO> atributos = entidadeDTO.getAtributos();
		for (AtributoDTO atributo : atributos) {
			if (atributo.isFk()) {
				System.out.println("Join<Object, Object> join" + StringUtil.capitalize(atributo.getNome())
						+ " = root.join(\"" + atributo.getNome() + "\");");
			}
		}

		System.out.println("");
		System.out.println("List<Predicate> predicates = new ArrayList<>();");

		for (AtributoDTO atributo : atributos) {
			if (!atributo.isFk()) {
				System.out.println("");
				System.out.println("if(filter.get" + StringUtil.capitalize(atributo.getNome()) + "() != null)){");
				System.out.println("   predicates.add(cb.equal(" + atributo.getEntidade().getNomeInstancia() + ".get(\""
						+ atributo.getNome() + "\"), filter.get" + StringUtil.capitalize(atributo.getNome()) + "()));");
				System.out.println("}");
			}
		}

	}

}
