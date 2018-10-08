import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Contribution } from 'app/shared/model/contribution.model';
import { ContributionService } from './contribution.service';
import { ContributionComponent } from './contribution.component';
import { ContributionDetailComponent } from './contribution-detail.component';
import { ContributionUpdateComponent } from './contribution-update.component';
import { ContributionDeletePopupComponent } from './contribution-delete-dialog.component';
import { IContribution } from 'app/shared/model/contribution.model';

@Injectable({ providedIn: 'root' })
export class ContributionResolve implements Resolve<IContribution> {
    constructor(private service: ContributionService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((contribution: HttpResponse<Contribution>) => contribution.body));
        }
        return of(new Contribution());
    }
}

export const contributionRoute: Routes = [
    {
        path: 'contribution',
        component: ContributionComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Contributions'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'contribution/:id/view',
        component: ContributionDetailComponent,
        resolve: {
            contribution: ContributionResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Contributions'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'contribution/new',
        component: ContributionUpdateComponent,
        resolve: {
            contribution: ContributionResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Contributions'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'contribution/:id/edit',
        component: ContributionUpdateComponent,
        resolve: {
            contribution: ContributionResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Contributions'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const contributionPopupRoute: Routes = [
    {
        path: 'contribution/:id/delete',
        component: ContributionDeletePopupComponent,
        resolve: {
            contribution: ContributionResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Contributions'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
