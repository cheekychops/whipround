import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { WhiproundWhiproundModule } from './whipround/whipround.module';
import { WhiproundContributionModule } from './contribution/contribution.module';
import { WhiproundPreapprovalModule } from './preapproval/preapproval.module';
import { WhiproundPersonModule } from './person/person.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    // prettier-ignore
    imports: [
        WhiproundWhiproundModule,
        WhiproundContributionModule,
        WhiproundPreapprovalModule,
        WhiproundPersonModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class WhiproundEntityModule {}
