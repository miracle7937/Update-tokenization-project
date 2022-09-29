package com.thalesgroup.tshpaysample.logic.network.model;

public class EncryptedCardModel {
    final String fpan, exp, cvv,additionalInfo;

    public EncryptedCardModel(String fpan, String exp, String cvv, String additionalInfo) {
        this.fpan = fpan;
        this.exp = exp;
        this.cvv = cvv;
        this.additionalInfo = additionalInfo;
    }

    public String getFpan() {
        return fpan;
    }

    public String getExp() {
        return exp;
    }

    public String getCvv() {
        return cvv;
    }

    public String getAdditionalInfo() {
        return additionalInfo;
    }
}

