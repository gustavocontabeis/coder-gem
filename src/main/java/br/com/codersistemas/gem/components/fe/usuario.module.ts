import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { PanelModule } from 'primeng/panel';
import { ToastModule } from 'primeng/toast';
import { TableModule } from 'primeng/table';
import { ButtonModule } from 'primeng/button';
import { DropdownModule } from 'primeng/dropdown';
import { InputTextModule } from 'primeng/inputtext';
import { ConfirmDialogModule } from 'primeng/confirmdialog';

import { MessageService, ConfirmationService, SelectItem } from 'primeng/api';
import { UsuarioService } from './usuario.service';

import { UsuarioRoutingModule } from './usuario-routing.module';
import { UsuarioAddComponent } from './usuario-add/usuario-add.component';
import { UsuarioFilterComponent } from './usuario-filter/usuario-filter.component';
import { UsuarioListComponent } from './usuario-list/usuario-list.component';

@NgModule({
  declarations: [UsuarioAddComponent, UsuarioFilterComponent, UsuarioListComponent],
  imports: [
    CommonModule, FormsModule,
    ToastModule, PanelModule, TableModule, ButtonModule, DropdownModule, InputTextModule, ConfirmDialogModule,
    UsuarioRoutingModule,
  ],
  providers: [MessageService, ConfirmationService, UsuarioService]
})
export class UsuarioModule { }
