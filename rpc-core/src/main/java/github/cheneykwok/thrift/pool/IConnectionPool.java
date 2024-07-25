package github.cheneykwok.thrift.pool;

public interface IConnectionPool<K, V> {
    /**
     * 从此池中为指定的key借用一个实例
     *
     * @param key 指定的key
     * @return V 实例
     * @author gzc
     * @date 2024-07-23
     */
    V borrowObject(K key);

    /**
     * 将指定的实例归还到池中
     *
     * @param key   指定的key
     * @param value 实例
     * @author gzc
     * @date 2024-07-23
     */
    void returnObject(K key, V value);

    /**
     * 将指定的实例从池中移除
     *
     * @param key   指定的key
     * @param value 实例
     * @author gzc
     * @date 2024-07-23
     */
    void invalidateObject(K key, V value);

    /**
     * 关闭池
     *
     * @author gzc
     * @date 2024-07-23
     */
    void shutdown();
}
