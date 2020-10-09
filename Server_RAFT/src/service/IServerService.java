package service;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IServerService extends Remote {
	
	public String request(String s) throws RemoteException;
	
}
