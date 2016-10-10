package com.github.hexocraftapi.checkargs.processor;/*
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

import com.google.testing.compile.JavaFileObjects;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static com.google.common.truth.Truth.assertAbout;
import static com.google.testing.compile.JavaSourceSubjectFactory.javaSource;

@RunWith(JUnit4.class)
public class NoArgsTest
{
	@Test
	public void PublicNoArgsConstructor()
	{
		assertAbout(javaSource())
		.that(JavaFileObjects.forResource("NoArgs/PublicNoArgsConstructor.java"))
		.processedWith(new CheckArgsProcessor())
		.compilesWithoutError();
	}

	@Test
	public void NoArgsConstructor()
	{
		assertAbout(javaSource())
		.that(JavaFileObjects.forResource("NoArgs/NoArgsConstructor.java"))
		.processedWith(new CheckArgsProcessor())
		.compilesWithoutError();
	}

	@Test
	public void SeveralConstructors()
	{
		assertAbout(javaSource())
		.that(JavaFileObjects.forResource("NoArgs/SeveralConstructors.java"))
		.processedWith(new CheckArgsProcessor())
		.compilesWithoutError();
	}

	@Test
	public void ProtectedNoArgsConstructor()
	{
		/*assertAbout(javaSource())
		.that(JavaFileObjects.forResource("NoArgs/ProtectedNoArgsConstructor.java"))
		.processedWith(new CheckArgsProcessor())
		.failsToCompile();*/
	}

	@Test
	public void LongConstructor()
	{
		/*assertAbout(javaSource())
		.that(JavaFileObjects.forResource("NoArgs/LongConstructor.java"))
		.processedWith(new CheckArgsProcessor())
		.failsToCompile();*/
	}
}


