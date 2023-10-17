package com.uog.mhike.database;

import android.content.Intent;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;

public class Hike {
//variables for table columns and keys while passing params from entry activity to detail activity
    public static final String ID ="id";
    public static final String NAME="name";
    public static final String LOCATION="location";
    public static final String DATE="hike_date";
    public static final String PARKING="parking";
    public static final String LENGTH="length";
    public static final String DIFFICULTY="difficulty";
    public static final String DESCRIPTION="description";
    public static final String ADDITIONAL1="additional1";
    public static final String ADDITIONAL2="additional2";
    public static final String ADDITIONAL_NUM1="additionalnum1";
    public static final String ADDITIONAL_NUM2="additionalnum2";

    private Integer id;
    private String name;
    private String location;
    private String date;
    private String parking;
    private Double length;
    private String difficulty;
    private String description;
    private String additional1;
    private String additional2;
    private Double additionalNum1;
    private Double additionalNum2;

    public Hike() {
    }

    public Hike(Integer id, String name, String location, String date, String parking, Double length, String difficulty, String description, String additional1, String additional2, Double additionalNum1, Double additionalNum2) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.date = date;
        this.parking = parking;
        this.length = length;
        this.difficulty = difficulty;
        this.description = description;
        this.additional1 = additional1;
        this.additional2 = additional2;
        this.additionalNum1 = additionalNum1;
        this.additionalNum2 = additionalNum2;

    }

    public Hike(String name, String location, String date, String parking, Double length, String difficulty, String description, String additional1, String additional2, Double additionalNum1, Double additionalNum2) {
        this.name = name;
        this.location = location;
        this.date = date;
        this.parking = parking;
        this.length = length;
        this.difficulty = difficulty;
        this.description = description;
        this.additional1 = additional1;
        this.additional2 = additional2;
        this.additionalNum1 = additionalNum1;
        this.additionalNum2 = additionalNum2;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getParking() {
        return parking;
    }

    public void setParking(String parking) {
        this.parking = parking;
    }

    public double getLength() {
        return length;
    }

    public void setLength(double length) {
        this.length = length;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAdditional1() {
        return additional1;
    }

    public void setAdditional1(String additional1) {
        this.additional1 = additional1;
    }

    public String getAdditional2() {
        return additional2;
    }

    public void setAdditional2(String additional2) {
        this.additional2 = additional2;
    }

    public Double getAdditionalNum1() {
        return additionalNum1;
    }

    public void setAdditionalNum1(Double additionalNum1) {
        this.additionalNum1 = additionalNum1;
    }

    public Double getAdditionalNum2() {
        return additionalNum2;
    }

    public void setAdditionalNum2(Double additionalNum2) {
        this.additionalNum2 = additionalNum2;
    }
}
