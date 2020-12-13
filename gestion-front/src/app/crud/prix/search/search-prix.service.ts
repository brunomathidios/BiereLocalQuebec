import { HttpClient, HttpParams } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { map } from "rxjs/operators";
import { Pageable } from "src/app/core/model/pageable";
import { environment } from "src/environments/environment";
import { PrixFilter } from "./prix.filter";

@Injectable({ providedIn: "root" })
export class SearchPrixService {

    private API_PRIX_BIERE: string;

    constructor(private http: HttpClient) {
        this.API_PRIX_BIERE = environment.API_GESTION + environment.API_GESTION_PRIX;
    }

    searchByFilters(filter: PrixFilter, page?: Pageable): Observable<any> {

        const params = new HttpParams()
            .set('filter', JSON.stringify(filter))
            .set('page', page.currentPage.toString())
            .set('size', page.pageSize.toString())
            .set('sort', page.sort? page.sort : '');
    
        return this.http.get(this.API_PRIX_BIERE, {params: params})
            .pipe(map(res => {
                    return res['data'][0];
            })
        );
    }
    
    delete(id: number): Observable<any> {
        return this.http.delete(`${this.API_PRIX_BIERE}/${id}`).pipe(
            map(res => {
                return res;
            })
        );
    }
}