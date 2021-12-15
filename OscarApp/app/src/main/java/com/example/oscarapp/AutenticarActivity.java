package com.example.oscarapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import ServiceBeans.Login;
import ServiceBeans.Token;
import Services.OscarRetrofit;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AutenticarActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onTryLogin(View view) {
        EditText loginEdit = findViewById(R.id.loginInput);
        String loginString = loginEdit.getText().toString();
        EditText passwordEdit = findViewById(R.id.pwdInput);
        String passwordString = passwordEdit.getText().toString();

        if ( loginString.equals("") || passwordString.equals("") ) {
            Toast toast = Toast.makeText(getApplicationContext(), "Você deve preencher ambos os campos.", Toast.LENGTH_SHORT);
            toast.show();
            return;
        }

        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Efetuanto tentativa de login...");
        progressDialog.show();

        Call<Login> call = new OscarRetrofit().getLoginService().getFullAddress(loginString, passwordString);
        call.enqueue(new Callback<Login>() {
            @Override
            public void onResponse(Call<Login> call, Response<Login> response) {
                if (response.isSuccessful()) {
                    Login login = response.body();
                    progressDialog.dismiss();

                    // Login falhou
                    if (!login.getResultado()) {
                        Toast toast = Toast.makeText(getApplicationContext(), "As credenciais estão incorretas!", Toast.LENGTH_SHORT);
                        toast.show();
                        return;
                    }

                    // Login bem sucedido, buscar token
                    ProgressDialog progressDialogToken = new ProgressDialog(AutenticarActivity.this);
                    progressDialogToken.setMessage("Recebendo token...");
                    progressDialogToken.show();

                    Call<Token> tokenCall = new OscarRetrofit().getTokenService().getFullAddress(loginString);
                    tokenCall.enqueue(new Callback<Token>() {
                        @Override
                        public void onResponse(Call<Token> call, Response<Token> response) {
                            if (response.isSuccessful()) {
                                Token token = response.body();
                                progressDialogToken.dismiss();

                                // Não encontrou um login com o string solicitado
                                if (token.getToken() == -1) {
                                    Toast toast = Toast.makeText(getApplicationContext(), "Não foi possível encontrar a conta fornecida para criar um token... Algo errado aconteceu.", Toast.LENGTH_SHORT);
                                    toast.show();
                                    return;
                                }

                                Intent it = new Intent(AutenticarActivity.this, DashboardActivity.class);
                                Bundle params = new Bundle();
                                params.putString("login", loginString);
                                params.putInt("token", token.getToken());
                                it.putExtras(params);
                                startActivity(it);
                                finish();
                            }
                        }

                        @Override
                        public void onFailure(Call<Token> call, Throwable t) {
                            progressDialogToken.dismiss();
                            Toast toast = Toast.makeText(getApplicationContext(), "Não foi possível receber o token do servidor, encerrando...", Toast.LENGTH_SHORT);
                            toast.show();
                            finish();
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<Login> call, Throwable t) {
                progressDialog.dismiss();
                Toast toast = Toast.makeText(getApplicationContext(), "Não foi possível se conectar ao servidor , encerrando...", Toast.LENGTH_SHORT);
                toast.show();
                finish();
            }
        });
    }
}