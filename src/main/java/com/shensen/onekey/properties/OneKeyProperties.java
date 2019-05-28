package com.shensen.onekey.properties;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * <p>
 * 一键部署配置.
 * </p>
 *
 * @author Leo
 * @date 2019-01-10 12:24
 */
@Getter
@Setter
@ToString
@ConfigurationProperties(prefix = "onekey")
public class OneKeyProperties {

    private String appName;
    private String mvnPath;
    private String mvnProfile;
    /** SSH配置 */
    private OneKeySshProperties ssh = new OneKeySshProperties();
    /** 本地配置 */
    private OneKeyLocalProperties local = new OneKeyLocalProperties();
    /** 远程配置 */
    private OneKeyRemoteProperties remote = new OneKeyRemoteProperties();

}
