package model;

import java.time.LocalDate;
import java.util.Objects;

public class Aluno {
	private Integer id;
	private String nome;
	private String matricula;
	private LocalDate dataNascimento;
	private String curso;

	public Aluno() {}

	public Aluno(Integer id, String nome, String matricula, LocalDate dataNascimento, String curso) {
		this.id = id;
		this.nome = nome;
		this.matricula = matricula;
		this.dataNascimento = dataNascimento;
		this.curso = curso;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public LocalDate getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(LocalDate dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public String getCurso() {
		return curso;
	}

	public void setCurso(String curso) {
		this.curso = curso;
	}
	
	@Override
	public String toString() {
		return "Aluno [id=" + id + 
		       ", nome=" + nome + 
		       ", matrícula=" + matricula + 
		       ", dataNascimento=" + dataNascimento + 
		       ", curso=" + curso + "]";
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
		Aluno other = (Aluno) obj;
		return Objects.equals(id, other.id);
	}
	
	
}
