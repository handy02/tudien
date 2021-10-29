package com.example.tudien;

import java.io.*;
import java.util.Scanner;

public class DictionaryManagement extends Dictionary {

    public void insertFromCommandline() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Nhập số từ muốn thêm:  ");
        int n = sc.nextInt();
        sc.nextLine();
        for (int i = 0; i < n; i++) {
            Scanner sc1 = new Scanner(System.in);
            System.out.print("Nhập từ Tiếng Anh: ");
            String target = sc.nextLine();
            System.out.print("Nhập nghĩa của từ: ");
            String explain = sc1.nextLine();
            Word a = new Word(target, explain);
            list.add(a);
        }
    }

    public void insertFromFile() {
        try {
            File file = new File("src/main/java/com/example/tudien/data/dictionaries.txt");
            Scanner sc = new Scanner(file);
            while (sc.hasNextLine()) {
                String data = sc.nextLine();
                String[] split = data.split("\\t");
                if(split.length == 2) {
                    Word temp = new Word(split[0], split[1]);
                    list.add(temp);
                }
            }
            sc.close();
        }
        catch (FileNotFoundException e) {
            System.out.println("An error");
        }
    }

    public void dictionaryLookup()
    {
        System.out.println("Nhập từ cần tra: ");
        Scanner sc = new Scanner(System.in);
        String input = sc.nextLine();
        boolean found = false;
        for(int i = 0 ; i < list.size(); i++) {
            Word word = list.get(i);
            if(word.getWord_target().equals(input)) {
                found = true;
                System.out.println("Từ bạn tra có nghĩa là: " + word.getWord_explain());
                break;
            }
        }
        if(!found) {
            System.out.println("Không tìm thấy từ");
        }

    }
    public void addNewWord(String word_target, String word_explain) throws IOException {
        boolean check = true;
        for(Word w : list) {
            if(w.getWord_target().equalsIgnoreCase(word_target)) {
                check = false;
                break;
            }
        }
        if(!check) {
            System.out.println("Từ này đã có trong từ điển");
        }
        else {
            list.add(new Word(word_target,word_explain));
        }
    }


    public void removeWord(String word) {
        for(Word w : list) {
            if(w.getWord_target().equalsIgnoreCase(word)) {
                list.remove(w);
                break;
            }
        }
    }

    public void editWord(String word) {
        int mark = 0;
        boolean check = false;
        for(int i = 0 ; i < list.size(); i++) {
            Word w = list.get(i);
            if(w.getWord_target().equalsIgnoreCase(word)) {
                check = true;
                mark = i;
                break;
            }
        }
        if(check) {
            Scanner sc = new Scanner(System.in);
            System.out.println("Nhập nghĩa mới: ");
            String meaning = sc.nextLine();
            System.out.println("Sửa từ thành công");
            list.set(mark, new Word(list.get(mark).getWord_target(), meaning));
        }
        else {
            System.out.println("Không tìm thấy từ bạn cần sửa");
        }
    }
    public void dictionaryExportToFile() throws IOException {
        FileWriter fw = new FileWriter("src/main/java/com/example/tudien/data/dictionaries.txt");
        for (Word word : list) {
            fw.write(String.format("%s\t%s\n", word.getWord_target(), word.getWord_explain()));
        }
        fw.close();
    }
}
