package Util;

import java.io.IOException;
import java.util.ArrayList;

import Model.*;

public class GerenciaSupermecado {

	
	//verifica se o login do usuario está correto
	public static Boolean verificaLogin(String user,String senha,ArrayList<Usuario> listUsers){
		if(listUsers == null)
			return false;
		for(Usuario u : listUsers){
			if(u.getId().compareTo(user) == 0 && u.getSenha().compareTo(senha) == 0)
				return true;
		}
		return false;
	}
	
	//Verifica se existe usuario na lista de usuarios
	public static Boolean existeUsuario(String user,ArrayList<Usuario> listUsers){
		for(Usuario u : listUsers){
			if(u.getId().compareTo(user) == 0)
				return true;
		}
		return false;
	}

	//Verifica se produto esta na lista de desejados
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

	//Realiza a compra do produto atualizando o arquivo de Vendas e de Produtos
	public static String compraProduto(String nomeUsuario,String nomeProduto,int quantidade,
									   ArrayList<Produto> listProdutos,ManipulaCSV manipulacaoArq) throws IOException {
		for(Produto p: listProdutos){
			if(p.getNome().compareTo(nomeProduto)==0){
				if(p.getQuantidade()>=quantidade){
					p.setQuantidade(p.getQuantidade()-quantidade);
					manipulacaoArq.atualizaEstoque(listProdutos);
					manipulacaoArq.registraVenda(nomeUsuario,p,quantidade);
					return "Compra efetuada com sucesso";
				}
				else
					return "Não há produto suficiente no estoque";
			}
		}
		return "Compra não efetuada";
	}

	//Adiciona na lista de desejos um novo produto desejado a receber notificacao
	public static void requisitarNotificacao(String idCliente,String nomeProduto, ArrayList<ProdDesejados> listProdDesejados) {
		listProdDesejados.add(new ProdDesejados(idCliente, nomeProduto));
	}
}
