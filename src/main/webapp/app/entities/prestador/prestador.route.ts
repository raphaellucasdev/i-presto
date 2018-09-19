import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Prestador } from 'app/shared/model/prestador.model';
import { PrestadorService } from './prestador.service';
import { PrestadorComponent } from './prestador.component';
import { PrestadorDetailComponent } from './prestador-detail.component';
import { PrestadorUpdateComponent } from './prestador-update.component';
import { PrestadorDeletePopupComponent } from './prestador-delete-dialog.component';
import { IPrestador } from 'app/shared/model/prestador.model';

@Injectable({ providedIn: 'root' })
export class PrestadorResolve implements Resolve<IPrestador> {
    constructor(private service: PrestadorService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((prestador: HttpResponse<Prestador>) => prestador.body));
        }
        return of(new Prestador());
    }
}

export const prestadorRoute: Routes = [
    {
        path: 'prestador',
        component: PrestadorComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Prestadors'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'prestador/:id/view',
        component: PrestadorDetailComponent,
        resolve: {
            prestador: PrestadorResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Prestadors'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'prestador/new',
        component: PrestadorUpdateComponent,
        resolve: {
            prestador: PrestadorResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Prestadors'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'prestador/:id/edit',
        component: PrestadorUpdateComponent,
        resolve: {
            prestador: PrestadorResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Prestadors'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const prestadorPopupRoute: Routes = [
    {
        path: 'prestador/:id/delete',
        component: PrestadorDeletePopupComponent,
        resolve: {
            prestador: PrestadorResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Prestadors'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
