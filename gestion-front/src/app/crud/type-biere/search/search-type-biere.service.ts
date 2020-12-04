import { HttpClient, HttpParams } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { map } from 'rxjs/operators';
import { Pageable } from "src/app/core/model/pageable";
import { environment } from "src/environments/environment";
import { TypeBiereFilter } from "./type-biere.filter";

@Injectable({ providedIn: 'root' })
export class SearchTypeBiereService {

    private API_TYPE_BIERE: string;

    constructor(private http: HttpClient) {
        this.API_TYPE_BIERE = environment.API_GESTION + environment.API_GESTION_TYPE_BIERE;
    }

    searchByFilters(filter: TypeBiereFilter, page?: Pageable): Observable<any> {

        const params = new HttpParams()
            .set('filter', JSON.stringify(filter))
            .set('page', page.currentPage.toString())
            .set('size', page.pageSize.toString())
            .set('sort', page.sort? page.sort : '');

        return this.http.get(this.API_TYPE_BIERE, {params: params})
            .pipe(map(res => {
                    return res['data'][0];
            })
        );
    }

    delete(id: number): Observable<any> {
        return this.http.delete(`${this.API_TYPE_BIERE}/${id}`).pipe(
            map(res => {
                return res;
            })
        );
    }
}