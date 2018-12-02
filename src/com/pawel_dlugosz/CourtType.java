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
        if(type.equals("COMMON"))
            return COMMON;
        if(type.equals("SUPREME"))
            return SUPREME;
        if(type.equals("ADMINISTRATIVE"))
            return ADMINISTRATIVE;
        if(type.equals("CONSTITUTIONAL_TRIBUNAL"))
            return CONSTITUTIONAL_TRIBUNAL;
        else
            return NATIONAL_APPEAL_CHAMBER;
    }
}
