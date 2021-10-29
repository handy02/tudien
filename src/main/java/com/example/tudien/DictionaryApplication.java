package com.example.tudien;

import java.util.Locale;
import javax.speech.Central;
import javax.speech.synthesis.Synthesizer;
import javax.speech.synthesis.SynthesizerModeDesc;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import jfxtras.styles.jmetro.JMetro;
import jfxtras.styles.jmetro.JMetroStyleClass;
import jfxtras.styles.jmetro.Style;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class DictionaryApplication extends Application {

    Stage stage;
    TextField wordField;
    Label meaning;
    List<String> wordList;
    ObservableList<String> observableList;
    DictionaryManagement dictionary;

    @Override
    public void start(Stage stage) {


        dictionary = new DictionaryManagement();
        dictionary.insertFromFile();
        dictionary.sortWords();

        wordList = new ArrayList<>();
        for (Word word : dictionary.getList()) {
            wordList.add(word.getWord_target());
        }
        // Tạo MenuBar
        MenuBar menuBar = new MenuBar();

        // Tạo các Menu
        Menu fileMenu = new Menu("File");
        Menu helpMenu = new Menu("Help");

        // Tạo các MenuItem
        MenuItem addAWord = new MenuItem("Add a word");
        addAWord.setOnAction(event -> addAWordMenu());

        MenuItem editWord = new MenuItem("Edit a word");
        editWord.setOnAction(event -> {
            try {
                editAWordMenu();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        MenuItem deleteAWord = new MenuItem("Delete a word");
        deleteAWord.setOnAction(event -> deleteAWordMenu());

        MenuItem saveFile = new MenuItem("Save file");
        saveFile.setOnAction(event -> {
            try {
                saveFileMenu();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        // Thêm các MenuItem vào Menu.
        fileMenu.getItems().addAll(addAWord, editWord, deleteAWord, saveFile);

        // Thêm các Menu vào MenuBar
        menuBar.getMenus().addAll(fileMenu, helpMenu);
        menuBar.setStyle("-fx-background-color: white");

        //Tạo textfield input và các nút
        wordField= new TextField();
        wordField.setPrefWidth(251);
        wordField.setPrefHeight(30);
        wordField.setPromptText("Enter a word");
        wordField.setOnKeyReleased(e -> textFieldGo());

        Button lookUpButton = new Button("Look up");
        lookUpButton.setPrefHeight(30);
        Image magnifierImage = new Image(new File("src/main/java/com/example/tudien/icon/magnifier1.png").toURI().toString());
        lookUpButton.setGraphic(new ImageView(magnifierImage));
        lookUpButton.setStyle("""
                -fx-background-color:\s
                        #000000,
                        linear-gradient(#7ebcea, #2f4b8f),
                        linear-gradient(#426ab7, #263e75),
                        linear-gradient(#395cab, #223768);
                    -fx-text-fill: white;""");

        lookUpButton.setOnAction(e -> buttonLookUp());

        Button speakButton = new Button("Speak");
        speakButton.setPrefHeight(30);
        Image speakerImage = new Image(new File("src/main/java/com/example/tudien/icon/speaker.png").toURI().toString());
        speakButton.setGraphic(new ImageView(speakerImage));
        speakButton.setStyle("""
                -fx-background-color:\s
                        #000000,
                        linear-gradient(#7ebcea, #2f4b8f),
                        linear-gradient(#426ab7, #263e75),
                        linear-gradient(#395cab, #223768);
                    -fx-text-fill: white;""");
        speakButton.setOnAction(event -> speakButton());

        Button ggTranslate = new Button("Use Google Translate");
        ggTranslate.setPrefHeight(30);
        Image imageDecline = new Image(new File("src/main/java/com/example/tudien/icon/google.png").toURI().toString());
        ggTranslate.setGraphic(new ImageView(imageDecline));
        ggTranslate.setStyle("""
                -fx-background-color:\s
                        #000000,
                        linear-gradient(#7ebcea, #2f4b8f),
                        linear-gradient(#426ab7, #263e75),
                        linear-gradient(#395cab, #223768);
                    -fx-text-fill: white;""");
        ggTranslate.setOnAction(event -> {
            try {
                useGoogleApiButton();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        HBox input = new HBox(10);
        input.getChildren().addAll(wordField, lookUpButton, ggTranslate, speakButton);

        //Tạo scroll pane meaning
        meaning = new Label("Meaning");
        meaning.setAlignment(Pos.CENTER);
        meaning.setPrefWidth(550);
        meaning.setStyle("-fx-font-size: 15;");
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setContent(meaning);

        //Tạo danh sách từ
        observableList = FXCollections.observableList(wordList);
        ListView<String> recommendWordsList = new ListView<String>(observableList);
        recommendWordsList.setPrefWidth(250);

        //Khi bấm vào từ thì hiện ra nghĩa
        recommendWordsList.setOnMouseClicked(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                String str = recommendWordsList.getSelectionModel().getSelectedItems().toString();
                str = str.substring(1, str.length() - 1);
                wordField.setText(str);
                int result = dictionary.search(str);
                meaning.setText("Meaning\n\n-" + dictionary.getList().get(result).getWord_explain());
            }
        });
        HBox output = new HBox(10);
        output.getChildren().addAll(recommendWordsList, scrollPane);

        VBox mainPanel = new VBox();
        mainPanel.setStyle("-fx-background-color: linear-gradient(to top, #ff8489, #90d5ec);");
        mainPanel.setMargin(menuBar, new Insets(0,0,5,0));
        mainPanel.getStyleClass().add(JMetroStyleClass.BACKGROUND);
        mainPanel.getChildren().addAll(menuBar, input, output);
        Scene scene = new Scene(mainPanel, 832, 468);
        stage.setTitle("English - Vietnamese Dictionary");
        JMetro jMetro = new JMetro(Style.LIGHT);
        jMetro.setScene(scene);
        stage.setMinHeight(300);
        stage.setMinWidth(600);
        stage.setScene(scene);
        stage.show();
    }

    private void buttonLookUp() {
        String s = wordField.getText();
        if (!s.equals("")) {
            int result = dictionary.search(s);
            if (result >= 0) {
                meaning.setText("Meaning\n\n-" + dictionary.getList().get(result).getWord_explain());

            } else {
                meaning.setText("Word not found,you may want to use Google Translate button");
            }
        }
    }

    private void addAWordMenu() {
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Add a word");
        stage.setMinWidth(300);
        stage.setMinHeight(300);

        Label inputWordLabel = new Label();
        inputWordLabel.setText("Enter the word you want to add");

        TextField inputWord = new TextField();

        Label detailLabel = new Label();
        detailLabel.setText("Meaning");

        TextArea wordDetail = new TextArea();

        Button confirm = new Button();
        confirm.setPrefWidth(80);
        confirm.setPrefHeight(30);
        confirm.setText("Add");
        confirm.setStyle("""
                -fx-background-color:\s
                        #000000,
                        linear-gradient(#7ebcea, #2f4b8f),
                        linear-gradient(#426ab7, #263e75),
                        linear-gradient(#395cab, #223768);
                    -fx-text-fill: white;""");

        VBox addPane = new VBox(10);
        addPane.setAlignment(Pos.TOP_CENTER);
        addPane.setMargin(confirm, new Insets(60,0,0,0));
        addPane.getChildren().addAll(inputWordLabel, inputWord, detailLabel, wordDetail, confirm);

        confirm.setOnAction(event -> {
            String s = inputWord.getText();
            int result = Collections.binarySearch(dictionary.getList(), new Word(s));
            if (result < 0) {
                String s2 = wordDetail.getText();
                Word newWord = new Word(s, s2);
                dictionary.getList().add(newWord);
                dictionary.sortWords();
                wordList.clear();
                for (Word word : dictionary.getList()) {
                    wordList.add(word.getWord_target());
                }
                MessageBox.show("Word added succesfully", "");
            } else {
                MessageBox.show("Word already exists", "");
            }
        });
        Scene scene = new Scene(addPane, 300, 400);
        JMetro jMetro = new JMetro(Style.LIGHT);
        jMetro.setScene(scene);
        stage.setScene(scene);
        stage.show();
    }

    private void editAWordMenu() throws IOException {
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Edit a word");
        stage.setMinWidth(300);
        stage.setMinHeight(300);

        TextField inputField = new TextField();
        Label input = new Label();
        input.setText("Enter the Word You Want To edit");
        Label meaning = new Label();
        meaning.setText("Meaning");
        TextArea wordDetail = new TextArea();
        Button confirm = new Button();
        confirm.setPrefWidth(80);
        confirm.setPrefHeight(30);
        confirm.setText("Edit");
        confirm.setStyle("""
                -fx-background-color:\s
                        #000000,
                        linear-gradient(#7ebcea, #2f4b8f),
                        linear-gradient(#426ab7, #263e75),
                        linear-gradient(#395cab, #223768);
                    -fx-text-fill: white;""");
        confirm.setOnAction(event -> {
            String s = inputField.getText();
            int result = Collections.binarySearch(dictionary.getList(), new Word(s));
            if (result >= 0) {
                dictionary.getList().set(result, new Word(s, wordDetail.getText()));
                MessageBox.show("Word edited successfully", "");
            } else {
                MessageBox.show("Word can't be found", "");
            }
        });
        VBox editPane = new VBox(10);
        editPane.setAlignment(Pos.TOP_CENTER);
        editPane.setMargin(confirm, new Insets(60,0,0,0));
        editPane.getChildren().addAll(input, inputField, meaning, wordDetail, confirm);
        Scene scene = new Scene(editPane, 300, 300);
        JMetro jMetro = new JMetro(Style.LIGHT);
        jMetro.setScene(scene);
        stage.setScene(scene);
        stage.show();
    }

    private void deleteAWordMenu() {
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Delete a word");
        stage.setMinWidth(300);
        stage.setMinHeight(300);

        TextField inputField = new TextField();
        Label input = new Label();
        input.setText("Enter the Word You Want To Delete");
        Button confirm = new Button();
        confirm.setPrefWidth(80);
        confirm.setPrefHeight(30);
        confirm.setText("Delete");
        confirm.setStyle("""
                -fx-background-color:\s
                        #000000,
                        linear-gradient(#7ebcea, #2f4b8f),
                        linear-gradient(#426ab7, #263e75),
                        linear-gradient(#395cab, #223768);
                    -fx-text-fill: white;""");
        confirm.setOnAction(event -> {
            String s = inputField.getText();
            int result = Collections.binarySearch(dictionary.getList(), new Word(s));
            if (result >= 0) {
                dictionary.getList().remove(result);
                wordList.clear();
                for (Word word : dictionary.getList()) {
                    wordList.add(word.getWord_target());
                }
                MessageBox.show("Word deleted successfully", "");
            } else {
                MessageBox.show("Word can't be found", "");
            }
        });
        VBox deletePane = new VBox(10);
        deletePane.setAlignment(Pos.TOP_CENTER);
        deletePane.setMargin(confirm, new Insets(60,0,0,0));
        deletePane.getChildren().addAll(input, inputField, confirm);
        Scene scene = new Scene(deletePane, 300, 300);
        JMetro jMetro = new JMetro(Style.LIGHT);
        jMetro.setScene(scene);
        stage.setScene(scene);
        stage.show();
    }

    private void saveFileMenu() throws IOException {
        dictionary.dictionaryExportToFile();
        MessageBox.show("File saved successfully", "");
    }

    private void useGoogleApiButton() throws IOException {
        String s = GoogleApi.translate("en", "vi", wordField.getText());
        meaning.setText("Meaning\n\n" + s);
    }

    private void speakButton() {
        try {
            // Set property as Kevin Dictionary
            System.setProperty(
                    "freetts.voices",
                    "com.sun.speech.freetts.en.us"
                            + ".cmu_us_kal.KevinVoiceDirectory");

            // Register Engine
            Central.registerEngineCentral(
                    "com.sun.speech.freetts"
                            + ".jsapi.FreeTTSEngineCentral");

            // Create a Synthesizer
            Synthesizer synthesizer
                    = Central.createSynthesizer(
                    new SynthesizerModeDesc(Locale.US));

            // Allocate synthesizer
            synthesizer.allocate();

            // Speaks the given text
            // until the queue is empty.
            synthesizer.speakPlainText(
                    wordField.getText(), null);
            synthesizer.waitEngineState(
                    Synthesizer.QUEUE_EMPTY);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void textFieldGo() {
        String s = wordField.getText();
        List<String> a = new ArrayList<>();
        for (int i = 0 ; i < dictionary.getList().size(); ++i) {
            Word w = dictionary.getList().get(i);
            if (w.getWord_target().startsWith(s)) {
                a.add(w.getWord_target());
            }
        }
        observableList.clear();
        observableList.addAll(a);
        meaning.setText("Meaning");
    }
    public void runApplication() {
        launch();
    }

    public static void main(String[] args) {
        launch();
    }
}