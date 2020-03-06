import { Router, ActivatedRoute } from '@angular/router';
import { UsuarioService } from '../usuario.service';
import { Component, OnInit } from '@angular/core';
import { Usuario } from '../usuario';
import { MessageService, ConfirmationService, SelectItem } from 'primeng/api';
import {ConfirmDialogModule} from 'primeng/confirmdialog';

@Component({
  selector: 'app-usuario',
  templateUrl: './usuario.component.html',
  styleUrls: ['./usuario.component.css']
})
export class UsuarioComponent implements OnInit {

  usuario: Usuario = new Usuario();
  usuarios: Usuario[];
  exibirDialog: boolean;
  novoRegistro: boolean;
  
  //[declaracoes]

  constructor(
    private router: Router,
    private activatedRoute: ActivatedRoute,
    private messageService: MessageService,
    private confirmationService: ConfirmationService,
    private usuarioService: UsuarioService //[construtor]) { }

  ngOnInit() {
    this.exibirDialog = false;
    this.novoRegistro = false;
    this.consultar();
    this.usuario = new Usuario();
    //[ngOnInit]
    this.activatedRoute.params.subscribe(params => {
      const id = params.id ? Number(params.id) : null;
      console.log(id);
      if (id != null) {
      console.log("contem id" + id);
        this.buscar(id);
      }
    });

  }
  
  buscar(id: number) {
    this.usuarioService.buscar(id).subscribe(resposta => {
      this.usuario = resposta as Usuario;
    }, error => {
      console.log(error);
      alert('erro usuarios.' + error);
    });
  }

  consultar() {
    this.usuarioService.consultar().subscribe(resposta => {
      this.usuarios = resposta as Usuario[];
    }, error => {
      console.log(error);
      alert('erro usuarios.' + error);
    });
  }

  novo() {
    const usuario = new Usuario();
    this.exibirModal(usuario);
  }

  exibirModal(usuario: Usuario) {
    this.novoRegistro = true;
    this.exibirDialog = true;
    this.usuario = usuario;
  }

  salvar() {
    console.log('salvar');
    this.usuarioService.adicionar(this.usuario).subscribe(resposta => {
      this.consultar();
      this.exibirDialog = false;
      this.novoRegistro = false;
      this.messageService.add({severity: 'success', summary: 'OK', detail: 'Registro adicionado com sucesso.'});
      this.router.navigate(['/usuario/usuario-list']);
      }, error => {
        console.log(error);
        alert(error.ok);
      }
    );
  }

  confirmarExcluir() {
    console.log('confirmarExcluir');
    this.confirmationService.confirm({
      message: 'Tem certeza que deseja excluir este registro?',
      accept: () => {
          console.log('confirmarExcluir - accept');
          this.excluir();
      },
      reject: () => {
          this.messageService.add({severity: 'success', summary: 'Cancelado', detail: 'Ok. Cancelado.'});
      }
    });
  }

  excluir() {
    console.log('excluir');
    this.usuarioService.excluir(this.usuario).subscribe(resposta => {
      this.consultar();
      this.exibirDialog = false;
      this.novoRegistro = false;
      this.messageService.add({severity: 'success', summary: 'OK', detail: 'Registro excluído com sucesso.'});
      }, error => alert('erro usuarios.')
    );
  }

  aoSelecionar(event) {
    this.novoRegistro = false;
  }
  
  onSubmit(usuarioForm) {

  }

}
