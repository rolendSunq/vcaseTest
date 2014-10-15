/*
 * Copyright 2002-2009 the original author or authors.
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

package com.airensoft.skovp.sample.utils.common;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.util.FileCopyUtils;

/**
 * Implementation of
 * {@link org.springframework.http.converter.HttpMessageConverter} that can read
 * and write strings.
 * 
 * 
 * @author Arjen Poutsma
 * @author Matthijs Bierman
 * @since 3.0
 */
public class ConfigurableStringHttpMessageConverter extends
		AbstractHttpMessageConverter<String>
{

	private final List<Charset>		availableCharsets;
	// private static final Charset DEFAULT_CHARSET =
	// Charset.forName("ISO-8859-1");
	private static final Charset	DEFAULT_CHARSET	= Charset.forName("UTF-8");

	public ConfigurableStringHttpMessageConverter()
	{
		this(DEFAULT_CHARSET);
	}

	public ConfigurableStringHttpMessageConverter(Charset defaultCharset)
	{
		super(new MediaType("text", "plain", defaultCharset), MediaType.ALL);
		this.availableCharsets = new ArrayList<Charset>(Charset
				.availableCharsets().values());
	}

	@Override
	protected String readInternal(Class<? extends String> clazz,
			HttpInputMessage inputMessage) throws IOException,
			HttpMessageNotReadableException
	{
		MediaType contentType = inputMessage.getHeaders().getContentType();
		Charset charset = contentType.getCharSet() != null ? contentType
				.getCharSet() : DEFAULT_CHARSET;
		return FileCopyUtils.copyToString(new InputStreamReader(inputMessage
				.getBody(), charset));
	}

	@Override
	protected boolean supports(Class<?> clazz)
	{
		return String.class.equals(clazz);
	}

	@Override
	protected void writeInternal(String s, HttpOutputMessage outputMessage)
			throws IOException, HttpMessageNotWritableException
	{
		outputMessage.getHeaders().setAcceptCharset(getAcceptedCharsets());
		MediaType contentType = outputMessage.getHeaders().getContentType();
		Charset charset = contentType.getCharSet() != null ? contentType
				.getCharSet() : DEFAULT_CHARSET;
		FileCopyUtils.copy(s, new OutputStreamWriter(outputMessage.getBody(),
				charset));
	}

	/**
	 * Return the list of supported {@link Charset}.
	 * 
	 * <p>
	 * By default, returns {@link Charset#availableCharsets()}. Can be
	 * overridden in subclasses.
	 * 
	 * @return the list of accepted charsets
	 */
	protected List<Charset> getAcceptedCharsets()
	{
		return this.availableCharsets;
	}

	@Override
	protected Long getContentLength(String s, MediaType contentType)
	{
		if (contentType != null && contentType.getCharSet() != null)
		{
			Charset charset = contentType.getCharSet();
			try
			{
				return (long) s.getBytes(charset.name()).length;
			} catch (UnsupportedEncodingException ex)
			{
				// should not occur
				throw new InternalError(ex.getMessage());
			}
		} else
		{
			return null;
		}
	}
}