package com.example.orcamento;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private SQLiteDatabase bancoDados;

    private TextView textFinal;
    private EditText editNome;
    private EditText editPeca;
    private EditText editQuantidade;
    private Spinner spinerOperacao;
    private EditText editPerimetro;
    private EditText editPeso;

    public ListView listCalculo;

    public  List<String> ListaCalc;

    public String resultadoFinal;


    private ArrayAdapter adapter;
    private ArrayAdapter adapter2;

    public MainActivity() {
    }


    public void carregarTecnologia() {

       Tecnologia T0 = new Tecnologia(0,"Selecione a tecnologia",0);
       Tecnologia T1 = new Tecnologia(1,"Plasma",0.08);
       Tecnologia T2 = new Tecnologia(2,"Laser",0.11);
       Tecnologia T3 = new Tecnologia(3,"Puncionadeira",0.5);

       List<Tecnologia> ListaTec = new ArrayList<>();

       ListaTec.add(T0);
       ListaTec.add(T1);
       ListaTec.add(T2);
       ListaTec.add(T3);

       adapter = new  ArrayAdapter(this, android.R.layout.simple_list_item_1, ListaTec);

       spinerOperacao.setAdapter(adapter);

   }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        //textFinal = findViewById(R.id.textFinal);
        editNome= findViewById(R.id.editNome);
        editPeca= findViewById(R.id.editPeca);
        editQuantidade = findViewById(R.id.editQuantidade);
        spinerOperacao = findViewById(R.id.spinerOperacao);
        editPerimetro = findViewById(R.id.editPerimetro);
        editPeso = findViewById(R.id.editPeso);
        listCalculo = findViewById(R.id.listCalculo);


        carregarTecnologia();

        criarBancoDados();


        listarDados();



    }


    public void criarBancoDados() {
        try{
            bancoDados = openOrCreateDatabase("orcamentos", MODE_PRIVATE, null);
            bancoDados.execSQL("CREATE TABLE IF NOT EXISTS orca(" +
                    " id INTEGER PRIMARY KEY AUTOINCREMENT" +
                    " , resul TEXT)");
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void listarDados() {
        try {
            ListaCalc = new ArrayList<>();
            bancoDados = openOrCreateDatabase("orcamentos", MODE_PRIVATE, null);
            Cursor meuCursor = bancoDados.rawQuery("SELECT id, resul FROM orca", null);


            adapter2 = new ArrayAdapter( this, android.R.layout.simple_list_item_1, ListaCalc
            );

            listCalculo.setAdapter(adapter2);

            meuCursor.moveToFirst();
            while(meuCursor!=null){
                ListaCalc.add(meuCursor.getString(1));
                meuCursor.moveToNext();
            }

        }catch (Exception e){
            e.printStackTrace();
        }

    }



    public void gerar(View view){

        Tecnologia escolida = (Tecnologia) spinerOperacao.getSelectedItem();
        if (escolida.getId() == 0){
            Toast.makeText(this,"Preencha a tecnologia!",Toast.LENGTH_LONG).show();

        }else {

            String nome = editNome.getText().toString();
            String peca = editPeca.getText().toString();




            double quantidade = Double.parseDouble(editQuantidade.getText().toString());
            double peso = Double.parseDouble(editPeso.getText().toString());
            double perimetro = Double.parseDouble(editPerimetro.getText().toString());



            double resultadoUni = peso * 6 + (perimetro * escolida.getCusto_mm());
            double resultadoTotal = resultadoUni * quantidade;

             resultadoFinal = ("Cliente:" + nome +  "\n" + "Peça"+
                     quantidade + "  x  " + peca + "\n" +
                     "Valor Unitário:R$ " + resultadoUni
                     + "\n" +
                    "Valor Total:R$ " + resultadoTotal);

            bancoDados = openOrCreateDatabase("orcamentos", MODE_PRIVATE, null);
            String sql = "INSERT INTO orca (resul) VALUES (?)";
            SQLiteStatement stmt = bancoDados.compileStatement(sql);

            stmt.bindString(1,resultadoFinal);
            stmt.executeInsert();
            //bancoDados.close();




            //ListaCalc.add(resultadoFinal );



            listarDados();
            //adapter2.notifyDataSetChanged();





        }


    }



    }
