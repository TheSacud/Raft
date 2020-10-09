import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

import service.IServerService;

public class ClienteTFD {

	private static IServerService server;

	public static void main(String[] args) {
		try {
			int port = 12345;
			String name = "rmi://localhost/server";
			tryConnect(false);
			
			Scanner s = new Scanner(System.in);
			System.out.println("Insira String: ");
			String linha = s.nextLine();
			String resposta = server.request(linha);
			server = locateAux(port, name);
			resposta = server.request(linha);
			System.out.println("Resposta : \n"+resposta);


			s.close();
		}catch (Exception e) {
			e.printStackTrace();
			System.err.print("Morreu " + e.getMessage()+"\n");
		}
	}
	
	public static IServerService locateAux(int port, String name) {
		try {
			Registry reg = LocateRegistry.getRegistry(port);
			return (IServerService) reg.lookup(name);
		}catch ( RemoteException | NotBoundException e) {
			return null;
		}
		
	}
	
	public static boolean tryConnect(boolean connected) {
		while(!connected) {
			server = locateAux(123456, "rmi://localhost/server");
			if(server instanceof Remote) {
				connected = true;
			}
		}
		return connected;
	}


}
