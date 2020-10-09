import java.io.IOException;
import java.net.Socket;
import java.util.List;

import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;

public class ServidorTFD {

	private List<String> utilizadores;
	private static int numPorto;

	public static void main(String[] args) {
		System.out.println("Servidor: main");
		numPorto = Integer.parseInt(args[0]);
		ServidorTFD server = new ServidorTFD();
		server.startServer();


	}
	public void startServer (){

		SSLServerSocket sSoc = null;

		try {
			sSoc = (SSLServerSocket) SSLServerSocketFactory.getDefault().createServerSocket(numPorto);
			System.out.println("Aguardando cliente...");
		} catch (IOException e) {
			System.err.println(e.getMessage());
			System.exit(-1);
		}
		while(true) {
			try {
				SSLSocket inSoc = (SSLSocket) sSoc.accept();
				ServerThread newServerThread = new ServerThread(inSoc);
				newServerThread.start();
				System.out.println("Cliente conectado! A espera de dados pessoais...");


			}
			catch (IOException e) {
				e.printStackTrace();
			}

		}

	}

	//Threads utilizadas para comunicacao com os clientes
	class ServerThread extends Thread {

		private Socket socket = null;

		ServerThread(SSLSocket inSoc) {
			socket = inSoc;
			//System.out.println("thread do server para cada cliente");
		}

		public void run(){
			String user = null;
			String passwd = null;
			String diretoria = System.getProperty("user.dir");

		}

	}

}
