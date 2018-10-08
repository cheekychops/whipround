import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IWhipround } from 'app/shared/model/whipround.model';

type EntityResponseType = HttpResponse<IWhipround>;
type EntityArrayResponseType = HttpResponse<IWhipround[]>;

@Injectable({ providedIn: 'root' })
export class WhiproundService {
    private resourceUrl = SERVER_API_URL + 'api/whiprounds';

    constructor(private http: HttpClient) {}

    create(whipround: IWhipround): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(whipround);
        return this.http
            .post<IWhipround>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(whipround: IWhipround): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(whipround);
        return this.http
            .put<IWhipround>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IWhipround>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IWhipround[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    private convertDateFromClient(whipround: IWhipround): IWhipround {
        const copy: IWhipround = Object.assign({}, whipround, {
            startDate: whipround.startDate != null && whipround.startDate.isValid() ? whipround.startDate.toJSON() : null,
            endDate: whipround.endDate != null && whipround.endDate.isValid() ? whipround.endDate.toJSON() : null
        });
        return copy;
    }

    private convertDateFromServer(res: EntityResponseType): EntityResponseType {
        res.body.startDate = res.body.startDate != null ? moment(res.body.startDate) : null;
        res.body.endDate = res.body.endDate != null ? moment(res.body.endDate) : null;
        return res;
    }

    private convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        res.body.forEach((whipround: IWhipround) => {
            whipround.startDate = whipround.startDate != null ? moment(whipround.startDate) : null;
            whipround.endDate = whipround.endDate != null ? moment(whipround.endDate) : null;
        });
        return res;
    }
}
