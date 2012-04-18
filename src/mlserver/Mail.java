package mlserver;

public class Mail {
	private String fromCdkey = "";
	private int fromRegNumber = 0;
	private String toCdkey = "";
	private int toRegNumber = 0;
	private String msg = "";
	private int unk1 = 0;
	private int unk2 = 0;
	private int unk3 = 0;
	
	public Mail()
	{}
	
	public Mail(String[] t)
	{
		this.fromCdkey = t[1];
		this.fromRegNumber = Integer.parseInt(t[2]);
		this.toCdkey = t[3];
		this.toRegNumber = Integer.parseInt(t[4]);
		this.msg = t[5];
		this.unk1 = Integer.parseInt(t[6]);
		this.unk2 = Integer.parseInt(t[7]);
		this.unk3 = Integer.parseInt(t[8]);
	}
	
	public String getMailPacket()
	{
		String ret = "";
		ret = "Message " + this.fromCdkey + " " + this.fromRegNumber + " " + this.toCdkey + " " + this.toRegNumber + " " + this.msg + " " + this.unk1 + " " + this.unk2 + " " + this.unk3; 
		return ret;
	}

	/**
	 * @return the fromCdkey
	 */
	public String getFromCdkey() {
		return fromCdkey;
	}

	/**
	 * @param fromCdkey the fromCdkey to set
	 */
	public void setFromCdkey(String fromCdkey) {
		this.fromCdkey = fromCdkey;
	}

	/**
	 * @return the fromRegNumber
	 */
	public int getFromRegNumber() {
		return fromRegNumber;
	}

	/**
	 * @param fromRegNumber the fromRegNumber to set
	 */
	public void setFromRegNumber(int fromRegNumber) {
		this.fromRegNumber = fromRegNumber;
	}

	/**
	 * @return the toCdkey
	 */
	public String getToCdkey() {
		return toCdkey;
	}

	/**
	 * @param toCdkey the toCdkey to set
	 */
	public void setToCdkey(String toCdkey) {
		this.toCdkey = toCdkey;
	}

	/**
	 * @return the toRegNumber
	 */
	public int getToRegNumber() {
		return toRegNumber;
	}

	/**
	 * @param toRegNumber the toRegNumber to set
	 */
	public void setToRegNumber(int toRegNumber) {
		this.toRegNumber = toRegNumber;
	}

	/**
	 * @return the msg
	 */
	public String getMsg() {
		return msg;
	}

	/**
	 * @param msg the msg to set
	 */
	public void setMsg(String msg) {
		this.msg = msg;
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
	 * @return the unk3
	 */
	public int getUnk3() {
		return unk3;
	}

	/**
	 * @param unk3 the unk3 to set
	 */
	public void setUnk3(int unk3) {
		this.unk3 = unk3;
	}
	
	
}
