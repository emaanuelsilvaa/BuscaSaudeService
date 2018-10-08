package dao;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

import model.UnidadeSaude;


/**
 * Class UnidadeSaudeDAO Definida para simular um Banco de dados.
 */
public class UnidadeSaudeDAO {
	
	/** The Constant unidadesSaude. */
	private static final List<UnidadeSaude> unidadesSaude = new ArrayList<UnidadeSaude>();
	
	
	/**
	 * Retorna uma unidade de saude a partir do id que foi passado por parâmetro
	 *
	 * @param id the id
	 * @return result the result
	 */
	public synchronized static UnidadeSaude getById(int id) {
		UnidadeSaude result = null;
		
		for (UnidadeSaude unidade : unidadesSaude)
		{
			if (unidade.getId() == id)
			{
				result = unidade;
				break;
			}
		}
		
		return result;
	}
	
	/**
	 * Operação feita para retorna a ista de unidades de saude composta no DAO
	 *
	 * @return the Lista de unidadesSaude
	 */
	public synchronized static List<UnidadeSaude> list() {
		return unidadesSaude;
	}
	
	/**
	 * Encontra uma unidade de saude espefica a parti de um criterio informado.
	 *
	 * @param name the criterio ( EX: nome, endereco, bairro )
	 * @param type the tipo selecionado do criterio selecionado a partir da interface
	 * @return the list de unidades de saude.
	 */
	public synchronized static List<UnidadeSaude> findByType(String name, int type) {
		List<UnidadeSaude> result = new ArrayList<UnidadeSaude>();
		
		switch (type) {
			case 1:
				// Nome
				for (UnidadeSaude unidade : unidadesSaude) {
					if (unidade.getNome() != null && unidade.getNome().toLowerCase().contains(name.toLowerCase()))
					{
						result.add(unidade);
					}
				}
			break;
			
			case 2:
				// Endereco
				for (UnidadeSaude unidade : unidadesSaude) {
					if (unidade.getEndereco() != null && unidade.getEndereco().toLowerCase().contains(name.toLowerCase()))
					{
						result.add(unidade);
					}
				}
			break;
			
			case 3:
				// Bairro
				for (UnidadeSaude unidade : unidadesSaude) {
					if (unidade.getBairro() != null && unidade.getBairro().toLowerCase().contains(name.toLowerCase()))
					{
						result.add(unidade);
					}
				}
			break;
			
			case 4:
				// Especialidade
				for (UnidadeSaude unidade : unidadesSaude) {
					for( String especialidade : unidade.getEspecialidades() )
					{
						if (especialidade != null && especialidade.toLowerCase().contains(name.toLowerCase()))
						{
							result.add(unidade);
							break;
						}
					}
				}
			break;
			
			default:
		}
		
		return result;
	}
	
	/**
	 * Adiciona uma unidade de saude no DAO
	 *
	 * @param unidade the unidade a ser adicionada no DAO
	 * @return the unidade saude
	 * @throws WebApplicationException the web application exception
	 */
	public synchronized static UnidadeSaude add(UnidadeSaude unidade) throws WebApplicationException {
		if (unidade.getNome() == null || unidade.getNome().trim().equals(""))
		{
			throw new WebApplicationException(Response.status(Response.Status.BAD_REQUEST).entity("O nome do contato � obrigat�rio").build());
		}
	  
		unidadesSaude.add(unidade);
		unidade.setId(unidadesSaude.indexOf(unidade) + 1);
		
		return unidade;
	}
	
	/**
	 * Atualiza  no DAO os dados de uma unidade de saude existente
	 *
	 * @param unidade the unidade de saude
	 * @param id the id
	 */
	public synchronized static void update(UnidadeSaude unidade, int id) {
		unidadesSaude.set(id - 1, unidade);
		unidade.setId(unidadesSaude.indexOf(unidade) + 1);
	}
	
	/**
	 * Deleta uma unidade de saude a partir do seu id passado como parâmetro
	 *
	 * @param id the id
	 */
	public synchronized static void delete(int id) {
		UnidadeSaude unidade = unidadesSaude.get(id - 1);
		unidadesSaude.remove(unidade);
	}
}
