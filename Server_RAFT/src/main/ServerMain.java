package main;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

import enums.State;
import service.ServerService;

public class ServerMain {

	public static void main(String[] args) {
		ServerService server = null;
		System.out.println("Introduza porto do server: ");
		Scanner sc = new Scanner(System.in);
		int port = Integer.parseInt(sc.nextLine());
		try{
			if(port == 1111) {
				server = new ServerService(port, State.LEADER);
			}else {
				server = new ServerService(port, State.FOLLOWER);
			}
			Registry reg = LocateRegistry.createRegistry(port);
			reg.rebind("rmi:/server"+port, server);
			System.out.println("Server ah escutar no porto " + port);
			sc.close();
		}catch(Exception e){
			System.err.println(e.getMessage());
		}
	}
}
