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

import br.com.codersistemas.codergemapi.domain.Aplicacao;
import br.com.codersistemas.codergemapi.domain.Atributo;
import br.com.codersistemas.codergemapi.domain.Entidade;
import br.com.codersistemas.codergemapi.domain.Pessoa;
import br.com.codersistemas.gem.components.IComponent;
import br.com.codersistemas.gem.components.Replacememnt;
import br.com.codersistemas.gem.components.ResourceComponent;
import br.com.codersistemas.gem.components.TSClass;
import br.com.codersistemas.gem.components.be.ControllerComponent;
import br.com.codersistemas.gem.components.be.HQLComponent;
import br.com.codersistemas.gem.components.be.PojoComponent;
import br.com.codersistemas.gem.components.be.RespositoryComponent;
import br.com.codersistemas.gem.components.be.SQLInsertComponent;
import br.com.codersistemas.gem.components.fe.NgCli;
import br.com.codersistemas.gem.components.fe.NgComponent;
import br.com.codersistemas.gem.components.fe.NgComponentHtml;
import br.com.codersistemas.gem.components.fe.NgDialogHtml;
import br.com.codersistemas.gem.components.fe.NgFormularioHtml;
import br.com.codersistemas.gem.components.fe.NgHtmlFormAdd;
import br.com.codersistemas.gem.components.fe.NgModule;
import br.com.codersistemas.gem.components.fe.NgService;
import br.com.codersistemas.gem.components.fe.NgTabelaHtml;
import br.com.codersistemas.libs.dto.AplicacaoDTO;
import br.com.codersistemas.libs.dto.AtributoDTO;
import br.com.codersistemas.libs.dto.EntidadeDTO;
import br.com.codersistemas.libs.utils.ReflectionUtils;
import br.com.codersistemas.libs.utils.StringUtil;

public class AppTest2 {

	private Class classe = null;
	private Class[] classes;
	private Replacememnt r;
	private AplicacaoDTO appDTO;
	private EntidadeDTO entidadeDTO;
	private String json;
	private String appName;
	private Object obj;

	@Before
	public void antes() throws Exception {

		classe = br.com.codersistemas.codergemapi.domain.Aplicacao.class;
		//classe = br.gov.caixa.pedes.sistemas.siarr.domain.Termo.class;
		classes = new Class[] {Aplicacao.class, Entidade.class, Atributo.class, Pessoa.class};
		appName = "coder-gem-ui";

		r = Replacememnt.builder().addClass(classe).build();

		gerarAplicacaoDTO();

		obj = ReflectionUtils.newInstance(classe);

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
		
		entidadeDTO = appDTO.getEntidades().get(1);

	}

	@Test
	public void gerarJson() throws Exception {
		System.out.println(json);
	}

	@Test
	public void gerarPojo() {
		PojoComponent component = new PojoComponent(obj);
		System.out.println(component.print());
	}

	@Test
	public void gerarSQLInserts() {
		String print = new SQLInsertComponent(entidadeDTO).print();
		System.out.println(print);

	}

	@Test
	public void gerarRepository() {
		ResourceComponent component = new RespositoryComponent(r);
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
	public void gerarNGComponent() throws Exception {
		NgComponent controller = new NgComponent(entidadeDTO);
		System.out.println(controller.print());
	}

	@Test
	public void gerarFormulario() throws Exception {
		NgComponentHtml ngHtmlCrud = new NgComponentHtml(obj, r);
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
