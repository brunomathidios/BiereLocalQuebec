import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { map } from "rxjs/operators";
import { environment } from "src/environments/environment";
import { PrixModel } from "./prix.model";

@Injectable({ providedIn: 'root' })
export class CreatePrixService {

    private API_PRIX: string;

    constructor(private http: HttpClient) {
        this.API_PRIX = environment.API_GESTION + environment.API_GESTION_PRIX;
    }

    post(entite: PrixModel): Observable<any> {
        return this.http.post(this.API_PRIX, entite).pipe(
            map(res => {
                return res;
            })
        );
    }

    put(entite: PrixModel, id: number): Observable<any> {
        return this.http.put(`${this.API_PRIX}/${id}`, entite).pipe(
            map(res => {
                return res;
            })
        );
    }

    getById(id: number): Observable<any> {
        return this.http.get(`${this.API_PRIX}/${id}`).pipe(
            map(res => {
                return res['data'][0];
            })
        );
    }
}