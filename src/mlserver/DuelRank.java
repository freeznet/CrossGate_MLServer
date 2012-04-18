package mlserver;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class DuelRank {
	private ArrayList<Rank> duelrank =  null;
	private final String DUELRANK_DB = "duelrank.db";
	public DuelRank()
	{
		duelrank = new ArrayList<Rank> ();
	}
	
	public void RankSort()
	{
		if(duelrank!=null && duelrank.size()>1)
		{
			Comparator<Rank> comp = new Mycomparator();
			Collections.sort(duelrank,comp); 
		}
	}
	
	public Rank getRank(String cdkey, int rn)
	{
		Rank ret = null;
		if(duelrank!=null && duelrank.size()>0)
		{
			for(int i=0;i<duelrank.size();i++)
			{
				ret = duelrank.get(i);
				if(ret != null && ret.getCdkey().equals(cdkey) && ret.getRegNumber()==rn)
					return ret;
				else
					ret = null;
			}
		}
		return ret;
	}
	
	public int getPlayerRankInfo(String cdkey, int rn)
	{
		int ret = -1;
		
		this.RankSort();
		//System.out.println("Looking for " + cdkey + " " + rn);
		//System.out.println("duelrank num: " + duelrank.size());
		if(duelrank!=null && duelrank.size()>0)
		{
			for(int i = 0;i<duelrank.size();i++)
			{
				Rank r = duelrank.get(i);
				//System.out.println(cdkey + "->" + r.getCdkey() + " , " + rn + " -> " + r.getRegNumber());
				if(r!=null && r.getCdkey().equals(cdkey) && r.getRegNumber()==rn)
				{
					ret = i;
					break;
				}
			}
		}
		
		return ret;
	}
	
	public String getLimitRankInfo(int start)
	{
		String ret = "";
		
		this.RankSort();
		
		if(start >= duelrank.size())
			start = 0;
		
		if(duelrank!=null && duelrank.size()>0)
		{
			int count = 0;
			for(int i = start;i<duelrank.size();i++)
			{
				//总数,目前数量,账号信息,值,str|
				Rank r = duelrank.get(i);
				if(r!=null){
					ret = ret + "10," + count + "," + r.getCdkey() + "#" + Tools.SixtyTwoScale(r.getRegNumber())+","+r.getValue() + "," +r.getStr()+"|";
					count++;
				}
			}
		}
		
		return ret;
	}
	
	public void delRank(Rank r)
	{
		if(r!=null && duelrank.contains(r))
		{
			duelrank.remove(r);
		}
	}
	
	public void addRank(Rank r)
	{
		if(r!=null && !duelrank.contains(r))
		{
			duelrank.add(r);
			//System.out.println("duelrank num: " + duelrank.size());
		}
	}
	
	public void loadFromFile()
	{
		File sysfile = new File(DUELRANK_DB);
		try {
            if (sysfile.exists()) { //文件存在时
                InputStreamReader read = null;
                read = new InputStreamReader(new FileInputStream(sysfile));
                BufferedReader reader = new BufferedReader(read);
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] buf = line.split("	");
                    Rank m = new Rank(buf);
                    this.addRank(m);
                }
                read.close();
                System.out.println("Load " + this.duelrank.size() + " buffered duel_rank info.");
            }
        } catch (FileNotFoundException ex) {
        	return;
        } catch (UnsupportedEncodingException ex) {
        	return;
        } catch (IOException ex) {
        	return;
        }
	}
	
	
	public void writeToFile()
	{
		FileWriter fw = null;
		if(this.duelrank == null || this.duelrank.size()==0)
			return;
		int i = 0;
		try{
			fw = new FileWriter(DUELRANK_DB);
			for(i = 0;i<this.duelrank.size();i++)
			{
				Rank r = this.duelrank.get(i);
				String buf = "";
				if(r != null)
				{
					buf = i + "	" + r.getTable() + "	" + r.getCdkey() + "	" + r.getRegNumber() + "	" + r.getValue() + "	" + r.getStr() + "	" + r.getUnk1() + "	" + r.getUnk2() + "\r\n";
					fw.write(new String(buf.getBytes("GB2312")));
				}
				
			}
			fw.close();
		}
		catch (Exception ex)
		{
			ex.printStackTrace();   
		}
		finally {
			try
			{
				fw.close();
			}
			catch (Exception ex)
			{
				ex.printStackTrace();   
			}
		}
	}
}
