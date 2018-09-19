package br.com.presto.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Comentario.
 */
@Entity
@Table(name = "comentario")
public class Comentario implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "mensagem")
    private String mensagem;

    @Column(name = "avaliacao")
    private Integer avaliacao;

    @ManyToOne
    @JsonIgnoreProperties("comentarios")
    private Cliente cliente;

    @ManyToOne
    @JsonIgnoreProperties("comentarios")
    private Anuncio anuncio;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMensagem() {
        return mensagem;
    }

    public Comentario mensagem(String mensagem) {
        this.mensagem = mensagem;
        return this;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public Integer getAvaliacao() {
        return avaliacao;
    }

    public Comentario avaliacao(Integer avaliacao) {
        this.avaliacao = avaliacao;
        return this;
    }

    public void setAvaliacao(Integer avaliacao) {
        this.avaliacao = avaliacao;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public Comentario cliente(Cliente cliente) {
        this.cliente = cliente;
        return this;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Anuncio getAnuncio() {
        return anuncio;
    }

    public Comentario anuncio(Anuncio anuncio) {
        this.anuncio = anuncio;
        return this;
    }

    public void setAnuncio(Anuncio anuncio) {
        this.anuncio = anuncio;
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
        Comentario comentario = (Comentario) o;
        if (comentario.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), comentario.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Comentario{" +
            "id=" + getId() +
            ", mensagem='" + getMensagem() + "'" +
            ", avaliacao=" + getAvaliacao() +
            "}";
    }
}
