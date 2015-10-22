package f.ape;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import java.util.HashMap;
import java.util.Vector;


public class DBAdapter {

	private SQLiteDatabase database;
	private DbHelper dbHelper;
	//private String[] allColumns = { DbHelper.ID, DbHelper.NOME, DbHelper.SIGLA,};

	public DBAdapter(Context context) {          
		dbHelper = new DbHelper(context);
	}


	public void createDisciplina(String nome, String sigla, Vector<Aula> aulas, Vector<Teste> testes, Vector<Trabalho> trabalhos)
	{
		database=dbHelper.getWritableDatabase();
		ContentValues values = new ContentValues(); 
		values.put(DbHelper.NOME, nome); 
		values.put(DbHelper.SIGLA,sigla);
		for(int i=0;i<aulas.size();i++){
			String hora="Aula"+(i+1)+"Hora";
			values.put(hora,aulas.get(i).getHorai()+(aulas.get(i).getMini()*0.01));
			String dura="Aula"+(i+1)+"Duracao";
			values.put(dura,aulas.get(i).getDuracao());
			String sala="Aula"+(i+1)+"Sala";
			values.put(sala,aulas.get(i).getSala());
			String dia="Aula"+(i+1)+"DiaSemana";
			values.put(dia,aulas.get(i).getDia());
		}
		for(int i=0;i<testes.size();i++){
			String dia="Teste"+(i+1)+"Dia";
			values.put(dia,testes.get(i).getDia());
			String mes="Teste"+(i+1)+"Mes";
			values.put(mes,testes.get(i).getMes());
			String ano="Teste"+(i+1)+"Ano";
			values.put(ano,testes.get(i).getAno());
			String dura="Teste"+(i+1)+"Duracao";
			values.put(dura,testes.get(i).getDuracao());
			String hora="Teste"+(i+1)+"Hora";
			values.put(hora,testes.get(i).getHorai()+(testes.get(i).getMini()*0.01));
			String nota="Teste"+(i+1)+"Nota";
			values.put(nota,testes.get(i).getNota());
			String notam="Teste"+(i+1)+"NotaMinima";
			values.put(notam,testes.get(i).getNotamin());
			String percent="Teste"+(i+1)+"Percentagem";
			values.put(percent,testes.get(i).getPercentagem());
			String sala="Teste"+(i+1)+"Sala";
			values.put(sala,testes.get(i).getSalas());
		}
		for(int i=0;i<trabalhos.size();i++){
			String dia="Trabalho"+(i+1)+"Dia";
			values.put(dia,trabalhos.get(i).getDia());
			String mes="Trabalho"+(i+1)+"Mes";
			values.put(mes,trabalhos.get(i).getMese());
			String ano="Trabalho"+(i+1)+"Ano";
			values.put(ano,trabalhos.get(i).getAnoe());
			String hora="Trabalho"+(i+1)+"Hora";
			values.put(hora,trabalhos.get(i).getHorae()+(trabalhos.get(i).getMine()*0.01));
			String nota="Trabalho"+(i+1)+"Nota";
			values.put(nota,trabalhos.get(i).getNota());
			String notam="Trabalho"+(i+1)+"NotaMinima";
			values.put(notam,trabalhos.get(i).getNotamin());
			String entrega="Trabalho"+(i+1)+"EntregaDigital";
			values.put(entrega,trabalhos.get(i).isEntrega());
			String percent="Trabalho"+(i+1)+"Percentagem";
			values.put(percent,trabalhos.get(i).getPercentagem());           
		} 
		long insertId = database.insert(DbHelper.TABLE_NAME, null, values); 
		// To show how to query 
		Cursor cursor = database.query(DbHelper.TABLE_NAME, null, DbHelper.ID + " = " + insertId, null,null, null, null); 
		cursor.moveToFirst(); 
		//return cursorToDisciplina(cursor); 
	}

	private Disciplina cursorToDisciplina(Cursor cursor) {              
		Disciplina disciplina =
				new Disciplina(cursor.getString(1),cursor.getString(2)); 
		return disciplina; 
	}

	public Disciplina getDisciplina (String sigla){ 
		Cursor cursor = database.query(DbHelper.TABLE_NAME, null, DbHelper.SIGLA + " = " + sigla, null,null, null, null); 
		cursor.moveToFirst(); 
		return cursorToDisciplina(cursor); 
	}

	public void EliminaDisciplina (String sigla){ 
		database.delete(DbHelper.TABLE_NAME, DbHelper.SIGLA + " = " + sigla, null); 
	}

	public ArrayList<HashMap<String, Object>> selectAllFromDay(int HDay) {
		database = dbHelper.getWritableDatabase();
		String DSemana="";
		if(HDay==1) DSemana="Terca";
		else if(HDay==2) DSemana="Quarta";
		else if(HDay==3) DSemana="Quinta";
		else if(HDay==4) DSemana="Sexta";
		else DSemana="Segunda";

		ArrayList<HashMap<String, Object>> results =
				new ArrayList<HashMap<String, Object>>();
		Cursor c = database.rawQuery("SELECT * from disciplinas where Aula1DiaSemana='"+DSemana+"' order by Aula1Hora",null);                        
		if (c.getCount() > 0) {
			c.moveToFirst();
			do {
				HashMap<String,Object> resultsMap =
						new HashMap<String, Object>();
				resultsMap.put("Nome" , c.getString(c.getColumnIndex("Nome")));
				resultsMap.put("Sigla" , c.getString(c.getColumnIndex("Sigla")));
				for(int i=0;i<5;i++){
					String hora="Aula"+(i+1)+"Hora";
					resultsMap.put(hora,c.getString(c.getColumnIndex("Aula"+(i+1)+"Hora")));
					String dura="Aula"+(i+1)+"Duracao";
					resultsMap.put(dura,c.getString(c.getColumnIndex("Aula"+(i+1)+"Duracao")));
					String sala="Aula"+(i+1)+"Sala";
					resultsMap.put(sala,c.getString(c.getColumnIndex("Aula"+(i+1)+"Sala")));
				}
				//	                                        resultsMap.put("STeacher" , c.getString(c.getColumnIndex("STeacher")));
				//	                                        resultsMap.put("HClass" , c.getString(c.getColumnIndex("HClass")));
				//	                                        resultsMap.put("HStart" , c.getString(c.getColumnIndex("HStart")));
				//	                                        resultsMap.put("HEnd" , c.getString(c.getColumnIndex("HEnd")));
				//	                                        resultsMap.put("SColor" , c.getString(c.getColumnIndex("SColor")));
				results.add(resultsMap);
			} while(c.moveToNext());
		}
		c.close();
		return results;
	}

	public void actualizaLinha(String nome, String sigla, Vector<Aula> aulas, Vector<Teste> testes, Vector<Trabalho> trabalhos){

		database = dbHelper.getWritableDatabase();
		ContentValues values = new ContentValues();
		for(int i=0;i<aulas.size();i++)
		{
			String hora="Aula"+(i+1)+"Hora";
			values.put(hora,aulas.get(i).getHorai()+(aulas.get(i).getMini()*0.01));
			String dura="Aula"+(i+1)+"Duracao";
			values.put(dura,aulas.get(i).getDuracao());
			String sala="Aula"+(i+1)+"Sala";
			values.put(sala,aulas.get(i).getSala());
			String dia="Aula"+(i+1)+"DiaSemana";
			values.put(dia,aulas.get(i).getDia());
		}
		for(int i=0;i<testes.size();i++){
			String dia="Teste"+(i+1)+"Dia";
			values.put(dia,testes.get(i).getDia());
			String mes="Teste"+(i+1)+"Mes";
			values.put(mes,testes.get(i).getMes());
			String ano="Teste"+(i+1)+"Ano";
			values.put(ano,testes.get(i).getAno());
			String dura="Teste"+(i+1)+"Duracao";
			values.put(dura,testes.get(i).getDuracao());
			String hora="Teste"+(i+1)+"Hora";
			values.put(hora,testes.get(i).getHorai()+(testes.get(i).getMini()*0.01));
			String nota="Teste"+(i+1)+"Nota";
			values.put(nota,testes.get(i).getNota());
			String notam="Teste"+(i+1)+"NotaMinima";
			values.put(notam,testes.get(i).getNotamin());
			String percent="Teste"+(i+1)+"Percentagem";
			values.put(percent,testes.get(i).getPercentagem());
			String sala="Teste"+(i+1)+"Sala";
			values.put(sala,testes.get(i).getSalas());
		}
		for(int i=0;i<trabalhos.size();i++){
			String dia="Trabalho"+(i+1)+"Dia";
			values.put(dia,trabalhos.get(i).getDia());
			String mes="Trabalho"+(i+1)+"Mes";
			values.put(mes,trabalhos.get(i).getMese());
			String ano="Trabalho"+(i+1)+"Ano";
			values.put(ano,trabalhos.get(i).getAnoe());
			String hora="Trabalho"+(i+1)+"Hora";
			values.put(hora,trabalhos.get(i).getHorae()+(trabalhos.get(i).getMine()*0.01));
			String nota="Trabalho"+(i+1)+"Nota";
			values.put(nota,trabalhos.get(i).getNota());
			String notam="Trabalho"+(i+1)+"NotaMinima";
			values.put(notam,trabalhos.get(i).getNotamin());
			String entrega="Trabalho"+(i+1)+"EntregaDigital";
			values.put(entrega,trabalhos.get(i).isEntrega());
			String percent="Trabalho"+(i+1)+"Percentagem";
			values.put(percent,trabalhos.get(i).getPercentagem());           
		}

		database.updateWithOnConflict(DbHelper.TABLE_NAME, values, "Sigla =" + "'"+sigla+"'" /* + "AND" + nome + "=" + DbHelper.NOME*/, null, SQLiteDatabase.CONFLICT_REPLACE);
	}
	public void open() throws SQLException {
		database = dbHelper.getWritableDatabase();
	}

	public void close() {
		dbHelper.close();
	}

	public Vector<Disciplina> carregaTabela(){
		database = dbHelper.getWritableDatabase();
		int sometotal = (int) DatabaseUtils.longForQuery(database, "select count(*) from disciplinas", null);
		Vector<Disciplina> disciplinas = new Vector<Disciplina>();
		for(int i=1;i<=sometotal;i++){
			Cursor c1 = database.query(DbHelper.TABLE_NAME, null, DbHelper.ID + "="+i, null,null, null, null);
			String nome= c1.getString(c1.getColumnIndex("nome"));
			String sigla= c1.getString(c1.getColumnIndex("sigla"));
			Disciplina disciplina = new Disciplina(nome,sigla);
			disciplinas.add(disciplina);
		}
		return disciplinas;
	}

	public Vector<Vector<Teste>> carregaTabelaTestes(){
		database = dbHelper.getWritableDatabase();
		int sometotal = (int) DatabaseUtils.longForQuery(database, "select count(*) from disciplinas", null);
		Vector<Vector<Teste>> testes_disciplinas= new Vector<Vector<Teste>>();
		Vector<Teste> testes = new Vector<Teste>();
		Teste teste;
		for(int i=1;i<=sometotal;i++){
			Cursor c1 = database.query(DbHelper.TABLE_NAME, null, DbHelper.ID + "="+i, null,null, null, null);
			for(int j=0;j<8;j++){
				teste= new Teste();
				String dia="Teste"+(j+1)+"Dia";
				int di= c1.getInt(c1.getColumnIndex(dia));
				teste.setDia(di);
				String mes="Teste"+(j+1)+"Mes";
				int m= c1.getInt(c1.getColumnIndex(mes));
				teste.setMes(m);
				String ano="Teste"+(j+1)+"Ano";
				int a= c1.getInt(c1.getColumnIndex(ano));
				teste.setAno(a);
				String dura="Teste"+(j+1)+"Duracao";
				int d= c1.getInt(c1.getColumnIndex(dura));
				teste.setDuracao(d);
				String hora="Teste"+(j+1)+"Hora";
				float h= c1.getFloat(c1.getColumnIndex(hora));
				int hi= (int) Math.floor(h);
				int mi= (int) h-hi;
				teste.setHorai(hi);
				teste.setMini(mi);
				String nota="Teste"+(j+1)+"Nota";
				float n= c1.getFloat(c1.getColumnIndex(nota));
				teste.setNota(n);
				String notam="Teste"+(j+1)+"NotaMinima";
				float nm= c1.getFloat(c1.getColumnIndex(notam));
				teste.setNotamin(nm);
				String percent="Teste"+(j+1)+"Percentagem";
				float p= c1.getFloat(c1.getColumnIndex(percent));
				teste.setPercentagem(p);
				String sala="Teste"+(j+1)+"Sala";
				String s= c1.getString(c1.getColumnIndex(sala));
				teste.setSalas(s);
				testes.add(teste);
			}
			testes_disciplinas.add(testes);
		}
		return testes_disciplinas;
	}

	public Vector<Vector<Aula>> carregaTabelaAulas(){
		database = dbHelper.getWritableDatabase();
		int sometotal = (int) DatabaseUtils.longForQuery(database, "select count(*) from disciplinas", null);
		Vector<Vector<Aula>> aulas_disciplinas= new Vector<Vector<Aula>>();
		Vector<Aula> aulas = new Vector<Aula>();
		Aula aula;
		for(int i=1;i<=sometotal;i++){
			Cursor c1 = database.rawQuery("SELECT * FROM disciplinas where _id='"+ i +"'" ,null);
			//Cursor c1 = database.query(DbHelper.TABLE_NAME, null, DbHelper.ID + "="+i, null,null, null, null);
			for(int j=0;j<5;j++){
				aula= new Aula();
				String hora="Aula"+(j+1)+"Hora";
				float h= c1.getFloat(1);
				int hi= (int) Math.floor(h);
				int mi= (int) h-hi;
				aula.setHorai(hi);
				aula.setMini(mi);
				String dura="Aula"+(j+1)+"Duracao";
				int d= c1.getInt(c1.getColumnIndex(dura));
				aula.setDuracao(d);
				String sala="Aula"+(j+1)+"Sala";
				String s= c1.getString(c1.getColumnIndex(sala));
				aula.setSala(s);
				String dia="Aula"+(j+1)+"DiaSemana";
				String ds= c1.getString(c1.getColumnIndex(dia));
				aula.setDia(ds);
				aulas.add(aula);
			}
			aulas_disciplinas.add(aulas);
		}
		return aulas_disciplinas;
	}

	public Vector<Vector<Trabalho>> carregaTabelaTrabalhos(){
		database = dbHelper.getWritableDatabase();
		int sometotal = (int) DatabaseUtils.longForQuery(database, "select count(*) from disciplinas", null);
		Vector<Vector<Trabalho>> trabalhos_disciplinas= new Vector<Vector<Trabalho>>();
		Vector<Trabalho> trabalhos = new Vector<Trabalho>();
		Trabalho trabalho;
		for(int i=1;i<=sometotal;i++){
			Cursor c1 = database.query(DbHelper.TABLE_NAME, null, DbHelper.ID + "="+i, null,null, null, null);
			for(int j=0;j<4;j++){
				trabalho= new Trabalho();
				String dia="Trabalho"+(i+1)+"Dia";
				int d= c1.getInt(c1.getColumnIndex(dia));
				String mes="Trabalho"+(i+1)+"Mes";
				trabalho.setDia(d);
				int m= c1.getInt(c1.getColumnIndex(mes));
				trabalho.setMese(m);
				String ano="Trabalho"+(i+1)+"Ano";
				int a= c1.getInt(c1.getColumnIndex(ano));
				trabalho.setAnoe(a);
				String hora="Trabalho"+(i+1)+"Hora";
				float h= c1.getFloat(c1.getColumnIndex(hora));
				int hi= (int) Math.floor(h);
				int mi= (int) h-hi;
				trabalho.setHorae(hi);
				trabalho.setMine(mi);
				String nota="Trabalho"+(i+1)+"Nota";
				float n= c1.getFloat(c1.getColumnIndex(nota));
				trabalho.setNota(n);
				String notam="Trabalho"+(i+1)+"NotaMinima";
				float nm= c1.getFloat(c1.getColumnIndex(notam));
				trabalho.setNotamin(nm);
				String entrega="Trabalho"+(i+1)+"EntregaDigital";
				int ed= c1.getInt(c1.getColumnIndex(entrega));
				if(ed==0) trabalho.setEntrega(false); else trabalho.setEntrega(true);
				String percent="Trabalho"+(i+1)+"Percentagem";
				float p= c1.getFloat(c1.getColumnIndex(percent));
				trabalho.setPercentagem(p);
				trabalhos.add(trabalho);
			}
			trabalhos_disciplinas.add(trabalhos);
		}
		return trabalhos_disciplinas;
	}

	public void ApagaTudo()
	{
		//database.deleteDatabase(DbHelper.TABLE_NAME);
	}
}