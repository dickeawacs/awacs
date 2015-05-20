var winPanel, SettingForm,bottomForm,firstDeviceStore;
//主窗口 ，         上级表单   下级表单
var datapath={	
		queryFirstInfoURL:'editQ!getSys.action',
		queryFirstDevice:'cache/firstStore.action',
		saveSettingURL:'admin!saveSysSetting.action'
		//querydeviceTreeURL:"editQ!devices.action", 
		//querySecondDevice:'editQ!second.action',
		//saveDeviceURL:'admin!saveDevice.action'
		};
Ext.onReady(function(){	
	Ext.QuickTips.init();
	firstDeviceStore=new  Ext.data.Store({
								      proxy:new Ext.data.HttpProxy({ url: datapath.queryFirstDevice}),
									   reader:	new Ext.data.JsonReader(
									   	{totalProperty:'total',root:'array',idProperty:'field3'},
										[{name:'field3'}//设备地址
										,{name:'field121'}//设备名称
										,{name:'field130'}//禁用设备 屏蔽
										,{name:'field5'}
										,{name:'field6'}
										,{name:'field7'}
										,{name:'field8'}
										,{name:'field9'}
										,{name:'field10'}])
								    }); 	
	SettingForm=new Ext.FormPanel({
	id:'setingForm',
	title:'设备设置',
	renderTo:'setting', 
	frame:true,
	layout:'form',
	padding:'5 0 5 0',
	defaults:{labelAlign:'right'},
	items:[new Ext.form.FieldSet({border:true,items:[{id:'firstDeviceCombo',hiddenName:'firstDevice',xtype:'combo',fieldLabel:'一层设备',  width:140,emptyText:'请选择设备'
	,store:firstDeviceStore,triggerAction:'all',editable:false,valueField:'field3',displayField:'field121',
							  listeners:{
									  select:function(combo,record,index){
									  Ext.Ajax.request({
										url:datapath.queryFirstInfoURL,
										params:{"firstLayerID":this.value},
										success: function(a,b){
											   	   var b = Ext.util.JSON.decode(a.responseText);                       
													if(b.success){
														var first=b.returnVal; 
														if(first!=null&&first!=""){
															SettingForm.getForm().findField("num1").setValue(first.telNum1);
															SettingForm.getForm().findField("num2").setValue(first.telNum2);
															SettingForm.getForm().findField("tels").setValue(first.telSpace);
															SettingForm.getForm().findField("msgs").setValue(first.messageSpace);
															
															/*SettingForm.getForm().findField("num1dis").setValue(first.telNum1==""?true:false);
															SettingForm.getForm().findField("num2dis").setValue(first.telNum2==""?true:false);*/
															SettingForm.getForm().findField("telsdis").setValue(first.disableTel=="1"?true:false);
															SettingForm.getForm().findField("msgsdis").setValue(first.disableMessage=="1"?true:false);
															
															
															if(first.printSet!=""){
															var str=(first.printSet+"").split(",");
															if(str.length!=4)str=["0","0","0","0"];
															SettingForm.getForm().findField("printdis").setValue(str[0]=="1"?true:false);
															SettingForm.getForm().findField("content1").setValue(str[1]=="1"?true:false);
															SettingForm.getForm().findField("content2").setValue(str[2]=="1"?true:false);
															SettingForm.getForm().findField("content3").setValue(str[3]=="1"?true:false);
															 
															
															}
														}//else SettingForm.getForm().reset();
													}else{
														Ext.Msg.alert('提示',b.message);
													}
											   	},failure:function(a){
											   		Ext.Msg.alert("提示","与服务器连接失败");
										
										}
										});
									  }  
								}   
							}]}),
			new Ext.form.FieldSet({name:'otherInfo',title:'接警设置',layout:'column',border:true,items:[new Ext.form.FieldSet({border:false,width:300,defaults:{labelAlign:'right',labelStyle:"width:120px"},items:[
			{xtype:'numberfield',hiddenName:'tels',name:'tels',fieldLabel:'电话通迅间隔(分钟)',maxLength:5,minValue:0},
			{xtype:'numberfield',hiddenName:'msgs',name:'msgs',fieldLabel:'短信通迅间隔(分钟)',maxLength:5,minValue:0},
			{xtype:'textfield',hiddenName:'num1',name:'num1',fieldLabel:'接警中心号码一',maxLength:16,regex: /^[0-9]{1,16}$/,regexText:'最长不可超过16位!'},
			{xtype:'textfield',hiddenName:'num2',name:'num2',fieldLabel:'接警中心号码二',maxLength:16,regex: /^[0-9]{1,16}$/,regexText:'最长不可超过16位!'}
		
			]})
			,new Ext.form.FieldSet({border:false,width:300,items:[
/*			{xtype:'checkbox',name:'num1dis',boxLabel:'禁止',inputValue:'1'},
			{xtype:'checkbox',name:'num2dis',boxLabel:'禁止',inputValue:'1'},*/
			{xtype:'checkbox',name:'telsdis',boxLabel:'电话通迅禁止',inputValue:'1'},
			{xtype:'checkbox',name:'msgsdis',boxLabel:'短信通迅禁止',inputValue:'1'} 
			]})
			]}),
			new Ext.form.FieldSet({id:'pintInfo',title:'打印设置（禁止/开启）',titleCollapse:true,border:true,items:[
			{xtype:'checkbox',name:'printdis',boxLabel:'打印机禁止',inputValue:'1',listeners:{check:function(){
				if(this.checked)
					 Ext.getCmp("printContentSet").hide();
				else Ext.getCmp("printContentSet").show();
			}}}	
			,new Ext.form.FieldSet({id:'printContentSet',border:false,title:'打印内容',border:true,items:[
			{xtype:'checkbox',name:'content1',boxLabel:'报警事件',inputValue:'1'},
			{xtype:'checkbox',name:'content2',boxLabel:'普通输入事件',inputValue:'1'},
			{xtype:'checkbox',name:'content3',boxLabel:'用户操作事件',inputValue:'1'}
			]})
			]})
			],buttonAlign:'center',buttons:[{text:'保存',handler:function(){
			if(hasDevice()){
			if(SettingForm.getForm().isValid()){
		  	 	pingBi();
				  SettingForm.getForm().submit({ url: datapath.saveSettingURL,
						                    success: function(f, o){
												if(o.result.code==300){
													refreshUtils(o.result.returnVal,null,function(){
														Ext.Msg.alert('提示','操作成功！'); 
													});
												}else if(o.result.code>=900){
													quXiaoPingBi();
												 	Ext.Msg.alert('提示',o.result.message);
												}
						                    },failure:function(f, o,x){
						                    	quXiaoPingBi();
												Ext.Msg.alert("失败",(o.result?o.result.message:"访问服务器失败！"));
											}
						                });
		}else Ext.Msg.alert("提示","数据格式不合法,请重新输入！ &nbsp;");
			}else Ext.Msg.alert("警告","请选择需要操作的二层设备,否则无法执行操作！") 
			 
		}}]
	});		
 	//  在formpanel中设置labelWidth,labelAlign,labelPad,labelSeparator,labelStyle 
/*	bottomForm=new Ext.FormPanel({
	region:'center',
	layout:'form',
	frame:true,
	padding:'5 0 5 0',
	defaults:{border:false,margins:'5 5 5 5'},
	items:[{xtype:'checkbox',fieldLabel:'打印机禁止'},
			{xtype:'checkbox',fieldLabel:'打印机打印内容'},
			{xtype:'checkbox',fieldLabel:'报警事件'},
			{xtype:'checkbox',fieldLabel:'普通输入事件'},
			{xtype:'checkbox',fieldLabel:'用户操作事件'}
		  ],
	buttons:[{text:'保存'}]
		 
	});		  */
	 
	
});
var hasDevice=function(){

return Ext.getCmp("firstDeviceCombo").getValue()!="";
}