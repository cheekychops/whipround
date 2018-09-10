import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IContribution } from 'app/shared/model/contribution.model';

type EntityResponseType = HttpResponse<IContribution>;
type EntityArrayResponseType = HttpResponse<IContribution[]>;

@Injectable({ providedIn: 'root' })
export class ContributionService {
    private resourceUrl = SERVER_API_URL + 'api/contributions';

    constructor(private http: HttpClient) {}

    create(contribution: IContribution): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(contribution);
        return this.http
            .post<IContribution>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(contribution: IContribution): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(contribution);
        return this.http
            .put<IContribution>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IContribution>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IContribution[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    private convertDateFromClient(contribution: IContribution): IContribution {
        const copy: IContribution = Object.assign({}, contribution, {
            date: contribution.date != null && contribution.date.isValid() ? contribution.date.toJSON() : null
        });
        return copy;
    }

    private convertDateFromServer(res: EntityResponseType): EntityResponseType {
        res.body.date = res.body.date != null ? moment(res.body.date) : null;
        return res;
    }

    private convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        res.body.forEach((contribution: IContribution) => {
            contribution.date = contribution.date != null ? moment(contribution.date) : null;
        });
        return res;
    }
}
