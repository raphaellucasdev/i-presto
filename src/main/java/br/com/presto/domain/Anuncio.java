package br.com.presto.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Anuncio.
 */
@Entity
@Table(name = "anuncio")
public class Anuncio implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "titulo")
    private String titulo;

    @Column(name = "descricao")
    private String descricao;

    @Column(name = "preco")
    private Float preco;

    @ManyToOne
    @JsonIgnoreProperties("anuncios")
    private Prestador prestador;

    @OneToMany(mappedBy = "anuncio")
    private Set<Comentario> comentarios = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public Anuncio titulo(String titulo) {
        this.titulo = titulo;
        return this;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public Anuncio descricao(String descricao) {
        this.descricao = descricao;
        return this;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Float getPreco() {
        return preco;
    }

    public Anuncio preco(Float preco) {
        this.preco = preco;
        return this;
    }

    public void setPreco(Float preco) {
        this.preco = preco;
    }

    public Prestador getPrestador() {
        return prestador;
    }

    public Anuncio prestador(Prestador prestador) {
        this.prestador = prestador;
        return this;
    }

    public void setPrestador(Prestador prestador) {
        this.prestador = prestador;
    }

    public Set<Comentario> getComentarios() {
        return comentarios;
    }

    public Anuncio comentarios(Set<Comentario> comentarios) {
        this.comentarios = comentarios;
        return this;
    }

    public Anuncio addComentario(Comentario comentario) {
        this.comentarios.add(comentario);
        comentario.setAnuncio(this);
        return this;
    }

    public Anuncio removeComentario(Comentario comentario) {
        this.comentarios.remove(comentario);
        comentario.setAnuncio(null);
        return this;
    }

    public void setComentarios(Set<Comentario> comentarios) {
        this.comentarios = comentarios;
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
        Anuncio anuncio = (Anuncio) o;
        if (anuncio.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), anuncio.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Anuncio{" +
            "id=" + getId() +
            ", titulo='" + getTitulo() + "'" +
            ", descricao='" + getDescricao() + "'" +
            ", preco=" + getPreco() +
            "}";
    }
}
