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
    private List<Statute> statutes;

    public Judgement(int id, String date, CourtType courtType, List<Judge> judges, Map<Judge, List<String>> judgesRoles, String textContent, List<Statute> statutes) {
        this.id = id;
        this.date = date;
        this.courtType = courtType;
        this.judges = judges;
        this.judgesRoles = judgesRoles;
        this.substantiation = textContent;
        this.statutes = statutes;
    }

    public int getId() {
        return id;
    }

    public String getDate() {
        return date;
    }

    public CourtType getCourtType() {
        return courtType;
    }

    public List<Judge> getJudges() {
        return judges;
    }

    public Map<Judge, List<String>> getJudgesRoles() {
        return judgesRoles;
    }

    public String getSubstantiation() {
        return substantiation;
    }

    public List<Statute> getStatutes() {
        return statutes;
    }

    @Override
    public String toString() {
        return id + "\n" + date + "\n" + substantiation + "\n";
    }
}
