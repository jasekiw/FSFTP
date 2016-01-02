package fsftp;

import java.io.*;

import com.jcraft.jsch.*;

import fsftp.file.*;
public class FSFTP
{
	JSch jsch;
	java.util.Properties config;
	Session session;
	ChannelSftp channelSftp;
	String host;
	String user;
	String password;
	int port;
	public static void main(String[] args)
	{
		FSFTP fastsftp = new FSFTP();
		try
		{
			BufferedReader reader = new BufferedReader(new FileReader(new File("settings.txt")));
			String server = reader.readLine();
			String username = reader.readLine();
			String password = reader.readLine();
			fastsftp.setServer(server, username, password);
			fastsftp.connect();
			System.out.println(fastsftp.directoryListing("/var/www/html"));
			fastsftp.disconnect();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public FSFTP()
	{
		this.initialize();
	}
	private void initialize()
	{
		jsch = new JSch();
		config = new java.util.Properties(); 
		config.put("StrictHostKeyChecking", "no");
	}


	/**
	 * Sends ssh command to the server
	 * @requires connect() to be called
	 * @param command The command to be sent via ssh
	 * @return The response from the server
	 */
	private String ssh(String command)
	{
		long start = System.currentTimeMillis();
		try{
            String result = "";
            Channel channel= session.openChannel("exec");
            ((ChannelExec)channel).setCommand(command);
            channel.setInputStream(null);
            ((ChannelExec)channel).setErrStream(System.err);
             
            InputStream in=channel.getInputStream();
            channel.connect();
            byte[] tmp=new byte[1024];
            while(true){
              while(in.available()>0){
                int i= in.read(tmp, 0, 1024);
                if( i < 0 ) break;
                result += new String(tmp, 0, i);
              }
              if(channel.isClosed()){
                //System.out.println("exit-status: "+channel.getExitStatus());
                break;
              }
//              try{Thread.sleep(1000);} catch(Exception ee){ee.printStackTrace();}
            }
			System.out.println("time spent: " + (System.currentTimeMillis() - start) );
            channel.disconnect();
            //System.out.println("DONE");
            return result;
        }catch(Exception e){
            e.printStackTrace();
        }
		return "";
	}


	/**
	 * Sets the server to connect to
	 * @param host The hostname
	 * @param user The username
	 * @param password The password
	 */
	public void setServer(String host, String user, String password)
	{
		this.host = host;
		this.user = user;
		this.password = password;
		this.port = 22;
	}

	/**
	 * Sets the server to connect to
	 * @param host The hostname
	 * @param user The username
	 * @param password The password
	 */
	public void setServer(String host, String user, String password, int port)
	{
		this.host = host;
		this.user = user;
		this.password = password;
		this.port = port;
	}

	/**
	 * Sets the server to connect to
	 * @param host The hostname
	 * @param user The username
	 * @param password The password
	 */
	public void setServer(String host, String user, String password, String port)
	{
		this.host = host;
		this.user = user;
		this.password = password;
		try { this.port = Integer.valueOf(port); }
		catch(java.lang.NumberFormatException e)
		{
			System.out.println("Port has to be a number");
			System.exit(0);
		}
	}

	/**
	 * Connects to the remote server
	 * @requires setServer to be called and initialization
	 *
	 */
	public boolean connect()
	{
		try
		{
			session= jsch.getSession(user, host, 22);
			session.setPassword(password);
			session.setConfig(config);
			session.connect();

		}
		catch(JSchException e)
		{
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * Checks if the session is connected to the server
	 * @return Connected
	 */
	public boolean isConnected() {
		return session != null && session.isConnected();
	}

	/**
	 * Disconnects from the server
	 * @return Successful
	 */
	public boolean disconnect()
	{
		session.disconnect();
		return true;
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
		Channel     channel     = null;
		try{
			JSch jsch = new JSch();
			session = jsch.getSession(user,host,port);
			session.setPassword(password);
			java.util.Properties config = new java.util.Properties();
			config.put("StrictHostKeyChecking", "no");
			session.setConfig(config);
			session.connect();
			channelSftp = (ChannelSftp)session.openChannel("sftp");
			channelSftp.connect();
			channelSftp.cd("/");

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
