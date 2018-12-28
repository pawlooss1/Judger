package com.pawel_dlugosz;

import java.util.*;

public class Functions {
    private List<Judge> judges;
    private List<Statute> statutes;
    private LinkedHashMap<String, Judgement> judgements;

    Functions(List<Judge> judges, List<Statute> statutes, LinkedHashMap<String, Judgement> judgements) {
        this.judges = judges;
        this.statutes = statutes;
        this.judgements = judgements;
    }

    public String getRubrums(List<String> signatures) {
        StringBuilder result = new StringBuilder();
        for (String signature : signatures) {
            result.append(this.getRubrum(signature));
            result.append("\n");
        }
        return result.toString();
    }

    public String getRubrum(String caseSignature) {
        caseSignature = clean(caseSignature);
        if (judgements.containsKey(caseSignature))
            return judgements.get(caseSignature).toString();
        return "Brak orzeczenia o danej sygnaturze.";
    }

    public static String clean(String caseSignature) {
        while (caseSignature.charAt(0) == ' ') {
            caseSignature = caseSignature.substring(1);
            if (caseSignature.length() == 0)
                return "";
        }
        while (caseSignature.charAt(caseSignature.length() - 1) == ' ')
            caseSignature = caseSignature.substring(0, caseSignature.length() - 1);
        return caseSignature;
    }

    public String getSubstantiation(String caseSignature) {
        caseSignature = clean(caseSignature);
        if (judgements.containsKey(caseSignature))
            return judgements.get(caseSignature).getSubstantiation() + "\n";
        return "Brak orzeczenia o danej sygnaturze.\n";
    }

    public int getNumberOfJudgementsForJudge(String name) {
        Iterator<Judge> iterator = judges.iterator();
        while (iterator.hasNext()) {
            Judge currentJudge = iterator.next();
            if (currentJudge.getName().equals(name))
                return currentJudge.getNumberOfJudgements();
        }
        throw new NoSuchElementException("Brak sędziego o danym nazwisku.\n");
    }

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

    public String getJudgementsByMonth() {
        int[] months = Functions.countJudgementsByMonth(judgements);
        StringBuilder result = new StringBuilder();
        result.append("Styczeń: ");
        result.append(months[0]);
        result.append("\nLuty: ");
        result.append(months[1]);
        result.append("\nMarzec: ");
        result.append(months[2]);
        result.append("\nKwiecień: ");
        result.append(months[3]);
        result.append("\nMaj: ");
        result.append(months[4]);
        result.append("\nCzerwiec: ");
        result.append(months[5]);
        result.append("\nLipiec: ");
        result.append(months[6]);
        result.append("\nSierpień: ");
        result.append(months[7]);
        result.append("\nWrzesień: ");
        result.append(months[8]);
        result.append("\nPaździernik: ");
        result.append(months[9]);
        result.append("\nListopad: ");
        result.append(months[10]);
        result.append("\nGrudzień: ");
        result.append(months[11]);
        result.append("\n");
        return result.toString();
    }

    private static int[] countJudgementsByMonth(LinkedHashMap<String, Judgement> judgements) {
        int[] result = new int[12];
        for (Judgement judgement : judgements.values()) {
            int month = judgement.getMonth();
            result[month]++;
        }
        return result;
    }

    public String getJudgementsByCourt() {
        Map<CourtType, Integer> courts = Functions.countJudgementsByCourt(judgements);
        StringBuilder result = new StringBuilder();
        result.append(CourtType.COMMON.toString());
        result.append(": ");
        result.append(courts.get(CourtType.COMMON));
        result.append("\n");
        result.append(CourtType.SUPREME.toString());
        result.append(": ");
        result.append(courts.get(CourtType.SUPREME));
        result.append("\n");
        result.append(CourtType.ADMINISTRATIVE.toString());
        result.append(": ");
        result.append(courts.get(CourtType.ADMINISTRATIVE));
        result.append("\n");
        result.append(CourtType.CONSTITUTIONAL_TRIBUNAL.toString());
        result.append(": ");
        result.append(courts.get(CourtType.CONSTITUTIONAL_TRIBUNAL));
        result.append("\n");
        result.append(CourtType.NATIONAL_APPEAL_CHAMBER.toString());
        result.append(": ");
        result.append(courts.get(CourtType.NATIONAL_APPEAL_CHAMBER));
        result.append("\n");
        return result.toString();
    }

    private static Map<CourtType, Integer> countJudgementsByCourt(LinkedHashMap<String, Judgement> judgements) {
        Map<CourtType, Integer> result = new HashMap<>();
        result.put(CourtType.COMMON, 0);
        result.put(CourtType.SUPREME, 0);
        result.put(CourtType.ADMINISTRATIVE, 0);
        result.put(CourtType.CONSTITUTIONAL_TRIBUNAL, 0);
        result.put(CourtType.NATIONAL_APPEAL_CHAMBER, 0);
        for (Judgement judgement : judgements.values()) {
            CourtType court = judgement.getCourtType();
            result.put(court, result.get(court) + 1);
        }
        return result;
    }

    public String getJudgementsByJudges() {
        int[] judgementsByJudges = Functions.countJudgementsByJudges(judgements);
        StringBuilder result = new StringBuilder();
        for (int i = 1; i < judgementsByJudges.length; i++) {
            result.append("Liczba sędziów: ");
            result.append(i);
            result.append(" - Suma orzeczeń: ");
            result.append(judgementsByJudges[i]);
            result.append("\n");
        }
        return result.toString();
    }

    private static int[] countJudgementsByJudges(LinkedHashMap<String, Judgement> judgements) {
        int[] result = new int[16];
        for (Judgement judgement : judgements.values()) {
            int judges = judgement.countJudges();
            result[judges]++;
        }
        return result;
    }

}
