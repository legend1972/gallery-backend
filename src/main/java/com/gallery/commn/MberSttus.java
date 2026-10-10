package com.gallery.commn;

public enum MberSttus {
    GRNL("MBER"),
    ARTS("ARTS");

    private final String name;

    MberSttus(String name) {
        this.name = name;
    }

    public static String getName(String code) {
        for(MberSttus sttus: values()) {
            if(sttus.name().equals(code)) {
                return sttus.getName();
            }
        }

        return "NONE";
    }

    public String getName() {
        return name;
    }
}
