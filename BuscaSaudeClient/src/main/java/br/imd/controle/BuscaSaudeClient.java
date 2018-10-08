package br.imd.controle;

import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
 


/**
 * The Class BuscaSaudeClient.
 */
public class BuscaSaudeClient {
	 
	/** The base target. */
	private WebTarget baseTarget;
	 
	/**
	 * Instantiates a new busca saude client.
	 *
	 * @param serviceUrl the service url
	 */
	public BuscaSaudeClient(String serviceUrl) {
		Client client = ClientBuilder.newClient();
		
		baseTarget = client.target(serviceUrl);
	}
	 
	/**
	 * Find all.
	 *
	 * @return the list
	 */
	public List<UnidadeSaude> findAll() {
		Invocation.Builder invocationBuilder = baseTarget.request(MediaType.APPLICATION_JSON);
		Response response = invocationBuilder.get();
	  
		if (response.getStatus() != Response.Status.OK.getStatusCode()) {
				throw new RuntimeException("Erro listando contatos");
		}
		
		return response.readEntity(new GenericType<List<UnidadeSaude>>() {});
	}
	 
	/**
	 * Find by type.
	 *
	 * @param name the name
	 * @param tipo the tipo
	 * @return the list
	 */
	public List<UnidadeSaude> findByType(String name, int tipo)
	{
		WebTarget searchTarget = baseTarget.path("/find/" + name + "/" + tipo);
		
		Invocation.Builder invocationBuilder = searchTarget.request(MediaType.APPLICATION_JSON);
		Response response = invocationBuilder.get();
		 
		if (response.getStatus() != Response.Status.OK.getStatusCode()) {
			throw new RuntimeException("Erro listando contatos");
		}
		 
		return response.readEntity(new GenericType<List<UnidadeSaude>>() {});
	}
	
	/**
	 * Adds the.
	 *
	 * @param unidade the unidade
	 * @return the unidade saude
	 */
	public UnidadeSaude add(UnidadeSaude unidade) {
		Invocation.Builder invocationBuilder = baseTarget.request(MediaType.APPLICATION_JSON);
		Response response = invocationBuilder.post(Entity.entity(unidade,MediaType.APPLICATION_JSON));
		  
		if (response.getStatus() != Response.Status.OK.getStatusCode()) {
			throw new RuntimeException("Erro criando contato" + response);
		}
		
		return response.readEntity(UnidadeSaude.class);
	}
	 
	/**
	 * Update.
	 *
	 * @param unidade the unidade
	 */
	public void update(UnidadeSaude unidade)
	{
		WebTarget updateTarget = baseTarget.path("/" + unidade.getId());
		Invocation.Builder invocationBuilder = updateTarget.request(MediaType.APPLICATION_JSON);
		Response response = invocationBuilder.put(Entity.entity(unidade, MediaType.APPLICATION_JSON));
		  
		if (response.getStatus() != Response.Status.NO_CONTENT.getStatusCode()) {
			throw new RuntimeException("Erro atualizando contato: " +  response.getStatus());
		}
	}
	 
	/**
	 * Delete.
	 *
	 * @param unidade the unidade
	 */
	public void delete(UnidadeSaude unidade) { 
		WebTarget deleteTarget = baseTarget.path("/" + unidade.getId());
		Invocation.Builder invocationBuilder = deleteTarget.request(MediaType.APPLICATION_JSON);
		Response response = invocationBuilder.delete();
		
		if (response.getStatus() != Response.Status.NO_CONTENT.getStatusCode()) {
			throw new RuntimeException("Erro removendo contato: " + response.getStatus());
		}
	}
 
}