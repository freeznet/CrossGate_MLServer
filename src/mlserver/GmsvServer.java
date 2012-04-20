package mlserver;

import java.util.ArrayList;

public class GmsvServer {
	private String ServerName = "";
	private int ServerID = 0;
	private ArrayList<Player> players = null;
	private String serverIP = "";
	private String serverPort = "";
	
	public String getServerIP() {
		return serverIP;
	}
	public void setServerIP(String serverIP) {
		this.serverIP = serverIP;
	}
	public String getServerPort() {
		return serverPort;
	}
	public void setServerPort(String serverPort) {
		this.serverPort = serverPort;
	}
	public GmsvServer()
	{
		players = new ArrayList<Player>();
	}
	/**
	 * @return the players
	 */
	public ArrayList<Player> getPlayers() {
		return players;
	}
	/**
	 * @param players the players to set
	 */
	public void setPlayers(ArrayList<Player> players) {
		this.players = players;
	}
	/**
	 * @return the serverName
	 */
	public String getServerName() {
		return ServerName;
	}
	/**
	 * @param serverName the serverName to set
	 */
	public void setServerName(String serverName) {
		ServerName = serverName;
	}
	/**
	 * @return the serverID
	 */
	public int getServerID() {
		return ServerID;
	}
	/**
	 * @param serverID the serverID to set
	 */
	public void setServerID(int serverID) {
		ServerID = serverID;
	}
	
	public ArrayList<Player> hasPlyaer(String cdkey)
	{
		Player p = null;
		ArrayList<Player> ret = new ArrayList<Player>();
		//System.out.println("Server ID "+this.ServerID + " has " + this.players.size() + " players" );
		for (int i=0;i<players.size();i++)
		{
			p = players.get(i);
			//System.out.println(i + ":-> " + p.getCdkey() + " -> " + p.getRegNumber());
			if(p!=null && p.getCdkey().equals(cdkey) && !ret.contains(p))
				ret.add(p);
		}
		return ret;
	}
	
	public Player findPlayer(String cdkey,int RegNumber)
	{
		Player p = null;
		//System.out.println("Server ID "+this.ServerID + " has " + this.players.size() + " players" );
		for (int i=0;i<players.size();i++)
		{
			p = players.get(i);
			//System.out.println(i + ":-> " + p.getCdkey() + " -> " + p.getRegNumber());
			if(p!=null && p.getCdkey().equals(cdkey) && p.getRegNumber()==RegNumber)
				return p;
			else
				p = null;
		}
		return p;
	}
	
	public void addPlayer(Player p)
	{
		if(players!=null && !players.contains(p))
		{
			players.add(p);
		}
	}
	
	public void delPlayer(Player p)
	{
		if(players!=null && players.contains(p))
		{
			players.remove(p);
		}
	}
	
}
