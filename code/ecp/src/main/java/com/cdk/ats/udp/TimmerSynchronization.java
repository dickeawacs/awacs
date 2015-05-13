package com.cdk.ats.udp;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.cdk.ats.udp.cache.AtsEquipmentCache;
import com.cdk.ats.udp.operating.Operating_0x30;
import com.cdk.ats.udp.process.ActionReady;
import com.cdk.ats.udp.transmitter.TransmitterContext;
import com.cdk.ats.udp.utils.Constant;
import com.cdk.ats.web.pojo.hbm.Table10;

public class TimmerSynchronization {
	private static TimmerSynchronization timer;
	private Operating_0x30 op0x03;
	private int today=-1;
	private int hour_in_day=12;
	
	private TimmerSynchronization() {
		op0x03=new Operating_0x30();
	}

	public static  TimmerSynchronization getinstance() {
		if (timer == null) {
			timer = new TimmerSynchronization();
		}
		return timer;

	}

	/***
	 * 
	 * 描述：判断today不是当前天，且刚好是这个时辰，就同步时间 ，同步完成后，且将today变为当前日期
	 * @createBy dingkai
	 * @createDate 2014-4-7
	 * @lastUpdate 2014-4-7
	 */
	public void submitTime(){ 
		Calendar cal = Calendar.getInstance();
		 int hourOfDay=cal.get(Calendar.HOUR_OF_DAY); 
		 int todayVal=cal.get(Calendar.DAY_OF_MONTH);
		 if(todayVal!=today&&hourOfDay==this.hour_in_day){
			List<Table10> t10s=AtsEquipmentCache.getEquipmentFirst();
			if(t10s!=null&&t10s.size()>0){
				List<ActionReady> readys=new ArrayList<ActionReady>();
				for (Table10 table10 : t10s) {
					System.out.println(table10.getField3()+"-"+table10.getFip()+"-"+table10.getFport());
					if(Integer.valueOf(Constant.EQUIPMENT_STATUS_NORMAL_VAL).equals(table10.getField130())&&table10.getFip()!=null&&table10.getFport()!=null){
						ActionReady temp=op0x03.ox30_0x02(table10.getField3());
						Constant.getInstance().bindTarget(table10, temp);
						readys.add(temp);
					}
				}
				if(readys.size()>0){
					TransmitterContext.pushGroupDatagram(readys);
					today=todayVal;
				}
			}
			 
		 }
	 
	 
	}
	 
}
