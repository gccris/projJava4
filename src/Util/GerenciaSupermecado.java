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
}
