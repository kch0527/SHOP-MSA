package chan.shop.goodsread.cache;

@FunctionalInterface
public interface OptimizedCacheOriginDataSupplier<T> {
    T get() throws Throwable;
}
