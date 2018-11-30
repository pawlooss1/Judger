package com.pawel_dlugosz;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Judgement {
    private int id;
    private String date;    //yyyy-mm-dd
    private CourtType courtType;
    private List<Judge> judges = new ArrayList<>();
    private Map<String, String> judgesFunctions = new HashMap<>();  //K - name, V - function
    private String substantiation;

    public Judgement(int id, String date, CourtType courtType, List<Judge> judges, Map<String, String> judgesFunctions, String textContent) {
        this.id = id;
        this.date = date;
        this.courtType = courtType;
        this.judges = judges;
        this.judgesFunctions = judgesFunctions;
        this.substantiation = textContent;
    }
}
