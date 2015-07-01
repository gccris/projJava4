package Executavel;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

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
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import org.omg.CORBA.portable.ApplicationException;
import org.omg.CORBA.portable.OutputStream;

import com.sun.jmx.snmp.InetAddressAcl;

import Model.ProdDesejados;
import Model.Produto;

public class Cliente extends Application{
    private String host;
    private int porta;
	private ObjectInputStream input;
	private ObjectOutputStream output;
	private ArrayList<Produto> listProdutos;
	private ArrayList<ProdDesejados> listProdDesejados;
	ArrayList<Produto> cartListProd = new ArrayList<>();
	private String idCliente;
	Stage window;
	Scene loginScene, cadScene, prodScene, cartScene;
	ObservableList<ProdDesejados> cartList = FXCollections.observableArrayList();
	ObservableList<Produto> prodList = FXCollections.observableArrayList();
	
	public static void main(String[] args) throws UnknownHostException, IOException{
	     // dispara cliente
		launch(args);
   }
	
	private void executa() throws UnknownHostException, IOException {
		this.porta = 12345;
		this.listProdutos = new ArrayList<Produto>();
		this.listProdDesejados = new ArrayList<ProdDesejados>();
		Socket cSocket = new Socket(InetAddress.getLocalHost(), this.porta);
		this.input = new ObjectInputStream(cSocket.getInputStream());
		this.output = new ObjectOutputStream(cSocket.getOutputStream());	
	}

	public Boolean login(String login,String senha) throws IOException{
		this.output.writeObject(new String("0,"+login+","+senha));  //avisa e manda os parametros para pesquisa para o servidor
		this.output.flush();
		
		if (this.input.readBoolean()){//recebe a resposta do servidor
			this.idCliente = login;
			return true;
		}
			return false;
	}
	
	public void criaUsuario(String id, String senha,String nome,String endereco,String telefone,String email) throws IOException{
		this.output.writeObject(new String("1,"+id+","+senha+","+nome+","+endereco+","+telefone+","+email));
		this.output.flush();
		if (!this.input.readBoolean())//recebe a resposta do servidor
			JOptionPane.showMessageDialog(null, "Usuario ja existe");
		else
			JOptionPane.showMessageDialog(null, "Usuario cadastrado");
	}
	
	public void loadListProdutos() throws IOException{
			this.output.writeObject(new String("2"));
			this.output.flush();
			try {
				listProdutos = (ArrayList<Produto>) this.input.readObject();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
	
	}
	
	public void requisitarNotificacao(Produto p) throws IOException{
		this.output.writeObject(new String("6,"+idCliente+","+p.getNome()));
		this.output.flush();
	}
	
	public void loadListProdDesejados() throws IOException{
		this.output.writeObject(new String("3,"+idCliente));
		this.output.flush();
		try {
			listProdDesejados = (ArrayList<ProdDesejados>) this.input.readObject();
			verificaListaProdDesejados();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
	}
	public void verificaListaProdDesejados(){
		for(ProdDesejados lp:listProdDesejados){
			for(Produto p:listProdutos){
				if(p.getNome().compareTo(lp.getNomeProduto()) == 0){
					if(p.getQuantidade()>0){
						JOptionPane.showMessageDialog(null, "Produto: "+lp.getNomeProduto()+" disponivel!");
						try {
							this.output.writeObject(new String("4,"+idCliente+","+lp.getNomeProduto()));//remove o produto desejado da lista do cliente no servidor
							this.output.flush();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			}
		}
	}
	
	public void compraProduto(Produto p,String quantidade) throws IOException{
		this.output.writeObject(new String("5,"+p.getNome()+","+quantidade));
		this.output.flush();
		String retorno;
		try {
			retorno = (String) this.input.readObject();
			JOptionPane.showMessageDialog(null, retorno);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	
	/*-/-/-/-/-/-/-/-/-/-/-/-/-/-/-/-//-/-/-/-/PARTE VISUAL-/-/-/-/-/-/-/-/-/-/-/-/-/-/-/-/-/-/*/
	@Override
	public void start(Stage primaryStage) throws Exception{
		executa();
		window = primaryStage;
		//	LOGIN	XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
		Image img = new Image("http://www.productcart.com/images/slides/cart.png");
		ImageView imgView = new ImageView();
		imgView.setImage(img);
		imgView.setFitWidth(300);
		imgView.setPreserveRatio(true);
		
		Label loginUserLB = new Label("Login:");
		TextField loginUserTF = new TextField();
		HBox loginUser = new HBox(7);
		loginUser.getChildren().addAll(loginUserLB, loginUserTF);
		loginUser.setAlignment(Pos.CENTER);
		Label loginPassLB = new Label("Senha:");
		TextField loginPassTF = new TextField();
		HBox loginPass = new HBox(7);
		loginPass.getChildren().addAll(loginPassLB, loginPassTF);
		loginPass.setAlignment(Pos.CENTER);
		
		Button loginOK = new Button("ENTRAR");
		Button loginCad = new Button("CADASTRO");
		HBox loginButtons = new HBox (7);
		loginButtons.getChildren().addAll(loginCad, loginOK);
		loginButtons.setAlignment(Pos.CENTER);
		
		VBox layoutLogin = new VBox(15);
		layoutLogin.getChildren().addAll(imgView, loginUser, loginPass, loginButtons);
		layoutLogin.setAlignment(Pos.CENTER);
		
		//	CADASTRO	XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
		Label cadUserLB = new Label("Login:");
		TextField cadUserTF = new TextField();
		HBox cadUser = new HBox(7);
		cadUser.getChildren().addAll(cadUserLB, cadUserTF);
		cadUser.setAlignment(Pos.CENTER);
		Label cadPassLB = new Label("Senha:");
		TextField cadPassTF = new TextField();
		HBox cadPass = new HBox(7);
		cadPass.getChildren().addAll(cadPassLB, cadPassTF);
		cadPass.setAlignment(Pos.CENTER);
		Label cadNameLB = new Label("Nome:");
		TextField cadNameTF = new TextField();
		HBox cadName = new HBox(7);
		cadName.getChildren().addAll(cadNameLB, cadNameTF);
		cadName.setAlignment(Pos.CENTER);
		Label cadAddressLB = new Label("Endereço:");
		TextField cadAddressTF = new TextField();
		HBox cadAddress = new HBox(7);
		cadAddress.getChildren().addAll(cadAddressLB, cadAddressTF);
		cadAddress.setAlignment(Pos.CENTER);
		Label cadPhoneLB = new Label("Telefone:");
		TextField cadPhoneTF = new TextField();
		HBox cadPhone = new HBox(7);
		cadPhone.getChildren().addAll(cadPhoneLB, cadPhoneTF);
		cadPhone.setAlignment(Pos.CENTER);
		Label cadEmailLB = new Label("Email:");
		TextField cadEmailTF = new TextField();
		HBox cadEmail = new HBox(7);
		cadEmail.getChildren().addAll(cadEmailLB, cadEmailTF);
		cadEmail.setAlignment(Pos.CENTER);
		
		Button cadOK = new Button("CADASTRAR");
		Button cadCancel = new Button("CANCELAR");
		HBox cadButtons = new HBox(7);
		cadButtons.getChildren().addAll(cadCancel, cadOK);
		cadButtons.setAlignment(Pos.CENTER);
		
		VBox layoutCad = new VBox(7);
		layoutCad.getChildren().addAll(cadUser, cadPass, cadName, cadAddress, cadPhone, cadEmail, cadButtons);
		layoutCad.setAlignment(Pos.CENTER);
		
		//	PRODUTOS	XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
		Label prodLogged = new Label();
		prodLogged.setTextFill(Color.WHITE);
		Button prodCart = new Button();
		ImageView imgBtn = new ImageView();
		imgBtn.setImage(img);
		imgBtn.setFitWidth(20);
		imgBtn.setPreserveRatio(true);
		prodCart.setGraphic(imgBtn);
		prodCart.setTooltip(new Tooltip("Produtos desejados"));
		HBox prodBar = new HBox(7);
		prodBar.getChildren().addAll(prodLogged, prodCart);
		prodBar.setStyle("-fx-background-color: #336699;");
		prodBar.setAlignment(Pos.BASELINE_RIGHT);
		
		
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
		
		Button prodBuy = new Button("COMPRAR");
		prodBuy.setDisable(true);
		Button prodReserva = new Button("RESERVAR");
		prodReserva.setDisable(true);
		
		Label prodInfoText = new Label("INFORMAÇÕES DO PRODUTO");
		Label prodInfoName = new Label("Produto:");
		Label prodInfoForn = new Label("Fornecedor:");
		Label prodInfoValid = new Label("Validade:");
		Label prodInfoValue = new Label("Valor:");
		Label prodInfoQuantidade = new Label("Quantidade:");
		TextField prodQtd = new TextField();
		prodQtd.setPromptText("Qtde");
		prodTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
		prodTable.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
			if(prodTable.getSelectionModel().getSelectedItem() != null){
				prodInfoName.setText("Produto: " + newValue.getNome());
				prodInfoForn.setText("Fornecedor: " + newValue.getFornecedor());
				prodInfoValid.setText("Validade: " + newValue.getValidade());
				prodInfoValue.setText("Valor: R$" + newValue.getPreço());
				prodInfoQuantidade.setText("Quantidade: " + newValue.getQuantidade());
				prodBuy.setDisable(false);
				if(newValue.getQuantidade()==0)
					prodReserva.setDisable(false);
				else
					prodReserva.setDisable(true);
				prodBuy.setOnAction(e -> {
					try {
						compraProduto(newValue,prodQtd.getText());
						loadListProdutos();
						loadListProdDesejados();
						atualizaTabelaDesejos();
						atualizaTabelaProduto();
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				});
				prodReserva.setOnAction(e -> {
					try {
						requisitarNotificacao(newValue);
						loadListProdutos();
						loadListProdDesejados();
						atualizaTabelaProduto();
						atualizaTabelaDesejos();
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				});
			}
		});
		
		VBox prodInfo = new VBox(7);
		prodInfo.getChildren().addAll(prodInfoText, prodInfoName, prodInfoForn, prodInfoValid, prodInfoValue,prodInfoQuantidade, prodQtd, prodBuy,prodReserva);
		
		HBox prodAll = new HBox(7);
		prodAll.getChildren().addAll(prodTable, prodInfo);
		
		
		TextField prodSearchTF = new TextField();
		Button prodSearchBT = new Button("PROCURAR");
		HBox prodSearch = new HBox(7);
		prodSearch.getChildren().addAll(prodSearchTF, prodSearchBT);
		CheckBox prodCheckBox = new CheckBox();
		Label prodCheckLB = new Label("Disponível");
		HBox prodCheck = new HBox(7);
		prodCheck.getChildren().addAll(prodCheckLB, prodCheckBox);
		
		VBox layoutProd = new VBox(7);
		layoutProd.getChildren().addAll(prodBar, prodSearch, prodCheck, prodAll);
		layoutProd.setAlignment(Pos.TOP_CENTER);
	
		
		//	CARRINHO	XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
		Label cartBarLB = new Label();
		cartBarLB.setTextFill(Color.WHITE);
		HBox cartBar = new HBox(7);
		cartBar.getChildren().addAll(cartBarLB);
		cartBar.setMinHeight(30);
		cartBar.setStyle("-fx-background-color: #336699;");
		cartBar.setAlignment(Pos.CENTER_RIGHT);
		
		TableView<ProdDesejados> cartTable = new TableView<>();
		TableColumn tcCartName = new TableColumn("PRODUTO");
		tcCartName.setMinWidth(400);
		tcCartName.setCellValueFactory(
				new PropertyValueFactory<>("nomeProduto"));
		cartTable.getColumns().addAll(tcCartName);
		cartTable.setMaxWidth(400);
		cartTable.setItems(cartList);
		

		Button cartBack = new Button("VOLTAR");
		HBox cartButtons = new HBox(7);
		cartButtons.getChildren().addAll(cartBack);
		cartButtons.setAlignment(Pos.CENTER);
		
		VBox layoutCart = new VBox(15);
		layoutCart.getChildren().addAll(cartBar, cartTable, cartButtons);
		layoutCart.setAlignment(Pos.TOP_CENTER);
		
		//	BUTTONS		XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
		loginOK.setOnAction(e -> {
			try {
				if(login(loginUserTF.getText(), loginPassTF.getText())){
					prodLogged.setText("Logado como: " + loginUserTF.getText());
					loadListProdutos();
					loadListProdDesejados();
					atualizaTabelaProduto();
					atualizaTabelaDesejos();
					Timer timer = new Timer();
					timer.schedule(new TimerTask() {
						  public void run() {
							  try {
								loadListProdutos();
								atualizaTabelaProduto();
								loadListProdDesejados();
								atualizaTabelaDesejos();
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							  
						  }
					}, 30*1000,30*1000);
					window.setScene(prodScene);
				}
				else{
					JOptionPane.showMessageDialog(null, "Usuario/senha incorreto");
				}
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			
		});
		loginCad.setOnAction(e -> {
			window.setScene(cadScene);
		});
		
		cadOK.setOnAction(e ->{
			try {
				criaUsuario(cadUserTF.getText(), cadPassTF.getText(), cadNameTF.getText(), cadAddressTF.getText(), cadPassTF.getText(), cadEmailTF.getText());
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
		cadCancel.setOnAction(e -> {
			window.setScene(loginScene);
		});
		
		prodSearchBT.setOnAction(e-> {
			// FILTRA TABELA COM STRING EM prodSearchTF
		});
		
		prodCheckBox.setOnAction(e-> { // não sei qual event é, então coloquei onAction
			// FILTRA TABELA POR DISPONIBILIDADE (QTD > 0)
		});
		
		prodCart.setOnAction(e -> {
			cartBarLB.setText("Logado como: " + loginUserTF.getText());
			window.setScene(cartScene);
		});
		
		cartBack.setOnAction(e -> {
			window.setScene(prodScene);
		});
		
		
		//-------------------------
		
		
		loginScene = new Scene(layoutLogin, 600, 600);
		cadScene = new Scene(layoutCad, 600, 600);
		prodScene = new Scene(layoutProd, 600, 600);
		cartScene = new Scene(layoutCart, 600, 600);
		window.setScene(loginScene);
		window.setTitle("SUPERMERCADO DOS PARÇA");
		window.show();
	}
	
	void atualizaTabelaProduto(){
		prodList.clear();
		for(Produto p:listProdutos)
			prodList.add(p);
	}
	void atualizaTabelaDesejos(){
		cartList.clear();
		for(ProdDesejados pd:listProdDesejados){
			cartList.add(pd);
		}
	}
}




