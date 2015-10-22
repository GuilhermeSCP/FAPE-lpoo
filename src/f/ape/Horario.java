package f.ape;

import java.util.Vector;



/**
 * Classe Horário que cria uma tabela com as aulas durante uma semana.
 * @author Utilizador
 * @version 1.0
 * @created 14-Mai-2012 17:08:33
 */
public class Horario {

	/**
	 * Vector com as disciplinas do utilizador.
	 */
	private Vector<Disciplina> disciplinas;
	/**
	 * Se existem intervalos entre as aulas.
	 */
	private boolean intervalo;
	/**
	 * Numero total de disciplinas.
	 */
	private int numDisciplinas;
	/**
	 * Se o horario ja foi criado.
	 */
	private boolean horarioCreated;

	public Horario()
	{
		numDisciplinas=0;
		horarioCreated=false;
		disciplinas = new Vector<Disciplina>();
	}

	public void finalize() throws Throwable {

	}

	public Vector<Disciplina> getDisciplinas() {
		return disciplinas;
	}

	public void setDisciplinas(Vector<Disciplina> disciplinas) {
		this.disciplinas = disciplinas;
	}
	
	public void addToDisciplinas(Disciplina disc)
	{
		disciplinas.add(disc);
	}

	public boolean isIntervalo() {
		return intervalo;
	}

	public void setIntervalo(boolean intervalo) {
		this.intervalo = intervalo;
	}

	public int getNumDisciplinas() {
		return numDisciplinas;
	}

	public void setNumDisciplinas(int numDisciplinas) {
		this.numDisciplinas = numDisciplinas;
	}

	public boolean isHorarioCreated() {
		return horarioCreated;
	}

	public void setHorarioCreated(boolean horarioCreated) {
		this.horarioCreated = horarioCreated;
	}

}