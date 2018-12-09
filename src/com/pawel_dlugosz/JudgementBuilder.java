package com.pawel_dlugosz;

import java.util.List;
import java.util.Map;

public class JudgementBuilder implements IJudgementBuilder {
    private Judgement judgementTmp;

    JudgementBuilder() {
        judgementTmp = new Judgement();
    }

    public Judgement build() {
        Judgement judgement = new Judgement();
        judgement.setId(judgementTmp.getId());
        judgement.setDate(judgementTmp.getDate());
        judgement.setCaseNumber(judgementTmp.getCaseNumber());
        judgement.setCourtType(judgementTmp.getCourtType());
        judgement.setJudges(judgementTmp.getJudges());
        judgement.setJudgesRoles(judgementTmp.getJudgesRoles());
        judgement.setSubstantiation(judgementTmp.getSubstantiation());
        judgement.setStatutes(judgementTmp.getStatutes());
        return judgement;
    }

    public JudgementBuilder setId(int id) {
        judgementTmp.setId(id);
        return this;
    }

    public JudgementBuilder setDate(String date) {
        judgementTmp.setDate(date);
        return this;
    }
    public JudgementBuilder setCaseNumber(String caseNumber) {
        judgementTmp.setCaseNumber(caseNumber);
        return this;
    }

    public JudgementBuilder setCourtType(CourtType courtType) {
        judgementTmp.setCourtType(courtType);
        return this;
    }

    public JudgementBuilder setJudges(List<Judge> judges) {
        judgementTmp.setJudges(judges);
        return this;
    }

    public JudgementBuilder setJudgesRoles(Map<Judge, List<SpecialRole>> judgesRoles) {
        judgementTmp.setJudgesRoles(judgesRoles);
        return this;
    }

    public JudgementBuilder setSubstantiation(String substantiation) {
        judgementTmp.setSubstantiation(substantiation);
        return this;
    }

    public JudgementBuilder setStatutes(List<Statute> statutes) {
        judgementTmp.setStatutes(statutes);
        return this;
    }
}
