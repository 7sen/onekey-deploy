package com.shensen.onekey.deploy;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.RuntimeUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.ssh.JschUtil;
import cn.hutool.extra.ssh.Sftp;
import com.jcraft.jsch.Session;
import com.shensen.onekey.properties.OneKeyProperties;
import com.shensen.onekey.properties.OneKeyRemoteProperties;
import com.shensen.onekey.properties.OneKeySshProperties;
import java.io.File;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * <p>
 * 一键部署Runner.
 * </p>
 *
 * @author Leo
 * @date 2019-01-09 16:45
 */
@Component
@Order(1)
public class OnekeyDeployRunner implements ApplicationRunner {

    private static final Logger logger = LoggerFactory.getLogger(OnekeyDeployRunner.class);

    @Autowired
    private OneKeyProperties oneKeyProperties;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        logger.info("One Key deploy starting...");

        logger.info("========================[MVN]========================");
        String mvn = "mvn clean package -P" + oneKeyProperties.getMvnProfile();
        String[] cmd = new String[]{"cmd", "/c", mvn};
        Process process = RuntimeUtil.exec(null, new File(oneKeyProperties.getMvnPath()), cmd);
        String str = RuntimeUtil.getResult(process);

        // SSH连接到远程主机
        logger.info("========================[SSH]========================");
        OneKeySshProperties ssh = oneKeyProperties.getSsh();
        Session session = JschUtil.getSession(ssh.getHost(), ssh.getPort(), ssh.getUser(), ssh.getPassword());

        logger.info("=======================[BACKUP]======================");
        // 备份服务jar、并删除已有备份.
        OneKeyRemoteProperties remote = oneKeyProperties.getRemote();
        cmd = new String[]{"cd " + remote.getAppPath(), "rm -rf " + remote.getBackupName() + "-*.tar.gz",
                "tar -czvf " + remote.getBackupName() + "-" + DateUtil.today() + ".tar.gz " + remote.getAppName()
                        + ".jar", "rm -rf " + remote.getAppName() + ".jar"};
        str = JschUtil.exec(session, StrUtil.join("&&", cmd), null);

        // 通过Sftp上传jar包到服务器
        logger.info("========================[SFTP]========================");
        Sftp sftp = JschUtil.createSftp(session);
        sftp.put(oneKeyProperties.getLocal().getFile(), oneKeyProperties.getRemote().getAppPath());

        cmd = new String[]{"cd " + remote.getAppPath(), "./" + remote.getScript() + " restart"};
        str = JschUtil.exec(session, StrUtil.join("&&", cmd), null);
        sftp.close();
        logger.info(str);
        logger.info("One Key deploy end...");
    }
}
