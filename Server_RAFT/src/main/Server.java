package main;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

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
		String diretoria = System.getProperty("user.dir");
		File ficheiroLog = new File(diretoria + "/logServer"+port+".txt");
		ficheiroLog.delete();
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
		String log = "Operacao: " + idOperacao +", Cliente: " + idCliente + ", String no log: " + s;
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

	public int appendEntry(String info) {
		// TODO Auto-generated method stub
		return 0;
	}

}
