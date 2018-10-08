import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { WhiproundSharedModule } from 'app/shared';
import {
    PreapprovalComponent,
    PreapprovalDetailComponent,
    PreapprovalUpdateComponent,
    PreapprovalDeletePopupComponent,
    PreapprovalDeleteDialogComponent,
    preapprovalRoute,
    preapprovalPopupRoute
} from './';

const ENTITY_STATES = [...preapprovalRoute, ...preapprovalPopupRoute];

@NgModule({
    imports: [WhiproundSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        PreapprovalComponent,
        PreapprovalDetailComponent,
        PreapprovalUpdateComponent,
        PreapprovalDeleteDialogComponent,
        PreapprovalDeletePopupComponent
    ],
    entryComponents: [PreapprovalComponent, PreapprovalUpdateComponent, PreapprovalDeleteDialogComponent, PreapprovalDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class WhiproundPreapprovalModule {}
