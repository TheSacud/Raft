package service;


import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import main.Server;



public class ServerService extends UnicastRemoteObject implements IServerService{


	/**
	 * 
	 */
	private static final long serialVersionUID = -1570177312938766089L;
	private Server server;

	public ServerService(int port) throws RemoteException {
		server = new Server(port);
	}

	public String request(String s, int id) throws RemoteException{
		return server.request(s,id);
	}
	


}
