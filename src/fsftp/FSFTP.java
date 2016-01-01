package fsftp;

import java.io.*;

import com.jcraft.jsch.*;

import fsftp.file.*;
public class FSFTP {
	JSch jsch;
	java.util.Properties config;
	String host;
	String user;
	String password;
	int port;
	public static void main(String[] args)
	{
		FSFTP fastsftp = new FSFTP();
		fastsftp.initialize();
		fastsftp.setServer("visualrdseed.com", "root", "k1w1k1w1");
		System.out.println(fastsftp.directoryListing("/var/www/html"));
	}
	public void initialize()
	{
		jsch = new JSch();
		config = new java.util.Properties(); 
		config.put("StrictHostKeyChecking", "no");
	}
	
	
	private String ssh(String command)
	{
		try{
            String result = "";
            Session session=jsch.getSession(user, host, 22);
            session.setPassword(password);
            session.setConfig(config);
            session.connect();
            //System.out.println("Connected");
             
            Channel channel=session.openChannel("exec");
            ((ChannelExec)channel).setCommand(command);
            channel.setInputStream(null);
            ((ChannelExec)channel).setErrStream(System.err);
             
            InputStream in=channel.getInputStream();
            channel.connect();
            byte[] tmp=new byte[1024];
            while(true){
              while(in.available()>0){
                int i=in.read(tmp, 0, 1024);
                if(i<0)break;
                result += new String(tmp, 0, i);
              }
              if(channel.isClosed()){
                //System.out.println("exit-status: "+channel.getExitStatus());
                break;
              }
//              try{Thread.sleep(1000);} catch(Exception ee){ee.printStackTrace();}
            }
            channel.disconnect();
            session.disconnect();
            //System.out.println("DONE");
            return result;
        }catch(Exception e){
            e.printStackTrace();
        }
		return "";
	}
	
	
	public void setServer(String host, String user, String password)
	{
		this.host = host;
		this.user = user;
		this.password = password;
		this.port = 22;
	}
	public void setServer(String host, String user, String password, String port)
	{
		this.host = host;
		this.user = user;
		this.password = password;
		try
		{
			this.port = Integer.valueOf(port);
		}
		catch(java.lang.NumberFormatException e)
		{
			System.out.println("Port has to be a number");
			System.exit(0);
		}
	}
	
	public String directoryListing(String location)
	{
		String response = ssh("ls -al " + location);

		FSFTPFile[] files = FSFTPFile.getFiles(response);
		String result = "";
		for(FSFTPFile file : files)
		{
			result += file.getFilename() + "\n";
		}
		return result;
	}
	public void download()
	{
		String SFTPHOST = "10.20.30.40";
		int    SFTPPORT = 22;
		String SFTPUSER = "username";
		String SFTPPASS = "password";
		String SFTPWORKINGDIR = "/export/home/kodehelp/";

		Session     session     = null;
		Channel     channel     = null;
		ChannelSftp channelSftp = null;

		try{
			JSch jsch = new JSch();
			session = jsch.getSession(SFTPUSER,SFTPHOST,SFTPPORT);
			session.setPassword(SFTPPASS);
			java.util.Properties config = new java.util.Properties();
			config.put("StrictHostKeyChecking", "no");
			session.setConfig(config);
			session.connect();
			channel = session.openChannel("sftp");
			channel.connect();
			channelSftp = (ChannelSftp)channel;
			channelSftp.cd(SFTPWORKINGDIR);
			byte[] buffer = new byte[1024];
			BufferedInputStream bis = new BufferedInputStream(channelSftp.get("Test.java"));
			File newFile = new File("C:/Test.java");
			OutputStream os = new FileOutputStream(newFile);
			BufferedOutputStream bos = new BufferedOutputStream(os);
			int readCount;
			//System.out.println("Getting: " + theLine);
			while( (readCount = bis.read(buffer)) > 0) {
				System.out.println("Writing: " );
				bos.write(buffer, 0, readCount);
			}
			bis.close();
			bos.close();
		}
		catch(Exception ex){
			ex.printStackTrace();
		}

	}
}
