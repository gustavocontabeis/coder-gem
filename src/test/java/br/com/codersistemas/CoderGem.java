package br.com.codersistemas;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

import javax.persistence.Entity;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.junit.Before;
import org.junit.Test;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.FileTemplateResolver;
import org.thymeleaf.templateresolver.StringTemplateResolver;

import br.com.codersistemas.gem.components.IComponent;
import br.com.codersistemas.gem.components.ResourceComponent;
import br.com.codersistemas.gem.components.TSClass;
import br.com.codersistemas.gem.components.be.AdapterComponent;
import br.com.codersistemas.gem.components.be.ControllerComponent;
import br.com.codersistemas.gem.components.be.DtoComponent;
import br.com.codersistemas.gem.components.be.HQLComponent;
import br.com.codersistemas.gem.components.be.PojoComponent;
import br.com.codersistemas.gem.components.be.RepositoryComponent;
import br.com.codersistemas.gem.components.be.SQLInsertComponent;
import br.com.codersistemas.gem.components.be.ServiceComponent;
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
import br.com.codersistemas.libs.utils.FileUtil;
import br.com.codersistemas.libs.utils.ReflectionUtils;
import br.com.codersistemas.libs.utils.StringUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CoderGem {

	int indexEntidade;
	private Class classe = null;
	private Class[] classes;
	private AplicacaoDTO appDTO;
	private EntidadeDTO entidadeDTO;
	private String json;
	private String appName;

	@Before
	public void antes() throws Exception {

		// condominio-adm
		classes = new Class[] { 
				br.com.codersistemas.condominiosadm.domain.Condominio.class, // 0
				br.com.codersistemas.condominiosadm.domain.Pessoa.class, // 1
				br.com.codersistemas.condominiosadm.domain.Sindico.class, // 2
				br.com.codersistemas.condominiosadm.domain.Bloco.class, // 3
				br.com.codersistemas.condominiosadm.domain.Apartamento.class, // 4
				br.com.codersistemas.condominiosadm.domain.Morador.class, // 5
				br.com.codersistemas.condominiosadm.domain.Faturamento.class, // 6
				br.com.codersistemas.condominiosadm.domain.Boleto.class, // 7
				br.com.codersistemas.condominiosadm.domain.CentroDeCusto.class, // 8
				br.com.codersistemas.condominiosadm.domain.Caixa.class, // 9
				br.com.codersistemas.condominiosadm.domain.Banco.class, // 10
				br.com.codersistemas.condominiosadm.domain.BancoLancamento.class, // 11
				br.com.codersistemas.condominiosadm.domain.Garagem.class,// 12
		};

		indexEntidade = 7;

		classe = classes[indexEntidade];
		appName = "coder-blog-ui-cad";

		gerarAplicacaoDTO();

	}

	@Test
	public void orderClasses() throws Exception {

		List<Class> entidadesOrder = new ArrayList<>();

		List<EntidadeDTO> entidades = appDTO.getEntidades();
		do {

			Iterator<EntidadeDTO> iterator = entidades.iterator();
			while (iterator.hasNext()) {
				EntidadeDTO entidadeDTO1 = (EntidadeDTO) iterator.next();

				log.info("{}", entidadeDTO1.getClasse());
				boolean test = false;
				boolean containsFK = false;

				Class cc = null;
				for (AtributoDTO atributo : entidadeDTO1.getAtributos()) {
					cc = atributo.getClasse();
					log.info("   -{} {}", atributo.getNome(), cc);

					Annotation annotationEntity = atributo.getClasse().getAnnotation(Entity.class);
					if (annotationEntity != null && !entidadesOrder.contains(cc)) {
						log.info("    FK1-{} {}", annotationEntity != null, !entidadesOrder.contains(cc));
						containsFK = true;
						break;
					}

					if (atributo.isCollection()) {

						log.info("    +{} {} {}", atributo.getTipoClasse(), atributo.getTipoClasseGenerica(), atributo.getTipoClasseGenericaNome());

						cc = Class.forName(atributo.getTipoClasseGenerica());
						annotationEntity = cc.getAnnotation(Entity.class);

						if (annotationEntity != null && !entidadesOrder.contains(cc)) {
							log.info("    FK2-{} {}", annotationEntity != null, !entidadesOrder.contains(cc));
							containsFK = true;
							break;
						}

					}

					log.info("    > FK? {}", containsFK ? "Sim" : "Nao");

				}

				if (!containsFK) {
					log.info("    > adicionando: {}", entidadeDTO1.getClasse());
					entidadesOrder.add(entidadeDTO1.getClasse());
					// entidades.remove(entidadeDTO1);
					log.info("    > removendo antes: {}", entidades.size());
					iterator.remove();
					log.info("    > removendo depois: {}", entidades.size());
				} else {
					log.info("    > Não adicionado.");
				}

			}
		} while (!entidades.isEmpty());

		log.info("---------------------------");
		for (Class c : entidadesOrder) {
			log.info("{}", c);
		}
	}

	@Test
	public void xxx() throws Exception {
		System.out.println("-------------------------------------------------------------------");
		gerarPojo();
		System.out.println("-------------------------------------------------------------------");
		gerarRepository();
		System.out.println("-------------------------------------------------------------------");
		gerarRestController();
		System.out.println("-------------------------------------------------------------------");
	}

	public void gerarAplicacaoDTO() throws Exception {

		// Gson gson = new GsonBuilder().setPrettyPrinting().create();
		// json = gson.toJson(appDTO);

		appDTO = new AplicacaoDTO(appName, classes);

		entidadeDTO = appDTO.getEntidades().get(indexEntidade);

	}

	@Test
	public void exibirAplicacaoDTO() throws Exception {
		System.out.println("getPacoteBackend: " + appDTO.getPacoteBackend());
		System.out.println("getNome: " + appDTO.getNome());
		for (EntidadeDTO entidadeDTO : appDTO.getEntidades()) {
			System.out.println("	getClasse: " + entidadeDTO.getClasse());
			System.out.println("	getNome: " + entidadeDTO.getNome());
			System.out.println("	getNomeCapitalizado: " + entidadeDTO.getNomeCapitalizado());
			System.out.println("	getNomeClasse: " + entidadeDTO.getNomeClasse());
			System.out.println("	getNomeInstancia: " + entidadeDTO.getNomeInstancia());
			System.out.println("	getRestURI: " + entidadeDTO.getRestURI());
			System.out.println("	getRotulo: " + entidadeDTO.getRotulo());
			System.out.println("	getTabela: " + entidadeDTO.getTabela());
			List<AtributoDTO> atributos = entidadeDTO.getAtributos();
			for (AtributoDTO atributo : atributos) {
				System.out.println("		getNome: " + atributo.getNome());
				System.out.println("		getColuna: " + atributo.getColuna());
				System.out.println("		getFkField: " + atributo.getFkField());
				System.out.println("		getNomeCapitalizado: " + atributo.getNomeCapitalizado());
				System.out.println("		getNomeInstancia: " + atributo.getNomeInstancia());
				System.out.println("		getNomeLista: " + atributo.getNomeLista());
				System.out.println("		getPrecisao: " + atributo.getPrecisao());
				System.out.println("		getRotulo: " + atributo.getRotulo());
				System.out.println("		getTamanho: " + atributo.getTamanho());
				System.out.println("		getTipo: " + atributo.getTipo());
				System.out.println("		getTipoClasse: " + atributo.getTipoClasse());
				System.out.println("		getTipoClasseGenerica: " + atributo.getTipoClasseGenerica());
				System.out.println("		getTipoClasseGenericaNome: " + atributo.getTipoClasseGenericaNome());
				System.out.println("		getClasse: " + atributo.getClasse());
				System.out.println("		getEnumaracao: " + atributo.getEnumaracao());
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

		try {

			indexEntidade = 4;
			entidadeDTO = appDTO.getEntidades().get(indexEntidade);
			classe = entidadeDTO.getClasse();

			IComponent component = new PojoComponent(classe);
			System.out.println(component.print());
			
//			File file = new File("/home/gustavo/dev/workspace-coder/condominios-adm-api/src/main/java/br/com/codersistemas/condominiosadm/domain/"+classe.getSimpleName()+"2.java");
//			file.createNewFile();
//			FileUtil.write(file, component.print());

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void gerarDDLPostgres() {
		// CREATE TABLE
		// CREATE SEQUENCE
		// CONSTRAINTS, FK, UNIQUE, ETC
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
		ResourceComponent component = new RepositoryComponent(entidadeDTO);
		System.out.println(component.print());
	}

	@Test
	public void gerarRepositoryTest() {

	}

	@Test
	public void gerarService() {
		
//		indexEntidade = 7;
//		entidadeDTO = appDTO.getEntidades().get(indexEntidade);

		ResourceComponent component = new ServiceComponent(entidadeDTO);
		System.out.println(component.print());
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
	public void gerarTSClass() throws Exception {

		for (int i = 0; i < classes.length; i++) {
			
			indexEntidade = i;
			entidadeDTO = appDTO.getEntidades().get(indexEntidade);
			classe = entidadeDTO.getClasse();
			
			System.out.println("ng generate class " + entidadeDTO.getNomeHyphenCase() + "/" + entidadeDTO.getNomeHyphenCase() + " --skipTests=true");
		}

		System.out.println("");

		for (int i = 0; i < classes.length; i++) {
			
			indexEntidade = i;
			entidadeDTO = appDTO.getEntidades().get(indexEntidade);
			classe = entidadeDTO.getClasse();

			System.out.println(StringUtil.toUnderlineCase(entidadeDTO.getNome()).replace("_", "-") + ".ts");
			TSClass tsClass = new TSClass(entidadeDTO);
			System.out.println(tsClass.print());
		}

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
	public void gerarNGComponentList() throws Exception {
		IComponent controller = new NgComponentList(entidadeDTO);
		System.out.println(controller.print());
	}

	@Test
	public void gerarTabela() throws Exception {
		IComponent ngHtmlCrud = new NgTabelaHtml(entidadeDTO);
		String print = ngHtmlCrud.print();
		System.out.println(print);
	}

	@Test
	public void gerarNGComponentAdd() throws Exception {
		IComponent controller = new NgComponentAdd(entidadeDTO);
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

	@Test
	public void gerarMenu() throws Exception {
		List<EntidadeDTO> entidades = appDTO.getEntidades();
		
		
		StringBuilder sb = new StringBuilder();
		sb.append("this.items = [\n");
		for (EntidadeDTO entidadeDTO : entidades) {
			
			String x = "";
			x += String.format("   {label: 'Listar %s', routerLink: '%s/%s-list' , icon: 'pi pi-fw pi-list'},\n", 
					entidadeDTO.getNomeCapitalizado(),
					entidadeDTO.getNomeHyphenCase(), 
					entidadeDTO.getNomeHyphenCase()
				);
			x += String.format("   {label: 'Cadastrar %s', routerLink: '%s/%s-add' , icon: 'pi pi-fw pi-plus'},\n", 
					entidadeDTO.getNomeCapitalizado(),
					entidadeDTO.getNomeHyphenCase(), 
					entidadeDTO.getNomeHyphenCase()
				);
			
			sb.append(String.format("{label: '%s', icon: 'pi pi-fw pi-list', items: [\n%s]},\n", 
					entidadeDTO.getNomeCapitalizado(), x));
			
		}
		sb.append("];");
		System.out.println(sb.toString());
	}

	/**
	 * https://www.baeldung.com/java-with-jsoup
	 */
	@Test
	public void alterarFormulario() throws Exception {

		List<String> readAllLines = Files.readAllLines(Paths.get(this.getClass().getResource("post.component.html").toURI()), Charset.defaultCharset());
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
				System.out.println("Join<Object, Object> join" + StringUtil.capitalize(atributo.getNome()) + " = root.join(\"" + atributo.getNome() + "\");");
			}
		}

		System.out.println("");
		System.out.println("List<Predicate> predicates = new ArrayList<>();");

		for (AtributoDTO atributo : atributos) {
			if (!atributo.isFk()) {
				System.out.println("");
				System.out.println("if(filter.get" + StringUtil.capitalize(atributo.getNome()) + "() != null)){");
				System.out.println("   predicates.add(cb.equal(" + atributo.getEntidade().getNomeInstancia() + ".get(\"" + atributo.getNome() + "\"), filter.get" + StringUtil.capitalize(atributo.getNome()) + "()));");
				System.out.println("}");
			}
		}

	}

	@Test
	public void asJavaClass() throws IOException {

		String fileContent = FileUtil.readResource(this, "meutemplate.txt");

		StringTemplateResolver templateResolver = new StringTemplateResolver();
		templateResolver.setOrder(1);
		templateResolver.setTemplateMode(TemplateMode.TEXT);
		templateResolver.setCacheable(false);
		TemplateEngine templateEngine = new TemplateEngine();
		templateEngine.setTemplateResolver(templateResolver);

		Context context = new Context();

		context.setVariable("Pessoa", "Cliente");
		context.setVariable("pessoa", "cliente");
		context.setVariable("Pessoas", "Clientes");
		context.setVariable("pessoas", "clientes");

		context.setVariable("items", new String[] { "Telefone", "Endereco" });

		String process = templateEngine.process(fileContent, context);
		System.out.println(process);

	}

}
