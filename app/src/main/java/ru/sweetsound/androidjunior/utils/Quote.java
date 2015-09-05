package ru.sweetsound.androidjunior.utils;


public class Quote {
    private String mText;
    private String mDate;
    private String mId;

    public Quote(String text, String date, String id) {
        mText = text;
        mDate = date;
        mId = id;
    }

    public String getText() {
        return mText;
    }

    public String getDate() {
        return mDate;
    }

    public String getId() {
        return mId;
    }
}
