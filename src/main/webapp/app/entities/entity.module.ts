import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { IPrestoAnuncioModule } from './anuncio/anuncio.module';
import { IPrestoComentarioModule } from './comentario/comentario.module';
import { IPrestoClienteModule } from './cliente/cliente.module';
import { IPrestoEnderecoModule } from './endereco/endereco.module';
import { IPrestoPessoaModule } from './pessoa/pessoa.module';
import { IPrestoPrestadorModule } from './prestador/prestador.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    // prettier-ignore
    imports: [
        IPrestoAnuncioModule,
        IPrestoComentarioModule,
        IPrestoClienteModule,
        IPrestoEnderecoModule,
        IPrestoPessoaModule,
        IPrestoPrestadorModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class IPrestoEntityModule {}
