package com.tanghao.takagi.config;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @description 全局缓存接口
 */
public interface IGlobalCache {

    /**
     * 指定缓存失效时间
     * @param key 键
     * @param time 时间(秒)
     */
    Boolean expire(String key, Long time);

    /**
     * @param key 键
     */
    Long getExpire(String key);

    /**
     * 判断key是否存在
     * @param key 键
     */
    Boolean hasKey(String key);

    /**
     * 删除缓存
     * @param key 可以传一个值或多个
     */
    void del(String... key);

    /**
     * 普通缓存获取
     * @param key 键
     */
    Object get(String key);

    /**
     * 普通缓存放入
     * @param key 键
     * @param value 值
     */
    Boolean set(String key, Object value);

    /**
     * 普通缓存放入并设置时间
     * @param key 键
     * @param value 值
     * @param time 时间(秒)
     */
    Boolean set(String key, Object value, Long time);

    /**
     * 递增
     * @param key 键
     * @param delta 要增加几(大于0)
     */
    Long incr(String key, Long delta);

    /**
     * 递减
     * @param key 键
     * @param delta 要减少几(小于0)
     */
    Long decr(String key, Long delta);

    /**
     * HashGet
     * @param key 键
     * @param item 项
     */
    Object hget(String key, String item);

    /**
     * 获取hashKey对应的所有键值
     * @param key 键
     */
    Map<Object, Object> hmget(String key);

    /**
     * HashSet
     * @param key 键
     * @param map 对应多个键值
     */
    Boolean hmset(String key, Map<String, Object> map);

    /**
     * HashSet 并设置时间
     * @param key 键
     * @param map 对应多个键值
     * @param time 时间(秒)
     */
    Boolean hmset(String key, Map<String, Object> map, Long time);

    /**
     * 向一张hash表中放入数据,如果不存在将创建
     * @param key 键
     * @param item 项
     * @param value 值
     */
    Boolean hset(String key, String item, Object value);

    /**
     * 向一张hash表中放入数据,如果不存在将创建
     * @param key 键
     * @param item 项
     * @param value 值
     * @param time 时间(秒) 注意:如果已存在的hash表有时间,这里将会替换原有的时间
     */
    Boolean hset(String key, String item, Object value, Long time);

    /**
     * 删除hash表中的值
     * @param key 键
     * @param item 项 可以删多个
     */
    void hdel(String key, Object... item);

    /**
     * 判断hash表中是否有该项的值
     * @param key 键
     * @param item 项
     */
    Boolean hHasKey(String key, String item);

    /**
     * hash递增
     * @param key 键
     * @param item 项
     * @param by 要增加几(大于0)
     */
    Double hincr(String key, String item, Double by);

    /**
     * hash递减
     * @param key 键
     * @param item 项
     * @param by 要减少几(小于0)
     */
    Double hdecr(String key, String item, Double by);

    /**
     * 根据key获取Set中的所有值
     * @param key 键
     */
    Set<Object> sGet(String key);

    /**
     * 根据value从一个set中查询,是否存在
     * @param key 键
     * @param value 值
     */
    Boolean sHasKey(String key, Object value);

    /**
     * 将数据放入set缓存
     * @param key 键
     * @param values 值 可以是多个
     */
    Long sSet(String key, Object... values);

    /**
     * 将set数据放入缓存
     * @param key 键
     * @param time 时间(秒)
     * @param values 值 可以是多个
     */
    Long sSetAndTime(String key, Long time, Object... values);


    /**
     * 获取set缓存的长度
     * @param key 键
     */
    Long sGetSetSize(String key);

    /**
     * 移除值为value的
     * @param key 键
     * @param values 值 可以是多个
     */
    Long setRemove(String key, Object... values);

    /**
     * 获取list缓存的内容
     * @param key 键
     * @param start 开始
     * @param end 结束
     */
    List<Object> lGet(String key, Long start, Long end);

    /**
     * 获取list缓存的长度
     * @param key 键
     */
    Long lGetListSize(String key);

    /**
     * 通过索引 获取list中的值
     * @param key 键
     * @param index 索引
     */
    Object lGetIndex(String key, Long index);

    /**
     * 将list放入缓存
     * @param key 键
     * @param value 值
     */
    Boolean lSet(String key, Object value);

    /**
     * 将list放入缓存
     * @param key 键
     * @param value 值
     */
    Boolean lSet(String key, Object value, Long time);

    /**
     * 将list放入缓存
     * @param key 键
     * @param value 值
     */
    Boolean lSetAll(String key, List<Object> value);


    /**
     * 将list放入缓存
     * @param key 键
     * @param value 值
     * @param time 时间(秒)
     */
    Boolean lSetAll(String key, List<Object> value, Long time);

    /**
     * 将list放入缓存
     * @param key 键
     * @param value 值
     */

    Boolean rSet(String key, Object value);

    /**
     * 将list放入缓存
     * @param key 键
     * @param value 值
     * @param time 时间(秒)
     */

    Boolean rSet(String key, Object value, Long time);

    /**
     * 将list放入缓存
     * @param key 键
     * @param value 值
     */
    Boolean rSetAll(String key, List<Object> value);

    /**
     * 将list放入缓存
     * @param key 键
     * @param value 值
     * @param time 时间(秒)
     */
    Boolean rSetAll(String key, List<Object> value, Long time);

    /**
     * 根据索引修改list中的某条数据
     * @param key 键
     * @param index 索引
     * @param value 值
     */
    Boolean lUpdateIndex(String key, Long index, Object value);

    /**
     * 移除N个值为value
     * @param key 键
     * @param count 移除多少个
     * @param value 值
     */
    Long lRemove(String key, Long count, Object value);

    /**
     * 从Redis集合中移除[start,end]之间的元素
     * @param key 键
     * @param start 开始
     * @param end 结束
     */
    void rangeRemove(String key, Long start, Long end);

}
