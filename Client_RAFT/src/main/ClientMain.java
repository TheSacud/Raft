package main;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Random;
import java.util.Scanner;
import service.IServerService;

public class ClientMain{

	private static IServerService server;

	public static void main(String[] args) {
		try {
			int id = 0;
			int port = 1234;
			String name = "rmi://localhost/server";
			Registry reg = LocateRegistry.getRegistry(port);
			server = (IServerService) reg.lookup(name);
			boolean sair = true;
			while(sair){
				Scanner s = new Scanner(System.in);
				System.out.println("Insira String: ");
				String linha = s.nextLine();
				if(linha.equals("quit")) {
					sair = false;
				}else {
					String resposta = server.request(linha, id);
					server = (IServerService) reg.lookup(name);
					resposta = server.request(linha, id);
					System.out.println("Resposta : \n"+resposta);
				}
				s.close();
			}
		}catch (Exception e) {
			e.printStackTrace();
			System.err.print(e.getMessage()+"\n");
		}
	}
}
