package model;

import java.util.Objects;

public class Livro {
    private Integer idLivro;
    private String titulo;
    private String autor;
    private int anoPublicacao;
    private int quantidadeEstoque;
    private int quantidadeEmprestada;

    public Livro() {}

	public Livro(Integer idLivro, String titulo, String autor, int anoPublicacao, int quantidadeEstoque) {
		super();
		this.idLivro = idLivro;
		this.titulo = titulo;
		this.autor = autor;
		this.anoPublicacao = anoPublicacao;
		this.quantidadeEstoque = quantidadeEstoque;
	}

	public Integer getIdLivro() {
		return idLivro;
	}

	public void setIdLivro(Integer idLivro) {
		this.idLivro = idLivro;
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

	public int getQuantidadeEmprestada() {
		return quantidadeEmprestada;
	}

	public void setQuantidadeEmprestada(int quantidadeEmprestada) {
		this.quantidadeEmprestada = quantidadeEmprestada;
	}

	@Override
	public int hashCode() {
		return Objects.hash(idLivro);
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
		return Objects.equals(idLivro, other.idLivro);
	}

	@Override
	public String toString() {
		return "Livro [idLivro=" + idLivro + ", titulo=" + titulo + ", autor=" + autor + ", anoPublicacao="
				+ anoPublicacao + ", quantidadeEstoque=" + quantidadeEstoque + ", quantidadeEmprestada="
				+ quantidadeEmprestada + "]";
	}

}
