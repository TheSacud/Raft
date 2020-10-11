package service;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IServerService extends Remote {

	public String request(String s, String i) throws RemoteException;
	
	public String append(String info, String i) throws RemoteException;

}
