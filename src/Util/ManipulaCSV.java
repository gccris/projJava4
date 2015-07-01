package Util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import Model.*;

public class ManipulaCSV {
	private String arquivoUsuario;
	private String arquivoProdDesejado;
	private String arquivoProduto;
	
	
	public ManipulaCSV(String arqUser,String arqProduto, String arqProdDesejado){
		this.setArquivoUsuario(arqUser);
		this.setArquivoProduto(arqProduto);
		this.setArquivoProdDesejado(arqProdDesejado);
	}


	public String getArquivoUsuario() {
		return arquivoUsuario;
	}


	public void setArquivoUsuario(String arquivoUsuario) {
		this.arquivoUsuario = arquivoUsuario;
	}


	public String getArquivoProduto() {
		return arquivoProduto;
	}


	public void setArquivoProduto(String arquivoProduto) {
		this.arquivoProduto = arquivoProduto;
	}
	
	public ArrayList<Usuario> loadUsuarios(){
		BufferedReader leitor;
		
		try {
			leitor = new BufferedReader(new FileReader(getArquivoUsuario()));
		} catch(FileNotFoundException ex) {
			return null;	//caso o arquivo nao exista retorna nulo;
		}
		
		ArrayList<Usuario> usuarios = new ArrayList<Usuario>();
		String linhaLida;
		String[] valoresLidos = new String[6];
		
		try {
			while ((linhaLida = leitor.readLine()) != null)
			{
				valoresLidos = linhaLida.split(",");
				usuarios.add(new Usuario(valoresLidos[0],valoresLidos[1],valoresLidos[2],
						  	 valoresLidos[3],valoresLidos[4],valoresLidos[5]));
			}
			
			leitor.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return usuarios;
	}
	
	public ArrayList<Produto> loadProdutos(){
		BufferedReader leitor;
		
		try {
			leitor = new BufferedReader(new FileReader(getArquivoProduto()));
		} catch(FileNotFoundException ex) {
			return null;	//caso o arquivo nao exista retorna nulo;
		}
		
		ArrayList<Produto> produtos = new ArrayList<Produto>();
		String linhaLida;
		String[] valoresLidos = new String[4];
		
		try {
			while ((linhaLida = leitor.readLine()) != null)
			{
				valoresLidos = linhaLida.split(",");
				produtos.add(new Produto(valoresLidos[0],valoresLidos[1],
										 valoresLidos[2],valoresLidos[3],Integer.parseInt(valoresLidos[4])));
			}
			
			leitor.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return produtos;
	}
	
	public ArrayList<ProdDesejados> loadProdDesejado(){
		BufferedReader leitor;
		
		try {
			leitor = new BufferedReader(new FileReader(getArquivoProdDesejado()));
		} catch(FileNotFoundException ex) {
			return null;	//caso o arquivo nao exista retorna nulo;
		}
		
		ArrayList<ProdDesejados> estoque = new ArrayList<ProdDesejados>();
		String linhaLida;
		String[] valoresLidos = new String[2];
		
		try {
			while ((linhaLida = leitor.readLine()) != null)
			{
				valoresLidos = linhaLida.split(",");
				estoque.add(new ProdDesejados(valoresLidos[0],valoresLidos[1]));
			}
			
			leitor.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return estoque;
	}
	
	public Boolean cadastrarUsuario(Usuario user) throws IOException{
		PrintWriter escritor;
		File arquivo;
		/*if(!existeUsuario(user.getId(), user.getSenha())){
			return false;	//usuario ja cadastrado
		}*/

		arquivo = new File(arquivoUsuario);
		if(!arquivo.exists()){
			arquivo.createNewFile();
		}
		
		escritor = new PrintWriter(arquivo);
		escritor.append(user.getId() + "," + user.getSenha() + "," + user.getNome() + "," 
						+ user.getEndereco() + "," + user.getEmail()+ "," 
						+ user.getTelefone() + '\n');
		
		escritor.close();
		return true;
	}


	public String getArquivoProdDesejado() {
		return arquivoProdDesejado;
	}


	public void setArquivoProdDesejado(String arquivoProdDesejado) {
		this.arquivoProdDesejado = arquivoProdDesejado;
	}


	public void atualizaEstoque(String nomeProduto, int quantidade) {
		BufferedReader leitor;
		try {
			leitor = new BufferedReader(new FileReader(getArquivoProduto()));
		
		
		String linhaLida;
		String[] valoresLidos = new String[6];
		

			while ((linhaLida = leitor.readLine()) != null)
			{
				valoresLidos = linhaLida.split(",");
				if(valoresLidos[0].compareTo(nomeProduto) == 0){
				}
			}
			
			leitor.close();
		}catch(FileNotFoundException ex) {}
		catch (IOException e) {
			e.printStackTrace();
		}
	}		
	
}
