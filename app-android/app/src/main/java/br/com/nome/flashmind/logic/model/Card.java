package br.com.nome.flashmind.logic.model;

import android.os.Parcel;
import android.os.Parcelable;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.Property;

/**
 * Created by Alessandro Pryds on 17/01/2017.
 */
@Entity(indexes = {
        @Index(value = "frontText, backText DESC", unique = true)
})
public class Card implements Parcelable {

    @Id
    private int id;

    @Property(nameInDb = "FRONT_TEXT")
    private String frontText;
    @Property(nameInDb = "BACK_TEXT")
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

    public int getId() {
        return id;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.frontText);
        dest.writeString(this.backText);
    }

    public void setId(int id) {
        this.id = id;
    }

    protected Card(Parcel in) {
        this.id = in.readInt();
        this.frontText = in.readString();
        this.backText = in.readString();
    }

    @Generated(hash = 1863408420)
    public Card(int id, String frontText, String backText) {
        this.id = id;
        this.frontText = frontText;
        this.backText = backText;
    }

    public static final Creator<Card> CREATOR = new Creator<Card>() {
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
