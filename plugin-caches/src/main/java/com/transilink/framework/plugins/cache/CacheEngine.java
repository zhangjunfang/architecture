package com.transilink.framework.plugins.cache;

public interface CacheEngine extends Cache {

	
	/****
	 * 
	 * @Title: init
	 * @Description: TODO(缓存初始化 ,会在本地项目启动时自动加载。)    设定文件
	 * @return void 返回类型 返回格式
	 * @throws
	 * @author 杨航(Sntey)
	 * @date 创建日期：2012-12-26
	 * 修改日期：
	 * 修改人：
	 * 复审人：
	 */
	void init();
	
	/****
	 * 
	 * @Title: put
	 * @Description: TODO(
	 * 
	 * 	向缓存中放入数据 key对应一个value值。value可以是对象，也可以是单个的各种类型的值
	 * 
	 * 
	 * )
	 * @param key
	 * @param value    设定文件
	 * @return void 返回类型 返回格式
	 * @throws
	 * @author 杨航(Sntey)
	 * @date 创建日期：2013-2-27
	 * 修改日期：
	 * 修改人：
	 * 复审人：
	 */
	void put(String key ,Object value);
	
	/****
	 * 
	 * @Title: put
	 * @Description: TODO(time为时长，以分钟为单位，代表放入缓存时存储的时间 ，如果要永久保存设为0即可)
	 * @param key
	 * @param value
	 * @param time    设定文件
	 * @return void 返回类型 返回格式
	 * @throws
	 * @author 杨航(Sntey)
	 * @date 创建日期：2013-6-20
	 * 修改日期：
	 * 修改人：
	 * 复审人：
	 */
	void put(String key,Object value,Long time);
	
	/****
	 * 
	 * @Title: get
	 * @Description: TODO(从缓存中取得数据。如果clazz为类型，则返回的值为clazz类型的对象)
	 * @param <T>
	 * @param key
	 * @param clazz
	 * @return    设定文件
	 * @return T 返回类型 返回格式
	 * @throws
	 * @author 杨航(Sntey)
	 * @date 创建日期：2012-12-26
	 * 修改日期：
	 * 修改人：
	 * 复审人：
	 */
	<T extends Object>T get(String key ,Class<T> clazz);
	
	/****
	 * 
	 * @Title: get
	 * @Description: TODO(从缓存中按照KEY值取出对象)
	 * @param key
	 * @return    设定文件
	 * @return Object 返回类型 返回格式
	 * @throws
	 * @author 杨航(Sntey)
	 * @date 创建日期：2012-12-26
	 * 修改日期：
	 * 修改人：
	 * 复审人：
	 */
	Object get(String key);
	
	/****
	 * 
	 * @Title: flush
	 * @Description: TODO(刷新缓存，用于缓存同步机制。将集群中的缓存进行一次同步)    设定文件
	 * @return void 返回类型 返回格式
	 * @throws
	 * @author 杨航(Sntey)
	 * @date 创建日期：2012-12-26
	 * 修改日期：
	 * 修改人：
	 * 复审人：
	 */
	void flush();
	
	/**刷新某个集合的某点。主要用于membercache**/
	void flush(Integer index);
	
	/****
	 * 
	 * @Title: remove
	 * @Description: TODO(从缓存中去 除key对象的value记录,同时key值也会消失)
	 * @param key    设定文件
	 * @return void 返回类型 返回格式
	 * @throws
	 * @author 杨航(Sntey)
	 * @date 创建日期：2012-12-26
	 * 修改日期：
	 * 修改人：
	 * 复审人：
	 */
	void remove(String key);
	
	/****
	 * 
	 * @Title: clear
	 * @Description: TODO(清空缓存)    设定文件
	 * @return void 返回类型 返回格式
	 * @throws
	 * @author 杨航(Sntey)
	 * @date 创建日期：2012-12-26
	 * 修改日期：
	 * 修改人：
	 * 复审人：
	 */
	void clear();
	
	/****
	 * 
	 * @Title: size
	 * @Description: TODO(取得缓存长度。可以为抽象方法。)
	 * @return    设定文件
	 * @return int 返回类型 返回格式
	 * @throws
	 * @author 杨航(Sntey)
	 * @date 创建日期：2012-12-26
	 * 修改日期：
	 * 修改人：
	 * 复审人：
	 */
	int size();

	/****
	 * 
	 * @Title: getDefaultTime
	 * @Description: TODO(取得默认时间)
	 * @return    设定文件
	 * @return Long 返回类型 返回格式
	 * @throws
	 * @author 杨航(Sntey)
	 * @date 创建日期：2013-6-21
	 * 修改日期：
	 * 修改人：
	 * 复审人：
	 */
	Long getDefaultTime();
}
