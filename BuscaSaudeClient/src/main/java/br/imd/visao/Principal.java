package br.imd.visao;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Classe principal que representa o cliente.
 */
public class Principal extends Application   {

	
	
	private Stage primeiro;
	private VBox tlPrincipal;
	
	
	/* (non-Javadoc)
	 * @see javafx.application.Application#start(javafx.stage.Stage)
	 */
	@Override
	public void start(Stage primaryStage){
		primeiro = primaryStage;
		primeiro.setTitle("Cliente");
		primeiro.setResizable(false);
		initPrincipal();
	}

	public static void main(String[] args) {
		launch(args);		
	}
	
	/**
	 * M�todo respons�vel pela inicializa��o da interface gr�fica.
	 */
	private void initPrincipal(){
		
		try {
			
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Principal.class.getResource("TelaPrincipal.fxml"));
			tlPrincipal = (VBox) loader.load();
			
			//mostra a scene contendo o layout
			Scene scene = new Scene(tlPrincipal);
			primeiro.setScene(scene);
			primeiro.show();
			
		}
		catch (IOException e) {

			System.out.println("ERRO: IOException - O Arquivo fxml não pode ser carregado ");
		}
		
	}
}
