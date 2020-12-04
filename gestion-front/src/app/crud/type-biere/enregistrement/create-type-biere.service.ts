import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { map } from "rxjs/operators";
import { environment } from "src/environments/environment";
import { TypeBiereModel } from "./type-biere.model";

@Injectable({ providedIn: 'root' })
export class CreateTypeBiereService {

    private API_TYPE_BIERE: string;

    constructor(private http: HttpClient) {
        this.API_TYPE_BIERE = environment.API_GESTION + environment.API_GESTION_TYPE_BIERE;
    }

    post(entite: TypeBiereModel): Observable<any> {
        return this.http.post(this.API_TYPE_BIERE, entite).pipe(
            map(res => {
                return res;
            })
        );
    }

    put(entite: TypeBiereModel, id: number): Observable<any> {
        return this.http.put(`${this.API_TYPE_BIERE}/${id}`, entite).pipe(
            map(res => {
                return res;
            })
        );
    }

    getById(id: number): Observable<any> {
        return this.http.get(`${this.API_TYPE_BIERE}/${id}`).pipe(
            map(res => {
                return res['data'][0];
            })
        );
    }
}