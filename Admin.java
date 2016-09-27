package com.niit.martzone.model;

import javax.persistence.*;


import org.springframework.stereotype.Component;

@Entity
@Table(name = "admin")
@Component
public class Admin {
	@Id
	@Column(name="adminname")
	private String adminname;
	private String password;
	private int contactnumber;   
	public String getAdminname() {
		return adminname;
	}
	public void setAdminname(String adminname) {
		this.adminname = adminname;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public int getContactnumber() {
		return contactnumber;
	}
	public void setContactnumber(int contactnumber) {
		this.contactnumber = contactnumber;
	}
	
}
