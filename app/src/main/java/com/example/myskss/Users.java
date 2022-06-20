package com.example.myskss;

import com.google.firebase.Timestamp;

import java.util.ArrayList;
import java.util.List;

public class Users {

    public Users() {

    }

    String name, card, email, password;

    public Users(String name, String card, String email, String password) {
        this.name = name;
        this.card = card;
        this.email = email;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCard() {
        return card;
    }

    public void setCard(String card) {
        this.card = card;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    private List<Integer> book = new ArrayList<Integer>();
    private List<Integer> fine = new ArrayList<Integer>();
    private List<Integer> re = new ArrayList<Integer>();
    private List<Timestamp> date = new ArrayList<Timestamp>();
    private String fcmToken;
    private int left_fine;

    public List<Integer> getBook() {
        return book;
    }

    public void setBook(List<Integer> book) {
        this.book = book;
    }

    public List<Integer> getFine() {
        return fine;
    }

    public void setFine(List<Integer> fine) {
        this.fine = fine;
    }

    public List<Integer> getRe() {
        return re;
    }

    public void setRe(List<Integer> re) {
        this.re = re;
    }

    public List<Timestamp> getDate() {
        return date;
    }

    public void setDate(List<Timestamp> date) {
        this.date = date;
    }

    public String getFcmToken() {
        return fcmToken;
    }

    public void setFcmToken(String fcmToken) {
        this.fcmToken = fcmToken;
    }

    public int getLeft_fine() {
        return left_fine;
    }

    public void setLeft_fine(int left_fine) {
        this.left_fine = left_fine;
    }

}
