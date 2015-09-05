package ru.sweetsound.androidjunior.utils;

import org.xml.sax.InputSource;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;


public class ServiceXMLParser {

    private final static String TAG_RESULT = "result";
    private final static String TAG_QUOTES = "quotes";
    private final static String TAG_QUOTE = "quote";
    private final static String TAG_TOTAL_PAGES = "totalPages";
    private final static String TAG_ID = "id";
    private final static String TAG_DATE = "date";
    private final static String TAG_TEXT = "text";

    private XmlPullParser mParser;

    public ServiceXMLParser(){
        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            mParser = factory.newPullParser();
        } catch (XmlPullParserException e) {
            mParser = null;
        }
    }

    public ArrayList<Quote> parseResult(String xml) throws XmlPullParserException, IOException {
        ArrayList<Quote> list = new ArrayList<>();
        mParser.setInput(new StringReader(xml));
        mParser.nextTag();
        mParser.require(XmlPullParser.START_TAG, null, TAG_RESULT);
        while (mParser.next() != XmlPullParser.END_TAG) {
            if (mParser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = mParser.getName();
            if (name.equals(TAG_QUOTES)) {
                list.addAll(readQuoteArray());
            } else {
                skip();
            }
        }
        return list;
    }

    private ArrayList<Quote> readQuoteArray() throws IOException, XmlPullParserException {
        ArrayList<Quote> list = new ArrayList<>();
        mParser.require(XmlPullParser.START_TAG, null, TAG_QUOTES);
        while (mParser.next() != XmlPullParser.END_TAG) {
            if (mParser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = mParser.getName();
            if (name.equals(TAG_QUOTE)) {
                list.add(readQuote());
            } else {
                skip();
            }
        }
        return list;
    }


    private Quote readQuote() throws XmlPullParserException, IOException {
        String text = null;
        String id = null;
        String date = null;
        mParser.require(XmlPullParser.START_TAG, null, TAG_QUOTE);
        while (mParser.next() != XmlPullParser.END_TAG) {
            if (mParser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
                String name = mParser.getName();
                switch (name) {
                    case TAG_TEXT: text=readText();
                        break;
                    case TAG_DATE: date = readDate();
                        break;
                    case TAG_ID: id = readId();
                        break;
                    default: skip();
                        break;
                }
            }
        return new Quote(text,date,id);
        }


    private String readText() throws IOException, XmlPullParserException {
        mParser.require(XmlPullParser.START_TAG,null,TAG_TEXT);
        String text = readString();
        mParser.require(XmlPullParser.END_TAG,null,TAG_TEXT);
        return text;
    }

    private String readId() throws IOException, XmlPullParserException {
        mParser.require(XmlPullParser.START_TAG,null,TAG_ID);
        String id = readString();
        mParser.require(XmlPullParser.END_TAG,null,TAG_ID);
        return id;
    }

    private String readDate() throws IOException, XmlPullParserException {
        mParser.require(XmlPullParser.START_TAG,null,TAG_DATE);
        String date = readString();
        mParser.require(XmlPullParser.END_TAG,null,TAG_DATE);
        return date;
    }

    private String readString() throws IOException, XmlPullParserException {
        String result = "";
        if (mParser.next() == XmlPullParser.TEXT) {
            result = mParser.getText();
            mParser.nextTag();
        }
        return result;
    }

    private void skip() throws XmlPullParserException, IOException {
        if (mParser.getEventType() != XmlPullParser.START_TAG) {
            throw new IllegalStateException();
        }
        int depth = 1;
        while (depth != 0) {
            switch (mParser.next()) {
                case XmlPullParser.END_TAG:
                    depth--;
                    break;
                case XmlPullParser.START_TAG:
                    depth++;
                    break;
            }
        }
    }

}
