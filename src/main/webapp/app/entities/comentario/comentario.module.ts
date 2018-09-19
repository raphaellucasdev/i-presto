import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { IPrestoSharedModule } from 'app/shared';
import {
    ComentarioComponent,
    ComentarioDetailComponent,
    ComentarioUpdateComponent,
    ComentarioDeletePopupComponent,
    ComentarioDeleteDialogComponent,
    comentarioRoute,
    comentarioPopupRoute
} from './';

const ENTITY_STATES = [...comentarioRoute, ...comentarioPopupRoute];

@NgModule({
    imports: [IPrestoSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        ComentarioComponent,
        ComentarioDetailComponent,
        ComentarioUpdateComponent,
        ComentarioDeleteDialogComponent,
        ComentarioDeletePopupComponent
    ],
    entryComponents: [ComentarioComponent, ComentarioUpdateComponent, ComentarioDeleteDialogComponent, ComentarioDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class IPrestoComentarioModule {}
