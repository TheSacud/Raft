package service;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import enums.State;
import main.Server;

public class ServerService extends UnicastRemoteObject implements IServerService{

	/**
	 * 
	 */
	private static final long serialVersionUID = 9217032947382738515L;

	private Server server;

	public ServerService(int port, @SuppressWarnings("exports") State s) throws RemoteException {
		server = new Server(port, s);
	}

	public String request(String s, int i) throws RemoteException{
		return server.execute(s, i);
	}

	public int getPort() {
		return server.getPort();
	}

	@SuppressWarnings("exports")
	public State state() {
		return server.getState();
	}
	
	public int AppendEntriesRPC(String info) throws RemoteException {
		return server.appendEntry(info);
	}



}
