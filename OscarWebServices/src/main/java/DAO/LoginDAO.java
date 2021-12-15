package DAO;

import Beans.Token;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Random;

public class LoginDAO {
    public boolean verificarLogin(String usuario, String senha) {
        Connection con = null;
        PreparedStatement st = null;
        ResultSet rs = null;

        try {
            con = ConnectionFactory.getConnection();
            st = con.prepareStatement("SELECT login FROM usuario WHERE login = ? AND senha = ?");
            st.setString(1, usuario);
            st.setString(2, senha);

            rs = st.executeQuery();
            return rs.next(); // Retorna se já existe um login

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Token gerarToken(String usuario) {
        Connection con = null;
        PreparedStatement st = null;

        try {
            Random rand = new Random();
            int randomInt = rand.nextInt(101); // 0 a 100

            con = ConnectionFactory.getConnection();
            st = con.prepareStatement("UPDATE usuario SET token = ? WHERE login = ?");
            st.setInt(1, randomInt);
            st.setString(2, usuario);
            int result = st.executeUpdate();
            if (result <= 0) // Sem entradas no banco de dados para esse usuário
                return null;

            Token token = new Token();
            token.setUsuario(usuario);
            token.setToken(randomInt);
            return token;
        } catch (Exception e) {
            // throw new RuntimeException(e);
            return null;
        }
    }

    public int confirmarVoto(String usuario, int token, int filmeID, int diretorID) {
        Connection con = null;
        PreparedStatement st = null;
        ResultSet rs = null;

        try {
            con = ConnectionFactory.getConnection();

            // Verifica se o token é válido
            st = con.prepareStatement("SELECT login FROM usuario WHERE login = ? AND token = ?");
            st.setString(1, usuario);
            st.setInt(2, token);
            rs = st.executeQuery();
            if (!rs.next())
                return -2; // Token inválido

            // Verifica se o voto já não foi realizado
            st = con.prepareStatement("SELECT login FROM usuario WHERE login = ? AND votoFilme IS NULL AND votoDiretor IS NULL");
            st.setString(1, usuario);
            rs = st.executeQuery();
            if (!rs.next())
                return -3; // Voto já realizado

            // Finalmente computa o voto
            st = con.prepareStatement("UPDATE usuario SET votoFilme = ?, votoDiretor = ? WHERE login = ?");
            st.setInt(1, filmeID);
            st.setInt(2, diretorID);
            st.setString(3, usuario);
            int result = st.executeUpdate();
            if (result <= 0)
                return -1; // Erro Genérico

            return 1; // Sucesso
        } catch (Exception e) {
            // throw new RuntimeException(e);
            return -1; // Erro Genérico
        }
    }
}
