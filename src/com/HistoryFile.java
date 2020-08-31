package com;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.nio.file.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class HistoryFile {
    private String name;
    private String extension;
    private Date date;
    private Path path;

    private String historyMessage;

    public HistoryFile(String name, String extension) {
        this.name = name;
        this.extension = extension;
    }

    public void createFile(){
        try {
            path = Paths.get(String.format("%s.%s",name,extension));
            if(!Files.exists(Path.of( name+"."+extension)))
                if (name.length()!=0&extension.length()!=0){
                    Files.createFile(path);
                }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void readFile(int countMessage) {
        try {
            List<String> read=Files.readAllLines(path);
            int count=read.size();
            if(count-countMessage<0||countMessage==-1){
                for(int i=0;i<count;i++) {
                    historyMessage=historyMessage+read.get(i)+"\n";
                }
            }else{
                for(int i=count-countMessage;i<count;i++) {
                    historyMessage=historyMessage+read.get(i)+"\n";
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getHistoryMessage(int countMessage) {
        readFile(countMessage);
        return historyMessage;
    }


    public void writeFile(String message) throws IOException {
        SimpleDateFormat dateFormat=new SimpleDateFormat("ddMMyyyy");
        date=new Date();
        String s=dateFormat.format(date)+" "+message;
        Files.write(path, s.getBytes(), StandardOpenOption.APPEND);
    }

}
