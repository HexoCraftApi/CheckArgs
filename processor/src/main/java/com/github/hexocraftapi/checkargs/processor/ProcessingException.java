package com.github.hexocraftapi.checkargs.processor;

/*
 * Copyright 2016 hexosse
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import javax.lang.model.element.Element;

/**
 * @author <b>Hexosse</b> (<a href="https://github.com/hexosse">on GitHub</a>))
 */
public class ProcessingException extends Exception {

	private static final long serialVersionUID = -7962563253802631284L;

	private Element element;

	public ProcessingException(Element element, String msg, Object... args)
	{
		super(String.format(msg, args));
		this.element = element;
	}

	public Element getElement() {
		return element;
	}
}