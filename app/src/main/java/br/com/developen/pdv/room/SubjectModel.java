package br.com.developen.pdv.room;

public class SubjectModel {

    private Integer identifier;

    private Boolean active;

    private Integer level;

    public Integer getIdentifier() {

        return identifier;

    }

    public void setIdentifier(Integer identifier) {

        this.identifier = identifier;

    }

    public Boolean getActive() {

        return active;

    }

    public void setActive(Boolean active) {

        this.active = active;

    }

    public Integer getLevel() {

        return level;

    }

    public void setLevel(Integer level) {

        this.level = level;

    }

    public boolean equals(Object o) {

        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SubjectModel countryVO = (SubjectModel) o;
        return identifier.equals(countryVO.identifier);

    }

    public int hashCode() {

        return identifier.hashCode();

    }

}