package main;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
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
			
			boolean ligado = false;
			int[] portos = {1111,1112,1113,1114,1115};
			
			while(!ligado) {
				int i = r.nextInt(5);
				String nomeServer = "server:"+portos[i];
				try {
					Registry reg = LocateRegistry.getRegistry(portos[i]);
					server = (IServerService) reg.lookup(nomeServer);
					if(server instanceof Remote) {
						System.out.println("Encontrou um server no port: " + portos[i]);
						ligado = true;
					}
				}catch ( RemoteException | NotBoundException e) {
					System.out.println("Tentando estabelecer ligação ao server...");
				}
				
			}
			

			Scanner s = new Scanner(System.in);
			boolean sair = true;
			while(sair){
				System.out.println("Insira String: (exit para sair)");
				String linha = s.nextLine();
				if(linha.equals("exit")) {
					sair = false;
				}else {
					String ids = Integer.toString(id) + ":" + Integer.toString(idOperacao);
					String resposta = server.request(linha, ids);
					if(resposta.contains("error:leader:")) {
						System.out.println("Servidor contactado não eh o lider!");
						System.out.println("A mudar para o lider...");
						System.out.println("Efetue novamente a operação");
						String [] splitReposta = resposta.split(":");
						String portoLider = splitReposta[2];
						String nomeServer = "server:"+portoLider;
						try {
							Registry reg = LocateRegistry.getRegistry(Integer.parseInt(portoLider));
							server = (IServerService) reg.lookup(nomeServer);
							if(server instanceof Remote) {
								ligado = true;
							}
						}catch ( RemoteException | NotBoundException e) {
							System.out.println("Tentando estabelecer ligação ao server...");
						}
						
					}else {
						idOperacao++;
						System.out.println("Resposta : "+resposta);
						System.out.println();
					}
				}
			}
			s.close();
		}catch (Exception e) {
			e.printStackTrace();
			System.err.print(e.getMessage()+"\n");
		}
	}

}
