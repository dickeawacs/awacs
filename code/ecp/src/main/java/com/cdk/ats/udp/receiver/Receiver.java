package com.cdk.ats.udp.receiver;

import java.net.DatagramPacket;
import java.net.DatagramSocket;

/***
 * 接收器：用于接收指定端口的数据包
 * @author cdk
 *
 */
public class Receiver implements Runnable {
	private DatagramSocket  datagramSocket;
	
	
	
	public void run() {
		try {
			while (true) {
				ReceiverProcess rp=new ReceiverProcess();
				DatagramPacket tempDP = new DatagramPacket(rp.getBuff(), rp.getBuff().length);
				datagramSocket.receive(tempDP);
				rp.setDp(tempDP);
				ReceiverContext.pushReceiverProcess(rp);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

}



	public DatagramSocket getDatagramSocket() {
		return datagramSocket;
	}



	public void setDatagramSocket(DatagramSocket datagramSocket) {
		this.datagramSocket = datagramSocket;
	}
	
	
	
	
	
}
