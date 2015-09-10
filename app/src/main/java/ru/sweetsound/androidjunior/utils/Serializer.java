package ru.sweetsound.androidjunior.utils;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;


public class Serializer<T extends Serializable> {

    public void serialize(String file, T target) {
        ObjectOutputStream oos = null;
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(target);
            oos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (oos != null)
                    oos.close();
                if (fos != null)
                    fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public T deserialize(String file) {
        T result = null;
        FileInputStream fis = null;
        ObjectInputStream oin = null;
        try {
            fis = new FileInputStream(file);
            oin = new ObjectInputStream(fis);
            result = (T) oin.readObject();
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fis != null) fis.close();
                if (oin != null) oin.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

}
