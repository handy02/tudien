package com.example.tudien;

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class DictionaryCommandLine extends Dictionary{

    static DictionaryManagement manage = new DictionaryManagement();
    static DictionaryCommandLine show = new DictionaryCommandLine();

    public void showAllWords() {
        System.out.printf("%-8s|%-30s|%s\n", "No", "English", "Tiếng Việt");
        int count = 1;
        for (Word w : list) {
            String t = w.getWord_target();
            String e = w.getWord_explain();
            System.out.printf("%-8s|%-30s|%s\n",count , t, e);
            count ++;
        }
    }

    public void dictionaryBasic() {
        DictionaryManagement manager = new DictionaryManagement();
        DictionaryCommandLine show = new DictionaryCommandLine();
        manager.insertFromCommandline();
        show.showAllWords();
    }


    public void dictionaryAdvanced() throws IOException {
        int opt = -1;
        manage.insertFromFile();
        Scanner input = new Scanner(System.in);
        do {
            System.out.println("1. Tra từ");
            System.out.println("2. Thêm từ vào danh sách");
            System.out.println("3. Xóa từ khỏi danh sách");
            System.out.println("4. Sửa từ trong danh sách");
            System.out.println("5. Hiển thị tất cả các từ");
            System.out.println("6. Xuất ra file");
            System.out.println("7. Thoát");
            System.out.println("Nhập lựa chọn: ");
            try{
                opt = input.nextInt();
            }
            catch (InputMismatchException e) {
            }
            input.nextLine();
            switch (opt) {
                case 1: {
                    // manage.dictionaryLookup();
                    System.out.println("Nhập từ cần tra: ");
                    String tra = input.nextLine();
                    show.dictionarySearcher(tra);
                    System.out.println("Nhấn enter để tiếp tục ");
                    input.nextLine();
                    continue;
                }
                case 2: {
                    System.out.println("Nhập từ cần thêm: ");
                    String t = input.nextLine();
                    System.out.println("Nhập nghĩa của từ cần thêm: ");
                    String e = input.nextLine();
                    manage.addNewWord(t,e);
//                    show.showAllWords();
                    System.out.println("Nhấn enter để tiếp tục ");
                    input.nextLine();
                    continue;
                }
                case 3: {
                    System.out.println("Nhập từ cần xóa: ");
                    String word = input.nextLine();
                    manage.removeWord(word);
//                    show.showAllWords();
                    System.out.println("Nhấn enter để tiếp tục ");
                    input.nextLine();
                    continue;
                }

                case 4: {
                    System.out.println("Nhập từ cần sửa: ");
                    String word = input.nextLine();
                    manage.editWord(word);
                    System.out.println("Nhấn enter để tiếp tục ");
                    input.nextLine();
                    continue;
                }

                case 5: {
                    show.showAllWords();
                    System.out.println("Nhấn enter để tiếp tục ");
                    input.nextLine();
                    continue;
                }

                case 6: {
                    manage.dictionaryExportToFile();
                    System.out.println("Xuất file thành công!");
                    System.out.println("Nhấn enter để tiếp tục ");
                    input.nextLine();
                    continue;
                }
                case 7: {
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
    public void dictionarySearcher (String t) {
        System.out.printf("%-30s|%s\n", "English", "Tieng Viet");
        boolean check = false;
        for (Word w : list) {
            if (w.getWord_target().startsWith(t)) {
                System.out.printf("%-30s|%s\n", w.getWord_target(), w.getWord_explain());
                check = true;
            }
        }
        if (!check) {
            System.out.println("Không tìm thấy từ bạn cần tra!");
        }
    }
}
