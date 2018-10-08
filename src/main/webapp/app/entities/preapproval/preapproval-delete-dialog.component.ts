import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPreapproval } from 'app/shared/model/preapproval.model';
import { PreapprovalService } from './preapproval.service';

@Component({
    selector: 'jhi-preapproval-delete-dialog',
    templateUrl: './preapproval-delete-dialog.component.html'
})
export class PreapprovalDeleteDialogComponent {
    preapproval: IPreapproval;

    constructor(
        private preapprovalService: PreapprovalService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.preapprovalService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'preapprovalListModification',
                content: 'Deleted an preapproval'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-preapproval-delete-popup',
    template: ''
})
export class PreapprovalDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ preapproval }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(PreapprovalDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.preapproval = preapproval;
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
