import { IPrestador } from 'app/shared/model//prestador.model';
import { IComentario } from 'app/shared/model//comentario.model';

export interface IAnuncio {
    id?: number;
    titulo?: string;
    descricao?: string;
    preco?: number;
    prestador?: IPrestador;
    comentarios?: IComentario[];
}

export class Anuncio implements IAnuncio {
    constructor(
        public id?: number,
        public titulo?: string,
        public descricao?: string,
        public preco?: number,
        public prestador?: IPrestador,
        public comentarios?: IComentario[]
    ) {}
}
