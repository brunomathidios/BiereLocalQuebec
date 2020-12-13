import { BaseEntiteModel } from "src/app/core/model/base-entite.model";
import { BiereModel } from "../../biere/biere.model";

export class PrixModel extends BaseEntiteModel {

    idPrix: number;
    prix: string;
    idBiere: number;
    biere: BiereModel;
}