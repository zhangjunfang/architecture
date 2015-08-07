package com.transilink.framework.core.dao.db;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.restlet.data.CharacterSet;
import org.restlet.data.MediaType;
import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;

import com.transilink.framework.core.exception.BaseException;
import com.transilink.framework.core.model.variant.VariantSet;
import com.transilink.framework.core.utils.MathUtils.MathUtil;
import com.transilink.framework.core.utils.clazzUtils.BeanUtils;
import com.transilink.framework.core.utils.clazzUtils.DBUtil;
import com.transilink.framework.core.utils.pagesUtils.Page;
import com.transilink.framework.core.utils.pagesUtils.PageContext;
import com.transilink.framework.core.utils.stringUtils.JsonUtil;
import com.transilink.framework.core.utils.stringUtils.StringUtil;

/**
 *
 * @author ocean(zhangjufang0505@163.com)
 *
 */
@SuppressWarnings("all")
public class DbDataset implements Serializable {

	/** serialVersionUID */
	private static final long serialVersionUID = -2042432252201796659L;

	/**
	 * 构造方法
	 *
	 */
	public DbDataset() {

	}

	/**
	 * 构造方法.
	 *
	 * @param sql
	 *            查询sql
	 * @param params
	 *            查询参数.
	 * @param supportPaging
	 *            是否需要分页，默认为true.
	 * @param pageIndex
	 *            请求的页号,默认为1.
	 * @param pageSize
	 *            每页记录数，默认为20条.
	 */
	public DbDataset(String sql, ParameterSet params, boolean supportPaging,
			int pageIndex, int pageSize) {

		this.setSql(sql);
		if (params != null) {
			this.parameterSet = params;
		}
		this.setSupportPaging(supportPaging);
		this.setPageIndex(pageIndex);
		this.setPageSize(pageSize);
	}

	/**
	 * 构造方法.
	 *
	 * @param sql
	 *            查询sql
	 * @param params
	 *            查询参数.
	 * @param supportPaging
	 *            是否需要分页.
	 * @param pageIndex
	 *            请求的页号,默认为1.
	 * @param pageSize
	 *            每页记录数，默认为20.
	 */
	public DbDataset(String sql, ParameterSet params, boolean supportPaging,
			String pageIndex, String pageSize) {
		this(sql, params, supportPaging, MathUtil.getInteger(pageIndex)
				.intValue(), MathUtil.getInteger(pageSize).intValue());
	}

	/**
	 * 构造方法.
	 *
	 * @param sql
	 *            查询sql
	 * @param params
	 *            查询参数.
	 * @param supportPaging
	 *            是否需要分页.
	 */
	public DbDataset(String sql, ParameterSet params, boolean supportPaging) {
		this(sql, params, supportPaging, 1, DEFAULT_PAGING_SIZE);
	}

	/**
	 * 构造方法.
	 *
	 * @param sql
	 *            查询sql
	 * @param params
	 *            查询参数.
	 * @param pageIndex
	 *            请求的页号,默认为1.
	 * @param pageSize
	 *            每页记录数，默认为20条.
	 */
	public DbDataset(String sql, ParameterSet params, int pageIndex,
			int pageSize) {

		this(sql, params, true, pageIndex, pageSize);
	}

	/**
	 * 构造方法.
	 *
	 * @param sql
	 *            查询sql
	 * @param params
	 *            查询参数.
	 * @param pageIndex
	 *            请求的页号,默认为1.
	 * @param pageSize
	 *            每页记录数，默认为20.
	 */
	public DbDataset(String sql, ParameterSet params, String pageIndex,
			String pageSize) {
		this(sql, params, true, MathUtil.getInteger(pageIndex).intValue(),
				MathUtil.getInteger(pageSize).intValue());
	}

	/**
	 * 构造方法.
	 *
	 * @param sql
	 *            查询sql
	 * @param params
	 *            查询参数.
	 * @param pageIndex
	 *            请求的页号,默认为1.
	 * @param pageSize
	 *            每页记录数，默认为20.
	 */
	public DbDataset(String sql, String pageIndex, String pageSize) {
		this(sql, null, true, MathUtil.getInteger(pageIndex).intValue(),
				MathUtil.getInteger(pageSize).intValue());
	}

	/**
	 * 构造方法.
	 *
	 * @param sql
	 *            查询sql
	 * @param params
	 *            查询参数.
	 * @param pageIndex
	 *            请求的页号,默认为1.
	 * @param pageSize
	 *            每页记录数，默认为20条.
	 */
	public DbDataset(String sql, int pageIndex, int pageSize) {

		this(sql, null, true, pageIndex, pageSize);
	}

	/**
	 * 构造方法.
	 *
	 * @param sql
	 *            查询sql
	 */
	public DbDataset(String sql) {
		this(sql, null, true, 1, DEFAULT_PAGING_SIZE);
	}

	private Logger log = Logger.getLogger(this.getClass());
	/**
	 * 参数集合
	 */
	private ParameterSet parameterSet = new ParameterSet();

	private String sql = null;

	/**
	 * 是否支持分页
	 */
	private boolean supportPaging = true;

	/**
	 * 默认实体对应的类型
	 */
	public static final Class DEFUALT_ENTITY_CLAZZ = Record.class;

	/**
	 * 默认每页显示的分页记录数
	 */
	public static final int DEFAULT_PAGING_SIZE = 20;

	/**
	 * 对应的实体类型
	 */
	private Class clazz;
	/**
	 * 返回Dataset所对应的数据共有几页。
	 */
	private int pageCount;

	/**
	 * 返回Dataset所对应的当前分页数据共有几行。
	 */
	private int currPageRowCount;

	/**
	 * 返回Dataset的当前页号
	 */
	private int pageIndex;

	/**
	 * 取得每页的行数。
	 */
	private int pageSize = DEFAULT_PAGING_SIZE;

	/**
	 * 获取Dataset可能存在的总记录数。
	 */
	private long possibleRecordCount;

	private List data;

	/**
	 * 参数
	 *
	 * @return
	 */
	public ParameterSet parameters() {
		if (this.parameterSet == null) {
			this.parameterSet = new ParameterSet();
		}
		return this.parameterSet;
	}

	/**
	 * 设置参数
	 *
	 * @param name
	 *            参数名
	 * @param value
	 *            参数值
	 * @return 返回当前对象.
	 */
	public DbDataset setParameter(String name, Object value) {
		this.parameters().setValue(name, value);
		return this;
	}

	/**
	 * 设置参数.
	 *
	 * @param map
	 *            参数map
	 * @param names
	 *            参数名列表
	 * @return
	 */
	public DbDataset setParameters(Map map, String[] names) {

		if (map == null || map.isEmpty()) {
			return this;
		}

		if (names == null || names.length == 0) {
			for (Iterator<Map.Entry> iterator = map.entrySet().iterator(); iterator
					.hasNext();) {
				Map.Entry entry = iterator.next();
				this.parameters().setValue(entry.getKey().toString(),
						entry.getValue());
			}

		} else {
			for (Iterator<Map.Entry> iterator = map.entrySet().iterator(); iterator
					.hasNext();) {
				Map.Entry entry = iterator.next();
				if (isExists(names, entry.getKey().toString())) {
					this.parameters().setValue(entry.getKey().toString(),
							entry.getValue());
				}
			}
		}
		return this;
	}

	/**
	 * 设置参数.
	 *
	 * @param map
	 *            参数map
	 * @return
	 */
	public DbDataset setParameters(Map map) {
		return this.setParameters(map, null);
	}

	/**
	 * 根据Bean Object批量设置参数.
	 *
	 * @param obj
	 * @return
	 */
	public DbDataset setParameters(Object obj) {
		return this.setParameters(BeanUtils.describe(obj), null);

	}

	/**
	 * 根据Bean Object批量设置参数.
	 *
	 * @param obj
	 *            参数map
	 * @param names
	 *            参数名列表
	 * @return
	 */
	public DbDataset setParameters(Object obj, String[] names) {
		return this.setParameters(BeanUtils.describe(obj), names);
	}

	/**
	 * 根据Variantset 对象批量设置参数.
	 *
	 * @param obj
	 *            variantSet
	 * @param names
	 *            参数名列表
	 * @return
	 */
	public DbDataset setParameters(VariantSet variantSet, String[] names) {
		int len = 0;
		if (variantSet == null || ((len = variantSet.count()) == 0)) {
			log.error("varinatSet 参数 为空！");
			return this;
		}
		if (names == null || names.length == 0) {
			for (int i = 0; i < len; i++) {
				this.parameters().setValue(variantSet.indexToName(i),
						variantSet.getValue(i));
			}

		} else {
			for (int i = 0; i < len; i++) {
				String key = variantSet.indexToName(i);
				if (isExists(names, key)) {
					this.parameters().setValue(variantSet.indexToName(i),
							variantSet.getValue(i));
				}
			}
		}
		return this;

	}

	/**
	 * 根据Variantset 对象批量设置参数.
	 *
	 * @param obj
	 *            参数map
	 * @return
	 */
	public DbDataset setParameters(VariantSet variantSet) {

		return this.setParameters(variantSet, null);

	}

	/**
	 * 设置参数
	 *
	 * @return
	 */
	public Object getParameter(String name) {
		return this.parameters().getValue(name);
	}

	public Class getClazz() {
		return clazz;
	}

	/**
	 * 设置数据结果集DO对象类型
	 *
	 * @param clazz
	 * @return
	 */
	public DbDataset setClazz(Class clazz) {
		this.clazz = clazz;
		return this;
	}

	public int getPageCount() {
		return pageCount;
	}

	public int getPageIndex() {
		return pageIndex;
	}

	/**
	 * 加载数据
	 */
	/**
	 *
	 */
	public DbDataset loadData() {

		if (StringUtil.isEmpty(this.sql)) {
			throw new BaseException("请设置dataset SQl!");
		}
		// 根据TransilinkContext上下文编译字符
		String parsedSQl = translateSql(this.sql);
		if (StringUtil.isEmpty(parsedSQl)) {
			throw new BaseException("dataset SQl 解析错误!");
		}

		String querySQL = this.doGetSql(parsedSQl);

		Object[] parameters = this.doGetSqlParameters(parsedSQl);

		Class modelClazz = this.DEFUALT_ENTITY_CLAZZ;
		if (this.getClazz() != null) {
			modelClazz = this.getClazz();
		}

		if (this.isSupportPaging()) {

			PagingResultSet pagePagingResultSet = DBUtil.getPagingResultSet(
					querySQL, parameters, modelClazz, pageSize, pageIndex);
			// 结果数据
			this.setData(pagePagingResultSet.getData());
			// 分页总数
			this.setPageCount(pagePagingResultSet.getPageCount());
			// 当前页记录总数
			this.setCurrPageRowCount(this.getData().size());
			// 当前页号
			this.setPageIndex(pagePagingResultSet.getPageIndex());
			// 数据库总记录条数
			this.setPossibleRecordCount(pagePagingResultSet.getRowCount());

		} else {
			List list = DBUtil.query(querySQL, parameters, modelClazz);
			// 结果数据
			this.setData(list);
			int len = list.size();
			// 分页总数
			this.setPageCount(1);
			// 当前页记录总数
			this.setCurrPageRowCount(len);
			// 当前页号
			this.setPageIndex(1);
			// 总记录条数
			this.setPossibleRecordCount(len);
		}
		return this;
	}

	/**
	 * 转换为分页 huangxin
	 *
	 * @return
	 */
	public Page toPage() {
		Page page = new Page();
		page.setItems(this.getData());
		Long total = 0L;
		if (this.getPossibleRecordCount() > 0) {
			total = this.getPossibleRecordCount();
		}
		page.setTotal(total.intValue());
		return page;
	}

	/**
	 * 根据当前Context上下文环境翻译字符串
	 *
	 * @param sql
	 * @return
	 */
	private String translateSql(String sql) {
		Map params = new HashMap();

		for (int i = 0, len = this.parameterSet.count(); i < len; i++) {
			String name = this.parameterSet.indexToName(i);
			Object value = this.parameterSet.getValue(i);
			params.put(name, value);
		}

		return sql;
	}

	/**
	 *
	 * 翻译字符串
	 *
	 * @param sql
	 * @return
	 */
	private String doGetSql(String sql) {

		return sql.replaceAll(":(\\w*)", "?");

	}

	/**
	 * 得到解析后的sql字符
	 *
	 * @return
	 */
	public String getParsedSql() {

		// 根据TransilinkContext上下文编译字符
		String parsedSQl = translateSql(this.sql);
		if (StringUtil.isEmpty(parsedSQl)) {
			return null;
		}

		String querySQL = this.doGetSql(parsedSQl);
		return querySQL;

	}

	/**
	 * 根据SQL获取对应的参数值
	 *
	 * @param sql
	 * @return
	 */
	private Object[] doGetSqlParameters(String sql) {

		List list = new ArrayList();
		Pattern patter = Pattern.compile(":(\\w*)", Pattern.CASE_INSENSITIVE
				| Pattern.DOTALL);
		Matcher pMatcher = patter.matcher(sql);
		while (pMatcher.find()) {
			String parameName = StringUtil.trim(pMatcher.group(1));
			if (StringUtil.isValid(parameName)) {
				Object value = this.parameterSet.getValue(parameName);
				if (value != null && value instanceof String) {
				}
				list.add(value);
			}
		}
		if (list.isEmpty())
			return null;
		return list.toArray();
	}

	protected DbDataset setPageIndex(int pageIndex) {
		if (pageIndex <= 0) {
			this.pageIndex = 1;
		} else {
			this.pageIndex = pageIndex;

		}

		return this;
	}

	public int getPageSize() {

		return pageSize;
	}

	/**
	 *
	 * 设置每页显示的记录数量
	 *
	 * @param pageSize
	 */
	public DbDataset setPageSize(int pageSize) {
		if (pageSize <= 0) {
			this.pageSize = PageContext.getPageSize();
		} else {
			this.pageSize = pageSize;

		}
		return this;
	}

	/**
	 * 设置每页显示的记录数量
	 *
	 * @param pageSize
	 */
	public DbDataset setPageSize(String pageSize) {

		return this.setPageSize(MathUtil.getInteger(pageSize).intValue());

	}

	/**
	 * 得到记录总数量
	 *
	 * @return
	 */
	public long getPossibleRecordCount() {
		return possibleRecordCount;
	}

	protected DbDataset setPossibleRecordCount(long possibleRecordCount) {
		this.possibleRecordCount = possibleRecordCount;
		return this;
	}

	/**
	 * 获取当前页总记录条数
	 *
	 * @return
	 */
	public int getCurrPageRowCount() {
		return currPageRowCount;
	}

	protected DbDataset setCurrPageRowCount(int currPageRowCount) {
		this.currPageRowCount = currPageRowCount;
		return this;
	}

	public String getSql() {
		return sql;
	}

	/**
	 * 设置sql
	 *
	 * @param sql
	 */
	public DbDataset setSql(String sql) {
		this.sql = sql;
		return this;
	}

	/**
	 * 设置sql
	 *
	 * @param sql
	 */
	public DbDataset setSql(StringBuffer sql) {
		if (sql != null) {
			this.sql = sql.toString();
		} else {

			this.sql = null;

		}
		return this;
	}

	public String getPagingSql(String sql) {

		return sql;

	}

	public boolean isSupportPaging() {
		return supportPaging;
	}

	protected DbDataset setSupportPaging(boolean supportPaging) {
		this.supportPaging = supportPaging;
		return this;
	}

	/**
	 * 返回结果集数据
	 *
	 * @return
	 */
	public List getData() {
		return data;
	}

	protected DbDataset setData(List data) {
		this.data = data;
		return this;
	}

	protected DbDataset setPageCount(int pageCount) {
		this.pageCount = pageCount;
		return this;
	}

	/**
	 * 将查询结果转换为JSONArray数组,格式[{},{}],其中对象属性包括结果集对象所有属性值列表.
	 *
	 * @return
	 */
	public JSONArray toJSONArray() {
		return this.toJSONArray(null);
	}

	/**
	 * 将查询结果转换为JSONArray数组，格式[{},{}]
	 *
	 * @param names
	 *            转换为json对象的属性列表
	 * @return
	 */
	public JSONArray toJSONArray(String[] names) {

		JSONArray jsonArray = new JSONArray();
		if (this.data != null && !this.data.isEmpty()) {
			for (int i = 0, len = this.data.size(); i < len; i++) {
				jsonArray.add(toJSONObject(i, names));
			}
		}
		return jsonArray;

	}

	/**
	 * 将查询结果转换为JSONObject,格式:{total:xx,data:[{name:value,name1:value1,...},..,{}
	 * ]}
	 *
	 * @param names
	 *            转换为json对象的属性列表
	 * @return
	 */
	public JSONObject toJSONObject() {
		return toJSONObject(null);
	}

	/**
	 * 将查询结果转换为JSONObject,格式:{total:xx,data:[{name:value,name1:value1,...},..,{}
	 * ]}
	 *
	 * @param names
	 *            转换为json对象的属性列表
	 * @return
	 */
	public JSONObject toJSONObject(String[] names) {
		JSONObject jsonObject = new JSONObject();
		JsonUtil.put(jsonObject, "total", this.getPossibleRecordCount());
		JsonUtil.put(jsonObject, "data", toJSONArray(names));
		return jsonObject;
	}

	/**
	 * 将首条记录转换为JSONObject,格式{name:value,name1:value1..}
	 *
	 * @return
	 */
	public JSONObject toSingleJSONObject() {

		return toSingleJSONObject(null);

	}

	/**
	 * 将首条记录转换为JSONObject,格式{name:value,name1:value1,...}
	 *
	 * @param names
	 *            需转换为json对象的属性列表
	 * @return
	 */
	public JSONObject toSingleJSONObject(String[] names) {
		JSONObject jsonObject = null;
		if (this.data == null || this.data.isEmpty()) {
			jsonObject = new JSONObject();
		} else {
			jsonObject = toJSONObject(0, names);
		}
		return jsonObject;

	}

	/**
	 * 将查询结果转换为JSON
	 * Presention格式:{total:xx,data:[{name:value,name1:value1,...},..,{}]}
	 *
	 * @return
	 */
	public Representation toJSONObjectPresention() {

		return this.toJSONObjectPresention(null);
	}

	/**
	 * 将查询结果转换为JSON
	 * Presention格式:{total:xx,data:[{name:value,name1:value1,...},..,{}]}
	 *
	 * @param names
	 *            转换为json对象的属性列表
	 * @return
	 */
	public Representation toJSONObjectPresention(String[] names) {

		return new StringRepresentation(toJSONObject(names).toString(),
				MediaType.TEXT_PLAIN, null, CharacterSet.UTF_8);
	}

	/**
	 * 将首条记录转换为JSON Presention ,格式{name:value,name1:value1..}
	 *
	 * @return
	 */
	public Representation toSingleJSONObjectPresention() {

		return toSingleJSONObjectPresention(null);

	}

	/**
	 * 将首条记录转换为JSON Presention,格式{name:value,name1:value1,...}
	 *
	 * @param names
	 *            转换为json对象的属性列表
	 * @return
	 */
	public Representation toSingleJSONObjectPresention(String[] names) {

		return new StringRepresentation(toSingleJSONObject(names).toString(),
				MediaType.TEXT_PLAIN, null, CharacterSet.UTF_8);

	}

	/**
	 * 将查询结果转换为JSON Array Presention,格式[{},{}],其中对象属性包括结果集对象所有属性值列表.
	 *
	 * @return
	 */
	public Representation toJSONArrayPresention() {
		return this.toJSONArrayPresention(null);
	}

	/**
	 * 将查询结果转换为JSONArray数组，格式[{},{}]
	 *
	 * @param names
	 *            转换为json对象的属性列表
	 * @return
	 */
	public Representation toJSONArrayPresention(String[] names) {

		return new StringRepresentation(toJSONArray(names).toString(),
				MediaType.TEXT_PLAIN, null, CharacterSet.UTF_8);

	}

	/**
	 * 将第n个对象转换为JsonObject对象,如果为空，则转为空对象{}
	 *
	 * @param index
	 * @param names
	 *            转换json对象的属性列表
	 * @return
	 */
	public JSONObject toJSONObject(int index, String names[]) {
		if (this.getData() == null || this.data.isEmpty()) {
			return new JSONObject();
		}

		Object obj = this.getData().get(index);

		return JsonUtil.toJSONObject(obj, names);

	}

	/**
	 * 判断是否存在
	 *
	 * @param names
	 * @param name
	 * @return
	 */
	private static boolean isExists(String[] names, String name) {
		boolean flag = false;
		for (int i = 0, len = names.length; i < len; i++) {
			if (name.equals(names[i])) {
				flag = true;
				break;
			}
		}
		return flag;
	}

}
