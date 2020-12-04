import { CommonModule } from "@angular/common";
import { HttpClientModule } from '@angular/common/http';
import { NgModule } from "@angular/core";
import { FormsModule, ReactiveFormsModule } from "@angular/forms";
import { RouterModule } from "@angular/router";
import { NgbModalModule } from "@ng-bootstrap/ng-bootstrap";
import { ChampMessageModule } from "src/app/core/champ-message/champ-message.module";
import { PaginationModule } from "src/app/core/pagination/pagination.module";
import { CreateTypeBiereComponent } from "./enregistrement/create-type-biere.component";
import { SearchTypeBiereComponent } from "./search/search-type-biere.component";
import { TypeBiereRoutingModule } from "./type-biere-routing.module";

@NgModule({
    declarations: [
        SearchTypeBiereComponent,
        CreateTypeBiereComponent
    ],
    imports: [
        CommonModule,
        ReactiveFormsModule,
        HttpClientModule,
        PaginationModule,
        FormsModule,
        RouterModule,
        ChampMessageModule,
        TypeBiereRoutingModule,
        NgbModalModule
    ],
    exports: [
        SearchTypeBiereComponent,
        CreateTypeBiereComponent
    ]
})
export class TypeBiereModule {

}