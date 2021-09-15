package com.emotie.api.guestbook.domain;

import com.emotie.api.member.domain.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Getter
@NoArgsConstructor
@Entity(name = "members_report_guestbooks")
@IdClass(MemberReportGuestbookKey.class)
public class MemberReportGuestbook {
    @Id
    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @Id
    @ManyToOne
    @JoinColumn(name = "guestbook_id")
    private Guestbook guestbook;

    @Builder
    public MemberReportGuestbook(
            Member member, Guestbook guestbook
    ) {
        this.member = member;
        this.guestbook = guestbook;
    }

    @Override
    public boolean equals(Object o){
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MemberReportGuestbook memberReportGuestbook = (MemberReportGuestbook) o;
        return Objects.equals(getMember(), memberReportGuestbook.getMember()) &&
                Objects.equals(getGuestbook(), memberReportGuestbook.getGuestbook());
    }

    @Override
    public int hashCode(){
        return Objects.hash(getMember(), getGuestbook());
    }
}

class MemberReportGuestbookKey implements Serializable {
    private Member member;
    private Guestbook guestbook;
}