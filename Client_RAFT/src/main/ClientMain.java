package main;

import java.rmi.registry.LocateRegistry;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.util.Scanner;

import service.IServerService;

public class ClientMain {

	private static IServerService server;

	public static void main(String[] args) {
		try {
			int port = 1234;
			String name = "rmi://localhost/server";
			Registry reg = LocateRegistry.getRegistry(port);
			server = (IServerService) reg.lookup(name);
			Scanner s = new Scanner(System.in);
			System.out.println("Insira String: ");
			String linha = s.nextLine();
			String resposta = server.request(linha);
			server = (IServerService) reg.lookup(name);
			resposta = server.request(linha);
			System.out.println("Resposta : \n"+resposta);


			s.close();
		}catch (Exception e) {
			e.printStackTrace();
			System.err.print(e.getMessage()+"\n");
		}
	}


}
