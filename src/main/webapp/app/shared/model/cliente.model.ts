import { IPessoa } from 'app/shared/model//pessoa.model';
import { IComentario } from 'app/shared/model//comentario.model';

export interface ICliente {
    id?: number;
    pessoa?: IPessoa;
    comentarios?: IComentario[];
}

export class Cliente implements ICliente {
    constructor(public id?: number, public pessoa?: IPessoa, public comentarios?: IComentario[]) {}
}
