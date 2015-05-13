package com.cdk.ats.udp.process;

import java.net.DatagramPacket;

import org.apache.log4j.Logger;

import com.cdk.ats.udp.heart.HeartContext;
import com.cdk.ats.udp.operating.Operating_0x01;
import com.cdk.ats.udp.operating.Operating_0x02;
import com.cdk.ats.udp.operating.Operating_0x10;
import com.cdk.ats.udp.operating.Operating_0x20;
import com.cdk.ats.udp.operating.Operating_0x30;
import com.cdk.ats.udp.operating.Operating_0x40;
import com.cdk.ats.udp.operating.Operating_0x50;
import com.cdk.ats.udp.operating.Operating_0x60;
import com.cdk.ats.udp.reset.ResetContext;
import com.cdk.ats.udp.utils.CommandTools;

/**
 * 类型处理
 * 
 * @author cdk
 * 
 */
public class TypeProcess {

	private static Logger log=Logger.getLogger(TypeProcess.class);
	private int ft;// 命令总分类（一级分类）
	private int st;// 命令子分类（二级分类）
	private byte[] data;
	private DatagramPacket dp;

	public byte[] getData() {
		return data;
	}

	public void setData(byte[] data) {
		this.data = data;
	}

	/***
	 * 被调用 的处理类
	 * 
	 * @param dp
	 *            传入的数据包
	 */
	public void process(DatagramPacket dp) {
		this.receiverProcess(dp);
	}

	/****
	 * 接收数据包时调用 的处理方法
	 * 
	 * @param dp
	 */
	public void receiverProcess(DatagramPacket dp) {
		this.dp = dp;
		this.data = dp.getData();
		this.ft = data[0] & 0xff;
		this.st = data[1] & 0xff;
		receiverType();
	}

	/****
	 * 接收外来数据包时，相应的命令的处理类
	 */
	private void receiverType() {
		// 连接Operating6.java
		if (ft == 0x40) {
			Operating_0x40 ox40 = new Operating_0x40(dp);
			// 49 心跳包 0x40 0x01
			if (st == 0x01) {
				ox40.process_0x40_0x01();
			}
			// 50 握手包 0x40 0x02
			else if (st == 0x02) {
				ox40.process_0x40_0x02();
			}
		//} else if (true) {
		} else if (HeartContext.inputHasheart(dp)) {
			//判断当前是不是正在进行数据重置，如果是，则验证是不是重置命令对应的响应包
			if(ResetContext.isReset(dp)){
				//如果重置命令对应的响应包，则移除对应的包，并结束
				if(ActionContext.hasRestKey(CommandTools.formateSequnceToInt(dp.getData())))
				{
				//	LogContent.receiveCount++;
					return;
				}else 	if(ft==0x10){
					//重置启示命令 
					if(st==0x24){
						Operating_0x10 ox10 = new Operating_0x10();
						ox10.setDp(dp);
						ox10.process_0x10_0x24();
						return;
					}else if(st==0x25){
						//重置结束命令
						Operating_0x10 ox10 = new Operating_0x10();
						ox10.setDp(dp);
						ox10.process_0x10_0x25();	
						return;
					}else if(ResetContext.connectioning(dp.getData())){
						//如果处理链接状态，则不处理此数据包
						log.error("异常数据包"+CommandTools.formateSequnceToInt(dp.getData())+",设备"+CommandTools.getOne(dp.getData()));
						
						return;
					}
					
				}
				
			}
			// 设备信息 Operating1.java
			if (ft == 0x01) {
				// 1 上报设备信息 0x01 0x01
				if (st == 0x01) {

					Operating_0x01 o1 = new Operating_0x01(dp);
					o1.process1();
					o1.CloseDao();
				}
			}
			// 设备状态 Operating2.java
			else if (ft == 0x02) {
				Operating_0x02 o2 = new Operating_0x02(dp);

				// 2 上报设备电压信息 0x02 0x01
				if (st == 0x01) {

					o2.process_0x02_0x01();
				}
				// 3 上报设备通讯状态 0x02 0x02
				else if (st == 0x02) {

					o2.process_0x02_0x02();
				}
				// 4 上报设备被撬 0x02 0x03
				else if (st == 0x03) {

					o2.process_0x02_0x03();
				}
				// 5 上报设备输入状态 0x02 0x04
				else if (st == 0x04) {

					o2.process_0x02_0x04();
				}
				// 6 上报设备输出状态 0x02 0x05
				else if (st == 0x05) {

					o2.process_0x02_0x05();
				}
				// 6 上报设备输入异常 0x02 0x06
				else if (st == 0x06) {

					o2.process_0x02_0x06();
				}
				// 6 上报设备输入触发 0x02 0x07
				else if (st == 0x07) {

					o2.process_0x02_0x07();
				}
				o2.CloseDao();
			}
			// 系统设置 Operating3.java
			else if (ft == 0x10) {
				Operating_0x10 ox10 = new Operating_0x10();
				ox10.setDp(dp);
				// 7 上报交叉设置信息 0x10 0x01
				if (st == 0x01) {

					ox10.process_0x10_0x01();
				}
				// 8 下传交叉设置信息 0x10 0x02
				else if (st == 0x02) {
					ox10.process_0x10_0x02();
				}
				// 9 上报输入联动信息 0x10 0x03
				else if (st == 0x03) {

					ox10.process_0x10_0x03();
				}
				// 10 下传输入联动信息 0x10 0x04
				else if (st == 0x04) {

					ox10.process_0x10_0x04();
				}
				// 11 上报用户密码信息 0x10 0x05
				else if (st == 0x05) {

					ox10.process_0x10_0x05();
				}
				// 12 下传用户密码信息 0x10 0x06
				else if (st == 0x06) {
					ox10.process_0x10_0x06();
				}
				// 13 上报管理员密码信息 0x10 0x07
				else if (st == 0x07) {
					ox10.process_0x10_0x07();
				}
				// 14 下传管理员密码信息 0x10 0x08
				else if (st == 0x08) {
					ox10.process_0x10_0x08();
				}
				// 15 上报输入属性信息 0x10 0x09

				else if (st == 0x09) {
					ox10.process_0x10_0x09();
				}
				// 16 下传输入属性信息 0x10 0x0a

				else if (st == 0x0a) {
					ox10.process_0x10_0x0a();
				}
				// 17 上报用户电话号码信息 0x10 0x0b

				else if (st == 0x0b) {
					ox10.process_0x10_0x0b();
				}
				// 18 下传用户电话号码信息 0x10 0x0c

				else if (st == 0x0c) {
					ox10.process_0x10_0x0c();
				}
				// 19 上报用户短信号码信息 0x10 0x0d

				else if (st == 0x0d) {
					ox10.process_0x10_0x0d();
				}
				// 20 下传用户短信号码信息 0x10 0x0e

				else if (st == 0x0e) {
					ox10.process_0x10_0x0e();
				}
				// 21 上报开启/屏蔽短信端口信息 0x10 0x0f

				else if (st == 0x0f) {
					ox10.process_0x10_0x0f();
				}
				// 22 下传开启/屏蔽短信端口信息 0x10 0x10

				else if (st == 0x10) {
					ox10.process_0x10_0x10();
				}
				// 23 上报开启/屏蔽电话端口信息 0x10 0x11

				else if (st == 0x11) {
					ox10.process_0x10_0x11();
				}
				// 24 下传开启/屏蔽电话端口信息 0x10 0x12

				else if (st == 0x12) {
					ox10.process_0x10_0x12();
				}
				// 25 上报打印机设置信息 0x10 0x13

				else if (st == 0x13) {
					ox10.process_0x10_0x13();
				}
				// 26 下传打印机设置信息 0x10 0x14

				else if (st == 0x14) {
					ox10.process_0x10_0x14();
				}
				// 27 上报接警中心1号码信息 0x10 0x15

				else if (st == 0x15) {
					ox10.process_0x10_0x15();
				}
				// 28 下传接警中心1号码信息 0x10 0x16

				else if (st == 0x16) {
					ox10.process_0x10_0x16();
				}
				// 29 上报接警中心2号码信息 0x10 0x17

				else if (st == 0x17) {
					ox10.process_0x10_0x17();
				}
				// 30 下传接警中心2号码信息 0x10 0x18

				else if (st == 0x18) {
					ox10.process_0x10_0x18();
				}
				// 31 上报开启/屏蔽用户信息 0x10 0x19

				else if (st == 0x19) {
					ox10.process_0x10_0x19();
				}
				// 32 下传开启/屏蔽用户信息 0x10 0x1a

				else if (st == 0x1a) {
					ox10.process_0x10_0x1a();
				}
				// 33 下传用户名信息 0x10 0x1b

				else if (st == 0x1b) {
					ox10.process_0x10_0x1b();

				}
				// 34 下传设备名信息 0x10 0x1c

				else if (st == 0x1c) {
					ox10.process_0x10_0x1c();
				}
				// 35 上报开启/屏蔽设备信息 0x10 0x1d

				else if (st == 0x1d) {
					ox10.process_0x10_0x1d();
				}
				// 36 下传开启/屏蔽设备信息 0x10 0x1e

				else if (st == 0x1e) {
					ox10.process_0x10_0x1e();
				}
				// 37 下传输入所对应2级用户信息 0x10 0x1f

				else if (st == 0x1f) {
					ox10.process_0x10_0x1f();
				}
				// 38 下传输入名信息 0x10 0x20
				else if (st == 0x20) {
					ox10.process_0x10_0x20();
				}
				// 39 下传输出名信息 0x10 0x21
				else if (st == 0x21) {
					ox10.process_0x10_0x21();
				}
				// 40 下传电话通讯上报时间间隔信息 0x10 0x22
				else if (st == 0x22) {
					ox10.process_0x10_0x22();
				}
				// 41 下传短信通讯上报时间间隔信息 0x10 0x23
				else if (st == 0x23) {
					ox10.process_0x10_0x23();
				}
				ox10.CloseDao();
			}
			// 用户操作 Operating_0x20.java
			else if (ft == 0x20) {
				Operating_0x20 ox20 = new Operating_0x20(dp);
				// 42 上报用户操作信息 0x20 0x01
				if (st == 0x01) {

					ox20.process_0x20_0x01();
				}
				// 43 下传用户操作信息 0x20 0x02
				else if (st == 0x02) {

					ox20.process_0x20_0x02();
				}

				// 44 上报管理员操作信息 0x20 0x03
				else if (st == 0x03) {

					ox20.process_0x20_0x03();
				}
				// 45 下传管理员操作信息 0x20 0x04
				else if (st == 0x04) {

					ox20.process_0x20_0x04();
				}
				// 46 下传输出信息 0x20 0x05
				else if (st == 0x05) {

					ox20.process_0x20_0x05();
				}
				ox20.closeDao();
			}
			// 数据传输 Operating5.java
			else if (ft == 0x30) {
				Operating_0x30 ox30 = new Operating_0x30(dp);
				// 47 下传文件信息 0x30 0x01
				if (st == 0x01) {
					ox30.process_0x30_0x01();
				}
				// 48 下传时间信息 0x30 0x02
				else if (st == 0x02) {
					ox30.process_0x30_0x01();
				}
			}

			// 初始化Operating7.java
			else if (ft == 0x50) {
				Operating_0x50 ox50 = new Operating_0x50(dp);
				if (st == 0x01) {
					ox50.process_0x50_0x01();
				} else if (st == 0x02) {
					ox50.process_0x50_0x02();
				} else if (st == 0x03) {
					ox50.process_0x50_0x03();
				} else if (st == 0x04) {
					ox50.process_0x50_0x04();
				} else if (st == 0x05) {
					ox50.process_0x50_0x05();
				} else if (st == 0x06) {
					ox50.process_0x50_0x06();
				} else if (st == 0x07) {
					ox50.process_0x50_0x07();
				} else if (st == 0x08) {
					ox50.process_0x50_0x08();
				} else if (st == 0x09) {
					ox50.process_0x50_0x09();
				} else if (st == 0x0a) {
					ox50.process_0x50_0x0a();
				} else if (st == 0x0b) {
					ox50.process_0x50_0x0b();
				} else if (st == 0x0c) {
					ox50.process_0x50_0x0c();
				} else if (st == 0x0d) {
					ox50.process_0x50_0x0d();
				} else if (st == 0x0e) {
					ox50.process_0x50_0x0e();
				} else if (st == 0x0f) {
					ox50.process_0x50_0x0f();
				} else if (st == 0x10) {
					ox50.process_0x50_0x10();
				} else if (st == 0x11) {
					ox50.process_0x50_0x11();
				} else if (st == 0x12) {
					ox50.process_0x50_0x12();
				} else if (st == 0x13) {
					ox50.process_0x50_0x13();
				} else if (st == 0x14) {
					ox50.process_0x50_0x14();
				} else if (st == 0x15) {
					ox50.process_0x50_0x15();
				} else if (st == 0x16) {
					ox50.process_0x50_0x16();
				}
				ox50.CloseDao();
			} else if (ft == 0x60) {
				if (st == 0x01) {
					Operating_0x60 ox60 = new Operating_0x60(dp);
					ox60.process_0x60_0x01();
					ox60.CloseDao();
				}

			}
		}
	}
	

}

