package com.cdk.ats.udp.receiver;

import java.net.DatagramPacket;

import com.cdk.ats.udp.process.TypeProcess;

/***
 * 数据处理类:用于接收数据包后进行处理
 * @author cdk
 *
 */
public class ReceiverProcess implements Runnable {
	/***
	 * 数据报缓冲区
	 */
	private byte[] buff=new byte[1024];
	/***
	 * 接收到的数据包
	 */
	private DatagramPacket  dp;
	
	public void run() {
		//开始时间
		//long kill=System.currentTimeMillis();
		//LogContent.times(kill);
		if (check()) {
			//LogContent.r(dp);
			TypeProcess typep = new TypeProcess();
			typep.process(dp);
			typep=null;
		}else{
		//	LogContent.r_e(dp);
		}
		//时间统计 
		//LogContent.times(System.currentTimeMillis());
	//	LogContent.times(kill-System.currentTimeMillis());
	}
	/***
	 * 判断验证值 ,
	 * (0+1+...+(n-1))%256==n;
	 * @return boolean
	 */
	private boolean check() {
		boolean end = false;
		if(dp!=null){
				int len=dp.getLength();
				buff=dp.getData();
				int t=0;
				for (int i = 0; i <len-1; i++) {
					t+=buff[i]&0xff;
				}
				if((buff[len-1]&0xff)==t%256){
					end=true;
				}
		}
		return end;
	}

	public byte[] getBuff() {
		return buff;
	}

	public void setBuff(byte[] buff) {
		this.buff = buff;
	}

	public DatagramPacket getDp() {
		return dp;
	}

	public void setDp(DatagramPacket dp) {
		this.dp = dp;
	}

}
