package com.practice.springbootstarter.user;

import java.util.Date;

import javax.validation.constraints.Past;
import javax.validation.constraints.Size;

public class User {

	private Integer id;

	@Size(min = 2, message = "Name should have atleast 2 charachters")
	private String name;

	@Past
	private Date bdate;

	protected User() {
	}

	public User(Integer id, String name, Date bdate) {
		super();
		this.id = id;
		this.name = name;
		this.bdate = bdate;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getBdate() {
		return bdate;
	}

	public void setBdate(Date bdate) {
		this.bdate = bdate;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", bdate=" + bdate + "]";
	}
}
