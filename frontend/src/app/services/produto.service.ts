import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Produto } from '../models/produto';
@Injectable({ providedIn: 'root' })
export class ProdutoService {
  private http = inject(HttpClient);
  private url = '/api/produtos';
  listar() { return this.http.get<Produto[]>(this.url); }
  buscar(id: string) { return this.http.get<Produto>(`${this.url}/${encodeURIComponent(id)}`); }
  criar(dados: Produto) { return this.http.post<Produto>(this.url, dados); }
  atualizar(id: string, dados: Produto) { return this.http.put<Produto>(`${this.url}/${encodeURIComponent(id)}`, dados); }
  excluir(id: string) { return this.http.delete<void>(`${this.url}/${encodeURIComponent(id)}`); }
}
