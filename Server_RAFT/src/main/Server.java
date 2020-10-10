package main;
import java.util.ArrayList;
import enums.State;

public class Server implements IServer{

	private int port;
	private State state;

	public Server(int port, State s){
		this.port = port;
		this.state = State.FOLLOWER;
	}

	public void leader() {
		ArrayList<Integer> ports = new ArrayList<>();
	}

	public String execute(String s, int i)  {
		return s + " " + i;
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
