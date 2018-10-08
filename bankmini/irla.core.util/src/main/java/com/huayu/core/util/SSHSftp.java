package com.huayu.core.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Properties;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;

/** 
* @author 作者 fzh: 
* @version 创建时间：2016年10月17日 下午16:15:10 
* jsch 类上传ssh2 Shell服务器
* 类说明 
*/

public class SSHSftp {
    private static final Logger logger = Logger.getLogger(SSHSftp.class);
//    public final static String IP = SysUploadUtils.getInstance().getValue("sync.ip.port", "");;
//    public final static String USERNAME =  SysUploadUtils.getInstance().getValue("sync.username", "");;
//    public final static String PD = SysUploadUtils.getInstance().getValue("sync.password", "");;
//    public final static String JAVAHOME = SysUploadUtils.getInstance().getValue("sync.java.home", "");

    public final static String PORT = "22";

    private static String FINAL_YYYYMMFileName = ""; // 年月文件名
    private static String FINAL_uploadPathYYYYMM = ""; // 路径名

    /**
     * 利用JSch包实现SFTP下载、上传文件
     * @param ip 主机IP
     * @param user 主机登陆用户名
     * @param psw  主机登陆密码
     * @param port 主机ssh2登陆端口，如果取默认值，传-1
     * @param syncFile 本地资源路径
     * @param cDir 上传到服务器资源路径
     */
    public static boolean sshSftp(String ip, String user, String psw, int port, String cDir, File syncFile,
            InputStream instream) throws Exception {
        Session session = null;
        Channel channel = null;
        OutputStream outstream = null;
        JSch jsch = new JSch();
        if (port <= 0) {
            //连接服务器，采用默认端口
            session = jsch.getSession(user, ip);
        } else {
            //采用指定的端口连接服务器
            session = jsch.getSession(user, ip, port);
        }

        //如果服务器连接不上，则抛出异常
        if (session == null) {
            throw new Exception("session is null");
        }

        //设置登陆主机的密码
        session.setPassword(psw);//设置密码   
        //设置第一次登陆的时候提示，可选值：(ask | yes | no)
        session.setConfig("StrictHostKeyChecking", "no");
        //设置登陆超时时间   
        session.connect(30000);

        try {
            //创建sftp通信通道
            channel = (Channel) session.openChannel("sftp");
            channel.connect(1000);
            ChannelSftp sftp = (ChannelSftp) channel;

            //进入服务器指定的文件夹
            sftp.cd(cDir);
            //以下代码实现从本地上传一个文件到服务器，如果要实现下载，对换以下流就可以了
            outstream = sftp.put(syncFile.getName());

            byte b[] = new byte[1024];
            int n;
            while ((n = instream.read(b)) != -1) {
                outstream.write(b, 0, n);
            }
            outstream.flush();
        } catch (Exception e) {
            logger.error("写文件失败", e);
            return false;
        } finally {
            IOUtils.closeQuietly(outstream);
            IOUtils.closeQuietly(instream);
            if (session != null) {
                session.disconnect();
            }
            if (channel != null) {
                channel.disconnect();
            }

        }
        return true;
    }

    /**
     * 利用JSch包实现SFTP下载、上传文件
     * @param ip 主机IP
     * @param user 主机登陆用户名
     * @param psw  主机登陆密码
     * @param port 主机ssh2登陆端口，如果取默认值，传-1
     * @param syncFile 本地资源路径
     * @param cDir 上传到服务器资源路径
     */
    public static boolean sshSftpImg(String ip, String user, String psw, int port, String cDir, File syncFilejt,
            InputStream instream) throws Exception {
        Session session = null;
        Channel channel = null;
        OutputStream outstream = null;
        JSch jsch = new JSch();
        if (port <= 0) {
            //连接服务器，采用默认端口
            session = jsch.getSession(user, ip);
        } else {
            //采用指定的端口连接服务器
            session = jsch.getSession(user, ip, port);
        }

        //如果服务器连接不上，则抛出异常
        if (session == null) {
            throw new Exception("session is null");
        }

        //设置登陆主机的密码
        session.setPassword(psw);//设置密码   
        //设置第一次登陆的时候提示，可选值：(ask | yes | no)
        session.setConfig("StrictHostKeyChecking", "no");
        //设置登陆超时时间   
        session.connect(30000);

        try {
            //创建sftp通信通道
            channel = (Channel) session.openChannel("sftp");
            channel.connect(1000);
            ChannelSftp sftp = (ChannelSftp) channel;

            //进入服务器指定的文件夹
            // sftp.mkdir("20140307");
            sftp.cd(cDir);
            //列出服务器指定的文件列表

            //以下代码实现从本地上传一个文件到服务器，如果要实现下载，对换以下流就可以了
            outstream = sftp.put(syncFilejt.getName());
            //InputStream instream = new FileInputStream(syncFile.getPath());

            byte b[] = new byte[1024];
            int n;
            while ((n = instream.read(b)) != -1) {
                outstream.write(b, 0, n);
            }
            outstream.flush();
        } catch (Exception e) {
            logger.error("写文件失败", e);
            return false;
        } finally {
            IOUtils.closeQuietly(outstream);
            IOUtils.closeQuietly(instream);
            if (session != null) {
                session.disconnect();
            }
            if (channel != null) {
                channel.disconnect();
            }
        }
        return true;
    }

    /**
     * 上传图片
     * @param session 会话
     * @param cDir 批定到服务器路径
     * @param syncFile 本地文件路径
     * @param instream 文件流
     * @return
     * @throws Exception
     */
    public static boolean sshSftpImg(Session session, String cDir, File syncFile, InputStream instream)
            throws Exception {
        Channel channel = null;
        OutputStream outstream = null;
        try {
            //创建sftp通信通道
            channel = (Channel) session.openChannel("sftp");
            channel.connect(1000);
            ChannelSftp sftp = (ChannelSftp) channel;

            //进入服务器指定的文件夹
            sftp.cd(cDir);

            //以下代码实现从本地上传一个文件到服务器，如果要实现下载，对换以下流就可以了
            outstream = sftp.put(syncFile.getName());
            byte b[] = new byte[1024];
            int n;
            while ((n = instream.read(b)) != -1) {
                outstream.write(b, 0, n);
            }
            outstream.flush();
        } finally {
            IOUtils.closeQuietly(outstream);
            IOUtils.closeQuietly(instream);
            if (channel != null) {
                channel.disconnect();
            }
        }
        return true;
    }

    /**
     * 
     * sftpSyncFile(将服务器指定文件下载到本地指定目录)
     * 
     * @param session
     * @param orgDir
     *            文件源目录(服务器文件地址)
     * @param distDir
     *            目标目标目录(本地要存放文件的地址)
     * @param fileNameList
     * @return
     * @exception
     */
    public static void sftpSyncFile(Session session, String orgDir, String distDir, List<String> fileNameList) {
        if (null != fileNameList && 0 < fileNameList.size()) {
            ChannelSftp sftp = null;
            // 如果文件夹不存在，创建文件夹
            File file = new File(distDir);
            if (!file.exists()) {
                file.mkdir();
            }
            sftp = getSftpChanel(session);
            // 打开源目录
            try {
                sftp.cd(orgDir);
                // 同步文件
                for (String fileName : fileNameList) {
                    try {
                        sftp.get(fileName, distDir);
                    } catch (SftpException e) {
                        logger.error("文件“" + fileName + "”同步失败：", e);
                    }
                }
            } catch (SftpException e) {
                logger.error("打开源目录失败：", e);
                return;
            } finally {
                sftp.disconnect();
            }
        }
    }

    /**
     * 
     * sftpSyncFile(将服务器指定文件下载到本地指定目录)
     * 
     * @param session
     * @param orgDir
     *            文件源目录(服务器文件地址)
     * @param 
     * @return
     * @exception
     */
    public static void sftpServiceFile(String host, String hostname, String password, String orgDir)
            throws SftpException {
        Session session = null;
        // 打开源目录
        ChannelSftp sftp = null;
        try {
            session = SSHSftp.getSftpSession(host, hostname, password, 22, 3);
            // 如果文件夹不存在，创建文件夹
            File file = new File(orgDir);
            if (!file.exists()) {
                file.mkdir();
            }
            //获取连接通道
            sftp = getSftpChanel(session);
            initNewFileName(orgDir, FINAL_YYYYMMFileName, sftp, session);
            //sftp.mkdir(orgDir);
            //sftp.cd(orgDir);
        } catch (Exception e) {
            logger.error(e);
        } finally {
            if (sftp != null) {
                sftp.disconnect();
            }
            if (session != null) {
                session.disconnect();
            }
        }
    }

    /**
     * 
     * @param path 当前路径
     * @param newFileName 当前月
     * @param sftp
     * @return
     */
    public static String initNewFileName(String path, String newFileName, ChannelSftp sftp, Session session) {
        try {
            String newDateYm = DateTimeUtils.simple2(Calendar.getInstance().getTime()); //当前年月份;

            //File file = new File(path); //获取当日期上一级文件夹
            //File[] files = file.listFiles(); //获取所有日期文件
            //获取所有日期文件
            String[] files = getTopNumFileName(session, path);

            //最开始第一次没有文件夹时进来创建
            if (null == files || 0 == files.length) {
                sftp.mkdir(path + newDateYm);
                //给当前月赋值
                FINAL_YYYYMMFileName = newDateYm;
                //给当前路径赋值
                FINAL_uploadPathYYYYMM = path + newDateYm + File.separator;
                //取当前路径下按时间的第一个文件名
                return path + newDateYm + File.separator;
            }
            //取最后一个
            String last = "";
            if (files.length > 0) {
                //排序
                Arrays.sort(files);
                //取出最后一个
                last = files[files.length - 1];
            }
            //第二次判断是否为同一月份
            if (newDateYm.equals(newFileName)) {
                return FINAL_uploadPathYYYYMM;
            } else {
                if (newDateYm.equals(last)) {//文件夹里有当前月
                    return last;
                } else {//文件夹里没有当前月创建下一月
                            // 创建文件，根据给定的路径创建
                    sftp.mkdir(path + newDateYm);
                    // 按时间排序
                    /*
                     * File[] fileSEnd = fileNext.listFiles(); Arrays.sort(fileSEnd, new FileComparator());
                     */
                    //给当前月赋值
                    FINAL_YYYYMMFileName = newDateYm;
                    //给当前路径赋值
                    FINAL_uploadPathYYYYMM = path + newDateYm + File.separator;
                    return path + newDateYm + File.separator;
                }
            }
        } catch (Exception e) {
            logger.error("操作失败", e);
        }
        return "";
    }

    /**
     * 获取文件夹列表
     * @param session
     * @param path
     * @param num
     * @return
     */
    private static String[] getTopNumFileName(Session session, String path) {
        Channel channel = null;
        InputStream in = null;
        try {
            channel = session.openChannel("exec");
            ChannelExec execChannel = (ChannelExec) channel;
            // 多个命令的执行需要用分号隔开 
            // cd /home/cnyes_xml/;ls -lst | head -n20+ num
            String cmd = "cd " + path + ";ls -Gt | head -n100";
            execChannel.setCommand(cmd);
            in = channel.getInputStream();
            channel.connect();
            StringBuffer sb = new StringBuffer("");
            int c = -1;
            while ((c = in.read()) != -1) {
                sb.append((char) c);
            }
            return sb.toString().split("\n");
        } catch (JSchException e) {
            logger.error("获取Exec Channel失败：", e);
        } catch (IOException e) {
            logger.error("输入文件流异常：", e);
        } finally {
            if (channel != null) {
                channel.disconnect();
            }
            try {
                IOUtils.closeQuietly(in);
            } catch (Exception e) {
                logger.error("文件流关闭失败：", e);
            }
        }
        return null;

    }

    /**
     * 删除服务资源
     * @param session
     * @param path
     * @param num
     * @return
     */
    public static String delName(Session session, String path) {
        Channel channel = null;
        try {
            channel = session.openChannel("exec");
            ChannelExec execChannel = (ChannelExec) channel;
            // 多个命令的执行需要用分号隔开 
            // cd /home/cnyes_xml/;ls -lst | head -n20+ num
            String cmd = "rm -rf " + path;
            execChannel.setCommand(cmd);
            channel.connect();
            return "1";
        } catch (JSchException e) {
            logger.error("获取Exec Channel失败：", e);
        } finally {
            if (channel != null) {
                channel.disconnect();
            }
        }
        return null;

    }
    
    /**
     * 删除图片
     * @param session
     * @param path
     * @param num
     * @return
     */
    public static String delImg(String serverIp, String userName, String password, String path) {
        Channel channel = null;
        Session session =null;
        try {
        	session = getSftpSession(serverIp, userName, password, 22, 3);
        	
            channel = session.openChannel("exec");
            ChannelExec execChannel = (ChannelExec) channel;
            // 多个命令的执行需要用分号隔开 
            // cd /home/cnyes_xml/;ls -lst | head -n20+ num
            String cmd = "rm -rf " + path;
            execChannel.setCommand(cmd);
            channel.connect();
            return "1";
        } catch (Exception e) {
            logger.error("获取session Channel失败：", e);
        } finally {
        	 if (session != null) {
                 session.disconnect();
             }
            if (channel != null) {
                channel.disconnect();
            }
            
        }
        return null;

    }

    /**
     * 
     * getSftpChanel(通过session获取指定类型的SftpChannel)
     * 
     * @param session
     * @param type
     * @return Channel
     * @exception
     */
    public static ChannelSftp getSftpChanel(Session session) {
        if (null == session) {
            return null;
        }
        try {
            Channel channel = session.openChannel("sftp");
            channel.connect();
            return (ChannelSftp) channel;
        } catch (JSchException e) {
            logger.error("获取SFTP Channel失败：", e);
        }
        return null;
    }

    /**
     * 
     * getSFTPSession(获取SFTP Session)
     * 
     * @param serverIp
     * @param userName
     * @param password
     * @param port
     * @param num
     *            出现异常后重复连接的次数
     * @return Session
     * @exception
     */

    public static Session getSftpSession(String serverIp, String userName, String password, int port, int num)
            throws Exception {

        JSch jsch = new JSch();
        Session session = null;
        try {
            session = jsch.getSession(userName, serverIp, port);
            session.setPassword(password);
            // 设置超时时间
            session.setTimeout(30000);
            Properties config = new Properties();
            config.put("StrictHostKeyChecking", "no");
            session.setConfig(config);
            session.connect();
        } catch (JSchException e) {
            if (num >= 1) {
                num -= 1;
                return getSftpSession(serverIp, userName, password, port, num);
            }
            throw e;
        }
        return session;

    }

    /**
     * 上传图片
     * @param cDir
     * @param fileName
     * @throws Exception
     */
   /* public static void syncDelImg(String cDir, String fileName) throws Exception {
        Session session = null;
        Channel channel = null;
        JSch jsch = new JSch();
        if (new Integer(SSHSftp.PORT).intValue() <= 0) {
            session = jsch.getSession(SSHSftp.USERNAME, SSHSftp.IP);
        } else {
            session = jsch.getSession(SSHSftp.USERNAME, SSHSftp.IP, new Integer(SSHSftp.PORT).intValue());
        }
        if (session == null) {
            throw new Exception("session is null");
        }
        session.setPassword(SSHSftp.PD);//设置密码   
        session.setConfig("StrictHostKeyChecking", "no");
        session.connect(30000);
        try {
            channel = (Channel) session.openChannel("sftp");
            channel.connect(1000);
            ChannelSftp sftp = (ChannelSftp) channel;
            sftp.cd(cDir);
            sftp.rm(fileName);
        } catch (Exception e) {
            logger.error(e);
        } finally {
            if (session != null) {
                session.disconnect();
            }
            if (channel != null) {
                channel.disconnect();
            }
        }
    }*/
    
  
    
    


    
    

}
