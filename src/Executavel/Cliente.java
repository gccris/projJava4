package Executavel;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

import org.omg.CORBA.portable.OutputStream;

import Model.ProdDesejados;
import Model.Produto;

public class Cliente{
    private String host;
    private int porta;
	private ObjectInputStream input;
	private ObjectOutputStream output;
	private ArrayList<Produto> listProdutos;
	private ArrayList<ProdDesejados> listProdDesejados;
	private String idCliente;
	
	public Cliente(String host, int porta) {
		this.host = host;
		this.porta = porta;
		this.listProdutos = null;
	}

	public static void main(String[] args) throws UnknownHostException, IOException{
	     // dispara cliente
	     new Cliente("127.0.0.1", 12345).executa();
   }
	
	private void executa() throws UnknownHostException, IOException {
		Socket cSocket = new Socket(this.host, this.porta);
		this.input = new ObjectInputStream(cSocket.getInputStream());
		this.output = new ObjectOutputStream(cSocket.getOutputStream());
		
		//this.output.writeObject("header");
		//this.output.flush();
		//chama parte visual
		
		login("gustavo","gustavo");
	}

	public void login(String login,String senha) throws IOException{
		this.output.writeObject(new String("0,"+login+","+senha));  //avisa e manda os parametros para pesquisa para o servidor
		this.output.flush();
		
		if (this.input.readBoolean()){//recebe a resposta do servidor
			this.idCliente = login;
		}
			//TODO avisa que logou
	}
	
	public void criaUsuario(String id, String senha,String nome,String endereco,String telefone,String email) throws IOException{
		this.output.writeObject(new String("1,"+id+","+senha+","+nome+","+endereco+","+telefone+","+email));
		this.output.flush();
		if (this.input.readBoolean()){//recebe a resposta do servidor
			//TODO avisa usuario que já existe id
		}
	}
	
	public void loadListProdutos() throws IOException{
			this.output.writeChars("2");
			this.output.flush();
			try {
				listProdutos = (ArrayList<Produto>) this.input.readObject();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
	
	}
	
	public void loadListProdDesejados() throws IOException{
		this.output.writeChars("3,"+idCliente);
		this.output.flush();
		try {
			listProdDesejados = (ArrayList<ProdDesejados>) this.input.readObject();
			for(ProdDesejados lp:listProdDesejados){
				for(Produto p:listProdutos){
					if(p.getNome().compareTo(lp.getNomeProduto()) == 0){
						//TODO ALERT;
						this.output.writeChars("4,"+idCliente+","+lp.getNomeProduto());//remove o produto desejado da lista do cliente no servidor
						this.output.flush();
					}
				}
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
	}
	
	public void compraProduto(Produto p) throws IOException{
		this.output.writeChars("5,"+p.getNome());
		this.output.flush();
		
	}


}