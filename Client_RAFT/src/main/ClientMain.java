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
		try {
			Random r = new Random();
			int id = r.nextInt(1000000);
			int idOperacao = 0;
			String name = "rmi://localhost/server";
			int port = 1111;
			try {
				Registry reg = LocateRegistry.getRegistry(port);
				server = (IServerService) reg.lookup(name);
				if(server instanceof Remote) {
					System.out.println("Encontrou o LEADER server no port: " + port);
				}
			}catch (Exception e) {
				System.out.println("Ah procura....");
			}

			Scanner s = new Scanner(System.in);
			boolean sair = true;
			while(sair){
				System.out.println("Insira String: ");
				String linha = s.nextLine();
				if(linha.equals("exit")) {
					sair = false;
				}else {
					String ids = Integer.toString(id) + ":" + Integer.toString(idOperacao);
					String resposta = server.request(linha, ids);
					idOperacao++;
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
