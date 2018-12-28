package com.pawel_dlugosz;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

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
        if (!directory.isDirectory())
            throw new IllegalArgumentException("Podana ścieżka nie jest katalogiem");
        JsonParser jsonLoader = new JsonParser();
        HtmlParser htmlLoader = new HtmlParser();
        this.loadFromDirectory(directory, jsonLoader, htmlLoader);
        return loadedJudgements;
    }

    private void loadFromDirectory(File directory, JsonParser jsonLoader, HtmlParser htmlLoader) {
        for (File fileEntry : directory.listFiles()) {
            if (!fileEntry.isDirectory()) {
                if (fileEntry.getName().matches(".+\\.json"))
                    loadJson(fileEntry, jsonLoader);
                if (fileEntry.getName().matches(".+\\.html"))
                    loadHtml(fileEntry, htmlLoader);
            } else if (fileEntry.isDirectory())
                this.loadFromDirectory(fileEntry, jsonLoader, htmlLoader);
        }
    }

    private void loadJson(File file, JsonParser jsonLoader) {
        try {
            InputStream streamWithJson = new FileInputStream(file);
            List<Judgement> loadedFromFile = jsonLoader.readJsonStream(streamWithJson);
            this.putLoadedJudgements(loadedFromFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadHtml(File file, HtmlParser htmlLoader) {
        try {
            Document document = Jsoup.parse(file, "UTF-8");
            List<Judgement> loadedFromFile = htmlLoader.readHtmlDocument(document);
            this.putLoadedJudgements(loadedFromFile);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NoSuchElementException e) {
            e.printStackTrace();
        }
    }

    private void putLoadedJudgements(List<Judgement> loadedFromFile) {
        for (Judgement judgement : loadedFromFile) {
            this.loadedJudgements.put(judgement.getCaseNumber(), judgement);
            for (Judge judge : judgement.getJudges())
                this.loadedJudges.add(judge);
            for (Statute statute : judgement.getStatutes())
                this.loadedStatutes.add(statute);
        }
    }

    public List<Judge> countJudges() {
        List<Judge> result = new LinkedList<>();
        while (!this.loadedJudges.isEmpty()) {
            Judge currentJudge = loadedJudges.getFirst();
            while (loadedJudges.removeFirstOccurrence(currentJudge))
                currentJudge.incrementNumberOfJudgements();
            result.add(currentJudge);
        }
        Collections.sort(result);
        return result;
    }

    public List<Statute> countStatutes() {
        List<Statute> result = new LinkedList<>();
        while (!this.loadedStatutes.isEmpty()) {
            Statute currentStatute = loadedStatutes.getFirst();
            while (loadedStatutes.removeFirstOccurrence(currentStatute))
                currentStatute.incrementOccurances();
            result.add(currentStatute);
        }
        Collections.sort(result);
        return result;
    }
}
