package com.transilink.framework.core.rest;

import org.restlet.Application;
import org.restlet.Context;

/**
 *
 * @author ocean(zhangjufang0505@163.com)
 *
 */
public class BaseApplication extends Application {
	private boolean tunnelServiceExtensionsTunnerl = false;

	public BaseApplication(Context context) {
		super(context.createChildContext());
		getTunnelService().setExtensionsTunnel(
				this.tunnelServiceExtensionsTunnerl);
	}

	public boolean isTunnelServiceExtensionsTunnerl() {
		return this.tunnelServiceExtensionsTunnerl;
	}

	public void setTunnelServiceExtensionsTunnerl(
			boolean tunnelServiceExtensionsTunnerl) {
		this.tunnelServiceExtensionsTunnerl = tunnelServiceExtensionsTunnerl;
	}
}