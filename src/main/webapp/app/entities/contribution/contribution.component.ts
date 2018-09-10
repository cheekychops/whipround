import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IContribution } from 'app/shared/model/contribution.model';
import { Principal } from 'app/core';
import { ContributionService } from './contribution.service';

@Component({
    selector: 'jhi-contribution',
    templateUrl: './contribution.component.html'
})
export class ContributionComponent implements OnInit, OnDestroy {
    contributions: IContribution[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private contributionService: ContributionService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.contributionService.query().subscribe(
            (res: HttpResponse<IContribution[]>) => {
                this.contributions = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInContributions();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IContribution) {
        return item.id;
    }

    registerChangeInContributions() {
        this.eventSubscriber = this.eventManager.subscribe('contributionListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
