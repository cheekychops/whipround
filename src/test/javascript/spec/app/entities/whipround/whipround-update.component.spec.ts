/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { WhiproundTestModule } from '../../../test.module';
import { WhiproundUpdateComponent } from 'app/entities/whipround/whipround-update.component';
import { WhiproundService } from 'app/entities/whipround/whipround.service';
import { Whipround } from 'app/shared/model/whipround.model';

describe('Component Tests', () => {
    describe('Whipround Management Update Component', () => {
        let comp: WhiproundUpdateComponent;
        let fixture: ComponentFixture<WhiproundUpdateComponent>;
        let service: WhiproundService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [WhiproundTestModule],
                declarations: [WhiproundUpdateComponent]
            })
                .overrideTemplate(WhiproundUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(WhiproundUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(WhiproundService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Whipround(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.whipround = entity;
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
                    const entity = new Whipround();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.whipround = entity;
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
