package com.liumy.util;


import org.apache.log4j.Logger;

import redis.clients.jedis.Jedis;

public class JedisUtilsSolo {
	public static Logger log = Logger.getLogger(JedisUtilsSolo.class.getName());
	/**
	 * 将 value 关联key ，并key 的生存时间设seconds (以秒为单如果 key 已经存在SETEX
	 * 命令将覆写旧值
	 * 
	 * 返回值： 设置成功时返OK seconds 参数不合法时，返回一个错误
	 * 
	 * @param key
	 * @param seconds
	 * @param value
	 * @return
	 */
	public static boolean setex(String key, int seconds, String value,Jedis jedis) {
		boolean result = false;
 
		try {
		 
			String status = jedis.setex(key, seconds, value);
			if ("OK".equalsIgnoreCase(status)) {
				result = true;
			}
		} catch (Exception e) {
			log.error( "JedisCache.setex falid", e);
		}
		return result;
	}
	/**
	 * 返回 key ��联的字符串如果 key 不存在那么返回特殊 nil 假如 key 储存的不是字符串类型，返回��错误，因GET
	 * 只能用于处理字符串
	 * 
	 * 返回值： key 不存在时，返nil ，否则，返回 key 的如果 key 不是字符串类型，那么返回��错误
	 * 
	 * @param key
	 * @return
	 */
	public static String get(String key,Jedis jedis) {
		String result = null;
 
		try {
	 
			result = jedis.get(key);
 
		} catch (Exception e) {
 
			log.error( "JedisCache.get falid", e);
		}
		return result;
	}
	/**
	 * 清空
	 * 
	 * 返回 被成功移除的域的数量，不包括被忽略的域
	 * 
	 * @return
	 */
	public static boolean flush(Jedis jedis) {
		boolean result =false;
 
		try {
			String status=jedis.flushAll();
			jedis.flushDB();
			if ("OK".equalsIgnoreCase(status)) {
				result=true;
			}
		} catch (Exception e) {
 
			log.error( "JedisCache.hdel falid", e);
		}
		return result;
	}
	
	/* ==========================Key(操作====================== */
	/**
	 * 删除给定的一个key
	 * 
	 * 返回值：被删除key的数
	 * 
	 * @param key
	 * @return
	 */
	public static long del(String key,Jedis jedis) {
		long result = -10000;
	 
		try {
		 
			result = jedis.del(key);
 
		} catch (Exception e) {
		 
			log.error( "JedisCache.del falid", e);
		}
		return result;
	}
	
	/* ==========================对value操作====================== */
	/**
	 * 将字符串value 关联key 如果 key 已经持有其他值， SET 就覆写旧值，无视类型
	 * 对于某个原本带有生存时间（TTL）的键来说， SET 命令成功在这个键上执行时这个键原有的 TTL 将被清除
	 * 
	 * 返回 OK
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public static boolean set(String key, String value,Jedis jedis) {
		boolean result = false;
		try {
			String status = jedis.set(key, value);
			if ("OK".equalsIgnoreCase(status)) {
				result = true;
			}
		} catch (Exception e) {
			log.error( "JedisCache.set falid", e);
		}
		return result;
	}
}
