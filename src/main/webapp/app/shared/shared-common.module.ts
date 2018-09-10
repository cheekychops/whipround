import { NgModule } from '@angular/core';

import { WhiproundSharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent } from './';

@NgModule({
    imports: [WhiproundSharedLibsModule],
    declarations: [JhiAlertComponent, JhiAlertErrorComponent],
    exports: [WhiproundSharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent]
})
export class WhiproundSharedCommonModule {}
