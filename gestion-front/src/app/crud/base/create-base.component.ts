import { FormGroup } from "@angular/forms";
import { EnumStateModel } from "src/app/core/model/enum-state-model";

export class CreateBaseComponent {

    signupForm: FormGroup;
    titleForm: string;
    stateModeCode: number;
    protected paramId: number;;

    isViewState() {
        return EnumStateModel.VISUALISER === this.stateModeCode;
    }

    isAddState() {
        return EnumStateModel.AJOUTER === this.stateModeCode;
    }

    isEditState() {
        return EnumStateModel.MODIFIER === this.stateModeCode;
    }

    showBtnEnregistrer() {
        return this.isAddState() || this.isEditState();
    }
}