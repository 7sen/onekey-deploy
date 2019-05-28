package com.shensen.onekey.properties;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * <p>
 * 一键部署远程配置.
 * </p>
 *
 * @author Leo
 * @date 2019-01-10 12:24
 */
@Getter
@Setter
@ToString
public class OneKeyRemoteProperties {

    private String appPath;
    private String appName;
    /** 脚本名称 */
    private String script;
    /** 备份名称 */
    private String backupName;
}
