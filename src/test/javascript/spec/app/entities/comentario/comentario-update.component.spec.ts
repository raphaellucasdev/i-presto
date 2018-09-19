/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { IPrestoTestModule } from '../../../test.module';
import { ComentarioUpdateComponent } from 'app/entities/comentario/comentario-update.component';
import { ComentarioService } from 'app/entities/comentario/comentario.service';
import { Comentario } from 'app/shared/model/comentario.model';

describe('Component Tests', () => {
    describe('Comentario Management Update Component', () => {
        let comp: ComentarioUpdateComponent;
        let fixture: ComponentFixture<ComentarioUpdateComponent>;
        let service: ComentarioService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [IPrestoTestModule],
                declarations: [ComentarioUpdateComponent]
            })
                .overrideTemplate(ComentarioUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ComentarioUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ComentarioService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Comentario(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.comentario = entity;
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
                    const entity = new Comentario();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.comentario = entity;
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
