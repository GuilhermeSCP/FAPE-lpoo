package f.ape;



/**
 * Classe Aula cria uma Aula com todos os parâmetros.
 * @author Utilizador
 * @version 1.0
 * @created 14-Mai-2012 17:08:30
 */
public class Aula
{

	/**
	 * Dia da semana da Aula.
	 */
	private String dia;
	/**
	 * Duração da Aula em minutos.
	 */
	private int duracao;
	/**
	 * Hora de fim da Aula.
	 */
	private int horaf;
	/**
	 * Hora de início da Aula.
	 */
	private int horai;
	/**
	 * Minutos de início da Aula.
	 */
	private int mini;
	/**
	 * Minutos de fim da Aula.
	 */
	private int minf;
	/**
	 * Professor da Aula.
	 */
	private String prof;
	/**
	 * Sala da Aula.
	 */
	private String sala;

	public Aula()
	{
		
	}

	public void finalize() throws Throwable {

	}

	public String getDia()
	{
		return dia;
	}

	public void setDia(String dia)
	{
		this.dia = dia;
	}

	public int getDuracao() {
		return duracao;
	}

	public void setDuracao(int duracao) {
		this.duracao = duracao;
	}

	public int getHoraf() {
		return horaf;
	}

	public void setHoraf(int horaf) {
		this.horaf = horaf;
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

	public int getMinf() {
		return minf;
	}

	public void setMinf(int minf) {
		this.minf = minf;
	}

	public String getProf() {
		return prof;
	}

	public void setProf(String prof) {
		this.prof = prof;
	}

	public String getSala() {
		return sala;
	}

	public void setSala(String sala) {
		this.sala = sala;
	}

}