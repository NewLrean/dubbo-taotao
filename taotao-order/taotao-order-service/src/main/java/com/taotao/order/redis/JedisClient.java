package com.taotao.order.redis;

public interface JedisClient {

	/**
	 * 向redis添加缓存
	 * @param key  键
	 * @param value 值
	 * @return
	 */
	String set(String key, String value);

	/**
	 * 通过key获取redis缓存里的值
	 * @param key 值
	 * @return
	 */
	String get(String key);

	/**
	 * 查询redis中是否存在key
	 * @param key
	 * @return
	 */
	Boolean exists(String key);

	/**
	 * 设置key的生存时间
	 * @param key key
	 * @param seconds 时间  毫秒为单位
	 * @return
	 */
	Long expire(String key, int seconds);

	/**
	 * 查看key的生存时间
	 * @param key 键
	 * @return
	 */
	Long ttl(String key);

	/**
	 * key递增
	 * @param key 键
	 * @return
	 */
	Long incr(String key);

	/**
	 * redis加入三列数据
	 * @param key 值
	 * @param field
	 * @param value
	 * @return
	 */
	Long hset(String key, String field, String value);
	String hget(String key, String field);
	Long hdel(String key, String... field);
}
