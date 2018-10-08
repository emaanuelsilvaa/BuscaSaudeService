package br.imd.controle;

import javafx.event.ActionEvent;

import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;

import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TabPane;

public class Control implements Initializable {
	
	final int ERRO = 0, INFO = 1, QUESTION = 2;
	
	@FXML 
	TabPane painel;
	
	// -------- COMPONENTES DO PAINEL CADASTRO / ATUALIZAR / EXCLUIR ----------------
	
	@FXML
	TextField nome_field_c;
	@FXML
	TextField address_field_c;
	@FXML
	TextField city_fileld_c;
	@FXML
	TextField skills_field_c;
	@FXML
	ListView<UnidadeSaude> listCadastro;
	@FXML
	Button listarCadastro;
	
	// --------------------------------------------------------- 
	
	// ---------- COMPONENTES DO PAINEL BUSCAR ---------------- 
	
	ToggleGroup toggleGroup;
	
	@FXML
	RadioButton radio_nome;
	@FXML
	RadioButton radio_bairro;
	@FXML
	RadioButton radio_endereco;
	@FXML
	RadioButton radio_especialidade;
	@FXML
	Button buscaUnidade;
	@FXML
	ListView<UnidadeSaude> listBuscar;
	@FXML
	TextField busca_field;
	
	//--------------------------------------------------------- 
	
	
	BuscaSaudeClient stub;
	
	
	/**
	 * Metodo construtor da classe controladora dos elementos da interface gr�fica
	 * @throws NotBoundException 
	 * @throws RemoteException 
	 * @throws MalformedURLException 
	 */
	public Control() throws MalformedURLException, RemoteException, NotBoundException {
		
		/* Busca no modulo de comunicacao remota (RMI Registry).
		 * Retorna-se uma referencia de objeto para o stub de servidor, 
		 * atraves do qual e possivel realizar a invocacao de metodos remotos */
		
		stub = new BuscaSaudeClient("http://localhost:8080/ServiceBuscaSaude/crud");
	}

	/* (non-Javadoc)
	 * @see javafx.fxml.Initializable#initialize(java.net.URL, java.util.ResourceBundle)
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		toggleGroup = new ToggleGroup();
		
		radio_nome.setToggleGroup(toggleGroup);
		radio_bairro.setToggleGroup(toggleGroup);
		radio_endereco.setToggleGroup(toggleGroup);
		radio_especialidade.setToggleGroup(toggleGroup);
		
		listCadastro.setOnMouseClicked(event -> {
			
			UnidadeSaude selectedItem = (UnidadeSaude) listCadastro.getSelectionModel().getSelectedItem();

			if( selectedItem != null ) {
				
				nome_field_c.setText( selectedItem.getNome() );
		    	address_field_c.setText( selectedItem.getEndereco() );
		    	city_fileld_c.setText( selectedItem.getBairro() );
		    	
		    	ArrayList<String> especialidades = selectedItem.getEspecialidades();		
		    	String esp = "";
				
				for (String especialidade : especialidades) {
					 esp+= especialidade+";";
				}
				
				if( !esp.isEmpty() )
				{
			    	skills_field_c.setText( esp.substring(0, esp.length()-1) );
				}
				else
					skills_field_c.setText("");
			}
            	
        });
	}
	
	/**
	 * Metodo para cadastrar uma unidade de sa�de no servidor utilizando o stub.
	 * @param event Referencia para o criador do evento a ser tratado.
	 * @throws NotBoundException 
	 * @throws MalformedURLException 
	 * @throws RemoteException 
	 */
	@FXML
	private void cadastrarUnidade(ActionEvent event) throws MalformedURLException, NotBoundException, RemoteException{
	    
		String nome = nome_field_c.getText();
		String endereco = address_field_c.getText();
		String bairro = city_fileld_c.getText();
		ArrayList<String> especialidades = new ArrayList<>();
		
		String especialidade = skills_field_c.getText();
		
		String[] textoSeparado = especialidade.split(";");
		
		for (int i = 0 ; i < textoSeparado.length ; i++) {
			especialidades.add(textoSeparado[i]);
		}
		
		UnidadeSaude unidadeSaude = new UnidadeSaude(nome, endereco, bairro, especialidades);
	    	
    	if( validarCampos() )
    	{
    		try
    		{
    			
	    		stub.add(unidadeSaude);

		    	nome_field_c.setText("");
		    	address_field_c.setText("");
		    	city_fileld_c.setText("");
		    	skills_field_c.setText("");
		    	
		    	listarCadastro.fire();
		    	exibirDialogo(INFO, "Atenção", "Operação Concluida", "Unidade Cadastrada com Sucesso !");
    		}
    		catch(RuntimeException e)
    		{
    			exibirDialogo(INFO, "Atenção", "Operação não Concluida", "Falha na Adição da Unidade de Saude !");	
    		}	
    	} 
    	else
    	{
    		exibirDialogo(INFO, "Atenção", "Operação não Realizada", "Todos os Campos devem ser preenchidos!");
    	}
	}
	
	/**
	 * Metodo respons�vel por criar uma caixa de dialogo.
	 * @param error Define se um dialogo de erro ou informativo.
	 * @param titulo Titulo a ser exibido no dialogo.
	 * @param cabecalho Cabeçalho do dialogo.
	 * @param mensagem Mensagem a ser exibida.
	 * @throws NotBoundException 
	 * @throws RemoteException 
	 * @throws MalformedURLException 
	 */
	private void exibirDialogo(int type, String titulo, String cabecalho, String mensagem) throws MalformedURLException, RemoteException, NotBoundException{
		
		Alert dialogo = new Alert( Alert.AlertType.ERROR );
		
		dialogo.setTitle(titulo);
		dialogo.setHeaderText(cabecalho);
        dialogo.setContentText(mensagem);
		
		switch( type ){
			case INFO:
				dialogo.setAlertType( Alert.AlertType.INFORMATION );
			break;
			
			case QUESTION:
				dialogo.setAlertType( Alert.AlertType.INFORMATION );
				
				ButtonType buttonTypeYes = new ButtonType("Sim");
		        ButtonType buttonTypeNo = new ButtonType("Não");
		        
		        dialogo.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);
		        
		        Optional<ButtonType> result = dialogo.showAndWait();
		        
		        if (result.get() == buttonTypeYes){
		        		stub = new BuscaSaudeClient("http://localhost:8080/ServiceBuscaSaude/crud");
		        } else if (result.get() == buttonTypeNo) {
		        	System.exit(-1);
		        }
		        
			break;
		}

        dialogo.showAndWait();
		
	}
	
	/**
	 * Função para validar os campos de cadastro/atualiza, para não permitir deixa-los em
	 * branco ou com apenas espaços.
	 * @return Retorna true se os campos contiverem algum valor, false caso estejam em branco
	 * ou com apenas espa�os.
	 */
	private boolean validarCampos(){
		
		if( nome_field_c.getText().trim().isEmpty() ) {
			return false;
		}
    	
		if( address_field_c.getText().trim().isEmpty() ) {
			return false;
		}
				
		if( city_fileld_c.getText().trim().isEmpty() ) {
			return false;
		}
				
		if( skills_field_c.getText().trim().isEmpty() ) {
			return false;
		}
		
		return true;
	}
	
	/**
	 * Metodo para atualizar as informações de uma unidade de saude no serviço web utilizando o stub.
	 * @param event Referencia para o criador do evento a ser tratado.
	 * @throws NotBoundException 
	 * @throws MalformedURLException 
	 * @throws RemoteException 
	 */
	@FXML
	private void atualizarUnidade(ActionEvent event) throws MalformedURLException, NotBoundException, RemoteException {
		
		UnidadeSaude selectedUnidade = (UnidadeSaude) listCadastro.getSelectionModel().getSelectedItem();;
		
		if( selectedUnidade != null ) {
							
			selectedUnidade.setNome( nome_field_c.getText() );
			selectedUnidade.setEndereco( address_field_c.getText() );
			selectedUnidade.setBairro( city_fileld_c.getText() );
			
			ArrayList<String> especialidades = new ArrayList<>();
			
			String especialidade = skills_field_c.getText();
			
			String[] textoSeparado = especialidade.split(";");
			
			for (int i = 0 ; i < textoSeparado.length ; i++) {
				especialidades.add(textoSeparado[i]);
			}
			
			selectedUnidade.setEspecialidades( especialidades );
			
			if( validarCampos() ) {
	    		
				try
				{
			    	stub.update(selectedUnidade);
			    	
			    	nome_field_c.setText("");
			    	address_field_c.setText("");
			    	city_fileld_c.setText("");
			    	skills_field_c.setText("");

			    	listarCadastro.fire();			
			    	
			    	exibirDialogo(INFO, "Atenção", "Operação Concluida", "Unidade Atualizada com Sucesso !");
				}
	    		catch(RuntimeException e)
	    		{
	    			exibirDialogo(INFO, "Atenção", "Operação não Concluida", "Falha na Atualização da Unidade de Saude !");	
	    		}
			}
			else{
				exibirDialogo(INFO, "Atenção", "Operação n�o Realizada", "Todos os Campos devem ser preenchidos!");
			}
		}
	}
	
	/**
	 * Metodo para excluir uma unidade de saude do serviço utilizando o stub.
	 * @param event Referencia para o criador do evento a ser tratado.
	 * @throws NotBoundException 
	 * @throws MalformedURLException 
	 * @throws RemoteException 
	 */
	@FXML
	private void excluirUnidade(ActionEvent event) throws MalformedURLException, NotBoundException, RemoteException{
		
		UnidadeSaude selectedUnidade = (UnidadeSaude) listCadastro.getSelectionModel().getSelectedItem();
		
		if( selectedUnidade != null ) {
			
			
			try{
				stub.delete( selectedUnidade );
				
		    	nome_field_c.setText("");
		    	address_field_c.setText("");
		    	city_fileld_c.setText("");
		    	skills_field_c.setText("");
		    	
		    	listarCadastro.fire();
		    	exibirDialogo(INFO, "Atenção", "Operação Concluida", "Unidade excluida com Sucesso !");
			}
    		catch(RuntimeException e)
    		{
    			exibirDialogo(INFO, "Atenção", "Operação não Concluida", "Falha na Exclusão da Unidade de Saude !");	
    		}
		}
	      
	}
	
	/**
	 * Metodo para popular o listView da interface grafica com todas as unidades de sa�de
	 * armazenadas no serviço.
	 * @param event Referencia para o criador do evento a ser tratado.
	 * @throws NotBoundException 
	 * @throws RemoteException 
	 * @throws MalformedURLException 
	 */
	@FXML
	private void listar(ActionEvent event) throws MalformedURLException, RemoteException, NotBoundException
	{		
		List<UnidadeSaude> unidades;

		try
		{
			unidades = stub.findAll();
			
			listCadastro.getItems().clear();
			
			for (UnidadeSaude unidade : unidades){
				listCadastro.getItems().add(unidade);
	        }
		}
		catch(RuntimeException e)
		{
			exibirDialogo(INFO, "Atenção", "Operação não Concluida", "Falha na Listagem das Unidades de Saude !");	
		}
		
	}
	
	/**
	 * Metodo para realizar a busca de uma unidade de saude no servidor por diferentes
	 * criterios utilizando o stub.
	 * @param event Referencia para o criador do evento a ser tratado.
	 * @throws NotBoundException 
	 * @throws RemoteException 
	 * @throws MalformedURLException 
	 */
	@FXML
	private void buscarUnidade(ActionEvent event) throws MalformedURLException, RemoteException, NotBoundException{
		int selecao = 1;
		
		if( radio_nome.isSelected() ){
			selecao = 1;
		}
		else if( radio_endereco.isSelected() ) {
			selecao = 2;
		}
		else if( radio_bairro.isSelected() ) {
			selecao = 3;
		}
		else if( radio_especialidade.isSelected() ) {
			selecao = 4;
		}
		
		String busca = busca_field.getText();
		
		List<UnidadeSaude> unidades;
		
		if( !busca.isEmpty() )
		{
			try
			{
				unidades = stub.findByType(busca, selecao );
				
				listBuscar.getItems().clear();
		
				for (UnidadeSaude unidade : unidades){
					listBuscar.getItems().add(unidade);
		        }
			}
    		catch(RuntimeException e)
    		{
    			exibirDialogo(INFO, "Atenção", "Operação não Concluida", "Falha na Pesquisa das Unidades de Saude !");	
    		}
		}
		
	}
	
}
