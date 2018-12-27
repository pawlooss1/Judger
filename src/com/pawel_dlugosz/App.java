package com.pawel_dlugosz;

import java.io.*;
import java.util.*;

public class App {
    private SortedSet<Judge> judges; // = new TreeSet<>();
    private SortedSet<Statute> statutes; // = new TreeSet<>();
    private LinkedHashMap<String, Judgement> judgements;

    public static void main(String[] args) {        //JLine, single responsibility principle
        App judger = new App();
        FileLoader judgementLoader = new FileLoader(args[0]);
        try {
            judger.judgements = judgementLoader.loadFiles();
            judger.judges = judgementLoader.countJudges();
            judger.statutes = judgementLoader.countStatutes();
        }
        catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            System.exit(1);
        }
        List<Judge> testTop10 = Functions.take10Judges(judger.judges);
        for (Judge judge : testTop10) {
            System.out.println(judge.getName() + " " + judge.getNumberOfJudgements());
        }
        List<Statute> testTop10Statutes = Functions.take10Statues(judger.statutes);
        for (Statute statute : testTop10Statutes)
            System.out.println(statute.getJournalTitle() + " - " + statute.getNumberOfOccurances());

        List<String> testSignatures = Arrays.asList("U 2/87", "Uw 6/87", "P 3/87");
        System.out.println(Functions.getRubrums(testSignatures, judger.judgements));

        for (Judgement judgement : judger.judgements.values())
            System.out.println(judgement.toString());
    }
}
