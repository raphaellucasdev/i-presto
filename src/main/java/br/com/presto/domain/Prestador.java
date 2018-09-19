package br.com.presto.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Prestador.
 */
@Entity
@Table(name = "prestador")
public class Prestador implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "media")
    private Integer media;

    @Column(name = "contador")
    private Integer contador;

    @ManyToOne
    @JsonIgnoreProperties("prestadors")
    private Pessoa pessoa;

    @OneToMany(mappedBy = "prestador")
    private Set<Anuncio> anuncios = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getMedia() {
        return media;
    }

    public Prestador media(Integer media) {
        this.media = media;
        return this;
    }

    public void setMedia(Integer media) {
        this.media = media;
    }

    public Integer getContador() {
        return contador;
    }

    public Prestador contador(Integer contador) {
        this.contador = contador;
        return this;
    }

    public void setContador(Integer contador) {
        this.contador = contador;
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public Prestador pessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
        return this;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }

    public Set<Anuncio> getAnuncios() {
        return anuncios;
    }

    public Prestador anuncios(Set<Anuncio> anuncios) {
        this.anuncios = anuncios;
        return this;
    }

    public Prestador addAnuncio(Anuncio anuncio) {
        this.anuncios.add(anuncio);
        anuncio.setPrestador(this);
        return this;
    }

    public Prestador removeAnuncio(Anuncio anuncio) {
        this.anuncios.remove(anuncio);
        anuncio.setPrestador(null);
        return this;
    }

    public void setAnuncios(Set<Anuncio> anuncios) {
        this.anuncios = anuncios;
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
        Prestador prestador = (Prestador) o;
        if (prestador.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), prestador.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Prestador{" +
            "id=" + getId() +
            ", media=" + getMedia() +
            ", contador=" + getContador() +
            "}";
    }
}
