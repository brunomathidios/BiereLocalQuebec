import { Component, OnInit, TemplateRef, ViewChild } from "@angular/core";
import { Router } from "@angular/router";
import { AlertService } from "src/app/core/alert/alert.service";
import { EnumStateModel } from "src/app/core/model/enum-state-model";
import { SearchTypeBiereService } from "./search-type-biere.service";
import { TypeBiereFilter } from "./type-biere.filter";
import {NgbModal, NgbModalRef} from '@ng-bootstrap/ng-bootstrap';
import { SearchBaseComponent } from "../../base/search-base.component";

@Component({
    selector: 'gestion-search-type-biere',
    templateUrl: './search-type-biere.component.html'
})
export class SearchTypeBiereComponent extends SearchBaseComponent implements OnInit {
    
    typesBieres: any[] = [ ];
    typeBiereFilter: TypeBiereFilter;

    @ViewChild('modal') private modalContent: TemplateRef<SearchTypeBiereComponent>
    private modalRef: NgbModalRef;

    constructor(
        private service: SearchTypeBiereService,
        private router: Router,
        private alertService: AlertService,
        private modalService: NgbModal) {
            super();
        }

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

    setStorageFilter() {
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
                },
                err => {
                    
                    this.modalRef.close();
                    this.idEntiteSupprimer = undefined;

                    if(err.error.messages) {
                        this.alertService.danger(err.error.messages, true);
                    } else {
                        this.alertService.danger('Une erreur est arrivé, essayer plus tard.', true);
                    }
                }
            );
    }
}