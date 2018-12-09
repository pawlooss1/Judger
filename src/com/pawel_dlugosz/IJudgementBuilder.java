package com.pawel_dlugosz;

import java.util.List;
import java.util.Map;

public interface IJudgementBuilder {
    Judgement build();

    JudgementBuilder setId(int id);

    JudgementBuilder setDate(String date);

    JudgementBuilder setCaseNumber(String caseNumber);

    JudgementBuilder setCourtType(CourtType courtType);

    JudgementBuilder setJudges(List<Judge> judges);

    JudgementBuilder setJudgesRoles(Map<Judge, List<SpecialRole>> judgesRoles);

    JudgementBuilder setSubstantiation(String substantiation);

    JudgementBuilder setStatutes(List<Statute> statutes);
}
