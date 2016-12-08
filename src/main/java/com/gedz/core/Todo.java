package com.gedz.core;

/**
 * Created by Gedz on 07-Dec-16.
 */
public class Todo {
    public long id;
    public String subject;
    public String dueDate;
    public boolean done;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(id).append(";").append(subject).append(";").append(dueDate).append(";").append(done);
        return sb.toString();
    }
}
