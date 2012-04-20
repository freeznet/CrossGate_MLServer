package mlserver;

import java.util.ArrayList;

public class ServerList {
	private ArrayList<GmsvServer> servers = null;
	public ServerList()
	{
		servers = new ArrayList<GmsvServer> ();
	}
	/**
	 * @return the servers
	 */
	public ArrayList<GmsvServer> getServers() {
		return servers;
	}

	/**
	 * @param servers the servers to set
	 */
	public void setServers(ArrayList<GmsvServer> servers) {
		this.servers = servers;
	}
	
	public void addNewServer(GmsvServer gs)
	{
		if(servers!=null)
		{
			if(!servers.contains(gs))
				servers.add(gs);
		}
	}
	
	public GmsvServer getServerFromID(int id)
	{
		GmsvServer gs = null;
		for(int i=0;i<servers.size();i++)
		{
			gs = servers.get(i);
			if(gs!=null && gs.getServerID()==id)
				return gs;
			else
				gs = null;
		}
		return gs;
	}
	
	public GmsvServer getServerFromIP(String ip,String p)
	{
		GmsvServer gs = null;
		for(int i=0;i<servers.size();i++)
		{
			gs = servers.get(i);
			if(gs!=null && gs.getServerIP().equalsIgnoreCase(ip) && gs.getServerPort().equalsIgnoreCase(p))
				return gs;
			else
				gs = null;
		}
		return gs;
	}
	
	public int hasCdKeyPlayer(String cdkey)
	{
		//System.out.println("Looking for "+ cdkey+ " rn: " + rn);
		//System.out.println("Server Num: "+ servers.size());
		ArrayList<Player> p = null;
		int ret = 0;
		GmsvServer gs = null;
		for(int i=0;i<servers.size();i++)
		{
			gs = servers.get(i);
			p = gs.hasPlyaer(cdkey);
			if(p.size()>0)
			{
				for(int j = 0; j<p.size();j++){
					Player pl = p.get(j);
					ret+=pl.getOnline();
				}
			}
		}
		return ret;
	}
	
	public Player getPlayer(String cdkey,int rn)
	{
		//System.out.println("Looking for "+ cdkey+ " rn: " + rn);
		//System.out.println("Server Num: "+ servers.size());
		Player p = null;
		GmsvServer gs = null;
		for(int i=0;i<servers.size();i++)
		{
			gs = servers.get(i);
			p = gs.findPlayer(cdkey, rn);
			if(p!=null)
				return p;
			else
				p = null;
		}
		return p;
	}
	
	public void addPlayerToServer(Player p, int id)
	{
		GmsvServer gs = this.getServerFromID(id);
		//System.out.println("Get Server id " + id + " " + (gs!=null));
		if(gs!=null)
		{
			gs.addPlayer(p);
		}
	}
	public void delPlayerFromServer(Player p) {
		if(p!=null)
		{
			GmsvServer gs = this.getServerFromID(p.getOnline());
			if(gs!=null)
			{
				gs.addPlayer(p);
			}
		}
		
	}
}
