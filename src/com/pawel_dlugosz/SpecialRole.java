package com.pawel_dlugosz;

public enum SpecialRole {
    PRESIDING_JUDGE,
    REPORTING_JUDGE,
    REASONS_FOR_JUDGMENT_AUTHOR,
    NONE;

    @Override
    public String toString() {
        switch (this) {
            case PRESIDING_JUDGE:
                return "Przewodniczący składu sędziowskiego";
            case REPORTING_JUDGE:
                return "Sędzia sprawozdawca";
            case REASONS_FOR_JUDGMENT_AUTHOR:
                return "Autor uzasadnienia";
        }
        return "";
    }

    public static SpecialRole parseFromString(String role) {
        if (role.equals("PRESIDING_JUDGE") || role.matches(".*przewodniczący.*"))
            return PRESIDING_JUDGE;
        if (role.equals("REPORTING_JUDGE") || role.matches(".*sprawozdawca.*"))
            return REPORTING_JUDGE;
        if (role.equals("REASONS_FOR_JUDGMENT_AUTHOR") || role.matches(".*uzasad.*"))
            return REASONS_FOR_JUDGMENT_AUTHOR;
        else
            return NONE;
    }
}
