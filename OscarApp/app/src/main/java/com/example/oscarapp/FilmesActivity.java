package com.example.oscarapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import Adapter.AdapterFilmes;
import ServiceBeans.Filme;
import Model.FilmeItem;
import Model.RecyclerItemClickListener;
import Services.OscarListaRetrofit;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FilmesActivity extends AppCompatActivity {
    private RecyclerView recyclerViewFilmes;
    private List<FilmeItem> listFilmes = new ArrayList<>();

    // Função responsável por receber a lista de filmes do webservice, e tratar os dados apropriadamente.
    private void criarFilmes() {
        ProgressDialog progressDialogFilmes = new ProgressDialog(this);
        progressDialogFilmes.setMessage("Carregando filmes...");
        progressDialogFilmes.show();

        Call<List<Filme>> call = new OscarListaRetrofit().getFilmeService().getFullAddress();
        call.enqueue(new Callback<List<Filme>>() {
            @Override
            public void onResponse(Call<List<Filme>> call, Response<List<Filme>> response) {
                if (response.isSuccessful()) {
                    // Itera sobre o resultado, pega os valores da lista de "Filme" (classe de retorno), e coloca dentro de "FilmeItem" (classe de itens da RecyclerView)
                    List<Filme> listaFilmesRetorno = response.body();
                    for (Filme f : listaFilmesRetorno) {
                        FilmeItem obj = new FilmeItem();
                        obj.setId(f.getId());
                        obj.setImg(f.getFoto());
                        obj.setNome(f.getNome());
                        obj.setGenero(f.getGenero());
                        listFilmes.add(obj);
                    }
                    preencherLista();
                    progressDialogFilmes.dismiss();
                }
            }

            @Override
            public void onFailure(Call<List<Filme>> call, Throwable t) {

            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filmes);

        recyclerViewFilmes = findViewById(R.id.recyclerViewFilmes);
        this.criarFilmes();
    }

    // Chamado ao fim de criarFilmes, quando o resultado é retornado
    private void preencherLista() {
        AdapterFilmes adapter = new AdapterFilmes(this, listFilmes);

        // Configurando RecyclerView utilizando um layout linear
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerViewFilmes.setLayoutManager(layoutManager);
        recyclerViewFilmes.setHasFixedSize(true);
        recyclerViewFilmes.addItemDecoration(new DividerItemDecoration(this, LinearLayout.VERTICAL));
        recyclerViewFilmes.setAdapter(adapter);

        recyclerViewFilmes.addOnItemTouchListener(
                new RecyclerItemClickListener(
                        getApplicationContext(),
                        recyclerViewFilmes,
                        new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                FilmeItem obj = listFilmes.get(position);
                                Intent it = new Intent(FilmesActivity.this, FilmesDetalhesActivity.class);
                                Bundle params = new Bundle();
                                params.putInt("id", obj.getId());
                                params.putString("imageurl", obj.getImg());
                                params.putString("nome", obj.getNome());
                                params.putString("genero", obj.getGenero());
                                it.putExtras(params);
                                startActivity(it);
                            }

                            @Override
                            public void onLongItemClick(View view, int position) {

                            }

                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                            }
                        }
                )
        );
    }
}