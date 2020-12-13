import { NgModule } from "@angular/core";
import { RouterModule, Routes } from "@angular/router";
import { CreatePrixComponent } from "./enregistrement/create-prix.component";
import { SearchPrixComponent } from "./search/search-prix.component";

const routes: Routes = [
    {
        path: 'prix', component: SearchPrixComponent, 
        data: { title: 'Liste des prix' }
    },
    {
        path: 'prix/:state', component: CreatePrixComponent,
        data: { title: 'Gérer des prix' }
    },
    {
        path: 'prix/:state/:id', component: CreatePrixComponent,
        data: { title: 'Gérer des prix' }
    }
];

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class PrixRoutingModule {

}