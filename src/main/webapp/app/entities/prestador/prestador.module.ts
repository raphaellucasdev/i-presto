import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { IPrestoSharedModule } from 'app/shared';
import {
    PrestadorComponent,
    PrestadorDetailComponent,
    PrestadorUpdateComponent,
    PrestadorDeletePopupComponent,
    PrestadorDeleteDialogComponent,
    prestadorRoute,
    prestadorPopupRoute
} from './';

const ENTITY_STATES = [...prestadorRoute, ...prestadorPopupRoute];

@NgModule({
    imports: [IPrestoSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        PrestadorComponent,
        PrestadorDetailComponent,
        PrestadorUpdateComponent,
        PrestadorDeleteDialogComponent,
        PrestadorDeletePopupComponent
    ],
    entryComponents: [PrestadorComponent, PrestadorUpdateComponent, PrestadorDeleteDialogComponent, PrestadorDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class IPrestoPrestadorModule {}
