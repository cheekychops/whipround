import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';

import { IWhipround } from 'app/shared/model/whipround.model';
import { WhiproundService } from './whipround.service';
import { IContribution } from 'app/shared/model/contribution.model';
import { ContributionService } from 'app/entities/contribution';
import { IPerson } from 'app/shared/model/person.model';
import { PersonService } from 'app/entities/person';

@Component({
    selector: 'jhi-whipround-update',
    templateUrl: './whipround-update.component.html'
})
export class WhiproundUpdateComponent implements OnInit {
    private _whipround: IWhipround;
    isSaving: boolean;

    contributions: IContribution[];

    people: IPerson[];
    startDate: string;
    endDate: string;

    constructor(
        private jhiAlertService: JhiAlertService,
        private whiproundService: WhiproundService,
        private contributionService: ContributionService,
        private personService: PersonService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ whipround }) => {
            this.whipround = whipround;
        });
        this.contributionService.query().subscribe(
            (res: HttpResponse<IContribution[]>) => {
                this.contributions = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.personService.query().subscribe(
            (res: HttpResponse<IPerson[]>) => {
                this.people = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        this.whipround.startDate = moment(this.startDate, DATE_TIME_FORMAT);
        this.whipround.endDate = moment(this.endDate, DATE_TIME_FORMAT);
        if (this.whipround.id !== undefined) {
            this.subscribeToSaveResponse(this.whiproundService.update(this.whipround));
        } else {
            this.subscribeToSaveResponse(this.whiproundService.create(this.whipround));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IWhipround>>) {
        result.subscribe((res: HttpResponse<IWhipround>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackPersonById(index: number, item: IPerson) {
        return item.id;
    }

    getSelected(selectedVals: Array<any>, option: any) {
        if (selectedVals) {
            for (let i = 0; i < selectedVals.length; i++) {
                if (option.id === selectedVals[i].id) {
                    return selectedVals[i];
                }
            }
        }
        return option;
    }
    get whipround() {
        return this._whipround;
    }

    set whipround(whipround: IWhipround) {
        this._whipround = whipround;
        this.startDate = moment(whipround.startDate).format(DATE_TIME_FORMAT);
        this.endDate = moment(whipround.endDate).format(DATE_TIME_FORMAT);
    }
}
