import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IPerson } from 'app/shared/model/person.model';
import { PersonService } from './person.service';
import { IUser, UserService } from 'app/core';
import { IContribution } from 'app/shared/model/contribution.model';
import { ContributionService } from 'app/entities/contribution';
import { IWhipround } from 'app/shared/model/whipround.model';
import { WhiproundService } from 'app/entities/whipround';

@Component({
    selector: 'jhi-person-update',
    templateUrl: './person-update.component.html'
})
export class PersonUpdateComponent implements OnInit {
    private _person: IPerson;
    isSaving: boolean;

    users: IUser[];

    contributions: IContribution[];

    whiprounds: IWhipround[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private personService: PersonService,
        private userService: UserService,
        private contributionService: ContributionService,
        private whiproundService: WhiproundService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ person }) => {
            this.person = person;
        });
        this.userService.query().subscribe(
            (res: HttpResponse<IUser[]>) => {
                this.users = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.contributionService.query().subscribe(
            (res: HttpResponse<IContribution[]>) => {
                this.contributions = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.whiproundService.query().subscribe(
            (res: HttpResponse<IWhipround[]>) => {
                this.whiprounds = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.person.id !== undefined) {
            this.subscribeToSaveResponse(this.personService.update(this.person));
        } else {
            this.subscribeToSaveResponse(this.personService.create(this.person));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IPerson>>) {
        result.subscribe((res: HttpResponse<IPerson>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackUserById(index: number, item: IUser) {
        return item.id;
    }

    trackContributionById(index: number, item: IContribution) {
        return item.id;
    }

    trackWhiproundById(index: number, item: IWhipround) {
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
    get person() {
        return this._person;
    }

    set person(person: IPerson) {
        this._person = person;
    }
}
