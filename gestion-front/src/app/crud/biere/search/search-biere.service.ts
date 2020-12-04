import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { environment } from "src/environments/environment";
import { EnumDTOModel } from "src/app/core/model/enum-dto.model";
import { map } from "rxjs/operators";
import { Observable } from "rxjs";

@Injectable({ providedIn: 'root' })
export class SearchBiereService {

    private API_AMERTUME: string;

    constructor(private http: HttpClient) {
        this.API_AMERTUME = environment.API_GESTION + environment.API_GESTION_AMERTUME;
    }

    getAmertumeList(): Observable<any> {
        return this.http.get(this.API_AMERTUME)
            .pipe(map(res => {
                return res['data'][0];
            }));
    }
}