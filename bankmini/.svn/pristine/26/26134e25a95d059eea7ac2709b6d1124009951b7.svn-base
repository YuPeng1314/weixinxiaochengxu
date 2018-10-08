package com.huayu.core.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;

import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.Session;
import ch.ethz.ssh2.StreamGobbler;

/**
 * @author luozehua
 *
 * @date 2016年9月8日 下午3:40:29
 */
public class RemoteShellUtils {

    private static final Logger logger = Logger.getLogger(RemoteShellUtils.class);

    private Connection conn;

    /**
     * 远程服务器的IP
     */
    private String ipAddr;

    private String charset = Charset.defaultCharset().toString();
    /**
     * 登录用户名
     */
    private String userName;
    /**
     * 登录密码
     */
    private String password;

    private RemoteShellUtils(String ipAddr, String userName, String password, String charset) {
        this.ipAddr = ipAddr;
        this.userName = userName;
        this.password = password;
        if (charset != null) {
            this.charset = charset;
        }
    }

    public static RemoteShellUtils getInstance(String ipAddr, String userName, String password, String charset) {
        return new RemoteShellUtils(ipAddr, userName, password, charset);
    }
    
    /**
     * 获得链接
     */
    private boolean login() throws IOException {
        conn = new Connection(ipAddr);
        conn.connect(); // 连接
        return conn.authenticateWithPassword(userName, password); // 认证
    }
    

    /**
     * 执行命令
     */
    public String exec(String... cmds) {
        InputStream in = null;
        String result = "";
        BufferedReader stdoutReader = null;
        BufferedReader stderrReader = null;
        try {
            if (this.login()) {
                Session session = conn.openSession(); // 打开一个会话
                StringBuffer sb = new StringBuffer();
                for(String tmpStr:cmds) {
                	sb.append(tmpStr).append(" ");
                }
                                
                session.execCommand(sb.toString().trim(), this.charset);
                in = session.getStdout();
                result = this.processStdout(in, this.charset);                  

                InputStream stdout = new StreamGobbler(session.getStdout());
                InputStream stderr = new StreamGobbler(session.getStderr());
                stdoutReader = new BufferedReader(new InputStreamReader(stdout));
                stderrReader = new BufferedReader(new InputStreamReader(stderr));

                logger.debug("Here is the output from stdout:");
                while (true) {
                    String line = stdoutReader.readLine();
                    if (line == null)
                        break;
                    logger.debug(line);
                }

                logger.debug("Here is the output from stderr:");
                while (true) {
                    String line = stderrReader.readLine();
                    if (line == null)
                        break;
                    logger.error(line);
                }
                session.close();
            }
        } catch (IOException e1) {
            logger.error(e1);
        } finally {
            if (conn != null) {
                conn.close();
            }
            IOUtils.closeQuietly(stdoutReader);
            IOUtils.closeQuietly(stderrReader);
        }
        return result;
    }

    /**
     * 获取执行后的结果
     */
    private String processStdout(InputStream in, String charset) {

        StringBuffer buffer = new StringBuffer();
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        try {
            while (true) {
                String line = br.readLine();
                if (null == line) {
                    break;
                }
                buffer.append(line);
                buffer.append(System.getProperty("line.separator"));// 换行
            }
        } catch (IOException e) {
            logger.error(e);
        }
        return buffer.toString();
    }

   
	
}
