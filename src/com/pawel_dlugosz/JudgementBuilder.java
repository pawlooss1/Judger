package com.pawel_dlugosz;

public class JudgementBuilder implements IJudgementBuilder {
    private Judgement judgementTmp;

    JudgementBuilder() {
        judgementTmp = new Judgement();
    }

    public Judgement build() {
        Judgement judgement = new Judgement();

        return judgement;
    }
}
