package main;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.util.Timer;

import enums.State;
import service.IServerService;

public class Server implements IServer{

	private int port;
	private State state;
	private List<String> table ;
	List<Integer> ports;
	
	//parte da eleicao lider
	private Timer timer;
	private int term;
	private int voteServer; // server votado
	private int nAnswers;
	private Map<Integer,Integer> votes = new HashMap<>();
	private int portLider;

	////
	private Random r = new Random();
	//Comunicaçao com os followers
	private Communication one;
	private Communication two;
	private Communication three;
	private Communication four;
	private List<Communication> followers = Arrays.asList(one,two, three, four);
	private int voteForServer;
	
	public Server(int port, State s){
		this.port = port;
		this.state = State.FOLLOWER;
		this.table = new ArrayList<>();
		this.term = 0;
		this.nAnswers = 0;
		addPorts();
	
		String diretoria = System.getProperty("user.dir");
		File ficheiroLog = new File(diretoria + "/logServer"+port+".txt");
		ficheiroLog.delete();
	}

	private void addPorts() {
		ports = new ArrayList<>();
		ports.add(1111);
		ports.add(1112);
		ports.add(1113);
		ports.add(1114);
		ports.add(1115);		
	}
	
	public void run() {
		int t = r.nextInt(2)+1; //[1,2]
		int t2 = r.nextInt(2)+1;
		timer = new Timer();
		Tasks task = new Tasks(this);
		timer.schedule(task, t* t2 * 10000); // maximo tempo = 40s
	}

	public String execute(String s, String i)  {
		String [] dividir = i.split(":");
		int idCliente =  Integer.parseInt(dividir[0]);
		int idOperacao = Integer.parseInt(dividir[1]);
		table.add(s+":"+idCliente+":"+idOperacao);
		String log = "Cliente: " + idCliente + ", Operacao: " + idOperacao + ", Log ->> " + s;
		System.out.println(log);
		try {
			escreveLog(log);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return s;
	}

	private void escreveLog(String log) throws IOException {
		String diretoria = System.getProperty("user.dir");
		File ficheiroLog = new File(diretoria + "/logServer"+port+".txt");
		ficheiroLog.createNewFile();
		Scanner sc = new Scanner(ficheiroLog);
		while(sc.hasNextLine()) {
			sc.nextLine();

		}
		FileOutputStream fos0 = new FileOutputStream(ficheiroLog,true);
		fos0.write(log.getBytes());
		fos0.write("\n".getBytes());
		sc.close();
		fos0.close();
	}

	public int getPort() {
		return this.port;
	}

	public State getState() {
		return this.state;
	}

	public void appendEntry(String info, String i) {		
		for (Integer porto : ports) {
			if(porto != port) {
				IServerService server;
				String name = "server:"+porto;
				Registry reg;
				try {
					reg = LocateRegistry.getRegistry(porto);
					server = (IServerService) reg.lookup(name);
					if(server instanceof Remote) {
						System.out.println("Replicou para o server no porto: " + porto);
					}
					server.append(info,i);
				} catch (RemoteException | NotBoundException e) {
					System.out.println("Ah procura de servers para replicar...");
				}
			}
		}
	}
	
	

	public String getLeader() {
		String result = "erro";
		for (Integer porto : ports) {
			if(porto != port) {
				IServerService server;
				String name = "server:"+porto;
				Registry reg;
				try {
					reg = LocateRegistry.getRegistry(porto);
					server = (IServerService) reg.lookup(name);
					if(server instanceof Remote) {
						if(server.state().equals(State.LEADER)) {
							result = Integer.toString(porto);
						}
					}
				} catch (RemoteException | NotBoundException e) {
					//System.out.println("Ah procura de servers...");
				}
			}
		}
		return result;
	}
	
	private void eleicao() {
		System.out.println("Iniciar eleicao");
		this.term =+1;
		this.state = State.CANDIDATE;
		voteServer = port;
		ArrayList<Integer> otherPorts = new ArrayList<Integer>();
		
		for(Integer i : ports) {
			if(i != this.getPort())
				otherPorts.add(i);
		}
		
		
		
		
	}

	public void cancelTimer() {
		this.timer.cancel();		
		
	}

	public void incTerm() {
		this.term++;
	}

	public void setState(State estado) {
		this.state = estado;
	}

	public void setVoteFor(int vote) {
		this.voteForServer = port;
	}

	public Map<Integer,Integer> getVotes() {
		return this.votes;
	}
	
	//verify eh um Heartbeat sem nada
	public void addVote(int port, int verify) {		
		this.votes.put(port, verify);
	}

	public void incNAnswers() {
		this.nAnswers++;
	}

	public int getnAnswers() {
		return this.nAnswers;
	}

	public void setnAnswers(int i) {
		this.nAnswers = i;
	}

	public int getTerm() {
		return this.term;
	}

	public void setTerm(int newTerm) {
		this.term = newTerm;
		
	}

	public void resetVotes() {
		this.votes = new HashMap<>();
	}

	public ArrayList<Integer> getPorts() {
		return (ArrayList<Integer>) this.ports;
	}

	public List<Communication> getFollowers() {
		return this.followers;
	}

	public int getLeaderPort() {
		return this.portLider;
	}

	public void setLeaderPort(int pLider) {
		this.portLider = pLider;
	}

}
