package main;

import java.util.ArrayList;
import java.util.TimerTask;

import enums.State;

public class Tasks extends TimerTask{

	private Server server;
	private boolean lider;
	
	public Tasks(Server server) {
		this.server = server;
		this.lider = false;
	}
	
	@Override
	public void run() {
		System.out.println("Inicia a eleicao");
		while(!lider) {
			startVote();
			//envia RequestVote RPCs a todos os servers
			eleicao();
		}
		server.cancelTimer();		
	}

	private void startVote() {
		// incrementar termo
		server.incTerm();
		server.setState(State.CANDIDATE);
		//voto
		server.setVoteFor(server.getPort());
	}
	
	private void eleicao() {
		server.resetVotes();
		ArrayList<Integer> portsDisponiveis = new ArrayList<>();
		ArrayList<Integer> ports = new ArrayList<>();
		portsDisponiveis = (ArrayList<Integer>) server.getPorts();
		
		//verifica os portsDisponiveis tirando o seu para enviar RPC
		for(Integer i : portsDisponiveis) {
			if(i != this.server.getPort())
				ports.add(i);
		}
		
		int j = 0;
		for(Communication c : server.getFollowers()) {
			c = new Communication(server, ports.get(j), 5000,true);
			c.start();
			j++;
		}
		
		synchronized(server.getVotes()) {
			server.addVote(server.getPort(),0);
			if(server.getPort() == 1111) {
				int count = 0;
				for(Integer i: server.getVotes().values()) {
					if(i == 0)
						count++;
				}
				
				if(count > 3) {
					server.setState(State.LEADER);
					// alterar LIDER para true
					lider = true;
					server.setLeaderPort(server.getPort());
					System.out.println("Tornei-me Lider " + server.getLeaderPort());
					//server.leader();
				}
				server.resetVotes();
			}
		
		
		
		}
	}
}
