/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { WhiproundTestModule } from '../../../test.module';
import { PreapprovalUpdateComponent } from 'app/entities/preapproval/preapproval-update.component';
import { PreapprovalService } from 'app/entities/preapproval/preapproval.service';
import { Preapproval } from 'app/shared/model/preapproval.model';

describe('Component Tests', () => {
    describe('Preapproval Management Update Component', () => {
        let comp: PreapprovalUpdateComponent;
        let fixture: ComponentFixture<PreapprovalUpdateComponent>;
        let service: PreapprovalService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [WhiproundTestModule],
                declarations: [PreapprovalUpdateComponent]
            })
                .overrideTemplate(PreapprovalUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(PreapprovalUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PreapprovalService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Preapproval(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.preapproval = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.update).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );

            it(
                'Should call create service on save for new entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Preapproval();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.preapproval = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.create).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );
        });
    });
});
