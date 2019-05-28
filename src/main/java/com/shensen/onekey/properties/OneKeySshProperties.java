package com.shensen.onekey.properties;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * <p>
 * SSH配置.
 * </p>
 *
 * @author Leo
 * @date 2019-01-10 12:24
 */
@Getter
@Setter
@ToString
public class OneKeySshProperties {

    private String host;
    private int port;
    private String user;
    private String password;

}
