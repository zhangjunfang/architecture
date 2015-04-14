package com.transilink.framework.core.model;

import javax.persistence.MappedSuperclass;

import com.transilink.framework.core.model.datacontainer.DataObject;

/**
 *
 * @author huangxin (3203317@qq.com)
 *
 */
@MappedSuperclass
public class AppBaseModel extends BaseModel implements DataObject {
	private static final long serialVersionUID = -602515871859035627L;
}