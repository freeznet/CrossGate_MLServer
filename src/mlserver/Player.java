package mlserver;

public class Player {
	private String cdkey = "";
	private int RegNumber = 0;
	private int online = 0;
	private String index ="";
	private String fdIndex = "";
	private int level = 0;
	private int faceNumber = 0;
	private int DataPlaceNumber = 0;
	private String name = "";
	private String titleName = "";
	private int guildID = -1;
	private int guildTitleID = -1;
	public Player()
	{
		
	}

	/**
	 * @return the index
	 */
	public String getIndex() {
		return index;
	}




	/**
	 * @param index the index to set
	 */
	public void setIndex(String index) {
		this.index = index;
	}




	/**
	 * @return the fdIndex
	 */
	public String getFdIndex() {
		return fdIndex;
	}




	/**
	 * @param fdIndex the fdIndex to set
	 */
	public void setFdIndex(String fdIndex) {
		this.fdIndex = fdIndex;
	}




	/**
	 * @return the faceNumber
	 */
	public int getFaceNumber() {
		return faceNumber;
	}


	/**
	 * @param faceNumber the faceNumber to set
	 */
	public void setFaceNumber(int faceNumber) {
		this.faceNumber = faceNumber;
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
	 * @return the online
	 */
	public int getOnline() {
		return online;
	}
	/**
	 * @param online the online to set
	 */
	public void setOnline(int online) {
		this.online = online;
	}
	/**
	 * @return the dataPlaceNumber
	 */
	public int getDataPlaceNumber() {
		return DataPlaceNumber;
	}
	/**
	 * @param dataPlaceNumber the dataPlaceNumber to set
	 */
	public void setDataPlaceNumber(int dataPlaceNumber) {
		DataPlaceNumber = dataPlaceNumber;
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
	 * @return the titleName
	 */
	public String getTitleName() {
		return titleName;
	}
	/**
	 * @param titleName the titleName to set
	 */
	public void setTitleName(String titleName) {
		this.titleName = titleName;
	}
	/**
	 * @return the guildID
	 */
	public int getGuildID() {
		return guildID;
	}
	/**
	 * @param guildID the guildID to set
	 */
	public void setGuildID(int guildID) {
		this.guildID = guildID;
	}
	/**
	 * @return the guildTitleID
	 */
	public int getGuildTitleID() {
		return guildTitleID;
	}
	/**
	 * @param guildTitleID the guildTitleID to set
	 */
	public void setGuildTitleID(int guildTitleID) {
		this.guildTitleID = guildTitleID;
	}
	
	public String makeDBGetEntryString()
	{
		String ret = "";
		ret = "DBGetEntryString successful " + this.getOnline() + "|" + this.getLevel() + "|" + this.getTitleName() + "|" +this.getFaceNumber() + "|" + "-1" + "|" + " " + "db_addressbook" + " " + this.getCdkey() + "#" + Tools.SixtyTwoScale(this.getRegNumber()) + " "+this.getOnline()+" 0";
		return ret;
	}
	
}
