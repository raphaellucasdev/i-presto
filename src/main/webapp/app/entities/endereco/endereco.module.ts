import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { IPrestoSharedModule } from 'app/shared';
import {
    EnderecoComponent,
    EnderecoDetailComponent,
    EnderecoUpdateComponent,
    EnderecoDeletePopupComponent,
    EnderecoDeleteDialogComponent,
    enderecoRoute,
    enderecoPopupRoute
} from './';

const ENTITY_STATES = [...enderecoRoute, ...enderecoPopupRoute];

@NgModule({
    imports: [IPrestoSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        EnderecoComponent,
        EnderecoDetailComponent,
        EnderecoUpdateComponent,
        EnderecoDeleteDialogComponent,
        EnderecoDeletePopupComponent
    ],
    entryComponents: [EnderecoComponent, EnderecoUpdateComponent, EnderecoDeleteDialogComponent, EnderecoDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class IPrestoEnderecoModule {}
