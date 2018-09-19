import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IComentario } from 'app/shared/model/comentario.model';
import { ComentarioService } from './comentario.service';
import { ICliente } from 'app/shared/model/cliente.model';
import { ClienteService } from 'app/entities/cliente';
import { IAnuncio } from 'app/shared/model/anuncio.model';
import { AnuncioService } from 'app/entities/anuncio';

@Component({
    selector: 'jhi-comentario-update',
    templateUrl: './comentario-update.component.html'
})
export class ComentarioUpdateComponent implements OnInit {
    private _comentario: IComentario;
    isSaving: boolean;

    clientes: ICliente[];

    anuncios: IAnuncio[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private comentarioService: ComentarioService,
        private clienteService: ClienteService,
        private anuncioService: AnuncioService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ comentario }) => {
            this.comentario = comentario;
        });
        this.clienteService.query().subscribe(
            (res: HttpResponse<ICliente[]>) => {
                this.clientes = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.anuncioService.query().subscribe(
            (res: HttpResponse<IAnuncio[]>) => {
                this.anuncios = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.comentario.id !== undefined) {
            this.subscribeToSaveResponse(this.comentarioService.update(this.comentario));
        } else {
            this.subscribeToSaveResponse(this.comentarioService.create(this.comentario));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IComentario>>) {
        result.subscribe((res: HttpResponse<IComentario>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackClienteById(index: number, item: ICliente) {
        return item.id;
    }

    trackAnuncioById(index: number, item: IAnuncio) {
        return item.id;
    }
    get comentario() {
        return this._comentario;
    }

    set comentario(comentario: IComentario) {
        this._comentario = comentario;
    }
}
