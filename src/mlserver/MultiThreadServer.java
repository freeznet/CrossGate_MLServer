package mlserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.*;
import java.util.ArrayList;
import java.util.concurrent.*;

public class MultiThreadServer {
	private final int port = 9651;
	private final ServerSocket serverSocket;
	private final ExecutorService executorService;// 线程池
	private final int POOL_SIZE = 5;// 单个CPU线程池大小
	private ServerList sl = new ServerList();
	private Mails mails = new Mails();
	private GoldRank gr = new GoldRank();
	private DuelRank dr = new DuelRank();

	public MultiThreadServer() throws IOException {
		serverSocket = new ServerSocket(port);
		// Runtime的availableProcessor()方法返回当前系统的CPU数目.
		executorService = Executors.newFixedThreadPool(Runtime.getRuntime()
				.availableProcessors() * POOL_SIZE);
		System.out.println("CrossGate MLServer Ver 20110803");
		System.out.println("http://cgdev.me/");
		System.out.println("By Free");
		System.out.println("===============================");
		System.out.println("MLServer Start... port:"+port+"...");
		//System.out.println("测试中文输出!!!");
		//System.getProperties().list(System.out);
	}

	public void service() {
		while (true) {
			Socket socket = null;
			try {
				// 接收客户连接,只要客户进行了连接,就会触发accept();从而建立连接
				socket = serverSocket.accept();
				executorService.execute(new Handler(socket,sl,mails,gr,dr));

			} catch (final Exception e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(final String[] args) throws IOException {
		new MultiThreadServer().service();
	}

}

class Handler implements Runnable {
	private final String TBL_GOLD = "db_gold";
	private final String TBL_DUEL = "db_duel";
	private final Socket socket;
	private final String connectPassword = "aho.java";
	//private final String acceptIP = "192.168.2.20";
	private ServerList sl = null;
	private Mails ml = null;
	private GoldRank gr = null;
	private DuelRank dr = null;
	public Handler(final Socket socket, ServerList sl, Mails ml, GoldRank gr, DuelRank dr) {
		this.socket = socket;
		this.sl = sl;
		this.ml = ml;
		this.gr = gr;
		this.dr = dr;
	}

	private PrintWriter getWriter(final Socket socket) throws IOException {
		final OutputStream socketOut = socket.getOutputStream();
		return new PrintWriter(socketOut, true);
	}

	private BufferedReader getReader(final Socket socket) throws IOException {
		final InputStream socketIn = socket.getInputStream();
		
		return new BufferedReader(new InputStreamReader(socketIn));
	}

	public String[] echo(final String msg) {
		String []ret = null;
		String myIP = socket.getInetAddress().toString();
		String myPort = Integer.toString(socket.getPort());
		if(msg!=null)
		{
			String[] packet = msg.split(" ");
			if(vaildPacketHeader(packet[0]))
			{
				if(packet[0].equalsIgnoreCase("ACServerLogin"))
				{
					if(packet[2]!=null && packet[2].equals(connectPassword))
					{
						GmsvServer gs = new GmsvServer();
						gs.setServerName(packet[1]);
						gs.setServerID(Integer.parseInt(packet[3]));
						gs.setServerIP(myIP);
						gs.setServerPort(myPort);
						sl.addNewServer(gs);
						ret = new String[1];
						ret[0] = "ACServerLogin successful";
						System.out.println("Authed Server [" +packet[3] + " : " + packet[1] +  "] connected... From "+myIP + ":" + myPort + ".");
					}
				}
				else if(packet[0].equalsIgnoreCase("ACServerLogout") )
				{
					ret = null;
				}
				else if(packet[0].equalsIgnoreCase("DBUpdateEntryString") )
				{
					if (packet[1].equalsIgnoreCase("db_addressbook")) {
						Player p = null;
						String cdkey = packet[2].split("#")[0];
						int RegNumber = Tools.SixtyTwoScale(packet[2].split("#")[1]);
						
						String buf = packet[3];
						String[] bufInfo = buf.split("\\|");
						p = sl.getPlayer(cdkey, RegNumber);
						if (p != null) {
							p.setOnline(Integer.parseInt(bufInfo[0]));
							p.setLevel(Integer.parseInt(bufInfo[1]));
							p.setTitleName(bufInfo[2]);
							p.setFaceNumber(Integer.parseInt(bufInfo[3]));
							p.setIndex(packet[4]);
						} else {
							p = new Player();
							p.setCdkey(cdkey);
							p.setRegNumber(RegNumber);
							p.setOnline(Integer.parseInt(bufInfo[0]));
							p.setLevel(Integer.parseInt(bufInfo[1]));
							p.setTitleName(bufInfo[2]);
							p.setFaceNumber(Integer.parseInt(bufInfo[3]));
							p.setIndex(packet[4]);
							sl.addPlayerToServer(p, p.getOnline());
						}
						ret = new String[1];
						ret[0] = p.makeDBGetEntryString();
					}
					else if(packet[1].equalsIgnoreCase("db_guild"))
					{
						/*String str = packet[2].split("#")[0];
						int num = Integer.parseInt(packet[2].split("#")[1]);
						*/
					}
				}
				else if(packet[0].equalsIgnoreCase("DBGetEntryString") )
				{
					if (packet[1].equalsIgnoreCase("db_addressbook")) {
						Player p = null;
						String cdkey = packet[2].split("#")[0];
						int RegNumber = Tools.SixtyTwoScale(packet[2].split("#")[1]);
						p = sl.getPlayer(cdkey, RegNumber);
						if (p != null) {
							p.setFdIndex(packet[3]);
							p.setIndex(packet[4]);
							ret = new String[1];
							ret[0] = p.makeDBGetEntryString();
						}
						
					}
				}
				else if(packet[0].equalsIgnoreCase("Broadcast") )
				{
					ret = new String[1];
					ret[0] = packet[0] + " " + packet[1] + " " + packet[2] + " " + packet[3];
				}
				else if(packet[0].equalsIgnoreCase("Message") )
				{
					if(packet[2].trim().equals("-1") && packet[5].startsWith("P|"))//普通说话
					{
						
					}
					else if(packet[2].trim().equals("-1") && !packet[5].startsWith("P|"))//发送邮件给在线玩家
					{
						
					}
					else if(!packet[2].trim().equals("-1"))//发送离线邮件
					{
						Mail m = new Mail(packet);
						//System.out.println(m.getToCdkey() + " " + m.getToRegNumber());
						ml.addMail(m);
						ml.writeToFile();
					}
				}
				else if(packet[0].equalsIgnoreCase("ACUCheckReq"))//自动踢人
				{
					int RegNumber = Tools.SixtyTwoScale(packet[1]);
					String cdkey = packet[2];
					GmsvServer gs = sl.getServerFromIP(myIP, myPort);
					//System.out.println("ACUCheckReq "+RegNumber+" " + cdkey);
					if(gs.findPlayer(cdkey, RegNumber)!=null && gs.findPlayer(cdkey, RegNumber).getOnline()==1)
					{
						//System.out.println("ACUCheckReq sent");
						ret = new String[1];
						ret[0] = "ACUCheck " + cdkey;
					}
				}
				else if(packet[0].equalsIgnoreCase("MessageFlush"))
				{
					//System.out.println("in MessageFlush");
					ArrayList<Mail> m = ml.findMails(packet[1], Tools.SixtyTwoScale(packet[2]));
					if(m!=null && m.size()>0)
					{
						ret = new String[m.size()];
						for(int i=0;i<m.size();i++)
						{
							ret[i] = m.get(i).getMailPacket();
						}
						ml.writeToFile();
					}
				}
				else if(packet[0].equalsIgnoreCase("DBUpdateEntryInt") )
				{
					if(packet[1].equals(TBL_GOLD))
					{
						String cdkey = packet[2].split("#")[0];
						int RegNumber = Tools.SixtyTwoScale(packet[2].split("#")[1]);
						Rank r = gr.getRank(cdkey, RegNumber);
						if(r==null)
						{
							r = new Rank();
							r.setTable(TBL_GOLD);
							r.setCdkey(cdkey);
							r.setRegNumber(RegNumber);
							r.setValue(Tools.SixtyTwoScale(packet[3]));
							r.setStr(packet[4]);
							r.setUnk1(Tools.SixtyTwoScale(packet[5]));
							r.setUnk2(Tools.SixtyTwoScale(packet[6]));
							gr.addRank(r);
						}
						else
						{
							r.setTable(TBL_GOLD);
							r.setCdkey(cdkey);
							r.setRegNumber(RegNumber);
							r.setValue(Tools.SixtyTwoScale(packet[3]));
							r.setStr(packet[4]);
							r.setUnk1(Tools.SixtyTwoScale(packet[5]));
							r.setUnk2(Tools.SixtyTwoScale(packet[6]));
						}
						ret = new String[1];
						ret[0] = "DBUpdateEntryInt successful " + packet[2] + " " + TBL_GOLD + " " + packet[5] + " " + packet[6];
						gr.writeToFile();
					}
					else if(packet[1].equals(TBL_DUEL))
					{
						String cdkey = packet[2].split("#")[0];
						int RegNumber = Tools.SixtyTwoScale(packet[2].split("#")[1]);
						Rank r = dr.getRank(cdkey, RegNumber);
						if(r==null)
						{
							r = new Rank();
							r.setTable(TBL_DUEL);
							r.setCdkey(cdkey);
							r.setRegNumber(RegNumber);
							r.setValue(Tools.SixtyTwoScale(packet[3]));
							r.setStr(packet[4]);
							r.setUnk1(Tools.SixtyTwoScale(packet[5]));
							r.setUnk2(Tools.SixtyTwoScale(packet[6]));
							dr.addRank(r);
						}
						else
						{
							r.setTable(TBL_DUEL);
							r.setCdkey(cdkey);
							r.setRegNumber(RegNumber);
							r.setValue(Tools.SixtyTwoScale(packet[3]));
							r.setStr(packet[4]);
							r.setUnk1(Tools.SixtyTwoScale(packet[5]));
							r.setUnk2(Tools.SixtyTwoScale(packet[6]));
						}
						ret = new String[1];
						ret[0] = "DBUpdateEntryInt successful " + packet[2] + " " + TBL_DUEL + " " + packet[5] + " " + packet[6];
						dr.writeToFile();
					}
				}
				else if(packet[0].equalsIgnoreCase("DBDeleteEntryInt") )
				{
					String cdkey = packet[2].split("#")[0];
					int RegNumber = Tools.SixtyTwoScale(packet[2].split("#")[1]);
					Rank r = null;
					ret = new String[1];
					if(packet[1].equals(TBL_GOLD))
					{
						r = gr.getRank(cdkey, RegNumber);
						if(r!=null)
						{
							gr.delRank(r);
							ret[0] = "DBDeleteEntryInt successful " + packet[2] + " " + TBL_GOLD + " " + packet[3] + " " + packet[4];
						}
						else
						{
							ret[0] = "DBDeleteEntryInt failed " + packet[2] + " " + TBL_GOLD + " " + packet[3] + " " + packet[4];
						}
						gr.writeToFile();
					}
					else if(packet[1].equals(TBL_DUEL))
					{
						r = dr.getRank(cdkey, RegNumber);
						if(r!=null)
						{
							dr.delRank(r);
							ret[0] = "DBDeleteEntryInt successful " + packet[2] + " " + TBL_DUEL + " " + packet[3] + " " + packet[4];
						}
						else
						{
							ret[0] = "DBDeleteEntryInt failed " + packet[2] + " " + TBL_DUEL + " " + packet[3] + " " + packet[4];
						}
						dr.writeToFile();
					}
				}
				else if(packet[0].equalsIgnoreCase("DBGetEntryRank") ){ //查看自己排名
					//DBGetEntryRank 成功与否 (总数,目前数量,账号信息,值,str|) 表 账号信息 packet[3] packet[4]
					String cdkey = packet[2].split("#")[0];
					int RegNumber = Tools.SixtyTwoScale(packet[2].split("#")[1]);
					if(packet[1].equals(TBL_GOLD))
					{//mlsvproto_DBGetEntryRank_recv(v83, a1, sFlg, v123, rank, tName, v80, fd, objIndex);
						ret = new String[1];
						int re = gr.getPlayerRankInfo(cdkey, RegNumber);
						if(re!=-1)
						{
							ret[0] = "DBGetEntryRank successful " + "0" + " " + re +" " + TBL_GOLD +" " +  packet[2] + " " + packet[3] + " " + packet[4];
						}
						else
						{
							ret[0] = "DBGetEntryRank failed " + "0" + " " + re +" " + TBL_GOLD +" " +  packet[2] + " " + packet[3] + " " + packet[4];
						}
					}
					else if(packet[1].equals(TBL_DUEL))
					{
						ret = new String[1];
						int re = dr.getPlayerRankInfo(cdkey, RegNumber);
						if(re!=-1)
						{
							ret[0] = "DBGetEntryRank successful " + "0" + " " + re +" " + TBL_DUEL +" " +  packet[2] + " " + packet[3] + " " + packet[4];
						}
						else
						{
							ret[0] = "DBGetEntryRank failed " + "0" + " " + re +" " + TBL_DUEL +" " +  packet[2] + " " + packet[3] + " " + packet[4];
						}
					}
				}
				else if(packet[0].equalsIgnoreCase("DBGetEntryByCount") ){ //查看所有排名
					if(packet[1].equals(TBL_GOLD))
					{
						int from = Tools.SixtyTwoScale(packet[2]);
						ret = new String[1];
						String r = gr.getLimitRankInfo(from);
						if(r!=null && !r.equals(""))
							ret[0] = "DBGetEntryByCount successful "+r+" " +TBL_GOLD +" " + packet[3] + " " + packet[4] + " " + packet[5];
						else
							ret[0] = "DBGetEntryByCount failed "+"1"+" " +TBL_GOLD +" " + packet[3] + " " + packet[4] + " " + packet[5];
						
					}
					else if(packet[1].equals(TBL_DUEL))
					{
						int from = Tools.SixtyTwoScale(packet[2]);
						ret = new String[1];
						String r = dr.getLimitRankInfo(from);
						if(r!=null && !r.equals(""))
							ret[0] = "DBGetEntryByCount successful "+r+" " +TBL_DUEL +" " + packet[3] + " " + packet[4] + " " + packet[5];
						else
							ret[0] = "DBGetEntryByCount failed "+"1"+" " +TBL_DUEL +" " + packet[3] + " " + packet[4] + " " + packet[5];
						
					}
				}
			}
		}
		//System.out.println("Echo:"+ret);
		return ret;
	}

	private boolean vaildPacketHeader(String string) {
		String[] packets = {"ACServerLogin","DBUpdateEntryString","ACUCheckReq","ACServerLogout","Broadcast","Message","DBGetEntryByCount","DBDeleteEntryInt","DBUpdateEntryInt","DBGetEntryRank","DBGetEntryByRank","DBDeleteEntryString","DBGetEntryString","MessageFlush"};
		for(int i=0;i<packets.length;i++)
		{
			if(packets[i].equalsIgnoreCase(string))
				return true;
		}
		return false;
	}

	public void run() {
		try {
			//if (socket.getInetAddress().toString().equals(acceptIP)) {
				//System.out.println("New connection accepted "
						//+ socket.getInetAddress() + ":" + socket.getPort());
				final BufferedReader br = getReader(socket);
				final PrintWriter pw = getWriter(socket);
				//InputStream socketIn = socket.getInputStream();
				String msg = null;
				//byte[] mm = new byte[65535];
				while ((msg = br.readLine()) != null) {
				//while (socketIn.read(mm)!=-1) {
					
					//msg = new String(mm,"GB2312");
					//System.out.println("Recv:" + msg);
					String[] ret = echo(msg);
					
					if(ret!=null)
					{
						int l = ret.length;
						if(l==1)
						{
							//System.out.println("Echo:"+ret[0]);
							
							pw.println(new String(ret[0]));
						}
						else
						{
							for (int i = 0;i<l;i++)
							{
								//System.out.println("Echo:"+ret[i]);
								
								pw.println(new String(ret[i]));
							}
						}
					}
				}
			//}
		} catch (final IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (socket != null)
					socket.close();
			} catch (final IOException e) {
				e.printStackTrace();
			}
		}
	}
}