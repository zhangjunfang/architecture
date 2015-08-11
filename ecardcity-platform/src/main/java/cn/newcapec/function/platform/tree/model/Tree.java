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
 * @date : 2014-5-13 上午10:37:13
 * @email : zhangjunfang0505@163.com
 * @Copyright : newcapec zhengzhou
 */
public class Tree implements Serializable {
	private static final long serialVersionUID = 5654774038177500429L;

	private String id = "";
	private String pId = "";
	private String name = "";
	private String file = "";
	private List<Tree> trees = new ArrayList<Tree>(400);

	/**
	 * @param id
	 * @param pId
	 * @param name
	 * @param file
	 * @param trees
	 */
	public Tree(String id, String pId, String name, String file,
			List<Tree> trees) {
		super();
		this.id = id;
		this.pId = pId;
		this.name = name;
		this.file = file;
		this.trees = trees;
	}

	/**
	 *
	 */
	public Tree() {
		super();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getpId() {
		return pId;
	}

	public void setpId(String pId) {
		this.pId = pId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFile() {
		return file;
	}

	public void setFile(String file) {
		this.file = file;
	}

	public List<Tree> getTrees() {
		return trees;
	}

	public void setTrees(List<Tree> trees) {
		this.trees = trees;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((file == null) ? 0 : file.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((pId == null) ? 0 : pId.hashCode());
		result = prime * result + ((trees == null) ? 0 : trees.hashCode());
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
		Tree other = (Tree) obj;
		if (file == null) {
			if (other.file != null)
				return false;
		} else if (!file.equals(other.file))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (pId == null) {
			if (other.pId != null)
				return false;
		} else if (!pId.equals(other.pId))
			return false;
		if (trees == null) {
			if (other.trees != null)
				return false;
		} else if (!trees.equals(other.trees))
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("{id=");
		builder.append(id);
		builder.append(", pId=");
		builder.append(pId);
		builder.append(", name=");
		builder.append(name);
		builder.append(", file=");
		builder.append(file);
		builder.append(", trees=");
		builder.append(trees);
		builder.append("}");
		return builder.toString();
	}

}
