package com.example.oscarapp;

import android.app.Application;

public class Usuario extends Application {
    private Integer id;
    private String login;
    private Integer token;
    private Integer votoFilme;
    private String votoFilmeDisplay;
    private Integer votoDiretor;
    private String votoDiretorDisplay;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public Integer getToken() {
        return token;
    }

    public void setToken(Integer token) {
        this.token = token;
    }

    public Integer getVotoFilme() {
        return votoFilme;
    }

    public void setVotoFilme(Integer votoFilme) {
        this.votoFilme = votoFilme;
    }

    public String getVotoFilmeDisplay() {
        return votoFilmeDisplay;
    }

    public void setVotoFilmeDisplay(String votoFilmeDisplay) {
        this.votoFilmeDisplay = votoFilmeDisplay;
    }

    public Integer getVotoDiretor() {
        return votoDiretor;
    }

    public void setVotoDiretor(Integer votoDiretor) {
        this.votoDiretor = votoDiretor;
    }

    public String getVotoDiretorDisplay() {
        return votoDiretorDisplay;
    }

    public void setVotoDiretorDisplay(String votoDiretorDisplay) {
        this.votoDiretorDisplay = votoDiretorDisplay;
    }
}
