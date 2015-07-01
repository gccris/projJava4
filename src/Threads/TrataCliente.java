package Threads;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Scanner;

import Executavel.Servidor;

public class TrataCliente implements Runnable {
	
	private ObjectInputStream inCliente;
	private Servidor servidor;
	private ObjectOutputStream outCliente;
	
	public TrataCliente(InputStream inCliente,OutputStream outCliente, Servidor servidor) {
		try {
			this.outCliente = new ObjectOutputStream(outCliente); 
			this.outCliente.flush();
			
			this.inCliente = new ObjectInputStream(inCliente);
			
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		this.servidor = servidor;
	}


	//Thread que trata todas as requisições do cliente. O cliente mandará uma string com um id inicial de sua ação e parametros separados por virgula.
	//A thread reconhecerá o id e mandará esses argumentos para as corretas funções
	@Override
	public void run() {
		String linha;
		try {
			while ((linha = ((String) inCliente.readObject())) != null) {
				String[] parts = linha.split(",");
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
				if(parts[0].compareTo("2") == 0){//RECUPERA LISTA DE PRODUTOS
						try {
							outCliente.writeObject(servidor.getListProdutos());
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
				}
				if(parts[0].compareTo("3") == 0){//RECUPERA LISTA DE PROD DESEJADOS
					try {
						outCliente.writeObject(servidor.loadProdDesejados(parts[1]));
					} catch (IOException e) {e.printStackTrace();}
				}
				if(parts[0].compareTo("4") == 0){//REMOVER PROD DA LISTA DE PROD DESEJADOS
						servidor.removeProdDesejado(parts[1],parts[2]);
				}
				if(parts[0].compareTo("5") == 0){//COMPRAR PRODUTO
					try {
						outCliente.writeObject(servidor.compraProduto(parts[1],Integer.parseInt(parts[2])));
					} catch (NumberFormatException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				try {
					outCliente.flush();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			 }
		} catch (ClassNotFoundException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
