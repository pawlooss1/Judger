package com.pawel_dlugosz;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Judgement {
    private int id;
    private String date;    //yyyy-mm-dd
    private String caseNumber;
    private CourtType courtType;
    private List<Judge> judges;
    private Map<Judge, List<SpecialRole>> judgesRoles;  //K - name, V - role
    private String substantiation;
    private List<Statute> statutes;

    public Judgement(int id, String date, String caseNumber, CourtType courtType, List<Judge> judges, Map<Judge, List<SpecialRole>> judgesRoles, String textContent, List<Statute> statutes) {
        this.id = id;
        this.date = date;
        this.caseNumber = caseNumber;       //wzorzec builder
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

    public String getCaseNumber() {
        return caseNumber;
    }

    public CourtType getCourtType() {
        return courtType;
    }

    public List<Judge> getJudges() {
        return judges;
    }

    public Map<Judge, List<SpecialRole>> getJudgesRoles() {
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
        String judgesWithRoles = "Skład sędziowski:\n";
        for (Judge judge : judges) {
            String judgeDescription = judge.toString();
            if (!judgesRoles.get(judge).isEmpty())
                judgeDescription += ": ";
            for (SpecialRole role : judgesRoles.get(judge)) {
                judgeDescription += role.toString() + " ";
            }
            judgesWithRoles += judgeDescription + "\n";
        }
        return caseNumber + "\n" + date + "\n" + courtType.toString() + "\n\n" + judgesWithRoles + "\n";
    }
}
