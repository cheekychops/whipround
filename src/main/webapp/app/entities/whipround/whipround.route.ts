import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Whipround } from 'app/shared/model/whipround.model';
import { WhiproundService } from './whipround.service';
import { WhiproundComponent } from './whipround.component';
import { WhiproundDetailComponent } from './whipround-detail.component';
import { WhiproundUpdateComponent } from './whipround-update.component';
import { WhiproundDeletePopupComponent } from './whipround-delete-dialog.component';
import { IWhipround } from 'app/shared/model/whipround.model';

@Injectable({ providedIn: 'root' })
export class WhiproundResolve implements Resolve<IWhipround> {
    constructor(private service: WhiproundService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((whipround: HttpResponse<Whipround>) => whipround.body));
        }
        return of(new Whipround());
    }
}

export const whiproundRoute: Routes = [
    {
        path: 'whipround',
        component: WhiproundComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Whiprounds'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'whipround/:id/view',
        component: WhiproundDetailComponent,
        resolve: {
            whipround: WhiproundResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Whiprounds'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'whipround/new',
        component: WhiproundUpdateComponent,
        resolve: {
            whipround: WhiproundResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Whiprounds'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'whipround/:id/edit',
        component: WhiproundUpdateComponent,
        resolve: {
            whipround: WhiproundResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Whiprounds'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const whiproundPopupRoute: Routes = [
    {
        path: 'whipround/:id/delete',
        component: WhiproundDeletePopupComponent,
        resolve: {
            whipround: WhiproundResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Whiprounds'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
