package data;

import java.io.Serializable;

public class Administrator implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = -2250308882486389329L;
	private String id;
    private String password;
    private String name;
	
    public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getId() {
		return id;
	}
	
    public void setId(String id) {
		this.id = id;
	}
	
    public String getPassword() {
		return password;
	}
	
	public Administrator(String id, String password,String name) {
		super();
		this.id = id;
		this.password = password;
		this.name=name;
	}
	
	public Administrator(String id, String password) {
		super();
		this.id = id;
		this.password = password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
