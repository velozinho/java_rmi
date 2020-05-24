package br.com.logatti.rmi;

import br.com.logatti.model.Cliente_model;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Servico  extends Remote {

    public void insereCliente(Cliente_model cli) throws RemoteException;

    public Cliente_model buscarCPF(String cpf) throws RemoteException;

    public void excluiCliente(String cpf) throws RemoteException;

    public void updateCliente(Cliente_model cli) throws RemoteException;

}
