import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Preapproval } from 'app/shared/model/preapproval.model';
import { PreapprovalService } from './preapproval.service';
import { PreapprovalComponent } from './preapproval.component';
import { PreapprovalDetailComponent } from './preapproval-detail.component';
import { PreapprovalUpdateComponent } from './preapproval-update.component';
import { PreapprovalDeletePopupComponent } from './preapproval-delete-dialog.component';
import { IPreapproval } from 'app/shared/model/preapproval.model';

@Injectable({ providedIn: 'root' })
export class PreapprovalResolve implements Resolve<IPreapproval> {
    constructor(private service: PreapprovalService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((preapproval: HttpResponse<Preapproval>) => preapproval.body));
        }
        return of(new Preapproval());
    }
}

export const preapprovalRoute: Routes = [
    {
        path: 'preapproval',
        component: PreapprovalComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Preapprovals'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'preapproval/:id/view',
        component: PreapprovalDetailComponent,
        resolve: {
            preapproval: PreapprovalResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Preapprovals'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'preapproval/new',
        component: PreapprovalUpdateComponent,
        resolve: {
            preapproval: PreapprovalResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Preapprovals'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'preapproval/:id/edit',
        component: PreapprovalUpdateComponent,
        resolve: {
            preapproval: PreapprovalResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Preapprovals'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const preapprovalPopupRoute: Routes = [
    {
        path: 'preapproval/:id/delete',
        component: PreapprovalDeletePopupComponent,
        resolve: {
            preapproval: PreapprovalResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Preapprovals'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
