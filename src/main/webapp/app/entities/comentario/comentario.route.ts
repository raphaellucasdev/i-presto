import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Comentario } from 'app/shared/model/comentario.model';
import { ComentarioService } from './comentario.service';
import { ComentarioComponent } from './comentario.component';
import { ComentarioDetailComponent } from './comentario-detail.component';
import { ComentarioUpdateComponent } from './comentario-update.component';
import { ComentarioDeletePopupComponent } from './comentario-delete-dialog.component';
import { IComentario } from 'app/shared/model/comentario.model';

@Injectable({ providedIn: 'root' })
export class ComentarioResolve implements Resolve<IComentario> {
    constructor(private service: ComentarioService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((comentario: HttpResponse<Comentario>) => comentario.body));
        }
        return of(new Comentario());
    }
}

export const comentarioRoute: Routes = [
    {
        path: 'comentario',
        component: ComentarioComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Comentarios'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'comentario/:id/view',
        component: ComentarioDetailComponent,
        resolve: {
            comentario: ComentarioResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Comentarios'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'comentario/new',
        component: ComentarioUpdateComponent,
        resolve: {
            comentario: ComentarioResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Comentarios'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'comentario/:id/edit',
        component: ComentarioUpdateComponent,
        resolve: {
            comentario: ComentarioResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Comentarios'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const comentarioPopupRoute: Routes = [
    {
        path: 'comentario/:id/delete',
        component: ComentarioDeletePopupComponent,
        resolve: {
            comentario: ComentarioResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Comentarios'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
