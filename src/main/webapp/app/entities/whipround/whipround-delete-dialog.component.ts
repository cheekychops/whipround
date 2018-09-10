import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IWhipround } from 'app/shared/model/whipround.model';
import { WhiproundService } from './whipround.service';

@Component({
    selector: 'jhi-whipround-delete-dialog',
    templateUrl: './whipround-delete-dialog.component.html'
})
export class WhiproundDeleteDialogComponent {
    whipround: IWhipround;

    constructor(private whiproundService: WhiproundService, public activeModal: NgbActiveModal, private eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.whiproundService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'whiproundListModification',
                content: 'Deleted an whipround'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-whipround-delete-popup',
    template: ''
})
export class WhiproundDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ whipround }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(WhiproundDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.whipround = whipround;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    }
                );
            }, 0);
        });
    }

    ngOnDestroy() {
        this.ngbModalRef = null;
    }
}
