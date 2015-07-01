package Executavel;

import java.util.*;

import Model.*;
import Util.*;
import javafx.application.Application;
import javafx.collections.*;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class biluga extends Application{
	Stage window;
	Scene loginScene, cadScene, prodScene, cartScene;
	ArrayList<Estoque> cartListProd = new ArrayList<>();
	ObservableList<Estoque> cartList;
	
	public static void main(String[] args){
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception{
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
		HBox prodBar = new HBox(7);
		prodBar.getChildren().addAll(prodLogged, prodCart);
		prodBar.setStyle("-fx-background-color: #336699;");
		prodBar.setAlignment(Pos.BASELINE_RIGHT);
		
		ArrayList<Estoque> prodListCSV = new ArrayList<>(); // <= RECEBE LISTA DE PRODUTOS
		prodListCSV.add(new Estoque(new Produto("detergente", "20", "15/02", "omo"), 15));
		ObservableList<Estoque> prodList = FXCollections.observableList(prodListCSV);
		TableView<Estoque> prodTable = new TableView<>();
		TableColumn tcProdName = new TableColumn("PRODUTO");
		tcProdName.setMinWidth(300);
		tcProdName.setMaxWidth(300);
		tcProdName.setCellValueFactory(
				new PropertyValueFactory<>("produto"));
		TableColumn tcProdQtd = new TableColumn("QTD");
		tcProdQtd.setMinWidth(100);
		tcProdQtd.setMinWidth(100);
		tcProdQtd.setCellValueFactory(
				new PropertyValueFactory<>("quantidade"));
		prodTable.getColumns().addAll(tcProdName, tcProdQtd);
		prodTable.setItems(prodList);
		
		Button prodBuy = new Button("COMPRAR");
		
		Label prodInfoText = new Label("INFORMAÇÕES DO PRODUTO");
		Label prodInfoName = new Label("Produto:");
		Label prodInfoForn = new Label("Fornecedor:");
		Label prodInfoValid = new Label("Validade:");
		Label prodInfoValue = new Label("Valor");
		TextField prodQtd = new TextField();
		prodQtd.setPromptText("Qtde");
		prodTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
		prodTable.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
			if(prodTable.getSelectionModel().getSelectedItem() != null){
				prodInfoName.setText("Produto: " + newValue.getProduto().getNome());
				prodInfoForn.setText("Fornecedor: " + newValue.getProduto().getFornecedor());
				prodInfoValid.setText("Validade: " + newValue.getProduto().getValidade());
				prodInfoValue.setText("Valor: R$" + newValue.getProduto().getPreço());
				prodBuy.setOnAction(e -> {
					//IF 0 < QTD <= MAX
					cartList.add(newValue);
					window.setScene(cartScene);
				});
			}
		});
		
		VBox prodInfo = new VBox(7);
		prodInfo.getChildren().addAll(prodInfoText, prodInfoName, prodInfoForn, prodInfoValid, prodInfoValue, prodQtd, prodBuy);
		
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
		
		cartList = FXCollections.observableList(cartListProd);
		TableView<Estoque> cartTable = new TableView<>();
		TableColumn tcCartName = new TableColumn("PRODUTO");
		tcCartName.setMinWidth(300);
		tcCartName.setMaxWidth(300);
		tcCartName.setCellValueFactory(
				new PropertyValueFactory<>("produto.nome"));
		TableColumn tcCartQtd = new TableColumn("QTD");
		tcCartQtd.setMinWidth(100);
		tcCartQtd.setMaxWidth(100);
		tcCartQtd.setCellValueFactory(
				new PropertyValueFactory<>("quantidade"));
		cartTable.getColumns().addAll(tcCartName, tcCartQtd);
		cartTable.setMaxWidth(400);
		cartTable.setItems(cartList);
		
		Button cartDel = new Button("REMOVER");
		Button cartBuy = new Button("FINALIZAR");
		Button cartBack = new Button("VOLTAR");
		HBox cartButtons = new HBox(7);
		cartButtons.getChildren().addAll(cartBack, cartBuy);
		cartButtons.setAlignment(Pos.CENTER);
		
		VBox layoutCart = new VBox(15);
		layoutCart.getChildren().addAll(cartBar, cartTable, cartDel, cartButtons);
		layoutCart.setAlignment(Pos.TOP_CENTER);
		
		//	BUTTONS		XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
		loginOK.setOnAction(e -> {
			// IF LOGADO ENTÃO...
			prodLogged.setText("Logado como: " + loginUserTF.getText());
			window.setScene(prodScene);
		});
		loginCad.setOnAction(e -> {
			window.setScene(cadScene);
		});
		
		cadOK.setOnAction(e ->{
			// CADASTRA USUARIO
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
		
		cartBuy.setOnAction(e-> {
			// VERIFICA COM SERVIDOR SE AINDA ESTAO DISPONIVEIS E FINALIZA COMPRA
		});
		cartDel.setOnAction(e-> {
			// REMOVE ITEM NA LISTA DO CARRINHO
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
}
