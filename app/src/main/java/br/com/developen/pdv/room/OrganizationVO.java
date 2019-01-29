package br.com.developen.pdv.room;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import androidx.annotation.NonNull;

@Entity(tableName = "Organization",
        foreignKeys = {
                @ForeignKey(entity = SubjectVO.class,
                        parentColumns = "identifier",
                        childColumns = "subject",
                        onDelete = ForeignKey.RESTRICT,
                        onUpdate = ForeignKey.CASCADE)},
        indices = {@Index("subject")})
public class OrganizationVO {

    @PrimaryKey
    @ColumnInfo(name="subject")
    private Integer subject;

    @NonNull
    @ColumnInfo(name="denomination")
    private String denomination;

    @NonNull
    @ColumnInfo(name="fancyName")
    private String fancyName;

    public Integer getSubject() {

        return subject;

    }

    public void setSubject(Integer subject) {

        this.subject = subject;

    }

    public String getDenomination() {

        return denomination;

    }

    public void setDenomination(String denomination) {

        this.denomination = denomination;

    }

    public String getFancyName() {

        return fancyName;

    }

    public void setFancyName(String fancyName) {

        this.fancyName = fancyName;

    }

    public boolean equals(Object o) {

        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrganizationVO that = (OrganizationVO) o;
        return subject.equals(that.subject);

    }

    public int hashCode() {

        return subject.hashCode();

    }

}