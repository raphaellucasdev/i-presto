/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { IPrestoTestModule } from '../../../test.module';
import { PrestadorUpdateComponent } from 'app/entities/prestador/prestador-update.component';
import { PrestadorService } from 'app/entities/prestador/prestador.service';
import { Prestador } from 'app/shared/model/prestador.model';

describe('Component Tests', () => {
    describe('Prestador Management Update Component', () => {
        let comp: PrestadorUpdateComponent;
        let fixture: ComponentFixture<PrestadorUpdateComponent>;
        let service: PrestadorService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [IPrestoTestModule],
                declarations: [PrestadorUpdateComponent]
            })
                .overrideTemplate(PrestadorUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(PrestadorUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PrestadorService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Prestador(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.prestador = entity;
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
                    const entity = new Prestador();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.prestador = entity;
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
