import { NgModule } from "@angular/core";
import { RouterModule, Routes } from "@angular/router";
import { CreateTypeBiereComponent } from "./enregistrement/create-type-biere.component";
import { SearchTypeBiereComponent } from "./search/search-type-biere.component";

const routes: Routes = [
    {
        path: 'type-biere', component: SearchTypeBiereComponent, 
        data: { title: 'Liste types de bières' }
    },
    {
        path: 'type-biere/:state', component: CreateTypeBiereComponent,
        data: { title: 'Gérer types de bières' }
    },
    {
        path: 'type-biere/:state/:id', component: CreateTypeBiereComponent,
        data: { title: 'Gérer types de bières' }
    }
];

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class TypeBiereRoutingModule {

}