package main;
import java.rmi.Remote;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Random;
import java.util.Scanner;
import service.IServerService;

public class ClientMain{

	private static IServerService server;

	public static void main(String[] args) {
		Random r = new Random();
		try {
			int id = 0;
			int[] ports = {1111,1112,1113,1114,1115};
			
			String name = "rmi://localhost/server";
			
			boolean connected = false;
			while(!connected) {
				int port = r.nextInt(5);
				try {
					Registry reg = LocateRegistry.getRegistry(ports[port]);
					server = (IServerService) reg.lookup(name);
					if(server instanceof Remote) {
						System.out.println("encontrou o server no port: " + ports[port]);
						connected = true;
					}
				}catch (Exception e) {
					System.out.println("Ah procura....");
					//e.printStackTrace();
					//System.err.print(e.getMessage()+"\n");
				}
			}
			
			
			


			Scanner s = new Scanner(System.in);
			
			
			boolean sair = true;
			while(sair){
				System.out.println("Insira String: ");
				String linha = s.nextLine();
				if(linha.equals("exit")) {
					sair = false;
				}else {
					String resposta = server.request(linha, id);
					id++;
					resposta = server.request(linha, id);
					System.out.println("Resposta : \n"+resposta);
				}
			}
			s.close();
		}catch (Exception e) {
			e.printStackTrace();
			System.err.print(e.getMessage()+"\n");
		}
	}
	
}
