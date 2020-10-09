package main;

public class Server implements IServer{

	private int port;

	public Server(int port){

		this.port = port;
		
	} 
	
	/**
	 * Funcao partilhada pelo ClientRMI, permite troca de mensagens com o client
	 * e o retorno de respostas.
	 * @return String de resposta, se for o leader, ou o porto do leader, se for um follower
	 */
	public String request(String s)  {
		return s;
	}

}
