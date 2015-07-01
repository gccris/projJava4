package Executavel;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import Model.*;
import Threads.TrataCliente;
import Util.GerenciaSupermecado;
import Util.ManipulaCSV;



public class Servidor extends Application{//aqui sera também uma aplication, fazer tela aqui
	private int porta;
	private List<PrintStream> clientes;
	private ArrayList<Usuario> listUsuarios;
	private ArrayList<Produto> listProdutos;
	private ArrayList<ProdDesejados> listProdDesejados;
	Stage window;
	Scene cadScene, prodScene;
	ArrayList<Produto> cartListProd = new ArrayList<>();
	ObservableList<Produto> cartList;
	ObservableList<Produto> prodList = FXCollections.observableArrayList();
	
	public ArrayList<ProdDesejados> getListProdDesejados() {
		return listProdDesejados;
	}
	public void setListProdDesejados(ArrayList<ProdDesejados> listProdDesejados) {
		this.listProdDesejados = listProdDesejados;
	}
	private static ManipulaCSV manipulacaoArquivos;
	
	public static void main(String[] args) throws IOException {
	     // inicia o servidor
		manipulacaoArquivos = new ManipulaCSV("usuarios.csv", "produtos.csv", "prodDesejados.csv", "vendas.csv");
		launch(args);
   }

	private void executa(){
		ServerSocket servidor;
		this.porta = 12345;
	    this.clientes = new ArrayList<PrintStream>();
	    this.listUsuarios = manipulacaoArquivos.loadUsuarios();
	    this.setListProdutos(manipulacaoArquivos.loadProdutos());
	    this.listProdDesejados = manipulacaoArquivos.loadProdDesejado();
		try {
			servidor = new ServerSocket(this.porta);

			Runnable acceptClients = () -> { //thread para ficar aceitando os usuarios
				while(true){
					Socket cliente;
					try {
						cliente = servidor.accept();
				        // adiciona saida do cliente à lista
				        PrintStream ps = new PrintStream(cliente.getOutputStream());
				        this.clientes.add(ps);
			       
				       // cria tratador de cliente numa nova thread
				        TrataCliente tc = 										//abre uma thread para receber as informações do cliente
				           new TrataCliente(cliente.getInputStream(),cliente.getOutputStream(), this);
				        new Thread(tc).start();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			};
		new Thread(acceptClients).start();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public Boolean Login(String user,String senha){
		 return GerenciaSupermecado.verificaLogin(user,senha,listUsuarios);
	}
	
	public Boolean criaUsuario(String id, String senha,String nome,String endereco,String telefone,String email) throws IOException{
		if(GerenciaSupermecado.existeUsuario(id, listUsuarios))
			return false;
		else{
			Usuario u = new Usuario(id, senha, nome, endereco, telefone, email);
			listUsuarios.add(u);
			manipulacaoArquivos.cadastrarUsuario(u);
			return true;
		}
			
	}
	public ArrayList<Produto> getListProdutos() {
		return listProdutos;
	}
	
	public void setListProdutos(ArrayList<Produto> listProdutos) {
		this.listProdutos = listProdutos;
	}
	
	public ArrayList<ProdDesejados> loadProdDesejados(String idCliente) {
		return GerenciaSupermecado.loadProdDesejados(idCliente,listProdDesejados);
	}
	
	public void removeProdDesejado(String idCliente, String nomeProduto) throws IOException {
		ProdDesejados pRemover = new ProdDesejados(idCliente, nomeProduto);
		GerenciaSupermecado.removeProdDesejado(pRemover,listProdDesejados);
		manipulacaoArquivos.adicionaListaDesejos(listProdDesejados);//refaz o arquivo de desejos
	}
	
	public String compraProduto(String nomeUsuario, String nomeProduto,int quantidade) throws IOException {
		return GerenciaSupermecado.compraProduto(nomeUsuario,nomeProduto,quantidade,listProdutos,manipulacaoArquivos);
	}
	
	public void requisitarNotificacao(String idCliente, String nomeProduto) throws IOException {
		GerenciaSupermecado.requisitarNotificacao(idCliente,nomeProduto,listProdDesejados);
		manipulacaoArquivos.adicionaDesejo(new ProdDesejados(idCliente, nomeProduto));
	}
	
	public void cadastraNovoProduto(String nome,String preco,String validade,String fornecedor, int quantidade){
		Produto p = new Produto(nome,preco,validade,fornecedor,quantidade);
		listProdutos.add(p);
		try {
			manipulacaoArquivos.adicionaProduto(p);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void gerarRelatorio(int mes){
		//TODO gera relatorio com as vendas de determinado mes
	}
	
	public void start(Stage primaryStage) throws Exception{
		executa();
		atualizaTabelaProduto();
		window = primaryStage;
		
		//	PRODUTOS	XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
		HBox prodBar = new HBox(7);
		prodBar.setMinHeight(30);
		prodBar.setStyle("-fx-background-color: #336699;");
		prodBar.setAlignment(Pos.CENTER_RIGHT);
		
		 
		TableView<Produto> prodTable = new TableView<>();
		TableColumn tcProdName = new TableColumn("PRODUTO");
		tcProdName.setMinWidth(300);
		tcProdName.setMaxWidth(300);
		tcProdName.setCellValueFactory(
				new PropertyValueFactory<>("nome"));
		TableColumn tcProdValor = new TableColumn("VALOR");
		tcProdValor.setMinWidth(100);
		tcProdValor.setMinWidth(100);
		tcProdValor.setCellValueFactory(
				new PropertyValueFactory<>("preço"));
		TableColumn tcProdQtd = new TableColumn("QTD");
		tcProdQtd.setMinWidth(100);
		tcProdQtd.setMinWidth(100);
		tcProdQtd.setCellValueFactory(
				new PropertyValueFactory<>("quantidade"));
		prodTable.getColumns().addAll(tcProdName,tcProdValor, tcProdQtd);
		prodTable.setItems(prodList);
		
		Button prodAtt = new Button("ATUALIZAR");
		prodAtt.setDisable(true);
		TextField prodAttVal = new TextField();
		prodAttVal.setPromptText("Qtde");
		
		Label prodInfoText = new Label("INFORMAÇÕES DO PRODUTO");
		Label prodInfoName = new Label("Produto:");
		Label prodInfoForn = new Label("Fornecedor:");
		Label prodInfoValid = new Label("Validade:");
		Label prodInfoValue = new Label("Valor:");
		prodTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
		prodTable.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
			if(prodTable.getSelectionModel().getSelectedItem() != null){
				prodInfoName.setText("Produto: " + newValue.getNome());
				prodInfoForn.setText("Fornecedor: " + newValue.getFornecedor());
				prodInfoValid.setText("Validade: " + newValue.getValidade());
				prodInfoValue.setText("Valor: R$" + newValue.getPreço());
				prodAtt.setDisable(false);
				prodAtt.setOnAction(e -> {
					atualizaEstoque(newValue,prodAttVal.getText());
				});
			}
		});
		
		VBox prodInfo = new VBox(7);
		prodInfo.getChildren().addAll(prodInfoText, prodInfoName, prodInfoForn, prodInfoValid, prodInfoValue, prodAttVal, prodAtt);
		
		HBox prodAll = new HBox(7);
		prodAll.getChildren().addAll(prodTable, prodInfo);
		
		TextField prodSearchTF = new TextField();
		Button prodSearchBT = new Button("PROCURAR");
		HBox prodSearch = new HBox(7);
		prodSearch.getChildren().addAll(prodSearchTF, prodSearchBT);
		CheckBox prodCheckBox = new CheckBox();
		Label prodCheckLB = new Label("Em falta");
		HBox prodCheck = new HBox(7);
		prodCheck.getChildren().addAll(prodCheckLB, prodCheckBox);
		
		Button prodCad = new Button("NOVO PRODUTO");
		
		VBox layoutProd = new VBox(7);
		layoutProd.getChildren().addAll(prodBar, prodSearch, prodCheck, prodAll, prodCad);
		layoutProd.setAlignment(Pos.TOP_CENTER);
		
		//	CADASTRO	XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
		Label cadNameLB = new Label("Nome:");
		TextField cadNameTF = new TextField();
		HBox cadName = new HBox(7);
		cadName.getChildren().addAll(cadNameLB, cadNameTF);
		cadName.setAlignment(Pos.CENTER);
		Label cadFornLB = new Label("Fornecedor:");
		TextField cadFornTF = new TextField();
		HBox cadForn = new HBox(7);
		cadForn.getChildren().addAll(cadFornLB, cadFornTF);
		cadForn.setAlignment(Pos.CENTER);
		Label cadValidLB = new Label("Validade:");
		TextField cadValidTF = new TextField();
		HBox cadValid = new HBox(7);
		cadValid.getChildren().addAll(cadValidLB, cadValidTF);
		cadValid.setAlignment(Pos.CENTER);
		Label cadValueLB = new Label("Valor:");
		TextField cadValueTF = new TextField();
		HBox cadValue = new HBox(7);
		cadValue.getChildren().addAll(cadValueLB, cadValueTF);
		cadValue.setAlignment(Pos.CENTER);
		
		Button cadOK = new Button("CADASTRAR");
		Button cadCancel = new Button("CANCELAR");
		HBox cadButtons = new HBox(7);
		cadButtons.getChildren().addAll(cadCancel, cadOK);
		cadButtons.setAlignment(Pos.CENTER);
		
		VBox layoutCad = new VBox(7);
		layoutCad.getChildren().addAll(cadName, cadForn, cadValid, cadValue, cadButtons);
		layoutCad.setAlignment(Pos.CENTER);
		
		//	BUTTONS	XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
		prodCad.setOnAction(e->{
			window.setScene(cadScene);
		});
		
		cadOK.setOnAction(e->{
			this.cadastraNovoProduto(cadNameTF.getText(),cadValueTF.getText() ,cadValidTF.getText(), cadFornTF.getText(), 0);
			atualizaTabelaProduto();
			window.setScene(prodScene);
			JOptionPane.showMessageDialog(null, "Produto: "+cadNameTF.getText()+" cadastrado");
		});
		cadCancel.setOnAction(e->{
			window.setScene(prodScene);
		});
		
		
		//-------------------------
		
		
		prodScene = new Scene(layoutProd, 600, 600);
		cadScene = new Scene(layoutCad, 600, 600);
		window.setScene(prodScene);
		window.setTitle("SUPERMERCADO DOS PARÇA");
		window.show();
	}
	
	private void atualizaEstoque(Produto p, String quantidade) {
		p.setQuantidade(Integer.parseInt(quantidade));
		try {
			manipulacaoArquivos.atualizaEstoque(listProdutos);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		atualizaTabelaProduto();
	}
	public void atualizaTabelaProduto(){
		prodList.clear();
		for(Produto p:listProdutos)
			prodList.add(p);
	}
}
