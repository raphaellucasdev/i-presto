import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPrestador } from 'app/shared/model/prestador.model';

@Component({
    selector: 'jhi-prestador-detail',
    templateUrl: './prestador-detail.component.html'
})
export class PrestadorDetailComponent implements OnInit {
    prestador: IPrestador;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ prestador }) => {
            this.prestador = prestador;
        });
    }

    previousState() {
        window.history.back();
    }
}
