package com.chuangfeigu.tools.common;

/**
 * Pair 类变种，修改属性的作用域和equal方法只比较key;
 *
 * @param <F>
 * @param <S>
 */
public class Pair<F, S> {
    public F key;
    public S second;

    /**
     * Constructor for a Pair.
     *
     * @param first  the first object in the Pair
     * @param second the second object in the pair
     */
    public Pair(F first, S second) {
        this.key = first;
        this.second = second;
    }

    /**
     * Checks the two objects for equality by delegating to their respective
     * {@link Object#equals(Object)} methods.
     *
     * @param o the {@link android.util.Pair} to which this one is to be checked for equality
     * @return true if the underlying objects of the Pair are both considered
     * equal
     */
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Pair)) {
            return false;
        }
        Pair<?, ?> p = (Pair<?, ?>) o;
        if (p.key != null) {
            return p.key.equals(((Pair<?, ?>) o).key);
        }
        return ((Pair<?, ?>) o).key == null;
    }

    /**
     * Compute a hash code using the hash codes of the underlying objects
     *
     * @return a hashcode of the Pair
     */
    @Override
    public int hashCode() {
        return (key == null ? 0 : key.hashCode()) ^ 3251;
    }

    @Override
    public String toString() {
        return "Pair{" + String.valueOf(key) + " " + String.valueOf(second) + "}";
    }

    /**
     * Convenience method for creating an appropriately typed pair.
     *
     * @param a the first object in the Pair
     * @param b the second object in the pair
     * @return a Pair that is templatized with the types of a and b
     */
    public static <A, B> Pair<A, B> create(A a, B b) {
        return new Pair<A, B>(a, b);
    }
}