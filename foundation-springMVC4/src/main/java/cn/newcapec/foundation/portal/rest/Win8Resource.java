package cn.newcapec.foundation.portal.rest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import cn.newcapec.foundation.privilege.biz.MenuService;
import cn.newcapec.foundation.privilege.biz.ResourceService;
import cn.newcapec.foundation.privilege.model.Menu;
import cn.newcapec.foundation.privilege.utils.LoginAccount;
import cn.newcapec.framework.core.context.HttpNewcapecContextFactory;
import cn.newcapec.framework.core.context.Keys;
import cn.newcapec.framework.core.exception.asserts.AssertObject;
import cn.newcapec.framework.core.rest.BaseRequest;
import cn.newcapec.framework.core.rest.BaseResource;
import cn.newcapec.framework.core.rest.BaseResourceHandler;
import cn.newcapec.framework.core.rest.BaseResponse;
import cn.newcapec.framework.core.rest.Msg;
import cn.newcapec.framework.core.utils.httpUtils.WebUtils;
import cn.newcapec.framework.core.utils.pagesUtils.PageContext;




/**
 * win8模式视图资源类
 * @author andy.li
 *
 */

@Component
@Scope("prototype")
@SuppressWarnings("all")
public class Win8Resource extends BaseResource implements BaseResourceHandler {


	/* 资源业务类 */
	@Autowired
	private MenuService menuService;
	@Autowired
	private ResourceService resourceService;
	// 排序参数
	private LinkedHashMap<String, String> orderby = new LinkedHashMap<String, String>();
	
	/**
	 * 首页
	 * @param request
	 * @param response
	 */
	public void welcome(BaseRequest request, final BaseResponse response) {
		final String url = "function/foundation/portal/win8/pagelet/welcome.html";
		doExpAssert(new AssertObject(){
			@Override
			public void AssertMethod(Msg Msg) {
				response.toView(url, getNewcapectViewContext());	
			}
			
		});
	}
	
	/**
	 * 首页
	 * @param request
	 * @param response
	 */
	public void index(final BaseRequest request, final BaseResponse response) {
		final String url = "foundation/portal/office/pagelet/index.html";
		doExpAssert( new AssertObject(){

			@Override
			public void AssertMethod(Msg Msg) {
				HttpSession session = WebUtils
				.getSession(request.getOrginRequest());
//		User user = (User) session.getAttribute("user");
		LoginAccount user = (LoginAccount)HttpNewcapecContextFactory.getContext(WebUtils.getRequests(request.getOrginRequest())).getAttribute(5, Keys.USER);

		if (null != user) {
			PageContext.setPagesize(Integer.MAX_VALUE);
			Map<String, Object> rootParams = new HashMap<String, Object>();
			rootParams.put("parent_id", -1);
//			// 获取根节点
//			context.put("first", menuService.queryMenu(rootParams, orderby));
//			// 第二结点
//			context.put("second", menuService.queryMenuBySecond(null,
//					orderby));
			//获取用户的所有资源（缺少根菜单）
			List list  = resourceService.queryResorucesByUserid(user.getOperatorId().toString());
			if(!list.isEmpty()){
				Set set = new HashSet();
				for (int i = 0; i < list.size(); i++) {
					Iterator it = list.iterator();
					while (it.hasNext()) {
						Map map = (Map) it.next();
						for (Iterator<String> keys = map.keySet().iterator(); keys
						.hasNext();) {
							String key = (String) keys.next();
							if("MENU_ID".equals(key)){
								String value = map.get(key).toString();
								set.add("'" + value + "'");
							}
						}
					}
				}
				if(!set.isEmpty()){
					//获得所有登录用户的菜单信息
					List<Menu> menusList  = menuService.queryCascadeMenus(set.toArray(), orderby);
					//获取根节点
					List<Menu> rootMenu = new ArrayList();
					List<Menu> noRootMenu = new ArrayList();
					if(null!=menusList && menusList.size()>0){
						for(int i =0;i<menusList.size();i++){
							Menu menu = menusList.get(i);
							if(null!=menu && "-1".equals(menu.getParentId())){
								if(null!=rootMenu && rootMenu.size()>0){
									for(Menu menu2 : rootMenu){
										if(!menu2.getId().equals(menu.getId())){
											rootMenu.add(menu);
										}
									}
								}else{
									rootMenu.add(menu);
								}
							}else{
								noRootMenu.add(menu);
							}
						}
					}
			
					getNewcapectViewContext().put("rootMenu", rootMenu);
					getNewcapectViewContext().put("noRootMenu", noRootMenu);
				}
			}
			response.toView(url, getNewcapectViewContext());
		}
			}
			
		});
	}

}
