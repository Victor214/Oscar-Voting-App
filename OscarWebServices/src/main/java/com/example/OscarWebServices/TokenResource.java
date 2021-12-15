package com.example.OscarWebServices;


import Beans.Login;
import Beans.Token;
import DAO.LoginDAO;
import org.json.JSONObject;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

@Path("/token/{usuario}")
public class TokenResource {
    @GET
    @Produces("text/plain")
    public String login(@PathParam("usuario") String usuario) {
        // Pega o resultado do banco de dados, com as informações fornecidas nos parâmetros
        LoginDAO loginDAO = new LoginDAO();
        Token tokenResultado = loginDAO.gerarToken(usuario);

        // Cria o jSON de retorno em cima do objeto
        JSONObject tokenJson = new JSONObject();
        if (tokenResultado != null) {
            tokenJson.put("login", tokenResultado.getUsuario());
            tokenJson.put("token", tokenResultado.getToken());
        } else {
            tokenJson.put("login", usuario);
            tokenJson.put("token", -1);
        }

        return "" + tokenJson.toString(); // Retorna em formato json como um string
    }
}
