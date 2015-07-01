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

public class bilugasv extends Application{
	Stage window;
	Scene cadScene, prodScene;
	ArrayList<Estoque> cartListProd = new ArrayList<>();
	ObservableList<Estoque> cartList;
	
	public static void main(String[] args){
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception{
		window = primaryStage;
		
		//	PRODUTOS	XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
		HBox prodBar = new HBox(7);
		prodBar.setMinHeight(30);
		prodBar.setStyle("-fx-background-color: #336699;");
		prodBar.setAlignment(Pos.CENTER_RIGHT);
		
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
		
		Button prodAtt = new Button("ATUALIZAR");
		Button prodDel = new Button("REMOVER");
		TextField prodAttVal = new TextField();
		prodAttVal.setPromptText("Qtde");
		
		Produto prod;
		Label prodInfoText = new Label("INFORMAÇÕES DO PRODUTO");
		Label prodInfoName = new Label("Produto:");
		Label prodInfoForn = new Label("Fornecedor:");
		Label prodInfoValid = new Label("Validade:");
		Label prodInfoValue = new Label("Valor:");
		prodTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
		prodTable.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
			if(prodTable.getSelectionModel().getSelectedItem() != null){
				prodInfoName.setText("Produto: " + newValue.getProduto().getNome());
				prodInfoForn.setText("Fornecedor: " + newValue.getProduto().getFornecedor());
				prodInfoValid.setText("Validade: " + newValue.getProduto().getValidade());
				prodInfoValue.setText("Valor: R$" + newValue.getProduto().getPreço());
				prodAtt.setOnAction(e -> {
					// SETA prodAttVal NA quantidade DO PRODUTO (ESTOQUE)
				});
				prodDel.setOnAction(e->{
					// REMOVE PRODUTO DO ESTOQUE
				});
			}
		});
		
		VBox prodInfo = new VBox(7);
		prodInfo.getChildren().addAll(prodInfoText, prodInfoName, prodInfoForn, prodInfoValid, prodInfoValue, prodDel, prodAttVal, prodAtt);
		
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
			// CADASTRA PRODUTO
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
}
