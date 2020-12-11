import { EnumStateModel } from "src/app/core/model/enum-state-model";
import { Pageable } from "src/app/core/model/pageable";

export abstract class SearchBaseComponent {

    pageable: Pageable = new Pageable();
    tableResume: string;
    ENUM_STATE_MODEL = EnumStateModel;
    stateModel = EnumStateModel.SANS_ETAT;
    protected idEntiteSupprimer: number;
    protected hasSearched = false;
    protected demarrerPage = true;

    abstract search();
    abstract setStorageFilter();

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

    displayActivePage(activePageNumber:number){  
        if(!this.demarrerPage) {
            this.pageable.currentPage = (activePageNumber - 1);
            this.setStorageFilter();
            this.search();
        } else {
            this.demarrerPage = !this.demarrerPage;
        }
    } 
}