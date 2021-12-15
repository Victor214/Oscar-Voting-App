package com.example.OscarWebServices;

import Beans.Login;
import Beans.Voto;
import DAO.LoginDAO;
import org.json.JSONObject;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

@Path("/voto/{usuario}/{token}/{filmeID}/{diretorID}")
public class VotoResource {
    @GET
    @Produces("text/plain")
    public String login(@PathParam("usuario") String usuario, @PathParam("token") int token, @PathParam("filmeID") int filmeID, @PathParam("diretorID") int diretorID) {
        // Pega o resultado do banco de dados, com as informações fornecidas nos parâmetros
        LoginDAO loginDAO = new LoginDAO();
        int votoResultado = loginDAO.confirmarVoto(usuario, token, filmeID, diretorID);

        // Monta o objeto de retorno
        Voto voto = new Voto();
        voto.setUsuario(usuario);
        voto.setResultado(votoResultado);

        // Cria o jSON de retorno em cima do objeto
        JSONObject votoJson = new JSONObject();
        votoJson.put("login", voto.getUsuario());
        votoJson.put("resultado", voto.getResultado());

        return "" + votoJson.toString(); // Retorna em formato json como um string
    }
}
