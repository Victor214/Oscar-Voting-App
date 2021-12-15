package com.example.oscarapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import Adapter.AdapterDiretores;
import Model.DiretorItem;
import Model.FilmeItem;
import Model.RecyclerItemClickListener;
import ServiceBeans.Diretor;
import Services.OscarListaRetrofit;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DiretoresActivity extends AppCompatActivity {
    private RecyclerView recyclerViewDiretores;
    private List<DiretorItem> listDiretores = new ArrayList<>();
    private Integer diretorSelecionado;
    private String diretorSelecionadoDisplay;

    // Função responsável por receber a lista de diretores do webservice, e tratar os dados apropriadamente.
    private void criarDiretores() {
        ProgressDialog progressDialogDiretores = new ProgressDialog(this);
        progressDialogDiretores.setMessage("Carregando diretores...");
        progressDialogDiretores.show();

        Call<List<Diretor>> call = new OscarListaRetrofit().getDiretorService().getFullAddress();
        call.enqueue(new Callback<List<Diretor>>() {
            @Override
            public void onResponse(Call<List<Diretor>> call, Response<List<Diretor>> response) {
                System.out.println("Response (1)");
                System.out.println("Response Message : " + response.message());
                if (response.isSuccessful()) {
                    System.out.println("Response (2)");
                    // Itera sobre o resultado, pega os valores da lista de "Diretor" (classe de retorno), e coloca dentro de "DiretorItem" (classe de itens da RecyclerView)
                    List<Diretor> listaDiretoresRetorno = response.body();
                    for (Diretor d : listaDiretoresRetorno) {
                        DiretorItem obj = new DiretorItem();
                        obj.setId(d.getId());
                        obj.setNome(d.getNome());
                        listDiretores.add(obj);
                    }
                    preencherLista();
                    progressDialogDiretores.dismiss();
                }
            }

            @Override
            public void onFailure(Call<List<Diretor>> call, Throwable t) {

            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diretores);

        diretorSelecionado = -1; // Nenhum selecionado por padrão
        diretorSelecionadoDisplay = "";

        recyclerViewDiretores = findViewById(R.id.recyclerViewDiretor);
        this.criarDiretores();
    }

    private void preencherLista() {
        AdapterDiretores adapter = new AdapterDiretores(this, listDiretores);

        // Configurando RecyclerView utilizando um layout linear
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerViewDiretores.setLayoutManager(layoutManager);
        recyclerViewDiretores.setHasFixedSize(true);
        recyclerViewDiretores.addItemDecoration(new DividerItemDecoration(this, LinearLayout.VERTICAL));
        recyclerViewDiretores.setAdapter(adapter);

        recyclerViewDiretores.addOnItemTouchListener(
                new RecyclerItemClickListener(
                        getApplicationContext(),
                        recyclerViewDiretores,
                        new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                DiretorItem obj = listDiretores.get(position);

                                // Setar todos os radio buttons como não selecionados
                                for (RadioButton r : adapter.getRadioButtons()) {
                                    r.setChecked(false);
                                }

                                // Setar o radio button clicado como selecionado
                                ViewGroup viewGroup = (ViewGroup) view;
                                RadioButton r = (RadioButton) viewGroup.getChildAt(0);
                                r.setChecked(true);

                                // Registrar que o radio button foi alterado na variável de diretorSelecionado
                                diretorSelecionado = obj.getId();
                                diretorSelecionadoDisplay = obj.getNome();
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

    public void onVotarDiretor(View view) {
        Usuario usuario = (Usuario) getApplicationContext();
        usuario.setVotoDiretor(diretorSelecionado);
        usuario.setVotoDiretorDisplay(diretorSelecionadoDisplay);

        // Feedback
        Toast toast = Toast.makeText(getApplicationContext(), "Voto de diretor escolhido.", Toast.LENGTH_SHORT);
        toast.show();

        // Retornar ao dashboard
        Intent it = new Intent(this, DashboardActivity.class);
        it.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivityIfNeeded(it, 0);
    }
}