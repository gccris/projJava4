package Executavel;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import Model.*;
import Threads.TrataCliente;
import Util.GerenciaSupermecado;
import Util.ManipulaCSV;



public class Servidor{//aqui sera também uma aplication, fazer tela aqui
	private int porta;
	private List<PrintStream> clientes;
	private ArrayList<Usuario> listUsuarios;
	private ArrayList<Produto> listProdutos;
	private ArrayList<ProdDesejados> listProdDesejados;
	public ArrayList<ProdDesejados> getListProdDesejados() {
		return listProdDesejados;
	}
	public void setListProdDesejados(ArrayList<ProdDesejados> listProdDesejados) {
		this.listProdDesejados = listProdDesejados;
	}
	private static ManipulaCSV manipulacaoArquivos;
	
	public static void main(String[] args) throws IOException {
	     // inicia o servidor
		manipulacaoArquivos = new ManipulaCSV("usuarios.csv", "produtos,csv", "prodDesejados.csv");
	    new Servidor(12345).executa();
   }
	public Servidor(int porta) {
		this.porta = porta;
	    this.clientes = new ArrayList<PrintStream>();
	    this.listUsuarios = manipulacaoArquivos.loadUsuarios();
	    this.setListProdutos(manipulacaoArquivos.loadProdutos());
	    this.listProdDesejados = manipulacaoArquivos.loadProdDesejado();
	    //TODO carregar lists
	}
	
	private void executa(){
		ServerSocket servidor;
		try {
			servidor = new ServerSocket(this.porta);

			Runnable acceptClients = () -> { //thread para ficar aceitando os usuarios
				while(true){
					Socket cliente;
					try {
						cliente = servidor.accept();
				        // adiciona saida do cliente à lista
				        PrintStream ps = new PrintStream(cliente.getOutputStream());
				        this.clientes.add(ps);
			       
				       // cria tratador de cliente numa nova thread
				        TrataCliente tc = 										//abre uma thread para receber as informações do cliente
				           new TrataCliente(new DataInputStream(cliente.getInputStream()), new ObjectOutputStream(cliente.getOutputStream()), this);
				        new Thread(tc).start();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			};
		new Thread(acceptClients).start();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//TODO chama a parte visual
	}
	
	public Boolean Login(String user,String senha){
		 return GerenciaSupermecado.verificaLogin(user,senha,listUsuarios);
	}
	
	public Boolean criaUsuario(String id, String senha,String nome,String endereco,String telefone,String email) throws IOException{
		if(GerenciaSupermecado.existeUsuario(id, listUsuarios))
			return false;
		else{
			Usuario u = new Usuario(id, senha, nome, endereco, telefone, email);
			listUsuarios.add(u);
			manipulacaoArquivos.cadastrarUsuario(u);
			return true;
		}
			
	}
	public ArrayList<Produto> getListProdutos() {
		return listProdutos;
	}
	public void setListProdutos(ArrayList<Produto> listProdutos) {
		this.listProdutos = listProdutos;
	}
	public ArrayList<ProdDesejados> loadProdDesejados(String idCliente) {
		return GerenciaSupermecado.loadProdDesejados(idCliente,listProdDesejados);
	}
	public void removeProdDesejado(String idCliente, String nomeProduto) {
		ProdDesejados pRemover = new ProdDesejados(idCliente, nomeProduto);
		GerenciaSupermecado.removeProdDesejado(pRemover,listProdDesejados);
	}
	public String compraProduto(String nomeProduto,int quantidade) {
		return GerenciaSupermecado.compraProduto(nomeProduto,quantidade,listProdutos,manipulacaoArquivos);
	}
}
