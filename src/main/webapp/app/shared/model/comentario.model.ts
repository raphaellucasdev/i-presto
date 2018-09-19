import { ICliente } from 'app/shared/model//cliente.model';
import { IAnuncio } from 'app/shared/model//anuncio.model';

export interface IComentario {
    id?: number;
    mensagem?: string;
    avaliacao?: number;
    cliente?: ICliente;
    anuncio?: IAnuncio;
}

export class Comentario implements IComentario {
    constructor(
        public id?: number,
        public mensagem?: string,
        public avaliacao?: number,
        public cliente?: ICliente,
        public anuncio?: IAnuncio
    ) {}
}
