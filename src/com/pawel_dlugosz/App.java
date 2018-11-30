package com.pawel_dlugosz;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.*;

public class App {
    private List<Judgement> loadedJudgements = new LinkedList<>();
    private Set<Judge> judges = new HashSet<>();
    private Set<Statute> statutes = new HashSet<>();

    public List<Judge> prepare10Judges(){
        List<Judge> judgesInOrder = new ArrayList<>(judges);
        Collections.sort(judgesInOrder);
        return judgesInOrder.subList(0, 10);
    }
    public static void loadFiles(String dir){
        File directory = new File(dir);
        for (File fileEntry : directory.listFiles()) {
            if(!fileEntry.isDirectory()) {
                try {
                    InputStream streamWithJson = new FileInputStream(fileEntry);
                    List<Judgement> loadedFromFile =
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public static void main(String[] args) {
        App.loadFiles(args[0]);
    }
}
