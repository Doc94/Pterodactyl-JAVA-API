/**
MIT License

Copyright (c) 2017 Axel Vatan

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
*/
package fr.Axeldu18.PterodactylAPI.Methods;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.logging.Level;

import fr.Axeldu18.PterodactylAPI.PterodactylAPI;
import fr.Axeldu18.PterodactylAPI.Methods.DELETEMethods.Methods;
import lombok.AllArgsConstructor;
import lombok.Getter;

public class GETMethods {
	
	private PterodactylAPI main;
	
	public GETMethods(PterodactylAPI main){
		this.main = main;
	}
	
	public String get(Methods method){
		if(method.getURL().contains("{params}")){
			main.log(Level.SEVERE, "The method '" + method.toString() + "'contains field {params}, please use 'get' withs params function for this");
			return null;
		}
		return call(main.getMainURL() + method.getURL());
	}
	
	public String get(Methods method, String params){
		if(!method.getURL().contains("{params}")){
			main.log(Level.SEVERE, "The method '" + method.toString() + "' doesn't contains field {params}, please use 'get' function for this");
			return null;
		}
		return call(main.getMainURL() + method.getURL().replace("{params}", params));
	}
	
	public String get(Methods method, int id){
		return this.get(method, id + "");
	}

	
	private String call(String methodURL){
		try {
			URL url = new URL(methodURL);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			String hmac = main.getPublicKey() + "." + main.hmac(methodURL);
			connection.setRequestMethod("GET");
			connection.setRequestProperty("User-Agent", "Pterodactyl Java-API");
			connection.setRequestProperty("Authorization", "Bearer " + hmac.replaceAll("\n", ""));
			connection.setRequestProperty("Content-Type","application/json");
			connection.setRequestProperty("Accept","application/vnd.pterodactyl.v1+json");

			int responseCode = connection.getResponseCode();
			if (responseCode == HttpURLConnection.HTTP_OK) {
				return main.readResponse(connection.getInputStream()).toString();
			} else {
				return main.readResponse(connection.getErrorStream()).toString();
			}
		} catch (Exception e) {
			main.log(Level.SEVERE, e.getMessage());
			e.printStackTrace();
			return null;
		}
	}
	
	@AllArgsConstructor
	public enum Methods{
		USERS_LIST_USERS("api/application/users"), //Returns all users currently existing on the system as a paginated result.
		USERS_SINGLE_USER("api/application/users/{params}"), //Returns information about a single user.
		
		SERVERS_LIST_SERVERS("api/application/servers"), //List all servers on the system.
		SERVERS_SINGLE_SERVER("api/application/servers/{params}"), //Lists information about a single server.
		
		NODES_LIST_NODES("api/application/nodes"), //Lists all nodes in the system.
		NODES_SINGLE_NODE("api/application/nodes/{params}"), //View data for a single node.
		NODES_NODE_CONFIG("api/application/nodes/{params}/config"), //Returns the config file contents for the node daemon.
		
		LOCATIONS_LIST_LOCATIONS("api/application/locations"), //Returns a list of all locations on the system and associated nodes.
		
		SERVICES_LIST_SERVICES("api/application/services"), //Returns a listing of all services on the system.
		SERVICES_SINGLE_SERVICE("api/application/services/{params}"); //Returns detailed information about a single service on the system.
		
		private @Getter String URL;
	}
}
