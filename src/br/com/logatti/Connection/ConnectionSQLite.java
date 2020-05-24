/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.logatti.Connection;

import br.com.logatti.model.Cliente_model;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConnectionSQLite {   
    
    private final Connection conexao;
    private final Statement statement;
    PreparedStatement stmt;

    public ConnectionSQLite() throws ClassNotFoundException, SQLException {
        Class.forName("org.sqlite.JDBC");
        conexao = DriverManager.getConnection("jdbc:sqlite:C:\\tmp\\proj_p1.db");
        statement = conexao.createStatement();
        conexao.setAutoCommit(true);
        System.out.println("Conectou!");
    }

    public void desconectar() {
        try {
            conexao.close();
            System.out.println("Conexão fechada!");
        } catch (SQLException e) {
        }
    }

    //INSERT / UPDATE / DELETE
    public void exec_insere_cliente(Cliente_model cli) throws SQLException {

        stmt = conexao.prepareStatement("INSERT INTO Cliente (nome, cpf, telefone, celular, endereco, email, cidade, rg,datNasc) values (?,?,?,?,?,?,?,?,?)");
        stmt.setString(1, cli.getNome());
        stmt.setString(2, cli.getCPF());
        stmt.setString(3, cli.getTelefone());
        stmt.setString(4, cli.getCelular());
        stmt.setString(5, cli.getEndereco());
        stmt.setString(6, cli.getEmail());
        stmt.setString(7, cli.getCidade());
        stmt.setString(8, cli.getRG());
        stmt.setString(9, cli.getDataNasc());

        stmt.execute();

    }

    public Cliente_model exec_buscarCPF(String cpf) throws SQLException, ClassNotFoundException {
        Cliente_model cli = new Cliente_model();
        ResultSet rs;
        stmt = conexao.prepareStatement("select * from Cliente where cpf = ? ");
        stmt.setString(1, cpf);
        rs = stmt.executeQuery();

        while (rs.next()) {
            cli.setId(rs.getInt("id"));
            cli.setNome(rs.getString("nome"));
            cli.setCPF(rs.getString("cpf"));
            cli.setCidade(rs.getString("cidade"));
            cli.setCelular(rs.getString("celular"));
            cli.setDataNasc(rs.getString("datNasc"));
            cli.setEmail(rs.getString("email"));
            cli.setRG(rs.getString("rg"));
            cli.setEndereco(rs.getString("endereco"));
            cli.setTelefone(rs.getString("telefone"));

        }

        return cli;
    }

    //SELECT
    public ResultSet execComRetorno(String sql) throws SQLException {
        return statement.executeQuery(sql);
    }

    public void excluiCliente(String cpf) {

        try {
            stmt = conexao.prepareStatement("DELETE FROM Cliente where cpf = ? ");
            stmt.setString(1, cpf);
            stmt.execute();
        } catch (SQLException ex) {
            Logger.getLogger(ConnectionSQLite.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void exec_update_cliente(Cliente_model cli) throws SQLException {

        stmt = conexao.prepareStatement(" UPDATE Cliente SET nome = ?, telefone = ?, celular = ?, endereco = ?, email = ?, cidade = ?, rg = ?,datNasc = ? WHERE cpf = ? ");
        stmt.setString(1, cli.getNome());
        stmt.setString(2, cli.getTelefone());
        stmt.setString(3, cli.getCelular());
        stmt.setString(4, cli.getEndereco());
        stmt.setString(5, cli.getEmail());
        stmt.setString(6, cli.getCidade());
        stmt.setString(7, cli.getRG());
        stmt.setString(8, cli.getDataNasc());
        stmt.setString(9, cli.getCPF());

        stmt.executeUpdate();

    }
}
