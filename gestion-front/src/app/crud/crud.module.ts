import { CommonModule } from "@angular/common";
import { NgModule } from "@angular/core";
import { PaginationModule } from "../core/pagination/pagination.module";
import { BiereModule } from "./biere/biere.module";
import { TypeBiereModule } from "./type-biere/type-biere.module";

@NgModule({
    imports: [
        CommonModule,
        TypeBiereModule,
        BiereModule
    ]
})
export class CrudModule {

}