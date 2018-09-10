/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { WhiproundTestModule } from '../../../test.module';
import { PreapprovalDetailComponent } from 'app/entities/preapproval/preapproval-detail.component';
import { Preapproval } from 'app/shared/model/preapproval.model';

describe('Component Tests', () => {
    describe('Preapproval Management Detail Component', () => {
        let comp: PreapprovalDetailComponent;
        let fixture: ComponentFixture<PreapprovalDetailComponent>;
        const route = ({ data: of({ preapproval: new Preapproval(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [WhiproundTestModule],
                declarations: [PreapprovalDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(PreapprovalDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(PreapprovalDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.preapproval).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
