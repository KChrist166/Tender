package com.example.tender.entities;

import android.net.Uri;

import java.util.List;

public class Profile {

    private String name;

    private String imageUrl;

    private Integer age;

    private String location;
    private String self;
    private String hobbies;
    private String zodiac;
    private String study;
    private String character;
    private String nickname;
    private String living;
    private String work;
    private List<Uri> changedImages;
    public Profile() {
    }
    public Profile(String zodiac, String study, String character, String nickname, String living, String work, List<Uri> changedImages, String self, String hobbies) {
        this.zodiac = zodiac;
        this.study = study;
        this.character = character;
        this.nickname = nickname;
        this.living = living;
        this.work = work;
        this.changedImages = changedImages;
        this.self = self;
        this.hobbies = hobbies;
    }
    public String getSelf() {
        return self;
    }

    public void setSelf(String self) {
        this.self = self;
    }
    public String getHobbies() {
        return hobbies;
    }

    public void setHobbies(String hobbies) {
        this.hobbies = hobbies;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getLiving() {
        return living;
    }

    public void setLiving(String living) {
        this.living = living;
    }
    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
    public String getWork() {
        return work;
    }

    public void setWork(String work) {
        this.work = work;
    }
    public List<Uri> getChangedImages() {
        return changedImages;
    }

    public void setChangedImages(List<Uri> name) {
        this.changedImages = name;
    }
    public String getCharacter() {
        return character;
    }

    public void setCharacter(String character) {
        this.character = character;
    }
    public String getStudy() {
        return study;
    }

    public void setStudy(String study) {
        this.study = study;
    }
    public String getZodiac() {
        return zodiac;
    }

    public void setZodiac(String zodiac) {
        this.zodiac = zodiac;
    }
    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}