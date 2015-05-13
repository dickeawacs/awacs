Ext.onReady(function(){
Ext.QuickTips.init();
//Ext.state.Manager.setProvider(new Ext.state.CookieProvider());
/************公共变量声明************/
var myinit;//初始化界面数据
var openTrades;//打开一个清单列表
var todayStore,todayRecord,todayCM;//今天列表的数据源
var beforStore,beforRecord,beforCM;//昨天列表的数据源
var tradeStore,tradeTable,tradeColumnModel;
var fileReadWin;//文件上传的界面
var datepath;//使用的url 集
var readFile;// 文件分析ajax
var msg,confirmsg;//消息提示框
var btools=new Ext.Toolbar();//主功能按钮
var btbtn1;//上传
var actionPlan;//业务主功能区
var ppgrid;//批次详情
var updateTradeStatus;//修改单笔状态
var tradeMenu;//汇款菜单
var controlMenu,beforMenu;//批次菜单 
var updateControlStatus;//修改批次 状态
var clickCotrolInfo;//批次点击自动装载信息
var actionWinSXF,actionWinFormSXF,tongJiSXF;//统计手续费
var contorlTabPanel;//批次纪录区
var tabChangeIndex;//当前tab的id;
/*状态字段，用于进度显示与状态标记（待办废弃）
上传：待办:  0  
废弃：不可上传分行   -5
上传分行：  5  ，
经办下载:   10
经办完成:   15 
复核下载：  20
复核完成：  25
授权下载：  30
授权完成：  35
授权发送：  45
备份完成：  50 */
var statustext={trade:{zhenchang:'正常',feiqi:'废弃'},
control:{t0:'待办',t5:'上传分行',t5p:'废弃',t10:'上传分行',t15:'经办完成',t20:'复核下载',t25:'复核完成',t30:'授权下载',t35:'授权完成',t45:'授权发送',t50:'备份完成'}}
var getCurrency=function(curNum){
	this.currencry=curNum;
	this.getVal=function(){
		var end='未定义'
		if(this.currencry=='001')end='人民币';
		else if(this.currencry=='002')end='';
		else if(this.currencry=='003')end='';
		else if(this.currencry=='004')end='';
		else if(this.currencry=='005')end='';
		else if(this.currencry=='014')end='美元';
		return end;
	};
}
var shangChuanFenHang;//可共用处理事件
var zhenPiShanChu;//删除批次及其下交易信息
var chaKanKeHuXinXi;//客户信息打印

datapath={	todayDataURL:		"batch!today.action",
			beforDataURL:		"batch!beforToday.action",
			fileUploadURL:		"file!struts2Ajax.action",
			fileReadURL:		'file!load.action',
			tradeDateURL:		'trade!tradeList.action',
			controlStatusFeiQiURL:'batch!feiqi.action',
			controlStatusHuiFuURL:'batch!huifu.action',
			controlDelAllURL:	'batch!delBatch.action',
			controlSend:		'batch!send.action',
			tradeStatusFeiQiURL:'trade!feiqi.action',
			tradeStatusHuiFuURL:'trade!huifu.action',
			quitURL:			'loginhhy!quit.action',
			printTCURL:			'print!p_toucun.action',
			customerList:		'customer!customers.action',
			printErrorList:		'',
			updatepwdURL:		'loginhhy!updatePwd.action'};

			
	
			
/**************公共方法----（由于它们是被引用 ，所以需要写在调用者前）-------*******/		
			
 
var mytool=function(){
	/***
	 *  return   年-月-日
	 */
	this.formatTime=function(obj){
	return (parseInt(obj.year)+1900)+"-"+ (parseInt(obj.month)+1)+"-"+obj.date;
	}
}
  /****
   * 上传分行
   */
  shangChuanFenHang=function(){
	var sm=todayRecord.getSelectionModel();
	//console.log(tgrid);
	var rowVal=sm.getSelected().get("field1");
	rowVal=Ext.util.Format.trim(rowVal);
			confirmsg('提示','是否确认将当前选中的数据发送至分行？',function(obj){
			if(obj=='yes'){
				Ext.Ajax.request({url:datapath.controlSend,params:{controlID:rowVal},
				success:function(response,options){
					var result = Ext.util.JSON.decode(response.responseText); 
					if(result.success){
						todayRecord.store.reload();//刷新列表
						beforRecord.store.reload();
					}else{
						msg('错误',result.message);
					}
				}
				});
			}
		});
}	
var getControlStatus=function(val,ts){
		//ts=ts||0;
		//alert(ts)
		var redtxt=0;
		val=Ext.util.Format.trim(val);
    	if(val=="-5") {redtxt=1;val=statustext.control.t5p;}
     	else if( val=='0') val=statustext.control.t0;
    	else if(val=="5")  val=statustext.control.t5;
    	else if(val=="10")  val=statustext.control.t10;
    	else if(val=="15")  val=statustext.control.t15;
    	else if(val=="20")  val=statustext.control.t20;
    	else if(val=="25")  val=statustext.control.t25;
    	else if(val=="30")  val=statustext.control.t30;
    	else if(val=="35")  val=statustext.control.t35;
    	else if(val=="45")  val=statustext.control.t45;
    	else if(val=="50")  val=statustext.control.t50;
    	else  val;
    	if(ts!=0){
    		if(redtxt==1)val="<span style='color:red;font-weight:bold;'>"+val+"</span>";
    		else val="<span style='color:green;font-weight:bold;'>"+val+"</span>";
    	}
    	return val;
}
/***
 * 事批删除
 */
zhenPiShanChu=function(){
	if(tabChangeIndex!='t-today')return;
	var sm=todayRecord.getSelectionModel();
	if(sm.getSelected()==null||sm.getSelected()=='undefined')return;
	var rowVal=sm.getSelected().get("field1");
	rowVal=Ext.util.Format.trim(rowVal);
			confirmsg('提示','是否确认将当前选中的数据删除？删除后将无法恢复！',function(obj){
			if(obj=='yes'){
				Ext.Ajax.request({url:datapath.controlDelAllURL,params:{controlID:rowVal},
				success:function(response,options){
					var result = Ext.util.JSON.decode(response.responseText); 
					if(result.success){
						todayRecord.store.reload();//刷新列表
						tradeTable.store.reload();
					}else{
						msg('错误',result.message);
					}
				}
				});
			}
		});
}
/**查看客户信息****/
chaKanKeHuXinXi=function(ref){
	var tempgrid,sted,cutid='';
	if(ref==null||ref=='undefined'){
		if(tabChangeIndex=='t-today'){
			tempgrid=todayRecord;
		}else if(tabChangeIndex=='t-befor'){
			tempgrid=beforRecord
		}else{
			return;
		}
		{
		 sted=tempgrid.getSelectionModel().getSelected();
		 if(sted!=null&&sted!='undefined'){
		 	cutid=sted.get("customerID");
		 }
		}
	}
var customertore=new Ext.data.Store({
       url:     datapath.customerList,
	   reader:	new Ext.data.JsonReader(
	   	{totalProperty:'total',root:'array',idProperty:'field1'},
		[{name:'field1'},{name:'customerName'}
		,{name:'customerCode'},{name:'customerNo'},{name:'customerTel'},{name:'blankInnerNo'}]),
		listeners :{      
                   load:function(){      
                   		if(cutid!=''){ 
							console.log(cutid);
							var trc=customertore.getById(cutid);
								console.log(trc)
								if(trc!=null&&trc!='undefined'){
                        			Ext.getCmp('customer-combo').setValue(cutid);      
									var vals={customerCode:trc.get("customerCode"),customerNo:trc.get("customerNo"),blankInnerNo:trc.get("blankInnerNo"),customerTel:trc.get("customerTel")};
									customerInfoPanel.getForm().setValues(vals);
								}
								
							}
                      }
		}
    });
	customertore.load();
	var cbobox=new Ext.form.ComboBox({ id:'customer-combo',fieldLabel:'客户名称', mode: 'local',autoLoad:true, triggerAction: 'all',store:customertore,valueField:'field1',displayField:'customerName'});
	var customerInfoPanel=new Ext.form.FormPanel({ 
						width: 500,frame: true,autoHeight: true,bodyStyle: 'padding: 10px 10px 0 10px;',labelWidth: 100,
			items:[cbobox,{xtype:'textfield', name:'customerCode' ,readOnly:true ,fieldLabel:'客户代码 '},
				        	{xtype:'textfield', name:'customerNo',readOnly:true ,fieldLabel:'客户帐号'},
							{xtype:'textfield', name:'blankInnerNo',readOnly:true ,fieldLabel:'银行内部帐号'},
							{xtype:'textfield', name:'customerTel',readOnly:true ,fieldLabel:'客户电话 '}],
				       		defaults: {anchor: '95%',msgTarget: 'side'},
			buttons: [{text: '打印此客户信息',iconCls:'print-ic',handler: function(){
				alert(cbobox.getValue());
				alert(cbobox.getRawValue());
				
				//window.open();
			}},{ text: '打印所有客户信息',iconCls:'print-ic',handler: function(){
			//window.open();
			}}]
				    });
	var customerInfoWin=new Ext.Window({title:'客户资料',floating:true,width:510,height:250,
	closable:true,layout:'fit', constrain:true,modal:true,items:[customerInfoPanel]});
				    
	customerInfoWin.show();
	cbobox.on("select",function(comboBox){
		var trc=customertore.getById(comboBox.getValue());
		if(trc!=null&&trc!='undefined'){
			var vals={customerCode:trc.get("customerCode"),customerNo:trc.get("customerNo"),blankInnerNo:trc.get("blankInnerNo"),customerTel:trc.get("customerTel")};
			customerInfoPanel.getForm().setValues(vals);
		}
	});
	   /*listeners :{      
9.                      load:function(){      
10.                        Ext.getCmp('test').setValue(<%=mer.getZoneId()%>);      
11.                      }*/
	
	if(cutid!=''){ 
		console.log(cutid);
		var trc=customertore.getById(cutid);
			console.log(trc)
			if(trc!=null&&trc!='undefined'){
				var vals={customerCode:trc.get("customerCode"),customerNo:trc.get("customerNo"),blankInnerNo:trc.get("blankInnerNo"),customerTel:trc.get("customerTel")};
				customerInfoPanel.getForm().setValues(vals);
			}
			
		}
};

/**调用文件分析方法 **/
readFile=function(excelID){
	Ext.Ajax.request({url:datapath.fileReadURL,params:{fileID:excelID},
	success:function(response,options){
		var result = Ext.util.JSON.decode(response.responseText); 
		if(result.success){
			msg('成功','处理完成');
		Ext.Msg.alert('成功','处理完成',function(){
			if(fileReadWin!=null)fileReadWin.close();
		})
			todayStore.reload();//刷新列表
		}else{
			msg('失败',result.message);
		}
	}
	});
};
 /***
     * title 提示标题
     * message 提示内容  
     */
msg = function(title, message){
        Ext.Msg.show({
            title: title,
            msg: message,
            minWidth: 300,
            modal: true,
            icon: Ext.Msg.INFO,
            buttons: Ext.Msg.OK
        });
    }
 //   Ext.m
confirmsg= function(title, message,fn){
        Ext.Msg.confirm(title,message,fn);
    }
 /**初始化加载**/
 myinit=function(){
        todayStore.load();
		beforStore.load();// 页面装载完后加载数据
		ppgrid.getColumnModel().setColumnWidth(0, 100);
		ppgrid.getColumnModel().setColumnWidth(1, 280);
 }
 
openTrades=function(controlkey,tabTip){
	tradeStore.baseParams.controlID = controlkey;
	tradeTable.title=tabTip;
    tradeStore.reload();
}
var dformat=function(v){
	if(v==null||v=='undefined')v="";
	return v;
}
clickCotrolInfo=function(tgrid){
	var sm=tgrid.getSelectionModel();
	var rowVal=sm.getSelected().get("field1");
	var cusCode=sm.getSelected().get("cusCode");	
	//console.log(sm);
 		ppgrid.setSource({
					 		"批次编号"   : dformat(sm.getSelected().get("controlBatchNo")),
 							"客户代码 "	: dformat(sm.getSelected().get("cusCode")),
                            "客户名称"	: dformat(sm.getSelected().get("dataSendName1")),
                            "客户帐号"	: dformat(sm.getSelected().get("dataSendAccount")),
                            "总笔数"		: dformat(sm.getSelected().get("totalRec")),
                            "总金额"		: dformat(sm.getSelected().get("totalAmt")),
                            "上传日期"	: dformat(new mytool().formatTime(sm.getSelected().get("dateRead"))) ,
                            "网点号"		: dformat(sm.getSelected().get("dataSbankNo")),
                            "状态"		: dformat(getControlStatus(sm.getSelected().get("status"),0))});
	
};
/****
 * 批次的状态更新
 */
updateControlStatus=function(tgrid,toVal,otherGrid){
	var myurl="";
	var sm=tgrid.getSelectionModel();
	//console.log(tgrid);
	var rowVal=sm.getSelected().get("field1");
	rowVal=Ext.util.Format.trim(rowVal);
	var status=sm.getSelected().get("status");
	status=Ext.util.Format.trim(status);
	
	if(toVal==status){
		msg('警告','数据状态为‘'+toVal+'’,无法操作!');
	}
	else if(toVal==status){
		msg('警告','数据状态为‘'+toVal+'’,无法操作!');
	}else if(toVal=='废弃'){
		myurl=datapath.controlStatusFeiQiURL;
	}else if(toVal=='恢复'){
		myurl=datapath.controlStatusHuiFuURL;
	}
	if(myurl!="") {
		confirmsg('提示','你确定需要'+toVal+'此数据吗？',function(obj){
			if(obj=='yes'){
				Ext.Ajax.request({url:myurl,params:{controlID:rowVal},
				success:function(response,options){
					var result = Ext.util.JSON.decode(response.responseText); 
					if(result.success){
						//msg('成功','处理完成');
						tgrid.store.reload();//刷新列表
						if(otherGrid!=null&&otherGrid!='undefined'){
							otherGrid.store.reload();
						}
					}else{
						msg(result.message);
					}
				}
				});
			}
		});
	}
}
/*****
 * 单比交易更新状态
 */
updateTradeStatus=function(tgrid,toVal){
	var myurl="";
	var sm=tgrid.getSelectionModel();
	var rowVal=sm.getSelected().get("field1");
	rowVal=Ext.util.Format.trim(rowVal);
	var status=sm.getSelected().get("field52");
	status=Ext.util.Format.trim(status);
	var lineAt=sm.lastActive;
	if(toVal==status){
		msg('警告','数据状态为‘'+toVal+'’,无法操作!');
	}else if(toVal=='废弃'){
		myurl=datapath.tradeStatusFeiQiURL;
	}else if(toVal=='恢复'){
		myurl=datapath.tradeStatusHuiFuURL;
	}
	if(myurl!="") {
		confirmsg('提示','你确定需要'+toVal+'此数据吗？',function(obj){
			if(obj=='yes'){
				Ext.Ajax.request({url:myurl,params:{tradeID:rowVal},
				success:function(response,options){
					var result = Ext.util.JSON.decode(response.responseText); 
					if(result.success){
						tgrid.store.getAt(lineAt).set("field52",toVal);
					}else{
						msg(result.message);
					}
				}
				});
			}
		});
	}
}
tongJiSXF=function(){
  actionWinFormSXF=new Ext.form.FormPanel({ 
	        			fileUpload: true,width: 500,frame: true,
				        autoHeight: true,bodyStyle: 'padding: 10px 10px 0 10px;',labelWidth: 100,
				        items:[{xtype:'textfield',fieldLabel:'网点号'},{xtype:'textfield',fieldLabel:'公司帐号'},
								{xtype:'textfield',fieldLabel:'公司代号'},{xtype:'textfield',fieldLabel:'起始日期'},
		{xtype:'textfield',fieldLabel:'截止日期'},{xtype:'textfield',fieldLabel:'批次号'},
		{xtype:'textfield',fieldLabel:'货币号'}],
				        defaults: {anchor: '95%',allowBlank: false,msgTarget: 'side'},
				        buttons: [{
				            text: '保存',
				            handler: function(){/*
				                if(fileuploadform.getForm().isValid()){
					                fileuploadform.getForm().submit({
					                    url: datapath.fileUploadURL,
					                    waitMsg: '正在上传文件...',
					                    success: function(fileuploadform, o){
					                    	msg("成功","上传成功,正在分析数据,请稍等......");//+o.result.returnVal
					                    	readFile(o.result.returnVal);
					                    },failure:function(fileuploadform, o){
					                    	msg("失败","上传失败!");
										}
					                });
				                }
				            */}
				        },{ text: '清空',handler: function(){actionWinFormSXF.getForm().reset();}}]
				    });
	 actionWinSXF=new Ext.Window({title:'统计手续费',floating:true,width:510,height:350,closable:true,layout:'fit', constrain:true,modal:true,items:[actionWinFormSXF]});
	 actionWinSXF.show();
	actionWinSXF.show();
}
/****************界面构建*********************/    

/**根据批次查看汇款信息**/
tradeMenu=new Ext.menu.Menu({id:'trade_Menu',
items:[//	{text:'查看客户信息',handler:chaKanKeHuXinXi ,iconCls:'customer-ic'},
		{text:'单笔手续费', iconCls :'jiSuan-ic' },
		{text:'废弃',iconCls:'feiqi-ic',handler:function(){updateTradeStatus(tradeTable,'废弃');}},
		{text:'恢复',iconCls:'huifu-ic',handler:function(){updateTradeStatus(tradeTable,'恢复');}}]
		});	
 tradeStore=new Ext.data.Store({
    	proxy: new Ext.data.HttpProxy({method: 'POST' ,url:datapath.tradeDateURL,params:{controlID:""}}),
	    reader:new Ext.data.JsonReader({totalProperty:'total',root:'array'},
	    [{name:'field1'},{name:'field52'},
	     {name:'dataRecvName1'},{name:'dataRecvAccount'},
	     {name:'dataAmt'},{name:'dataCurr'},{name:'dataRecvBankName1'},
	     {name:'cusCode',hidden:true}]) 
    });    
    tradeColumnModel=new Ext.grid.ColumnModel([
    {header:'序号',dataIndex:'field1',width:40},
    {header:'状态',dateIndex:'field52',width:60,renderer:function(val,cell,record,rowIndex,columnIndex,store){    	
    	if( Ext.util.Format.trim(val)=='正常')return "<span style='color:green;font-weight:bold;'>正常</span>";
    	else if(Ext.util.Format.trim(val)=="废弃") return "<span style='color:red;font-weight:bold;'>废弃</span>";
    	else return val;
    	}},
    {header:'收款人姓名',dataIndex:'dataRecvName1',width:100},
    {header:'收款人账号',dataIndex:'dataRecvAccount',width:100},
    {header:'付款金额',dataIndex:'dataAmt',width:100},
    {header:'付款货币',dataIndex:'dataCurr',width:80,renderer:function(val,cell,record,rowIndex,columnIndex,store){ 
    	return new getCurrency( Ext.util.Format.trim(val)).getVal(); }},
    {header:'收款人开户行',dataIndex:'dataRecvBankName1',width:200},
    {header:'客户代码' ,dataIndex:'cusCode',hidden:true}
    ]);
	tradeTable=new Ext.grid.GridPanel({
		width:300,
		height:450,
		title:'汇款明细',
	//	closable:true,
		store:tradeStore,
		cm:tradeColumnModel,
		sm:new Ext.grid.RowSelectionModel({singleSelect:true})
	}); 
	tradeTable.on("rowcontextmenu",function(grid,rowIndex,e){
		e.preventDefault();
		tradeTable.getSelectionModel().selectRow(rowIndex);
		tradeMenu.showAt(e.getXY());
	});
	

//功能按钮构建       开始............
btools.add('  ');
btbtn1=new Ext.Button({text:'<div style="width:55;height:55">&nbsp;&nbsp;</div>',scale:'large',tooltip:'上传批次文件',cls:'upload-btn',height:60,width:60, handleMouseEvents:false,
				handler:function(){ 
				var fileuploadform=new Ext.form.FormPanel({ 
				        			fileUpload: true,width: 550,frame: true,
							         autoHeight: true,bodyStyle: 'padding: 10px 10px 0 10px;',labelWidth: 50,
							        defaults: {anchor: '95%',allowBlank: false,msgTarget: 'side'},
							        items: [
							        	{xtype: 'fileuploadfield',id: 'form-file',
							        	emptyText: '选择文件',fieldLabel: '批次文件',
							        	name: 'file',
							            buttonText:'选择地图',buttonCfg: {iconCls: 'upload-icon'}}],
							        buttons: [{
							            text: '保存',
							            handler: function(){
							                if(fileuploadform.getForm().isValid()){
								                fileuploadform.getForm().submit({
								                    url: datapath.fileUploadURL,
								                    waitMsg: '正在上传文件...',
								                    success: function(fileuploadform, o){
								                    	//("成功","上传成功,正在分析数据,请稍等......");//+o.result.returnVal
								                    	   Ext.Msg.progress("提示",'上传成功,正在分析数据,请稍等...');
														readFile(o.result.returnVal);
								                    },failure:function(fileuploadform, o){
								                    	msg("失败","上传失败!");
													}
								                });
							                }
							            }
							        },{ text: '清空',handler: function(){fileuploadform.getForm().reset();}}]
							    });
				 fileReadWin=new Ext.Window({title:'上传批次文件',floating:true,width:510,height:150,closable:true,layout:'fit',bodyBorder:false, constrain:true,modal:true,items:[fileuploadform]});
					fileReadWin.show();
				}
		});
btools.add(btbtn1);
btools.add(' ');btools.add(' ');btools.add(' ');
btbtn4=new Ext.Button({cls:'send-btn',height:60,width:60,text:'<div style="width:55;height:55">&nbsp;&nbsp;</div>',scale:'large',handleMouseEvents:false,
tooltip:'上传分行',
handler:shangChuanFenHang});
btools.add(btbtn4); 
btools.add(' ');
btbtn2=new Ext.Button({text:'<div style="width:55;height:55">&nbsp;&nbsp;</div>',scale:'large',tooltip:'打印错报清单' ,handleMouseEvents:false,height:60,width:60,cls:'print-btn',handler:function(){
window.open(datapath.printErrorList);
	
}});
btools.add(btbtn2);
btools.add(' ');
/*btbtn3=new Ext.Button({text:'打印头寸',icon:"http://4.su.bdimg.com/icon/2563.png",height:30,handler:function(){
 
//todayRecord.

	var sm=todayRecord.getSelectionModel();
	//console.log(tgrid);
	var rowVal=sm.getSelected().get("field1");
	//alert(rowVal);
	rowVal=Ext.util.Format.trim(rowVal);
	
	
	if(null==rowVal||""==rowVal){
	
	}
	else {
			//alert(datapath.printTCURL+"?controlID="+rowVal);
		 window.open(datapath.printTCURL+"?controlID="+rowVal);
		}
	
}});
btools.add(btbtn3); */
//btools.add(' ');

btbtn5=new Ext.Button({
text:'<div style="width:55;height:55">&nbsp;&nbsp;</div>',scale:'large',
	tooltip:'统计手续费',
	handleMouseEvents:false,
 	cls:'chart-btn',
	height:60,
	width:60,
	handler:function(){tongJiSXF();}
});
btools.add(btbtn5); 
btools.add(' ');
btools.add(' ');
btbtn6=new Ext.Button({
	 handleMouseEvents:false, 
	 text:'<div style="width:55;height:55">&nbsp;&nbsp;</div>',scale:'large',
	tooltip:"整批删除",
	//labelStyle:'witdh:60px;height:60px;',
	 cls:'del-all-btn',
	width:60,
	height:60,
	handler:zhenPiShanChu
});
btools.add(btbtn6); 
btools.add(' ');btools.add(' ');btools.add(' ');
btbtn7=new Ext.Button({text:'<div style="width:55;height:55">&nbsp;&nbsp;</div>',scale:'large',tooltip:'查看客户信息',handleMouseEvents:false ,iconCls:'see-ic',	height:60,width:60,cls:'customer-btn', handler:chaKanKeHuXinXi});
btools.add(btbtn7); 
/*btools.add('-');
btbtn8=new Ext.Button({	text:'整批恢复  ',	icon:"http://4.su.bdimg.com/icon/2563.png",	height:30});
btools.add(btbtn8); */
btools.add('->');
btools.add(new Ext.Button({	text:'<div style="width:55;height:55">&nbsp;&nbsp;</div>',tooltip:'修改密码',handleMouseEvents:false, cls:'setting-btn',width:60,height:60,handler:function(){

  actionWinFormSXF=new Ext.form.FormPanel({ 
	        			fileUpload: true,width: 500,frame: true,
				        autoHeight: true,bodyStyle: 'padding: 10px 10px 0 10px;',labelWidth: 100,
				        items:[{xtype:'textfield',name:'name',fieldLabel:'柜员号'},
				        	{xtype:'textfield',name:'pwd',fieldLabel:'原始密码', inputType:"password"},
							{xtype:'textfield',name:'newPwd',fieldLabel:'新密码',   inputType:"password"},
							{xtype:'textfield',name:'confirmPwd',fieldLabel:'确认密码', inputType:"password"}/*,
							{xtype:'datefield',name:'endTime',fieldLabel:'截止日期'}*/],
				        defaults: {anchor: '95%',allowBlank: false,msgTarget: 'side'},
				        buttons: [{
				            text: '保存',
				            handler: function(){ 
				                if(actionWinFormSXF.getForm().isValid()){
					                actionWinFormSXF.getForm().submit({
					                    url: datapath.updatepwdURL,
					                    success: function(f, o){
					                    	 Ext.Msg.alert("成功",o.result.message,function(){
					                    	 actionWinSXF.close();
					                    	 });
					                    },failure:function(f, o){
											Ext.Msg.alert("失败",o.result.message);
										}
					                });
				                }
				            }
				        },{ text: '清空',handler: function(){actionWinFormSXF.getForm().reset();}}]
				    });
	 actionWinSXF=new Ext.Window({title:'修改柜员密码',floating:true,width:510,height:200,closable:true,layout:'fit', constrain:true,modal:true,items:[actionWinFormSXF]});
	 actionWinSXF.show();
	 //actionWinSXF.show();

	
}})); 
btools.add(' ');
btools.add(new Ext.Button({	text:'<div style="width:55;height:55">&nbsp;&nbsp;</div>',scale:'large',tooltip:'退出系统',handleMouseEvents:false, cls:'close-btn',width:60,height:60,handler:function(){
	confirmsg('提示','您确定要退出本系统？',function(obj){
	//window.location.href="";
		if(obj=='yes')
		Ext.Ajax.request({url:datapath.quitURL,
		success:function(response,options){
			//alert(response.responseText);
			var result = Ext.util.JSON.decode(response.responseText); 
			//alert(result.success);
			if(result.success){
				window.location.href="";
			}else{
				msg(result.message);
			}
		}
		});

		
	})
	
}})); 
btools.add(new Ext.Toolbar.Spacer());
btools.add('-');
btools.add(new Ext.Toolbar.Spacer());
actionPlan=new Ext.Panel({layout:'fit',height:65,items:[btools]});
var growthColumn = new Ext.ux.grid.ProgressColumn({
    header : "进度",
    dataIndex : 'status',
    width : 85,
    textPst : '%',
    colored : true 
  });
todayCM=new Ext.grid.ColumnModel([new Ext.grid.CheckboxSelectionModel(),
    	{header:'批次编号',dataIndex:'controlBatchNo',width:150,renderer:function(val){return"<img src='pub/images/btn/2579.png'/>"+val;}},
    	growthColumn,
    	{header:'状态',dataIndex:'status',width:70,renderer:getControlStatus},
    	{header:'上传日期',width:80,dataIndex:'dateRead',renderer:function(v){return new mytool().formatTime(v);}},
    	{header:'客户序号',dataIndex:'customerID',hidden:true},
    	{header:'客户代码',dataIndex:'cusCode',hidden:true},
    	{header:'客户名称',dataIndex:'dataSendName1',hidden:true},
    	{header:'客户帐号',dataIndex:'dataSendAccount',hidden:true},
    	{header:'总笔数',dataIndex:'totalRec',hidden:true},
    	{header:'总金额',dataIndex:'totalAmt',hidden:true},
    	{header:'网点号',dataIndex:'dataSbankNo',hidden:true},
    	{header:'序号',dataIndex:'field1', hidden : true}]);
       todayStore=new Ext.data.Store({
   		proxy: new Ext.data.HttpProxy({method: 'POST' ,url:datapath.todayDataURL}),
		reader:new Ext.data.JsonReader({totalProperty:'total',root:'array'},
		[{name:'controlBatchNo'},{name:'status'},{name:'customerID'}
		,{name:'cusCode'},{name:'dataSendName1'}
		,{name:'dataSendAccount'},{name:'totalRec'}
		,{name:'totalAmt'},
		{name:'dateRead'},
		{name:'dataSbankNo'},{name:'field1'}]) 
    });
 todayRecord=new Ext.grid.GridPanel({
 	id:'todayID',
	width:480,
	border:false,
	autoHeight:true,
	store:todayStore,
	cm:todayCM,
	plugins : [ growthColumn],
	sm:new Ext.grid.RowSelectionModel({singleSelect:true})
});
    
controlMenu=new Ext.menu.Menu({id:'control_Menu',items:[
	{text:'查看清单' ,iconCls:'see-ic',
	handler:function(item,checked){
		//alert(0);
			var sm=todayRecord.getSelectionModel();
			var rowVal=sm.getSelected().get("field1");
			var batchNo=sm.getSelected().get("controlBatchNo");
			//msg('tip',' index:'+rowVal);
			openTrades(rowVal,batchNo);
	}},
	{text:'上传分行',handler:shangChuanFenHang,iconCls:'send-ic'},
	{text:'打印错报清单',iconCls:'print-ic'},
	{text:'查看客户信息',handler:chaKanKeHuXinXi ,iconCls:'customer-ic'},
	{text:'打印汇款头寸',iconCls:'print-ic'},
	{text:'统计手续费',handler:tongJiSXF, iconCls :'jiSuan-ic'},
	{text:'废弃',iconCls:'feiqi-ic',
		handler:function(){
			updateControlStatus(todayRecord,'废弃',beforRecord);
	}},
	{text:'恢复',iconCls:'huifu-ic',
		handler:function(){
		updateControlStatus(todayRecord,'恢复');
	}},
	{text:'整批删除',iconCls:'del-all-ic', handler:zhenPiShanChu}
]});

todayRecord.on("rowcontextmenu",function(grid,rowIndex,e){
	e.preventDefault();
	todayRecord.getSelectionModel().selectRow(rowIndex);
	controlMenu.showAt(e.getXY());
});
todayRecord.on("click",function(one){
	clickCotrolInfo(todayRecord);
});

        
        

/****
 * 昨天的操作纪录 
 * 
 */

	
beforCM=new Ext.grid.ColumnModel([new Ext.grid.CheckboxSelectionModel(),
    	{header:'批次编号',dataIndex:'controlBatchNo',width:150,renderer:function(val){return"<img src='pub/images/btn/2579.png'/>"+val;}},
    	growthColumn,
    	{header:'状态',dataIndex:'status',width:70,renderer:getControlStatus},
    	{header:'上传日期',width:80,dataIndex:'dateRead',renderer:function(v){return new mytool().formatTime(v);}},
    	{header:'客户序号',dataIndex:'customerID',hidden:true},
    	{header:'客户代码',dataIndex:'cusCode',hidden:true},
    	{header:'客户名称',dataIndex:'dataSendName1',hidden:true},
    	{header:'客户帐号',dataIndex:'dataSendAccount',hidden:true},
    	{header:'总笔数',dataIndex:'totalRec',hidden:true},
    	{header:'总金额',dataIndex:'totalAmt',hidden:true},
    	{header:'网点号',dataIndex:'dataSbankNo',hidden:true},
    	{header:'序号',dataIndex:'field1', hidden : true}]);
beforStore=new Ext.data.Store({
   		proxy: new Ext.data.HttpProxy({method: 'POST' ,url:datapath.beforDataURL}),
		reader:new Ext.data.JsonReader({totalProperty:'total',root:'array'},
		[{name:'controlBatchNo'},{name:'status'},{name:'customerID'}
		,{name:'cusCode'},{name:'dataSendName1'}
		,{name:'dataSendAccount'},{name:'totalAmt'}
		,{name:'totalRec'},
		{name:'dateRead'}
		,{name:'dataSbankNo'},{name:'field1'}]) 
    });
  beforMenu=new Ext.menu.Menu({id:'befor_Menu',items:[ {text:'查看清单' ,iconCls:'see-ic',
handler:function(item,checked){
	//alert(5);
	var sm=beforRecord.getSelectionModel();
	var rowVal=sm.getSelected().get("field1");
	var batchNo=sm.getSelected().get("controlBatchNo");
	//msg('tip',' index:'+rowVal);
	openTrades(rowVal,batchNo);
}},{text:'打印错报清单',iconCls:'print-ic'},{text:'查看客户信息',iconCls:'customer-ic', handler:chaKanKeHuXinXi},{text:'打印汇款头寸',iconCls:'print-ic'},{text:'统计手续费', iconCls :'jiSuan-ic',handler:tongJiSXF}
/*,{text:'废弃',handler:function(){
	updateControlStatus(beforRecord,'废弃');
}},{text:'恢复',handler:function(){
	updateControlStatus(beforRecord,'待办');
}}*/
]});

beforRecord=new Ext.grid.GridPanel({
	width:480, 
	autoHeight:true,
	store:beforStore,
	border:false,
	cm:beforCM,
	plugins:[growthColumn],
	sm:new Ext.grid.RowSelectionModel({singleSelect:true})
});
beforRecord.on("click",function(one){
	clickCotrolInfo(beforRecord);
});
beforRecord.on("rowcontextmenu",function(grid,rowIndex,e){
	e.preventDefault();
	beforRecord.getSelectionModel().selectRow(rowIndex);
	beforMenu.showAt(e.getXY());
});
        /***************************************************/
ppgrid= new Ext.grid.PropertyGrid({
                    	id:'ppgrid',
                        title: '批次信息',
                        //closable: true,
                        anchor:'-1 45%',
                       // height:380,
                        source: {
                        	"批次编号"  : "",
                            "客户代码 "	: "",
                            "客户名称"	: "",
                            "客户帐号"	: "",
                            "上传日期"	: "",
                            "网点号"		: "",
                            "总笔数"		: "",
                            "总金额"		: "",
                            "状态"		: ""
                        }
                    });
 
     /**左侧的列表区***/               
 contorlTabPanel= new Ext.TabPanel({
                	id:'page-tab',
                    border: false, // already wrapped so don't add another border
                    activeTab: 0, // second tab initially active
                    tabPosition: 'top',
                    anchor:'-0 55%',
                  //  height:350,
                    items: [{
                        title: '当前',
                        id:'t-today',
                        autoScroll: true,
                        items: todayRecord
                        
                        
                    },{
                        title: '历史',
                        id:'t-befor',
                        autoScroll: true,
                        items: beforRecord
                        
                    }]
                }) 
        contorlTabPanel.on('tabchange',function(thePanel,newCard, oldCard){
       	tabChangeIndex=newCard.id;
       // console.log(newCard);
        
        
        });
var viewport = new Ext.Viewport({
            layout: 'border',
            items: [{
                region: 'north',
               // title:'汇划易系统',
                //title:'${appInfo.webAppName}',
                split: true,
                collapsible: false,
                margins: '0 0 0 0',
                height:65, // give north and south regions a height
                items:[actionPlan]
            }, {
                region: 'south',
                contentEl: 'south',
                split: true,
                height: 100,
                minSize: 100,
                maxSize: 200,
                collapsible: true,
                title: '信息',
                margins: '0 0 0 0'
            }/*, {
                region: 'east',
                collapsible: true,
                split: true,
                width: 225,  
                minSize: 175,
                maxSize: 400,
                margins: '0 5 0 0',
                layout: 'fit',  
                items: 
                new Ext.TabPanel({
                    border: false, 
                    activeTab: 0, 
                    tabPosition: 'bottom',
                    items: [hkgrid]
                })
            }*/
            , {
                region: 'west',
                id: 'west-panel', // see Ext.getCmp() below
                title: '操作纪录',
                split: true,
                collapsible: true,
                width: 500, // give east and west regions a width
                minSize: 175,
                maxSize: 700,
                margins: '0 0 0 0',
                layout: 'anchor', // specify layout manager for items
                items: [           // this TabPanel is wrapped by another Panel so the title will be applied
                contorlTabPanel,ppgrid]
            },
            new Ext.TabPanel({
                region: 'center', // a center region is ALWAYS required for border layout
                deferredRender: false,
                activeTab: 0,     // first tab initially active
                items: [tradeTable]
            })]
        });

     /*   Ext.get("hideit").on('click', function(){
            // get a reference to the Panel that was created with id = 'west-panel' 
            var w = Ext.getCmp('west-panel');
            // 在折叠属性状态的基础上，展开或折叠该面板
            w.collapsed ? w.expand() : w.collapse();
        });*/
   myinit();
   
    });