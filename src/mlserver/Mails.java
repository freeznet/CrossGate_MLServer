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

public class Mails {
	private ArrayList<Mail> mails = null;
	private final String MAIL_DB = "mails.db";
	public Mails()
	{
		mails = new ArrayList<Mail>();
		this.loadFromFile();
	}
	
	public void addMail(Mail m)
	{
		if(mails!=null && !mails.contains(m))
		{
			mails.add(m);
			//System.out.println(mails.size());
		}
	}
	
	public ArrayList<Mail> findMails(String tc,int tr)
	{
		ArrayList<Mail> Ret = new ArrayList<Mail>();
		
		for(int i=0;i<mails.size();i++)
		{
			Mail m = mails.get(i);
			//System.out.println(m.getToCdkey() + ":" + tc + " -> " + m.getToRegNumber() + ":" + tr);
			if(m!=null && m.getToCdkey().equals(tc) && m.getToRegNumber()==tr)
			{
				if(!Ret.contains(m))
					Ret.add(m);
			}
			
		}
		//System.out.println(Ret.size());
		if(Ret.size()>0)
		{
			for(int i=0;i<Ret.size();i++)
			{
				Mail m = Ret.get(i);
				if(mails.contains(m))
					mails.remove(m);
			}
		}
		return Ret;
	}
	
	public void loadFromFile()
	{
		File sysfile = new File(MAIL_DB);
		try {
            if (sysfile.exists()) { //文件存在时
                InputStreamReader read = null;
                read = new InputStreamReader(new FileInputStream(sysfile));
                BufferedReader reader = new BufferedReader(read);
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] buf = line.split("	");
                    Mail m = new Mail(buf);
                    this.addMail(m);
                }
                read.close();
                System.out.println("Load " + this.mails.size() + " buffered mails.");
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
		if(this.mails == null || this.mails.size()==0)
			return;
		int i = 0;
		try{
			fw = new FileWriter(MAIL_DB);
			for(i = 0;i<this.mails.size();i++)
			{
				Mail m = this.mails.get(i);
				String buf = "";
				if(m != null)
				{
					buf = i + "	" + m.getFromCdkey() + "	" + m.getFromRegNumber() + "	" + m.getToCdkey() + "	" + m.getToRegNumber() + "	" + m.getMsg() + "	" + m.getUnk1() + "	" + m.getUnk2() + "	" + m.getUnk3() + "\r\n";
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
