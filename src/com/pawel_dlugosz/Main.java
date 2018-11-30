package com.pawel_dlugosz;

import java.util.*;

public class Main {
    private List<Judgement> loadedJudgements = new LinkedList<>();
    private Set<Judge> judges = new HashSet<>();
    private Set<Statute> statutes = new HashSet<>();

    public List<Judge> prepare10Judges(){
        List<Judge> judgesInOrder = new ArrayList<>(judges);
        Collections.sort(judgesInOrder);
        return judgesInOrder.subList(0, 10);
    }
    public static void main(String[] args) {
    }
}
