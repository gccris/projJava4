package Util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;

import Model.*;

public class GerenciaSupermecado {

	public static Boolean verificaLogin(String user,String senha,ArrayList<Usuario> listUsers){
		if(listUsers == null)
			return false;
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
					try {
						manipulacaoArq.atualizaEstoque(listProdutos);
					} catch (IOException e) {e.printStackTrace();}
					return "Compra efetuada com sucesso";
				}
				else
					return "Não há produto suficiente no estoque";
			}
		}
		return "Compra não efetuada";
	}
	
	public static Usuario getUsuarioPorID(String ID, ArrayList<Usuario> listUsuarios){
		for(Usuario u: listUsuarios){
			if(u.getId().compareTo(ID)==0)
				return u;
		}
		return null;
	}
	
	public static void mandaEmailClientes(Produto p, ArrayList<ProdDesejados> listProdDesejados,ArrayList<Usuario> listUsuarios){
		for(ProdDesejados pDesejado : listProdDesejados){
			if(p.getNome().compareTo(pDesejado.getNomeProduto())==0){
				Usuario u = getUsuarioPorID(pDesejado.getIdUsuario(),listUsuarios);
				if(u != null){
					Properties prop = System.getProperties();
					prop.setProperty("mail.smtp.host", "localhost");
					//Session session = Session.getDefaultInstance(prop);
				}
					
			}
		}
	}
}
