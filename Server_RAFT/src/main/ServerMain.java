package main;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import service.ServerService;

public class ServerMain {

	public static void main(String[] args) {
		ServerService server = null;
		if(args.length != 1){
			System.err.println("Uso errado");
			System.exit(0);
		}
		
		int port = Integer.parseInt(args[0]);
		
		try{
			
			server = new ServerService(port);

			Registry reg = LocateRegistry.createRegistry(port);
			
			reg.rebind("rmi://localhost/server", server);
			
			System.out.println("Server estah a escutar no porto " + args[0]);
			
			
		}catch(Exception e){
			System.err.println(e.getMessage());
		
		}
	
	}
	
}
