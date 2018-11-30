package com.pawel_dlugosz;

import com.google.gson.stream.JsonReader;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JudgementParser {
    public List<Judgement> readJsonStream(InputStream in) throws IOException {
        JsonReader reader = new JsonReader(new InputStreamReader(in));
        List<Judgement> result = new ArrayList<>();
        try {
            reader.beginObject();
            while(reader.hasNext()){
                String header = reader.nextName();
                if(header.equals("items"))
                    result = readJudgementsArray(reader);
                else
                    reader.skipValue();

            }
            reader.endObject();
            return result;
        }
        finally {
            reader.close();
        }
    }
    public List<Judgement> readJudgementsArray(JsonReader reader) throws IOException {
        List<Judgement> judgements = new ArrayList<>();
        reader.beginArray();
        while(reader.hasNext()){
            judgements.add(readJudgement(reader));
        }
        reader.endArray();
        return judgements;
    }
    public Judgement readJudgement (JsonReader reader) throws IOException {
        int id;
        String date;
        CourtType courtType;
        List<Judge> judges = new ArrayList<>();
        Map<Judge, List<String>> judgesRoles;
        String textContent;

        reader.beginObject();
        while(reader.hasNext()){
            String name = reader.nextName();
            if(name.equals("id"))
                id = reader.nextInt();
            else if(name.equals("judgmentDate"))
                date = reader.nextString();
            else if(name.equals("courtType"))
                courtType = CourtType.parseFromString(reader.nextString());
            else if(name.equals("judges")){
                judgesRoles = readJudgesArray(reader);
                judges.addAll(judgesRoles.keySet());
            }
            else if ()                                                       //dodać wczytywanie ustaw
            else if(name.equals("textContent"))
                textContent = reader.nextString();
        }
        reader.endObject();

    }
    public Map<Judge, List<String>> readJudgesArray(JsonReader reader) throws IOException {
        Map<Judge, List<String>> result = new HashMap<>();
        String judgeName = "";
        Judge judge = null;
        List<String> judgeRoles = new ArrayList<>();

        reader.beginArray();
        while(reader.hasNext()){
            reader.beginObject();
            while(reader.hasNext()){
                String name = reader.nextName();
                if(name.equals("name"))
                    judge = new Judge(reader.nextString());
                else if(name.equals("specialRoles"))
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
    public List<String> readRolesArray(JsonReader reader) throws IOException {
        List<String> result = new ArrayList<>();
        reader.beginArray();
        while(reader.hasNext()){
            result.add(reader.nextString());
        }
        reader.endArray();
        return result;
    }
}
