package cn.newcapec.framework.plugins.cache.jndi;

import java.io.Serializable;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.Name;
import javax.naming.NameAlreadyBoundException;
 
public class Rediscache implements RediscacheMBean, Serializable {
	private String jndiName;
	private String host;
	private Integer port;
	
	private void rebind(){
		try {
			this.rebind(null);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Override
	public void rebind(String bindName) throws Exception {
		InitialContext ictx = new InitialContext();
		if (jndiName != null) {
			Name name = ictx.getNameParser(jndiName).parse(jndiName);
			Context ctx = ictx;
			int i = 0;
			for (int max = name.size() - 1; i < max; i++)
				try {
					ctx = ctx.createSubcontext(name.get(i));
				} catch (NameAlreadyBoundException ignore) {
					ctx = (Context) ctx.lookup(name.get(i));
				}

			ictx.rebind(jndiName, this);
		}
	}

	@Override
	public String getJndiName() {
		// TODO Auto-generated method stub
		return this.jndiName;
	}

	@Override
	public void setJndiName(String jndiName) throws Exception {
		this.jndiName = jndiName;
		this.rebind(this.jndiName);
	}

	@Override
	public String getHost() {
		// TODO Auto-generated method stub
		return this.host;
	}

	@Override
	public void setHost(String host) {
		this.host = host;
		this.rebind();
	}

	@Override
	public Integer getPort() {
		// TODO Auto-generated method stub
		return this.port;
	}

	@Override
	public void setPort(Integer port) {
		this.port = port;
		this.rebind();
	}

}
