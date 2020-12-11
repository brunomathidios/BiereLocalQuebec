import { BaseEntiteModel } from "src/app/core/model/base-entite.model";
import { TypeBiereModel } from "../type-biere/enregistrement/type-biere.model";

export class BiereModel extends BaseEntiteModel {

    idBiere: number;
    nom: string;
    idTypeBiere: number;
    tauxAlcool: number;
    origine: string;
    ibu: number;
    amertume: string;
    description: string;
    amertumeText: string;
    typeBiere: TypeBiereModel;
    photo: Blob;
}