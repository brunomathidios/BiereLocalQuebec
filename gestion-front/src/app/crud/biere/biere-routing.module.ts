import { NgModule } from "@angular/core";
import { RouterModule, Routes } from "@angular/router";
import { CreateBiereComponent } from "./enregistrement/create-biere.component";
import { SearchBiereComponent } from "./search/search-biere.component";

const routes: Routes = [
    {
        path: 'biere', component: SearchBiereComponent, 
        data: { title: 'Liste des bières' }
    },
    {
        path: 'biere/:state', component: CreateBiereComponent,
        data: { title: 'Gérer des bières' }
    },
    {
        path: 'biere/:state/:id', component: CreateBiereComponent,
        data: { title: 'Gérer des bières' }
    }
];

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class BiereRoutingModule {

}