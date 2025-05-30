import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HomeComponent } from './home/home.component';
import { RootComponent } from './root/root.component';



@NgModule({
  declarations: [
    HomeComponent,
    RootComponent
  ],
  imports: [
    CommonModule
  ]
})
export class PagesModule { }
