import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Cliente } from '../models/cliente';
@Injectable({ providedIn: 'root' })
export class ClienteService {
  private http = inject(HttpClient);
  private url = '/api/clientes';
  listar() { return this.http.get<Cliente[]>(this.url); }
  buscar(id: string) { return this.http.get<Cliente>(`${this.url}/${encodeURIComponent(id)}`); }
  criar(dados: Cliente) { return this.http.post<Cliente>(this.url, dados); }
  atualizar(id: string, dados: Cliente) { return this.http.put<Cliente>(`${this.url}/${encodeURIComponent(id)}`, dados); }
  excluir(id: string) { return this.http.delete<void>(`${this.url}/${encodeURIComponent(id)}`); }
}
