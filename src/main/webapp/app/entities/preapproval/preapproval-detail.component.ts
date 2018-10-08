import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPreapproval } from 'app/shared/model/preapproval.model';

@Component({
    selector: 'jhi-preapproval-detail',
    templateUrl: './preapproval-detail.component.html'
})
export class PreapprovalDetailComponent implements OnInit {
    preapproval: IPreapproval;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ preapproval }) => {
            this.preapproval = preapproval;
        });
    }

    previousState() {
        window.history.back();
    }
}
