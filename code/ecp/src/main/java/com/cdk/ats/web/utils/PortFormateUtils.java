package com.cdk.ats.web.utils;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.MethodUtils;
import org.apache.commons.lang.StringUtils;

import com.cdk.ats.udp.utils.CommandTools;
import com.cdk.ats.web.pojo.hbm.EquipmentPort;
import com.cdk.ats.web.pojo.hbm.Table10;

public class PortFormateUtils {

	/***
	 * 
	 * 描述：获取设备的输出端口
	 * @createBy dingkai
	 * @createDate 2014-2-16
	 * @lastUpdate 2014-2-16
	 * @param t10
	 * @return
	 */
	public static List<EquipmentPort> getOutPorts(Table10 t10) {
		List<EquipmentPort> ports = new ArrayList<EquipmentPort>();
		int count = getOutPutCount(t10.getDtype());
		if (count > 0) {
			int field = 112;
			int portfield=154;
			for (int j = 1; j <= count; j++) {
				try {
					Object [] objs=new Object[]{};
					//get equipment out port name
					String name ="空";
					Object tempName=  MethodUtils.invokeExactMethod(t10,
							"getField" + (field + j), objs);
					if (tempName!=null&&StringUtils.isNotBlank(tempName.toString())) name = (String) tempName;
					else{
						name="输出"+j;
					}
					//get equipment out port state  (between 155 and 162)
					Integer state=0;
					Object tempState= MethodUtils.invokeExactMethod(t10,
							"getField" + (portfield + j), objs);
					if(tempState!=null&&StringUtils.isNotBlank(tempState.toString())
							&&(tempState.toString().equals("1")))state=(Integer) tempState; 
					else{
						MethodUtils.invokeExactMethod(t10,
								"setField" + (portfield + j), new Object[]{0});
					}
					
					ports.add(new EquipmentPort(j,t10.getField3(), t10
							.getField4(), j, 2, state, name,"输出端口"+j));
				} catch (NoSuchMethodException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				}
			}

		}
		return ports;

	}
	/***
	 * 
	 * 描述：获取设备的输入端口
	 * @createBy dingkai
	 * @createDate 2014-2-16
	 * @lastUpdate 2014-2-16
	 * @param t10
	 * @return
	 */
	public static List<EquipmentPort> getInPorts(Table10 t10) {
		List<EquipmentPort> ports = new ArrayList<EquipmentPort>();
		int count = getInPutCount(t10.getDtype());
		if (count > 0) {
			int field = 104;
			int portfield=154;
			for (int j = 1; j <= count; j++) {
				try {
					Object [] objs=new Object[]{};
					//get equipment out port name
					String name ="空";
					Object tempName=  MethodUtils.invokeExactMethod(t10,
							"getField" + (field + j), objs);
					if (tempName != null&&!tempName.equals("")) name = (String) tempName;
					else{
						name="输入"+j;
					}
					//get equipment out port state  (between 155 and 162)
					Integer state=0;
					Object tempState= MethodUtils.invokeExactMethod(t10,
							"getField" + (portfield + j), objs);
					if(tempState!=null)state=(Integer) tempState; 
					
					ports.add(new EquipmentPort(j,t10.getField3(), t10
							.getField4(), j, 2, state, name,"输入端口"+j));
				} catch (NoSuchMethodException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				}
			}

		}
		return ports;

	}
	/***
	 * 
	 * 描述： 查询出有几个输入端口 
	 * @createBy dingkai
	 * @createDate 2014-1-11
	 * @lastUpdate 2014-1-11
	 * @param type
	 * @return
	 */
	public static int getInPutCount(Integer type) {
		if(type==null)return 0;
		int[] typeBit=CommandTools.getPortCountByType(type.intValue());
		return typeBit[0];
	}
	/***
	 * 
	 * 描述： 查询出有几个输出端口 
	 * @createBy dingkai
	 * @createDate 2014-1-11
	 * @lastUpdate 2014-1-11
	 * @param type
	 * @return
	 */
	public static int getOutPutCount(Integer type) {
		if(type==null)return 0;
		int[] typeBit=CommandTools.getPortCountByType(type.intValue());
		return typeBit[1];
	}
	/***
	 * 
	 * 描述： 根据设备类型，转换出对应的设备有多少个输出与输入 
	 * @createBy dingkai
	 * @createDate 2014-2-16
	 * @lastUpdate 2014-2-16
	 * @param type
	 * @return [0=输入个数，1=输出个数]
	 */
	public static int[] getPortCount(Integer type) {
		if(type==null)return new int[]{0,0};
		int[] typeBit=CommandTools.getPortCountByType(type.intValue());
		return typeBit;
	}
}
