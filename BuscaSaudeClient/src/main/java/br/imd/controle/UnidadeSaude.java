package br.imd.controle;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Classe UnidadeSaude
 * Responsavel por representar uma unidade de saude.
 */

public class UnidadeSaude{
	
	private String nome;
	private String endereco;
	private String bairro;
	private int id;
	private ArrayList<String> especialidades;

	
	/**
	 * Metodo Construtor Com todos os parametros da classe.
	 *
	 * @param nome Nome da unidade de sa�de.
	 * @param endereco Endereço da unidade de sa�de.
	 * @param bairro Bairro da unidade de sa�de.
	 * @param id id da unidade de saude.
	 * @param especialidades Especialidades presentes na unidade de sa�de.
	 */
	public UnidadeSaude(String nome, String endereco, String bairro, int id, ArrayList<String> especialidades) {
		this.nome = nome;
		this.endereco = endereco;
		this.bairro = bairro;
		this.id = id;
		this.especialidades = especialidades;
	}
	
	
	/**
	 * M�todo Construtor sem ID da classe.
	 *
	 * @param nome Nome da unidade de sa�de.
	 * @param endereco Endereço da unidade de sa�de.
	 * @param bairro Bairro da unidade de saude.
	 * @param especialidades Especialidades presentes na unidade de saude.
	 */
	public UnidadeSaude(String nome, String endereco, String bairro, ArrayList<String> especialidades) {
		this.nome = nome;
		this.endereco = endereco;
		this.bairro = bairro;
		this.especialidades = especialidades;
	}
	
	/**
	 * Metodo Construtor Padr�o da classe.
	 *
	 */
	public UnidadeSaude()
	{
		this.nome = "";
		this.endereco = "";
		this.bairro = "";
		this.id = -1;
		this.especialidades = new ArrayList<String>();
	}

	
	/**
	 * M�todo get do atributo nome
	 *
	 * @return Retorna o valor atual do atributo nome.
	 */
	public String getNome() {
		return nome;
	}


	/**
	 * M�todo set do atributo nome 
	 *
	 * @param nome tem como um parametro o nome a ser setado no atributo
	 */
	public void setNome(String nome) {
		this.nome = nome;
	}

	/**
	/**
	 * M�todo get do atributo endereco
	 *
	 * @return Retorna o valor atual do atributo endereco.
	 */
	public String getEndereco() {
		return endereco;
	}


	/**
	 * M�todo set do atributo endereco.
	 *
	 * @param endereco Novo valor do atributo endereco.
	 */
	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}


	/**
	 * M�todo get do atributo bairro.
	 *
	 * @return Retorna o valor atual do atributo bairro.
	 */
	public String getBairro() {
		return bairro;
	}


	/**
	/**
	 * Metodo get do atributo id
	 *
	 * @return Retorna o valor atual do atributo id.
	 */
	public int getId() {
		return id;
	}


	/**
	 * M�todo set do atributo id.
	 *
	 * @param id Novo valor do atributo id.
	 */
	public void setId(int id) {
		this.id = id;
	}


	/**
	 * M�todo set do atributo bairro.
	 *
	 * @param bairro Novo valor do atributo bairro.
	 */
	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	/**
	 * M�todo get do atributo especialidades.
	 *
	 * @return Retorna o valor atual do atributo especialidades.
	 */
	public ArrayList<String> getEspecialidades() {
		return especialidades;
	}


	/**
	 * M�todo set do atributo especialidades.
	 *
	 * @param especialidades Novo valor do atributo especialidades.
	 */
	public void setEspecialidades(ArrayList<String> especialidades) {
		this.especialidades = especialidades;
	}
	
	/**
	 * M�todo para adicionar uma nova especialidade � unidade de sa�de.
	 *
	 * @param especialidade Nova especialidade a ser inserida no atributo ArrayList especialidades.
	 */
	public void addEspecialidade(String especialidade){
		especialidades.add(especialidade);
	}
	
	/**
	 * M�todo para juntar todas as informa��es da unidade de sa�de em uma string.
	 *
	 * @return Retorna uma string contendo todos os atributos da classe.
	 */
	@Override
	public String toString(){
		
		String atributos = "";

		atributos += "ID: " + this.id + "\n";
		atributos += "Nome: " + this.nome + "\n";
		atributos += "Endereco: " + this.endereco + "\n";
		atributos += "Bairro: " + this.bairro + "\n";
		atributos += "Especialidades: ";
		
		Iterator<String> inter1 = especialidades.iterator();
		
		boolean primeiro = true;
		
		while (inter1.hasNext()){
			
			if( primeiro ){
				atributos += inter1.next();
				primeiro = false;
			}
			else
				atributos +=  ", " + inter1.next();
			
		}
		
		return atributos;
	}
	
	@Override
	public boolean equals(Object obj){
		
		boolean result = false;
		 
		if (obj instanceof UnidadeSaude){
			UnidadeSaude c = (UnidadeSaude) obj;
			result = c.getId() == this.getId();
		}
	  
		return result;
	}
	  
	@Override
	public int hashCode() {
		return getId() ^ 7;
	}
}
