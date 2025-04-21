package model;

import java.util.Objects;

public class Livro {
    private Integer id;
    private String titulo;
    private String autor;
    private int anoPublicacao;
    private int quantidadeEstoque;

    public Livro() {}

    public Livro(Integer id, String titulo, String autor, int anoPublicacao, int quantidadeEstoque) {
        super();
        this.id = id;
        this.titulo = titulo;
        this.autor = autor;
        this.anoPublicacao = anoPublicacao;
        this.quantidadeEstoque = quantidadeEstoque;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public int getAnoPublicacao() {
        return anoPublicacao;
    }

    public void setAnoPublicacao(int anoPublicacao) {
        this.anoPublicacao = anoPublicacao;
    }

    public int getQuantidadeEstoque() {
        return quantidadeEstoque;
    }

    public void setQuantidadeEstoque(int quantidadeEstoque) {
        this.quantidadeEstoque = quantidadeEstoque;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Livro other = (Livro) obj;
        return Objects.equals(id, other.id);
    }

    @Override
    public String toString() {
        return "Livro [id=" + id + 
               ", título=" + titulo + 
               ", autor=" + autor + 
               ", anoPublicacao=" + anoPublicacao + 
               ", quantidadeEstoque=" + quantidadeEstoque + "]";
    }
}
