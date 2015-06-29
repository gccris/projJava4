package Util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

import Model.Usuario;

public class OperacoesCliente {

	public static Boolean usuarioCadastrado(String id, String senha) throws IOException{
		BufferedReader leitor = new BufferedReader(new FileReader("usuarios.csv"));
		String linhaLida;
		String[] valoresLidos = new String[6];
		
		while ((linhaLida = leitor.readLine()) != null){
			valoresLidos = linhaLida.split(",");
			if(valoresLidos[0].equals(id))
			{
				leitor.close();
				return valoresLidos[1].equals(senha);
			}
		}
		leitor.close();
		return false;
	}
	
	public static Boolean cadastrarUsuario(Usuario user) throws IOException{
		PrintWriter escritor;
		File arquivo;
		if(usuarioCadastrado(user.getId(), user.getSenha())){
			return false;	//usuario ja cadastrado
		}

		arquivo = new File("usuarios.csv");
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
