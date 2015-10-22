package f.ape;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DbHelper extends SQLiteOpenHelper {
          private static final String DATABASE_NAME = "disciplinas.db";
          public static final String TABLE_NAME = "disciplinas";
          private static final int DATABASE_VERSION = 1;
          public static final String ID = "_id";
          public static final String NOME = "nome";
          public static final String SIGLA = "sigla";
          //-------Aulas--------------------------------
          public static final String A1H = "Aula1Hora";
          public static final String A1D = "Aula1Duracao";
          public static final String A1S = "Aula1Sala";
          public static final String A1DS = "Aula1DiaSemana";
          public static final String A2H = "Aula2Hora";
          public static final String A2D = "Aula2Duracao";
          public static final String A2S = "Aula2Sala";
          public static final String A2DS = "Aula2DiaSemana";
          public static final String A3H = "Aula3Hora";
          public static final String A3D = "Aula3Duracao";
          public static final String A3S = "Aula3Sala";
          public static final String A3DS = "Aula3DiaSemana";
          public static final String A4H = "Aula4Hora";
          public static final String A4D = "Aula4Duracao";
          public static final String A4S = "Aula4Sala";
          public static final String A4DS = "Aula4DiaSemana";
          public static final String A5H = "Aula5Hora";
          public static final String A5D = "Aula5Duracao";
          public static final String A5S = "Aula5Sala";
          public static final String A5DS = "Aula5DiaSemana";
          //-------Testes--------------------------------
          public static final String T1Di = "Teste1Dia";
          public static final String T1M = "Teste1Mes";
          public static final String T1A = "Teste1Ano";
          public static final String T1Du = "Teste1Duracao";
          public static final String T1H = "Teste1Hora";
          public static final String T1N = "Teste1Nota";
          public static final String T1NM = "Teste1NotaMinima";
          public static final String T1P = "Teste1Percentagem";
          public static final String T1S = "Teste1Sala";
          public static final String T2Di = "Teste2Dia";
          public static final String T2M = "Teste2Mes";
          public static final String T2A = "Teste2Ano";
          public static final String T2Du = "Teste2Duracao";
          public static final String T2H = "Teste2Hora";
          public static final String T2N = "Teste2Nota";
          public static final String T2NM = "Teste2NotaMinima";
          public static final String T2P = "Teste2Percentagem";
          public static final String T2S = "Teste2Sala";
          public static final String T3Di = "Teste3Dia";
          public static final String T3M = "Teste3Mes";
          public static final String T3A = "Teste3Ano";
          public static final String T3Du = "Teste3Duracao";
          public static final String T3H = "Teste3Hora";
          public static final String T3N = "Teste3Nota";
          public static final String T3NM = "Teste3NotaMinima";
          public static final String T3P = "Teste3Percentagem";
          public static final String T3S = "Teste3Sala";
          public static final String T4Di = "Teste4Dia";
          public static final String T4M = "Teste4Mes";
          public static final String T4A = "Teste4Ano";
          public static final String T4Du = "Teste4Duracao";
          public static final String T4H = "Teste4Hora";
          public static final String T4N = "Teste4Nota";
          public static final String T4NM = "Teste4NotaMinima";
          public static final String T4P = "Teste4Percentagem";
          public static final String T4S = "Teste4Sala";
          public static final String T5Di = "Teste5Dia";
          public static final String T5M = "Teste5Mes";
          public static final String T5A = "Teste5Ano";
          public static final String T5Du = "Teste5Duracao";
          public static final String T5H = "Teste5Hora";
          public static final String T5N = "Teste5Nota";
          public static final String T5NM = "Teste5NotaMinima";
          public static final String T5P = "Teste5Percentagem";
          public static final String T5S = "Teste5Sala";
          public static final String T6Di = "Teste6Dia";
          public static final String T6M = "Teste6Mes";
          public static final String T6A = "Teste6Ano";
          public static final String T6Du = "Teste6Duracao";
          public static final String T6H = "Teste6Hora";
          public static final String T6N = "Teste6Nota";
          public static final String T6NM = "Teste6NotaMinima";
          public static final String T6P = "Teste6Percentagem";
          public static final String T6S = "Teste6Sala";
          public static final String T7Di = "Teste7Dia";
          public static final String T7M = "Teste7Mes";
          public static final String T7A = "Teste7Ano";
          public static final String T7Du = "Teste7Duracao";
          public static final String T7H = "Teste7Hora";
          public static final String T7N = "Teste7Nota";
          public static final String T7NM = "Teste7NotaMinima";
          public static final String T7P = "Teste7Percentagem";
          public static final String T7S = "Teste7Sala";
          public static final String T8Di = "Teste8Dia";
          public static final String T8M = "Teste8Mes";
          public static final String T8A = "Teste8Ano";
          public static final String T8Du = "Teste8Duracao";
          public static final String T8H = "Teste8Hora";
          public static final String T8N = "Teste8Nota";
          public static final String T8NM = "Teste8NotaMinima";
          public static final String T8P = "Teste8Percentagem";
          public static final String T8S = "Teste8Sala";
          //-------Trabalhos-----------------------------------
          public static final String P1Di = "Trabalho1Dia";
          public static final String P1M = "Trabalho1Mes";
          public static final String P1A= "Trabalho1Ano";
          public static final String P1H = "Trabalho1Hora";
          public static final String P1N = "Trabalho1Nota";
          public static final String P1NM = "Trabalho1NotaMinima";
          public static final String P1P = "Trabalho1Percentagem";
          public static final String P1ED = "Trabalho1EntregaDigital";
          public static final String P2Di = "Trabalho2Dia";
          public static final String P2M = "Trabalho2Mes";
          public static final String P2A= "Trabalho2Ano";
          public static final String P2H = "Trabalho2Hora";
          public static final String P2N = "Trabalho2Nota";
          public static final String P2NM = "Trabalho2NotaMinima";
          public static final String P2ED = "Trabalho2EntregaDigital";
          public static final String P2P = "Trabalho2Percentagem";
          public static final String P3Di = "Trabalho3Dia";
          public static final String P3M = "Trabalho3Mes";
          public static final String P3A= "Trabalho3Ano";
          public static final String P3H = "Trabalho3Hora";
          public static final String P3N = "Trabalho3Nota";
          public static final String P3NM = "Trabalho3NotaMinima";
          public static final String P3ED = "Trabalho3EntregaDigital";
          public static final String P3P = "Trabalho3Percentagem";
          public static final String P4Di = "Trabalho4Dia";
          public static final String P4M = "Trabalho4Mes";
          public static final String P4A= "Trabalho4Ano";
          public static final String P4H = "Trabalho4Hora";
          public static final String P4N = "Trabalho4Nota";
          public static final String P4NM = "Trabalho4NotaMinima";
          public static final String P4ED = "Trabalho4EntregaDigital";
          public static final String P4P = "Trabalho4Percentagem";
         //-------Exame-----------------------------------------------
          public static final String E1Di = "Exame1Dia";
          public static final String E1M = "Exame1Mes";
          public static final String E1A = "Exame1Ano";
          public static final String E1Du = "Exame1Duracao";
          public static final String E1H = "Exame1Hora";
          public static final String E1N = "Exame1Nota";
          public static final String E1NM = "Exame1NotaMinima";
          public static final String E1P = "Exame1Percentagem";
          public static final String E1S = "Exame1Sala";
          private static final String DATABASE_CREATE = "create table "
+ TABLE_NAME + "( " + ID
+ " integer primary key autoincrement, " + NOME + " text, " + SIGLA + " text, "
//AULAS
+ A1H + " real, " + A1D + " number, "+ A1S + " text, "+ A1DS + " text, "
+ A2H + " real, " + A2D + " number, "+ A2S + " text, "+ A2DS + " text, "
+ A3H + " real, " + A3D + " number, "+ A3S + " text, "+ A3DS + " text, "
+ A4H + " real, " + A4D + " number, "+ A4S + " text, "+ A4DS + " text, "
+ A5H + " real, " + A5D + " number, "+ A5S + " text, "+ A5DS + " text, "
//TESTES
+ T1Di + " number, "+ T1M + " number, "+ T1A + " number, "+ T1Du + " number, "+ T1H + " real, "+ T1N + " real, "+ T1NM + " real, "+ T1P + " real, "+ T1S + " text, "
+ T2Di + " number, "+ T2M + " number, "+ T2A + " number, "+ T2Du + " number, "+ T2H + " real, "+ T2N + " real, "+ T2NM + " real, "+ T2P + " real, "+ T2S + " text, "
+ T3Di + " number, "+ T3M + " number, "+ T3A + " number, "+ T3Du + " number, "+ T3H + " real, "+ T3N + " real, "+ T3NM + " real, "+ T3P + " real, "+ T3S + " text, "
+ T4Di + " number, "+ T4M + " number, "+ T4A + " number, "+ T4Du + " number, "+ T4H + " real, "+ T4N + " real, "+ T4NM + " real, "+ T4P + " real, "+ T4S + " text, "
+ T5Di + " number, "+ T5M + " number, "+ T5A + " number, "+ T5Du + " number, "+ T5H + " real, "+ T5N + " real, "+ T5NM + " real, "+ T5P + " real, "+ T5S + " text, "
+ T6Di + " number, "+ T6M + " number, "+ T6A + " number, "+ T6Du + " number, "+ T6H + " real, "+ T6N + " real, "+ T6NM + " real, "+ T6P + " real, "+ T6S + " text, "
+ T7Di + " number, "+ T7M + " number, "+ T7A + " number, "+ T7Du + " number, "+ T7H + " real, "+ T7N + " real, "+ T7NM + " real, "+ T7P + " real, "+ T7S + " text, "
+ T8Di + " number, "+ T8M + " number, "+ T8A + " number, "+ T8Du + " number, "+ T8H + " real, "+ T8N + " real, "+ T8NM + " real, "+ T8P + " real, "+ T8S + " text, "
//TRABALHOS
+ P1Di + " number, "+ P1M + " number, "+ P1A + " number, "+ P1H + " real, "+ P1N + " real, "+ P1NM + " real, "+ P1ED + " boolean, "+ P1P + " real, "              
+ P2Di + " number, "+ P2M + " number, "+ P2A + " number, "+ P2H + " real, "+ P2N + " real, "+ P2NM + " real, "+ P2ED + " boolean, "+ P2P + " real, "
+ P3Di + " number, "+ P3M + " number, "+ P3A + " number, "+ P3H + " real, "+ P3N + " real, "+ P3NM + " real, "+ P3ED + " boolean, "+ P3P + " real, "
+ P4Di + " number, "+ P4M + " number, "+ P4A + " number, "+ P4H + " real, "+ P4N + " real, "+ P4NM + " real, "+ P4ED + " boolean, "+ P4P + " real, "
//EXAME
+ E1Di + " number, "+ E1M + " number, "+ E1A + " number, "+ E1Du + " number, "+ E1H + " real, "+ E1N + " real, "+ E1NM + " real, "+ E1P + " real, "+ E1S + " text "
+ ");";
         public DbHelper(Context context) {
                  super(context, DATABASE_NAME, null, DATABASE_VERSION);
         }
         @Override
         public void onCreate(SQLiteDatabase db) {
                 db.execSQL(DATABASE_CREATE);
         }
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
                 Log.w(DbHelper.class.getName(),
                 "Upgrading database from version " + oldVersion + " to "
                 + newVersion + ", which will destroy all old data");
                db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
                onCreate(db);
        }
}