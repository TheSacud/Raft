package service;
import java.rmi.Remote;
import java.rmi.RemoteException;

import enums.State;

public interface IServerService extends Remote {

	public String request(String s, String i) throws RemoteException;

	public String append(String info, String i) throws RemoteException;
	
	@SuppressWarnings("exports")
	public State state() throws RemoteException;

}
