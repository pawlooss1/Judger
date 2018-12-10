package com.pawel_dlugosz;

import java.io.*;
import java.util.*;

public class FileLoader {
    private String directory;
    private LinkedHashMap<String, Judgement> loadedJudgements = new LinkedHashMap<>();
    private LinkedList<Judge> loadedJudges = new LinkedList<>();
    private LinkedList<Statute> loadedStatutes = new LinkedList<>();

    public FileLoader(String directory) {
        this.directory = directory;
    }

    public LinkedHashMap<String, Judgement> loadFiles() {
        File directory = new File(this.directory);
        JudgementParser loader = new JudgementParser();
        for (File fileEntry : directory.listFiles()) {
            if (!fileEntry.isDirectory()) {
                try {
                    InputStream streamWithJson = new FileInputStream(fileEntry);
                    List<Judgement> loadedFromFile = loader.readJsonStream(streamWithJson);
                    for (Judgement judgement : loadedFromFile) {
                        this.loadedJudgements.put(judgement.getCaseNumber(), judgement);
                        for (Judge judge : judgement.getJudges())
                            this.loadedJudges.add(judge);
                        for (Statute statute : judgement.getStatutes())
                            this.loadedStatutes.add(statute);
                    }
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return loadedJudgements;
    }

    public SortedSet<Judge> countJudges() {
        SortedSet<Judge> result = new TreeSet<>();
        while (!this.loadedJudges.isEmpty()) {
            Judge currentJudge = loadedJudges.getFirst();
            while (loadedJudges.removeFirstOccurrence(currentJudge))
                currentJudge.incrementNumberOfJudgements();
            result.add(currentJudge);
        }
        return result;
    }

    public SortedSet<Statute> countStatutes() {
        SortedSet<Statute> result = new TreeSet<>();
        while (!this.loadedStatutes.isEmpty()) {
            Statute currentStatute = loadedStatutes.getFirst();
            while (loadedStatutes.removeFirstOccurrence(currentStatute))
                currentStatute.incrementOccurances();
            result.add(currentStatute);
        }
        return result;
    }
}
