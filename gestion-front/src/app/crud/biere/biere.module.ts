import { CommonModule } from "@angular/common";
import { HttpClientModule } from "@angular/common/http";
import { NgModule } from "@angular/core";
import { ReactiveFormsModule, FormsModule } from "@angular/forms";
import { RouterModule } from "@angular/router";
import { NgbModalModule } from "@ng-bootstrap/ng-bootstrap";
import { NgxMaskModule } from "ngx-mask";
import { ChampMessageModule } from "src/app/core/champ-message/champ-message.module";
import { PaginationModule } from "src/app/core/pagination/pagination.module";
import { BiereRoutingModule } from "./biere-routing.module";
import { SearchBiereComponent } from "./search/search-biere.component";
import {AutocompleteLibModule} from 'angular-ng-autocomplete';
import { CreateBiereComponent } from "./enregistrement/create-biere.component";

@NgModule({
    declarations: [
        SearchBiereComponent,
        CreateBiereComponent
    ],
    imports: [
        CommonModule,
        ReactiveFormsModule,
        HttpClientModule,
        PaginationModule,
        FormsModule,
        RouterModule,
        ChampMessageModule,
        BiereRoutingModule,
        NgbModalModule,
        AutocompleteLibModule,
        NgxMaskModule.forRoot()
    ],
    exports: [
        SearchBiereComponent,
        CreateBiereComponent
    ]
})
export class BiereModule {

}