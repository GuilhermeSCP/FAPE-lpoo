package f.ape;


import java.util.Vector;

import android.app.Activity;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabHost.TabContentFactory;
import android.widget.TextView;
import android.widget.TimePicker;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.ToggleButton;

public class FAPEActivity extends Activity implements OnTabChangeListener
{
	int mHour;
	int mMinute;

	ListView mondayLW;
	ListView tuesdayLW;
	ListView wednesdayLW;
	ListView thursdayLW;
	ListView fridayLW;
	ListView currentLW;
	String MONDAY_TAB_TAG = "Segunda";
	String TUESDAY_TAB_TAG = "Terça";
	String WEDNESDAY_TAB_TAG = "Quarta";
	String THURSDAY_TAB_TAG = "Quinta";
	String FRIDAY_TAB_TAG = "Sexta";

	Calendar c = Calendar.getInstance();
	int currentDay = c.get(Calendar.DAY_OF_WEEK);

	DBAdapter datasource = new DBAdapter(this);

	private static int[] to = {R.id.Horario_Disciplina,R.id.Horario_Sigla,R.id.Horario_Sala,R.id.Horario_Hora,R.id.Horario_Duracao};
	private static String[] from = {"Nome","Sigla","Sala","Hora","Duracao"};
	private static ArrayList<HashMap<String, Object>> results;
	private static SimpleAdapter simpleAdapter;

	static final int TIME_DIALOG_ID = 0;
	Gestor gestor = new Gestor();

	public TimePickerDialog.OnTimeSetListener mTimeSetListener = new TimePickerDialog.OnTimeSetListener()
	{
		public void onTimeSet(TimePicker view, int hourOfDay, int minute)
		{
			mHour = hourOfDay;
			mMinute = minute;
		}
	};

	public Dialog onCreateDialog(int id)
	{
		switch (id)
		{
		case TIME_DIALOG_ID:
			return new TimePickerDialog(this, mTimeSetListener, mHour, mMinute, false);
		}
		return null;
	}

	/** Called when the activity is first created. */
	@Override
	public void onCreate(final Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		Button mainnovo = (Button) findViewById(R.id.mainnovo);
		Button maincontinua = (Button) findViewById(R.id.maincontinua);
		
		mainnovo.setOnClickListener(new OnClickListener()
		{
			public void onClick(View v)
			{
				menuMenuPrincipal(savedInstanceState);
			}
		});
		maincontinua.setOnClickListener(new OnClickListener()
		{
			public void onClick(View v)
			{
				CarregaTudo();
				gestor.horario.setNumDisciplinas(gestor.horario.getDisciplinas().size());
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
				if(gestor.horario.getDisciplinas().size()!=gestor.horario.getNumDisciplinas() ||
						gestor.horario.getDisciplinas().size()==0)
				{
					menuMenuNumDisc(savedInstanceState);
				}
				else
				{
					menuMenuGerirDisciplinas(savedInstanceState);
				}
			}
		});
		btn_visualizador.setOnClickListener(new OnClickListener()
		{
			public void onClick(View v)
			{ 
				menuVisualizador(savedInstanceState);
			}
		});
		btn_simulador.setOnClickListener(new OnClickListener()
		{
			public void onClick(View v)
			{ 
				menuSimulador(savedInstanceState);
			}
		});
		btn_opcoes.setOnClickListener(new OnClickListener()
		{
			public void onClick(View v)
			{ 
				menuOpcoes(savedInstanceState);
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

	public void menuMenuNumDisc(final Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.menunumdisc);

		Button btn_contNumDisc = (Button) findViewById(R.id.btn_contNumDisc);

		btn_contNumDisc.setOnClickListener(new OnClickListener()
		{
			public void onClick(View v)
			{
				CheckBox check_intervalo = (CheckBox) findViewById(R.id.check_intervalo);
				EditText txt_numDisc = (EditText)findViewById(R.id.txt_numDisc);

				gestor.horario.setNumDisciplinas(Integer.parseInt(txt_numDisc.getText().toString()));
				if (check_intervalo.isChecked())
				{
					gestor.horario.setIntervalo(true);
				}
				else
				{
					gestor.horario.setIntervalo(false);
				}
				menuMenuCriaDisciplina(savedInstanceState);
			}
		});
	}

	public void menuMenuCriaDisciplina(final Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.criadisciplina);

		Button voltacriadisc = (Button) findViewById(R.id.voltacriadisc);
		Button concluircriadisc = (Button) findViewById(R.id.concluircriadisc);
		Button seguintecriadisc = (Button) findViewById(R.id.seguintecriadisc);
		Button horasegunda = (Button) findViewById(R.id.horasegunda);
		Button horaterca = (Button) findViewById(R.id.horaterca);
		Button horaquarta = (Button) findViewById(R.id.horaquarta);
		Button horaquinta = (Button) findViewById(R.id.horaquinta);
		Button horasexta = (Button) findViewById(R.id.horasexta);
		final ToggleButton togglesegunda = (ToggleButton) findViewById(R.id.togglesegunda);
		final ToggleButton toggleterca = (ToggleButton) findViewById(R.id.toggleterca);
		final ToggleButton togglequarta = (ToggleButton) findViewById(R.id.togglequarta);
		final ToggleButton togglequinta = (ToggleButton) findViewById(R.id.togglequinta);
		final ToggleButton togglesexta = (ToggleButton) findViewById(R.id.togglesexta);
		final EditText nomedisc = (EditText) findViewById(R.id.nomedisc);
		final EditText sigladisc = (EditText) findViewById(R.id.sigladisc);
		final EditText durasegunda = (EditText) findViewById(R.id.durasegunda);
		final EditText duraterca = (EditText) findViewById(R.id.duraterca);
		final EditText duraquarta = (EditText) findViewById(R.id.duraquarta);
		final EditText duraquinta = (EditText) findViewById(R.id.duraquinta);
		final EditText durasexta = (EditText) findViewById(R.id.durasexta);
		final EditText salasegunda = (EditText) findViewById(R.id.salasegunda);
		final EditText salaterca = (EditText) findViewById(R.id.salaterca);
		final EditText salaquarta = (EditText) findViewById(R.id.salaquarta);
		final EditText salaquinta = (EditText) findViewById(R.id.salaquinta);
		final EditText salasexta = (EditText) findViewById(R.id.salasexta);
		final Vector<Aula> temp = new Vector<Aula> ();
		final Aula aula1 = new Aula();
		final Aula aula2 = new Aula();
		final Aula aula3 = new Aula();
		final Aula aula4 = new Aula();
		final Aula aula5 = new Aula();

		horasegunda.setOnClickListener(new View.OnClickListener()
		{
			public void onClick(View v)
			{
				showDialog(TIME_DIALOG_ID);
				aula1.setHorai(mHour);
				aula1.setMini(mMinute);
			}
		});

		horaterca.setOnClickListener(new View.OnClickListener()
		{
			public void onClick(View v)
			{
				showDialog(TIME_DIALOG_ID);
				aula2.setHorai(mHour);
				aula2.setMini(mMinute);
			}
		});
		horaquarta.setOnClickListener(new View.OnClickListener()
		{
			public void onClick(View v)
			{
				showDialog(TIME_DIALOG_ID);
				aula3.setHorai(mHour);
				aula3.setMini(mMinute);
			}
		});
		horaquinta.setOnClickListener(new View.OnClickListener()
		{
			public void onClick(View v)
			{
				showDialog(TIME_DIALOG_ID);
				aula4.setHorai(mHour);
				aula4.setMini(mMinute);
			}
		});
		horasexta.setOnClickListener(new View.OnClickListener()
		{
			public void onClick(View v)
			{
				showDialog(TIME_DIALOG_ID);
				aula5.setHorai(mHour);
				aula5.setMini(mMinute);
			}
		});
		seguintecriadisc.setOnClickListener(new OnClickListener()
		{
			public void onClick(View v)
			{
				if(togglesegunda.isChecked())
				{
					aula1.setDuracao(Integer.parseInt(durasegunda.getText().toString()));
					aula1.setSala(salasegunda.getText().toString());
					aula1.setDia("Segunda");
					temp.addElement(aula1);
				}
				if(toggleterca.isChecked())
				{
					aula2.setDuracao(Integer.parseInt(duraterca.getText().toString()));
					aula2.setSala(salaterca.getText().toString());
					aula2.setDia("Terça");
					temp.addElement(aula2);
				}
				if(togglequarta.isChecked())
				{
					aula3.setDuracao(Integer.parseInt(duraquarta.getText().toString()));
					aula3.setSala(salaquarta.getText().toString());
					aula3.setDia("Quarta");
					temp.addElement(aula3);
				}
				if(togglequinta.isChecked())
				{
					aula4.setDuracao(Integer.parseInt(duraquinta.getText().toString()));
					aula4.setSala(salaquinta.getText().toString());
					aula4.setDia("Quinta");
					temp.addElement(aula4);
				}
				if(togglesexta.isChecked())
				{
					aula5.setDuracao(Integer.parseInt(durasexta.getText().toString()));
					aula5.setSala(salasexta.getText().toString());
					aula5.setDia("Sexta");
					temp.addElement(aula5);
				}
				String _nomedisc = nomedisc.getText().toString();
				String _sigladisc = sigladisc.getText().toString();
				Disciplina disc1= new Disciplina(_nomedisc, _sigladisc);
				disc1.setAulas(temp);
				gestor.horario.addToDisciplinas(disc1);
				datasource.createDisciplina(disc1.getNome(), disc1.getSigla(), disc1.getAulas(), disc1.getTestes(), disc1.getTrabalhos());
				menuMenuCriaDisciplina(savedInstanceState);
			}
		});

		concluircriadisc.setOnClickListener(new OnClickListener()
		{
			public void onClick(View v)
			{
				menuMenuGerirDisciplinas(savedInstanceState);
			}
		});	 

		voltacriadisc.setOnClickListener(new OnClickListener()
		{
			public void onClick(View v)
			{
				menuMenuGerirDisciplinas(savedInstanceState);
			}
		});	 
	}

	public void menuMenuGerirDisciplinas(final Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.menugerirdisciplinas);

		Button adicionarteste = (Button) findViewById(R.id.adicionarteste);
		Button adicionartrabalho = (Button) findViewById(R.id.adicionartrabalho);
		Button adicionarexame = (Button) findViewById(R.id.adicionarexame);
		Button adicionaroutrascomponentes = (Button) findViewById(R.id.adicionaroutrascomponentes);
		Button remover = (Button) findViewById(R.id.remover);
		Button voltargerirdisciplinas = (Button) findViewById(R.id.voltargerirdisciplinas);

		adicionarteste.setOnClickListener(new OnClickListener()
		{
			public void onClick(View v)
			{
				menuMenuAdicionarTeste(savedInstanceState);
			}
		});
		adicionartrabalho.setOnClickListener(new OnClickListener()
		{
			public void onClick(View v)
			{
				menuMenuAdicionarTrabalho(savedInstanceState);
			}
		});
		adicionarexame.setOnClickListener(new OnClickListener()
		{
			public void onClick(View v)
			{
				menuMenuAdicionarExame(savedInstanceState);
			}
		});
		adicionaroutrascomponentes.setOnClickListener(new OnClickListener()
		{
			public void onClick(View v)
			{
				menuMenuOutrasComponentes(savedInstanceState);
			}
		});
		remover.setOnClickListener(new OnClickListener()
		{
			public void onClick(View v)
			{
				menuRemover(savedInstanceState);
			}
		});
		voltargerirdisciplinas.setOnClickListener(new OnClickListener()
		{
			public void onClick(View v)
			{
				menuMenuPrincipal(savedInstanceState);
			}
		});
	}

	public void menuMenuAdicionarTeste(final Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.addteste);

		final Teste teste1= new Teste();
		String array[] = new String[gestor.horario.getDisciplinas().size()];
		for(int i=0; i<gestor.horario.getDisciplinas().size();i++)
		{
			array[i] = gestor.horario.getDisciplinas().elementAt(i).getSigla();
		}

		final DatePicker addtestedatepicker = (DatePicker) findViewById(R.id.addtestedatepicker);
		final Spinner spinnerdisc = (Spinner) findViewById(R.id.spinnerdisc);
		Button addtesteselechora = (Button) findViewById(R.id.addtesteselechora);
		final EditText addtestesala = (EditText) findViewById(R.id.addtestesala);
		final EditText addtestedura = (EditText) findViewById(R.id.addtestedura);
		final EditText addtestepercent = (EditText) findViewById(R.id.addtestepercent);
		Button addtesteadicionar = (Button) findViewById(R.id.addtesteadicionar);
		Button addtestvoltar = (Button) findViewById(R.id.addtestevoltar);

		ArrayAdapter<String> adapter =  new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, array);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinnerdisc.setAdapter(adapter);
		addtesteselechora.setOnClickListener(new OnClickListener()
		{
			public void onClick(View v)
			{
				showDialog(TIME_DIALOG_ID);
				teste1.setHorai(mHour);
				teste1.setMini(mMinute);
			}
		});
		addtesteadicionar.setOnClickListener(new OnClickListener()
		{
			public void onClick(View v)
			{
				teste1.setAno(addtestedatepicker.getYear());
				teste1.setMes(addtestedatepicker.getMonth());
				teste1.setDia(addtestedatepicker.getDayOfMonth());
				teste1.setSalas(addtestesala.getText().toString());
				teste1.setDuracao(Integer.parseInt(addtestedura.getText().toString()));
				teste1.setPercentagem(Double.parseDouble(addtestepercent.getText().toString()));
				gestor.horario.getDisciplinas().elementAt(spinnerdisc.getSelectedItemPosition()).addToTestes(teste1);
				ActualizaDados();
				menuMenuGerirDisciplinas(savedInstanceState);
			}
		});
		addtestvoltar.setOnClickListener(new OnClickListener()
		{
			public void onClick(View v)
			{
				menuMenuGerirDisciplinas(savedInstanceState);
			}
		});
	}

	public void menuMenuAdicionarTrabalho(final Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.addtrab);

		final Trabalho trab1 = new Trabalho();

		String array[] = new String[gestor.horario.getDisciplinas().size()];
		for(int i=0; i<gestor.horario.getDisciplinas().size();i++)
		{
			array[i] = gestor.horario.getDisciplinas().elementAt(i).getSigla();
		}

		final DatePicker addtrabdatepicker = (DatePicker) findViewById(R.id.addtrabdatepicker);
		final Spinner spinnertrab = (Spinner) findViewById(R.id.spinnertrab);
		Button addtrabhoraentrega = (Button) findViewById(R.id.addtrabhoraentrega);
		final CheckBox addtrabentregadigital = (CheckBox) findViewById(R.id.addtrabentregadigital);
		Button addtrabadicionar = (Button) findViewById(R.id.addtrabadicionar);
		Button addtrabvoltar = (Button) findViewById(R.id.addtrabvoltar);

		ArrayAdapter<String> adapter =  new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, array);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinnertrab.setAdapter(adapter);

		addtrabhoraentrega.setOnClickListener(new OnClickListener()
		{
			public void onClick(View v)
			{
				showDialog(TIME_DIALOG_ID);
				trab1.setHorae(mHour);
				trab1.setMine(mMinute);
			}
		});
		addtrabadicionar.setOnClickListener(new OnClickListener()
		{
			public void onClick(View v)
			{
				if(addtrabentregadigital.isChecked())
				{
					trab1.setEntrega(true);
				}
				trab1.setAnoe(addtrabdatepicker.getYear());
				trab1.setMese(addtrabdatepicker.getMonth());
				trab1.setDia(addtrabdatepicker.getDayOfMonth());

				for(int j=0; j<gestor.horario.getDisciplinas().size(); j++)
				{
					if(spinnertrab.getSelectedItem().toString()==gestor.horario.getDisciplinas().elementAt(j).getNome())
					{
						gestor.horario.getDisciplinas().elementAt(j).addToTrabalhos(trab1);
					}
				}
				ActualizaDados();
				menuMenuGerirDisciplinas(savedInstanceState);
			}
		});
		addtrabvoltar.setOnClickListener(new OnClickListener()
		{
			public void onClick(View v)
			{
				menuMenuGerirDisciplinas(savedInstanceState);
			}
		});
	}

	public void menuMenuAdicionarExame(final Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.addteste);

		final Teste teste1= new Teste();
		String array[] = new String[gestor.horario.getDisciplinas().size()];
		for(int i=0; i<gestor.horario.getDisciplinas().size();i++)
		{
			array[i] = gestor.horario.getDisciplinas().elementAt(i).getSigla();
		}

		final DatePicker addtestedatepicker = (DatePicker) findViewById(R.id.addtestedatepicker);
		final Spinner spinnerdisc = (Spinner) findViewById(R.id.spinnerdisc);
		Button addtesteselechora = (Button) findViewById(R.id.addtesteselechora);
		final EditText addtestesala = (EditText) findViewById(R.id.addtestesala);
		final EditText addtestedura = (EditText) findViewById(R.id.addtestedura);
		final EditText addtestepercent = (EditText) findViewById(R.id.addtestepercent);
		Button addtesteadicionar = (Button) findViewById(R.id.addtesteadicionar);
		Button addtestvoltar = (Button) findViewById(R.id.addtestevoltar);

		ArrayAdapter<String> adapter =  new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, array);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinnerdisc.setAdapter(adapter);
		addtesteselechora.setOnClickListener(new OnClickListener()
		{
			public void onClick(View v)
			{
				showDialog(TIME_DIALOG_ID);
				teste1.setHorai(mHour);
				teste1.setMini(mMinute);
			}
		});
		addtesteadicionar.setOnClickListener(new OnClickListener()
		{
			public void onClick(View v)
			{
				teste1.setAno(addtestedatepicker.getYear());
				teste1.setMes(addtestedatepicker.getMonth());
				teste1.setDia(addtestedatepicker.getDayOfMonth());
				teste1.setSalas(addtestesala.getText().toString());
				teste1.setDuracao(Integer.parseInt(addtestedura.getText().toString()));
				teste1.setPercentagem(Double.parseDouble(addtestepercent.getText().toString()));
				teste1.setExame(true);
				for(int j=0; j<gestor.horario.getDisciplinas().size(); j++)
				{
					if(spinnerdisc.getSelectedItem().toString()==gestor.horario.getDisciplinas().elementAt(j).getNome())
					{
						gestor.horario.getDisciplinas().elementAt(j).addToTestes(teste1);
					}
				}
				ActualizaDados();
				menuMenuGerirDisciplinas(savedInstanceState);
			}
		});
		addtestvoltar.setOnClickListener(new OnClickListener()
		{
			public void onClick(View v)
			{
				menuMenuGerirDisciplinas(savedInstanceState);
			}
		});
	}

	public void menuMenuOutrasComponentes(final Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.addoutrascomp);

		final OutrasComponentes outras1 = new OutrasComponentes();

		String array[] = new String[gestor.horario.getDisciplinas().size()];
		for(int i=0; i<gestor.horario.getDisciplinas().size();i++)
		{
			array[i] = gestor.horario.getDisciplinas().elementAt(i).getSigla();
		}

		final Spinner spinneroutras = (Spinner) findViewById(R.id.spinneroutras);
		final EditText addoutrasnota = (EditText) findViewById(R.id.addoutrasnota);
		final EditText addoutraspercent = (EditText) findViewById(R.id.addoutraspercent);

		Button addoutrasadicionar = (Button) findViewById(R.id.addoutrasadicionar);
		Button addoutrasvoltar = (Button) findViewById(R.id.addoutrasvoltar);

		ArrayAdapter<String> adapter =  new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, array);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinneroutras.setAdapter(adapter);

		addoutrasadicionar.setOnClickListener(new OnClickListener()
		{
			public void onClick(View v)
			{
				outras1.setNota(Double.parseDouble(addoutrasnota.getText().toString()));
				outras1.setPercentagem(Double.parseDouble(addoutraspercent.getText().toString()));
				for(int j=0; j<gestor.horario.getDisciplinas().size(); j++)
				{
					if(spinneroutras.getSelectedItem().toString()==gestor.horario.getDisciplinas().elementAt(j).getNome())
					{
						gestor.horario.getDisciplinas().elementAt(j).setOutrasComponentes(outras1);
					}
				}
				ActualizaDados();
				menuMenuGerirDisciplinas(savedInstanceState);
			}
		});
		addoutrasvoltar.setOnClickListener(new OnClickListener()
		{
			public void onClick(View v)
			{
				menuMenuGerirDisciplinas(savedInstanceState);
			}
		});
	}

	public void menuVisualizador(final Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.visualizador);

		Button visualizadorhorario = (Button) findViewById(R.id.visualizadorhorario);
		Button visualizadortestes = (Button) findViewById(R.id.visualizadortestes);
		Button visualizadorexames = (Button) findViewById(R.id.visualizadorexames);
		Button visualizadortrabalhos = (Button) findViewById(R.id.visualizadortrabalhos);
		Button visualizadorvoltar = (Button) findViewById(R.id.visualizadorvoltar);

		visualizadorhorario.setOnClickListener(new OnClickListener()
		{
			public void onClick(View v)
			{ 
				mostraHorario(savedInstanceState);
			}
		});
		visualizadortrabalhos.setOnClickListener(new OnClickListener()
		{
			public void onClick(View v)
			{ 
				menuMenuVerTrabalhos(savedInstanceState);
			}
		});
		visualizadortestes.setOnClickListener(new OnClickListener()
		{
			public void onClick(View v)
			{ 
				menuMenuVerTestes(savedInstanceState);
			}
		});
		visualizadorexames.setOnClickListener(new OnClickListener()
		{
			public void onClick(View v)
			{ 
				menuMenuVerExames(savedInstanceState);
			}
		});
		visualizadorvoltar.setOnClickListener(new OnClickListener()
		{
			public void onClick(View v)
			{ 
				menuMenuPrincipal(savedInstanceState);
			}
		});
	}

	public void menuRemover(final Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.remover);

		Button removerteste = (Button) findViewById(R.id.removerteste);
		Button removertrabalho = (Button) findViewById(R.id.removertrabalho);
		Button removeroutrascomponentes = (Button) findViewById(R.id.removeroutrascomponentes);
		Button removerdisciplina = (Button) findViewById(R.id.removerdisciplina);
		Button voltarremover = (Button) findViewById(R.id.voltarremover);
		removerteste.setOnClickListener(new OnClickListener()
		{
			public void onClick(View v)
			{ 
				menuMenuRemoverTeste(savedInstanceState);
			}
		});
		removertrabalho.setOnClickListener(new OnClickListener()
		{
			public void onClick(View v)
			{ 
				menuMenuRemoverTrabalho(savedInstanceState);
			}
		});
		removeroutrascomponentes.setOnClickListener(new OnClickListener()
		{
			public void onClick(View v)
			{ 
				menuMenuRemoverOutrasComponentes(savedInstanceState);
			}
		});
		removerdisciplina.setOnClickListener(new OnClickListener()
		{
			public void onClick(View v)
			{ 
				menuMenuRemoverDisciplinas(savedInstanceState);
			}
		});
		voltarremover.setOnClickListener(new OnClickListener()
		{
			public void onClick(View v)
			{ 
				menuMenuGerirDisciplinas(savedInstanceState);
			}
		});
	}

	public void menuMenuRemoverTeste(final Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.removerteste);

		String array[] = new String[gestor.horario.getDisciplinas().size()];
		for(int i=0; i<gestor.horario.getDisciplinas().size();i++)
		{
			array[i] = gestor.horario.getDisciplinas().elementAt(i).getSigla();
		}

		final Spinner spinnerremovertestedisc = (Spinner) findViewById(R.id.spinnerremovertestedisc);
		final Spinner spinnerremoverteste = (Spinner) findViewById(R.id.spinnerremoverteste);
		Button removertesteremover = (Button) findViewById(R.id.removertesteremover);
		Button removertestevoltar = (Button) findViewById(R.id.removertestevoltar);

		ArrayAdapter<String> adapter1 =  new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, array);
		adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinnerremovertestedisc.setAdapter(adapter1);

		final String arraytest[] = new String[gestor.horario.getDisciplinas().size()];
		for(int j=0; j<gestor.horario.getDisciplinas().size(); j++)
		{
			if(gestor.horario.getDisciplinas().elementAt(j).getNome()==spinnerremovertestedisc.getSelectedItem().toString())
			{
				for(int i=0; i<gestor.horario.getDisciplinas().elementAt(j).getTestes().size();i++)
				{
					if(gestor.horario.getDisciplinas().elementAt(j).getTestes().elementAt(i).isExame())
					{
						arraytest[i] = "Exame";
					}
					arraytest[i] = "Teste" + i;
				}
			}
		}

		ArrayAdapter<String> adapter2 =  new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, arraytest);
		adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinnerremoverteste.setAdapter(adapter2);

		removertesteremover.setOnClickListener(new OnClickListener()
		{
			public void onClick(View v)
			{
				for(int j=0; j<gestor.horario.getDisciplinas().size(); j++)
				{
					if(gestor.horario.getDisciplinas().elementAt(j).getNome()==spinnerremovertestedisc.getSelectedItem().toString())
					{
						gestor.horario.getDisciplinas().elementAt(j).getTestes().remove(Integer.parseInt(arraytest[spinnerremoverteste.getSelectedItemPosition()]));
					}
				}
				ActualizaDados();
				menuRemover(savedInstanceState);
			}
		});
		removertestevoltar.setOnClickListener(new OnClickListener()
		{
			public void onClick(View v)
			{
				menuRemover(savedInstanceState);
			}
		});
	}

	public void menuMenuRemoverTrabalho(final Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.removerteste);

		String array[] = new String[gestor.horario.getDisciplinas().size()];
		for(int i=0; i<gestor.horario.getDisciplinas().size();i++)
		{
			array[i] = gestor.horario.getDisciplinas().elementAt(i).getSigla();
		}

		final Spinner spinnerremovertestedisc = (Spinner) findViewById(R.id.spinnerremovertestedisc);
		final Spinner spinnerremoverteste = (Spinner) findViewById(R.id.spinnerremoverteste);
		Button removertesteremover = (Button) findViewById(R.id.removertesteremover);
		Button removertestevoltar = (Button) findViewById(R.id.removertestevoltar);

		ArrayAdapter<String> adapter1 =  new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, array);
		adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinnerremovertestedisc.setAdapter(adapter1);

		final String arraytest[] = new String[gestor.horario.getDisciplinas().size()];
		for(int j=0; j<gestor.horario.getDisciplinas().size(); j++)
		{
			if(gestor.horario.getDisciplinas().elementAt(j).getNome()==spinnerremovertestedisc.getSelectedItem().toString())
			{
				for(int i=0; i<gestor.horario.getDisciplinas().elementAt(j).getTrabalhos().size();i++)
				{
					arraytest[i] = "" + i;
				}
			}
		}

		ArrayAdapter<String> adapter2 =  new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, arraytest);
		adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinnerremoverteste.setAdapter(adapter2);

		removertesteremover.setOnClickListener(new OnClickListener()
		{
			public void onClick(View v)
			{
				for(int j=0; j<gestor.horario.getDisciplinas().size(); j++)
				{
					if(gestor.horario.getDisciplinas().elementAt(j).getNome()==spinnerremovertestedisc.getSelectedItem().toString())
					{
						gestor.horario.getDisciplinas().elementAt(j).getTrabalhos().remove(Integer.parseInt(arraytest[spinnerremoverteste.getSelectedItemPosition()]));
					}
				}
				ActualizaDados();
				menuRemover(savedInstanceState);
			}
		});
		removertestevoltar.setOnClickListener(new OnClickListener()
		{
			public void onClick(View v)
			{
				menuRemover(savedInstanceState);
			}
		});
	}

	public void menuMenuRemoverOutrasComponentes(final Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.removerdisc);

		String array[] = new String[gestor.horario.getDisciplinas().size()];
		for(int i=0; i<gestor.horario.getDisciplinas().size();i++)
		{
			array[i] = gestor.horario.getDisciplinas().elementAt(i).getSigla();
		}

		final Spinner spinnerremoverdisc = (Spinner) findViewById(R.id.spinnerremoverdisc);
		Button removerdiscvoltar = (Button) findViewById(R.id.removerdiscvoltar);
		Button removerdiscremover = (Button) findViewById(R.id.removerdiscremover);

		ArrayAdapter<String> adapter1 =  new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, array);
		adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinnerremoverdisc.setAdapter(adapter1);

		removerdiscremover.setOnClickListener(new OnClickListener()
		{
			public void onClick(View v)
			{
				for(int j=0; j<gestor.horario.getDisciplinas().size(); j++)
				{
					if(gestor.horario.getDisciplinas().elementAt(j).getNome()==spinnerremoverdisc.getSelectedItem().toString())
					{
						gestor.horario.getDisciplinas().elementAt(j).getOutrasComponentes().setPercentagem(0);
					}
				}
				menuRemover(savedInstanceState);
			}
		});
		removerdiscvoltar.setOnClickListener(new OnClickListener()
		{
			public void onClick(View v)
			{
				menuRemover(savedInstanceState);
			}
		});
	}

	public void menuMenuRemoverDisciplinas(final Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.removerdisc);

		String array[] = new String[gestor.horario.getDisciplinas().size()];
		for(int i=0; i<gestor.horario.getDisciplinas().size();i++)
		{
			array[i] = gestor.horario.getDisciplinas().elementAt(i).getSigla();
		}

		final Spinner spinnerremoverdisc = (Spinner) findViewById(R.id.spinnerremoverdisc);
		Button removerdiscvoltar = (Button) findViewById(R.id.removerdiscvoltar);
		Button removerdiscremover = (Button) findViewById(R.id.removerdiscremover);

		ArrayAdapter<String> adapter1 =  new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, array);
		adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinnerremoverdisc.setAdapter(adapter1);

		removerdiscremover.setOnClickListener(new OnClickListener()
		{
			public void onClick(View v)
			{
				for(int j=0; j<gestor.horario.getDisciplinas().size(); j++)
				{
					if(gestor.horario.getDisciplinas().elementAt(j).getNome()==spinnerremoverdisc.getSelectedItem().toString())
					{
						gestor.horario.getDisciplinas().removeElementAt(j);
					}
				}
				menuRemover(savedInstanceState);
			}
		});
		removerdiscvoltar.setOnClickListener(new OnClickListener()
		{
			public void onClick(View v)
			{
				menuRemover(savedInstanceState);
			}
		});
	}

	public void menuMenuVerTestes(final Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.vertestes);

		String array[] = new String[gestor.horario.getDisciplinas().size()];
		for(int i=0; i<gestor.horario.getDisciplinas().size();i++)
		{
			array[i] = gestor.horario.getDisciplinas().elementAt(i).getSigla();
		}

		final Spinner spinnervertestes = (Spinner) findViewById(R.id.spinnervertestes);
		Button vertestesvoltar = (Button) findViewById(R.id.vertestesvoltar);
		Button vertestesactualizar = (Button) findViewById(R.id.vertestesactualizar);

		ArrayAdapter<String> adapter1 =  new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, array);
		adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinnervertestes.setAdapter(adapter1);

		vertestesactualizar.setOnClickListener(new OnClickListener()
		{
			public void onClick(View v)
			{
				mostraTestes(gestor.horario.getDisciplinas().elementAt(spinnervertestes.getSelectedItemPosition()));
			}
		});
		vertestesvoltar.setOnClickListener(new OnClickListener()
		{
			public void onClick(View v)
			{
				menuVisualizador(savedInstanceState);
			}
		});
	}

	public void menuMenuVerExames(final Bundle savedInstanceState)
	{
		mostraExames();
		super.onCreate(savedInstanceState);
		setContentView(R.layout.verexames);

		Button verexamesvoltar = (Button) findViewById(R.id.verexamesvoltar);
		verexamesvoltar.setOnClickListener(new OnClickListener()
		{
			public void onClick(View v)
			{
				menuVisualizador(savedInstanceState);
			}
		});
	}

	public void menuMenuVerTrabalhos(final Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.vertestes);

		String array[] = new String[gestor.horario.getDisciplinas().size()];
		for(int i=0; i<gestor.horario.getDisciplinas().size();i++)
		{
			array[i] = gestor.horario.getDisciplinas().elementAt(i).getSigla();
		}

		final Spinner spinnervertestes = (Spinner) findViewById(R.id.spinnervertestes);
		Button vertestesvoltar = (Button) findViewById(R.id.vertestesvoltar);
		Button vertestesactualizar = (Button) findViewById(R.id.vertestesactualizar);

		ArrayAdapter<String> adapter1 =  new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, array);
		adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinnervertestes.setAdapter(adapter1);

		vertestesactualizar.setOnClickListener(new OnClickListener()
		{
			public void onClick(View v)
			{
				mostraTrabalhos(gestor.horario.getDisciplinas().elementAt(spinnervertestes.getSelectedItemPosition()));
			}
		});
		vertestesvoltar.setOnClickListener(new OnClickListener()
		{
			public void onClick(View v)
			{
				menuVisualizador(savedInstanceState);
			}
		});
	}

	public void mostraHorario(final Bundle savedInstanceState)
	{

		super.onCreate(savedInstanceState);
		setContentView(R.layout.timetable);


		datasource.open();

		//setup tab host
		TabHost tabHost = (TabHost) findViewById(android.R.id.tabhost);//
		tabHost.setup();
		tabHost.setOnTabChangedListener(this);

		//setup lists
		mondayLW = (ListView) findViewById(R.id.tabsegunda);
		tuesdayLW = (ListView) findViewById(R.id.tabterca);
		wednesdayLW = (ListView) findViewById(R.id.tabquarta);
		thursdayLW = (ListView) findViewById(R.id.tabquinta);
		fridayLW = (ListView) findViewById(R.id.tabsexta);

		// add views to tab host
		tabHost.addTab(tabHost.newTabSpec(MONDAY_TAB_TAG)
				.setIndicator(MONDAY_TAB_TAG)
				.setContent(new TabContentFactory() {
					public View createTabContent(String arg0) {
						return mondayLW;
					}
				}));
		tabHost.addTab(tabHost.newTabSpec(TUESDAY_TAB_TAG)
				.setIndicator(TUESDAY_TAB_TAG)
				.setContent(new TabContentFactory() {
					public View createTabContent(String arg0) {
						return tuesdayLW;
					}
				}));
		tabHost.addTab(tabHost.newTabSpec(WEDNESDAY_TAB_TAG)
				.setIndicator(WEDNESDAY_TAB_TAG)
				.setContent(new TabContentFactory() {
					public View createTabContent(String arg0) {
						return wednesdayLW;
					}
				}));
		tabHost.addTab(tabHost.newTabSpec(THURSDAY_TAB_TAG)
				.setIndicator(THURSDAY_TAB_TAG)
				.setContent(new TabContentFactory() {
					public View createTabContent(String arg0) {
						return thursdayLW;
					}
				}));
		tabHost.addTab(tabHost.newTabSpec(FRIDAY_TAB_TAG)
				.setIndicator(FRIDAY_TAB_TAG)
				.setContent(new TabContentFactory() {
					public View createTabContent(String arg0) {
						return fridayLW;
					}
				}));

		int diaActual = getCurrentDayofWeek();
		tabHost.setCurrentTab(diaActual);
		currentLW = mondayLW;
		update();
	}

	public void menuSimulador(final Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.simulador);


		String esc[] = new String[4];
		esc[0]="Calcular Média";
		esc[1]="";
		esc[2]="";
		esc[3]="";

		String disc[] = new String[gestor.horario.getDisciplinas().size()];
		for(int i=0 ; i<gestor.horario.getDisciplinas().size(); i++)
		{
			disc[i]=gestor.horario.getDisciplinas().elementAt(i).getNome();
		}

		final Spinner spinnersimuladoresc = (Spinner) findViewById(R.id.spinnersimuladoresc);
		final Spinner spinnersimuladordisc = (Spinner) findViewById(R.id.spinnersimuladordisc);
		Button simuladorvoltar = (Button) findViewById(R.id.simuladorvoltar);
		Button simuladorseguinte = (Button) findViewById(R.id.simuladorseguinte);

		ArrayAdapter<String> adapter1 =  new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, esc);
		adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinnersimuladoresc.setAdapter(adapter1);

		ArrayAdapter<String> adapter2 =  new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, disc);
		adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinnersimuladordisc.setAdapter(adapter2);

		simuladorvoltar.setOnClickListener(new OnClickListener()
		{
			public void onClick(View v)
			{
				menuMenuPrincipal(savedInstanceState);
			}
		});

		simuladorseguinte.setOnClickListener(new OnClickListener()
		{
			public void onClick(View v)
			{
				if(spinnersimuladoresc.getSelectedItem().toString()=="Calcular Média")
				{
					menuCalculaMedia(savedInstanceState);
				}
			}
		});
	}

	public void menuCalculaMedia(final Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.simulador);
		
		TextView disc[] = new TextView[9];
		final TextView nota[] = new TextView[9];

		TextView calcularmediadisc1 = (TextView) findViewById(R.id.calcularmediadisc1);
		disc[0] = calcularmediadisc1;
		TextView calcularmediadisc2 = (TextView) findViewById(R.id.calcularmediadisc2);
		disc[1] = calcularmediadisc2;
		TextView calcularmediadisc3 = (TextView) findViewById(R.id.calcularmediadisc3);
		disc[2] = calcularmediadisc3;
		TextView calcularmediadisc4 = (TextView) findViewById(R.id.calcularmediadisc4);
		disc[3] = calcularmediadisc4;
		TextView calcularmediadisc5 = (TextView) findViewById(R.id.calcularmediadisc5);
		disc[4] = calcularmediadisc5;
		TextView calcularmediadisc6 = (TextView) findViewById(R.id.calcularmediadisc6);
		disc[5] = calcularmediadisc6;
		TextView calcularmediadisc7 = (TextView) findViewById(R.id.calcularmediadisc7);
		disc[6] = calcularmediadisc7;
		TextView calcularmediadisc8 = (TextView) findViewById(R.id.calcularmediadisc8);
		disc[7] = calcularmediadisc8;
		TextView calcularmediadisc9 = (TextView) findViewById(R.id.calcularmediadisc9);
		disc[8] = calcularmediadisc9;

		EditText calcularmedianota1 = (EditText) findViewById(R.id.calcularmedianota1);
		nota[0] = calcularmedianota1;
		EditText calcularmedianota2 = (EditText) findViewById(R.id.calcularmedianota2);
		nota[1] = calcularmedianota2;
		EditText calcularmedianota3 = (EditText) findViewById(R.id.calcularmedianota3);
		nota[2] = calcularmedianota3;
		EditText calcularmedianota4 = (EditText) findViewById(R.id.calcularmedianota4);
		nota[3] = calcularmedianota4;
		EditText calcularmedianota5 = (EditText) findViewById(R.id.calcularmedianota5);
		nota[4] = calcularmedianota5;
		EditText calcularmedianota6 = (EditText) findViewById(R.id.calcularmedianota6);
		nota[5] = calcularmedianota6;
		EditText calcularmedianota7 = (EditText) findViewById(R.id.calcularmedianota7);
		nota[6] = calcularmedianota7;
		EditText calcularmedianota8 = (EditText) findViewById(R.id.calcularmedianota8);
		nota[7] = calcularmedianota8;
		EditText calcularmedianota9 = (EditText) findViewById(R.id.calcularmedianota9);
		nota[8] = calcularmedianota9;

		TextView calcularmediamedia10 = (TextView) findViewById(R.id.calcularmediamedia10);

		Button calcularmediavoltar = (Button) findViewById(R.id.calcularmediavoltar);
		Button calcularmediaactualiza = (Button) findViewById(R.id.calcularmediaactualiza);
		Button calcularmediaseguinte = (Button) findViewById(R.id.calcularmediaseguinte);

		Double notat = 0.0;
		Double media = 0.0;
		for(int i=0; i<gestor.horario.getDisciplinas().size(); i++)
		{
			disc[i].setText("" + gestor.horario.getDisciplinas().elementAt(i).getSigla());
			nota[i].setText("" + gestor.horario.getDisciplinas().elementAt(i).getNota());
			notat+=gestor.horario.getDisciplinas().elementAt(i).getNota();
		}
		media=notat/gestor.horario.getDisciplinas().size();
		calcularmediamedia10.setText(media.toString());

		calcularmediavoltar.setOnClickListener(new OnClickListener()
		{
			public void onClick(View v)
			{
				menuSimulador(savedInstanceState);
			}
		});
		calcularmediaactualiza.setOnClickListener(new OnClickListener()
		{
			public void onClick(View v)
			{
				for(int j=0; j<gestor.horario.getDisciplinas().size(); j++)
				{
					gestor.horario.getDisciplinas().elementAt(j).setNota(Double.parseDouble(nota[j].getText().toString()));
				}
				menuCalculaMedia(savedInstanceState);
			}
		});
		calcularmediaseguinte.setOnClickListener(new OnClickListener()
		{
			public void onClick(View v)
			{
				for(int j=0; j<gestor.horario.getDisciplinas().size(); j++)
				{
					gestor.horario.getDisciplinas().elementAt(j).setNota(Double.parseDouble(nota[j].getText().toString()));
				}
				menuSimulador(savedInstanceState);
			}
		});
	}

	public void menuOpcoes(final Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.opcoes);

		Button reset = (Button) findViewById(R.id.reset);
		Button opcoesvoltar = (Button) findViewById(R.id.opcoesvoltar);
		opcoesvoltar.setOnClickListener(new OnClickListener()
		{
			public void onClick(View v)
			{
				menuMenuPrincipal(savedInstanceState);
			}
		});
	}

	private int getCurrentDayofWeek() {

		switch (currentDay) 
		{
		case 1:
			/*Sunday*/
			currentLW = mondayLW;
			currentDay = 0;
			break;
		case 2:
			/*Monday*/
			currentLW = mondayLW;
			currentDay -= 2;
			break;
		case 3:
			/*Tuesday*/
			currentLW = tuesdayLW;
			currentDay -= 2;
			break;
		case 4:
			/*Wednesday*/
			currentLW = wednesdayLW;
			currentDay -= 2;
			break;
		case 5:
			/*Thursday*/
			currentLW = thursdayLW;
			currentDay -= 2;
			break;
		case 6:
			/*Friday*/
			currentLW = fridayLW;
			currentDay -= 2;
			break;
		case 7:
			/*Saturday*/
			currentLW = mondayLW;
			currentDay = 0;
			break;
		}
		return currentDay;
	}

	public void onTabChanged(String tabName) {  
		/** Change the tab content to the appropriated one*/

		if(tabName.equals(MONDAY_TAB_TAG)) {
			currentLW = mondayLW;
			currentDay = 0;
		}
		else if(tabName.equals(TUESDAY_TAB_TAG)) {
			currentLW = tuesdayLW;
			currentDay = 1;
		}
		else if(tabName.equals(WEDNESDAY_TAB_TAG)) {
			currentLW = wednesdayLW;
			currentDay = 2;
		}
		else if(tabName.equals(THURSDAY_TAB_TAG)) {
			currentLW = thursdayLW;
			currentDay = 3;
		}
		else if(tabName.equals(FRIDAY_TAB_TAG)) {
			currentLW = fridayLW;
			currentDay = 4;
		}
	}

	void update() {

		String DSemana;
		if(currentDay==1) DSemana="Terça";
		else if(currentDay==2) DSemana="Quarta";
		else if(currentDay==3) DSemana="Quinta";
		else if(currentDay==4) DSemana="Sexta";
		else DSemana="Segunda";

		results = new ArrayList<HashMap<String, Object>>();
		HashMap<String,Object> resultsMap = new HashMap<String, Object>();
		for(int i=0;i<gestor.horario.getDisciplinas().size();i++){
			for(int j=0;j<gestor.horario.getDisciplinas().elementAt(i).getAulas().size();j++){
				if(gestor.horario.getDisciplinas().elementAt(i).getAulas().elementAt(j).getDia().equals(DSemana)){   
					resultsMap.put("Horario_Disciplina" , gestor.horario.getDisciplinas().elementAt(i).getNome());
					resultsMap.put("Horario_Sigla" ,gestor.horario.getDisciplinas().elementAt(i).getSigla());
					resultsMap.put("Horario_Sala" , gestor.horario.getDisciplinas().elementAt(i).getAulas().elementAt(i));
					resultsMap.put("Horario_Hora" , gestor.horario.getDisciplinas().elementAt(i).getAulas().elementAt(j).getHorai() + gestor.horario.getDisciplinas().elementAt(i).getAulas().elementAt(j).getMini() );
					resultsMap.put("Horario_Duracao" , gestor.horario.getDisciplinas().elementAt(i).getAulas().elementAt(j).getDuracao());
					results.add(resultsMap);
				}
			}
		}
		//results = datasource.selectAllFromDay(currentDay);
		//colorVet = new String[results.size()];

		simpleAdapter = new myAdapter(this, results, R.layout.daylist, from, to);
		currentLW.setAdapter(simpleAdapter); 
	}

	public class myAdapter extends SimpleAdapter{

		public myAdapter(Context context, List<? extends Map<String, ?>> data,
				int resource, String[] from, int[] to) {
			super(context, data, resource, from, to);
		}
		@Override
		public View getView(int position, View convertView, ViewGroup parent ) {
			View view = super.getView(position, convertView, parent);
			//View label = view.findViewById(R.id.colorLabel);
			//label.setBackgroundColor(Color.parseColor(colors[position]));
			return view;
		}
	}

	public void ActualizaDados()
	{
		for(int i=0; i<gestor.horario.getDisciplinas().size(); i++)
		{
			datasource.actualizaLinha(
					gestor.horario.getDisciplinas().elementAt(i).getNome(),
					gestor.horario.getDisciplinas().elementAt(i).getSigla(), 
					gestor.horario.getDisciplinas().elementAt(i).getAulas(), 
					gestor.horario.getDisciplinas().elementAt(i).getTestes(), 
					gestor.horario.getDisciplinas().elementAt(i).getTrabalhos()
					);
		}
	}

	public void mostraTestes(Disciplina d)
	{
		TextView DATA[] = new TextView[7];
		TextView DIA[] = new TextView[7];
		TextView PERCENTAGEM[] = new TextView[7];

		TextView verteste1DATA = (TextView) findViewById(R.id.verteste1DATA);
		DATA[0]=verteste1DATA;
		TextView verteste1DIA = (TextView) findViewById(R.id.verteste1DIA);
		DIA[0]=verteste1DIA;
		TextView verteste1PERCENTAGEM = (TextView) findViewById(R.id.verteste1PERCENTAGEM);
		PERCENTAGEM[0] = verteste1PERCENTAGEM;
		TextView verteste2DATA = (TextView) findViewById(R.id.verteste2DATA);
		DATA[1]=verteste2DATA;
		TextView verteste2DIA = (TextView) findViewById(R.id.verteste2DIA);
		DIA[1]=verteste2DIA;
		TextView verteste2PERCENTAGEM = (TextView) findViewById(R.id.verteste2PERCENTAGEM);
		PERCENTAGEM[1] = verteste2PERCENTAGEM;
		TextView verteste3DATA = (TextView) findViewById(R.id.verteste3DATA);
		DATA[2]=verteste3DATA;
		TextView verteste3DIA = (TextView) findViewById(R.id.verteste3DIA);
		DIA[2]=verteste3DIA;
		TextView verteste3PERCENTAGEM = (TextView) findViewById(R.id.verteste3PERCENTAGEM);
		PERCENTAGEM[2] = verteste3PERCENTAGEM;
		TextView verteste4DATA = (TextView) findViewById(R.id.verteste4DATA);
		DATA[3]=verteste4DATA;
		TextView verteste4DIA = (TextView) findViewById(R.id.verteste4DIA);
		DIA[3]=verteste4DIA;
		TextView verteste4PERCENTAGEM = (TextView) findViewById(R.id.verteste4PERCENTAGEM);
		PERCENTAGEM[3] = verteste4PERCENTAGEM;
		TextView verteste5DATA = (TextView) findViewById(R.id.verteste5DATA);
		DATA[4]=verteste5DATA;
		TextView verteste5DIA = (TextView) findViewById(R.id.verteste5DIA);
		DIA[4]=verteste5DIA;
		TextView verteste5PERCENTAGEM = (TextView) findViewById(R.id.verteste5PERCENTAGEM);
		PERCENTAGEM[4] = verteste5PERCENTAGEM;
		TextView verteste6DATA = (TextView) findViewById(R.id.verteste6DATA);
		DATA[5]=verteste6DATA;
		TextView verteste6DIA = (TextView) findViewById(R.id.verteste6DIA);
		DIA[5]=verteste6DIA;
		TextView verteste6PERCENTAGEM = (TextView) findViewById(R.id.verteste6PERCENTAGEM);
		PERCENTAGEM[5] = verteste6PERCENTAGEM;
		TextView verteste7DATA = (TextView) findViewById(R.id.verteste7DATA);
		DATA[6]=verteste7DATA;
		TextView verteste7DIA = (TextView) findViewById(R.id.verteste7DIA);
		DIA[6]=verteste7DIA;
		TextView verteste7PERCENTAGEM = (TextView) findViewById(R.id.verteste7PERCENTAGEM);
		PERCENTAGEM[6] = verteste7PERCENTAGEM;


		for(int i=0; i<d.getTestes().size(); i++)
		{
			if(!d.getTestes().elementAt(i).isExame())
			{
				String date = "" + d.getTestes().elementAt(i).getDia() + "/" + d.getTestes().elementAt(i).getMes() + "/" +  d.getTestes().elementAt(i).getAno();
				Calendar cal = Calendar.getInstance();
				cal.set(d.getTestes().elementAt(i).getAno(), d.getTestes().elementAt(i).getMes(), d.getTestes().elementAt(i).getDia());
				int DiaS=cal.get(Calendar.DAY_OF_WEEK);
				String diaS;
				if(DiaS==2)
				{
					diaS="Segunda-feira";
				}
				else if(DiaS==3)
				{
					diaS="Terça-feira";
				}
				else if(DiaS==4)
				{
					diaS="Quarta-feira";
				}
				else if(DiaS==5)
				{
					diaS="Quinta-feira";
				}
				else
				{
					diaS="Sexta-feira";
				}
				String percentagem = "" + d.getTestes().elementAt(i).getPercentagem() + "%";
				DATA[i].setText(date);
				DIA[i].setText(diaS);
				PERCENTAGEM[i].setText(percentagem);
			}
		}
	}

	public void mostraExames()
	{
		TextView DATA[] = new TextView[7];
		TextView DIA[] = new TextView[7];
		TextView PERCENTAGEM[] = new TextView[7];

		TextView verteste1DATA = (TextView) findViewById(R.id.verteste1DATA);
		DATA[0]=verteste1DATA;
		TextView verteste1DIA = (TextView) findViewById(R.id.verteste1DIA);
		DIA[0]=verteste1DIA;
		TextView verteste1PERCENTAGEM = (TextView) findViewById(R.id.verteste1PERCENTAGEM);
		PERCENTAGEM[0] = verteste1PERCENTAGEM;
		TextView verteste2DATA = (TextView) findViewById(R.id.verteste2DATA);
		DATA[1]=verteste2DATA;
		TextView verteste2DIA = (TextView) findViewById(R.id.verteste2DIA);
		DIA[1]=verteste2DIA;
		TextView verteste2PERCENTAGEM = (TextView) findViewById(R.id.verteste2PERCENTAGEM);
		PERCENTAGEM[1] = verteste2PERCENTAGEM;
		TextView verteste3DATA = (TextView) findViewById(R.id.verteste3DATA);
		DATA[2]=verteste3DATA;
		TextView verteste3DIA = (TextView) findViewById(R.id.verteste3DIA);
		DIA[2]=verteste3DIA;
		TextView verteste3PERCENTAGEM = (TextView) findViewById(R.id.verteste3PERCENTAGEM);
		PERCENTAGEM[2] = verteste3PERCENTAGEM;
		TextView verteste4DATA = (TextView) findViewById(R.id.verteste4DATA);
		DATA[3]=verteste4DATA;
		TextView verteste4DIA = (TextView) findViewById(R.id.verteste4DIA);
		DIA[3]=verteste4DIA;
		TextView verteste4PERCENTAGEM = (TextView) findViewById(R.id.verteste4PERCENTAGEM);
		PERCENTAGEM[3] = verteste4PERCENTAGEM;
		TextView verteste5DATA = (TextView) findViewById(R.id.verteste5DATA);
		DATA[4]=verteste5DATA;
		TextView verteste5DIA = (TextView) findViewById(R.id.verteste5DIA);
		DIA[4]=verteste5DIA;
		TextView verteste5PERCENTAGEM = (TextView) findViewById(R.id.verteste5PERCENTAGEM);
		PERCENTAGEM[4] = verteste5PERCENTAGEM;
		TextView verteste6DATA = (TextView) findViewById(R.id.verteste6DATA);
		DATA[5]=verteste6DATA;
		TextView verteste6DIA = (TextView) findViewById(R.id.verteste6DIA);
		DIA[5]=verteste6DIA;
		TextView verteste6PERCENTAGEM = (TextView) findViewById(R.id.verteste6PERCENTAGEM);
		PERCENTAGEM[5] = verteste6PERCENTAGEM;
		TextView verteste7DATA = (TextView) findViewById(R.id.verteste7DATA);
		DATA[6]=verteste7DATA;
		TextView verteste7DIA = (TextView) findViewById(R.id.verteste7DIA);
		DIA[6]=verteste7DIA;
		TextView verteste7PERCENTAGEM = (TextView) findViewById(R.id.verteste7PERCENTAGEM);
		PERCENTAGEM[6] = verteste7PERCENTAGEM;

		int a=0;

		for(int i=0; i<gestor.horario.getDisciplinas().size(); i++)
		{
			for(int j=0; j<gestor.horario.getDisciplinas().elementAt(i).getTestes().size(); j++)
			{
				if(gestor.horario.getDisciplinas().elementAt(i).getTestes().elementAt(i).isExame())
				{
					String date = ""
							+ gestor.horario.getDisciplinas().elementAt(i).getTestes().elementAt(i).getDia()
							+ "/" + gestor.horario.getDisciplinas().elementAt(i).getTestes().elementAt(i).getMes()
							+ "/" + gestor.horario.getDisciplinas().elementAt(i).getTestes().elementAt(i).getAno();

					Calendar cal = Calendar.getInstance();

					cal.set(gestor.horario.getDisciplinas().elementAt(i).getTestes().elementAt(i).getAno(),
							gestor.horario.getDisciplinas().elementAt(i).getTestes().elementAt(i).getMes(),
							gestor.horario.getDisciplinas().elementAt(i).getTestes().elementAt(i).getDia());

					int DiaS=cal.get(Calendar.DAY_OF_WEEK);

					String diaS;
					if(DiaS==2)
					{
						diaS="Segunda-feira";
					}
					else if(DiaS==3)
					{
						diaS="Terça-feira";
					}
					else if(DiaS==4)
					{
						diaS="Quarta-feira";
					}
					else if(DiaS==5)
					{
						diaS="Quinta-feira";
					}
					else
					{
						diaS="Sexta-feira";
					}

					String percentagem = "" + gestor.horario.getDisciplinas().elementAt(i).getTestes().elementAt(i).getPercentagem() + "%";

					DATA[a].setText(date);
					DIA[a].setText(diaS);
					PERCENTAGEM[a].setText(percentagem);
					a++;
				}
			}
		}
	}

	public void mostraTrabalhos(Disciplina d)
	{
		TextView DATA[] = new TextView[7];
		TextView DIA[] = new TextView[7];
		TextView PERCENTAGEM[] = new TextView[7];

		TextView verteste1DATA = (TextView) findViewById(R.id.verteste1DATA);
		DATA[0]=verteste1DATA;
		TextView verteste1DIA = (TextView) findViewById(R.id.verteste1DIA);
		DIA[0]=verteste1DIA;
		TextView verteste1PERCENTAGEM = (TextView) findViewById(R.id.verteste1PERCENTAGEM);
		PERCENTAGEM[0] = verteste1PERCENTAGEM;
		TextView verteste2DATA = (TextView) findViewById(R.id.verteste2DATA);
		DATA[1]=verteste2DATA;
		TextView verteste2DIA = (TextView) findViewById(R.id.verteste2DIA);
		DIA[1]=verteste2DIA;
		TextView verteste2PERCENTAGEM = (TextView) findViewById(R.id.verteste2PERCENTAGEM);
		PERCENTAGEM[1] = verteste2PERCENTAGEM;
		TextView verteste3DATA = (TextView) findViewById(R.id.verteste3DATA);
		DATA[2]=verteste3DATA;
		TextView verteste3DIA = (TextView) findViewById(R.id.verteste3DIA);
		DIA[2]=verteste3DIA;
		TextView verteste3PERCENTAGEM = (TextView) findViewById(R.id.verteste3PERCENTAGEM);
		PERCENTAGEM[2] = verteste3PERCENTAGEM;
		TextView verteste4DATA = (TextView) findViewById(R.id.verteste4DATA);
		DATA[3]=verteste4DATA;
		TextView verteste4DIA = (TextView) findViewById(R.id.verteste4DIA);
		DIA[3]=verteste4DIA;
		TextView verteste4PERCENTAGEM = (TextView) findViewById(R.id.verteste4PERCENTAGEM);
		PERCENTAGEM[3] = verteste4PERCENTAGEM;
		TextView verteste5DATA = (TextView) findViewById(R.id.verteste5DATA);
		DATA[4]=verteste5DATA;
		TextView verteste5DIA = (TextView) findViewById(R.id.verteste5DIA);
		DIA[4]=verteste5DIA;
		TextView verteste5PERCENTAGEM = (TextView) findViewById(R.id.verteste5PERCENTAGEM);
		PERCENTAGEM[4] = verteste5PERCENTAGEM;
		TextView verteste6DATA = (TextView) findViewById(R.id.verteste6DATA);
		DATA[5]=verteste6DATA;
		TextView verteste6DIA = (TextView) findViewById(R.id.verteste6DIA);
		DIA[5]=verteste6DIA;
		TextView verteste6PERCENTAGEM = (TextView) findViewById(R.id.verteste6PERCENTAGEM);
		PERCENTAGEM[5] = verteste6PERCENTAGEM;
		TextView verteste7DATA = (TextView) findViewById(R.id.verteste7DATA);
		DATA[6]=verteste7DATA;
		TextView verteste7DIA = (TextView) findViewById(R.id.verteste7DIA);
		DIA[6]=verteste7DIA;
		TextView verteste7PERCENTAGEM = (TextView) findViewById(R.id.verteste7PERCENTAGEM);
		PERCENTAGEM[6] = verteste7PERCENTAGEM;


		for(int i=0; i<d.getTrabalhos().size(); i++)
		{
			String date = "" + d.getTrabalhos().elementAt(i).getDia() + "/" + d.getTrabalhos().elementAt(i).getMese() + "/" +  d.getTrabalhos().elementAt(i).getAnoe();
			Calendar cal = Calendar.getInstance();
			cal.set(d.getTrabalhos().elementAt(i).getAnoe(), d.getTrabalhos().elementAt(i).getMese(), d.getTrabalhos().elementAt(i).getDia());
			int DiaS=cal.get(Calendar.DAY_OF_WEEK);
			String diaS;
			if(DiaS==2)
			{
				diaS="Segunda-feira";
			}
			else if(DiaS==3)
			{
				diaS="Terça-feira";
			}
			else if(DiaS==4)
			{
				diaS="Quarta-feira";
			}
			else if(DiaS==5)
			{
				diaS="Quinta-feira";
			}
			else
			{
				diaS="Sexta-feira";
			}
			String percentagem = "" + d.getTrabalhos().elementAt(i).getPercentagem() + "%";
			DATA[i].setText(date);
			DIA[i].setText(diaS);
			PERCENTAGEM[i].setText(percentagem);
		}
	}

	public void CarregaTudo()
	{
		Vector<Vector<Aula>> aulas= new Vector<Vector<Aula>>();
		Vector<Vector<Teste>> testes= new Vector<Vector<Teste>>();
		Vector<Vector<Trabalho>> trabalhos= new Vector<Vector<Trabalho>>();

		aulas=datasource.carregaTabelaAulas();
		testes=datasource.carregaTabelaTestes();
		trabalhos=datasource.carregaTabelaTrabalhos();

		gestor.horario.setDisciplinas(datasource.carregaTabela());
		for(int i=0; i<gestor.horario.getDisciplinas().size(); i++)
		{
			gestor.horario.getDisciplinas().elementAt(i).setAulas(aulas.elementAt(i));
			gestor.horario.getDisciplinas().elementAt(i).setTestes(testes.elementAt(i));
			gestor.horario.getDisciplinas().elementAt(i).setTrabalhos(trabalhos.elementAt(i));
		}
	}
}