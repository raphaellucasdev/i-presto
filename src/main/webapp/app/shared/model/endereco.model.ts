export interface IEndereco {
    id?: number;
    logadouro?: string;
    numero?: number;
    cep?: number;
    cidade?: string;
    bairro?: string;
    pais?: string;
}

export class Endereco implements IEndereco {
    constructor(
        public id?: number,
        public logadouro?: string,
        public numero?: number,
        public cep?: number,
        public cidade?: string,
        public bairro?: string,
        public pais?: string
    ) {}
}
