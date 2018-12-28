package com.pawel_dlugosz;

import java.io.*;
import java.util.*;

public class App {

    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Podaj ścieżkę katalogu z orzeczeniami");
            System.exit(2);
        }
        List<Judge> judges;
        List<Statute> statutes;
        LinkedHashMap<String, Judgement> judgements;
        FileLoader judgementLoader = new FileLoader(args[0]);
        try {
            System.out.println("Ładuję pliki...");
            judgements = judgementLoader.loadFiles();
            judges = judgementLoader.countJudges();
            statutes = judgementLoader.countStatutes();
            System.out.println("Gotowe");
            Functions functions = new Functions(judges, statutes, judgements);
            Console console;
            if (args.length == 1)
                console = new Console(functions);
            else
                console = new Console(functions, args[1]);
            console.run();
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            System.exit(1);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
