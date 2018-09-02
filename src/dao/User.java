package dao;


public class User {
	private Integer id;
	private String username;
	private String password;
	private String name;
	private String recovery_contact;
	private String pin;
	private Integer logged;

	public User() {

	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRecovery_contact() {
		return recovery_contact;
	}

	public void setRecovery_contact(String recovery_contact) {
		this.recovery_contact = recovery_contact;
	}

	public String getPin() {
		return pin;
	}

	public void setPin(String pin) {
		this.pin = pin;
	}

	public Integer getLogged() {
		return logged;
	}

	public void setLogged(Integer logged) {
		this.logged = logged;
	}

}
