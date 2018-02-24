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
package fr.Axeldu18.PterodactylAPI;

import java.util.HashMap;
import java.util.logging.Level;

import org.json.JSONArray;
import org.json.JSONObject;

import fr.Axeldu18.PterodactylAPI.Classes.Server;
import fr.Axeldu18.PterodactylAPI.Classes.ServerAttributes;
import fr.Axeldu18.PterodactylAPI.Methods.DELETEMethods;
import fr.Axeldu18.PterodactylAPI.Methods.GETMethods.Methods;

public class Servers {

	private PterodactylAPI main;

	public Servers(PterodactylAPI main){
		this.main = main;
	}
	
	/**
	 * @return Return all the SERVER with ATTRIBUTES
	 */
	public HashMap<Integer, Server> getServers(){
		HashMap<Integer, Server> serversMap = new HashMap<Integer, Server>();
		JSONObject jsonObject = new JSONObject(main.getGetMethods().get(Methods.SERVERS_LIST_SERVERS));
		JSONArray jsonArray = jsonObject.getJSONArray("data");
		for(int i=0;i<jsonArray.length();i++){
			JSONObject serverJSON = jsonArray.getJSONObject(i);
			Server server = new Server();
			server.setType(serverJSON.getString("object"));
			JSONObject serverAttributesJSON = serverJSON.getJSONObject("attributes");
			ServerAttributes serverAttributes = new ServerAttributes();
			serverAttributes.setId(serverAttributesJSON.getInt("id"));
			serverAttributes.setUuid(serverAttributesJSON.getString("uuid"));
			serverAttributes.setIdentifier(serverAttributesJSON.getString("identifier"));
			serverAttributes.setName(serverAttributesJSON.getString("name"));
			serverAttributes.setDescription(serverAttributesJSON.getString("description"));
			serverAttributes.setSuspended(serverAttributesJSON.getBoolean("suspended"));
			serverAttributes.setOwnerID(serverAttributesJSON.getInt("user"));

			JSONObject serverLimitationsJSON = serverAttributesJSON.getJSONObject("limits");

			serverAttributes.setMemory(serverLimitationsJSON.getInt("memory"));
			serverAttributes.setSwap(serverLimitationsJSON.getInt("swap"));
			serverAttributes.setDisk(serverLimitationsJSON.getInt("disk"));
			serverAttributes.setIo(serverLimitationsJSON.getInt("io"));
			serverAttributes.setCpu(serverLimitationsJSON.getInt("cpu"));

			serverAttributes.setNodeID(serverAttributesJSON.getInt("node"));
			serverAttributes.setAllocationID(serverAttributesJSON.getInt("allocation"));
			serverAttributes.setNestID(serverAttributesJSON.getInt("nest"));
			serverAttributes.setEggID(serverAttributesJSON.getInt("egg"));
			//serverAttributes.setPackID(null); //Can get null values, and cause errors

			JSONObject serverContainerJSON = serverAttributesJSON.getJSONObject("container");
			serverAttributes.setStartup(serverContainerJSON.getString("startup_command"));
			serverAttributes.setImage(serverContainerJSON.getString("image"));
			serverAttributes.setInstalled(serverContainerJSON.getBoolean("installed"));


			serverAttributes.setCreatedAT(serverAttributesJSON.getString("created_at"));
			serverAttributes.setUpdatedAT(serverAttributesJSON.getString("updated_at"));
			server.setAttributes(serverAttributes);
			serversMap.put(server.getAttributes().getId(), server);
		}
		return serversMap;
	}

	/**
	 * @param id ID of the targeted server.
	 * @return Return the targeted SERVER with ATTRIBUTES
	 */
	public Server getServer(int id){
		JSONObject jsonObject = new JSONObject(main.getGetMethods().get(Methods.SERVERS_SINGLE_SERVER, id));
		if(!jsonObject.has("data")){
			main.log(Level.SEVERE, jsonObject.toString());
			main.log(Level.SEVERE, "No SERVER found with this ID");
			return new Server();
		}
		JSONObject serverJSON = jsonObject.getJSONObject("data");
		Server server = new Server();
		server.setType(serverJSON.getString("object"));
		JSONObject serverAttributesJSON = serverJSON.getJSONObject("attributes");
		ServerAttributes serverAttributes = new ServerAttributes();
		serverAttributes.setId(serverAttributesJSON.getInt("id"));
		serverAttributes.setUuid(serverAttributesJSON.getString("uuid"));
		serverAttributes.setIdentifier(serverAttributesJSON.getString("identifier"));
		serverAttributes.setName(serverAttributesJSON.getString("name"));
		serverAttributes.setDescription(serverAttributesJSON.getString("description"));
		serverAttributes.setSuspended(serverAttributesJSON.getBoolean("suspended"));
		serverAttributes.setOwnerID(serverAttributesJSON.getInt("user"));

		JSONObject serverLimitationsJSON = serverAttributesJSON.getJSONObject("limits");

		serverAttributes.setMemory(serverLimitationsJSON.getInt("memory"));
		serverAttributes.setSwap(serverLimitationsJSON.getInt("swap"));
		serverAttributes.setDisk(serverLimitationsJSON.getInt("disk"));
		serverAttributes.setIo(serverLimitationsJSON.getInt("io"));
		serverAttributes.setCpu(serverLimitationsJSON.getInt("cpu"));

		serverAttributes.setNodeID(serverAttributesJSON.getInt("node"));
		serverAttributes.setAllocationID(serverAttributesJSON.getInt("allocation"));
		serverAttributes.setNestID(serverAttributesJSON.getInt("nest"));
		serverAttributes.setEggID(serverAttributesJSON.getInt("egg"));
		//serverAttributes.setPackID(null); //Can get null values, and cause errors

		JSONObject serverContainerJSON = serverAttributesJSON.getJSONObject("container");
		serverAttributes.setStartup(serverContainerJSON.getString("startup_command"));
		serverAttributes.setImage(serverContainerJSON.getString("image"));
		serverAttributes.setInstalled(serverContainerJSON.getBoolean("installed"));


		serverAttributes.setCreatedAT(serverAttributesJSON.getString("created_at"));
		serverAttributes.setUpdatedAT(serverAttributesJSON.getString("updated_at"));
		server.setAttributes(serverAttributes);
		return server;
	}
	
	/**
	 * @param id ID of the targeted server.
	 * @return If the deletion was successful.
	 */
	public boolean deleteServer(int id){
		return main.getDeleteMethods().delete(DELETEMethods.Methods.SERVER, id);
	}

	
}
