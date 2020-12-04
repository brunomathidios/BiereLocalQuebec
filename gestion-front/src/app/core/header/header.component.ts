import { Component, OnInit } from "@angular/core";
import { Router } from "@angular/router";

@Component({
    selector: 'gestion-header',
    templateUrl: './header.component.html'
})
export class HeaderComponent implements OnInit {

    typeBiere = false;
    biere = false;
    boxBiere = false;
    rapports = false;

    constructor(
        private router: Router) {}

    ngOnInit(): void {
        let path = location.hash.split("/")[1];

        if(path === 'type-biere') {
            this.typeBiere = true;
        } else if (path === 'biere') {
            this.biere = true;
        }
    }

    preparerTypeBiere() {
        sessionStorage.clear();
        this.typeBiere = true;
        this.biere = false;
        this.boxBiere = false;
        this.rapports = false;
        this.router.navigate(['type-biere']);
    }

    preparerBiere() {
        sessionStorage.clear();
        this.typeBiere = false;
        this.biere = true;
        this.boxBiere = false;
        this.rapports = false;
        this.router.navigate(['biere']);
    }

    preparerBoxBiere() {
        sessionStorage.clear();
        this.typeBiere = false;
        this.biere = false;
        this.boxBiere = true;
        this.rapports = false;
        //this.router.navigate(['type-biere']);
    }

    preparerRapports() {
        sessionStorage.clear();
        this.typeBiere = false;
        this.biere = false;
        this.boxBiere = false;
        this.rapports = true;
        //this.router.navigate(['biere']);
    }
}