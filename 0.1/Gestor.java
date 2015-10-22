package f.ape;

public class Gestor
{
	private int tipoEnsino;
	Horario horario;
	
	Gestor()
	{
		horario = new Horario();
		setTipoEnsino(0);
	}

	public int getTipoEnsino() {
		return tipoEnsino;
	}

	public void setTipoEnsino(int tipoEnsino) {
		this.tipoEnsino = tipoEnsino;
	}
}
