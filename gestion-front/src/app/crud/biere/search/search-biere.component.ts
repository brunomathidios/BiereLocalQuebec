import { Component, OnInit, TemplateRef, ViewChild } from "@angular/core";
import { EnumDTOModel } from "src/app/core/model/enum-dto.model";
import { TypeBiereModel } from "../../type-biere/enregistrement/type-biere.model";
import { BiereFilter } from "./biere.filter";
import { SearchBiereService } from "./search-biere.service";
import * as $ from 'jquery';
import { BiereModel } from "../biere.model";
import { AlertService } from "src/app/core/alert/alert.service";
import { NgbModal, NgbModalRef } from "@ng-bootstrap/ng-bootstrap";
import { EnumStateModel } from "src/app/core/model/enum-state-model";
import { Router } from "@angular/router";
import { SearchBaseComponent } from "../../base/search-base.component";

@Component({
    selector: 'gestion-search-biere',
    templateUrl: './search-biere.component.html'
})
export class SearchBiereComponent extends SearchBaseComponent implements OnInit {

    amertumeList: EnumDTOModel[] = [];
    filter: BiereFilter;
    typeBiereList: TypeBiereModel[] = [];
    keyword = 'nom';
    biereList: BiereModel[] = [ ];
    totalElements: number;

    @ViewChild('modal') 
    private modalContent: TemplateRef<SearchBiereComponent>;
    private modalRef: NgbModalRef;

    constructor(
        private service: SearchBiereService,
        private router: Router,
        private alertService: AlertService,
        private modalService: NgbModal) {
        super();
    }

    ngOnInit(): void {
        this.filter = new BiereFilter();
        this.filter.amertume = null;

        this.service.getAmertumeList().subscribe(res => {
            this.amertumeList = res['enumList'];
        });

        let entiteEnregistre = sessionStorage.getItem('entiteEnregistre');
        if(entiteEnregistre == null) {
            
            let filterStorage = sessionStorage.getItem('biereFilter');
            if(filterStorage != null) {
                this.filter = <BiereFilter> JSON.parse(filterStorage); 
            }
    
            let filterHasSearched = sessionStorage.getItem('hasSearched');
            this.hasSearched = <boolean> JSON.parse(filterHasSearched);
            if(this.hasSearched) {
                this.search();
            }
        }

        sessionStorage.removeItem('entiteEnregistre');
    }

    setStorageFilter() {
        if((this.filter.nom != undefined && this.filter.nom != '') ||
            (this.filter.idTypeBiere != undefined) ||
            (this.filter.tauxAlcoolStart != undefined && this.filter.tauxAlcoolStart != '') ||
            (this.filter.tauxAlcoolEnd != undefined && this.filter.tauxAlcoolEnd != '') ||
            (this.filter.ibuStart != undefined) ||
            (this.filter.ibuEnd != undefined ) ||
            (this.filter.amertume != undefined && this.filter.amertume != '' && this.filter.amertume != null) ||
            (this.filter.origine != undefined && this.filter.origine != '') ||
            (this.filter.description != undefined && this.filter.description != '')) {
            sessionStorage.setItem('biereFilter', JSON.stringify(this.filter));
        }
        this.hasSearched = true;
        sessionStorage.setItem('hasSearched', JSON.stringify(this.hasSearched));
    }

    /** ng-autocomplete */
    selectEvent(item) {
        this.filter.idTypeBiere = item ? item.idTypeBiere : null;
    }
     
    onChangeSearch(nom: string) {
        this.service.getTypeBiereByNom(nom).subscribe(res => this.typeBiereList = res);
    }
    /** fin */

    clearFilter() {
        this.filter = new BiereFilter();

        $('.x').click(); //para limpar o campo type de biere
        $('#inputIbu').val(''); //para limpar o campo taux d'alcool, necessário por causa da máscara
        $('#inputIbu2').val(''); //para limpar o campo taux d'alcool, necessário por causa da máscara

        sessionStorage.removeItem('biereFilter');
    }

    search() {
        this.parseValueFilter();

        this.service
            .searchByFilters(this.filter, this.pageable)
            .subscribe(res => {
                this.biereList = res.content;
                this.totalElements = res.totalElements;

                let msg = 'enregistrement trouvé.';
                if (res && res.totalElements > 1) {
                    msg = 'enregistrements trouvés.';
                }

                this.tableResume = `Résultat: ${res.totalElements} ${msg}`;
                
                for(let biere of this.biereList) {   
                    biere.amertumeText = this.amertumeList.find(e => e.key === biere.amertume).value;
                }
            },
            err => console.log(err));
    }

    private parseValueFilter() {
        if(this.filter.tauxAlcoolStart) parseFloat(this.filter.tauxAlcoolStart);

        if(this.filter.tauxAlcoolEnd) parseFloat(this.filter.tauxAlcoolEnd);
    }

    effacer(id: number) {
        this.idEntiteSupprimer = id;
        this.modalRef = this.modalService.open(this.modalContent);
    }

    close() {
        this.modalRef.close();
    }

    supprimer() {
        this.service.delete(this.idEntiteSupprimer)
            .subscribe(
                res => {
                    this.modalRef.close();
                    this.idEntiteSupprimer = undefined;
                    this.alertService.success(`${res.messages[0]}`, true);
                    this.recherche();
                }
            );
    }

    allerToForm(state: EnumStateModel, idBiere?: number) {
        this.stateModel = state;
        let urlForm = `biere/${this.stateModel.valueOf()}`;

        if (idBiere !== undefined) {
            urlForm = `${urlForm}/${idBiere}`;
        }

        this.router.navigate([urlForm], {skipLocationChange: true});
    }
}