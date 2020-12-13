import { CommonModule } from "@angular/common";
import { NgModule } from "@angular/core";
import { BiereModule } from "./biere/biere.module";
import { PrixModule } from "./prix/prix.module";
import { TypeBiereModule } from "./type-biere/type-biere.module";

@NgModule({
    imports: [
        CommonModule,
        TypeBiereModule,
        BiereModule,
        PrixModule
    ]
})
export class CrudModule {

}