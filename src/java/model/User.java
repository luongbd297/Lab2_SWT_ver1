/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author 84354
 */
public class User {

    String account, password, userName, email, phone, address, role;

    public User() {
    }

    public User(String account, String password, String userName, String email, String phone, String address, String role) {
        this.account = account;
        this.password = password;
        this.userName = userName;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.role = role;
    }

    public User(String account, String password, String userName, String role) {
        this.account = account;
        this.password = password;
        this.userName = userName;
        this.role = role;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "User{" + "account=" + account + ", userName=" + userName + ", email=" + email + ", phone=" + phone + ", address=" + address + '}';
    }

}
