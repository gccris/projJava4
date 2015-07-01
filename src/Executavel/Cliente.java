package Executavel;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

import org.omg.CORBA.portable.OutputStream;

public class Cliente{
    private String host;
    private int porta;
	private ObjectInputStream input;
	private OutputStream output;
	
	public Cliente(String host, int porta) {
		this.host = host;
		this.porta = porta;
	}

	public static void main(String[] args) throws UnknownHostException, IOException{
	     // dispara cliente
	     new Cliente("127.0.0.1", 12345).executa();
   }
	
	private void executa() throws UnknownHostException, IOException {
		Socket cSocket = new Socket(this.host, this.porta);
		this.input = new ObjectInputStream(cSocket.getInputStream());
		this.output = (OutputStream) cSocket.getOutputStream();
		
	}

	public Boolean login(String login,String senha) throws IOException{
		this.output.write_string("0,"+login+","+senha);  //avisa e manda os parametros para pesquisa para o servidor
		return (this.input.readBoolean());//recebe a resposta do servidor
	}
	
	public void criaUsuario(String id, String senha,String nome,String endereco,String telefone,String email) throws IOException{
		this.output.write_string("1,"+id+","+senha+","+nome+","+endereco+","+telefone+","+email);
		if (this.input.readBoolean()){//recebe a resposta do servidor
			//TODO avisa usuario que já existe id
		}
	}


}
