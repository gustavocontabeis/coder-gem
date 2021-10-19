package br.com.codersistemas.gem.components.fe;

import br.com.codersistemas.gem.components.IComponent;
import br.com.codersistemas.libs.dto.AplicacaoDTO;
import br.com.codersistemas.libs.dto.EntidadeDTO;

public class NgCli implements IComponent {
	
	private AplicacaoDTO aplicacaoDTO;
	
	public NgCli(AplicacaoDTO aplicacaoDTO) {
		this.aplicacaoDTO = aplicacaoDTO;
	}

	@Override
	public String print() {
		System.out.println("ng new "+aplicacaoDTO.getNome()+" --routing=true");
		System.out.println("cd "+aplicacaoDTO.getNome()+"/src/app/");
		System.out.println("ng build --prod");
		System.out.println("ng serve --open");
		System.out.println("git status");
		System.out.println("git add .");
		System.out.println("git commit -m \"Primeiro commit\"");
		System.out.println("# adicione o repositório remoto");
		System.out.println("git push github master --force");
		System.out.println("	sudo npm install primeng --save");
		System.out.println("	sudo npm install primeicons --save");
		System.out.println("	sudo npm install @angular/animations --save");
		System.out.println("	sudo npm install @angular/cdk --save");
		System.out.println("	sudo npm install primeflex --save");
		System.out.println("	sudo npm install rxjs-compat --save");
		System.out.println("#	Adicione os css... faça conforme a documentação!");
		System.out.println("ng build --prod");
		System.out.println("ng serve --open");
		System.out.println("git status");
		System.out.println("git add .");
		System.out.println("git commit -m \"Adicionado o PrimeNG\"");
		System.out.println("# ------------------------------------------------------------");
		System.out.println("# GERANDO O PRIMEIRO COMPONENT:");
		System.out.println("# ------------------------------------------------------------");
		System.out.println("ng generate component home --skipTests=true");
		System.out.println("# 	em app-routing.module.ts");
		System.out.println("		import { HomeComponent } from './home/home.component';");
		System.out.println("		const routes: Routes = [");
		System.out.println("		  { path: 'home', component: HomeComponent }");
		System.out.println("		];");
		System.out.println("		");
		System.out.println("	Ajuste os imports");
		System.out.println("		em app.component.html adicione:");
		System.out.println("		<nav>");
		System.out.println("			<a routerLink=\"home\">Home Component</a> | ");
		System.out.println("		</nav>");
		System.out.println("	ng serve --open");
		System.out.println("	Teste em:");
		System.out.println("		http://localhost:4200/home");
		System.out.println("configurando acesso http:");
		System.out.println("no AppModule  adicionar HttpClientModule");
		System.out.println("	No app.module.ts adicione os componentes do primeng");
		System.out.println("	");
		System.out.println("		import { HomeComponent } from './home/home.component';");
		System.out.println("		import { BrowserAnimationsModule } from '@angular/platform-browser/animations'; //+++");
		System.out.println("		import { PanelModule } from 'node_modules/primeng/panel';	//+++");
		System.out.println("		imports: [");
		System.out.println("			BrowserModule,");
		System.out.println("			AppRoutingModule,");
		System.out.println("			BrowserAnimationsModule, //+++");
		System.out.println("			PanelModule	//+++");
		System.out.println("		],");
		System.out.println("	");
		System.out.println("	No home.component.html adicione as tags que exibitão o painel");
		System.out.println("	");
		System.out.println("		<p-panel header=\"Home\">");
		System.out.println("			Home works!");
		System.out.println("		</p-panel>");
		System.out.println("		");
		System.out.println("	Teste em:");
		System.out.println("		ng s");
		System.out.println("		e teste em http://localhost:4200/home");
		System.out.println("		");
		System.out.println("	Yeah!!!");
		System.out.println("# Em environment.ts");
		System.out.println("	apiUrl: 'http://localhost:8084/coder-gem'");
		System.out.println("------------------------------------------------------------");
		System.out.println("CRUD BÁSICO:");
		System.out.println("------------------------------------------------------------");
		for(EntidadeDTO entidade : aplicacaoDTO.getEntidades()) {
			System.out.println("ng generate module "+entidade.getNome()+" --routing");
			System.out.println("#Verifique ou adicione caso necessário o PessoaModule no imports do AppModule");
			System.out.println("ng generate component "+entidade.getNomeHyphenCase()+"/"+entidade.getNomeHyphenCase()+"-add --module="+entidade.getNomeHyphenCase()+" --skipTests=true");
			System.out.println("ng generate component "+entidade.getNomeHyphenCase()+"/"+entidade.getNomeHyphenCase()+"-filter --module="+entidade.getNomeHyphenCase()+" --skipTests=true");
			System.out.println("ng generate component "+entidade.getNomeHyphenCase()+"/"+entidade.getNomeHyphenCase()+"-list --module="+entidade.getNomeHyphenCase()+" --skipTests=true");
			System.out.println("ng generate service "+entidade.getNomeHyphenCase()+"/"+entidade.getNomeHyphenCase()+" --skipTests=true");
			System.out.println("ng generate class "+entidade.getNomeHyphenCase()+"/"+entidade.getNomeHyphenCase()+" --skipTests=true");
			System.out.println("#Verifique ou adicione caso necessário o AddComponent, FilterComponent e ListComponent no declarations do "+entidade.getNomeCapitalizado()+"Module");
		}
		System.out.println("ng build --prod");
		System.out.println("ng serve --open");
		System.out.println("Em AppRoutingModule Atualize as rotas");
		System.out.println("const routes: Routes = [");
		System.out.println("  { path: 'home', component: HomeComponent },");
		for(EntidadeDTO entidade : aplicacaoDTO.getEntidades()) {
			//System.out.println("  { path: '"+entidade.getNomeInstancia()+"', loadChildren: './"+entidade.getNomeInstancia()+"/"+entidade.getNomeInstancia()+".module#"+entidade.getNomeCapitalizado()+"Module'},");
			System.out.println("  { path: '"+entidade.getNomeHyphenCase()+"', loadChildren: () => import('./"+entidade.getNomeHyphenCase()+"/"+entidade.getNomeHyphenCase()+".module').then(m => m."+entidade.getNome()+"Module)},");
		}
		System.out.println("  { path: '', redirectTo: 'home', pathMatch: 'full'},");
		System.out.println("];");
		
		for(EntidadeDTO entidade : aplicacaoDTO.getEntidades()) {
			System.out.println("Em "+entidade.getNomeInstancia()+"-routing.module.ts Atualize as rotas");
			System.out.println("  { path: '"+entidade.getNomeHyphenCase()+"-add', component: "+entidade.getNomeCapitalizado()+"AddComponent },");
			System.out.println("  { path: '"+entidade.getNomeHyphenCase()+"-add/:id', component: "+entidade.getNomeCapitalizado()+"AddComponent },");
			System.out.println("  { path: '"+entidade.getNomeHyphenCase()+"-filter', component: "+entidade.getNomeCapitalizado()+"FilterComponent },");
			System.out.println("  { path: '"+entidade.getNomeHyphenCase()+"-list', component: "+entidade.getNomeCapitalizado()+"ListComponent },");
			entidade.getAtributos().stream().filter(i -> i.isFk() && !i.isCollection()).forEach(i -> {
				System.out.println("  { path: '"+i.getNome()+"/:id_"+i.getNome()+"', component: "+entidade.getNomeCapitalizado()+"ListComponent },");
			});
			System.out.println("  { path: ':id', component: "+entidade.getNomeCapitalizado()+"ListComponent },");
		}
		
		System.out.println("Atualizar o menu em app.component.html");
		System.out.println("		<nav>");
		System.out.println("			<a routerLink=\"home\">Home</a> |");
		for(EntidadeDTO entidade : aplicacaoDTO.getEntidades()) {
			System.out.println("			<a routerLink=\""+entidade.getNomeHyphenCase()+"/"+entidade.getNomeHyphenCase()+"-add\">"+entidade.getNomeInstancia()+" add</a> |");
			System.out.println("			<a routerLink=\""+entidade.getNomeHyphenCase()+"/"+entidade.getNomeHyphenCase()+"-filter\">"+entidade.getNomeInstancia()+" filter</a> |");
			System.out.println("			<a routerLink=\""+entidade.getNomeHyphenCase()+"/"+entidade.getNomeHyphenCase()+"-list\">"+entidade.getNomeInstancia()+" list</a> |");
		}
		System.out.println("		</nav>	");
		System.out.println("Em app-routing.module.ts ...");
		System.out.println("			  {path: '', redirectTo: 'home', pathMatch: 'full'},");
		System.out.println("			  {path: 'home', component: HomeComponent},");
		for(EntidadeDTO entidade : aplicacaoDTO.getEntidades()) {
			System.out.println("			  {path: '"+entidade.getNomeInstancia()+"', loadChildren: './"+entidade.getNomeHyphenCase()+"/"+entidade.getNomeHyphenCase()+".module#"+entidade.getNome()+"Module'}");
		}
		System.out.println("Criando a service:");
		System.out.println("ng generate service pessoa/pessoa --skipTests=true");
		System.out.println("verifique/adicione o service em providers no module");
		System.out.println("------------------------------------------------------------");
		System.out.println("");
		System.out.println("------------------------------------------------------------");
		System.out.println("	http://kazale.com/angular-2-modulos-ngmodule/");
		System.out.println("	https://medium.com/rocketseat/implementando-lazy-loading-no-angular-d8a6541c0580");
		System.out.println("	ng new lazy-app --routing");
		System.out.println("	cd .\\lazy-app\\");
		System.out.println("	ng generate component home");
		System.out.println("	ng generate module lazy --routing");
		System.out.println("		adiciona rotas em AppRoutingModule...");
		System.out.println("			const routes: Routes = [");
		System.out.println("			  {path: '', redirectTo: 'home', pathMatch: 'full'},");
		System.out.println("			  {path: 'home', component: HomeComponent},");
		System.out.println("			  {path: 'lazy', loadChildren: './lazy/lazy.module#LazyModule'}");
		System.out.println("			];");
		System.out.println("	em app.component.html adicione:");
		System.out.println("		<nav>");
		System.out.println("			<a routerLink=\"home\">Home Component</a> | ");
		System.out.println("			<a routerLink=\"lazy\">Lazy Module</a>");
		System.out.println("		</nav>");
		System.out.println("	cd src/app/lazy");
		System.out.println("	ng generate component sobre");
		System.out.println("	cd ../../..");
		System.out.println("	ng serve");
		System.out.println("	cd src/app/lazy");
		System.out.println("	ng generate component add");
		System.out.println("	ng generate component edit --routing");
		System.out.println("	ng generate component delete");
		System.out.println("	ng generate component list");
		System.out.println("		adiciona rotas em LazyRoutingModule...");
		System.out.println("			const routes: Routes = [");
		System.out.println("			  {path: '', component: SobreComponent},");
		System.out.println("			  {path: 'add', component: AddComponent},");
		System.out.println("			  {path: 'edit', component: EditComponent},");
		System.out.println("			  {path: 'delete', component: DeleteComponent},");
		System.out.println("			  {path: 'list', component: ListComponent},");
		System.out.println("			];");
		System.out.println("	em sobre.component.html coloque");
		System.out.println("		<nav>");
		System.out.println("		  <a routerLink=\"add\">add</a> | ");
		System.out.println("		  <a routerLink=\"edit\">edit</a> | ");
		System.out.println("		  <a routerLink=\"delete\">delete</a> | ");
		System.out.println("		  <a routerLink=\"list\">list</a> | ");
		System.out.println("		</nav>");
		System.out.println("	cd ../../..");
		System.out.println("	ng s");
		System.out.println("	");
		System.out.println("	ou ...");
		System.out.println("		ng g c pessoa/sobre --module=pessoa --skipTests=true");
		System.out.println("		ng g c pessoa/add --module=pessoa --skipTests=true");
		System.out.println("		ng g c pessoa/edit --module=pessoa --skipTests=true");
		System.out.println("		ng g c pessoa/delete --module=pessoa --skipTests=true");
		System.out.println("		ng g c pessoa/list --module=pessoa --skipTests=true");
		System.out.println("		ng g class pessoa/pessoa --skipTests=true");
		System.out.println("		ng g service pessoa/pessoa --skipTests=true");
		return null;
	}

}
