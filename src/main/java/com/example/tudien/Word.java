package com.example.tudien;

import java.util.Locale;
import java.util.Objects;

public class Word implements Comparable<Word> {

    private String word_target;
    private String word_explain;

    public Word() {

    }

    public Word(String word) {
        this.word_target = word;
        this.word_explain = "";
    }
    public Word(String word_target, String word_explain) {
        this.word_target = word_target;
        this.word_explain = word_explain;
    }

    public String getWord_target()
    {
        return word_target;
    }

    public String getWord_explain()
    {
        return word_explain;
    }

    public void setWord_target(String word_target)
    {
        this.word_target = word_target;
    }

    public void setWord_explain(String word_explain)
    {
        this.word_explain = word_explain;
    }

    @Override
    public int compareTo(Word word) {
        return this.getWord_target().toLowerCase().compareTo(word.getWord_target().toLowerCase());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Word)) return false;
        Word word1 = (Word) o;
        return Objects.equals(getWord_target(), word1.getWord_target());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getWord_target());
    }

    @Override
    public String toString() {
        return this.word_target;
    }
}
