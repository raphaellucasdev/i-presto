import { NgModule } from '@angular/core';

import { IPrestoSharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent } from './';

@NgModule({
    imports: [IPrestoSharedLibsModule],
    declarations: [JhiAlertComponent, JhiAlertErrorComponent],
    exports: [IPrestoSharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent]
})
export class IPrestoSharedCommonModule {}
