package main;

import java.util.ArrayList;
import java.util.List;

import enums.State;

public class Server implements IServer{

	private int port;
	private State state;
	
	private static final List<Integer> portsAvailable = List.of(1111,1112,1113,1114,1115);

	public Server(int port, State s){

		this.port = port;
		this.state = State.FOLLOWER;
		
		
		
	}
	
	public void leader() {
		
		ArrayList<Integer> ports = new ArrayList<>();
		
	}
	
	/**
	 * Funcao partilhada pelo ClientRMI, permite troca de mensagens com o client
	 * e o retorno de respostas.
	 * @return String de resposta mais o client id
	 */
	public String execute(String s, int i)  {
		return s + i;
	}

	public int getPort() {
		return this.port;
	}

	public State getState() {
		return this.state;
	}

}
