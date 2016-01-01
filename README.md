#FSFTP - A faster sftp

the purpose of this project is to make a client that will allow file transfer protocol of a large amount of files, much faster.

FTP is a great protocol for transferring files. It flies when you transfer one large file but what if you have to transfer thousands of small files that
add up to be very large in size? FTP croaks in speed and your transfer rate drops significantly.

##The Problem

FTP makes a request for a file, then downloads it. Once the file is downloaded, it requests for another file and downloads that too.
When there are thousands of small files, the request for the file itself takes longer than the file download. This makes transfer rates drop significantly


##The Solution

My solution to this problem has been to compress the target download into a single file and transfer it with one request. The download rate cap at the bandwidth trasnfer rate and ftp
becomes very useful again.


##The purpose

This program shall make the compression and transfer a seamless process that can be done with one push of a button. 

I call it FSFTP - Faster Secure File Transfer Protocol