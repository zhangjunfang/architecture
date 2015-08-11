package cn.newcapec.function.platform.ocean;

import java.util.List;
import java.util.Map;
/**
 *
 * @Description :
 * @author : ocean
 * @date : 2014-5-13 上午10:38:04
 * @email : zhangjunfang0505@163.com
 * @Copyright : newcapec zhengzhou
 */
@SuppressWarnings("all")
public class CacheSizes
{
  public static int sizeOfObject()
  {
    return 4;
  }

  public static int sizeOfString(String string) {
    if (string == null) {
      return 0;
    }
    return 4 + string.length() * 2;
  }

  public static int sizeOfInt() {
    return 4;
  }

  public static int sizeOfChar() {
    return 2;
  }

  public static int sizeOfBoolean() {
    return 1;
  }

  public static int sizeOfLong() {
    return 8;
  }

  public static int sizeOfDouble() {
    return 8;
  }

  public static int sizeOfDate() {
    return 12;
  }

  public static int sizeOfMap(Map map)
  {
    if (map == null)
      return 0;
    int size = 36;
    Object[] values = map.values().toArray();
    for (int i = 0; i < values.length; i++) {
      size += sizeOfString((String)values[i]);
    }
    Object[] keys = map.keySet().toArray();
    for (int i = 0; i < keys.length; i++) {
      size += sizeOfString((String)keys[i]);
    }
    return size;
  }

  public static int sizeOfList(List list)
  {
    if (list == null)
      return 0;
    int size = 36;
    Object[] values = list.toArray();
    for (int i = 0; i < values.length; i++) {
      Object obj = values[i];
      if ((obj instanceof String)) {
        size += sizeOfString((String)obj);
      }
      else if ((obj instanceof Long))
        size += sizeOfLong() + sizeOfObject();
      else {
        size += ((Cacheable)obj).getCachedSize();
      }
    }
    return size;
  }
}