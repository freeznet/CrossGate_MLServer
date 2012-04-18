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

public class GoldRank {
	private ArrayList<Rank> goldrank =  null;
	private final String GOLDRANK_DB = "goldrank.db";
	public GoldRank()
	{
		goldrank = new ArrayList<Rank> ();
		this.loadFromFile();
	}
	
	public void RankSort()
	{
		if(goldrank!=null && goldrank.size()>1)
		{
			Comparator<Rank> comp = new Mycomparator();
			Collections.sort(goldrank,comp); 
		}
	}
	
	public Rank getRank(String cdkey, int rn)
	{
		Rank ret = null;
		if(goldrank!=null && goldrank.size()>0)
		{
			for(int i=0;i<goldrank.size();i++)
			{
				ret = goldrank.get(i);
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
		//System.out.println("GoldRank num: " + goldrank.size());
		if(goldrank!=null && goldrank.size()>0)
		{
			for(int i = 0;i<goldrank.size();i++)
			{
				Rank r = goldrank.get(i);
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
		
		if(start >= goldrank.size())
			start = 0;
		
		if(goldrank!=null && goldrank.size()>0)
		{
			int count = 0;
			for(int i = start;i<goldrank.size();i++)
			{
				//总数,目前数量,账号信息,值,str|
				Rank r = goldrank.get(i);
				if(r!=null)
				{
					ret = ret + "10," + count + "," + r.getCdkey() + "#" + Tools.SixtyTwoScale(r.getRegNumber())+","+r.getValue() + "," +r.getStr()+"|";
					count++;
				}
			}
		}
		
		return ret;
	}
	
	public void delRank(Rank r)
	{
		if(r!=null && goldrank.contains(r))
		{
			goldrank.remove(r);
		}
	}
	
	public void addRank(Rank r)
	{
		if(r!=null && !goldrank.contains(r))
		{
			goldrank.add(r);
			//System.out.println("GoldRank num: " + goldrank.size());
		}
	}
	
	public void loadFromFile()
	{
		File sysfile = new File(GOLDRANK_DB);
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
                System.out.println("Load " + this.goldrank.size() + " buffered gold_rank info.");
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
		if(this.goldrank == null || this.goldrank.size()==0)
			return;
		int i = 0;
		try{
			fw = new FileWriter(GOLDRANK_DB);
			for(i = 0;i<this.goldrank.size();i++)
			{
				Rank r = this.goldrank.get(i);
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
