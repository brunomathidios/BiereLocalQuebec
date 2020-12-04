import { Component, Input } from '@angular/core';

@Component({
    selector: 'champ-message',
    templateUrl: './champ-message.component.html'
})
export class ChampMessageComponent {

    @Input() text: string = '';
}