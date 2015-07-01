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

	public static void removeProdDesejado(ProdDesejados pRemover,ArrayList<ProdDesejados> listProdDesejados) {
		listProdDesejados.remove(pRemover);
	}

	public static String compraProduto(String nomeProduto,int quantidade,ArrayList<Produto> listProdutos,ManipulaCSV manipulacaoArq) {
		for(Produto p: listProdutos){
			if(p.getNome().compareTo(nomeProduto)==0){
				if(p.getQuantidade()>=quantidade){
					p.setQuantidade(p.getQuantidade()-quantidade);
					manipulacaoArq.atualizaEstoque(nomeProduto,p.getQuantidade()-quantidade);
					return "Compra efetuada com sucesso";
				}
				else
					return "N�o h� produto suficiente no estoque";
			}
		}
		return "Compra n�o efetuada";
	}
}
