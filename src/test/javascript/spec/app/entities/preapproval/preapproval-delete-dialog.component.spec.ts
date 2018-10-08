/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { WhiproundTestModule } from '../../../test.module';
import { PreapprovalDeleteDialogComponent } from 'app/entities/preapproval/preapproval-delete-dialog.component';
import { PreapprovalService } from 'app/entities/preapproval/preapproval.service';

describe('Component Tests', () => {
    describe('Preapproval Management Delete Component', () => {
        let comp: PreapprovalDeleteDialogComponent;
        let fixture: ComponentFixture<PreapprovalDeleteDialogComponent>;
        let service: PreapprovalService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [WhiproundTestModule],
                declarations: [PreapprovalDeleteDialogComponent]
            })
                .overrideTemplate(PreapprovalDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(PreapprovalDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PreapprovalService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete', inject(
                [],
                fakeAsync(() => {
                    // GIVEN
                    spyOn(service, 'delete').and.returnValue(of({}));

                    // WHEN
                    comp.confirmDelete(123);
                    tick();

                    // THEN
                    expect(service.delete).toHaveBeenCalledWith(123);
                    expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                })
            ));
        });
    });
});
