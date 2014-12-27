package com.ruchi.engine.utils;

import opennlp.tools.namefind.NameFinderME;

import java.io.*;

/**
 * Created by brusoth on 12/13/2014.
 */
public class SerializeObject {
    private TrainNER object;

    public void saveObject(){
        TrainNER ner=new TrainNER();
        ner.train();
        try
        {
            FileOutputStream fileOut =
                    new FileOutputStream("res/FoodNER.ser");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(ner);
            out.close();
            fileOut.close();
        }catch(IOException i)
        {
            i.printStackTrace();
        }
    }

    public NameFinderME getObject(){
        try
        {
            FileInputStream fileIn = new FileInputStream("res/FoodNER.ser");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            object = (TrainNER) in.readObject();
            in.close();
            fileIn.close();
        }catch(IOException i)
        {
            i.printStackTrace();
        }catch(ClassNotFoundException c)
        {
            c.printStackTrace();
        }
        return object.getobject();
    }

    public static void main(String args[]){
        new SerializeObject().saveObject();
    }
}
