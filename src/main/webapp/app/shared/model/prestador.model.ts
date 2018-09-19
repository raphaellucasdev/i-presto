import { IPessoa } from 'app/shared/model//pessoa.model';
import { IAnuncio } from 'app/shared/model//anuncio.model';

export interface IPrestador {
    id?: number;
    media?: number;
    contador?: number;
    pessoa?: IPessoa;
    anuncios?: IAnuncio[];
}

export class Prestador implements IPrestador {
    constructor(
        public id?: number,
        public media?: number,
        public contador?: number,
        public pessoa?: IPessoa,
        public anuncios?: IAnuncio[]
    ) {}
}
