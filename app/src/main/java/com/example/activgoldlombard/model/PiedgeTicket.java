package com.example.activgoldlombard.model;

import java.io.Serializable;
import java.util.Date;

public class PiedgeTicket implements Serializable {

    public SampleType sampleType;
    public String date;
    public double amountForHand;
    public double credit;

    public PiedgeTicket() {
    }

    public PiedgeTicket( SampleType sampleType, String date, double amountForHand, double credit) {
        this.sampleType = sampleType;
        this.date = date;
        this.amountForHand = amountForHand;
        this.credit = credit;
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

    public void setAmountForHand(double amountForHand) {
        this.amountForHand = amountForHand;
    }

    public double getCredit() {
        return credit;
    }

    public void setCredit(double credit) {
        this.credit = credit;
    }
}
