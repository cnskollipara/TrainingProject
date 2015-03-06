package com.mkyong.rest;

public class Employee {
    private String name;
    private String address;
    private String email;
    private int id;
    private long contact;
    private float salary;
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getId() {
        return this.id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getEmail() {
        return this.email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public long getContact() {
        return this.contact;
    }
    public void setContact(long contact) {
        this.contact = contact;
    }
    public float getSalary() {
        return this.salary;
    }
    public void setSalary(float salary) {
        this.salary = salary;
    }
    public String getAddress() {
        return this.address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
}
