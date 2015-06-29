package Threads;

import java.io.*;

import Util.OperacoesCliente;

public class Cliente{
	private BufferedReader entrada;
	private PrintWriter saida;

	
	public Cliente(BufferedReader input,PrintWriter output){
		this.entrada = input;
		this.setSaida(output);
	}
	
	//não é necessário ser socket, pois não precisa ficar recebendo os dados do servidor direto
	/*public void run() {
		 String srvInput;
	        try {
	            while ((srvInput = entrada.readLine()) != null) {
	                if (Thread.interrupted()) {
	                    break;
	                }
	                //TODO SOMETHING   //socket cliente é criado na aplicação,passa por parametro e todas as funcoes que chamam/recebem do servidor é criado aqui
	            }
	        }
	        catch(Exception e) {
	            System.out.println("Something wrong happened!!");
	        }

		
	}*/
	
	public Boolean login(String login,String senha){
		this.getSaida().println("1");  //avisa o servidor que é um login
		this.getSaida().println(login);
		this.getSaida().println(senha);
		try {
			Boolean resultado = OperacoesCliente.usuarioCadastrado(login, senha);
			if(resultado)
				return true; 
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	public PrintWriter getSaida() {
		return saida;
	}

	public void setSaida(PrintWriter saida) {
		this.saida = saida;
	}
	public BufferedReader getEntrada() {
		return entrada;
	}

	public void setEntrada(BufferedReader entrada) {
		this.entrada = entrada;
	}

}
