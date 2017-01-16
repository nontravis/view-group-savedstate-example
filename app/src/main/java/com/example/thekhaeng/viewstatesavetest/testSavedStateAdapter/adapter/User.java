package com.example.thekhaeng.viewstatesavetest.testSavedStateAdapter.adapter;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by TheKhaeng
 */

public class User implements Parcelable{

    private String name;
    private int age;
    private String text;
    private boolean isCheck;

    public User( String name, int age ){

        this.name = name;
        this.age = age;
    }

    public String getName(){
        return name;
    }

    public void setName( String name ){
        this.name = name;
    }

    public int getAge(){
        return age;
    }

    public void setAge( int age ){
        this.age = age;
    }

    public String getText(){
        return text;
    }

    public void setText( String text ){
        this.text = text;
    }

    public void setCheck( boolean check ){
        isCheck = check;
    }

    public boolean isCheck(){
        return isCheck;
    }

    @Override
    public int describeContents(){
        return 0;
    }

    @Override
    public void writeToParcel( Parcel dest, int flags ){
        dest.writeString( this.name );
        dest.writeInt( this.age );
        dest.writeString( this.text );
        dest.writeByte( this.isCheck ? (byte) 1 : (byte) 0 );
    }

    protected User( Parcel in ){
        this.name = in.readString();
        this.age = in.readInt();
        this.text = in.readString();
        this.isCheck = in.readByte() != 0;
    }

    public static final Creator<User> CREATOR = new Creator<User>(){
        @Override
        public User createFromParcel( Parcel source ){
            return new User( source );
        }

        @Override
        public User[] newArray( int size ){
            return new User[size];
        }
    };
}
