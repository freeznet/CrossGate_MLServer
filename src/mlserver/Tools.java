package mlserver;

public class Tools {
	private final static char[] dict = { '0', '1', '2', '3', '4', '5', '6', '7', '8',
			'9', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l',
			'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y',
			'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L',
			'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y',
			'Z' };
	
	private static char Convert(int val)
	{
		if(val>=0 && val<=61)
			return dict[val];
		else
			return '0';
	}
	
	private static int Convert(char val)
	{
		for(int i=0;i<62;i++)
		{
			if(dict[i] == val)
				return i;
		}
		return 0;
	}
	
	public static int SixtyTwoScale(String value)
	{
		String val = value;
		int length = val.length();
		int ret = 0;
		for (int i = 0; i < length; i++)
		{
			int v = (int) Math.pow(62, (length - i - 1));
			char c = val.charAt(i);
			int t = Convert (c);
			ret += t * v;
		}
		return ret;
	}
	
	public static String SixtyTwoScale(int value_long)
	{
		int val = value_long;
		String res = "";
		if(val == 0)
			return "0";
		int ret = val;
		while (ret > 0)
		{
			int v = ret % 62;
			res = Convert(v) + res;
			ret = ret / 62;
		}
		return res;
	}
	
	
}
