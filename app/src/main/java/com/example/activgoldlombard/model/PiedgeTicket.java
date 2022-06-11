package com.example.activgoldlombard.model;

import java.io.Serializable;

public class PiedgeTicket implements Serializable {
    public String id;
    public SampleType sampleType;
    public String date;
    public long amountForHand;
    public double credit;
    public boolean paid;


    public PiedgeTicket() {
    }

    public PiedgeTicket( SampleType sampleType, String date, long amountForHand, double credit) {
        this.sampleType = sampleType;
        this.date = date;
        this.amountForHand = amountForHand;
        this.credit = credit;
    }

    public PiedgeTicket(String id, String date, double credit) {
        this.id = id;
        this.date = date;
        this.credit = credit;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public SampleType getSampleType() {
        return sampleType;
    }

    public void setSampleType(SampleType sampleType) {
        this.sampleType = sampleType;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getAmountForHand() {
        return amountForHand;
    }

    public void setAmountForHand(long amountForHand) {
        this.amountForHand = amountForHand;
    }

    public double getCredit() {
        return credit;
    }

    public void setCredit(double credit) {
        this.credit = credit;
    }

    public boolean isPaid() {
        return paid;
    }

    public void setPaid(boolean paid) {
        this.paid = paid;
    }
}
