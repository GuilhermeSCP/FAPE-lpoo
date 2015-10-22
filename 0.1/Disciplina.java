package f.ape;

import java.util.Vector;



/**
 * A classe Disciplina cria uma UC ou disciplina com as aulas ao longo da semana,
 * testes, trabalhos, exames e outras componentes de avaliação.
 * @author Utilizador
 * @version 1.0
 * @created 14-Mai-2012 17:08:31
 */

public class Disciplina
{
	/**
	 * Vector com as aulas daquela disciplina.
	 */
	private Vector<Aula> aulas;
	/**
	 * Nome da disciplina
	 */
	private String nome;
	/**
	 * Nota final à disciplina
	 */
	private double nota;
	/**
	 * Vector com os testes e exames da disciplina.
	 */
	private Vector<Teste> testes;
	/**
	 * Vector com os trabalhos ou projectos da disciplina.
	 */
	private Vector<Trabalho> trabalhos;

	public Disciplina(){

	}

	public void finalize() throws Throwable {

	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public double getNota() {
		return nota;
	}

	public void setNota(double nota) {
		this.nota = nota;
	}

	public Vector<Trabalho> getTrabalhos() {
		return trabalhos;
	}

	public void setTrabalhos(Vector<Trabalho> trabalhos) {
		this.trabalhos = trabalhos;
	}

	public Vector<Aula> getAulas() {
		return aulas;
	}

	public void setAulas(Vector<Aula> aulas) {
		this.aulas = aulas;
	}

	public Vector<Teste> getTestes() {
		return testes;
	}

	public void setTestes(Vector<Teste> testes) {
		this.testes = testes;
	}

}