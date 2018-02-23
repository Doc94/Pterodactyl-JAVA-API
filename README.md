# Pterodactyl-JAVA-API
 A java adaptation for the use of the Pterodactyl panel API

# Important about older versions
The actual version (1.5) of PterodactylAPI only supports for panels since 0.7, panels
with older version not work correctly, this is for changes since 0.7 in routes url, and how to work the API.

# How to use (VER:1.5)
For use you need to call the class PterodactylAPI like this:
```java 
PterodactylAPI api = new PterodactylAPI();
api.setMainURL("URL OF YOUR PANEL");
api.setapplicationKey("API KEY);
api.setSecureConection(true|false); //Set secure conection (default false)
```
Then to retrieve the list of servers on the system you need to call the function getServer() in Servers classes
```java
HashMap<Integer, Server> servers = this.getServers().getServers();
```

Now there is 5 Classes to get informations from the API
- Users
- Servers
- Nodes
- Locations
- Services

For create a new user you need to call the function like this: 
```java
User user = this.getUsers().createUser(email, username, first_name, last_name, password, root_admin);
System.out.println(user.toString());
```
It return the User class with all the attributes of the new user
