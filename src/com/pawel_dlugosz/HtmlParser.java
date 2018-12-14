package com.pawel_dlugosz;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.*;

public class HtmlParser {
    IJudgementBuilder builder = new JudgementBuilder();

    public List<Judgement> readHtmlDocument(Document document) {
        List<Judgement> result = new ArrayList<>();
        String date = this.readDate(document);
        String caseNumber = this.readCaseNumber(document);
        CourtType courtType = this.readCourtType(document);
        Map<Judge, List<SpecialRole>> judgesRoles = readJudges(document);
        List<Judge> judges = new ArrayList<>();
        judges.addAll(judgesRoles.keySet());
        String substantiation = readSubstantiation(document);

        return result;
    }

    private String readDate(Document document) {
        Element dateContent = document.getElementsByClass("info-list-value").get(0);
        String text = dateContent.text();
        return text.substring(0, 10);
    }

    private String readCaseNumber(Document document) {
        Element caseNumberContent = document.getElementsByClass("war_header").first();
        String text = caseNumberContent.text();
        String[] splitContent = text.split("-");
        return splitContent[0];
    }

    private CourtType readCourtType(Document document) {
        Element courtTypeContent = document.getElementsByClass("info-list-value").get(2);
        String text = courtTypeContent.text().toLowerCase();
        if (text.matches(".*administracyjny.*"))
            return CourtType.ADMINISTRATIVE;
        else if (text.matches(".*konstytucyjny.*"))
            return CourtType.CONSTITUTIONAL_TRIBUNAL;
        else if (text.matches(".*powszechny.*"))
            return CourtType.COMMON;
        else if (text.matches(".*Izba.*"))
            return CourtType.NATIONAL_APPEAL_CHAMBER;
        else
            return CourtType.SUPREME;
    }

    private Map<Judge, List<SpecialRole>> readJudges(Document document) {
        Element judgesContent = document.getElementsByClass("info-list-value").get(3);
        List<String> splitText = Arrays.asList(judgesContent.text().split(" "));
        List<Judge> judges = new ArrayList<>();
        Map<Judge, List<SpecialRole>> result = new HashMap<>();
        Iterator<String> iterator = splitText.iterator();
        while (iterator.hasNext()) {
            String object = iterator.next();
            if (!object.matches("/.*")) {
                Judge currentJudge = new Judge(object + " " + iterator.next());
                judges.add(currentJudge);
            } else {
                List<SpecialRole> roles = new ArrayList<>();
                while (!object.matches(".*/")) {
                    roles.add(SpecialRole.parseFromString(object));
                    object = iterator.next();
                }
                roles.add(SpecialRole.parseFromString(object));
                result.put(judges.get(judges.size() - 1), roles);
            }
        }
        for (Judge judge : judges) {
            if (!result.containsKey(judge)) {
                List<SpecialRole> listToPut = new ArrayList<>();
                listToPut.add(SpecialRole.NONE);
                result.put(judge, listToPut);
            }
        }
        return result;
    }

    private String readSubstantiation(Document document) {
        Element textContent = document.getElementsByClass("info-list-value-uzasadnienie").get(1);
        return textContent.text();
    }

    private List<Statute> readStatutes(Document document) {
        Elements tableHeaders = document.getElementsByClass("lista-label");
        int i;
        for (i = 0; i < tableHeaders.size(); i++) {
            if (tableHeaders.get(i).text().equals("Powołane przepisy"))
                break;
        }
        Element statuteContent = document.getElementsByClass()
    }

}
