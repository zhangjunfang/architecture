/**
 *
 */
package cn.newcapec.function.platform.tree.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @Description :
 * @author : ocean
 * @date : 2014-5-13 上午10:37:07
 * @email : zhangjunfang0505@163.com
 * @Copyright : newcapec zhengzhou
 */
public class AssistTree implements Serializable {

	private static final long serialVersionUID = -6195819894962586018L;

	private String moduleId = "";
	private String pModuleId = "";
	private String moduleName = "";
	private String depth = "";
	private String smallPic = "";
	private String bigPic = "";

	private List<Object> children = new ArrayList<Object>(200);// 这里有优化的地方

	public String getModuleId() {
		return moduleId;
	}

	public void setModuleId(String moduleId) {
		this.moduleId = moduleId;
	}

	public String getpModuleId() {
		return pModuleId;
	}

	public void setpModuleId(String pModuleId) {
		this.pModuleId = pModuleId;
	}

	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	public String getDepth() {
		return depth;
	}

	public void setDepth(String depth) {
		this.depth = depth;
	}

	public String getSmallPic() {
		return smallPic;
	}

	public void setSmallPic(String smallPic) {
		this.smallPic = smallPic;
	}

	public String getBigPic() {
		return bigPic;
	}

	public void setBigPic(String bigPic) {
		this.bigPic = bigPic;
	}

	public List<Object> getChildren() {
		return children;
	}

	public void setChildren(List<Object> children) {
		this.children = children;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((bigPic == null) ? 0 : bigPic.hashCode());
		result = prime * result
				+ ((children == null) ? 0 : children.hashCode());
		result = prime * result + ((depth == null) ? 0 : depth.hashCode());
		result = prime * result
				+ ((moduleId == null) ? 0 : moduleId.hashCode());
		result = prime * result
				+ ((moduleName == null) ? 0 : moduleName.hashCode());
		result = prime * result
				+ ((pModuleId == null) ? 0 : pModuleId.hashCode());
		result = prime * result
				+ ((smallPic == null) ? 0 : smallPic.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AssistTree other = (AssistTree) obj;
		if (bigPic == null) {
			if (other.bigPic != null)
				return false;
		} else if (!bigPic.equals(other.bigPic))
			return false;
		if (children == null) {
			if (other.children != null)
				return false;
		} else if (!children.equals(other.children))
			return false;
		if (depth == null) {
			if (other.depth != null)
				return false;
		} else if (!depth.equals(other.depth))
			return false;
		if (moduleId == null) {
			if (other.moduleId != null)
				return false;
		} else if (!moduleId.equals(other.moduleId))
			return false;
		if (moduleName == null) {
			if (other.moduleName != null)
				return false;
		} else if (!moduleName.equals(other.moduleName))
			return false;
		if (pModuleId == null) {
			if (other.pModuleId != null)
				return false;
		} else if (!pModuleId.equals(other.pModuleId))
			return false;
		if (smallPic == null) {
			if (other.smallPic != null)
				return false;
		} else if (!smallPic.equals(other.smallPic))
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder(400);
		builder.append("{moduleId=");
		builder.append(moduleId);
		builder.append(", pModuleId=");
		builder.append(pModuleId);
		builder.append(", moduleName=");
		builder.append(moduleName);
		builder.append(", depth=");
		builder.append(depth);
		builder.append(", smallPic=");
		builder.append(smallPic);
		builder.append(", bigPic=");
		builder.append(bigPic);
		builder.append(", children=");
		builder.append(children);
		builder.append("}");
		return builder.toString();
	}

}
