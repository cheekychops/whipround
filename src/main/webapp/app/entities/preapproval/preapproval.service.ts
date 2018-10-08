import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IPreapproval } from 'app/shared/model/preapproval.model';

type EntityResponseType = HttpResponse<IPreapproval>;
type EntityArrayResponseType = HttpResponse<IPreapproval[]>;

@Injectable({ providedIn: 'root' })
export class PreapprovalService {
    private resourceUrl = SERVER_API_URL + 'api/preapprovals';

    constructor(private http: HttpClient) {}

    create(preapproval: IPreapproval): Observable<EntityResponseType> {
        return this.http.post<IPreapproval>(this.resourceUrl, preapproval, { observe: 'response' });
    }

    update(preapproval: IPreapproval): Observable<EntityResponseType> {
        return this.http.put<IPreapproval>(this.resourceUrl, preapproval, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IPreapproval>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IPreapproval[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
