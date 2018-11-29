package io.codenames.serverdata;


import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;

public class WordList {

    private static WordList instance = null;

    private static final ArrayList<String> words = new ArrayList<String>();



    private WordList() {

        try {
            InputStream fileInputStream  = WordList.class.getClass().getResourceAsStream("/CNwordslist.txt");
            BufferedReader br = new BufferedReader(new InputStreamReader(fileInputStream));

            String strLine;

            while ((strLine = br.readLine()) != null)   {
                // Print the content on the console
                words.add(strLine);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } 

    }

    public static String getWord(){
        if(instance == null)
            instance = new WordList();
        int rnd = new Random().nextInt(words.size());
        return words.get(rnd);
    }

    public static WordList getInstance() {
        if(instance == null)
            instance = new WordList();
        return instance;
    }
}
