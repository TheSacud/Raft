package main;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IServer extends Remote{
	
	public String request(String s) throws RemoteException;
	
}
