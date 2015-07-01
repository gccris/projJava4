package Util;

import java.util.ArrayList;

import Model.*;

public class GerenciaSupermecado {

	public static Boolean verificaLogin(String user,String senha,ArrayList<Usuario> listUsers){
		for(Usuario u : listUsers){
			if(u.getId().compareTo(user) == 0 && u.getSenha().compareTo(senha) == 0)
				return true;
		}
		return false;
	}
	
	public static Boolean existeUsuario(String user,ArrayList<Usuario> listUsers){
		for(Usuario u : listUsers){
			if(u.getId().compareTo(user) == 0)
				return true;
		}
		return false;
	}

	public static ArrayList<ProdDesejados> loadProdDesejados(String idCliente,ArrayList<ProdDesejados> listProdDesejados) {
		ArrayList<ProdDesejados> produtosDesejados = new ArrayList<ProdDesejados>();
		for(ProdDesejados p: listProdDesejados){
			if(p.getIdUsuario().compareTo(idCliente) == 0)
				produtosDesejados.add(p);
		}
		return produtosDesejados;
	}
}
