package com.pawel_dlugosz;

public enum SpecialRole {
    PRESIDING_JUDGE,
    REPORTING_JUDGE,
    REASONS_FOR_JUDGMENT_AUTHOR;

    @Override
    public String toString() {
        switch(this){
            case PRESIDING_JUDGE:
                return "Przewodniczący składu sędziowskiego";
            case REPORTING_JUDGE:
                return "Sędzia sprawozdawca";
            case REASONS_FOR_JUDGMENT_AUTHOR:
                return "Autor uzasadnienia";
        }
        return "";
    }
    public static SpecialRole parseFromString(String role){
        if(role.equals("PRESIDING_JUDGE"))
            return PRESIDING_JUDGE;
        if(role.equals("REPORTING_JUDGE"))
            return REPORTING_JUDGE;
        if(role.equals("REASONS_FOR_JUDGMENT_AUTHOR"))
            return REASONS_FOR_JUDGMENT_AUTHOR;
        else
            return PRESIDING_JUDGE;
    }
}
