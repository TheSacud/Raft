package service;


import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import main.Server;



public class ServerService extends UnicastRemoteObject{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8238574734864073746L;

	private Server server;

	public ServerService(int port) throws RemoteException {
		server = new Server(port);
	}

	public String request(String s) throws RemoteException{
		return server.request(s);
	}


}
