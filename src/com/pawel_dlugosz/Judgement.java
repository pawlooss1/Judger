package com.pawel_dlugosz;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Judgement {
    private int id;
    private String date;    //yyyy-mm-dd
    private CourtType courtType;
    private List<Judge> judges;
    private Map<Judge, List<String>> judgesRoles;  //K - name, V - role
    private String substantiation;

    public Judgement(int id, String date, CourtType courtType, List<Judge> judges, Map<Judge, List<String>> judgesRoles, String textContent) {
        this.id = id;
        this.date = date;
        this.courtType = courtType;
        this.judges = judges;
        this.judgesRoles = judgesRoles;
        this.substantiation = textContent;
    }
}
