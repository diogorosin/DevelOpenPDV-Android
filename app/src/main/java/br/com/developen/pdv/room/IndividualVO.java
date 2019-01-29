package br.com.developen.pdv.room;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import androidx.annotation.NonNull;

@Entity(tableName = "Individual",
        foreignKeys = {
                @ForeignKey(entity = SubjectVO.class,
                        parentColumns = "identifier",
                        childColumns = "subject",
                        onDelete = ForeignKey.RESTRICT,
                        onUpdate = ForeignKey.CASCADE)},
        indices = {@Index("subject")})
public class IndividualVO {

    @PrimaryKey
    @ColumnInfo(name="subject")
    private Integer subject;

    @NonNull
    @ColumnInfo(name="name")
    private String name;

    public Integer getSubject() {

        return subject;

    }

    public void setSubject(Integer subject) {

        this.subject = subject;

    }

    public String getName() {

        return name;

    }

    public void setName(String name) {

        this.name = name;

    }

    public boolean equals(Object o) {

        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IndividualVO that = (IndividualVO) o;
        return subject.equals(that.subject);

    }

    public int hashCode() {

        return subject.hashCode();

    }

}