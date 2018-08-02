package crud;

import java.sql.Date;

public class Contact {

	private int num;
	private String name;
	private String phone;
	private String email;
	private Date birthday;
	
	
	public int getNum() {
		return num;
	}
	public String getName() {
		return name;
	}
	public String getPhone() {
		return phone;
	}
	public String getEmail() {
		return email;
	}
	public Date getBirthday() {
		return birthday;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	
	@Override
	public String toString() {
		return "Contact [num=" + num + ", name=" + name + ", phone=" + phone + ", email=" + email + ", birthday="
				+ birthday + "]";
	}
	
	
	
}
