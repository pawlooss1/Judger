package com.pawel_dlugosz;

import java.io.*;
import java.util.*;

public class App {
    private SortedSet<Judge> judges; // = new TreeSet<>();
    private SortedSet<Statute> statutes; // = new TreeSet<>();
    private List<Judgement> judgements;


    public List<Judge> take10Judges() {
        List<Judge> topJudges = new ArrayList<>();
        Iterator<Judge> it = judges.iterator();
        for (int i = 0; i < 10 && it.hasNext(); i++)
            topJudges.add(it.next());
        return topJudges;
    }

    public List<Statute> take10Statues() {
        List<Statute> topStatutes = new ArrayList<>();
        Iterator<Statute> it = statutes.iterator();
        for (int i = 0; i < 10 && it.hasNext(); i++)
            topStatutes.add(it.next());
        return topStatutes;
    }

    public String printRubrum(String caseSignature) {
        for (Judgement judgement : judgements) {
            if (judgement.getCaseNumber().equals(caseSignature))
                return judgement.toString();
        }
        return "Brak orzeczenia o danej sygnaturze";
    }

    public static void main(String[] args) {        //JLine, single responsibility principle
        if (args.length != 1) {
            System.out.println("Niepoprawne argumenty. Podaj ścieżkę dostępu do katalogu z orzeczeniami.");
            System.exit(1);
        }
        App judger = new App();
        FileLoader judgementLoader = new FileLoader(args[0]);
        judger.judgements = judgementLoader.loadFiles();
        judger.judges = judgementLoader.countJudges();
        judger.statutes = judgementLoader.countStatutes();

        List<Judge> testTop10 = judger.take10Judges();
        for (Judge judge : testTop10) {
            System.out.println(judge.getName() + " " + judge.getNumberOfJudgements());
        }
        /*List<Statute> testTop10Statutes = judger.take10Statues();
        for(Statute statute : testTop10Statutes)
            System.out.println(statute.getJournalTitle() + " - " + statute.getNumberOfOccurances());
        */
        for (Judgement judgement : judger.judgements)
            System.out.println(judgement.toString());


    }
}
