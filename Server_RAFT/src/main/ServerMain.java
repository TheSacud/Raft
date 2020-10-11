package main;
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
import service.ServerService;

public class ServerMain {

	public static void main(String[] args) {
		ServerService server = null;
		System.out.println("Introduza porto do server: <<1111,1112,1113,1114,1115>>");
		Scanner sc = new Scanner(System.in);
		int port = Integer.parseInt(sc.nextLine());
		boolean sair = false;
		while(!sair) {
			if(port == 1111 || port == 1112 || port == 1113 || port == 1114 || port == 1115) {
				sair = true;
			}else {
				System.out.println("Introduza corretamente o porto do server: <<1111,1112,1113,1114,1115>>");
				port = Integer.parseInt(sc.nextLine());
			}
		}
		try{
			if(verificaSeJaHaLider(port)) {
				server = new ServerService(port, State.FOLLOWER);
			}else {
				server = new ServerService(port, State.LEADER);
			}
			Registry reg = LocateRegistry.createRegistry(port);
			reg.rebind("server:"+port, server);
			System.out.println("Server ah escutar no porto " + port);
			sc.close();
		}catch(Exception e){
			System.err.println(e.getMessage());
		}
	}
	
	public static boolean verificaSeJaHaLider(int port) {
		List<Integer> ports = new ArrayList<>();
		ports.add(1111);
		ports.add(1112);
		ports.add(1113);
		ports.add(1114);
		ports.add(1115);
		boolean existe = false;
		for (Integer porto : ports) {
			if(porto != port) {
				IServerService server;
				String name = "server:"+porto;
				Registry reg;
				try {
					reg = LocateRegistry.getRegistry(porto);
					server = (IServerService) reg.lookup(name);
					if(server instanceof Remote) {
						existe = true;
					}
				} catch (RemoteException | NotBoundException e) {
					//System.out.println("Ah procura de servers ja online...");
				}
			}
		}
		return existe;
	}
	
}
