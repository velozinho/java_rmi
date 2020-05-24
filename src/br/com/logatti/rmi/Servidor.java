/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.logatti.rmi;

import br.com.logatti.Connection.ConnectionSQLite;
import br.com.logatti.model.Cliente_model;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Servidor extends UnicastRemoteObject implements Servico {     

    public Servidor() throws RemoteException {
        super();
    }

    public static void main(String[] args) {

        try {
            Servidor servidor = new Servidor();
            String localizacao = "//localhost/servico";
            Naming.rebind(localizacao, servidor);
        } catch (MalformedURLException ex) {
            System.out.println("Erro de url mal formada:" + ex.getMessage());

        } catch (RemoteException ex) {
            System.out.println("Erro:" + ex.getMessage());
        }

    }

    /**
     *
     * @param cliente
     */
    @Override
    public void insereCliente(Cliente_model cliente) {
        ConnectionSQLite conn;
        try {
            conn = new ConnectionSQLite();
            conn.exec_insere_cliente(cliente);
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     *
     * @param cpf
     * @return
     */
    @Override
    public Cliente_model buscarCPF(String cpf) {
        Cliente_model cli = new Cliente_model();
        ConnectionSQLite conn;
        try {
            conn = new ConnectionSQLite();
            cli = conn.exec_buscarCPF(cpf);
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
        }
        return cli;
    }

    /**
     *
     * @param cpf
     */
    @Override
    public void excluiCliente(String cpf) {

        try {
            ConnectionSQLite conn = new ConnectionSQLite();
            conn.excluiCliente(cpf);
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     *
     * @param cli
     */
    @Override
    public void updateCliente(Cliente_model cli) {
        ConnectionSQLite conn;
        try {
            conn = new ConnectionSQLite();
            conn.exec_update_cliente(cli);
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
