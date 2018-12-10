package com.pawel_dlugosz;

import java.util.*;

public class Functions {
    public static String getRubrum(String caseSignature, LinkedHashMap<String, Judgement> judgements) {
        if (judgements.containsKey(caseSignature))
            return judgements.get(caseSignature).toString();
        return "Brak orzeczenia o danej sygnaturze";
    }

    public static String getRubrums(List<String> signatures, LinkedHashMap<String, Judgement> judgements) {
        StringBuilder result = new StringBuilder();
        for (String signature : signatures) {
            result.append(Functions.getRubrum(signature, judgements));
            result.append("\n");
        }
        return result.toString();
    }

    public static int getNumberOfJudgementsForJudge(String name, SortedSet<Judge> judges) {
        Iterator<Judge> iterator = judges.iterator();
        while (iterator.hasNext()) {
            Judge currentJudge = iterator.next();
            if(currentJudge.getName().equals(name))
                return currentJudge.getNumberOfJudgements();
        }
        throw new NoSuchElementException("Brak sędziego o danym nazwisku");
    }

    public static List<Judge> take10Judges(SortedSet<Judge> judges) {
        List<Judge> topJudges = new ArrayList<>();
        Iterator<Judge> it = judges.iterator();
        for (int i = 0; i < 10 && it.hasNext(); i++)
            topJudges.add(it.next());
        return topJudges;
    }

    public static List<Statute> take10Statues(SortedSet<Statute> statutes) {
        List<Statute> topStatutes = new ArrayList<>();
        Iterator<Statute> it = statutes.iterator();
        for (int i = 0; i < 10 && it.hasNext(); i++)
            topStatutes.add(it.next());
        return topStatutes;
    }
}
