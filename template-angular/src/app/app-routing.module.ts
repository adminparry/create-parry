import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from 'src/pages/home/home.component';
import { RootComponent } from 'src/pages/root/root.component';

const routes: Routes = [

  // {
  //   path: 'home/:name',
  //   loadChildren:() => import('../components/components.module').then(m => m.ComponentsModule)
  // },
  // {
  //   path: 'test/:name',
  //   component: HomeComponent
  // },
  // {
  //   path: 'user',
  //   loadChildren:() => import('../pages/user/user.module').then(m => m.UserModule)
  // },
  {
    path: '',
    redirectTo: '',
    pathMatch: 'full'
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
