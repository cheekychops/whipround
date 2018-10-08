import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { WhiproundSharedModule } from 'app/shared';
import {
    ContributionComponent,
    ContributionDetailComponent,
    ContributionUpdateComponent,
    ContributionDeletePopupComponent,
    ContributionDeleteDialogComponent,
    contributionRoute,
    contributionPopupRoute
} from './';

const ENTITY_STATES = [...contributionRoute, ...contributionPopupRoute];

@NgModule({
    imports: [WhiproundSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        ContributionComponent,
        ContributionDetailComponent,
        ContributionUpdateComponent,
        ContributionDeleteDialogComponent,
        ContributionDeletePopupComponent
    ],
    entryComponents: [
        ContributionComponent,
        ContributionUpdateComponent,
        ContributionDeleteDialogComponent,
        ContributionDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class WhiproundContributionModule {}
