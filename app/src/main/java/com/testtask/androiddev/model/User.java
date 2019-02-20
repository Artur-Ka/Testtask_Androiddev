package com.testtask.androiddev.model;

public class User {
    private final int _id;
    private String _name;
    private String _phone;
    private String _mail;
    private String _note;
    private byte[] _photo;

    public User(int id) {
        _id = id;
    }

    public int getId() {
        return _id;
    }

    public String getName() {
        return _name;
    }

    public String getPhone() {
        return _phone;
    }

    public String getMail() {
        return _mail;
    }

    public String getNote()
    {
        return _note;
    }

    public byte[] getPhoto()
    {
        return _photo;
    }

    public void setName(String name)
    {
        _name = name;
    }

    public void setPhone(String phone)
    {
        _phone = phone;
    }

    public void setMail(String mail)
    {
        _mail = mail;
    }

    public void setNote(String note)
    {
        _note = note;
    }

    public void setPhoto(byte[] photo)
    {
        _photo = photo;
    }
}