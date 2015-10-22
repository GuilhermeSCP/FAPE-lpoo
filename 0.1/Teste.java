package f.ape;

import java.util.Vector;

/**
 * Classe que cria um Teste com todos os seus parâmetros.
 * @author Utilizador
 * @version 1.0
 * @created 14-Mai-2012 17:08:35
 */

public class Teste
{

	/**
	 * Ano do Teste.
	 */
	private int ano;
	/**
	 * Mês do teste.
	 */
	private int mes;
	/**
	 * Dia do Teste.
	 */
	private int dia;
	/**
	 * Duração do Teste em minutos.
	 */
	private int duracao;
	/**
	 * Hora de início do Teste.
	 */
	private int horai;
	/**
	 * Minuto de início do Teste.
	 */
	private int mini;
	/**
	 * A nota do Teste.
	 */
	private double nota;
	/**
	 * A nota mínima necessária para o Teste.
	 */
	private double notamin;
	/**
	 * A percentagem do Teste para a nota final.
	 */
	private double percentagem;
	/**
	 * Sala ou salas ou se vai realizar o Teste.
	 */
	private Vector<String> salas;

	public Teste(){

	}

	public void finalize() throws Throwable {

	}

	public int getAno() {
		return ano;
	}

	public void setAno(int ano) {
		this.ano = ano;
	}

	public int getMes() {
		return mes;
	}

	public void setMes(int mes) {
		this.mes = mes;
	}

	public int getDia() {
		return dia;
	}

	public void setDia(int dia) {
		this.dia = dia;
	}

	public int getDuracao() {
		return duracao;
	}

	public void setDuracao(int duracao) {
		this.duracao = duracao;
	}

	public int getHorai() {
		return horai;
	}

	public void setHorai(int horai) {
		this.horai = horai;
	}

	public int getMini() {
		return mini;
	}

	public void setMini(int mini) {
		this.mini = mini;
	}

	public double getNota() {
		return nota;
	}

	public void setNota(double nota) {
		this.nota = nota;
	}

	public double getNotamin() {
		return notamin;
	}

	public void setNotamin(double notamin) {
		this.notamin = notamin;
	}

	public double getPercentagem() {
		return percentagem;
	}

	public void setPercentagem(double percentagem) {
		this.percentagem = percentagem;
	}

	public Vector<String> getSalas() {
		return salas;
	}

	public void setSalas(Vector<String> salas) {
		this.salas = salas;
	}

}