import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { IPessoa } from 'app/shared/model/pessoa.model';
import { PessoaService } from './pessoa.service';

@Component({
    selector: 'jhi-pessoa-update',
    templateUrl: './pessoa-update.component.html'
})
export class PessoaUpdateComponent implements OnInit {
    private _pessoa: IPessoa;
    isSaving: boolean;

    constructor(private pessoaService: PessoaService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ pessoa }) => {
            this.pessoa = pessoa;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.pessoa.id !== undefined) {
            this.subscribeToSaveResponse(this.pessoaService.update(this.pessoa));
        } else {
            this.subscribeToSaveResponse(this.pessoaService.create(this.pessoa));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IPessoa>>) {
        result.subscribe((res: HttpResponse<IPessoa>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
    get pessoa() {
        return this._pessoa;
    }

    set pessoa(pessoa: IPessoa) {
        this._pessoa = pessoa;
    }
}
