import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IPreapproval } from 'app/shared/model/preapproval.model';
import { PreapprovalService } from './preapproval.service';
import { IContribution } from 'app/shared/model/contribution.model';
import { ContributionService } from 'app/entities/contribution';

@Component({
    selector: 'jhi-preapproval-update',
    templateUrl: './preapproval-update.component.html'
})
export class PreapprovalUpdateComponent implements OnInit {
    private _preapproval: IPreapproval;
    isSaving: boolean;

    contributions: IContribution[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private preapprovalService: PreapprovalService,
        private contributionService: ContributionService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ preapproval }) => {
            this.preapproval = preapproval;
        });
        this.contributionService.query().subscribe(
            (res: HttpResponse<IContribution[]>) => {
                this.contributions = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.preapproval.id !== undefined) {
            this.subscribeToSaveResponse(this.preapprovalService.update(this.preapproval));
        } else {
            this.subscribeToSaveResponse(this.preapprovalService.create(this.preapproval));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IPreapproval>>) {
        result.subscribe((res: HttpResponse<IPreapproval>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackContributionById(index: number, item: IContribution) {
        return item.id;
    }
    get preapproval() {
        return this._preapproval;
    }

    set preapproval(preapproval: IPreapproval) {
        this._preapproval = preapproval;
    }
}
