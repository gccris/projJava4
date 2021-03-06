package Util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;

import Model.*;

//Classe responsavel pelas funcoes de escrita e leitura nos arquivos csv
public class ManipulaCSV {
	
	private String arquivoUsuario;
	private String arquivoProdDesejado;
	private String arquivoProduto;
	private String arquivoVendas;
	
	//Construtor com todos os campos
	public ManipulaCSV(String arqUser,String arqProduto, String arqProdDesejado,String arquivoVendas){
		this.setArquivoUsuario(arqUser);
		this.setArquivoProduto(arqProduto);
		this.setArquivoProdDesejado(arqProdDesejado);
		this.setArquivoVendas(arquivoVendas);
	}
	
	//Getters e setters
	public String getArquivoVendas() {
		return arquivoVendas;
	}

	public void setArquivoVendas(String arquivoVendas) {
		this.arquivoVendas = arquivoVendas;
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
	
	//Le o arquivo de usuarios e retorna os registros 
	public ArrayList<Usuario> loadUsuarios(){
		BufferedReader leitor;
		
		try {
			leitor = new BufferedReader(new FileReader(getArquivoUsuario()));
		} catch(FileNotFoundException ex) {
			return new ArrayList<Usuario>();	//caso o arquivo nao exista retorna lista vazia;
		}
		
		ArrayList<Usuario> usuarios = new ArrayList<Usuario>();
		String linhaLida;
		String[] valoresLidos = new String[6];
		
		try {
			while ((linhaLida = leitor.readLine()) != null) //adiciona os usuarios na lista
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
	
	//Le o arquivo de produtos e retorna os registros 
	public ArrayList<Produto> loadProdutos(){
		BufferedReader leitor;
		
		try {
			leitor = new BufferedReader(new FileReader(getArquivoProduto()));
		} catch(FileNotFoundException ex) {
			return new ArrayList<Produto>();	//caso o arquivo nao exista retorna lista vazia;
		}
		
		ArrayList<Produto> produtos = new ArrayList<Produto>();
		String linhaLida;
		String[] valoresLidos = new String[4];
		
		try {
			while ((linhaLida = leitor.readLine()) != null)	//adiciona os produtos nalista
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
			return new ArrayList<ProdDesejados>();	//caso o arquivo nao exista retorna lista vazia;
		}
		
		ArrayList<ProdDesejados> estoque = new ArrayList<ProdDesejados>();
		String linhaLida;
		String[] valoresLidos = new String[2];
		
		try {
			while ((linhaLida = leitor.readLine()) != null)	//retorna lista de produtos desejados
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
		PrintStream escritor;
		File arquivo;

		arquivo = new File(arquivoUsuario);
		if(!arquivo.exists()){	//caso o arquivo nao exista, cria o arquivo
			arquivo.createNewFile();
		}
		
		escritor = new PrintStream(new FileOutputStream(arquivo,true));
		escritor.println(user.getId() + "," + user.getSenha() + "," + user.getNome() + "," 
						+ user.getEndereco() + "," + user.getEmail()+ "," 
						+ user.getTelefone());	//escreve os dados do usuario no arquivo
		
		escritor.close();
		return true;
	}
	
	public void registraVenda(String nomeUsuario,Produto produto, int quantidade) throws IOException{
		PrintStream escritor;
		File arquivo;
		Date d = new Date();
		String dataFormat = (d.getMonth()+1) + "/" + d.getDate() + "/" + d.getYear();
		
		arquivo = new File(arquivoVendas);
		if(!arquivo.exists()){
			arquivo.createNewFile();
		}
		
		escritor = new PrintStream(new FileOutputStream(arquivo,true));
		escritor.println(nomeUsuario + "," + produto.getNome() + "," + quantidade + ","+dataFormat);
		//escreve os dados da venda no arquivo
		
		
		escritor.close();
	}
	
	public ArrayList<Venda> loadVendas() {
		BufferedReader leitor;
		Date dataLida;
		try {
			leitor = new BufferedReader(new FileReader(getArquivoVendas()));
		} catch(FileNotFoundException ex) {
			return null;	//caso o arquivo nao exista retorna nulo;
		}
		
		ArrayList<Venda> vendas = new ArrayList<Venda>();
		String linhaLida;
		String[] valoresLidos = new String[4];
		
		try {
			while ((linhaLida = leitor.readLine()) != null)
			{
				valoresLidos = linhaLida.split(",");
				dataLida = new Date(valoresLidos[3]);
				vendas.add(new Venda(valoresLidos[0],valoresLidos[1],
									Integer.parseInt(valoresLidos[2]),dataLida));
			}
			
			leitor.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return vendas;
	}


	public String getArquivoProdDesejado() {
		return arquivoProdDesejado;
	}


	public void setArquivoProdDesejado(String arquivoProdDesejado) {
		this.arquivoProdDesejado = arquivoProdDesejado;
	}

	//Quando um produto � vendido esta funcao � resposavel por atualizar o arquivo de produtos estoque
	public void atualizaEstoque(ArrayList<Produto> listProdutos) throws IOException {
		PrintStream escritor;
		File arquivo;

		arquivo = new File(arquivoProduto);
		if(!arquivo.exists()){
			arquivo.createNewFile();
		}
		
		escritor = new PrintStream(arquivo);
		for(Produto p:listProdutos) 
			escritor.println(p.getNome()+ "," + p.getPre�o() + "," + p.getValidade() + "," 
							+ p.getFornecedor() + "," + p.getQuantidade());
		
		escritor.close();
	}
	
	//Adiciona desejo na lista de produtos desejados a receber notificacao
	public void adicionaDesejo(ProdDesejados p) throws IOException {
		PrintStream escritor;
		File arquivo;

		arquivo = new File(arquivoProdDesejado);
		if(!arquivo.exists()){
			arquivo.createNewFile();
		}
		
		escritor = new PrintStream(new FileOutputStream(arquivo,true));	//escreve no fim do arquivo
		escritor.println(p.getIdUsuario()+ "," + p.getNomeProduto());
		
		escritor.close();
	}

	//Adiciona produto na lista de produtos 
	public void adicionaProduto(Produto p) throws IOException {
		PrintStream escritor;
		File arquivo;

		arquivo = new File(arquivoProduto);
		if(!arquivo.exists()){
			arquivo.createNewFile();
		}
		
		escritor = new PrintStream(new FileOutputStream(arquivo,true));
		escritor.println(p.getNome()+ "," + p.getPre�o() + "," + p.getValidade() + "," 
				+ p.getFornecedor() + "," + p.getQuantidade());
		
		escritor.close();
	}

	//Escreve a lista de desejos no arquivo
	public void adicionaListaDesejos(ArrayList<ProdDesejados> listProdDesejados) throws IOException {
		PrintStream escritor;
		File arquivo;

		arquivo = new File(arquivoProdDesejado);
		if(!arquivo.exists()){
			arquivo.createNewFile();
		}
		
		escritor = new PrintStream(arquivo);
		for(ProdDesejados p:listProdDesejados) 
			escritor.println(p.getIdUsuario()+","+p.getNomeProduto());
		
		escritor.close();
		
	}
	
}
