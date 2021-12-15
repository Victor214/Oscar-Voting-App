package com.example.oscarapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class DashboardActivity extends AppCompatActivity {

    private void storeLoginInfo(Intent it) {
        // Certifica que intent pode ser recebido.
        if (it == null)
            return;

        Bundle params = it.getExtras();
        // Certifica que os parâmetros foram enviados corretamente.
        if (params == null)
            return;

        Usuario usuario = (Usuario) getApplicationContext();
        // Se o login ou token já foram definidos no contexto da aplicação, então só retornar
        if (usuario.getLogin() != null || usuario.getToken() != null)
            return;

        String login = params.getString("login");
        Integer token = params.getInt("token");
        usuario.setLogin(login);
        usuario.setToken(token);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        Intent it = getIntent();
        storeLoginInfo(it);

        TextView tokenText = findViewById(R.id.tokenText);
        Usuario usuario = (Usuario) getApplicationContext();
        tokenText.setText("Token : " + usuario.getToken());
    }

    public void onOpenVotarFilme(View view) {
        startActivity(new Intent(this, FilmesActivity.class));
    }

    public void onOpenVotarDiretor(View view) {
        startActivity(new Intent(this, DiretoresActivity.class));
    }

    public void onOpenConfirmarVoto(View view) {
        startActivity(new Intent(this, ConfirmarVotoActivity.class));
    }
}