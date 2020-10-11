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
import java.util.List;
import java.util.Scanner;

import enums.State;
import service.IServerService;

public class Server implements IServer{

	private int port;
	private State state;
	private List<String> table ;

	public Server(int port, State s){
		this.port = port;
		this.state = s;
		this.table = new ArrayList<>();
		String diretoria = System.getProperty("user.dir");
		File ficheiroLog = new File(diretoria + "/logServer"+port+".txt");
		ficheiroLog.delete();
	}

	public String execute(String s, String i)  {
		String [] dividir = i.split(":");
		int idCliente =  Integer.parseInt(dividir[0]);
		int idOperacao = Integer.parseInt(dividir[1]);
		table.add(s+":"+idCliente+":"+idOperacao);
		String log = "Cliente: " + idCliente + ", Operacao: " + idOperacao + ", String no log ->> " + s;
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
		List<Integer> ports = new ArrayList<>();
		ports.add(1112);
		ports.add(1113);
		ports.add(1114);
		ports.add(1115);
		for (Integer port : ports) {
			IServerService server;
			String name = "server:"+port;
			Registry reg;
			try {
				reg = LocateRegistry.getRegistry(port);
				server = (IServerService) reg.lookup(name);
				if(server instanceof Remote) {
					System.out.println("Replicou para o server no porto: " + port);
				}
				server.append(info,i);
			} catch (RemoteException | NotBoundException e) {
				System.out.println("Ah procura de servers...");
			}
		}
	}

	public String getLeader() {
		return "1111";
	}

}
