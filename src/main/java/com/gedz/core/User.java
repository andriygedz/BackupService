package com.gedz.core;

import com.gedz.core.Todo;

/**
 * Created by Gedz on 07-Dec-16.
 */
public class User {
    public long id;
    public String username;
    public String email;
    public Todo[] todos;

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        for(Todo todo : todos){
            sb.append(username).append(";").append(todo.toString()).append("\n");
        }
        return sb.toString();
    }
}
