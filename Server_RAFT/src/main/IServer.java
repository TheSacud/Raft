package main;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IServer extends Remote{

	public String execute(String s, String i) throws RemoteException;
	
	public void appendEntry(String info, String i) throws RemoteException;

}
