/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { IPrestoTestModule } from '../../../test.module';
import { PrestadorDetailComponent } from 'app/entities/prestador/prestador-detail.component';
import { Prestador } from 'app/shared/model/prestador.model';

describe('Component Tests', () => {
    describe('Prestador Management Detail Component', () => {
        let comp: PrestadorDetailComponent;
        let fixture: ComponentFixture<PrestadorDetailComponent>;
        const route = ({ data: of({ prestador: new Prestador(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [IPrestoTestModule],
                declarations: [PrestadorDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(PrestadorDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(PrestadorDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.prestador).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
