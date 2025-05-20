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

package org.springframework.ai.mcp.server.autoconfigure;

import org.springframework.boot.autoconfigure.condition.AllNestedConditions;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;

/**
 * This class defines a condition met when the MCP server is enabled and the STDIO
 * Transport is disabled.
 *
 * @since 1.0.0
 * @author YunKui Lu
 */

// 组合条件：由于 McpServerStdioDisabledCondition 继承自 AllNestedConditions，
// 这意味着它将所有嵌套的条件进行逻辑与操作（AND）。也就是说，只有当两个条件都满足时，整个条件才会被视为满足。
public class McpServerStdioDisabledCondition extends AllNestedConditions {

	public McpServerStdioDisabledCondition() {
		// 这表示在解析配置阶段时会应用这些条件。
		super(ConfigurationPhase.PARSE_CONFIGURATION);
	}

	// 内部静态类,McpServerEnabledCondition 类使用了 @ConditionalOnProperty 注解，
	// 指定了前缀为 McpServerProperties.CONFIG_PREFIX 和属性名为 "enabled" 的配置项。
	// 如果该配置项的值为 "true" 或者没有指定（即 matchIfMissing = true），则满足此条件。
	@ConditionalOnProperty(prefix = McpServerProperties.CONFIG_PREFIX, name = "enabled", havingValue = "true",
			matchIfMissing = true)
	static class McpServerEnabledCondition {

	}

	// StdioDisabledCondition 类同样使用了 @ConditionalOnProperty 注解，
	// 但这次检查的是前缀为 McpServerProperties.CONFIG_PREFIX 且名称为 "stdio" 的配置项。
	// 只有当该项的值为 "false" 或未指定时，才满足此条件。
	@ConditionalOnProperty(prefix = McpServerProperties.CONFIG_PREFIX, name = "stdio", havingValue = "false",
			matchIfMissing = true)
	static class StdioDisabledCondition {

	}

}
