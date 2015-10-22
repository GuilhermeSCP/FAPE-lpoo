package f.ape;



/**
 * Classe que possui toda a informação sobre Trabalhos ou projectos para entregar.
 * @author Utilizador
 * @version 1.0
 * @updated 14-Mai-2012 17:08:36
 */
public class Trabalho {

	/**
	 * Ano de entrega do Trabalho.
	 */
	private int anoe;
	/**
	 * Dia de entrega do Trabalho.
	 */
	private int dia;
	/**
	 * Define se o método de entrega é via Moodle, e-mail, pessoal ou outro.
	 */
	private boolean entrega;
	/**
	 * Hora de entrega do Trabalho.
	 */
	private int horae;
	/**
	 * Mês de entrega do Trabalho.
	 */
	private int mese;
	/**
	 * Minuto de entrega do Trabalho.
	 */
	private int mine;
	/**
	 * A nota do Trabalho.
	 */
	private double nota;
	/**
	 * A nota mínima para o Trabalho.
	 */
	private double notamin;
	/**
	 * A percentagem do Trabalho para a nota final.
	 */
	private double percentagem;

	public Trabalho(){

	}

	public void finalize() throws Throwable {

	}

	public int getAnoe() {
		return anoe;
	}

	public void setAnoe(int anoe) {
		this.anoe = anoe;
	}

	public int getDia() {
		return dia;
	}

	public void setDia(int dia) {
		this.dia = dia;
	}

	public boolean isEntrega() {
		return entrega;
	}

	public void setEntrega(boolean entrega) {
		this.entrega = entrega;
	}

	public int getHorae() {
		return horae;
	}

	public void setHorae(int horae) {
		this.horae = horae;
	}

	public int getMese() {
		return mese;
	}

	public void setMese(int mese) {
		this.mese = mese;
	}

	public int getMine() {
		return mine;
	}

	public void setMine(int mine) {
		this.mine = mine;
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

}