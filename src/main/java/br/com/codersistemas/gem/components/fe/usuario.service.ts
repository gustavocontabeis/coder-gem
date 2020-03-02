import { Usuario } from './usuario';
import { environment } from './../../environments/environment';
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class UsuarioService {

  apiUrl: string;

  constructor(private httpClient: HttpClient) {
    this.apiUrl = environment.apiUrl + '/usuarios';
  }

  adicionar(usuario: Usuario) {
    console.log('adicionar', usuario);
    return this.httpClient.post(this.apiUrl, usuario);
  }

  buscar(id: number) {
    console.log('buscar', this.apiUrl);
    return this.httpClient.get(this.apiUrl + '/' + id);
  }
  
  consultar() {
    console.log('consultar', this.apiUrl);
    return this.httpClient.get(this.apiUrl);
  }

  excluir(usuario: Usuario) {
    console.log('excluir', usuario);
    return this.httpClient.delete(this.apiUrl + '/' + usuario.id);
  }

}
