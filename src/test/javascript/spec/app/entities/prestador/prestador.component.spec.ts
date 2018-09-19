/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { IPrestoTestModule } from '../../../test.module';
import { PrestadorComponent } from 'app/entities/prestador/prestador.component';
import { PrestadorService } from 'app/entities/prestador/prestador.service';
import { Prestador } from 'app/shared/model/prestador.model';

describe('Component Tests', () => {
    describe('Prestador Management Component', () => {
        let comp: PrestadorComponent;
        let fixture: ComponentFixture<PrestadorComponent>;
        let service: PrestadorService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [IPrestoTestModule],
                declarations: [PrestadorComponent],
                providers: []
            })
                .overrideTemplate(PrestadorComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(PrestadorComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PrestadorService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new Prestador(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.prestadors[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
