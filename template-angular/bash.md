# struct

> bash

``` bash
ng new angular-app
cd  angular-app
cd src 
ng g environments

ng g guard guards/guard

ng g s services/user

ng g m directives
ng g m pipes
ng g m interceptors

ng g c pages/root --skip-import

ng g c components/layout --skip-import

npm i @ngrx/store @ngrx/effects @ngrx/entity @ngrx/router-store @ngrx/store-devtools @ngrx/schematics
ng config  cli.defaultCollection @ngrx/schematics

ng g store State --root --module app/app.module.ts --state-path stores --state-interface AppState

ng g action stores/actions/counter

ng g reducer stores/reducers/counter --reducers=../index.ts

ng g selector stores/selectors/counter --skip-tests
```