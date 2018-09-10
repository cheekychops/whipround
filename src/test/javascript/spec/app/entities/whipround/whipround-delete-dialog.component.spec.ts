/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { WhiproundTestModule } from '../../../test.module';
import { WhiproundDeleteDialogComponent } from 'app/entities/whipround/whipround-delete-dialog.component';
import { WhiproundService } from 'app/entities/whipround/whipround.service';

describe('Component Tests', () => {
    describe('Whipround Management Delete Component', () => {
        let comp: WhiproundDeleteDialogComponent;
        let fixture: ComponentFixture<WhiproundDeleteDialogComponent>;
        let service: WhiproundService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [WhiproundTestModule],
                declarations: [WhiproundDeleteDialogComponent]
            })
                .overrideTemplate(WhiproundDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(WhiproundDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(WhiproundService);
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
