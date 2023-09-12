package co.tunan.template.common.util.data;

import java.util.Objects;

/**
 * @author trifolium
 * @version 1.0
 */
public class KeyValue<K, V> {

    private K key;

    private V value;

    public KeyValue() {

    }

    public KeyValue(K k, V v) {
        this.key = k;
        this.value = v;
    }

    public K getKey() {
        return key;
    }

    public void setKey(K key) {
        this.key = key;
    }

    public V getValue() {
        return value;
    }

    public void setValue(V value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return this.key + ":" + this.value;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj instanceof KeyValue o) {
            return Objects.equals(this.key, o.getKey())
                    && Objects.equals(this.value, o.getValue());
        }

        return false;
    }

    @Override
    public int hashCode() {

        return Objects.hash(this.getKey(), this.getValue());
    }

}
