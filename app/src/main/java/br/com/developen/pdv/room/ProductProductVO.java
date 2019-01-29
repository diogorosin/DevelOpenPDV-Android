package br.com.developen.pdv.room;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.annotation.NonNull;


@Entity(tableName = "ProductProduct",
        primaryKeys = {"parent", "child"},
        foreignKeys = {
                @ForeignKey(entity = ProductVO.class,
                        parentColumns = "progeny",
                        childColumns = "parent",
                        onDelete = ForeignKey.RESTRICT,
                        onUpdate = ForeignKey.CASCADE),
                @ForeignKey(entity = ProductVO.class,
                        parentColumns = "progeny",
                        childColumns = "child",
                        onDelete = ForeignKey.RESTRICT,
                        onUpdate = ForeignKey.CASCADE)},
        indices = {@Index(value={"parent"}), @Index(value={"child"})})
public class ProductProductVO {

    @NonNull
    @ColumnInfo(name = "parent")
    private Integer parent;

    @NonNull
    @ColumnInfo(name = "child")
    private Integer child;

    @ColumnInfo(name="active")
    private Boolean active;

    @ColumnInfo(name="quantity")
    private Double quantity;

    public Integer getParent() {

        return parent;

    }

    public void setParent(Integer parent) {

        this.parent = parent;

    }

    public Integer getChild() {

        return child;

    }

    public void setChild(Integer child) {

        this.child = child;

    }

    public Boolean getActive() {

        return active;

    }

    public void setActive(Boolean active) {

        this.active = active;

    }

    public Double getQuantity() {

        return quantity;

    }

    public void setQuantity(Double quantity) {

        this.quantity = quantity;

    }

    public boolean equals(Object o) {

        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductProductVO that = (ProductProductVO) o;
        if (!parent.equals(that.parent)) return false;
        return child.equals(that.child);

    }

    public int hashCode() {

        int result = parent.hashCode();
        result = 31 * result + child.hashCode();
        return result;

    }

}