package com.pawel_dlugosz;

public enum CourtType {
    COMMON,
    SUPREME,
    ADMINISTRATIVE,
    CONSTITUTIONAL_TRIBUNAL,
    NATIONAL_APPEAL_CHAMBER;

    @Override
    public String toString() {
        switch(this){
            case COMMON:
                return "Sąd powszechny";
            case SUPREME:
                return "Sąd najwyższy";
            case ADMINISTRATIVE:
                return "Sąd administracyjny";
            case CONSTITUTIONAL_TRIBUNAL:
                return "Trybunał Konstytucyjny";
            case NATIONAL_APPEAL_CHAMBER:
                return "Krajowa Izba Odwoławcza";
        }
        return "";
    }

    public static CourtType parseFromString(String type){
        if(type.equals("COMMON") || type.matches(".*powszechny.*"))
            return COMMON;
        if(type.equals("SUPREME") || type.matches(".*najwyższy.*"))
            return SUPREME;
        if(type.equals("ADMINISTRATIVE") || type.matches(".*administracyjny.*"))
            return ADMINISTRATIVE;
        if(type.equals("CONSTITUTIONAL_TRIBUNAL") || type.matches(".*konstytucyjny.*"))
            return CONSTITUTIONAL_TRIBUNAL;
        else
            return NATIONAL_APPEAL_CHAMBER;
    }
}
