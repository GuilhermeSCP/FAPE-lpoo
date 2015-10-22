package f.ape;

import f.ape.Horario;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class FAPEActivity extends Activity
{
	Gestor gestor = new Gestor();
	/** Called when the activity is first created. */
	@Override
	public void onCreate(final Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		Button btn_start = (Button) findViewById(R.id.btn_start);
		btn_start.setOnClickListener(new OnClickListener()
		{
			public void onClick(View v)
			{
				if(gestor.getTipoEnsino()==0)
				{
					menuEscEnsino(savedInstanceState);
				}
				else
				{
					menuMenuPrincipal(savedInstanceState);
				}
			}
		});
	}

	public void menuEscEnsino(final Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.escensino);
		
		Button btn_sec = (Button) findViewById(R.id.btn_sec);
		Button btn_sup = (Button) findViewById(R.id.btn_sup);
		
		btn_sec.setOnClickListener(new OnClickListener()
		{
			public void onClick(View v)
			{
				gestor.setTipoEnsino(1);
				menuMenuPrincipal(savedInstanceState);
			}
		});
		
		btn_sup.setOnClickListener(new OnClickListener()
		{
			public void onClick(View v)
			{ 
				gestor.setTipoEnsino(2);
				menuMenuPrincipal(savedInstanceState);
			}
		});

	}

	public void menuMenuPrincipal(final Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.menuprincipal);
		
		Button btn_gerirDisciplinas = (Button) findViewById(R.id.btn_gerirDisciplinas);
		Button btn_visualizador = (Button) findViewById(R.id.btn_visualizador);
		Button btn_simulador = (Button) findViewById(R.id.btn_simulador);
		Button btn_opcoes = (Button) findViewById(R.id.btn_opcoes);
		Button btn_sair = (Button) findViewById(R.id.btn_sair);
		
		btn_gerirDisciplinas.setOnClickListener(new OnClickListener()
		{
			public void onClick(View v)
			{
				if(gestor.horario.getNumDisciplinas()==0)
				{
					menuMenuNumDis(savedInstanceState);
				}
			}
		});
		
		btn_sair.setOnClickListener(new OnClickListener()
		{
			public void onClick(View v)
			{ 
				finish();
			}
		});
	}
	
	public void menuMenuNumDis(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.menunumdisc);
	}
	
	public void menuMenuCriaHorario(Bundle savedInstanceState)
	{
		
	}
}