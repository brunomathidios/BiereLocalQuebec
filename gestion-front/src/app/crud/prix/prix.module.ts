import { CommonModule } from "@angular/common";
import { HttpClientModule } from "@angular/common/http";
import { NgModule } from "@angular/core";
import { ReactiveFormsModule, FormsModule } from "@angular/forms";
import { RouterModule } from "@angular/router";
import { NgbModalModule } from "@ng-bootstrap/ng-bootstrap";
import { AutocompleteLibModule } from "angular-ng-autocomplete";
import { ChampMessageModule } from "src/app/core/champ-message/champ-message.module";
import { PaginationModule } from "src/app/core/pagination/pagination.module";
import { CreatePrixComponent } from "./enregistrement/create-prix.component";
import { PrixRoutingModule } from "./prix-routing.module";
import { SearchPrixComponent } from "./search/search-prix.component";

@NgModule({
    declarations: [
        SearchPrixComponent,
        CreatePrixComponent
    ],
    imports: [
        CommonModule,
        ReactiveFormsModule,
        HttpClientModule,
        PaginationModule,
        FormsModule,
        RouterModule,
        ChampMessageModule,
        PrixRoutingModule,
        NgbModalModule,
        AutocompleteLibModule
    ],
    exports: [
        SearchPrixComponent,
        CreatePrixComponent
    ]
})
export class PrixModule {

}