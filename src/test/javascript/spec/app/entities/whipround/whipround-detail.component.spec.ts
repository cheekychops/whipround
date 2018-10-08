/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { WhiproundTestModule } from '../../../test.module';
import { WhiproundDetailComponent } from 'app/entities/whipround/whipround-detail.component';
import { Whipround } from 'app/shared/model/whipround.model';

describe('Component Tests', () => {
    describe('Whipround Management Detail Component', () => {
        let comp: WhiproundDetailComponent;
        let fixture: ComponentFixture<WhiproundDetailComponent>;
        const route = ({ data: of({ whipround: new Whipround(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [WhiproundTestModule],
                declarations: [WhiproundDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(WhiproundDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(WhiproundDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.whipround).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
