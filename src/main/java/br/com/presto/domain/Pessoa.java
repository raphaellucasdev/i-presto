package br.com.presto.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;

import br.com.presto.jsonview.InsensitiveInfo;
import br.com.presto.jsonview.SensitiveInfo;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Pessoa.
 */
@Entity
@Table(name = "pessoa")
public class Pessoa implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView(InsensitiveInfo.class)
    private Long id;

    @Column(name = "nome")
    @JsonView(InsensitiveInfo.class)
    private String nome;

    @Column(name = "sobrenome")
    @JsonView(InsensitiveInfo.class)
    private String sobrenome;

    @Column(name = "nick")
    @JsonView(InsensitiveInfo.class)
    private String nick;

    @Column(name = "senha")
    @JsonView(SensitiveInfo.class)
    private String senha;

    @Column(name = "email")
    @JsonView(InsensitiveInfo.class)
    private String email;

    @Column(name = "cpf")
    @JsonView(InsensitiveInfo.class)
    private String cpf;

    @Column(name = "cnpj")
    @JsonView(InsensitiveInfo.class)
    private String cnpj;

    @Column(name = "telfixo")
    @JsonView(InsensitiveInfo.class)
    private String telfixo;

    @Column(name = "telcelular")
    @JsonView(InsensitiveInfo.class)
    private String telcelular;

    @Column(name = "logadouro")
    @JsonView(InsensitiveInfo.class)
    private String logadouro;

    @Column(name = "numero")
    @JsonView(InsensitiveInfo.class)
    private Integer numero;

    @Column(name = "cep")
    @JsonView(InsensitiveInfo.class)
    private Integer cep;

    @Column(name = "cidade")
    @JsonView(InsensitiveInfo.class)
    private String cidade;

    @Column(name = "bairro")
    @JsonView(InsensitiveInfo.class)
    private String bairro;

    @Column(name = "pais")
    @JsonView(InsensitiveInfo.class)
    private String pais;

    @OneToMany(mappedBy = "pessoa")
    @JsonView(InsensitiveInfo.class)
    private Set<Prestador> prestadors = new HashSet<>();

    @OneToMany(mappedBy = "pessoa")
    @JsonView(InsensitiveInfo.class)
    private Set<Cliente> clientes = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public Pessoa nome(String nome) {
        this.nome = nome;
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSobrenome() {
        return sobrenome;
    }

    public Pessoa sobrenome(String sobrenome) {
        this.sobrenome = sobrenome;
        return this;
    }

    public void setSobrenome(String sobrenome) {
        this.sobrenome = sobrenome;
    }

    public String getNick() {
        return nick;
    }

    public Pessoa nick(String nick) {
        this.nick = nick;
        return this;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getSenha() {
        return senha;
    }

    public Pessoa senha(String senha) {
        this.senha = senha;
        return this;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getEmail() {
        return email;
    }

    public Pessoa email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCpf() {
        return cpf;
    }

    public Pessoa cpf(String cpf) {
        this.cpf = cpf;
        return this;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getCnpj() {
        return cnpj;
    }

    public Pessoa cnpj(String cnpj) {
        this.cnpj = cnpj;
        return this;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getTelfixo() {
        return telfixo;
    }

    public Pessoa telfixo(String telfixo) {
        this.telfixo = telfixo;
        return this;
    }

    public void setTelfixo(String telfixo) {
        this.telfixo = telfixo;
    }

    public String getTelcelular() {
        return telcelular;
    }

    public Pessoa telcelular(String telcelular) {
        this.telcelular = telcelular;
        return this;
    }

    public void setTelcelular(String telcelular) {
        this.telcelular = telcelular;
    }

    public String getLogadouro() {
        return logadouro;
    }

    public Pessoa logadouro(String logadouro) {
        this.logadouro = logadouro;
        return this;
    }

    public void setLogadouro(String logadouro) {
        this.logadouro = logadouro;
    }

    public Integer getNumero() {
        return numero;
    }

    public Pessoa numero(Integer numero) {
        this.numero = numero;
        return this;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public Integer getCep() {
        return cep;
    }

    public Pessoa cep(Integer cep) {
        this.cep = cep;
        return this;
    }

    public void setCep(Integer cep) {
        this.cep = cep;
    }

    public String getCidade() {
        return cidade;
    }

    public Pessoa cidade(String cidade) {
        this.cidade = cidade;
        return this;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getBairro() {
        return bairro;
    }

    public Pessoa bairro(String bairro) {
        this.bairro = bairro;
        return this;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getPais() {
        return pais;
    }

    public Pessoa pais(String pais) {
        this.pais = pais;
        return this;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public Set<Prestador> getPrestadors() {
        return prestadors;
    }

    public Pessoa prestadors(Set<Prestador> prestadors) {
        this.prestadors = prestadors;
        return this;
    }

    public Pessoa addPrestador(Prestador prestador) {
        this.prestadors.add(prestador);
        prestador.setPessoa(this);
        return this;
    }

    public Pessoa removePrestador(Prestador prestador) {
        this.prestadors.remove(prestador);
        prestador.setPessoa(null);
        return this;
    }

    public void setPrestadors(Set<Prestador> prestadors) {
        this.prestadors = prestadors;
    }

    public Set<Cliente> getClientes() {
        return clientes;
    }

    public Pessoa clientes(Set<Cliente> clientes) {
        this.clientes = clientes;
        return this;
    }

    public Pessoa addCliente(Cliente cliente) {
        this.clientes.add(cliente);
        cliente.setPessoa(this);
        return this;
    }

    public Pessoa removeCliente(Cliente cliente) {
        this.clientes.remove(cliente);
        cliente.setPessoa(null);
        return this;
    }

    public void setClientes(Set<Cliente> clientes) {
        this.clientes = clientes;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Pessoa pessoa = (Pessoa) o;
        if (pessoa.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), pessoa.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Pessoa{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", sobrenome='" + getSobrenome() + "'" +
            ", nick='" + getNick() + "'" +
            ", senha='" + getSenha() + "'" +
            ", email='" + getEmail() + "'" +
            ", cpf='" + getCpf() + "'" +
            ", cnpj='" + getCnpj() + "'" +
            ", telfixo='" + getTelfixo() + "'" +
            ", telcelular='" + getTelcelular() + "'" +
            ", logadouro='" + getLogadouro() + "'" +
            ", numero=" + getNumero() +
            ", cep=" + getCep() +
            ", cidade='" + getCidade() + "'" +
            ", bairro='" + getBairro() + "'" +
            ", pais='" + getPais() + "'" +
            "}";
    }
}
