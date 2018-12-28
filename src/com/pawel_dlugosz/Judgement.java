package com.pawel_dlugosz;

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

    public Judgement() {
    }

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

    public void setId(int id) {
        this.id = id;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setCaseNumber(String caseNumber) {
        this.caseNumber = caseNumber;
    }

    public void setCourtType(CourtType courtType) {
        this.courtType = courtType;
    }

    public void setJudges(List<Judge> judges) {
        this.judges = judges;
    }

    public void setJudgesRoles(Map<Judge, List<SpecialRole>> judgesRoles) {
        this.judgesRoles = judgesRoles;
    }

    public void setSubstantiation(String substantiation) {
        this.substantiation = substantiation;
    }

    public void setStatutes(List<Statute> statutes) {
        this.statutes = statutes;
    }

    @Override
    public String toString() {
        StringBuilder judgesWithRoles = new StringBuilder("Skład sędziowski:\n");
        for (Judge judge : judges) {
            StringBuilder judgeDescription = new StringBuilder(judge.toString());
            if (!judgesRoles.get(judge).isEmpty())
                judgeDescription.append(": ");
            for (SpecialRole role : judgesRoles.get(judge)) {
                judgeDescription.append(role.toString());
                judgeDescription.append(" ");
            }
            judgesWithRoles.append(judgeDescription.toString());
            judgesWithRoles.append("\n");
        }
        return caseNumber + "\n" + date + "\n" + courtType.toString() + "\n\n" + judgesWithRoles.toString() + "\n";
    }

    public int getMonth() {    //zwraca miesiac w postaci liczby 0-11
        String[] date = this.getDate().split("-");
        String month = date[1];
        return Integer.parseInt(month) - 1;
    }

    public int countJudges() {
        return this.judges.size();
    }
}
