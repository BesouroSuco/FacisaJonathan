import { Component } from '@angular/core';
import { RouterLink, RouterLinkActive, RouterOutlet } from '@angular/router';
@Component({ selector: 'app-root', standalone: true, imports: [RouterLink, RouterLinkActive, RouterOutlet],
  template: `<div class="shell"><aside><a class="brand" routerLink="/produtos"><span class="mark">O</span><span>ORION<small>CADASTROS</small></span></a>
  <p class="nav-title">GERENCIAR</p><nav><a routerLink="/produtos" routerLinkActive="active">Produtos</a><a routerLink="/clientes" routerLinkActive="active">Clientes</a></nav>
  <div class="aside-note">Orion 3D<small>Produtos e clientes em um só lugar.</small></div></aside>
  <main><header><span>Painel de cadastros</span><span class="badge">Orion 3D</span></header><router-outlet /></main></div>` })
export class AppComponent {}
