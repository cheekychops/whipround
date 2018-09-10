/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { WhiproundTestModule } from '../../../test.module';
import { ContributionComponent } from 'app/entities/contribution/contribution.component';
import { ContributionService } from 'app/entities/contribution/contribution.service';
import { Contribution } from 'app/shared/model/contribution.model';

describe('Component Tests', () => {
    describe('Contribution Management Component', () => {
        let comp: ContributionComponent;
        let fixture: ComponentFixture<ContributionComponent>;
        let service: ContributionService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [WhiproundTestModule],
                declarations: [ContributionComponent],
                providers: []
            })
                .overrideTemplate(ContributionComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ContributionComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ContributionService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new Contribution(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.contributions[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
