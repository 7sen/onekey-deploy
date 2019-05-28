package com.shensen.onekey.config;

import com.shensen.onekey.properties.OneKeyProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * <p>
 * 开启一键部署配置.
 * </p>
 *
 * @author Leo
 * @date 2019-01-09 16:38
 */
@Configuration
@EnableConfigurationProperties(OneKeyProperties.class)
public class OneKeyConfig {

}
