/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { IPrestoTestModule } from '../../../test.module';
import { AnuncioComponent } from 'app/entities/anuncio/anuncio.component';
import { AnuncioService } from 'app/entities/anuncio/anuncio.service';
import { Anuncio } from 'app/shared/model/anuncio.model';

describe('Component Tests', () => {
    describe('Anuncio Management Component', () => {
        let comp: AnuncioComponent;
        let fixture: ComponentFixture<AnuncioComponent>;
        let service: AnuncioService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [IPrestoTestModule],
                declarations: [AnuncioComponent],
                providers: []
            })
                .overrideTemplate(AnuncioComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(AnuncioComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AnuncioService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new Anuncio(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.anuncios[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
