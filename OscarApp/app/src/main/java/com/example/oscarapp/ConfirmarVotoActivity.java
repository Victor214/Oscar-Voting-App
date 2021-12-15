package com.example.oscarapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import ServiceBeans.Token;
import ServiceBeans.Voto;
import Services.OscarRetrofit;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ConfirmarVotoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmar_voto);

        Usuario usuario = (Usuario) getApplicationContext();
        String votoFilmeDisplay = usuario.getVotoFilmeDisplay();
        String votoDiretorDisplay = usuario.getVotoDiretorDisplay();

        // Atualizar displays de voto
        TextView confirmarFilmeText = findViewById(R.id.confirmarFilmeText);
        TextView confirmarDiretorText = findViewById(R.id.confirmarDiretorText);
        if (votoFilmeDisplay != null)
            confirmarFilmeText.setText("Filme Escolhido : " + votoFilmeDisplay);
        else
            confirmarFilmeText.setText("Filme Escolhido : N/A");

        if (votoDiretorDisplay != null)
            confirmarDiretorText.setText("Diretor Escolhido : " + votoDiretorDisplay);
        else
            confirmarDiretorText.setText("Diretor Escolhido : N/A");

    }

    public void onConfirmarVoto(View view) {
        Usuario usuario = (Usuario) getApplicationContext();
        String loginString = usuario.getLogin();
        Integer votoFilme = usuario.getVotoFilme();
        Integer votoDiretor = usuario.getVotoDiretor();

        // Verifica se ambas as opções de voto foram escolhidas
        if (votoFilme == null || votoDiretor == null) {
            Toast toast = Toast.makeText(getApplicationContext(), "Você deve escolher um filme e um diretor para confirmar seu voto.", Toast.LENGTH_SHORT);
            toast.show();
            return;
        }

        // Verifica se o token foi preenchido
        EditText tokenEdit = findViewById(R.id.confirmarTokenEdit);
        String tokenString = tokenEdit.getText().toString();
        if (tokenString.equals("")) {
            Toast toast = Toast.makeText(getApplicationContext(), "Você deve preencher o token. O valor deste se encontra na tela principal.", Toast.LENGTH_SHORT);
            toast.show();
            return;
        }

        // Tenta enviar / verifica o token com o servidor
        Integer token = Integer.valueOf(tokenString);

        ProgressDialog progressDialogVoto = new ProgressDialog(this);
        progressDialogVoto.setMessage("Confirmando voto...");
        progressDialogVoto.show();

        Call<Voto> votoCall = new OscarRetrofit().getVotoService().getFullAddress(loginString, token, votoFilme, votoDiretor);
        votoCall.enqueue(new Callback<Voto>() {
            @Override
            public void onResponse(Call<Voto> call, Response<Voto> response) {
                if (response.isSuccessful()) {
                    progressDialogVoto.dismiss();
                    Voto voto = response.body();

                    // Token Inválido
                    if (voto.getResultado() == -2) {
                        Toast toast = Toast.makeText(getApplicationContext(), "O token especificado é inválido.", Toast.LENGTH_SHORT);
                        toast.show();
                        return;
                    }

                    // Voto já foi computado
                    if (voto.getResultado() == -3) {
                        Toast toast = Toast.makeText(getApplicationContext(), "O voto já foi computado nessa conta", Toast.LENGTH_SHORT);
                        toast.show();
                        return;
                    }

                    // Erro Genérico (-1)
                    if (voto.getResultado() <= 0) {
                        Toast toast = Toast.makeText(getApplicationContext(), "Houve um erro ao se conectar com o servidor...", Toast.LENGTH_SHORT);
                        toast.show();
                        return;
                    }

                    Toast toast = Toast.makeText(getApplicationContext(), "Voto realizado com sucesso!", Toast.LENGTH_SHORT);
                    toast.show();
                    finish();
                    return;
                }
            }

            @Override
            public void onFailure(Call<Voto> call, Throwable t) {

            }
        });
    }
}