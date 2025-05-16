package model;

import java.time.LocalDate;
import java.util.Objects;

public class Aluno {
	private Integer idAluno;
	private String nomeAluno;
	private String matricula;
	private LocalDate dataNascimento;
	private String curso;

	public Aluno() {}

	public Aluno(Integer idAluno, String nomeAluno, String matricula, LocalDate dataNascimento, String curso) {
		super();
		this.idAluno = idAluno;
		this.nomeAluno = nomeAluno;
		this.matricula = matricula;
		this.dataNascimento = dataNascimento;
		this.curso = curso;
	}

	public Integer getIdAluno() {
		return idAluno;
	}

	public void setIdAluno(Integer idAluno) {
		this.idAluno = idAluno;
	}

	public String getNomeAluno() {
		return nomeAluno;
	}

	public void setNomeAluno(String nomeAluno) {
		this.nomeAluno = nomeAluno;
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
	public int hashCode() {
		return Objects.hash(idAluno);
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
		return Objects.equals(idAluno, other.idAluno);
	}

	@Override
	public String toString() {
		return "Aluno [idAluno=" + idAluno + ", nomeAluno=" + nomeAluno + ", matricula=" + matricula
				+ ", dataNascimento=" + dataNascimento + ", curso=" + curso + "]";
	}
	
}
