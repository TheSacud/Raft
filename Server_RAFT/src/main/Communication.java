package main;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import service.IServerService;

public class Communication extends Thread{
	private Registry reg;
	private IServerService iServer;
	private Server server;
	private int port;
	private int delay;
	private boolean eleicao;
	private int verify;
	private enums.State state;
	
	public Communication(Server s, int port, int delay, boolean b) {
		this.port = port;
		this.server = s;
		this.delay = delay;
		this.eleicao = b;
		
		//Falta criar log de cada server e ir busca lo
		
		connect();
		System.out.println(port + " --> " + this.getName());
	}

	private void connect() {
		try {
			reg = LocateRegistry.getRegistry(port);
			iServer = (IServerService) reg.lookup("server:"+port);
			
		}catch(RemoteException | NotBoundException e) {
			System.out.println("Falhou a conectar.");
		}
		
	}
	
	public void run() {
		try {
			if(eleicao) {
				verify = sendHB(iServer, null,eleicao);
				synchronized(server.getVotes()) {
					server.addVote(port, verify);
					server.incNAnswers();
					System.out.println(verify);
				
					if(server.getnAnswers() < 1) {
						System.out.println("Esperou : " + server.getnAnswers());
					
						server.getVotes().wait(8000);
						server.setnAnswers(0);
					}else {
						System.out.println("Deu unlock");
						server.setnAnswers(0);
						server.getVotes().notifyAll();
						eleicao = false;
					}
				}
			}
			
		} catch (InterruptedException | RemoteException e) {
			return;
		}
	}
	
	//HeartBeat do lider
	
	private int sendHB(IServerService iServer, String entry,boolean eleicao) throws RemoteException {
		int f;
			f = iServer.AppendEntriesRPC(server.getTerm(),server.getPort(), entry);
			if(!eleicao) {
				server.setTerm(f);
				state = enums.State.FOLLOWER;
				server.setState(state);
			}
			
			return 0;
	
	
	}
}
