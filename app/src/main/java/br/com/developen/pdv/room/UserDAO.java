package br.com.developen.pdv.room;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface UserDAO {

    String GET_USER_BY_IDENTIFIER =
            "SELECT " +
                    "Sbj.identifier, " +
                    "Sbj.active, " +
                    "Sbj.level, " +
                    "Ind.name, " +
                    "Usr.login, " +
                    "Usr.password, " +
                    "Usr.numericPassword " +
                    "FROM User Usr " +
                    "INNER JOIN Individual Ind ON Ind.subject = Usr.individual " +
                    "INNER JOIN Subject Sbj ON Sbj.identifier = Ind.subject " +
                    "WHERE Sbj.identifier = :identifier";

    String GET_USER_BY_LOGIN =
            "SELECT " +
                    "Sbj.identifier, " +
                    "Sbj.active, " +
                    "Sbj.level, " +
                    "Ind.name, " +
                    "Usr.login, " +
                    "Usr.password, " +
                    "Usr.numericPassword " +
                    "FROM User Usr " +
                    "INNER JOIN Individual Ind ON Ind.subject = Usr.individual " +
                    "INNER JOIN Subject Sbj ON Sbj.identifier = Ind.subject " +
                    "WHERE Usr.login = :login";

    @Insert
    void create(UserVO userVO);

    @Query("SELECT * FROM User WHERE individual = :individual")
    UserVO retrieve(int individual);

    @Query("SELECT COUNT(*) > 0 FROM User WHERE individual = :individual")
    Boolean exists(int individual);

    @Update
    void update(UserVO userVO);

    @Delete
    void delete(UserVO userVO);

    @Query(GET_USER_BY_IDENTIFIER)
    UserModel getUserByIdentifier(Integer identifier);

    @Query(GET_USER_BY_LOGIN)
    UserModel getUserByLogin(String login);

}