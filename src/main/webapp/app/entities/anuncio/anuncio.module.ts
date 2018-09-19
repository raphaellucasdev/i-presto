import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { IPrestoSharedModule } from 'app/shared';
import {
    AnuncioComponent,
    AnuncioDetailComponent,
    AnuncioUpdateComponent,
    AnuncioDeletePopupComponent,
    AnuncioDeleteDialogComponent,
    anuncioRoute,
    anuncioPopupRoute
} from './';

const ENTITY_STATES = [...anuncioRoute, ...anuncioPopupRoute];

@NgModule({
    imports: [IPrestoSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        AnuncioComponent,
        AnuncioDetailComponent,
        AnuncioUpdateComponent,
        AnuncioDeleteDialogComponent,
        AnuncioDeletePopupComponent
    ],
    entryComponents: [AnuncioComponent, AnuncioUpdateComponent, AnuncioDeleteDialogComponent, AnuncioDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class IPrestoAnuncioModule {}
