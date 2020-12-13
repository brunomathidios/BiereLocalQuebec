import { Component, OnInit, TemplateRef, ViewChild } from "@angular/core";
import { Router } from "@angular/router";
import { AlertService } from "src/app/core/alert/alert.service";
import { SearchBaseComponent } from "../../base/search-base.component";
import { BiereModel } from "../../biere/biere.model";
import { SearchBiereService } from "../../biere/search/search-biere.service";
import { PrixFilter } from "./prix.filter";
import { SearchPrixService } from "./search-prix.service";
import * as $ from 'jquery';
import { ResultSearchPrix } from "./result-search-prix.model";
import { NgbModal, NgbModalRef } from "@ng-bootstrap/ng-bootstrap";
import { SearchBiereComponent } from "../../biere/search/search-biere.component";
import { EnumStateModel } from "src/app/core/model/enum-state-model";

@Component({
    selector: 'gestion-search-prix-biere',
    templateUrl: "./search-prix.component.html"
})
export class SearchPrixComponent extends SearchBaseComponent implements OnInit {

    filter: PrixFilter;
    prixList: ResultSearchPrix[] = [ ];
    biereList: BiereModel[] = [ ];
    keyword = 'nom';

    @ViewChild('modal') 
    private modalContent: TemplateRef<SearchBiereComponent>;
    private modalRef: NgbModalRef;

    constructor(
        private service: SearchPrixService,
        private biereService: SearchBiereService,
        private router: Router,
        private alertService: AlertService,
        private modalService: NgbModal) {
            super();
        }

    ngOnInit(): void {
        this.filter = new PrixFilter();

        let entiteEnregistre = sessionStorage.getItem('entiteEnregistre');
        if(entiteEnregistre == null) {
            
            let filterStorage = sessionStorage.getItem('prixFilter');
            if(filterStorage != null) {
                this.filter = <PrixFilter> JSON.parse(filterStorage); 
            }
    
            let filterHasSearched = sessionStorage.getItem('hasSearched');
            this.hasSearched = <boolean> JSON.parse(filterHasSearched);
            if(this.hasSearched) {
                this.search();
            }
        }

        sessionStorage.removeItem('entiteEnregistre');
    }

    /** ng-autocomplete */
    selectEvent(item) {
        this.filter.idBiere = item ? item.idBiere : null;
    }
     
    onChangeSearch(nom: string) {
        this.biereService
            .getBiereByNom(nom)
            .subscribe(res => this.biereList = res);
    }
    /** fin */

    search() {
        this.service
            .searchByFilters(this.filter, this.pageable)
            .subscribe(res => {
                
                this.prixList = res.content;
                this.totalElements = res.totalElements;

                let msg = 'enregistrement trouvé.';
                if (res && res.totalElements > 1) {
                    msg = 'enregistrements trouvés.';
                }

                this.tableResume = `Résultat: ${res.totalElements} ${msg}`;
            },
            err => console.log(err));
    }

    clearFilter() {
        this.filter = new PrixFilter();
        $('.x').click(); //para limpar o campo autocomplete biere
        sessionStorage.removeItem('prixFilter');
    }

    setStorageFilter() {
        if((this.filter.prix != undefined && this.filter.prix != '') || (this.filter.idBiere != undefined)) {
            sessionStorage.setItem('prixFilter', JSON.stringify(this.filter));
        }
        this.hasSearched = true;
        sessionStorage.setItem('hasSearched', JSON.stringify(this.hasSearched));
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

    allerToForm(state: EnumStateModel, idPrix?: number) {
        this.stateModel = state;
        let urlForm = `prix/${this.stateModel.valueOf()}`;

        if (idPrix !== undefined) {
            urlForm = `${urlForm}/${idPrix}`;
        }

        this.router.navigate([urlForm], {skipLocationChange: true});
    }
}