import { Component, ElementRef, OnInit, ViewChild } from "@angular/core";
import { FormBuilder, FormControl, FormGroup, Validators } from "@angular/forms";
import { ActivatedRoute, Router } from "@angular/router";
import { AlertService } from "src/app/core/alert/alert.service";
import { EnumStateModel } from "src/app/core/model/enum-state-model";
import { PlatformDetectorService } from "src/app/core/platform-detector/platform-detector.service";
import { CreateTypeBiereService } from "./create-type-biere.service";
import { TypeBiereModel } from "./type-biere.model";

@Component({
    templateUrl: './create-type-biere.component.html'
})
export class CreateTypeBiereComponent implements OnInit {

    signupForm: FormGroup;
    @ViewChild('nomInput') nomInput: ElementRef<HTMLInputElement>;
    stateModeCode: number;
    entite: TypeBiereModel = new TypeBiereModel();
    titleForm: string;
    private paramId: number;

    constructor(
        private formBuilder: FormBuilder,
        private createTypeBiereService: CreateTypeBiereService,
        private router: Router,
        private platformDetectorService: PlatformDetectorService,
        private alertService: AlertService,
        private activatedRoute: ActivatedRoute) {
    }

    ngOnInit(): void {

        this.stateModeCode = parseInt(this.activatedRoute.snapshot.params['state'],10);

        this.signupForm = this.formBuilder.group({
            nom: new FormControl(
                { value: '', disabled: this.isViewState() },
                [ Validators.required, Validators.maxLength(200)]
            ), 
            description: new FormControl(
                { value: '', disabled: this.isViewState() },
                [ Validators.maxLength(4000) ]
            )
        });
        
        this.paramId = this.activatedRoute.snapshot.params['id'];

        if(this.paramId !== undefined && (this.isViewState() || this.isEditState()) ) {
            this.findEntiteById(this.paramId);
        }

        if(this.isAddState() && this.platformDetectorService.isPlatformBrowser()) {
            this.nomInput.nativeElement.focus();
            this.titleForm = 'Créer nouvelle type de bière';
        }

        if(this.isViewState()){
            this.titleForm = 'Visualiser type de bière';
        }

        if(this.isEditState()) {
            this.titleForm = 'Modifier type de bière';
        }
    }

    private findEntiteById(id: number) {
        this.createTypeBiereService.getById(id).subscribe(res => {
            this.entite = res;
        });
    }

    isViewState() {
        return EnumStateModel.VISUALISER === this.stateModeCode;
    }

    isAddState() {
        return EnumStateModel.AJOUTER === this.stateModeCode;
    }

    isEditState() {
        return EnumStateModel.MODIFIER === this.stateModeCode;
    }

    enregistrer() {
        if(this.signupForm.valid && !this.signupForm.pending) {

            if(this.isAddState()) {
                
                this.createTypeBiereService.post(this.entite)
                    .subscribe(
                        res => {
                            this.success(`${res.messages[0]}`);
                        },
                        err => {
                            if(err.error.messages) {
                                this.alertService.danger(err.error.messages, true);
                            } else {
                                this.alertService.danger('Une erreur est arrivé, essayer plus tard.', true);
                            }
                        }
                );
            } else {
                
                this.createTypeBiereService.put(this.entite, this.entite.idTypeBiere)
                    .subscribe(
                        res => {
                            this.success(`${res.messages[0]}`);
                        },
                        err => {
                            console.log(err);
                            if(err.error.messages) {
                                this.alertService.danger(err.error.messages, true);
                            } else {
                                this.alertService.danger('Une erreur est arrivé, essayer plus tard.', true);
                            }
                        }
                    );
            }
        }
    }

    private success(message: string) {
        sessionStorage.setItem('entiteEnregistre', 'true');
        this.alertService.success(message, true);
        this.router.navigate(['type-biere']);
    }

    annuler() {
        this.entite = new TypeBiereModel();
        this.router.navigate(['type-biere']);    
    }

    showBtnEnregistrer() {
        return this.isAddState() || this.isEditState();
    }
}