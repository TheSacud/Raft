package service;


import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import main.Server;



public class ServerService extends UnicastRemoteObject implements IServerService{


	/**
	 * 
	 */
	private static final long serialVersionUID = 9217032947382738515L;
	
	private Server server;

	public ServerService(int port) throws RemoteException {
		server = new Server(port);
	}

	public String request(String s, int i) throws RemoteException{
		return server.execute(s, i);
	}
	


}
