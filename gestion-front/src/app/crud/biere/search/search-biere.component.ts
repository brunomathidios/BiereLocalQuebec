import { Component, OnInit } from "@angular/core";
import { EnumDTOModel } from "src/app/core/model/enum-dto.model";
import { BiereFilter } from "./biere.filter";
import { SearchBiereService } from "./search-biere.service";

@Component({
    selector: 'gestion-search-biere',
    templateUrl: './search-biere.component.html'
})
export class SearchBiereComponent implements OnInit {

    amertumeList: EnumDTOModel[] = [];
    filter: BiereFilter;

    constructor(
        private service: SearchBiereService
    ) {}

    ngOnInit(): void {
        this.filter = new BiereFilter();

        this.service.getAmertumeList().subscribe(res => {
            this.amertumeList = res['enumList'];
        });
    }
}