import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IAnuncio } from 'app/shared/model/anuncio.model';
import { AnuncioService } from './anuncio.service';
import { IPrestador } from 'app/shared/model/prestador.model';
import { PrestadorService } from 'app/entities/prestador';

@Component({
    selector: 'jhi-anuncio-update',
    templateUrl: './anuncio-update.component.html'
})
export class AnuncioUpdateComponent implements OnInit {
    private _anuncio: IAnuncio;
    isSaving: boolean;

    prestadors: IPrestador[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private anuncioService: AnuncioService,
        private prestadorService: PrestadorService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ anuncio }) => {
            this.anuncio = anuncio;
        });
        this.prestadorService.query().subscribe(
            (res: HttpResponse<IPrestador[]>) => {
                this.prestadors = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.anuncio.id !== undefined) {
            this.subscribeToSaveResponse(this.anuncioService.update(this.anuncio));
        } else {
            this.subscribeToSaveResponse(this.anuncioService.create(this.anuncio));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IAnuncio>>) {
        result.subscribe((res: HttpResponse<IAnuncio>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackPrestadorById(index: number, item: IPrestador) {
        return item.id;
    }
    get anuncio() {
        return this._anuncio;
    }

    set anuncio(anuncio: IAnuncio) {
        this._anuncio = anuncio;
    }
}
