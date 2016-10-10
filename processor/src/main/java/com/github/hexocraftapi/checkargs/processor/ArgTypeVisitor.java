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

import javax.lang.model.type.ExecutableType;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.SimpleTypeVisitor7;

/**
 * @author <b>Hexosse</b> (<a href="https://github.com/hexosse">on GitHub</a>))
 */
public class ArgTypeVisitor extends SimpleTypeVisitor7<Boolean, AnnotatedClass>
{
	@Override
	public Boolean visitExecutable(ExecutableType t, AnnotatedClass annotatedClass)
	{
		if(annotatedClass.getCount()>0 && annotatedClass.getQualifiedClassName().size()==0) return true;
		if(t.getParameterTypes().size() != annotatedClass.getCount()) return false;
		if(t.getParameterTypes().size() != annotatedClass.getQualifiedClassName().size()) return false;

			for(int i = 0; i < t.getParameterTypes().size(); i++)
			{
				TypeMirror typeMirror = t.getParameterTypes().get(i);
				String qualifiedClassName = annotatedClass.getQualifiedClassName().get(i);
				if(!typeMirror.toString().equals(qualifiedClassName))
					return false;
			}
			return true;
	}
}
