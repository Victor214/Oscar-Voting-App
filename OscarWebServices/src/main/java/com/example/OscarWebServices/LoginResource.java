package com.example.OscarWebServices;

import Beans.Login;
import DAO.ConnectionFactory;
import DAO.LoginDAO;
import org.json.JSONObject;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

@Path("/login/{usuario}/{senha}")
public class LoginResource {
    @GET
    @Produces("text/plain")
    public String login(@PathParam("usuario") String usuario, @PathParam("senha") String senha) {
        // Pega o resultado do banco de dados, com as informações fornecidas nos parâmetros
        LoginDAO loginDAO = new LoginDAO();
        boolean loginResultado = loginDAO.verificarLogin(usuario, senha);

        // Monta o objeto de retorno
        Login login = new Login();
        login.setUsuario(usuario);
        login.setResultado("" + loginResultado);

        // Cria o jSON de retorno em cima do objeto
        JSONObject loginJson = new JSONObject();
        loginJson.put("login", login.getUsuario());
        loginJson.put("resultado", login.getResultado());

        return "" + loginJson.toString(); // Retorna em formato json como um string
    }
}