package cn.newcapec.function.platform.ocean;

import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
/**
 *
 * @Description :
 * @author : ocean
 * @date : 2014-5-13 上午10:38:11
 * @email : zhangjunfang0505@163.com
 * @Copyright : newcapec zhengzhou
 */
public class DefaultLocalCache<K, V> implements LocalCache<K, V> {
	private static final Log Logger = LogFactory
			.getLog(DefaultLocalCache.class);
	ConcurrentHashMap<K, SoftReference<V>>[] caches;
	ConcurrentHashMap<K, Long> expiryCache;
	private ScheduledExecutorService scheduleService;
	private int expiryInterval = 10;

	private int moduleSize = 10;

	public DefaultLocalCache() {
		init();
	}

	public DefaultLocalCache(int expiryInterval, int moduleSize) {
		this.expiryInterval = expiryInterval;
		this.moduleSize = moduleSize;
		init();
	}

	@SuppressWarnings("unchecked")
	private void init() {
		this.caches = new ConcurrentHashMap[this.moduleSize];

		for (int i = 0; i < this.moduleSize; i++) {
			this.caches[i] = new ConcurrentHashMap<K, SoftReference<V>>();
		}

		this.expiryCache = new ConcurrentHashMap<K, Long>();

		this.scheduleService = Executors.newScheduledThreadPool(1);

		this.scheduleService.scheduleAtFixedRate(new CheckOutOfDateSchedule(
				this.caches, this.expiryCache), 0L, this.expiryInterval * 60,
				TimeUnit.SECONDS);

		if (Logger.isInfoEnabled())
			Logger.info("DefaultCache CheckService is start!");
	}

	public boolean clear() {
		if (this.caches != null) {
			for (ConcurrentHashMap<K, SoftReference<V>> cache : this.caches) {
				cache.clear();
			}
		}
		if (this.expiryCache != null) {
			this.expiryCache.clear();
		}

		return true;
	}

	public boolean containsKey(K key) {
		checkValidate(key);
		return getCache(key).containsKey(key);
	}

	public V get(K key) {
		checkValidate(key);
		SoftReference<V> sr = getCache(key).get(key);
		if (sr == null) {
			this.expiryCache.remove(key);
			return null;
		}
		return sr.get();
	}

	public Set<K> keySet() {
		checkAll();
		return this.expiryCache.keySet();
	}

	public V put(K key, V value) {
		SoftReference<V> result = getCache(key).put(key,
				new SoftReference<V>(value));
		this.expiryCache.put(key, Long.valueOf(-1L));

		return (result == null ? null : result.get());
	}

	public V put(K key, V value, Date expiry) {
		SoftReference<V> result = getCache(key).put(key,
				new SoftReference<V>(value));
		this.expiryCache.put(key, Long.valueOf(expiry.getTime()));

		return (result == null ? null : result.get());
	}

	public V remove(K key) {
		SoftReference<V> result = getCache(key).remove(key);
		this.expiryCache.remove(key);
		return (result == null ? null : result.get());
	}

	public int size() {
		checkAll();

		return this.expiryCache.size();
	}

	public Collection<V> values() {
		checkAll();
		Collection<V> values = new ArrayList<V>();
		for (ConcurrentHashMap<K, SoftReference<V>> cache : this.caches) {
			for (SoftReference<V> sr : cache.values()) {
				values.add(sr.get());
			}
		}
		return values;
	}

	private ConcurrentHashMap<K, SoftReference<V>> getCache(K key) {
		long hashCode = key.hashCode();

		if (hashCode < 0L) {
			hashCode = -hashCode;
		}
		int moudleNum = (int) hashCode % this.moduleSize;

		return this.caches[moudleNum];
	}

	private void checkValidate(K key) {
		if ((key != null)
				&& (this.expiryCache.get(key) != null)
				&& (((Long) this.expiryCache.get(key)).longValue() != -1L)
				&& (new Date(((Long) this.expiryCache.get(key)).longValue())
						.before(new Date()))) {
			getCache(key).remove(key);
			this.expiryCache.remove(key);
		}
	}

	private void checkAll() {
		Iterator<K> iter = this.expiryCache.keySet().iterator();

		while (iter.hasNext()) {
			K key = iter.next();
			checkValidate(key);
		}
	}

	public V put(K key, V value, int TTL) {
		SoftReference<V> result = getCache(key).put(key,
				new SoftReference<V>(value));

		Calendar calendar = Calendar.getInstance();
		calendar.add(13, TTL);
		this.expiryCache.put(key, Long.valueOf(calendar.getTime().getTime()));

		return (result == null ? null : result.get());
	}

	public void destroy() {
		try {
			clear();

			if (this.scheduleService != null) {
				this.scheduleService.shutdown();
			}
			this.scheduleService = null;
		} catch (Exception ex) {
			Logger.error(ex);
		}
	}

	class CheckOutOfDateSchedule implements Runnable {
		ConcurrentHashMap<K, SoftReference<V>>[] caches;
		ConcurrentHashMap<K, Long> expiryCache;

		public CheckOutOfDateSchedule(
				ConcurrentHashMap<K, SoftReference<V>>[] caches,
				ConcurrentHashMap<K, Long> expiryCache) {
			this.caches = caches;
			this.expiryCache = expiryCache;
		}

		public void run() {
			check();
		}

		public void check() {
			try {
				for (ConcurrentHashMap<K, SoftReference<V>> cache : this.caches) {
					Iterator<K> keys = cache.keySet().iterator();

					while (keys.hasNext()) {
						Object key = keys.next();

						if (this.expiryCache.get(key) == null) {
							continue;
						}
						long date = ((Long) this.expiryCache.get(key))
								.longValue();

						if ((date > 0L) && (new Date(date).before(new Date()))) {
							this.expiryCache.remove(key);
							cache.remove(key);
						}
					}
				}
			} catch (Exception ex) {
				DefaultLocalCache.Logger
						.info("DefaultCache CheckService is start!");
			}
		}
	}
}