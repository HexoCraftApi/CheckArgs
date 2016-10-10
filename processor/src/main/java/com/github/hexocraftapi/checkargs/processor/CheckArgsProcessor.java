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
import com.google.auto.service.AutoService;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.tools.Diagnostic;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * @author <b>Hexosse</b> (<a href="https://github.com/hexosse">on GitHub</a>))
 */

@AutoService(Processor.class)
public class CheckArgsProcessor extends AbstractProcessor
{
	protected Types    typeUtils;
	protected Elements elementUtils;
	protected Filer    filer;
	protected Messager messager;

	/**
	 * Initializes the processor with the processing environment by
	 * setting the {@code processingEnv} field to the value of the
	 * {@code processingEnv} argument.  An {@code
	 * IllegalStateException} will be thrown if this method is called
	 * more than once on the same object.
	 *
	 * @param processingEnv environment to access facilities the tool framework
	 * provides to the processor
	 *
	 * @throws IllegalStateException if this method is called more than once.
	 */
	@Override
	public synchronized void init(ProcessingEnvironment processingEnv)
	{
		super.init(processingEnv);
		typeUtils = processingEnv.getTypeUtils();
		elementUtils = processingEnv.getElementUtils();
		filer = processingEnv.getFiler();
		messager = processingEnv.getMessager();
	}

	@Override
	public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv)
	{
		try
		{
			// Scan classes
			for (TypeElement type : annotations)
			{
				for(Element annotatedElement : roundEnv.getElementsAnnotatedWith(type))
				{
					// Check if a class has been annotated with @CheckArgs
					if(annotatedElement.getKind() != ElementKind.CLASS)
						throw new ProcessingException(annotatedElement, "Only classes can be annotated with @%s", CheckArgs.class.getSimpleName());

					// We can cast it, because we know that it is of ElementKind.CLASS
					TypeElement typeElement = (TypeElement) annotatedElement;

					//
					AnnotatedClass annotatedClass = new AnnotatedClass(typeElement, elementUtils);

					//
					if(!checkValidClass(annotatedClass)) {
						processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, "Class " + typeElement + " is not compliant with @CheckArgs parameters");
					}
				}
			}
		}
		catch(ProcessingException e)
		{
			printError(e.getElement(), e.getMessage());
		}
		catch(Exception e)
		{
			printError(null, e.getMessage());
		}

		return true;
	}

	@Override
	public Set<String> getSupportedAnnotationTypes()
	{
		Set<String> annotations = new LinkedHashSet<String>();
		annotations.add(CheckArgs.class.getCanonicalName());
		return annotations;	}

	@Override
	public SourceVersion getSupportedSourceVersion()
	{
		return SourceVersion.latestSupported();
	}

	private boolean checkValidClass(AnnotatedClass aClass) throws ProcessingException, NullPointerException
	{
		// Cast to TypeElement, has more type specific methods
		TypeElement classElement = aClass.getTypeElement();

		/* Not useful
		if (!classElement.getModifiers().contains(Modifier.PUBLIC)) {
			throw new ProcessingException(classElement, "The class %s is not public.",
			classElement.getQualifiedName().toString());
		}*/

		// Check each constructor of the class
		for(Element aConstructor : classElement.getEnclosedElements())
		{
			if(aConstructor.getKind() == ElementKind.CONSTRUCTOR /*&& aConstructor.getModifiers().contains(Modifier.PUBLIC)*/)
			{
				TypeMirror mirror = aConstructor.asType();
				ArgTypeVisitor argTypeVisitor = new ArgTypeVisitor();
				if(mirror.accept(argTypeVisitor, aClass))
					return true;
			}
		}
		return false;
	}

	/**
	 * Prints an error message
	 *
	 * @param e The element which has caused the error. Can be null
	 * @param msg The error message
	 */
	public void printError(Element e, String msg) {
		messager.printMessage(Diagnostic.Kind.ERROR, msg, e);
	}
}
