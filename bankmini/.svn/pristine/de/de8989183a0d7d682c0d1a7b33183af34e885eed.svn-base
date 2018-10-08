package com.huayu.core.util;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import org.apache.log4j.Logger;
import ch.ethz.ssh2.ChannelCondition;
import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.Session;
import ch.ethz.ssh2.StreamGobbler;

public class RemoteShellExecutor {
	private static final Logger logger = Logger.getLogger(RemoteShellExecutor.class);
     
     private Connection conn;
     /** 远程机器IP */
     private String ip;
     /** 用户名 */
     private String osUsername;
     /** 密码 */
     private String pd;
     private String charset = Charset.defaultCharset().toString();

     private static final int TIME_OUT = 1000 * 55 * 60;

     /**
      * 构造函数
      * @param ip
      * @param usr
      * @param pasword
      */
     public RemoteShellExecutor(String ip, String usr, String pd) {
          this.ip = ip;
         this.osUsername = usr;
         this.pd = pd;
     }


     /**
     * 登录
     * @return
     * @throws IOException
     */
     private boolean login() throws IOException {
         conn = new Connection(ip);
         conn.connect();
         return conn.authenticateWithPassword(osUsername, pd);
     }
     
     
    

     /**
     * 执行脚本
     * 将视频先切成ts 格试 ()
     * @param cmds
     * @return
     * @throws Exception
     */
     public int execffmpeg(String cmds) throws Exception {
         InputStream stdOut ;
         InputStream stdErr;
        // String outStr = "";
         String outErr = "";
         Session session = null;
         int ret = -1;
         try {
         if (login()) {
             // Open a new {@link Session} on this connection
              session = conn.openSession();
             // Execute a command on the remote machine.
             session.execCommand(cmds);
             stdOut = new StreamGobbler(session.getStdout());
             //outStr = processStream(stdOut, charset);
             stdErr = new StreamGobbler(session.getStderr());
           //  outErr = processStream(stdErr, charset);
             pubThread(stdOut, stdErr);
			session.waitForCondition(ChannelCondition.EXIT_STATUS, TIME_OUT);
             stdOut.close();
 			 stdErr.close();
             ret = session.getExitStatus();
         } else {
        	 logger.error(outErr);
             //throw new Exception("登录远程机器失败" + ip); // 自定义异常类 实现略
         }
         } finally {
             if (conn != null) {
                 conn.close();
             }
             if (session != null) {
           	  session.close();
             }
             //IOUtils.closeQuietly(stdErr);
             //IOUtils.closeQuietly(stdErr);
         }
         return ret;
     }
     
     /**
      * 执行脚本
      * 从ts 再切成多个片
      * @param cmds
      * @return
      * @throws Exception
      */
      public int execHlsts(String cmds) throws Exception {
          InputStream stdOut;
          InputStream stdErr;
          Session session = null;
          //String outStr = "";
          String outErr = "";
          int ret = -1;
          try {
          if (login()) {
              // Open a new {@link Session} on this connection
               session = conn.openSession();
              // Execute a command on the remote machine.
              session.execCommand(cmds);
              stdOut = new StreamGobbler(session.getStdout());
             // outStr = processStream(stdOut, charset);
              stdErr = new StreamGobbler(session.getStderr());
             // outErr = processStream(stdErr, charset);
              pubThread(stdOut, stdErr);
              session.waitForCondition(ChannelCondition.EXIT_STATUS, TIME_OUT);
              stdOut.close();
  			   stdErr.close();
              ret = session.getExitStatus();
          } else {
        	  logger.error(outErr);
             // throw new Exception("登录远程机器失败" + ip); // 自定义异常类 实现略
          }
          } finally {
              if (conn != null) {
                  conn.close();
              }
              if (session != null) {
            	  session.close();
              }
              //IOUtils.closeQuietly(stdOut);
              //IOUtils.closeQuietly(stdErr);
          }
          return ret;
      }
     //两个线程　读取流与错误流线程 
      private void pubThread(InputStream stdOut, InputStream stdErr){
    	  new Thread() {
				@Override
				public void run() {
					BufferedReader in = new BufferedReader(new InputStreamReader(stdOut));
					String line = null;
					try {
						while ((line = in.readLine()) != null) {
							
						}
					} catch (IOException e) {
						logger.error(e);
					} finally {
						try {
							in.close();
						} catch (IOException e) {
							logger.error(e);
						}
					}
				}
			}.start();
			
			new Thread() {
				@Override
				public void run() {
					BufferedReader err = new BufferedReader(new InputStreamReader(stdErr));
					String line = null;
					try {
						while ((line = err.readLine()) != null) {
							logger.error("err: " + line);
						}
					} catch (IOException e) {
						logger.error(e);
					} finally {
						try {
							err.close();
						} catch (IOException e) {
							logger.error(e);
						}
					}
				}
			}.start();
      }

     /**
     * @param in
     * @param charset
     * @return
     * @throws IOException
     * @throws UnsupportedEncodingException
     */
     /*private String processStream(InputStream in, String charset) throws Exception {
         byte[] buf = new byte[4096];
         StringBuilder sb = new StringBuilder();
         //long t1=System.currentTimeMillis();
         int i=0;
         while (in.read(buf) != -1) {
        	 System.out.println(i++);
             sb.append(new String(buf, charset));
         }
        // long t2=System.currentTimeMillis();
        // logger.error("用时:"+(t2-t1)+"ms");
         //System.out.println("用时:"+(t2-t1)+"ms");
         return sb.toString();
         
     }*/

    /*public static void main(String args[]) throws Exception {
        RemoteShellExecutor executor = new RemoteShellExecutor("192.168.2.230", "root", "huayukeji");
        String aa ="1490628309278.wmv";
        // 执行myTest.sh 参数为java Know dummy
        //System.out.println(executor.execffmpeg("/home/hykj/IFileGenTool/ffmpegHls.sh "+aa+" 5681"));
        // 执行将视频先切成ts 格试 ()
        String bb ="5681.ts";
        // 执行myTest.sh 参数为java Know dummy
        System.out.println(executor.execHlsts("/home/hykj/IFileGenTool/segmenterHls.sh "+bb+" 8811"));
    }*/
}