package com.example.virtuallibrary;


import org.json.JSONArray;

import java.util.ArrayList;

public class RoomInfo {

    private String roomID;
    private String roomName;
    private String numberOfUsers;
    private String roomType;
    private String subject;
    private String task;
    private JSONArray users;

    public RoomInfo(String roomID, String roomName, String numberOfUsers,
                    String roomType, String subject, String task, JSONArray users){
        this.roomID = roomID;
        this.roomName = roomName;
        this.numberOfUsers = numberOfUsers;
        this.roomType = roomType;
        this.subject = subject;
        this.task = task;
        this.users = users;
    }

    public String getRoomID(){
        return roomID;
    }

    public JSONArray getUsers(){
        return  users;
    }

    public String getRoomName(){
        return roomName;
    }

    public String getNumberOfUsers(){
        return numberOfUsers;
    }

    public String getRoomType(){
        return roomType;
    }

    public String getSubject(){
        return subject;
    }

    public String getTask(){
        return task;
    }

}
