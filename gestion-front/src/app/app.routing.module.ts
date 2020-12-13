import { NgModule } from "@angular/core";
import { RouterModule, Routes } from "@angular/router";
import { AppComponent } from "./app.component";

const routes: Routes = [
    {
        path: '',
        component: AppComponent
    },
    {
        path: 'type-biere',
        loadChildren: () =>
            import('./crud/type-biere/type-biere.module').then(m => m.TypeBiereModule)
    },
    {
        path: 'biere',
        loadChildren: () =>
            import('./crud/biere/biere.module').then(m => m.BiereModule)
    },
    {
        path: 'prix',
        loadChildren: () =>
            import('./crud/prix/prix.module').then(m => m.PrixModule)
    }
]

@NgModule({
    imports: [RouterModule.forRoot(routes, {useHash: true})],
    exports: [RouterModule]
})
export class AppRoutingModule {

}