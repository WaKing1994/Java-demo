package com.example.demo.common.util;

import org.apache.commons.lang.reflect.FieldUtils;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;


/**
 * 
 * 
 * 描述:集合工具类
 * 
 * @author huangpengpeng
 * @version 1.0
 * @since 2013年12月14日 下午1:04:10
 */
public class ArrayUtils extends org.apache.commons.lang.ArrayUtils {
	
	 /**
     * An empty immutable <code>BigDecimal</code> array.
     */
    public static final BigDecimal[] EMPTY_BIG_DECIMAL_ARRAY = new BigDecimal[0];
    
	/**
	 * 将数组转化成Set 去重
	 * 
	 * @param array
	 * @return
	 */
	public static <T> Set<T> toSet(T[] array) {
		Set<T> set = new HashSet<T>();
		if (array == null) {
			return set;
		}
		for (T t : array) {
			set.add(t);
		}
		return set;
	}
    
	/**
	 * 往数组里面增加项，增加项不能为空
	 * @param array
	 * @param element
	 * @return
	 */
	@Deprecated
	public static Object [] addNotNull(Object [] array,Object element){
		if(element==null){
			return array;
		}
		return add(array, element);
	}
	
	/**
	 * 讲数组替换成字符串
	 * 
	 * @param values
	 * @return
	 */
	public static String toNoWrapString(Object array, String stringIfNull) {
		String wrap = toString(array, stringIfNull);
		if ("".equals(wrap)) {
			return wrap;
		}
		return wrap.substring(1, wrap.length() - 1);
	}
	
	
	/**
	 * 讲数组替换成字符串
	 * 
	 * @param values
	 * @return
	 */
	public static String toNoWrapString(Object array) {
		String wrap = toString(array);
		if ("".equals(wrap)) {
			return wrap;
		}
		return wrap.substring(1, wrap.length() - 1);
	}
	
	/**
	 * 讲数组替换成字符串
	 * 
	 * @param values
	 * @return
	 */
	public static String toNoWrapString(String[] array) {
		String wrap = toString(array, "");
		if ("".equals(wrap)) {
			return wrap;
		}
		wrap = wrap.substring(1, wrap.length() - 1);
		wrap = "\"" + wrap + "\"";
		return wrap.replace(",", "\",\"");
	}
	
	public static String toSQLNoWrapString(String[] array) {
		String wrap = toString(array, "");
		if ("".equals(wrap)) {
			return wrap;
		}
		wrap = wrap.substring(1, wrap.length() - 1);
		wrap = "\'" + wrap + "\'";
		return wrap.replace(",", "\',\'");
	}
	
	/**
	 * 字符串分割转化为long数组
	 * @param array
	 * @return
	 */
	public static Long[] toNoWrapForLong(String warp, String separatorChars) {
		Long[] result = org.apache.commons.lang.ArrayUtils.EMPTY_LONG_OBJECT_ARRAY;
		if (org.apache.commons.lang.StringUtils.isBlank(warp)) {
			return result;
		}
		String[] values = warp.split(separatorChars == null ? ","
				: separatorChars);
		for (String string : values) {
			result = (Long[]) ArrayUtils.add(result, Long.valueOf(string));
		}
		return result;
	}
	
	/**
	 * 讲数组字段转化为字符串
	 * 
	 * @param objs
	 * @param fieldName
	 * @param separatorChars
	 * @return
	 */
	public static String toArrayWrapForString(Object[] objs, String fieldName,
			String separatorChars) {
		StringBuffer stringBuffer = new StringBuffer();
		if (isEmpty(objs)) {
			return stringBuffer.toString();
		}
		try {
			for (int i = 0; i < objs.length; i++) {
				Object obj = objs[i];
				stringBuffer.append(FieldUtils.readDeclaredField(obj,
						fieldName, true));
				if (i < (objs.length - 1)) {
					stringBuffer.append(separatorChars == null ? ","
							: separatorChars);
				}
			}
		} catch (IllegalAccessException e) {
			throw new IllegalStateException(e);
		}
		return stringBuffer.toString();
	}

	/**
	 * 验证数组返回
	 * 
	 * @param objs
	 * @param arg0
	 * @param arg1
	 * @return
	 */
	public static boolean isRange(Object[] objs, int arg0, int arg1) {
		if (isEmpty(objs)) {
			return false;
		}
		int size = objs.length;
		return size >= arg0 && size <= arg1;
	}

	/**
	 * 判断是否包含空数组
	 * 
	 * @param objs
	 * @return
	 */
	public static boolean containsNull(Object[] objs) {
		for (Object obj : objs) {
			if (obj == null) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 根据element value 删除对应位置的原始
	 * 
	 * @param t
	 * @param t2
	 *            当 t2 当中存在element 则删除t2 当中的 element 同时删除对应位置t的value
	 * @param element
	 * @return
	 */
	public static Long[][] removeElement(Long[] t, Long[] t2, Long element) {
		if (isEmpty(t) || isEmpty(t2)) {
			return new Long[][] { new Long[] {}, new Long[] {} };
		}
		int len = t2.length;
		Long[] arg0 = org.apache.commons.lang.ArrayUtils.EMPTY_LONG_OBJECT_ARRAY;
		Long[] arg1 = org.apache.commons.lang.ArrayUtils.EMPTY_LONG_OBJECT_ARRAY;
		for (int i = 0; i < len; i++) {
			if (element.equals(t2[i])) {
				continue;
			}
			arg0 = (Long[]) add(arg0, t[i]);
			arg1 = (Long[]) add(arg1, t2[i]);
		}
		return new Long[][] { arg0, arg1 };
	}

	public static void main(String[] args) {
		System.out.println(ArrayUtils.toNoWrapString(new Long[]{1L,2L}));
	}
}
