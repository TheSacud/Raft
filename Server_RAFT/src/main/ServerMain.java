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
			server = new ServerService(port, State.LEADER);
			Registry reg = LocateRegistry.createRegistry(port);
			reg.bind("rmi://localhost/server", server);
			System.out.println("Server ah escutar no porto " + port);
			sc.close();
		}catch(Exception e){
			System.err.println(e.getMessage());
		}
	}
}
