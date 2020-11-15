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

	public ServerService(int port, State s) throws RemoteException {
		server = new Server(port, s);
	}

	public String request(String s, String i) throws RemoteException{
		if(server.getState().equals(State.LEADER)) {
			appendEntriesRPC(s,i);
			return server.execute(s, i);
		}else {
			String resposta = "error:leader:"+server.getLeader()+":"+s+":"+i;
			return resposta;
		}
		
	}

	public int getPort() {
		return server.getPort();
	}

	public State state() throws RemoteException{
		return server.getState();
	}
	
	public void appendEntriesRPC(String info, String i) throws RemoteException {
		server.appendEntry(info,i);
	}

	public String append(String info, String i) {
		return server.execute(info,i);
	}

	@Override
	public int AppendEntriesRPC(int term, int port, String entry) {
		System.out.println("O jorge � gay");
		return 0;
	}



}
