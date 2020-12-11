import { HttpClient, HttpHeaders } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { map } from "rxjs/operators";
import { environment } from "src/environments/environment";
import { BiereModel } from "../biere.model";

@Injectable({providedIn: 'root'})
export class CreateBiereService {

    private API_BIERE: string;

    constructor(private http: HttpClient) {
        this.API_BIERE = environment.API_GESTION;
    }

    post(entite: BiereModel, file: File): Observable<any> {

        const formData = new FormData();
        formData.append('biere', JSON.stringify(entite));
        if(file) {
            formData.append('photo', file);
        }

        const headers = new HttpHeaders();
        headers.set('Content-Type', 'application/json; charset=utf-8');
        headers.set('Content-Type', 'image/jpeg;');
        headers.set('Content-Type', 'image/png;');

        return this.http.post(this.API_BIERE, formData, { headers: headers }).pipe(
            map(res => {
                return res;
            })
        );
    }

    put(entite: BiereModel, file: File, id: number): Observable<any> {

        const formData = new FormData();
        formData.append('biere', JSON.stringify(entite));
        formData.append('photo', file);

        const headers = new HttpHeaders();
        headers.set('Content-Type', 'application/json; charset=utf-8');
        headers.set('Content-Type', 'image/jpeg;');
        headers.set('Content-Type', 'image/png;');

        return this.http.put(`${this.API_BIERE}/${id}`, formData, { headers: headers }).pipe(
            map(res => {
                return res;
            })
        );
    }

    getById(id: number): Observable<any> {
        return this.http.get(`${this.API_BIERE}/${id}`).pipe(
            map(res => {
                return res['data'][0];
            })
        );
    }
}