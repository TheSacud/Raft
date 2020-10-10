package main;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IServer extends Remote{

	public String execute(String s, int i) throws RemoteException;

}
