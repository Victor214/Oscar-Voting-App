package com.example.oscarapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Application;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

public class FilmesDetalhesActivity extends AppCompatActivity {
    private int id;
    String imageurl;
    String nome;
    String genero;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filmes_detalhes);

        Intent it = getIntent();
        // Certifica que intent pode ser recebido.
        if (it == null)
            return;

        Bundle params = it.getExtras();
        // Certifica que os parâmetros foram enviados corretamente.
        if (params == null)
            return;

        id = params.getInt("id");
        imageurl = params.getString("imageurl");
        nome = params.getString("nome");
        genero = params.getString("genero");

        ImageView detalhesImg = findViewById(R.id.detalhesFilmeImg);
        TextView detalhesNome = findViewById(R.id.detalhesNomeFilmeText);
        TextView detalhesGenero = findViewById(R.id.detalhesGeneroFilmeText);

        detalhesNome.setText(nome);
        detalhesGenero.setText(genero);

        ProgressDialog progressDialogDetalhes = new ProgressDialog(this);
        progressDialogDetalhes.setMessage("Carregando...");
        progressDialogDetalhes.show();

        Glide.with(this)
                .load(imageurl)
                .into(detalhesImg);

        progressDialogDetalhes.dismiss();
    }

    public void OnVotar(View view) {
        // Salvar voto no contexto da aplicação
        Usuario usuario = (Usuario) getApplicationContext();
        usuario.setVotoFilme(id);
        usuario.setVotoFilmeDisplay(nome);

        // Feedback
        Toast toast = Toast.makeText(getApplicationContext(), "Voto de filme escolhido.", Toast.LENGTH_SHORT);
        toast.show();

        // Retornar ao dashboard
        Intent it = new Intent(this, DashboardActivity.class);
        it.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivityIfNeeded(it, 0);
    }
}