package main.java.Day5;

import java.util.Objects;

public class Tuple<U, V> {
    public U _0;
    public V _1;

    public Tuple() {
    }

    public Tuple(U _0, V _1) {
        this._0 = _0;
        this._1 = _1;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tuple<?, ?> tuple = (Tuple<?, ?>) o;
        return Objects.equals(_0, tuple._0) && Objects.equals(_1, tuple._1);
    }

    @Override
    public int hashCode() {
        return Objects.hash(_0, _1);
    }
}
