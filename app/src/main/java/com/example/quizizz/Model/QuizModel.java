package com.example.quizizz.Model;



public class QuizModel {

    private String quizID;
    private String image, level, title;
    private int questions;

    public QuizModel(String quizID, String image, String level, String title, int questions) {
        this.quizID = quizID;
        this.image = image;
        this.level = level;
        this.title = title;
        this.questions = questions;
    }

    public QuizModel() {}

    public String getQuizID() {
        return quizID;
    }

    public void setQuizID(String quizID) {
        this.quizID = quizID;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getQuestions() {
        return questions;
    }

    public void setQuestions(int questions) {
        this.questions = questions;
    }
}
