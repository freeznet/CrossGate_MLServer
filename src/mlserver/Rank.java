package mlserver;

public class Rank {
	private String table = "";
	private String cdkey = "";
	private int RegNumber = 0;
	private int value = 0;
	private int level = 0;
	private String job = "";
	private String name = "";
	private String str = "";
	private int unk1 = 0;
	private int unk2 = 0;
	public Rank(){}
	
	public Rank(String[] buf)
	{
		this.table = buf[1];
		this.cdkey = buf[2];
		this.RegNumber = Integer.parseInt(buf[3]);
		this.value = Integer.parseInt(buf[4]);
		this.str = buf[5];
		this.unk1 = Integer.parseInt(buf[6]);
		this.unk2 = Integer.parseInt(buf[7]);
	}
	/**
	 * @return the table
	 */
	public String getTable() {
		return table;
	}
	/**
	 * @param table the table to set
	 */
	public void setTable(String table) {
		this.table = table;
	}
	/**
	 * @return the cdkey
	 */
	public String getCdkey() {
		return cdkey;
	}
	/**
	 * @param cdkey the cdkey to set
	 */
	public void setCdkey(String cdkey) {
		this.cdkey = cdkey;
	}
	/**
	 * @return the regNumber
	 */
	public int getRegNumber() {
		return RegNumber;
	}
	/**
	 * @param regNumber the regNumber to set
	 */
	public void setRegNumber(int regNumber) {
		RegNumber = regNumber;
	}
	/**
	 * @return the value
	 */
	public int getValue() {
		return value;
	}
	/**
	 * @param value the value to set
	 */
	public void setValue(int value) {
		this.value = value;
	}
	/**
	 * @return the level
	 */
	public int getLevel() {
		return level;
	}
	/**
	 * @param level the level to set
	 */
	public void setLevel(int level) {
		this.level = level;
	}
	/**
	 * @return the job
	 */
	public String getJob() {
		return job;
	}
	/**
	 * @param job the job to set
	 */
	public void setJob(String job) {
		this.job = job;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the unk1
	 */
	public int getUnk1() {
		return unk1;
	}
	/**
	 * @param unk1 the unk1 to set
	 */
	public void setUnk1(int unk1) {
		this.unk1 = unk1;
	}
	/**
	 * @return the unk2
	 */
	public int getUnk2() {
		return unk2;
	}
	/**
	 * @param unk2 the unk2 to set
	 */
	public void setUnk2(int unk2) {
		this.unk2 = unk2;
	}
	/**
	 * @return the str
	 */
	public String getStr() {
		return str;
	}
	/**
	 * @param str the str to set
	 */
	public void setStr(String str) {
		this.str = str;
	}
	
	
	
}
