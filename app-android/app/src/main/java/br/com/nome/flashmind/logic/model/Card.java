package br.com.nome.flashmind.logic.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Alessandro Pryds on 17/01/2017.
 */
public class Card implements Parcelable {
    private String frontText;
    private String backText;

    public Card() {
    }

    public Card(String frontText, String backText) {
        this.frontText = frontText;
        this.backText = backText;
    }

    public String getFrontText() {
        return frontText;
    }

    public void setFrontText(String frontText) {
        this.frontText = frontText;
    }

    public String getBackText() {
        return backText;
    }

    public void setBackText(String backText) {
        this.backText = backText;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.frontText);
        dest.writeString(this.backText);
    }

    protected Card(Parcel in) {
        this.frontText = in.readString();
        this.backText = in.readString();
    }

    public static final Parcelable.Creator<Card> CREATOR = new Parcelable.Creator<Card>() {
        @Override
        public Card createFromParcel(Parcel source) {
            return new Card(source);
        }

        @Override
        public Card[] newArray(int size) {
            return new Card[size];
        }
    };
}
