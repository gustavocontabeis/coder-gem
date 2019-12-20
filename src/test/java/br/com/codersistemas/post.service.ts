import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Post } from '../entity/post';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class PostService {

  apiUrl: string;

  constructor(private httpClient: HttpClient) {
    this.apiUrl = environment.apiUrl + '/posts';
  }

  consultar() {
    return this.httpClient.get(this.apiUrl);
  }

  salvar(post: Post) {
    return this.httpClient.post(this.apiUrl, post);
  }

  excluir(post: Post) {
    return this.httpClient.delete(this.apiUrl + '/' + post.id);
  }

  buscar(id: number) {
    return this.httpClient.get(this.apiUrl + '/' + id);
  }

}
