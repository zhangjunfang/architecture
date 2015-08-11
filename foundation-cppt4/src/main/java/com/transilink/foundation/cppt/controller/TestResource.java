/**
 * 
 */
package com.transilink.foundation.cppt.controller;


import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
 
import com.transilink.framework.core.rest.BaseRequest;
import com.transilink.framework.core.rest.BaseResource;
import com.transilink.framework.core.rest.BaseResponse;

/**
 * 描述：
 * 
 * @author ocean
 * 2015年8月7日
 *  email：zhangjunfang0505@163.com
 */
@Component
@Scope("prototype")
public class TestResource extends BaseResource {
   
	public  void index(BaseRequest request, BaseResponse response){
		response.print("-------------------------------");
	}
	
}
