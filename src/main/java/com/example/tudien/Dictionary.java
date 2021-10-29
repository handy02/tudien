package com.example.tudien;

import java.util.ArrayList;
import java.util.Collections;

public class Dictionary {

    public static ArrayList<Word> list = new ArrayList<Word>();

    public ArrayList<Word> getList() {
        return list;
    }

    public void setList(ArrayList<Word> list) {
        this.list = list;
    }

    public void sortWords() {
        Collections.sort(list);
    }

    int search(String s) {
        Word toSearch = new Word(s);
        int i = Collections.binarySearch(list, toSearch);
        return i;
    }
}
