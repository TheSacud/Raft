package main;

public class Server implements IServer{

	private int port;

	public Server(int port){

		this.port = port;
		
	} 
	
	/**
	 * Funcao partilhada pelo ClientRMI, permite troca de mensagens com o client
	 * e o retorno de respostas.
	 * @return String de resposta mais o client id
	 */
	public String execute(String s, int i)  {
		return s + i;
	}

}
