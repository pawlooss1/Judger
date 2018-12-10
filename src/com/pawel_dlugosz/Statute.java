package com.pawel_dlugosz;

import java.util.Objects;

public class Statute implements Comparable<Statute> {
    private String journalTitle;
    private int journalNo;
    private int journalYear;
    private int journalEntry;
    private int numberOfOccurances;

    public Statute(String journalTitle, int journalNo, int journalYear, int journalEntry) {
        this.journalTitle = journalTitle;
        this.journalNo = journalNo;
        this.journalYear = journalYear;
        this.journalEntry = journalEntry;
        this.numberOfOccurances = 0;
    }

    public String getJournalTitle() {
        return journalTitle;
    }

    public int getJournalNo() {
        return journalNo;
    }

    public int getJournalYear() {
        return journalYear;
    }

    public int getJournalEntry() {
        return journalEntry;
    }

    public int getNumberOfOccurances() {
        return numberOfOccurances;
    }

    public int compareTo(Statute other) {
        return other.numberOfOccurances - this.numberOfOccurances;
    }

    public void incrementOccurances() {
        this.numberOfOccurances++;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Statute statute = (Statute) o;
        return journalNo == statute.journalNo &&
                journalYear == statute.journalYear &&
                journalEntry == statute.journalEntry;
    }

    @Override
    public int hashCode() {
        return Objects.hash(journalNo, journalYear, journalEntry);
    }

    @Override
    public String toString() {
        return this.journalTitle;
    }
}
