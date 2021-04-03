package com.ensta.librarymanager.modele;

public enum Abonnement {
    BASIC(2), PREMIUM(5), VIP(20);
    private int value;
    private Abonnement(int val) {
        this.value = val ;
    }

    public int getValue() {
        return value;
    }

    public static Abonnement fromString(String abn) {
        if(abn.equals("BASIC")) {
            return BASIC;
        }else if(abn.equals("PREMIUM")) {
            return PREMIUM ;
        }else if(abn.equals("VIP")) {
            return VIP ;
        }
        return null;
    }

}
