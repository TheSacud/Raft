package main;
import java.util.ArrayList;
import java.util.List;

import enums.State;

public class Server implements IServer{

	private int port;
	private State state;
	private List<String> table ;
	private int lastEntry;

	public Server(int port, State s){
		this.port = port;
		this.state = State.FOLLOWER;
		this.table = new ArrayList<>();
		lastEntry = 0;
	}

	public void leader() {
		ArrayList<Integer> ports = new ArrayList<>();
	}

	public String execute(String s, String i)  {
		String [] dividir = i.split(":");
		int idCliente =  Integer.parseInt(dividir[0]);
		int idOperacao = Integer.parseInt(dividir[1]);
		table.add(s+":"+idCliente+":"+idOperacao);
		lastEntry++;
		System.out.println("Operacao: " + idOperacao +", Cliente: " + idCliente + ", String no log: " + s);
		return s;
	}

	public int getPort() {
		return this.port;
	}

	public State getState() {
		return this.state;
	}

	public int appendEntry(String info) {
		// TODO Auto-generated method stub
		return 0;
	}

}
