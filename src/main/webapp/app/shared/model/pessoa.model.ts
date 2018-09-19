import { IPrestador } from 'app/shared/model//prestador.model';
import { ICliente } from 'app/shared/model//cliente.model';

export interface IPessoa {
    id?: number;
    nome?: string;
    sobrenome?: string;
    nick?: string;
    senha?: string;
    email?: string;
    cpf?: string;
    cnpj?: string;
    telfixo?: string;
    telcelular?: string;
    logadouro?: string;
    numero?: number;
    cep?: number;
    cidade?: string;
    bairro?: string;
    pais?: string;
    prestadors?: IPrestador[];
    clientes?: ICliente[];
}

export class Pessoa implements IPessoa {
    constructor(
        public id?: number,
        public nome?: string,
        public sobrenome?: string,
        public nick?: string,
        public senha?: string,
        public email?: string,
        public cpf?: string,
        public cnpj?: string,
        public telfixo?: string,
        public telcelular?: string,
        public logadouro?: string,
        public numero?: number,
        public cep?: number,
        public cidade?: string,
        public bairro?: string,
        public pais?: string,
        public prestadors?: IPrestador[],
        public clientes?: ICliente[]
    ) {}
}
