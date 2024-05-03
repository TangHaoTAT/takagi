package com.tanghao.takagi.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.CollectionUtils;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @description 全局缓存接口实现
 */
@Slf4j
public class IGlobalCacheImpl implements IGlobalCache{
    private final RedisTemplate<String, Object> redisTemplate;

    public IGlobalCacheImpl(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public Boolean expire(String key, Long time) {
        try {
            if (time > 0) {
                redisTemplate.expire(key, time, TimeUnit.SECONDS);
            }
            return true;
        } catch (Exception e) {
            log.error("exception message", e);
        }
        return false;
    }

    @Override
    public Long getExpire(String key) {
        return redisTemplate.getExpire(key, TimeUnit.SECONDS);
    }

    @Override
    public Boolean hasKey(String key) {
        try {
            return redisTemplate.hasKey(key);
        } catch (Exception e) {
            log.error("exception message", e);
        }
        return false;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void del(String... key) {
        if (null != key && key.length > 0) {
            if (1 == key.length) {
                redisTemplate.delete(key[0]);
            } else {
                redisTemplate.delete((Collection<String>) CollectionUtils.arrayToList(key));
            }
        }
    }

    @Override
    public Object get(String key) {
        return null == key ? null : redisTemplate.opsForValue().get(key);
    }

    @Override
    public Boolean set(String key, Object value) {
        try {
            redisTemplate.opsForValue().set(key, value);
            return true;
        } catch (Exception e) {
            log.error("exception message", e);
        }
        return false;
    }

    @Override
    public Boolean set(String key, Object value, Long time) {
        try {
            if (time > 0) {
                redisTemplate.opsForValue().set(key, value, time, TimeUnit.SECONDS);
            } else {
                set(key, value);
            }
            return true;
        } catch (Exception e) {
            log.error("exception message", e);
        }
        return false;
    }

    @Override
    public Long incr(String key, Long delta) {
        if (delta < 0) {
            throw new RuntimeException("递增因子必须大于0");
        }
        return redisTemplate.opsForValue().increment(key, delta);
    }

    @Override
    public Long decr(String key, Long delta) {
        if (delta < 0) {
            throw new RuntimeException("递减因子必须大于0");
        }
        return redisTemplate.opsForValue().increment(key, -delta);
    }

    @Override
    public Object hget(String key, String item) {
        return redisTemplate.opsForHash().get(key, item);
    }

    @Override
    public Map<Object, Object> hmget(String key) {
        return redisTemplate.opsForHash().entries(key);
    }

    @Override
    public Boolean hmset(String key, Map<String, Object> map) {
        try {
            redisTemplate.opsForHash().putAll(key, map);
            return true;
        } catch (Exception e) {
            log.error("exception message", e);
        }
        return false;
    }

    @Override
    public Boolean hmset(String key, Map<String, Object> map, Long time) {
        try {
            redisTemplate.opsForHash().putAll(key, map);
            if (time > 0) {
                expire(key, time);
            }
            return true;
        } catch (Exception e) {
            log.error("exception message", e);
        }
        return false;
    }

    @Override
    public Boolean hset(String key, String item, Object value) {
        try {
            redisTemplate.opsForHash().put(key, item, value);
            return true;
        } catch (Exception e) {
            log.error("exception message", e);
        }
        return false;
    }

    @Override
    public Boolean hset(String key, String item, Object value, Long time) {
        try {
            redisTemplate.opsForHash().put(key, item, value);
            if (time > 0) {
                expire(key, time);
            }
            return true;
        } catch (Exception e) {
            log.error("exception message", e);
        }
        return false;
    }

    @Override
    public void hdel(String key, Object... item) {
        redisTemplate.opsForHash().delete(key, item);
    }

    @Override
    public Boolean hHasKey(String key, String item) {
        return redisTemplate.opsForHash().hasKey(key, item);
    }

    @Override
    public Double hincr(String key, String item, Double by) {
        return redisTemplate.opsForHash().increment(key, item, by);
    }

    @Override
    public Double hdecr(String key, String item, Double by) {
        return redisTemplate.opsForHash().increment(key, item, -by);
    }

    @Override
    public Set<Object> sGet(String key) {
        try {
            return redisTemplate.opsForSet().members(key);
        } catch (Exception e) {
            log.error("exception message", e);
        }
        return null;
    }

    @Override
    public Boolean sHasKey(String key, Object value) {
        try {
            return redisTemplate.opsForSet().isMember(key, value);
        } catch (Exception e) {
            log.error("exception message", e);
        }
        return false;
    }

    @Override
    public Long sSet(String key, Object... values) {
        try {
            return redisTemplate.opsForSet().add(key, values);
        } catch (Exception e) {
            log.error("exception message", e);
        }
        return 0L;
    }

    @Override
    public Long sSetAndTime(String key, Long time, Object... values) {
        try {
            Long count = redisTemplate.opsForSet().add(key, values);
            if (time > 0) {
                expire(key, time);
            }
            return count;
        } catch (Exception e) {
            log.error("exception message", e);
        }
        return 0L;
    }

    @Override
    public Long sGetSetSize(String key) {
        try {
            return redisTemplate.opsForSet().size(key);
        } catch (Exception e) {
            log.error("exception message", e);
        }
        return 0L;
    }

    @Override
    public Long setRemove(String key, Object... values) {
        try {
            return redisTemplate.opsForSet().remove(key, values);
        } catch (Exception e) {
            log.error("exception message", e);
        }
        return 0L;
    }

    @Override
    public List<Object> lGet(String key, Long start, Long end) {
        try {
            return redisTemplate.opsForList().range(key, start, end);
        } catch (Exception e) {
            log.error("exception message", e);
        }
        return null;
    }

    @Override
    public Long lGetListSize(String key) {
        try {
            return redisTemplate.opsForList().size(key);
        } catch (Exception e) {
            log.error("exception message", e);
        }
        return 0L;
    }

    @Override
    public Object lGetIndex(String key, Long index) {
        try {
            return redisTemplate.opsForList().index(key, index);
        } catch (Exception e) {
            log.error("exception message", e);
        }
        return null;
    }

    @Override
    public Boolean lSetAll(String key, List<Object> value) {
        try {
            redisTemplate.opsForList().leftPushAll(key, value);
            return true;
        } catch (Exception e) {
            log.error("exception message", e);
        }
        return false;
    }

    @Override
    public Boolean lSet(String key, Object value) {
        try {
            redisTemplate.opsForList().leftPushIfPresent(key, value);
            return true;
        } catch (Exception e) {
            log.error("exception message", e);
        }
        return false;
    }

    @Override
    public Boolean lSet(String key, Object value, Long time) {
        try {
            redisTemplate.opsForList().leftPush(key, value);
            if (time > 0) {
                expire(key, time);
            }
            return true;
        } catch (Exception e) {
            log.error("exception message", e);
        }
        return false;
    }

    @Override
    public Boolean lSetAll(String key, List<Object> value, Long time) {
        try {
            redisTemplate.opsForList().leftPushAll(key, value);
            if (time > 0)
                expire(key, time);
            return true;
        } catch (Exception e) {
            log.error("exception message", e);
        }
        return false;
    }

    @Override
    public Boolean rSet(String key, Object value) {
        try {
            redisTemplate.opsForList().rightPush(key, value);
            return true;
        } catch (Exception e) {
            log.error("exception message", e);
        }
        return false;
    }

    @Override
    public Boolean rSet(String key, Object value, Long time) {
        try {
            redisTemplate.opsForList().rightPush(key, value);
            if (time > 0) {
                expire(key, time);
            }
            return true;
        } catch (Exception e) {
            log.error("exception message", e);
        }
        return false;
    }

    @Override
    public Boolean rSetAll(String key, List<Object> value) {
        try {
            redisTemplate.opsForList().rightPushAll(key, value);
            return true;
        } catch (Exception e) {
            log.error("exception message", e);
        }
        return false;
    }

    @Override
    public Boolean rSetAll(String key, List<Object> value, Long time) {
        try {
            redisTemplate.opsForList().rightPushAll(key, value);
            if (time > 0)
                expire(key, time);
            return true;
        } catch (Exception e) {
            log.error("exception message", e);
        }
        return false;
    }

    @Override
    public Boolean lUpdateIndex(String key, Long index, Object value) {
        try {
            redisTemplate.opsForList().set(key, index, value);
            return true;
        } catch (Exception e) {
            log.error("exception message", e);
        }
        return false;
    }

    @Override
    public Long lRemove(String key, Long count, Object value) {
        try {
            return redisTemplate.opsForList().remove(key, count, value);
        } catch (Exception e) {
            log.error("exception message", e);
        }
        return 0L;
    }

    @Override
    public void rangeRemove(String key, Long start, Long end) {
        try {
            redisTemplate.opsForList().trim(key, start, end);
        } catch (Exception e) {
            log.error("exception message", e);
        }
    }

}
