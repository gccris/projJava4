package Threads;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

import org.omg.CORBA.portable.OutputStream;

import Executavel.Servidor;

public class TrataCliente implements Runnable {
	
	private DataInputStream inCliente;
	private Servidor servidor;
	private DataOutputStream outCliente;
	
	public TrataCliente(DataInputStream inCliente,DataOutputStream outCliente, Servidor servidor) {
		this.inCliente = inCliente;
		this.outCliente = outCliente;
		this.servidor = servidor;
	}


	//Thread que trata todas as requisi��es do cliente. O cliente mandar� uma string com um id inicial de sua a��o e parametros separados por virgula.
	//A thread reconhecer� o id e mandar� esses argumentos para as corretas fun��es
	@Override
	public void run() {
		Scanner s = new Scanner(this.inCliente);
		while (s.hasNextLine()) {
			String[] parts = s.nextLine().split(",");
			if(parts[0].compareTo("0") == 0){ //LOGIN
				
				try {
					outCliente.writeBoolean(servidor.Login(parts[1], parts[2]));
				} catch (IOException e) {
					e.printStackTrace();
				}
				
			}
			if(parts[0].compareTo("1") == 0){//CADASTRO NOVO USUARIO
				try {
					outCliente.writeBoolean(servidor.criaUsuario(parts[1],parts[2],parts[3],parts[4],parts[5],parts[6]));
				} catch (IOException e) {e.printStackTrace();}
			}
			if(parts[0].compareTo("2") == 0){//CADASTRO NOVO USUARIO
				
			}
			try {
				outCliente.flush();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	     }
	     s.close();
		
	}

}
