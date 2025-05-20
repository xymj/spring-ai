/*
 * Copyright 2025-2025 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.ai.mcp.aot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import io.modelcontextprotocol.spec.McpSchema;

import org.springframework.aot.hint.MemberCategory;
import org.springframework.aot.hint.RuntimeHints;
import org.springframework.aot.hint.RuntimeHintsRegistrar;
import org.springframework.aot.hint.TypeReference;
import org.springframework.lang.Nullable;

/**
 * Runtime hints registrar for Model Context Protocol (MCP) schema classes.
 * <p>
 * This class provides GraalVM native image hints for MCP schema classes to ensure proper
 * reflection access in native images. It:
 * <ul>
 * <li>Registers all nested classes of {@link McpSchema} for reflection</li>
 * <li>Enables all member categories (fields, methods, etc.) for registered types</li>
 * <li>Ensures proper serialization/deserialization in native images</li>
 * </ul>
 *
 * @author Josh Long
 * @since 1.0.0
 * @see RuntimeHintsRegistrar
 * @see McpSchema
 *
 * 在 Spring 框架中，META-INF/spring 目录下的 aot.factories 文件主要用于 Spring AOT（Ahead-of-Time）处理。Spring AOT 是 Spring 的一项优化技术，旨在通过在构建时提前处理一些 Spring 组件和配置来提高运行时性能，特别是针对原生镜像的构建（如使用 GraalVM）。
 * aot.factories 文件的作用
 * 	aot.factories 文件的作用是定义在应用程序的构建阶段应该提前处理的特定 Spring 组件或配置类。这些信息用于帮助在构建时生成必要的元数据和代码，以便在运行时加快启动速度或对原生镜像进行优化。
 * 配合配置类路径使用
 * 	aot.factories 文件的格式类似于通常的 spring.factories 文件，使用键值对的形式来指定特定的配置类。在 aot.factories 中，键表示要处理的类型，值表示要加载的配置类。
 * 示例配置
 * 	假设您有一个自定义的 Spring 配置类 com.example.MyConfiguration，您希望在 AOT 编译时使用它：
 * 	创建 aot.factories 文件：
 * 	在 src/main/resources/META-INF/spring/aot.factories 中添加以下内容：
 * properties
 * Copy
 * org.springframework.context.ApplicationContextInitializer=com.example.MyConfiguration
 * 定义配置类：
 * java
 * Copy
 * package com.example;
 *
 * import org.springframework.context.annotation.Bean;
 * import org.springframework.context.annotation.Configuration;
 *
 * @Configuration
 * public class MyConfiguration {
 *
 *     @Bean
 *     public MyService myService() {
 *         return new MyService();
 *     }
 * }
 * 使用场景：
 * 	AOT 处理：当使用 Spring AOT 编译工具（如 Spring Native）时，aot.factories 中定义的类会在构建时被加载和处理。这有助于在构建期间生成优化的元数据或代码。
 * 	原生镜像优化：对于需要构建成原生镜像的应用，提前处理的配置可以减少运行时反射的使用，从而提高性能和缩短启动时间。
 * 使用时机
 * 	aot.factories 文件的使用时机主要是在以下两种情况下：
 * 	使用 Spring Native 构建原生镜像：
 * 		当您希望将 Spring 应用程序编译为原生可执行文件时，使用 Spring AOT 编译工具将提前处理 aot.factories 中指定的类，以优化性能和兼容性。
 * 	优化应用启动时间：
 * 	在需要优化应用启动时间的场景中，通过 AOT 预处理可以减少运行时的配置处理和反射使用。
 * 注意事项
 * 	AOT 处理的局限性：并非所有 Spring 组件都可以通过 AOT 处理。开发者需要根据 AOT 处理的特性设计和选择合适的组件。
 * 	依赖工具的支持：使用 AOT 处理需要使用支持该功能的工具，比如 Spring Native。
 * 	测试和验证：由于 AOT 处理可能会影响应用的行为，确保在各种环境中对应用进行充分的测试和验证。
 * 通过合理使用 aot.factories 文件，可以帮助开发者在特定场景下优化 Spring 应用的性能，特别是在构建原生镜像时。
 *
 *
 *----------------------------
 * 在 Spring AOT 中，aot.factories 文件主要用于配置在构建或者运行时需要进行特殊处理的组件类。虽然 aot.factories 中的键值对格式类似于 spring.factories，但它专注于为 AOT 编译提供更多的优化信息或指令。下面是一些常用的类型及其作用说明：
 * 常见的键类型
 * org.springframework.context.ApplicationContextInitializer:
 * 	作用：用于定义需要在应用启动时初始化的 ApplicationContext。通过在 AOT 编译时提前处理这些初始化类，可以减少运行时初始化的开销。
 * 	使用场景：通常在需要自定义应用上下文初始化逻辑时使用。
 *
 * org.springframework.aot.hint.RuntimeHintsRegistrar:
 * 	核心目的：用于注册运行时提示（hints），这些提示帮助 AOT 编译器（例如 GraalVM Native Image）在构建原生镜像时提前了解需要进行反射、资源加载或者代理的配置。
 * 	使用场景：在需要为原生镜像提供额外的运行时信息以避免反射配置和资源访问问题时使用。
 * 示例：
 * java
 * Copy
 * public class MyRuntimeHints implements RuntimeHintsRegistrar {
 *     @Override
 *     public void registerHints(RuntimeHints hints, ClassLoader classLoader) {
 *         hints.reflection().registerType(MyService.class, hint -> hint.withMembers(MemberCategory.INVOKE_DECLARED_METHODS));
 *     }
 * }
 * 在上面的示例中，MyService 类的方法会被反射使用，这通过 RuntimeHintsRegistrar 提供给 AOT 编译器。
 *
 * org.springframework.aot.ApplicationContextFactory:
 * 	作用：用于定义自定义的 ApplicationContext 工厂类，通过 AOT 编译器来创建上下文实例。
 * 	使用场景：当需要使用特定的上下文工厂逻辑时使用，可以用于替换或扩展默认的上下文创建方式。
 *
 * org.springframework.beans.factory.AotBeanFactoryInitialization:
 * 	作用：用于提前初始化 BeanFactory。
 * 	使用场景：在需要对 BeanFactory 进行 AOT 优化时使用，通常用于减少启动时的 Bean 初始化开销。
 *
 * RuntimeHintsRegistrar 的核心目的
 * 	反射优化：通过指定哪些类或方法需要在运行时通过反射访问，帮助 AOT 编译器在构建阶段生成必要的元数据，避免运行时反射错误。
 * 	资源管理：定义哪些资源文件需要在运行时被加载，这对于原生镜像中资源的管理非常重要。
 * 	代理配置：指定需要被动态代理的接口或类，确保在原生镜像中代理能够正确生成和使用。
 * 适用场景
 * 	构建原生镜像：在使用 Spring Native 和 GraalVM 构建原生镜像时，aot.factories 配置是不可或缺的一部分。
 * 	启动性能优化：通过提前处理和配置，减少应用启动时的动态处理，提升性能。
 * 通过正确配置 aot.factories，开发者可以有效地优化 Spring 应用在 AOT 编译和原生镜像环境下的性能表现。
 */
@SuppressWarnings("unused")
public class McpHints implements RuntimeHintsRegistrar {

	/**
	 * Registers runtime hints for MCP schema classes.
	 * <p>
	 * This method:
	 * <ol>
	 * <li>Discovers all nested classes within {@link McpSchema}</li>
	 * <li>Registers each discovered class for reflection access</li>
	 * <li>Enables all member categories for complete reflection support</li>
	 * </ol>
	 * @param hints the hints instance to register hints with
	 * @param classLoader the classloader to use (may be null)
	 */
	@Override
	public void registerHints(RuntimeHints hints, @Nullable ClassLoader classLoader) {
		var mcs = MemberCategory.values();

		for (var tr : innerClasses(McpSchema.class)) {
			hints.reflection().registerType(tr, mcs);
		}
	}

	/**
	 * Discovers all inner classes of a given class.
	 * <p>
	 * This method recursively finds all nested classes (both declared and inherited) of
	 * the provided class and converts them to type references.
	 * @param clazz the class to find inner classes for
	 * @return a set of type references for all discovered inner classes
	 */
	private Set<TypeReference> innerClasses(Class<?> clazz) {
		var indent = new HashSet<String>();
		this.findNestedClasses(clazz, indent);
		return indent.stream().map(TypeReference::of).collect(Collectors.toSet());
	}

	/**
	 * Recursively finds all nested classes of a given class.
	 * <p>
	 * This method:
	 * <ol>
	 * <li>Collects both declared and inherited nested classes</li>
	 * <li>Recursively processes each nested class</li>
	 * <li>Adds the class names to the provided set</li>
	 * </ol>
	 * @param clazz the class to find nested classes for
	 * @param indent the set to collect class names in
	 */
	private void findNestedClasses(Class<?> clazz, Set<String> indent) {
		var classes = new ArrayList<Class<?>>();
		classes.addAll(Arrays.asList(clazz.getDeclaredClasses()));
		classes.addAll(Arrays.asList(clazz.getClasses()));
		for (var nestedClass : classes) {
			this.findNestedClasses(nestedClass, indent);
		}
		indent.addAll(classes.stream().map(Class::getName).toList());
	}

}
