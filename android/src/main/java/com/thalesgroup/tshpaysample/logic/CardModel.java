package com.thalesgroup.tshpaysample.logic;

public class   CardModel{
    final  String nameOnCard,cardType,cardTypeDescription,maskedFPan,expiryDate,cipheredCardInfo;


    public CardModel(String nameOnCard, String cardType, String cardTypeDescription, String maskedFPan, String expiryDate, String cipheredCardInfo) {
        this.nameOnCard = nameOnCard;
        this.cardType = cardType;
        this.cardTypeDescription = cardTypeDescription;
        this.maskedFPan = maskedFPan;
        this.expiryDate = expiryDate;
        this.cipheredCardInfo = cipheredCardInfo;
    }

    public String getNameOnCard() {
        return nameOnCard;
    }

    public String getCardType() {
        return cardType;
    }

    public String getCardTypeDescription() {
        return cardTypeDescription;
    }

    public String getMaskedFPan() {
        return maskedFPan;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public String getCipheredCardInfo() {
        return cipheredCardInfo;
    }
}

