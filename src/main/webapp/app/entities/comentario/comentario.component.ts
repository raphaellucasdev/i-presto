import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IComentario } from 'app/shared/model/comentario.model';
import { Principal } from 'app/core';
import { ComentarioService } from './comentario.service';

@Component({
    selector: 'jhi-comentario',
    templateUrl: './comentario.component.html'
})
export class ComentarioComponent implements OnInit, OnDestroy {
    comentarios: IComentario[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private comentarioService: ComentarioService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.comentarioService.query().subscribe(
            (res: HttpResponse<IComentario[]>) => {
                this.comentarios = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInComentarios();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IComentario) {
        return item.id;
    }

    registerChangeInComentarios() {
        this.eventSubscriber = this.eventManager.subscribe('comentarioListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
