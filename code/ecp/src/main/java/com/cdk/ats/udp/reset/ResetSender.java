package com.cdk.ats.udp.reset;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ConcurrentMap;

import org.apache.log4j.Logger;

import com.cdk.ats.udp.exception.AtsException;
import com.cdk.ats.udp.process.ActionContext;
import com.cdk.ats.udp.process.ActionParams;
import com.cdk.ats.udp.process.ActionProcess;
import com.cdk.ats.udp.process.ActionReady;
import com.cdk.ats.udp.transmitter.TransmitterContext;
import com.cdk.ats.udp.utils.CommandTools;

/****
 * 
 * 重置线程（特殊，入口 ） 启动特殊缓存空间 开始发送数据包 暂停发送 紧急发送（缓存包） 减速
 * 
 * @author dingkai
 * 
 */
public class ResetSender implements Runnable {

	private static Logger logger = Logger.getLogger(ResetSender.class);
	/***
	 * 毫秒提速或减速的界限值，当大于这个值时，只能微秒提速，当小于这个值得1/2时只能微秒减速
	 */
	// private int temp_ms=10;
	/*** 毫秒级减速间隔 */
	//private int decelerate_ms_val = 1;
	/** 纳秒级减速间隔 */
	// private int decelerate_nanos_val=1000;
	/** 每次发送的间隔（单位 纳秒） **/
	private int split_time = 20;// 间隔为1毫秒
	// 1000纳秒，等于1微妙
	private int split_time_nanos =0;// 1000;

	/** 最小间隔（单位 毫秒）=1 */
	//private int split_time_min = 5;
	/** 最大间隔（单位 毫秒）=100 */
	//private int split_time_max = 50;
	// 暂停标记
	private boolean pause_tag = false;
	// 缓存器
	private ResetCache cache = null;
	// 停止发送标记
	private boolean stop = false;
	/**
	 * 缓存 的重复发送次数 20
	 * **/
	private int reSendTimes = 5;
	// 一层设备号
	private int one = 0;
	/** 最近一次变数时候的缓存量 **/
	//private int lastChangeSize = 0;
	/***
	 * 每次提速的数据包间隔量（至少要发送 skipSize个数据包之后才能加速） skipSize=10;
	 */
	//private int skipSize = 10;

	// 通信通道
	private DatagramSocket dataSocket;

	/***
	 * 数据包组的 流水好
	 */
	private Integer groupKey;
	/**
	 * 一层设备的第一个命令，用于获取IP与PORT以及一层设备编号
	 */
	private ActionReady dataFirst;
	/***
	 * 一套一层设备下的所有数据包
	 * 
	 */
	private List<ActionReady> data;
	/***
	 * 目标地址
	 */
	private SocketAddress targetAddress;

	/***
	 * 数据包量统计：不重复时的正常数量
	 */
	private int pdCount = 0;
	/**
	 * 发送的数量：包含了重复发送的
	 */
	private int sendCount = 0;

	/***
	 * 
	 * 描述：自动 波控制
	 * 
	 * @createBy dingkai
	 * @createDate 2013-12-7
	 * @lastUpdate 2013-12-7
	 * @return
	 */
	/*private int autoContorl() {
		if (pdCount < 100) {
			return 20;
		}
		if (pdCount > 100 && pdCount < 200) {
			return 15;
		}
		if (pdCount > 200 && pdCount < 500) {
			return 10;
		}
		if (pdCount > 1000 && pdCount < 10000) {
			return 5;
		} else {
			return 1;
		}

	}
*/
	/**
	 * 
	 * 描述：减速（增加发送的间隔时间）
	 * 
	 * @createBy dingkai
	 * @createDate 2013-12-7
	 * @lastUpdate 2013-12-7
	 * @param size
	 */
	public void decelerate() {/*
		if (sendCount - lastChangeSize > skipSize) {
			decelerate_ms_val =5;// autoContorl();
			// System.out.println((++decelerateCount)+(type==1?"毫秒":"纳秒")+"提速"+System.currentTimeMillis());
			if (split_time + decelerate_ms_val <= split_time_max) {
				split_time += decelerate_ms_val;
				LogContent.splitTime = split_time;
				//保存本次是发送的第多少个包，做为下次判断的依据
				lastChangeSize = sendCount;
				LogContent.info("第" + (++decelerateCount) + "次减速， "
						+ split_time + "毫秒,time:" + System.currentTimeMillis());
			}
		}
	*/}

	/**
	 * 
	 * 描述： 加速（减少发送的间隔时间）
	 * 
	 * @createBy dingkai
	 * @createDate 2013-12-7
	 * @lastUpdate 2013-12-7
	 * @param size
	 */
	public void dccelerate() {/*
		//System.out.println(sendCount +"--"+ lastChangeSize +"--"+skipSize);
		if (sendCount - lastChangeSize >skipSize) {
			if (split_time - 2 >= split_time_min) {
				split_time=split_time-2;
				LogContent.splitTime = split_time;
				lastChangeSize = sendCount;
				LogContent.info("第" + (++dccelerateCount) + "次加速，" + split_time
						+ "毫秒,time:" + System.currentTimeMillis());
			}
		}
	*/}

	/*
	 * 减速（增加发送的间隔时间）
	 * 
	 * @param type 类型 1-为毫秒级，2-纳秒级
	 * 
	 * public void decelerate( int size) { int type=(split_time<temp_ms?1:2); if
	 * (size - lastChangeSize > skipSize) {
	 * System.out.println((++decelerateCount
	 * )+(type==1?"毫秒":"纳秒")+"提速"+System.currentTimeMillis()); // 1-为毫秒级 if
	 * (type == 1) { if (split_time + decelerate_ms_val <= split_time_max) {
	 * split_time += decelerate_ms_val; } } else if (type == 2) { if
	 * (split_time_nanos + decelerate_nanos_val < 1000000) { split_time_nanos +=
	 * decelerate_nanos_val; }else{ if (split_time + 1 <= split_time_max) {
	 * split_time ++; split_time_nanos= 0; } } } lastChangeSize=size; }
	 * 
	 * }
	 */
	/***
	 * 减速统计
	 */
	//private int decelerateCount = 0;
	/**
	 * 加速统计
	 */
//	private int dccelerateCount = 0;

	/*
	 * 加速（减少发送的间隔时间）
	 * 
	 * @param type 类型 1-为毫秒级，2-纳秒级
	 * 
	 * public void dccelerate(int size) { int type=(split_time>temp_ms/2?1:2);
	 * if (lastChangeSize-size > skipSize) {
	 * System.out.println((++dccelerateCount
	 * )+(type==1?"毫秒":"纳秒")+"加速"+System.currentTimeMillis()); if (type == 1) {
	 * if (split_time - decelerate_ms_val >= split_time_min ) { split_time -=
	 * decelerate_ms_val; } } else if (type == 2) { if (split_time_nanos -
	 * decelerate_nanos_val > 0) { split_time_nanos -= decelerate_nanos_val;
	 * }else{ //如果纳秒加速不可用，则让毫秒减速，且纳秒变大 if (split_time - decelerate_ms_val >=
	 * split_time_min) { split_time -= decelerate_ms_val; split_time_nanos=
	 * 900000; }
	 * 
	 * } } } }
	 */

	/***
	 * 暂停
	 */
	public void pause() {
		pause_tag = true;
	}

	/***
	 * 紧急发送（缓存包） 紧急发送的过程中紧急控速
	 */
	private void sendCache() {
		try {

			if (cache != null && !cache.isCacheEmpty()) {
				//System.out.println("紧急发送" + System.currentTimeMillis());
				int times = reSendTimes;
				ActionReady tempReady = null;
				// 重复发送times次
				while (times > 0 && !cache.isCacheEmpty()) {
					try {
						ConcurrentMap<Integer, ActionReady> keys = cache
								.getCache();
						if (keys != null && !keys.isEmpty() && keys.size() > 0) {

							Iterator<Integer> iterator = keys.keySet()
									.iterator();
							while (iterator.hasNext()) {
								try {
									Integer key = (Integer) iterator.next();
									tempReady = keys.get(key);
									// 可能出现误差，所以需要先判断是否仍然存在
									if (tempReady != null) {
									//	LogContent.reSendCount++;
										// 发送
										send(tempReady);
										// 休眠
									 
											Thread.sleep(split_time);
											// 判断是否仍然超速,超速则减速
										/*	if (cache.isOutBorder()) {
												decelerate();
											}else if(cache.canDccelerate()){
												dccelerate();
											}*/
									 
									}

								} catch (Exception e) {
									iterator.remove();
								}

							}

							/*
							 * for (Iterator<Integer> iterator =
							 * keys.keySet().iterator(); iterator.hasNext();) {
							 * Integer key = (Integer) iterator.next();
							 * 
							 * tempReady=keys.get(key); //可能出现误差，所以需要先判断是否仍然存在
							 * if(tempReady!=null){ LogContent.reSendCount++;
							 * //发送 send(tempReady); //休眠 try { Thread.sleep(
							 * split_time,split_time_nanos); //判断是否仍然超速,超速则减速
							 * if(cache.isOutBorder()){ decelerate(); } } catch
							 * (InterruptedException e) {
							 * logger.error(e.getMessage()); } } }
							 */
						}
						times--;
					} catch (Exception e) {
						logger.error("重复发送出现异常", e);
					}
				}
				// 紧急发送times次后仍然超过最大数量，则停止发送，宣布此次发送失败
				if (cache.isOutMaxBorder()||cache.isOutBorder()) {
					logger.info("超时了，设备重置停止 ..");
					try {
						Thread.sleep(split_time, split_time_nanos);
					} catch (InterruptedException e) {
						logger.error(e.getMessage());
					}
					// 结束发送
					ResetContext
							.over(groupKey, 0, 0, ResetContext.Result_False);
					// 标记程序发送停止
					stop = true;

				}
			}
		} catch (Exception e) {
			// 结束发送
			ResetContext.over(groupKey, 0, 0, ResetContext.Result_False);
			// 标记程序发送停止
			stop = true;
		}
	}

	/***
	 * 发送数据包
	 * 
	 * @param ready
	 *            需要发送的实体
	 * @param dataSocket
	 *            发送的SOCKET通道
	 * @throws AtsException
	 * @throws IOException
	 * @throws InterruptedException
	 */
	private synchronized void send(ActionReady ready) {

		try {
			if (ready.getData() != null && ready.getData().length > 0) {
				// 创建一个需要发送的数据包
				DatagramPacket mydatagram = new DatagramPacket(ready.getData(),
						ready.getData().length);
				mydatagram.setSocketAddress(targetAddress);// 设置数据包的发送目标地址
				//LogContent.sendCount++;// 记录发送的数据包
				sendCount++;// 记录本地已经发送的包量
				dataSocket.send(mydatagram);
				//System.out.println(System.currentTimeMillis());
				//System.out.println("发送编程命令包");
				try {
					// Thread.sleep(split_time,split_time_nanos);
					Thread.sleep(split_time);
				} catch (InterruptedException e) {
					logger.error(e.getMessage());
				}
			}else{
				//System.out.println("发送编程命令包失败");
			}

		} catch (IOException e) {
			//System.out.println("发送编程命令包失败");
			logger.error(e.getMessage());
			/**
			 * 丢失不处理
			 */
		}
	}

	
	public void run() {

		this.setDataFirst(data.get(0));
		one = CommandTools.getOne(dataFirst.getData());
		try {
			targetAddress = TransmitterContext.targetSocketAddressFactory(
					dataFirst.getTargetIp(), dataFirst.getTargetPort());

			// 测试链接
			// ....
		} catch (UnknownHostException e) {
			logger.error(e.getMessage());
			ResetContext
					.error(groupKey, "无法重置设置，因为没有找到需要发送的地址 "
							+ dataFirst.getTargetIp() + ":"
							+ dataFirst.getTargetPort());
			ResetContext.over(groupKey, one, 0, ResetContext.Result_False);
			throw new RuntimeException("");
		}
		// 占用一个发送端口
		try {
			dataSocket = new DatagramSocket();
		} catch (Exception e) {
			logger.error(e.getMessage());
			try {
				dataSocket = new DatagramSocket();
			} catch (SocketException e1) {
				logger.error(e.getMessage());
				ResetContext.error(groupKey, "无法重置设置，系统没有可用端口 ");
				ResetContext.over(groupKey, one, 0, ResetContext.Result_False);
				throw new RuntimeException("");
			}
		}
		try {
			// 创建个缓存区，并且将缓存与当前线程绑定
			cache = new ResetCache(this, one, groupKey);
			if (doEditEquipment()) { 
				//打开编程状态后，睡眠 30S
				if (openDefault()) {
					Thread.sleep(5000);
					//System.out.println("open---打开编程状态----382");
					for (pdCount = 0; pdCount < data.size(); pdCount++) {
						// 如果超过警界线，则需要重设速度
						if (cache.isOutBorder()) {// ((i+1)%cache.getOutBorder()==0||i==cache.getOutBorder()-1)&&
							// 启用紧急发送,紧急发送的过程中会调速
							System.out.println("重发！");
							sendCache();

						} 

						// 判断是否有停止标记，只有当紧急处理失败后，会出现 STOP=TRUE情况
						if (!stop) {
							if (!pause_tag) {// 查看是否已经出现暂停标记，
								cache.add(data.get(pdCount));

								// 正常情况 下，进行发送
								send(data.get(pdCount));
								

							} else {
								// 如果 被 暂时了发送则启动 紧急处理方法
								sendCache();
							}

						} else {
							// 如果打上了停止标记，则不再执行发送
							break;
						}

					}
					// 如果是异常停止发送，则直接取消引用
					if (stop) {
						/**
						 * 关闭对应容器中的空间占用
						 */
						ResetContext.close(one,groupKey);
						ResetContext.over(groupKey, one, 0,
								ResetContext.Result_False);
					} else {
						// 如果是正常结束则需要关注缓存包中的数据是否已经发完了，没有发完要重复发送
						int tempcount = reSendTimes;
						while (!cache.isCacheEmpty() && tempcount > 0) {
							/*********/
							try {
								Thread.sleep(split_time, split_time_nanos);
							} catch (InterruptedException e) {
								logger.error(e.getMessage());
							}
							/***
							 * 如果此时已经没有了数据缓存包。则结束等待
							 */
							if (cache.isCacheEmpty()) {
								break;
							}
							tempcount--;
						}
						if (!cache.isCacheEmpty()) {
							sendCache();
							if (stop && !cache.isCacheEmpty()) {
								ResetContext.over(groupKey, one, 0,
										ResetContext.Result_False);
								/**
								 * 关闭对应容器中的空间占用
								 */
								ResetContext.close(one,groupKey);
							} else {
								over();
							}
						} else {
							over();

						}
					}
				} else {
					ResetContext.over(groupKey, one, 0,
							ResetContext.Result_False);
					ActionContext.resetTempKeys.remove(groupKey);
				}
				//System.out.println("减速：" + decelerateCount);
				//System.out.println("加速：" + dccelerateCount);
			} else {
				//System.out.println("打开编程状态失败...");
				ResetContext.over(groupKey, one, 0, ResetContext.Result_False);
				/**
				 * 关闭对应容器中的空间占用
				 */
				ResetContext.close(one,groupKey);

			}
		} catch (Exception e) {
			ResetContext.over(groupKey, one, 0, ResetContext.Result_False);
			/**
			 * 关闭对应容器中的空间占用
			 */
			ResetContext.close(one,groupKey);
			throw new RuntimeException("");
		}
	}

	public List<ActionReady> getData() {
		return data;
	}

	public void setData(List<ActionReady> data) {
		this.data = data;
	}

	public Integer getGroupKey() {
		return groupKey;
	}

	public void setGroupKey(Integer groupKey) {
		this.groupKey = groupKey;
	}

	public ActionReady getDataFirst() {
		return dataFirst;
	}

	public void setDataFirst(ActionReady dataFirst) {
		this.dataFirst = dataFirst;
	}

	/***
	 * 重置启动命令
	 */
	private boolean doEditEquipment() {
		boolean end=false;
		try{
		//LogContent.resetLogCount();
		 
		ActionParams params = new ActionParams();
		params.setOneP(one);
		params.setOnePT(one);
		params.setUserCode(1);
		params.setUserID(0);
		params.setComand0(0x04);// 0x04编程
		ActionProcess ap = new ActionProcess();
		ap.setParams(params);// 设置参数
		ActionReady ar = ap.ox20_0x04(groupKey);
	 

		ActionContext.putValues(groupKey, params);// 将数据保存至下传的缓存区
		//存储一个编程状态的标记，key=数据包组号,val=200
		ResetContext.connectionOpenDevelop(one);
		send(ar);
		int i = 10;//boolean end=false;  
			while (ResetContext.getConnection().get(one)!=null){
				//当链接的标记值为0时，说明接收到了0x20-0x04的 分类为 0x04的编程状态命令的响应
				if(ResetContext.getConnection().get(one).equals(0)){
					//System.out.println("打开编程状态成功  resetSender.java 518");
					end=true;
					break;
				}
				//第间隔1000毫秒发送一次
			if (i % 3 == 2) {
				send(ar);
			}
			i--;
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				logger.error("线程唤醒失败，" + e.getMessage(), e);
				throw new RuntimeException("重置失败！");
			}

		}
		}catch(Exception e){
			//System.out.println("打开编程状态失败  resetSender.java 530");
			
		}
		//System.out.println("是否成功！"+end);
		return end;
	}
	private boolean openDefault(){

		//LogContent.resetLogCount();

		//System.out.println("发送重置命令。。558");
		ResetContext.ResetOpen.put(one, true);
		ActionContext.resetTempKeys.put(groupKey, cache.getCache());
		ActionProcess ap = new ActionProcess();
		ActionParams apa = new ActionParams();
		apa.setComand0(one);
		apa.setOneP(one);
		ap.setParams(apa);
		ResetContext.connectionTest(one);
		ActionReady ar = ap.ox10_0x24(groupKey);
		//int key = ar.getKey();
		send(ar);
		/*int i = 10;
		while (ActionContext.TempParams.containsKey(key) && i > 0) {
			if (i % 3 == 2) {
				send(ar);
			}
			i--;
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				logger.error("线程唤醒失败，" + e.getMessage(), e);
				throw new RuntimeException("重置失败！");
			}

		}

		return !ActionContext.TempParams.containsKey(key);*/
		return true;
	}

	/***
	 * 重置启动命令
	 */
	/*private boolean open() {
		LogContent.resetLogCount();

		System.out.println("发送重置命令。。558");
		ResetContext.ResetOpen = true;
		ActionContext.resetTempKeys.put(groupKey, cache.getCache());
		ActionProcess ap = new ActionProcess();
		ActionParams apa = new ActionParams();
		apa.setComand0(one);
		apa.setOneP(one);
		ap.setParams(apa);
		ResetContext.connectionTest(groupKey);
		ActionReady ar = ap.ox10_0x24(groupKey);
		int key = ar.getKey();
		send(ar);
		int i = 10;
		while (ActionContext.TempParams.containsKey(key) && i > 0) {
			if (i % 3 == 2) {
				send(ar);
			}
			i--;
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				logger.error("线程唤醒失败，" + e.getMessage(), e);
				throw new RuntimeException("重置失败！");
			}

		}

		return !ActionContext.TempParams.containsKey(key);
	};*/

	/***
	 * 重置结束 命令
	 */
	private boolean over() {
		ActionProcess ap = new ActionProcess();
		ActionParams apa = new ActionParams();
		apa.setGroupID(groupKey);
		apa.setOneP(one);
		apa.setOnePT(1);
		apa.setComand0(1);

		ap.setParams(apa);
		ActionContext.putValues(groupKey, apa);
		ActionReady ar = ap.ox10_0x25(groupKey);

		//int key = ar.getKey();
		send(ar);
	
		/*int i = 10;
		while (ActionContext.TempParams.containsKey(groupKey) && i > 0) {
			if (i % 3 == 2) {
				send(ar);
			}
			i--;
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				logger.error("线程唤醒失败，" + e.getMessage(), e);
				throw new RuntimeException("重置失败！");
			}

		}

		return !ActionContext.TempParams.containsKey(groupKey);*/
		
		return true;

	}
}
