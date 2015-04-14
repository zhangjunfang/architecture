/**
 * 
 */
package cn.newcapec.framework.plugins.quartz.controller;

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
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import cn.newcapec.framework.core.handler.MultiViewResource;
import cn.newcapec.framework.core.utils.dataUtils.DateUtil;
import cn.newcapec.framework.core.utils.pagesUtils.PageContext;
import cn.newcapec.framework.core.utils.pagesUtils.PageView;
import cn.newcapec.framework.plugins.quartz.vo.TriggerForm;

/**
 * @author 景明超
 * @version TriggerController.java 2014-1-6 下午1:45:51
 */
@Controller
@Scope("prototype")
@RequestMapping(value = "trigger")
public class TriggerController extends MultiViewResource{
	@Autowired
	private Scheduler scheduler;
	
	@RequestMapping(value="triggerListUI")
	public ModelAndView triggerListUI(ModelMap modelMap){
		triggerListGrid(modelMap);
		return toView(getUrl("trigger.triggerListUI"),modelMap);
	}

	
	@RequestMapping(value="triggerListGrid")
	public ModelAndView triggerListGrid(ModelMap modelMap) {
		
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
	    
	    PageView<TriggerForm>pageView=new PageView<TriggerForm>(PageContext.getPagesize(), PageContext.getOffset());
	    if(PageContext.getOffset()==0){
			PageContext.setOffset(1);
		}
	    int fromIndex=(PageContext.getOffset()-1)*PageContext.getPagesize();
		int toIndex=fromIndex+PageContext.getPagesize();
		if(toIndex>allList.size()){
			toIndex=allList.size();
		}
		pageView.setTotalrecord(allList.size());
		pageView.setRecords(allList.subList(fromIndex, toIndex));
		pageView.setJsMethod("reloadTriggerList");
		modelMap.put("pageView", pageView);
		return toView(getUrl("trigger.triggerListGrid"), modelMap);
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
