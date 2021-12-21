package main.java.Day21;

import java.util.Objects;

public class Quadrupel <U> {
    public U _0;
    public U _1;
    public U _2;
    public U _3;

    public Quadrupel() {
    }

    public Quadrupel(U _0, U _1, U _2, U _3) {
        this._0 = _0;
        this._1 = _1;
        this._2 = _2;
        this._3 = _3;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Quadrupel<?> quad = (Quadrupel<?>) o;
        return Objects.equals(_0, quad._0) && Objects.equals(_1, quad._1)
                && Objects.equals(_2, quad._2) && Objects.equals(_3, quad._3);
    }

    @Override
    public int hashCode() {
        return Objects.hash(_0, _1, _2, _3);
    }
}
