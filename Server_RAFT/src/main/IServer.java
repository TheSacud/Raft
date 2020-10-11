package main;
import java.rmi.Remote;
import java.rmi.RemoteException;

import enums.State;

public interface IServer extends Remote{

	public String execute(String s, String i) throws RemoteException;
	
	public void appendEntry(String info, String i) throws RemoteException;
	
	public State getState();

}
