import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';

import { IContribution } from 'app/shared/model/contribution.model';
import { ContributionService } from './contribution.service';
import { IPreapproval } from 'app/shared/model/preapproval.model';
import { PreapprovalService } from 'app/entities/preapproval';
import { IWhipround } from 'app/shared/model/whipround.model';
import { WhiproundService } from 'app/entities/whipround';
import { IPerson } from 'app/shared/model/person.model';
import { PersonService } from 'app/entities/person';

@Component({
    selector: 'jhi-contribution-update',
    templateUrl: './contribution-update.component.html'
})
export class ContributionUpdateComponent implements OnInit {
    private _contribution: IContribution;
    isSaving: boolean;

    preapprovals: IPreapproval[];

    whiprounds: IWhipround[];

    people: IPerson[];
    date: string;

    constructor(
        private jhiAlertService: JhiAlertService,
        private contributionService: ContributionService,
        private preapprovalService: PreapprovalService,
        private whiproundService: WhiproundService,
        private personService: PersonService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ contribution }) => {
            this.contribution = contribution;
        });
        this.preapprovalService.query({ filter: 'contribution-is-null' }).subscribe(
            (res: HttpResponse<IPreapproval[]>) => {
                if (!this.contribution.preapprovalId) {
                    this.preapprovals = res.body;
                } else {
                    this.preapprovalService.find(this.contribution.preapprovalId).subscribe(
                        (subRes: HttpResponse<IPreapproval>) => {
                            this.preapprovals = [subRes.body].concat(res.body);
                        },
                        (subRes: HttpErrorResponse) => this.onError(subRes.message)
                    );
                }
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.whiproundService.query().subscribe(
            (res: HttpResponse<IWhipround[]>) => {
                this.whiprounds = res.body;
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
        this.contribution.date = moment(this.date, DATE_TIME_FORMAT);
        if (this.contribution.id !== undefined) {
            this.subscribeToSaveResponse(this.contributionService.update(this.contribution));
        } else {
            this.subscribeToSaveResponse(this.contributionService.create(this.contribution));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IContribution>>) {
        result.subscribe((res: HttpResponse<IContribution>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackPreapprovalById(index: number, item: IPreapproval) {
        return item.id;
    }

    trackWhiproundById(index: number, item: IWhipround) {
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
    get contribution() {
        return this._contribution;
    }

    set contribution(contribution: IContribution) {
        this._contribution = contribution;
        this.date = moment(contribution.date).format(DATE_TIME_FORMAT);
    }
}
