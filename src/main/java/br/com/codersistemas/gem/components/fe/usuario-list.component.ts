import { Router, ActivatedRoute } from '@angular/router';
import { UsuarioService } from '../usuario.service';
import { Component, OnInit } from '@angular/core';
import { Usuario } from '../usuario';
import { MessageService, ConfirmationService, SelectItem, LazyLoadEvent } from 'primeng/api';
import { Item } from 'src/app/models/dto/item';

@Component({
  selector: 'app-usuario-list',
  templateUrl: './usuario-list.component.html',
  styleUrls: ['./usuario-list.component.css']
})
export class UsuarioListComponent implements OnInit {

  usuarios!: Usuario[];
  usuario!: Usuario;
  totalRecords: number = 0;
  filters: Item[] = [];

//[declaracoes]

  constructor(
    private router: Router,
    private activatedRoute: ActivatedRoute,
    private messageService: MessageService,
    private confirmationService: ConfirmationService,
    private usuarioService: UsuarioService//[construtor]) { }

  ngOnInit() {
//[ngOnInit]
//[buscarFK]
//[buscarPorParametros]
  }

//[buscarFK2]

  consultarPaginado(event: LazyLoadEvent) {
    console.log(event);
    this.filters = this.formatFilters( event );
//[consultarPaginadoFKs]
    event.globalFilter = this.filters;
    console.log(this.filters);
    this.usuarioService.consultarPaginado(event).subscribe((resposta: any) => {
      console.log(resposta);
      this.usuarios = resposta.content as Usuario[];
      this.totalRecords = resposta.totalElements;
    }, (error : any) => {
      console.log(error);
      alert('erro condominios.' + error);
    });
  }
  
  formatFilters( event: LazyLoadEvent ): Item[] {
    const filterStrings: string[] = [];
    const itens: Item[] = [];
    for ( let prop in event.filters ) {
      let filterField: string = prop;
      let filterMeta = event.filters[filterField];
      if (Array.isArray(filterMeta)) {
        for (let meta of filterMeta) {
          if( meta.value !== null ) {
            const field: string = this.displayTitle( filterField );
            filterStrings.push( `${field} (${meta.matchMode}) ${meta.value}` );
            let it = new Item();
            it.field = field;
            it.matchMode = meta.matchMode;
            it.value = meta.value;
            itens.push(it);
          }
        }
      }
    }
    console.warn( filterStrings );
    return itens;
  }
  /*
  */
  displayTitle( s: string ) {
    return s.replace(/(^|[_-])([a-z])/g, (a, b, c) => c.toUpperCase())
      .replace(/([a-z])([A-Z])/g, (a, b, c) => `${b} ${c}`
  ) }
  

  consultar() {
    this.usuarioService.consultar().subscribe((resposta: Usuario[]) => {
      this.usuarios = resposta as Usuario[];
    }, (error: string) => {
      console.log(error);
      alert('erro usuarios.' + error);
    });
  }

  onSubmit(usuarioForm: any) {

  }

}
