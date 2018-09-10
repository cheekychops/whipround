import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { WhiproundSharedModule } from 'app/shared';
import {
    WhiproundComponent,
    WhiproundDetailComponent,
    WhiproundUpdateComponent,
    WhiproundDeletePopupComponent,
    WhiproundDeleteDialogComponent,
    whiproundRoute,
    whiproundPopupRoute
} from './';

const ENTITY_STATES = [...whiproundRoute, ...whiproundPopupRoute];

@NgModule({
    imports: [WhiproundSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        WhiproundComponent,
        WhiproundDetailComponent,
        WhiproundUpdateComponent,
        WhiproundDeleteDialogComponent,
        WhiproundDeletePopupComponent
    ],
    entryComponents: [WhiproundComponent, WhiproundUpdateComponent, WhiproundDeleteDialogComponent, WhiproundDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class WhiproundWhiproundModule {}
