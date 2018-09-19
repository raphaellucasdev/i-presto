import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IPrestador } from 'app/shared/model/prestador.model';
import { PrestadorService } from './prestador.service';
import { IPessoa } from 'app/shared/model/pessoa.model';
import { PessoaService } from 'app/entities/pessoa';

@Component({
    selector: 'jhi-prestador-update',
    templateUrl: './prestador-update.component.html'
})
export class PrestadorUpdateComponent implements OnInit {
    private _prestador: IPrestador;
    isSaving: boolean;

    pessoas: IPessoa[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private prestadorService: PrestadorService,
        private pessoaService: PessoaService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ prestador }) => {
            this.prestador = prestador;
        });
        this.pessoaService.query().subscribe(
            (res: HttpResponse<IPessoa[]>) => {
                this.pessoas = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.prestador.id !== undefined) {
            this.subscribeToSaveResponse(this.prestadorService.update(this.prestador));
        } else {
            this.subscribeToSaveResponse(this.prestadorService.create(this.prestador));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IPrestador>>) {
        result.subscribe((res: HttpResponse<IPrestador>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackPessoaById(index: number, item: IPessoa) {
        return item.id;
    }
    get prestador() {
        return this._prestador;
    }

    set prestador(prestador: IPrestador) {
        this._prestador = prestador;
    }
}
