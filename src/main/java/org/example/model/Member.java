package org.example.model;

public class Member {
    public String name;
    public String memberId;
    public String memberType; // "STUDENT", "TEACHER", "GUEST"
    // only used when memberType is GUEST, otherwise stays null
    public String guestSponsor;

    public Member(String name, String memberId, String memberType) {
        this.name = name;
        this.memberId = memberId;
        this.memberType = memberType;
    }
}