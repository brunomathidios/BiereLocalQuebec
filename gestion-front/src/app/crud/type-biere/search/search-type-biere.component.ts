import { Component, OnInit, TemplateRef, ViewChild } from "@angular/core";
import { Router } from "@angular/router";
import { AlertService } from "src/app/core/alert/alert.service";
import { EnumStateModel } from "src/app/core/model/enum-state-model";
import { Pageable } from "src/app/core/model/pageable";
import { SearchTypeBiereService } from "./search-type-biere.service";
import { TypeBiereFilter } from "./type-biere.filter";
import {NgbModal, NgbModalRef} from '@ng-bootstrap/ng-bootstrap';

@Component({
    selector: 'gestion-search-type-biere',
    templateUrl: './search-type-biere.component.html'
})
export class SearchTypeBiereComponent implements OnInit {

    pageable: Pageable = new Pageable();
    typesBieres: any[] = [ ];
    typeBiereFilter: TypeBiereFilter;
    tableResume: string;
    ENUM_STATE_MODEL = EnumStateModel;
    stateModel = EnumStateModel.SANS_ETAT;
    private idEntiteSupprimer: number;
    private hasSearched = false;
    private demarrerPage = true;

    @ViewChild('modal') private modalContent: TemplateRef<SearchTypeBiereComponent>
    private modalRef: NgbModalRef;

    constructor(
        private service: SearchTypeBiereService,
        private router: Router,
        private alertService: AlertService,
        private modalService: NgbModal) {}

    ngOnInit(): void {
        this.typeBiereFilter = new TypeBiereFilter();

        let entiteEnregistre = sessionStorage.getItem('entiteEnregistre');
        if(entiteEnregistre == null) {
            
            let filterStorage = sessionStorage.getItem('typeBiereFilter');
            if(filterStorage != null) {
                this.typeBiereFilter = <TypeBiereFilter> JSON.parse(filterStorage); 
            }
    
            let filterHasSearched = sessionStorage.getItem('hasSearched');
            this.hasSearched = <boolean> JSON.parse(filterHasSearched);
            if(this.hasSearched) {
                this.search();
            }
        }

        sessionStorage.removeItem('entiteEnregistre');
    }

    displayActivePage(activePageNumber:number){  
        if(!this.demarrerPage) {
            this.pageable.currentPage = (activePageNumber - 1);
            this.setStorageFilter();
            this.search();
        } else {
            this.demarrerPage = !this.demarrerPage;
        }
    } 

    search() {
        this.service
            .searchByFilters(this.typeBiereFilter, this.pageable)
            .subscribe(res => {
                
                this.typesBieres = res;

                let msg = 'enregistrement trouvé.';
                if (res && res.totalElements > 1) {
                    msg = 'enregistrements trouvés.';
                }

                this.tableResume = `Résultat: ${res.totalElements} ${msg}`;
            },
            err => console.log(err));
    }

    private setStorageFilter() {
        if((this.typeBiereFilter.nom != undefined && this.typeBiereFilter.nom != '') ||
            (this.typeBiereFilter.description != undefined && this.typeBiereFilter.description != '')) {
            sessionStorage.setItem('typeBiereFilter', JSON.stringify(this.typeBiereFilter));
        }
        this.hasSearched = true;
        sessionStorage.setItem('hasSearched', JSON.stringify(this.hasSearched));
    }

    clearFilter() {
        this.typeBiereFilter = new TypeBiereFilter();
        sessionStorage.removeItem('typeBiereFilter');
    }

    recherche() {
        this.pageable = new Pageable();
        this.setStorageFilter();
        this.search();
    }

    sort(key: string, order: string) {
        this.pageable = new Pageable();
        this.pageable.sort = `${key},${order}`;
        this.setStorageFilter();
        this.search();
    }

    allerToForm(state: EnumStateModel, idTypeBiere?: number) {
        this.stateModel = state;
        let urlForm = `type-biere/${this.stateModel.valueOf()}`;

        if (idTypeBiere !== undefined) {
            urlForm = `${urlForm}/${idTypeBiere}`;
        }

        this.router.navigate([urlForm], {skipLocationChange: true});
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
}