import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IPrestador } from 'app/shared/model/prestador.model';

type EntityResponseType = HttpResponse<IPrestador>;
type EntityArrayResponseType = HttpResponse<IPrestador[]>;

@Injectable({ providedIn: 'root' })
export class PrestadorService {
    private resourceUrl = SERVER_API_URL + 'api/prestadors';

    constructor(private http: HttpClient) {}

    create(prestador: IPrestador): Observable<EntityResponseType> {
        return this.http.post<IPrestador>(this.resourceUrl, prestador, { observe: 'response' });
    }

    update(prestador: IPrestador): Observable<EntityResponseType> {
        return this.http.put<IPrestador>(this.resourceUrl, prestador, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IPrestador>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IPrestador[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
