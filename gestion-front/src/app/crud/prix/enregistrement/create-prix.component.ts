import { Component, ElementRef, OnInit, ViewChild } from "@angular/core";
import { FormBuilder, FormControl, Validators } from "@angular/forms";
import { Router, ActivatedRoute } from "@angular/router";
import { AlertService } from "src/app/core/alert/alert.service";
import { PlatformDetectorService } from "src/app/core/platform-detector/platform-detector.service";
import { CreateBaseComponent } from "../../base/create-base.component";
import { BiereModel } from "../../biere/biere.model";
import { SearchBiereService } from "../../biere/search/search-biere.service";
import { CreatePrixService } from "./create-prix.service";
import { PrixModel } from "./prix.model";

@Component({
    templateUrl: './create-prix.component.html'
})
export class CreatePrixComponent extends CreateBaseComponent implements OnInit {

    @ViewChild('prixInput') 
    prixInput: ElementRef<HTMLInputElement>;

    @ViewChild('autocomplete')
    autocomplete: any;

    entite: PrixModel = new PrixModel();
    biereList: BiereModel[] = [ ];
    keyword = 'nom';

    constructor(
        private formBuilder: FormBuilder,
        private service: CreatePrixService,
        private biereService: SearchBiereService,
        private router: Router,
        private platformDetectorService: PlatformDetectorService,
        private alertService: AlertService,
        private activatedRoute: ActivatedRoute) {
            super();
    }

    ngOnInit(): void {

        this.stateModeCode = parseInt(this.activatedRoute.snapshot.params['state'],10);

        this.signupForm = this.formBuilder.group({
            prix: new FormControl(
                { value: '', disabled: this.isViewState() },
                [ Validators.required, Validators.maxLength(1000)]
            ), 
            biere: new FormControl(
                { value: '', disabled: this.isViewState() },
                [ Validators.required ]
            )
        });
        
        this.paramId = this.activatedRoute.snapshot.params['id'];

        if(this.paramId !== undefined && (this.isViewState() || this.isEditState()) ) {
            this.findEntiteById(this.paramId);
        }

        if(this.isAddState() && this.platformDetectorService.isPlatformBrowser()) {
            this.prixInput.nativeElement.focus();
            this.titleForm = 'Créer nouveau prix de bière';
        }

        if(this.isViewState()){
            this.titleForm = 'Visualiser prix de bière';
        }

        if(this.isEditState()) {
            this.titleForm = 'Modifier prix de bière';
        }
    }

    private findEntiteById(id: number) {
        this.service.getById(id).subscribe(res => {
            this.entite = res;

            this.biereList.push(this.entite.biere);
            this.autocomplete.searchInput.nativeElement.value = this.entite.biere.nom;
        });
    }

    /** ng-autocomplete */
    selectEvent(item) {
        this.entite.idBiere = item ? item.idBiere : null;
    }
     
    onChangeSearch(nom: string) {
        this.biereService.getBiereByNom(nom).subscribe(res => this.biereList = res);
    }
    /** fin */

    enregistrer() {
        if(this.signupForm.valid && !this.signupForm.pending) {

            if(this.isAddState()) {
                
                this.service.post(this.entite)
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
                
                this.service.put(this.entite, this.entite.idPrix)
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
            sessionStorage.removeItem('prixFilter');
        }
    }

    private success(message: string) {
        sessionStorage.setItem('entiteEnregistre', 'true');
        this.alertService.success(message, true);
        this.router.navigate(['prix']);
    }

    annuler() {
        this.entite = new PrixModel();
        this.router.navigate(['prix']);    
    }

}