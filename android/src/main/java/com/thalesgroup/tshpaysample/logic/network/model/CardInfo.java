package com.thalesgroup.tshpaysample.logic.network.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CardInfo {

    @SerializedName("nameOnCard")
    @Expose
    private String nameOnCard;
    @SerializedName("cardType")
    @Expose
    private String cardType;
    @SerializedName("cardTypeDescription")
    @Expose
    private String cardTypeDescription;
    @SerializedName("maskedFPan")
    @Expose
    private String maskedFPan;
    @SerializedName("expiryDate")
    @Expose
    private String expiryDate;
    @SerializedName("cipheredCardInfo")
    @Expose
    private String cipheredCardInfo;

    public String getNameOnCard() {
        return nameOnCard;
    }

    public void setNameOnCard(String nameOnCard) {
        this.nameOnCard = nameOnCard;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public String getCardTypeDescription() {
        return cardTypeDescription;
    }

    public void setCardTypeDescription(String cardTypeDescription) {
        this.cardTypeDescription = cardTypeDescription;
    }

    public String getMaskedFPan() {
        return maskedFPan;
    }

    public void setMaskedFPan(String maskedFPan) {
        this.maskedFPan = maskedFPan;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getCipheredCardInfo() {
        return cipheredCardInfo;
    }

    public void setCipheredCardInfo(String cipheredCardInfo) {
        this.cipheredCardInfo = cipheredCardInfo;
    }

}