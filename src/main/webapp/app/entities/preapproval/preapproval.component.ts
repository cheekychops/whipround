import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IPreapproval } from 'app/shared/model/preapproval.model';
import { Principal } from 'app/core';
import { PreapprovalService } from './preapproval.service';

@Component({
    selector: 'jhi-preapproval',
    templateUrl: './preapproval.component.html'
})
export class PreapprovalComponent implements OnInit, OnDestroy {
    preapprovals: IPreapproval[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private preapprovalService: PreapprovalService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.preapprovalService.query().subscribe(
            (res: HttpResponse<IPreapproval[]>) => {
                this.preapprovals = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInPreapprovals();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IPreapproval) {
        return item.id;
    }

    registerChangeInPreapprovals() {
        this.eventSubscriber = this.eventManager.subscribe('preapprovalListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
