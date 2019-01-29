package br.com.developen.pdv.room;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import androidx.annotation.NonNull;

@Entity(tableName = "User",
        foreignKeys = {
                @ForeignKey(entity = IndividualVO.class,
                        parentColumns = "subject",
                        childColumns = "individual",
                        onDelete = ForeignKey.RESTRICT,
                        onUpdate = ForeignKey.CASCADE)},
        indices = {@Index("individual")})
public class UserVO {

    @PrimaryKey
    @ColumnInfo(name = "individual")
    private Integer individual;

    @NonNull
    @ColumnInfo(name="login")
    private String login;

    @NonNull
    @ColumnInfo(name="password")
    private String password;

    public Integer getIndividual() {

        return individual;

    }

    public void setIndividual(Integer individual) {

        this.individual = individual;

    }

    public String getLogin() {

        return login;

    }

    public void setLogin(String login) {

        this.login = login;

    }

    public String getPassword() {

        return password;

    }

    public void setPassword(String password) {

        this.password = password;

    }

    public boolean equals(Object o) {

        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserVO userVO = (UserVO) o;
        return individual.equals(userVO.individual);

    }

    public int hashCode() {

        return individual.hashCode();

    }

}