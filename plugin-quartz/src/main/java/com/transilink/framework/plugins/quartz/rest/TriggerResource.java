
package com.transilink.framework.plugins.quartz.rest;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.quartz.Scheduler;
import org.quartz.Trigger;
import org.quartz.TriggerKey;
import org.quartz.impl.matchers.GroupMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.transilink.framework.core.rest.BaseRequest;
import com.transilink.framework.core.rest.BaseResource;
import com.transilink.framework.core.rest.BaseResponse;
import com.transilink.framework.core.utils.dataUtils.DateUtil;
import com.transilink.framework.core.utils.pagesUtils.PageContext;
import com.transilink.framework.core.utils.pagesUtils.PageView;
import com.transilink.framework.plugins.quartz.vo.TriggerForm;

/**
 * 
 * 描述：
 * 
 * @author ocean
 * 2015年4月15日
 *  email：zhangjunfang0505@163.com
 */
@Scope("prototype")
@Component
public class TriggerResource extends BaseResource{
	
	@Autowired
	private Scheduler scheduler;
	
	public void triggerListUI(BaseRequest request,BaseResponse response){
		triggerListGrid(request,response);
		response.toView(getUrl("trigger.triggerListUI"), getTransilinkViewContext());
	}

	/**
	 * @author 景明超
	 * 2014-1-2 下午1:37:51
	 * @param request
	 * @param response
	 */
	public void triggerListGrid(BaseRequest request, BaseResponse response) {
		
		Map<String, List<TriggerForm>> triggers = null;
		try {
			triggers = this.getTriggers();
		} catch (Exception e) {
			log.error(null, e);
		}
		List<TriggerForm> allList = new ArrayList<TriggerForm>();
	    if (triggers != null && !triggers.isEmpty()) {
			for (Iterator<String> iter = triggers.keySet().iterator(); iter.hasNext();) {
				String key = iter.next();
				List<TriggerForm> list = triggers.get(key);
				for (int i = 0; i < list.size(); i++) {
					allList.add(list.get(i));
				}
			}
		}
	    
	    PageView<TriggerForm>pageView=new PageView<TriggerForm>(PageContext.getPageSize(), PageContext.getOffset());
	    if(PageContext.getOffset()==0){
			PageContext.setOffset(1);
		}
	    int fromIndex=(PageContext.getOffset()-1)*PageContext.getPageSize();
		int toIndex=fromIndex+PageContext.getPageSize();
		if(toIndex>allList.size()){
			toIndex=allList.size();
		}
		pageView.setTotalRecord(allList.size());
		pageView.setRecords(allList.subList(fromIndex, toIndex));
		pageView.setJsMethod("reloadTriggerList");
		getTransilinkViewContext().put("pageView", pageView);
		response.toView(getUrl("trigger.triggerListGrid"), getTransilinkViewContext());
	}
	
	
	private Map<String, List<TriggerForm>> getTriggers() throws Exception {
		Map<String, List<TriggerForm>> triggers = new java.util.LinkedHashMap<String, List<TriggerForm>>();
		List<String> triggerGroupNames = this.scheduler.getTriggerGroupNames();
		if (triggerGroupNames != null) {
			for (String groupName : triggerGroupNames) {
				List<TriggerForm> tgs = new java.util.LinkedList<TriggerForm>();
				triggers.put(groupName, tgs);
				Set<TriggerKey> triggerKeys = this.scheduler.getTriggerKeys(GroupMatcher.triggerGroupEquals(groupName));
				if (triggerKeys != null) {
					for (TriggerKey triggerKey : triggerKeys) {
						Trigger trigger = this.scheduler.getTrigger(triggerKey);
						if (trigger == null) continue;

						TriggerForm form = new TriggerForm();
						form.setJobGroup(trigger.getJobKey().getGroup());
						form.setJobName(trigger.getJobKey().getName());
						form.setTriggerGroup(triggerKey.getGroup());
						form.setTriggerName(triggerKey.getName());
						form.setDescription(trigger.getDescription());
						form.setType(trigger.getClass().getSimpleName());
						form.setNextFireTime(DateUtil.toDatetimeString(trigger.getNextFireTime()));
						form.setStartTime(DateUtil.toDatetimeString(trigger.getStartTime()));
						form.setStopTime(DateUtil.toDatetimeString(trigger.getEndTime()));
						form.setPreviousFireTime(DateUtil.toDatetimeString(trigger.getPreviousFireTime()));
						form.setMisFireInstruction(trigger.getMisfireInstruction());
						tgs.add(form);
					}
				}
			}
		}
		return triggers;
	}
}
