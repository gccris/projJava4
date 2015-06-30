package Util;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import Model.Usuario;

public class ManipulaCSV {
	private String arquivoUsuario;
	private String arquivoEstoque;
	private String arquivoProduto;
	
	
	public ManipulaCSV(String arqUser,String arqProduto, String arqEstoque){
		this.setArquivoUsuario(arqUser);
		this.setArquivoProduto(arqProduto);
		this.setArquivoEstoque(arqEstoque);
	}


	public String getArquivoUsuario() {
		return arquivoUsuario;
	}


	public void setArquivoUsuario(String arquivoUsuario) {
		this.arquivoUsuario = arquivoUsuario;
	}


	public String getArquivoEstoque() {
		return arquivoEstoque;
	}


	public void setArquivoEstoque(String arquivoEstoque) {
		this.arquivoEstoque = arquivoEstoque;
	}


	public String getArquivoProduto() {
		return arquivoProduto;
	}


	public void setArquivoProduto(String arquivoProduto) {
		this.arquivoProduto = arquivoProduto;
	}
	
	public ArrayList<Usuario> loadUsuarios(){
		return null;
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
	
}
