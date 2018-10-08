/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { WhiproundTestModule } from '../../../test.module';
import { PreapprovalComponent } from 'app/entities/preapproval/preapproval.component';
import { PreapprovalService } from 'app/entities/preapproval/preapproval.service';
import { Preapproval } from 'app/shared/model/preapproval.model';

describe('Component Tests', () => {
    describe('Preapproval Management Component', () => {
        let comp: PreapprovalComponent;
        let fixture: ComponentFixture<PreapprovalComponent>;
        let service: PreapprovalService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [WhiproundTestModule],
                declarations: [PreapprovalComponent],
                providers: []
            })
                .overrideTemplate(PreapprovalComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(PreapprovalComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PreapprovalService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new Preapproval(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.preapprovals[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
