package com.pawel_dlugosz;

import com.google.gson.stream.JsonReader;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

public class JsonParser {
    IJudgementBuilder builder = new JudgementBuilder();

    public List<Judgement> readJsonStream(InputStream in) throws IOException {
        JsonReader reader = new JsonReader(new InputStreamReader(in));
        List<Judgement> result = new ArrayList<>();
        try {
            reader.beginObject();
            while (reader.hasNext()) {
                String header = reader.nextName();
                if (header.equals("items"))
                    result = readJudgementsArray(reader);
                else
                    reader.skipValue();

            }
            reader.endObject();
        } finally {
            reader.close();
        }
        return result;
    }

    private List<Judgement> readJudgementsArray(JsonReader reader) throws IOException {
        List<Judgement> judgements = new ArrayList<>();
        reader.beginArray();
        while (reader.hasNext()) {
            judgements.add(readJudgement(reader));
        }
        reader.endArray();
        return judgements;
    }

    private Judgement readJudgement(JsonReader reader) throws IOException {
        int id = 0;
        String date = "";
        String caseNumber = "";
        CourtType courtType = CourtType.COMMON;
        List<Judge> judges = new ArrayList<>();
        Map<Judge, List<SpecialRole>> judgesRoles = new HashMap<>();
        String substantiation = "";
        List<Statute> statutes = new LinkedList<>();

        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (name.equals("id"))
                id = reader.nextInt();
            else if (name.equals("judgmentDate"))
                date = reader.nextString();
            else if (name.equals("courtCases"))
                caseNumber = readCaseNumber(reader);
            else if (name.equals("courtType"))
                courtType = CourtType.parseFromString(reader.nextString());
            else if (name.equals("judges")) {
                judgesRoles = readJudgesArray(reader);
                judges.addAll(judgesRoles.keySet());
            } else if (name.equals("referencedRegulations"))
                statutes = readStatutesArray(reader);
            else if (name.equals("textContent"))
                substantiation = reader.nextString();
            else
                reader.skipValue();
        }
        reader.endObject();
        return builder.setId(id)
                      .setDate(date)
                      .setCaseNumber(caseNumber)
                      .setCourtType(courtType)
                      .setJudges(judges)
                      .setJudgesRoles(judgesRoles)
                      .setSubstantiation(substantiation)
                      .setStatutes(statutes)
                      .build();
    }

    private String readCaseNumber(JsonReader reader) throws IOException {
        String result = "";
        reader.beginArray();
        while (reader.hasNext()) {
            reader.beginObject();
            while (reader.hasNext()) {
                String name = reader.nextName();
                if (name.equals("caseNumber"))
                    result = reader.nextString();
                else
                    reader.skipValue();
            }
            reader.endObject();
        }
        reader.endArray();
        return result;
    }

    private Map<Judge, List<SpecialRole>> readJudgesArray(JsonReader reader) throws IOException {
        Map<Judge, List<SpecialRole>> result = new HashMap<>();
        String judgeName = "";
        Judge judge = null;
        List<SpecialRole> judgeRoles = new ArrayList<>();

        reader.beginArray();
        while (reader.hasNext()) {
            reader.beginObject();
            while (reader.hasNext()) {
                String name = reader.nextName();
                if (name.equals("name"))
                    judge = new Judge(reader.nextString());
                else if (name.equals("specialRoles"))
                    judgeRoles = readRolesArray(reader);
                else
                    reader.skipValue();
            }
            result.put(judge, judgeRoles);
            reader.endObject();
        }
        reader.endArray();
        return result;
    }

    private List<SpecialRole> readRolesArray(JsonReader reader) throws IOException {
        List<SpecialRole> result = new ArrayList<>();
        reader.beginArray();
        while (reader.hasNext()) {
            result.add(SpecialRole.parseFromString(reader.nextString()));
        }
        reader.endArray();
        if (result.size() == 0)
            result.add(SpecialRole.NONE);
        return result;
    }

    private List<Statute> readStatutesArray(JsonReader reader) throws IOException {
        List<Statute> result = new ArrayList<>();
        reader.beginArray();
        while (reader.hasNext()) {
            result.add(readStatute(reader));
        }
        reader.endArray();
        return result;
    }

    private Statute readStatute(JsonReader reader) throws IOException {
        String journalTitle = "";
        int journalNo = 0;
        int journalYear = 0;
        int journalEntry = 0;

        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (name.equals("journalTitle"))
                journalTitle = reader.nextString();
            else if (name.equals("journalNo"))
                journalNo = reader.nextInt();
            else if (name.equals("journalYear"))
                journalYear = reader.nextInt();
            else if (name.equals("journalEntry"))
                journalEntry = reader.nextInt();
            else
                reader.skipValue();
        }
        reader.endObject();
        return new Statute(journalTitle, journalNo, journalYear, journalEntry);
    }
}
