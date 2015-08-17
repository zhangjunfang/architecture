/**
 * Copyright (c) 2011-2015, @author ocean(zhangjufang0505@163.com).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.framework.core.single.table.orm.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * DuplexMap.Both the k and v are unique and you can find v by k or find k by v conveniently.
 * @author ocean(zhangjufang0505@163.com)
 *
 */
public class DuplexMap<K,V> {

	class Entry{
        K k;
        V v;
        public Entry(K k,V v){
            this.k=k;
            this.v=v;
        }
        public K getK() {
            return k;
        }
        public V getV() {
            return v;
        }
        public void setK(K k) {
            this.k = k;
        }
        public void setV(V v) {
            this.v = v;
        }
    }
    private HashMap<K,Entry> kEntyMap=new HashMap<K,Entry>();
    private HashMap<V,Entry> vEntyMap=new HashMap<V,Entry>();
    public boolean containsK(K k){
        return kEntyMap.containsKey(k);
    }
    public boolean containsV(V v){
        return vEntyMap.containsKey(v);
    }
    public V getByK(K k){
        Entry e=kEntyMap.get(k);
        if(e==null){
            return null;
        }
        return e.getV();
    }
    public K getbyV(V v){
        Entry e=vEntyMap.get(v);
        if(e==null){
            return null;
        }
        return e.getK();
    }
    public boolean put(K k,V v){
        if(k==null||v==null){
            return false;
        }
        Entry e=new Entry(k, v);
        if(containsK(k)){
            removeByK(k);
        }
        if(containsV(v)){
            removeByV(v);
        }
        kEntyMap.put(k, e);
        vEntyMap.put(v, e);
        return true;
    }
    public V removeByK(K k){
        Entry e=kEntyMap.remove(k);
        if(e==null){
            return null;
        }
        vEntyMap.remove(e.getV());
        return e.getV();
    }
    public K removeByV(V v){
        Entry e=vEntyMap.remove(v);
        if(e==null){
            return null;
        }
        kEntyMap.remove(e.getK());
        return e.getK();
    }
    public Set<K> KSet() {
    	return kEntyMap.keySet();
	}
    public Set<V> VSet() {
    	return vEntyMap.keySet();
	}
    public Set<java.util.Map.Entry<K, DuplexMap<K, V>.Entry>> KEntrySet() {
		return kEntyMap.entrySet();
	}
    public Set<java.util.Map.Entry<V, DuplexMap<K, V>.Entry>> VEntrySet() {
    	return vEntyMap.entrySet();
	}
    public DuplexMap<V, K> invert() {
    	DuplexMap<V, K> duplexMap = new DuplexMap<V, K>();
    	for(Map.Entry<K, Entry> e:KEntrySet()){
    		duplexMap.put(e.getValue().getV(), e.getKey());
    	}
		return duplexMap;
	}
    @Override
    public String toString() {
    	if(KEntrySet().isEmpty())
    		return null;
    	StringBuilder sb = new StringBuilder("{");
    	for(java.util.Map.Entry<K, DuplexMap<K, V>.Entry> e:KEntrySet()){
    		sb.append(e.getKey()).append("=").append(e.getValue().getV()).append(",");
    	}
    	sb.replace(sb.lastIndexOf(","), sb.length(), "}");
    	return sb.toString();
    }
    public boolean isEmpty() {
		return kEntyMap.isEmpty();
	}
}
