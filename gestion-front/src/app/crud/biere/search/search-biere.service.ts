import { HttpClient, HttpParams } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { environment } from "src/environments/environment";
import { EnumDTOModel } from "src/app/core/model/enum-dto.model";
import { map } from "rxjs/operators";
import { Observable } from "rxjs";
import { BiereFilter } from "./biere.filter";
import { Pageable } from "src/app/core/model/pageable";

@Injectable({ providedIn: 'root' })
export class SearchBiereService {

    private API_AMERTUME: string;
    private API_TYPE_BIERE: string;
    private API_BIERE: string;

    constructor(private http: HttpClient) {
        this.API_AMERTUME = environment.API_GESTION + environment.API_GESTION_AMERTUME;
        this.API_TYPE_BIERE = environment.API_GESTION + environment.API_GESTION_TYPE_BIERE;
        this.API_BIERE = environment.API_GESTION
    }

    getAmertumeList(): Observable<any> {
        return this.http.get(this.API_AMERTUME)
            .pipe(map(res => {
                return res['data'][0];
            }));
    }

    getTypeBiereByNom(nom: string): Observable<any> {
        
        const params = new HttpParams().set('nom', nom);

        return this.http.get(this.API_TYPE_BIERE + "/lister", {params: params})
            .pipe(map(res => {
                return res['data'];
            }));
    }

    searchByFilters(filter: BiereFilter, page?: Pageable): Observable<any> {

        const params = new HttpParams()
            .set('filter', JSON.stringify(filter))
            .set('page', page.currentPage.toString())
            .set('size', page.pageSize.toString())
            .set('sort', page.sort? page.sort : '');

        return this.http.get(this.API_BIERE, {params: params})
            .pipe(map(res => {
                    return res['data'][0];
            })
        );
    }

    delete(id: number): Observable<any> {
        return this.http.delete(`${this.API_BIERE}/${id}`).pipe(
            map(res => {
                return res;
            })
        );
    }
}