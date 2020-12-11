import { Component, ElementRef, OnInit, ViewChild } from "@angular/core";
import { FormBuilder, FormControl, Validators } from "@angular/forms";
import { DomSanitizer, SafeUrl } from "@angular/platform-browser";
import { Router, ActivatedRoute } from "@angular/router";
import { AlertService } from "src/app/core/alert/alert.service";
import { EnumDTOModel } from "src/app/core/model/enum-dto.model";
import { PlatformDetectorService } from "src/app/core/platform-detector/platform-detector.service";
import { CreateBaseComponent } from "../../base/create-base.component";
import { TypeBiereModel } from "../../type-biere/enregistrement/type-biere.model";
import { BiereModel } from "../biere.model";
import { SearchBiereService } from "../search/search-biere.service";
import { CreateBiereService } from "./create-biere.service";

@Component({
    templateUrl: './create-biere.component.html'
})
export class CreateBiereComponent extends CreateBaseComponent implements OnInit {

    @ViewChild('nomInput') 
    nomInput: ElementRef<HTMLInputElement>;

    @ViewChild('autocomplete')
    autocomplete: any;

    entite: BiereModel = new BiereModel();
    amertumeList: EnumDTOModel[] = [];
    typeBiereList: TypeBiereModel[] = [];
    keyword = 'nom';
    file: File;
    preview: string;
    imageUrl: SafeUrl;

    constructor(
        private formBuilder: FormBuilder,
        private createBiereService: CreateBiereService,
        private searchBiereService: SearchBiereService,
        private router: Router,
        private platformDetectorService: PlatformDetectorService,
        private alertService: AlertService,
        private activatedRoute: ActivatedRoute,
        private sanitizer: DomSanitizer) {
            super();
    }

    ngOnInit(): void {
        
        this.stateModeCode = parseInt(this.activatedRoute.snapshot.params['state'],10);

        this.signupForm = this.formBuilder.group({
            nom: new FormControl(
                { value: '', disabled: this.isViewState() },
                [ Validators.required, Validators.maxLength(150) ]
            ), 
            origine: new FormControl(
                { value: '', disabled: this.isViewState() },
                [ Validators.required, Validators.maxLength(200) ]
            ),
            tauxAlcool: new FormControl(
                { value: '', disabled: this.isViewState() },
                [ Validators.required ]
            ),
            ibu: new FormControl(
                { value: '', disabled: this.isViewState() },
                [ Validators.required, Validators.maxLength(3) ]
            ),
            amertume: new FormControl(
                { value: '', disabled: this.isViewState() },
                [ Validators.required ]
            ),
            description: new FormControl(
                { value: '', disabled: this.isViewState() },
                [ Validators.maxLength(4000) ]
            ),
            idTypeBiere: new FormControl(
                { value: '', disabled: this.isViewState() },
                [ Validators.required ]
            ),
            photo: new FormControl(
                { value: '', disabled: this.isViewState() }
            )
        });

        this.searchBiereService.getAmertumeList().subscribe(res => {
            this.amertumeList = res['enumList'];
        });
        
        this.paramId = this.activatedRoute.snapshot.params['id'];

        if(this.paramId !== undefined && (this.isViewState() || this.isEditState()) ) {
            this.findEntiteById(this.paramId);
        }

        if(this.isAddState() && this.platformDetectorService.isPlatformBrowser()) {
            this.nomInput.nativeElement.focus();
            this.titleForm = 'Créer nouvelle bière';
            this.entite.amertume = null;
        }

        if(this.isViewState()){
            this.titleForm = 'Visualiser bière';
        }

        if(this.isEditState()) {
            this.titleForm = 'Modifier bière';
        }
    }

    private findEntiteById(id: number) {
        this.createBiereService.getById(id).subscribe(res => {
            this.entite = res;
            this.typeBiereList.push(this.entite.typeBiere);
            this.autocomplete.searchInput.nativeElement.value = this.entite.typeBiere.nom;

            if(this.entite.photo) {
                let objectURL = 'data:image/png;base64,' + this.entite.photo;
                this.imageUrl = this.sanitizer.bypassSecurityTrustUrl(objectURL);
            }
        });
    }

    enregistrer() {
        if(this.signupForm.valid && !this.signupForm.pending) {

            if(this.isAddState()) {
                
                this.createBiereService.post(this.entite, this.file)
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
            } else {
                
                this.createBiereService.put(this.entite, this.file, this.paramId)
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
            sessionStorage.removeItem('biereFilter');
        }
    }

    private success(message: string) {
        sessionStorage.setItem('entiteEnregistre', 'true');
        this.alertService.success(message, true);
        this.router.navigate(['biere']);
    }

    annuler() {
        this.entite = new BiereModel();
        this.router.navigate(['biere']);    
    }

    /** ng-autocomplete */
    selectEvent(item) {
        this.entite.idTypeBiere = item ? item.idTypeBiere : null;
    }
     
    onChangeSearch(nom: string) {
        this.searchBiereService.getTypeBiereByNom(nom).subscribe(res => this.typeBiereList = res);
    }
    /** fin */

    handleFile(file: File) {
        this.file = file;
        const reader = new FileReader();
        reader.onload = (event: any) => this.preview = event.target.result;
        reader.readAsDataURL(file);

        this.imageUrl = null;
      }

}