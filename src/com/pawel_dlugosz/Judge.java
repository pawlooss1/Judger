package com.pawel_dlugosz;

import java.util.Objects;

public class Judge implements Comparable<Judge>{
    private String name;
    private int numberOfJudgements;

    public Judge(String name) {
        this.name = name;
        this.numberOfJudgements = 0;
    }

    public String getName() {
        return name;
    }

    public int getNumberOfJudgements() {
        return numberOfJudgements;
    }

    public int compareTo(Judge other){
        return other.getNumberOfJudgements() - this.numberOfJudgements;
    }

    @Override
public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Judge judge = (Judge) o;
        return Objects.equals(name, judge.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
