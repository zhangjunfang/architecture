/**
 * 
 */
package cn.newcapec.foundation.portal.tag;

import java.util.Map;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.lang.StringUtils;

import cn.newcapec.framework.core.exception.BaseException;
import cn.newcapec.framework.core.utils.pagesUtils.PageView;


/**
 * @author 景明超
 * @version Pagingtag.java 2013-12-9 上午9:23:02
 */
public class PagingTag extends TagSupport{

	
	private static final long serialVersionUID = 503860846066796073L;
	private PageView<Map<String, Object>> pageView;

	/* (non-Javadoc)
	 * @see javax.servlet.jsp.tagext.TagSupport#doStartTag()
	 */
	@Override
	public int doStartTag() throws JspException {
		try {
			StringBuilder sb=new StringBuilder();
			sb.append("<div class=\"pages\" style=\"font-size: 13px;\"> ");
			if(pageView.getTotalpage()>0){
				sb.append("<font color=\"#000000\">当前页:第").append(pageView.getCurrentpage())
				.append("页 | 总记录数:").append(pageView.getTotalrecord()).append("条 | 每页显示:")
				.append(pageView.getMaxresult()).append("条 | 总页数:").append(pageView.getTotalpage()).append("页</font>　");
				if(pageView.getCurrentpage()!=1){
					sb.append("<a href=\"javascript:");
					if(StringUtils.isNotBlank(pageView.getJsMethod())){
						sb.append(pageView.getJsMethod()).append("(1)");
					}
					sb.append("\" title=\"首页\" class=\"nav\"><span>首页</span></a><a href=\"javascript:");
					if(StringUtils.isNotBlank(pageView.getJsMethod())){
						sb.append(pageView.getJsMethod()).append("(")
						.append(pageView.getCurrentpage()-1).append(")");
					}
					sb.append("\" title=\"上一页\" class=\"nav\"><span>上一页</span></a>");
				}else {
					sb.append("<span>首页</span><span>上一页</span>");
				}

				if(pageView.getCurrentpage()!=pageView.getTotalpage()&&pageView.getTotalpage()!=0){
					sb.append("<a href=\"javascript:");
					if(StringUtils.isNotBlank(pageView.getJsMethod())){
						sb.append(pageView.getJsMethod()).append("(").append(pageView.getCurrentpage()+1)
						.append(")");
					}
					sb.append("\" title=\"下一页\" class=\"nav\"><span>下一页</span></a><a href=\"javascript:");
					if(StringUtils.isNotBlank(pageView.getJsMethod())){
						sb.append(pageView.getJsMethod()).append("(").append(pageView.getTotalpage())
						.append(")");
					}
					sb.append("\" title=\"尾页\" class=\"nav\"><span>尾页</span></a>");
				}else {
					sb.append("<span>下一页</span><span>未页</span>");
				}
				sb.append("<input type=\"text\" name=\"offset\" id=\"offset\" size=\"2\" value=\"")
				.append(pageView.getCurrentpage()).append("\" /><input type=\"button\" value=\"GO\" onclick=\"");
				if(StringUtils.isNotBlank(pageView.getJsMethod())){
					sb.append(pageView.getJsMethod()).append("(0)");
				}
				sb.append("\" />");
			}else {
				sb.append("<div style=\"font-size: 13px;font-weight: 700;line-height: 177px;text-align: center;\">暂无数据！</div>");
			}

			pageContext.getOut().write(sb.toString());
		} catch (Exception e) {
			if (e instanceof BaseException) {
				throw (BaseException) e;
			} else {
				throw new BaseException("分页失败！", e);
			}
		}
		return SKIP_BODY;
	}

	/**
	 * @return the pageView
	 */
	public PageView<Map<String, Object>> getPageView() {
		return pageView;
	}

	/**
	 * @param pageView the pageView to set
	 */
	public void setPageView(PageView<Map<String, Object>> pageView) {
		this.pageView = pageView;
	}


}
