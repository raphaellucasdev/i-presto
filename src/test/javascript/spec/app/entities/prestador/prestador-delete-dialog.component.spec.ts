/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { IPrestoTestModule } from '../../../test.module';
import { PrestadorDeleteDialogComponent } from 'app/entities/prestador/prestador-delete-dialog.component';
import { PrestadorService } from 'app/entities/prestador/prestador.service';

describe('Component Tests', () => {
    describe('Prestador Management Delete Component', () => {
        let comp: PrestadorDeleteDialogComponent;
        let fixture: ComponentFixture<PrestadorDeleteDialogComponent>;
        let service: PrestadorService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [IPrestoTestModule],
                declarations: [PrestadorDeleteDialogComponent]
            })
                .overrideTemplate(PrestadorDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(PrestadorDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PrestadorService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete', inject(
                [],
                fakeAsync(() => {
                    // GIVEN
                    spyOn(service, 'delete').and.returnValue(of({}));

                    // WHEN
                    comp.confirmDelete(123);
                    tick();

                    // THEN
                    expect(service.delete).toHaveBeenCalledWith(123);
                    expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                })
            ));
        });
    });
});
