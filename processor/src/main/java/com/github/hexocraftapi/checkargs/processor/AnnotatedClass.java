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

import com.github.hexocraftapi.checkargs.annotation.CheckArgs;
import org.apache.commons.lang3.ClassUtils;

import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author <b>Hexosse</b> (<a href="https://github.com/hexosse">on GitHub</a>))
 */
public class AnnotatedClass{

	private TypeElement  annotatedClassElement;
	private int          count;
	private List<String> qualifiedClassName = new ArrayList<>();
	private List<String> simpleClassName    = new ArrayList<>();

	/**
	 * @param classElement TypeElement
	 * @param elementUtils TypeElement
	 * @throws ClassNotFoundException ClassNotFoundException
	 */
	public AnnotatedClass(TypeElement classElement, Elements elementUtils) throws ClassNotFoundException
	{
		this.annotatedClassElement = classElement;

		CheckArgs annotation = classElement.getAnnotation(CheckArgs.class);
		List<? extends AnnotationMirror> annotationMirrors = elementUtils.getAllAnnotationMirrors(classElement);

		for(AnnotationMirror annotationMirror : annotationMirrors)
		{
			Map<? extends ExecutableElement, ? extends AnnotationValue> elementValues = annotationMirror.getElementValues();

			for(Map.Entry<? extends ExecutableElement, ? extends AnnotationValue> entry : elementValues.entrySet())
			{
				String key = entry.getKey().getSimpleName().toString();
				Object value = entry.getValue().getValue();

				switch (key) {
					case "count":
						this.count = (int)value;
						break;
					case "types":
						List<? extends AnnotationValue> typeMirrors = (List<? extends AnnotationValue>) value;
						for(AnnotationValue annotationValue : typeMirrors)
						{
							String clazz = annotationValue.getValue().toString();
							this.qualifiedClassName.add(clazz);
							this.simpleClassName.add(clazz.substring(clazz.lastIndexOf(".")+1));
						}
						break;
				}
			}
		}

		if(this.qualifiedClassName.size() > 0 && this.count != this.qualifiedClassName.size())
			this.count = this.qualifiedClassName.size();
	}

	public int getCount()
	{
		return count;
	}

	public List<String> getQualifiedClassName() {
		return qualifiedClassName;
	}

	public List<String> getSimpleClassName() {
		return simpleClassName;
	}

	/**
	 * The original element that was annotated with @Factory
	 *
	 * @return TypeElement
	 */
	public TypeElement getTypeElement() {
		return annotatedClassElement;
	}
}