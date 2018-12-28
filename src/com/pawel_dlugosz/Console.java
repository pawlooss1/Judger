package com.pawel_dlugosz;

import java.io.*;
import java.util.*;

public class Console {
    private Scanner scanner;
    private LinkedList<String> history;
    private Functions functions;
    private File fileToSave;
    private boolean saveMode;
    private BufferedWriter writer;

    Console(Functions functions) {
        this.scanner = new Scanner(System.in);
        this.history = new LinkedList<>();
        this.functions = functions;
        this.saveMode = false;
    }

    Console(Functions functions, String filePath) {
        this.scanner = new Scanner(System.in);
        this.history = new LinkedList<>();
        this.functions = functions;
        this.fileToSave = new File(filePath);
        if (!fileToSave.exists() || fileToSave.isDirectory()) {
            System.out.println("Niepoprawna ścieżka pliku do zapisu. W drugim argumencie podaj ścieżkę do pliku, w którym mają być zapisywane komendy oraz wyniki.");
            System.exit(3);
        }
        if (!fileToSave.canWrite()) {
            System.out.println("Program nie posiada możliwości zapisu do podanego pliku. Podaj ścieżkę do pliku z możliwością zapisu.");
            System.exit(4);
        }
        this.saveMode = true;
    }

    public void run() throws IOException {
        String command;
        String line;
        if (saveMode)
            this.writer = new BufferedWriter(new FileWriter(fileToSave, true));
        while (true) {
            printPromtChar();
            line = scanner.nextLine();
            if (saveMode) {
                writer.append(line);
                writer.append("\n");
            }
            history.addFirst(line);
            String[] splitLine = line.split(" ");
            command = splitLine[0];
            if (command.equals("quit"))
                break;
            executeCommand(command, Arrays.copyOfRange(splitLine, 1, splitLine.length));
        }
        if (saveMode)
            writer.close();
    }

    private void executeCommand(String command, String[] arguments) throws IOException {
        switch (command) {
            case "help":
                System.out.print(printHelp());
                break;
            case "rubrum":
                if (arguments.length != 0) {
                    String joinedArgs = String.join(" ", arguments);
                    List<String> signatures = Arrays.asList(joinedArgs.split(","));
                    String rubrums = functions.getRubrums(signatures);
                    System.out.print(rubrums);
                    if (saveMode)
                        writer.append(rubrums);
                } else
                    System.out.print("Podaj co najmniej jedną sygnaturę.\n");
                break;
            case "content":
                if (arguments.length != 0) {
                    String signature = String.join(" ", arguments);
                    String substantiation = functions.getSubstantiation(signature);
                    System.out.print(substantiation);
                    if (saveMode)
                        writer.append(substantiation);
                } else
                    System.out.print("Podaj sygnaturę orzeczenia.\n");
                break;
            case "judge":
                if (arguments.length != 0) {
                    try {
                        String name = String.join(" ", arguments);
                        String number = String.valueOf(functions.getNumberOfJudgementsForJudge(name)) + "\n";
                        System.out.print(number);
                        if (saveMode)
                            writer.append(number);
                    } catch (NoSuchElementException e) {
                        System.out.print(e.getMessage());
                    }
                } else
                    System.out.print("Podaj imię i nazwisko sędziego.\n");
                break;
            case "judges":
                String topJudges = print10Judges(functions.take10Judges());
                System.out.print(topJudges);
                if (saveMode)
                    writer.append(topJudges);
                break;
            case "months":
                String months = functions.getJudgementsByMonth();
                System.out.print(months);
                if (saveMode)
                    writer.append(months);
                break;
            case "courts":
                String courts = functions.getJudgementsByCourt();
                System.out.print(courts);
                if (saveMode)
                    writer.append(courts);
                break;
            case "regulations":
                String topStatutes = print10Statutes(functions.take10Statues());
                System.out.print(topStatutes);
                if (saveMode)
                    writer.append(topStatutes);
                break;
            case "jury":
                String jury = functions.getJudgementsByJudges();
                System.out.print(jury);
                if (saveMode)
                    writer.append(jury);
                break;
            case "history":
                if (arguments.length != 0) {
                    try {
                        int howMany = Integer.parseInt(arguments[0]);
                        printHistory(howMany);
                    } catch (Exception e) {
                        System.out.print("Niepoprawny argument. Podaj liczbę komend do wyświetlenia.\n");
                    }
                } else
                    printHistory();
                break;
            default:
                System.out.print("Niepoprawna komenda. Wpisz help aby wyświetlić pomoc.\n");
                break;

        }
    }

    private void printPromtChar() {
        System.out.print("Judger> ");
    }

    private String print10Judges(List<Judge> judges) {
        StringBuilder result = new StringBuilder();
        for (Judge judge : judges) {
            result.append(judge.getName());
            result.append(" - ");
            result.append(judge.getNumberOfJudgements());
            result.append("\n");
        }
        return result.toString();
    }

    private String print10Statutes(List<Statute> statutes) {
        StringBuilder result = new StringBuilder();
        for (Statute statute : statutes) {
            result.append(statute.toString());
            result.append(" - ");
            result.append(statute.getNumberOfOccurances());
            result.append("\n");
        }
        return result.toString();
    }

    private String printHelp() {
        StringBuilder result = new StringBuilder();
        result.append("Dostępne komendy:\n");
        result.append("rubrum - wyświetl metrykę dla orzeczeń o podanych sygnaturach (oddzielonych przecinkami)\n");
        result.append("content - wyświetl treść uzasadnienia o podanej sygnaturze\n");
        result.append("judge - wyświetl liczbę orzeczeń dla wybranego sędziego\n");
        result.append("judges - wyświetl 10 sędziów, którzy wydali największą liczbę orzeczeń\n");
        result.append("months - wyświetl liczbę orzeczeń w poszczególnych miesiącach\n");
        result.append("courts - wyświetl liczbę orzeczeń ze względu na typ sądu\n");
        result.append("regulations - wyświetl 10 najczęściej przywoływanych ustaw\n");
        result.append("jury - wyświetl liczbę orzeczeń ze względu na liczbę sędziów w składzie orzekającym\n");
        result.append("help - wyświetl tę listę\n");
        result.append("quit - wyjdź z programu\n");
        result.append("history - wyświelt historię poleceń (bez parametru - cała, paramentr liczbowy - n ostatnich poleceń)\n");
        return result.toString();
    }

    private void printHistory() {
        for (int i = history.size() - 1; i >= 0; i--)
            System.out.println(history.get(i));
    }

    private void printHistory(int howMany) {
        for (int i = howMany - 1; i >= 0; i--)
            System.out.println(history.get(i));
    }
}
