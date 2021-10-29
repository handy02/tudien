package com.example.tudien;

import java.io.IOException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException{
        ArrayList<Word> myDic = new ArrayList<Word>();

        DictionaryManagement manage = new DictionaryManagement();
        DictionaryCommandLine command = new DictionaryCommandLine();
        DictionaryApplication application = new DictionaryApplication();
        int opt = -1;
        Scanner input = new Scanner(System.in);
        do {
            System.out.println("1. Từ điển command line ");
            System.out.println("2. Ứng dụng từ điển");
            System.out.println("3. Thoát");
            System.out.println("Nhập lựa chọn: ");
            try{
                opt = input.nextInt();
            }
            catch (InputMismatchException e) {
            }
            input.nextLine();
            switch (opt) {
                case 1: {
                    command.dictionaryAdvanced();
                    continue;
                }
                case 2: {
                    application.runApplication();
                    continue;
                }
                case 3: {
                    System.exit(0);
                }
                default: {
                    System.out.println("Yêu cầu không hợp lệ!");
                    System.out.print("Nhấn enter để tiếp tục ");
                    input.nextLine();
                    continue;
                }
            }
        }while (opt != 0);
    }
}


