import { Component, OnInit, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { HttpErrorResponse } from '@angular/common/http';
import { finalize } from 'rxjs';
import { Produto } from '../../models/produto';
import { ProdutoService } from '../../services/produto.service';
@Component({ selector: 'app-produtos', standalone: true, imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './produtos.component.html' })
export class ProdutosComponent implements OnInit {
  private service = inject(ProdutoService);
  private fb = inject(FormBuilder);
  itens: Produto[] = [];
  editandoId: string | null = null;
  carregando = false;
  salvando = false;
  excluindoId: string | null = null;
  erro = '';
  mensagem = '';
  form = this.fb.nonNullable.group({ nome: ['', [Validators.required, Validators.maxLength(100)]], descricao: ['', [Validators.required, Validators.maxLength(500)]], preco: [0, [Validators.required, Validators.min(0.01), Validators.max(99999999.99), Validators.pattern(/^\d+(\.\d{1,2})?$/)]] });
  ngOnInit() { this.carregar(); }
  carregar() {
    this.carregando = true;
    this.erro = '';
    this.service.listar().pipe(finalize(() => this.carregando = false)).subscribe({
      next: itens => this.itens = itens,
      error: erro => this.falhou(erro)
    });
  }
  salvar() {
    if (this.salvando || this.excluindoId) return;
    if (this.form.invalid) { this.form.markAllAsTouched(); return; }
    this.salvando = true;
    this.erro = '';
    this.mensagem = '';
    const dados = this.form.getRawValue();
    const pedido = this.editandoId ? this.service.atualizar(this.editandoId, dados) : this.service.criar(dados);
    pedido.pipe(finalize(() => this.salvando = false)).subscribe({
      next: () => { this.cancelar(); this.mensagem = 'Produto salvo com sucesso.'; this.carregar(); },
      error: erro => this.falhou(erro)
    });
  }
  editar(item: Produto) {
    if (!item.id || this.salvando || this.excluindoId) return;
    this.erro = ''; this.mensagem = '';
    this.salvando = true;
    // GET BY ID carrega a versao atual antes de editar.
    this.service.buscar(item.id).pipe(finalize(() => this.salvando = false)).subscribe({
      next: atual => { this.editandoId = atual.id!; this.form.patchValue(atual); this.form.markAsUntouched(); },
      error: erro => this.falhou(erro)
    });
  }
  excluir(item: Produto) {
    if (!item.id || this.salvando || this.excluindoId || !confirm(`Excluir "${item.nome}"?`)) return;
    this.excluindoId = item.id;
    this.erro = ''; this.mensagem = '';
    this.service.excluir(item.id).pipe(finalize(() => this.excluindoId = null)).subscribe({
      next: () => { if (this.editandoId === item.id) this.cancelar(); this.mensagem = 'Produto excluído.'; this.carregar(); },
      error: erro => this.falhou(erro)
    });
  }
  cancelar() { this.editandoId = null; this.form.reset({ nome: '', descricao: '', preco: 0 }); }
  invalido(campo: string) { const c = this.form.get(campo); return !!c?.invalid && !!c?.touched; }
  private falhou(erro: HttpErrorResponse) {
    this.mensagem = '';
    if (erro.status === 0 || erro.status === 504 || erro.status === 502) {
      this.erro = 'API indisponível. Verifique se o backend está rodando na porta 8080.'; return;
    }
    const campos = erro.error?.campos ? Object.values(erro.error.campos).join(' ') : '';
    this.erro = campos || erro.error?.mensagem || 'Não foi possível concluir a operação.';
  }
}
