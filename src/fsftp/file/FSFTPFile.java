package fsftp.file;

public class FSFTPFile {
	String Filename = "";
	String Permissions = "";
	String Owner = "";
	String Group = "";
	String Size = "";
	String Date = "";
	boolean isDirectory;
	int numOfLinks;
	
	public FSFTPFile (String parse)
	{
		parse = parse.trim().replaceAll(" +", " ");
		String[] attribs = parse.split(" ");
		this.Permissions = attribs[0];
		isDirectory = Permissions.charAt(0) == 'd';
		this.numOfLinks = Integer.valueOf(attribs[1]);
		this.Owner = attribs[2];
		this.Group = attribs[3];
		this.Size = attribs[4];
		this.Date = attribs[5] + " " + attribs[6] + " " + attribs[7];
		this.Filename = attribs[8];
		
		//this.Filename = "" + attribs.length;
	}
	public static FSFTPFile[] getFiles(String parse)
	{
		
		String[] individualFiles = parse.split("\n");
		FSFTPFile[] files;
		files = new FSFTPFile[individualFiles.length - 1];
		for(int i =0; i < files.length; i++)
		{
			files[i] = new FSFTPFile(individualFiles[i + 1]);
		}
		
		return files;
	}
	public String getFilename()
	{
		return this.Filename;
	}
}
