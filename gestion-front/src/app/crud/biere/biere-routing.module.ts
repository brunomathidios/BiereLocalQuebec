import { NgModule } from "@angular/core";
import { RouterModule, Routes } from "@angular/router";
import { SearchBiereComponent } from "./search/search-biere.component";

const routes: Routes = [
    {
        path: 'biere', component: SearchBiereComponent, 
        data: { title: 'Liste des bières' }
    }
];

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class BiereRoutingModule {

}