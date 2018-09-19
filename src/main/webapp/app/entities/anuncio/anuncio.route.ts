import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Anuncio } from 'app/shared/model/anuncio.model';
import { AnuncioService } from './anuncio.service';
import { AnuncioComponent } from './anuncio.component';
import { AnuncioDetailComponent } from './anuncio-detail.component';
import { AnuncioUpdateComponent } from './anuncio-update.component';
import { AnuncioDeletePopupComponent } from './anuncio-delete-dialog.component';
import { IAnuncio } from 'app/shared/model/anuncio.model';

@Injectable({ providedIn: 'root' })
export class AnuncioResolve implements Resolve<IAnuncio> {
    constructor(private service: AnuncioService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((anuncio: HttpResponse<Anuncio>) => anuncio.body));
        }
        return of(new Anuncio());
    }
}

export const anuncioRoute: Routes = [
    {
        path: 'anuncio',
        component: AnuncioComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Anuncios'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'anuncio/:id/view',
        component: AnuncioDetailComponent,
        resolve: {
            anuncio: AnuncioResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Anuncios'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'anuncio/new',
        component: AnuncioUpdateComponent,
        resolve: {
            anuncio: AnuncioResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Anuncios'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'anuncio/:id/edit',
        component: AnuncioUpdateComponent,
        resolve: {
            anuncio: AnuncioResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Anuncios'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const anuncioPopupRoute: Routes = [
    {
        path: 'anuncio/:id/delete',
        component: AnuncioDeletePopupComponent,
        resolve: {
            anuncio: AnuncioResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Anuncios'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
