var datapath={	
		querySecondDevice:'',//'editQ!second.action',
		//queryFirstDevice:'sysManagement!first.action',
		queryFirstDevice:'cache/firstStore.action',
		setOneURL:"sysManagement!resetOne.action",
		reLoginPwdUrl:"sysManagement!reLoginAdmin.action",
		setAllURL:""//"sysManagement!resetAll.action"
		};
Ext.onReady(function(){	
	var  firstDeviceStore=new  Ext.data.Store({
								      proxy:new Ext.data.HttpProxy({ url: datapath.queryFirstDevice}),
									   reader:	new Ext.data.JsonReader(
									   	{totalProperty:'total',root:'array',idProperty:'field3'},
										[{name:'field3'}//设备地址
										,{name:'field121'}//设备名称
										])
										,listeners:{
						        	load:function(){/*
						        		if(myevent=="sf")//当二层设备修改后，需要刷新一层设备，此时需要默认选中之前的一层设备
						        		{
						        		  Ext.getCmp("firstDeviceCombo").setValue(fselect);
										  Ext.getCmp("firstDeviceCombo").hiddenField.value=fselect;
						        		}
									  */}
						        }
								    }); 	
var secondDeviceStore=new Ext.data.Store({proxy:new Ext.data.HttpProxy({ url: datapath.querySecondDevice}),
									   reader:	new Ext.data.JsonReader(
									   	{totalProperty:'total',root:'array',idProperty:'field4'},
										[{name:'field4'}//设备地址
										,{name:'field121'}//设备名称						
										])
	,listeners:{load:function(store,record,opts){
		 Ext.getCmp("secondDeviceCombo").setValue("空");
		 Ext.getCmp("secondDeviceCombo").hiddenField.value=""; 
		/*
		 Ext.getCmp("secondDeviceCombo").setValue(record[0].data.field4);
		 Ext.getCmp("secondDeviceCombo").hiddenField.value=record[0].data.field4;
	*/}
	}
	});
	
	
	
	var top=new Ext.FormPanel({
		//renderTo:"topView",
		id:"leftform",
		title:'重置指定设备',
		height:200,
		width:400,
		frame:true,
		collapsible:false,
		//html:'',
		items:[{id:'firstDeviceCombo',hiddenName:'firstDevice',xtype:'combo',fieldLabel:'一层设备',  width:140,emptyText:'请选择设备'
	,store:firstDeviceStore,triggerAction:'all',editable:false,valueField:'field3',displayField:'field121'//,
							 /* listeners:{//
									  select:function(combo,record,index){  
										   try{  
										 	    var userCombo = Ext.getCmp("secondDeviceCombo");  
										 		 userCombo.store.removeAll();  
											 	 userCombo.store.load({params:{"firstDevID":this.value}});
											   } catch(ex){
									 			  Ext.MessageBox.alert("错误","数据加载失败。");  
									 		  } 
									  }  
								}   */
							}/*,{id:'secondDeviceCombo',hiddenName:'secondDevice',xtype:'combo',fieldLabel:'二层设备',triggerAction:'all',mode:'local',width:140,emptyText:'选择二层设备',editable:false,
								store:secondDeviceStore,valueField:'field4',displayField:'field121',
								    listeners:{ 
								    	
								    }
							}*/,{html:'请注意：如果您执行了“重置”操作，那么被选择中的设备的相关数据将被设置为初始值'}],
		buttons:{buttonAlign:'center',items:[{text:'重置设备',handler:function(){
			
			var f_d=Ext.getCmp("firstDeviceCombo").hiddenField.value; 
			var f_d_r=Ext.getCmp("firstDeviceCombo").getRawValue();
			//var s_d=Ext.getCmp("secondDeviceCombo").getRawValue(); 
	    	//var s_d_r=Ext.getCmp("secondDeviceCombo").hiddenField.value; 
			var tipMsg="",isvalid=false;
			
			  if(!Ext.isEmpty(f_d_r) ){
			  /*	isvalid=true;
			  	tipMsg="确认要重置二层设备："+s_d;
			  }else if(!Ext.isEmpty(f_d)) {
			  	isvalid=true;
			  	tipMsg="确认要重置一层设备["+f_d_r+"],以及其下的所有二层设备？";
			  }else {*/
			  	isvalid=true;
			  	tipMsg="确认要重置设备：‘"+f_d_r+"’?";
			  } 
			  var okDo=function(OK){
			  	 	if(OK.toUpperCase()=='YES'){  			  	 		
							  	  pingBi();
							  	  /**
							  	   * 如果 没有 选择第二层设备 。则默认将一层设备 下的所有 二层设备重置
							  	   * **/
								  top.getForm().submit({ url: datapath.setOneURL,
										                    success: function(f, o){
																if(o.result.code==300){
																	//Ext.Msg.alert("提示","操作成功，请等待与设备数据同步 ");
																	refreshUtils(o.result.returnVal,null,function(){																		
																		Ext.Msg.alert('提示','操作成功！');
																		 
																	},0,100);
																}else if(o.result.code>900){
																	quXiaoPingBi();
																 	Ext.Msg.alert('提示',!Ext.isEmpty(o.result.message)?o.result.message:"操作失败["+(o.result.code||"")+"]");
																}else{
																		quXiaoPingBi();
																 	Ext.Msg.alert('提示',!Ext.isEmpty(o.result.message)?o.result.message:"操作成功["+(o.result.code||"")+"]");
																}
										                    },failure:function(f, o,x){
										                    	quXiaoPingBi();
										                     	if(o.result&&!Ext.isEmpty(o.result.message)){
										                     	Ext.Msg.alert("失败",o.result.message);	
										                     	}else
																Ext.Msg.alert("失败","链接服务器失败");
															}
										                });
							  	
			  	 		
			  	 	} 
			  	 
			  }
			
			  	if(isvalid){
			  	 Ext.MessageBox.confirm("提示",tipMsg,okDo);
			  	}else{
			  		Ext.Msg.alert("提示","请选择需要被重置的设备！ &nbsp;");
			  		
			  		}
		  }}
		  ,{text:'清空',handler:function(){ 
		  		Ext.getCmp("firstDeviceCombo").setValue("空");
		    	Ext.getCmp("firstDeviceCombo").hiddenField.value="";
		    	
		  		//Ext.getCmp("secondDeviceCombo").setValue("空");
		    	//Ext.getCmp("secondDeviceCombo").hiddenField.value=""; 
		  }}
		  ]}
	});
	
	
	
	
	
	
	
	
	
	
	
	
	
	/**
	 * 
	 **/
	 var  pwdForm=new  Ext.form.FormPanel({title:'输入管理员密码',id:'card-0',layout:'form',items:[
	new Ext.form.TextField({ fieldLabel:'密码',allowBlank:false,inputType :'password',name:'adminPwd',id:'adminPwdId',maxLength:8})
	,{html:'输入管理员密码，进入设备重置管理界面'}
	],buttonAlign:'center',buttons:[{text:'下一步',handler:function(){
		var pwdVal=Ext.getCmp("adminPwdId").getValue();
		if(!Ext.isEmpty(pwdVal)){
			 pwdForm.getForm().submit({ url: datapath.reLoginPwdUrl,
                success: function(f, o){
					if(o.result.success){ 
						resetCard.getLayout().setActiveItem(1);
					}else { 
					 	Ext.Msg.alert('提示',"操作失败!密码异常!");
					}
                },failure:function(f, o,x){ 
					Ext.Msg.alert("失败","操作失败!密码异常!");
				}
            });
		}
		}}]})
	
	
	
	
	
	
	
	
	
	
	
	/************************************
	 * 
	 */
	var defaultActiveIndex=0;
 	var resetCard=new Ext.Panel({
	
	id:'resetCard',
	layout:'card',
	activeItem:defaultActiveIndex,	 
	defaults:{frame:true,border :false}
	,items:[pwdForm,top]
});
 

	
	var backWin = new Ext.Window({
						closeAction : "hide",
						closable : false,
						id : 'resetWin',
						layout : 'fit',
						width : 400,
						height : 200,
						items : [resetCard]
					});

backWin.show(); 


});
 