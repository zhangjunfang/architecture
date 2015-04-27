/*
 * Copyright 2006-2007 the original author or authors.
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

package com.transilink.framework.core.utils.dataUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import net.sf.ezmorph.MorphException;
import net.sf.ezmorph.object.AbstractObjectMorpher;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 *
 * @author ocean(zhangjufang0505@163.com)
 *
 */
public class DateMorpherEx extends AbstractObjectMorpher {

	private Date defaultValue;
	private String[] formats;
	private boolean lenient;
	private Locale locale;

	public DateMorpherEx(String[] formats) {
		this(formats, Locale.getDefault(), false);
	}

	public DateMorpherEx(String[] formats, boolean lenient) {
		this(formats, Locale.getDefault(), lenient);
	}

	public DateMorpherEx(String[] formats, Date defaultValue) {
		this(formats, defaultValue, Locale.getDefault(), false);
	}

	public DateMorpherEx(String[] formats, Date defaultValue, Locale locale,
			boolean lenient) {
		super(true);
		if ((formats == null) || (formats.length == 0)) {
			throw new MorphException("invalid array of formats");
		}

		this.formats = formats;

		if (locale == null)
			this.locale = Locale.getDefault();
		else {
			this.locale = locale;
		}

		this.lenient = lenient;
		setDefaultValue(defaultValue);
	}

	public DateMorpherEx(String[] formats, Locale locale) {
		this(formats, locale, false);
	}

	public DateMorpherEx(String[] formats, Locale locale, boolean lenient) {
		if ((formats == null) || (formats.length == 0)) {
			throw new MorphException("invalid array of formats");
		}

		this.formats = formats;

		if (locale == null)
			this.locale = Locale.getDefault();
		else {
			this.locale = locale;
		}

		this.lenient = lenient;
	}

	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}

		if (!(obj instanceof DateMorpherEx)) {
			return false;
		}

		DateMorpherEx other = (DateMorpherEx) obj;
		EqualsBuilder builder = new EqualsBuilder();
		builder.append(this.formats, other.formats);
		builder.append(this.locale, other.locale);
		builder.append(this.lenient, other.lenient);
		if ((super.isUseDefault()) && (other.isUseDefault())) {
			builder.append(getDefaultValue(), other.getDefaultValue());
			return builder.isEquals();
		}
		if ((!super.isUseDefault()) && (!other.isUseDefault())) {
			return builder.isEquals();
		}
		return false;
	}

	public Date getDefaultValue() {
		if (this.defaultValue != null)
			return (Date) this.defaultValue.clone();
		else
			return this.defaultValue;
	}

	public int hashCode() {
		HashCodeBuilder builder = new HashCodeBuilder();
		builder.append(this.formats);
		builder.append(this.locale);
		builder.append(this.lenient);
		if (super.isUseDefault()) {
			builder.append(getDefaultValue());
		}
		return builder.toHashCode();
	}

	public Object morph(Object value) {
		if (value == null) {
			return null;
		}

		if (Date.class.isAssignableFrom(value.getClass())) {
			return (Date) value;
		}

		if (!supports(value.getClass())) {
			throw new MorphException(value.getClass() + " is not supported");
		}

		String strValue = (String) value;
		SimpleDateFormat dateParser = null;

		for (int i = 0; i < this.formats.length; ++i) {
			if (dateParser == null)
				dateParser = new SimpleDateFormat(this.formats[i], this.locale);
			else {
				dateParser.applyPattern(this.formats[i]);
			}
			dateParser.setLenient(this.lenient);
			try {
				return dateParser.parse(strValue.toLowerCase());
			} catch (ParseException localParseException) {
			}

		}

		if (super.isUseDefault()) {
			return this.defaultValue;
		}
		throw new MorphException("Unable to parse the date " + value);
	}

	@SuppressWarnings("rawtypes")
	public Class morphsTo() {
		return Date.class;
	}

	public void setDefaultValue(Date defaultValue) {
		if (defaultValue != null)
			this.defaultValue = ((Date) defaultValue.clone());
		else
			this.defaultValue = null;
	}

	@SuppressWarnings("rawtypes")
	public boolean supports(Class clazz) {
		return String.class.isAssignableFrom(clazz);
	}
}