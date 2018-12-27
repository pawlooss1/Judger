package com.pawel_dlugosz;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HtmlParser {
    IJudgementBuilder builder = new JudgementBuilder();

    public List<Judgement> readHtmlDocument(Document document) {
        Elements labels = document.select("table .info-list-label");
        Elements values = document.select("table .info-list-value");

        List<Judgement> result = new ArrayList<>();

        String date = this.readDate(labels, values);
        String caseNumber = this.readCaseNumber(document);
        CourtType courtType = this.readCourtType(labels, values);
        Map<Judge, List<SpecialRole>> judgesRoles = this.readJudges(labels, values);
        List<Judge> judges = new ArrayList<>();
        judges.addAll(judgesRoles.keySet());
        String substantiation = readSubstantiation(document);
        List<Statute> statutes = this.readStatutes(labels, values);

        result.add(builder.setDate(date)
                .setCaseNumber(caseNumber)
                .setCourtType(courtType)
                .setJudges(judges)
                .setJudgesRoles(judgesRoles)
                .setSubstantiation(substantiation)
                .setStatutes(statutes)
                .build());

        return result;
    }

    private String readDate(Elements labels, Elements values) {
        int index = this.getIndexOf("Data orzeczenia", labels);
        return values.get(index).text().substring(0, 10);
    }

    private String readCaseNumber(Document document) {
        Element caseNumberContent = document.select(".war_header").first();
        String text = caseNumberContent.text();
        String[] splitContent = text.split("-");
        return splitContent[0];
    }

    private CourtType readCourtType(Elements labels, Elements values) {
        int index = this.getIndexOf("Sąd", labels);
        String text = values.get(index).text().toLowerCase();
        return CourtType.parseFromString(text);
    }

    private Map<Judge, List<SpecialRole>> readJudges(Elements labels, Elements values) {
        try {
            int index = this.getIndexOf("Sędziowie", labels);
            String[] splitJudges = values.get(index).text().split("<br>");
            Map<Judge, List<SpecialRole>> result = new HashMap<>();
            for (String judgeWithRoles : splitJudges)
                result.put(getJudge(judgeWithRoles), getRoles(judgeWithRoles));
            return result;
        } catch (NoSuchElementException e) {
            return new HashMap<>();
        }
    }

    private Judge getJudge(String judgeWithRoles) {
        if (judgeWithRoles.charAt(0) == '<')
            return getJudge(judgeWithRoles.substring(judgeWithRoles.lastIndexOf('>') + 2));
        Pattern pattern = Pattern.compile("/(.*?)/");
        Matcher matcher = pattern.matcher(judgeWithRoles);
        if (matcher.find())
            return new Judge(judgeWithRoles.substring(0, judgeWithRoles.indexOf('/')));
        else
            return new Judge(judgeWithRoles);
    }

    private List<SpecialRole> getRoles(String judgeWithRoles) {
        if (judgeWithRoles.charAt(0) == '<')
            return getRoles(judgeWithRoles.substring(judgeWithRoles.lastIndexOf('>') + 2));
        List<SpecialRole> result = new ArrayList<>();
        Pattern pattern = Pattern.compile("/(.*?)/");
        Matcher matcher = pattern.matcher(judgeWithRoles);
        if (matcher.find()) {
            String[] roles = matcher.group(1).split(" ");
            for (String role : roles)
                result.add(SpecialRole.parseFromString(role));
        } else {
            result.add(SpecialRole.NONE);
        }
        return result;
    }

    private String readSubstantiation(Document document) {
        Elements labels = document.select("div.lista-label");
        Elements values = document.select("td.info-list-label-uzasadnienie");
        try {
            int index = this.getIndexOf("Uzasadnienie", labels);
            return values.get(index).text();
        } catch (NoSuchElementException e) {
            return "Brak uzasadnienia";
        }
    }

    private List<Statute> readStatutes(Elements labels, Elements values) {
        int index;
        try {
            index = this.getIndexOf("Powołane przepisy", labels);
        } catch (NoSuchElementException e) {
            return new LinkedList<Statute>();
        }
        Elements statutesDetails = values.get(index).select("a");
        Elements textContent = values.get(index).select("span");
        List<Statute> result = new LinkedList<>();
        for (int i = 0; i < statutesDetails.size(); i++) {
            result.add(this.readStatute(statutesDetails.get(i), textContent.get(i)));
        }
        return result;
    }

    private Statute readStatute(Element statute, Element textContent) {
        String journalTitle = textContent.text();
        List<Integer> journalNumbers = new ArrayList<>();
        String[] words = statute.text().split(" ");
        for (String word : words) {
            try {
                int number = Integer.parseInt(word);
                journalNumbers.add(number);
            } catch (NumberFormatException e) {
            }
        }
        return new Statute(journalTitle, journalNumbers.get(1), journalNumbers.get(0), journalNumbers.get(2));
    }

    private int getIndexOf(String label, Elements labels) {
        for (int i = 0; i < labels.size(); i++) {
            if (labels.get(i).text().equals(label))
                return i;
        }
        throw new NoSuchElementException("Brak podanej etykiety w tabeli.");
    }
}
