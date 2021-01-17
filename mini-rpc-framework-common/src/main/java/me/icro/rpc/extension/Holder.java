package me.icro.rpc.extension;

/**
 * 对象持有者
 *
 * @author lin
 * @version v 0.1 2021/1/17
 **/
public class Holder<T> {
    private volatile T value;

    public T get() {
        return value;
    }

    public void set(T value) {
        this.value = value;
    }
}
