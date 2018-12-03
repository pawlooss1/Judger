package com.pawel_dlugosz;

import java.io.*;
import java.util.LinkedList;
import java.util.*;

public class App {
    private List<Judgement> loadedJudgements = new LinkedList<>();
    private LinkedList<Judge> loadedJudges = new LinkedList<>();
    private LinkedList<Statute> loadedStatutes = new LinkedList<>();
    private SortedSet<Judge> judges = new TreeSet<>();
    private SortedSet<Statute> statutes = new TreeSet<>();

    public void loadFiles(String dir){
        File directory = new File(dir);
        JudgementParser loader = new JudgementParser();
        for (File fileEntry : directory.listFiles()) {
            if(!fileEntry.isDirectory()) {
                try {
                    InputStream streamWithJson = new FileInputStream(fileEntry);
                    List<Judgement> loadedFromFile = loader.readJsonStream(streamWithJson);
                    for(Judgement judgement: loadedFromFile){
                        this.loadedJudgements.add(judgement);
                        for(Judge judge : judgement.getJudges())
                            this.loadedJudges.add(judge);
                        for(Statute statute : judgement.getStatutes())
                            this.loadedStatutes.add(statute);
                    }
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e){
                    e.printStackTrace();
                }
            }
        }
    }
    public void countOccurances(){
        while (!this.loadedJudges.isEmpty()){
            Judge currentJudge = loadedJudges.getFirst();
            while(loadedJudges.removeFirstOccurrence(currentJudge))
                currentJudge.incrementNumberOfJudgements();
            judges.add(currentJudge);
        }
        while (!this.loadedStatutes.isEmpty()){
            Statute currentStatute = loadedStatutes.getFirst();
            while(loadedStatutes.removeFirstOccurrence(currentStatute))
                currentStatute.incrementOccurances();
            statutes.add(currentStatute);
        }
    }
    public List<Judge> take10Judges(){
        List<Judge> topJudges = new ArrayList<>();
        Iterator<Judge> it = judges.iterator();
        for(int i=0; i<10 && it.hasNext(); i++)
            topJudges.add(it.next());
        return topJudges;
    }
    public List<Statute> take10Statues(){
        List<Statute> topStatutes = new ArrayList<>();
        Iterator<Statute> it = statutes.iterator();
        for(int i=0; i<10 && it.hasNext(); i++)
            topStatutes.add(it.next());
        return topStatutes;
    }
    public String printRubrum(String caseSignature){
        for(Judgement judgement : loadedJudgements){
            if(judgement.getCaseNumber().equals(caseSignature))
                return judgement.toString();
        }
        return "Brak orzeczenia o danej sygnaturze";
    }
    public static void main(String[] args) {        //JLine, single responsibility principle
        App judger = new App();
        judger.loadFiles(args[0]);
        judger.countOccurances();

        List<Judge> testTop10 = judger.take10Judges();
        for(Judge judge : testTop10){
            System.out.println(judge.getName() + " " + judge.getNumberOfJudgements());
        }
        /*List<Statute> testTop10Statutes = judger.take10Statues();
        for(Statute statute : testTop10Statutes)
            System.out.println(statute.getJournalTitle() + " - " + statute.getNumberOfOccurances());
        */
        for(Judgement judgement : judger.loadedJudgements)
            System.out.println(judgement.toString());


    }
}
