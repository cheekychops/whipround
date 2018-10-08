import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IWhipround } from 'app/shared/model/whipround.model';

@Component({
    selector: 'jhi-whipround-detail',
    templateUrl: './whipround-detail.component.html'
})
export class WhiproundDetailComponent implements OnInit {
    whipround: IWhipround;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ whipround }) => {
            this.whipround = whipround;
        });
    }

    previousState() {
        window.history.back();
    }
}
