package com.project.domain;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "llibres")
public class Llibre implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "llibre_id")
    private long llibreId;

    @Column(name = "isbn", unique = true, nullable = false)
    private String isbn;

    @Column(name = "titol", nullable = false, length = 100)
    private String titol;

    @Column(name = "editorial", nullable = false, length = 100)
    private String editorial;

    @Column(name = "any_publicacio", nullable = false)
    private Integer anyPublicacio;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "llibre_autors",
        joinColumns = @JoinColumn(name = "llibre_id"),
        inverseJoinColumns = @JoinColumn(name = "autor_id")
    )
    private Set<Autor> autors = new HashSet<>();

    @OneToMany(mappedBy = "llibre", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private Set<Exemplar> exemplars = new HashSet<>();

    // Constructor por defecto
    public Llibre() {
    }

    // Constructor con todos los campos esenciales
    public Llibre(String isbn, String titol, String editorial, Integer anyPublicacio) {
        this.isbn = isbn;
        this.titol = titol;
        this.editorial = editorial;
        this.anyPublicacio = anyPublicacio;
        this.autors = new HashSet<>();
        this.exemplars = new HashSet<>();
    }

    // Constructor con autores
    public Llibre(String isbn, String titol, String editorial, Integer anyPublicacio, Set<Autor> autors) {
        this.isbn = isbn;
        this.titol = titol;
        this.editorial = editorial;
        this.anyPublicacio = anyPublicacio;
        this.autors = autors;
        this.exemplars = new HashSet<>();
    }

    // Métodos getters y setters
    public long getLlibreId() {
        return llibreId;
    }

    public void setLlibreId(long llibreId) {
        this.llibreId = llibreId;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getTitol() {
        return titol;
    }

    public void setTitol(String titol) {
        this.titol = titol;
    }

    public String getEditorial() {
        return editorial;
    }

    public void setEditorial(String editorial) {
        this.editorial = editorial;
    }

    public Integer getAnyPublicacio() {
        return anyPublicacio;
    }

    public void setAnyPublicacio(Integer anyPublicacio) {
        this.anyPublicacio = anyPublicacio;
    }

    public Set<Autor> getAutors() {
        return autors;
    }

    public void setAutors(Set<Autor> autors) {
        this.autors = autors;
    }

    public Set<Exemplar> getExemplars() {
        return exemplars;
    }

    public void setExemplars(Set<Exemplar> exemplars) {
        this.exemplars = exemplars;
    }

    // Método toString
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("Llibre[id=%d, isbn='%s', titol='%s'", llibreId, isbn, titol));

        if (editorial != null) {
            sb.append(String.format(", editorial='%s'", editorial));
        }
        if (anyPublicacio != null) {
            sb.append(String.format(", any=%d", anyPublicacio));
        }

        if (!autors.isEmpty()) {
            sb.append(", autors={");
            boolean first = true;
            for (Autor a : autors) {
                if (!first) sb.append(", ");
                sb.append(a.getNom());
                first = false;
            }
            sb.append("}");
        }

        if (!exemplars.isEmpty()) {
            sb.append(", exemplars={");
            boolean first = true;
            for (Exemplar e : exemplars) {
                if (!first) sb.append(", ");
                sb.append(e.getCodiBarres());
                first = false;
            }
            sb.append("}");
        }

        sb.append("]");
        return sb.toString();
    }

    // Métodos equals y hashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Llibre llibre = (Llibre) o;
        return llibreId == llibre.llibreId;
    }

    @Override
    public int hashCode() {
        return Long.hashCode(llibreId);
    }
}
