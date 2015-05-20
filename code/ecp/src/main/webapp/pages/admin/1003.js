var winPanel, userForm,firstDeviceStore,adminForm;
var clearCheckBox=function(){
	$("#tree_panle_id input:checkbox").removeAttr("checked");
}

//typeVals=[[2,"设备管理员"],[3,"设备用户"]];//[1,"系统管理员"],
	
//主窗口 ，         上级表单   下级表单
var datapath={	
		queryUserURL:"t1Query!getUser.action",
		querySecondURL:"editQ!second.action",
		queryDeviceTreePortURL:"editQ!deviceTreePort.action",
		queryUserRight:"editQ!getUserRight.action",
		table1SaveURL:"admin!saveUserSet.action",
		queryFirstDevice:'cache/firstStore.action',		
		resetAdminPwdURL:'adminResetPwd.action'
		};
	Ext.QuickTips.init();
Ext.onReady(function(){		
	var  typeVals=new Ext.data.SimpleStore({fields:['value','text'],
					data:[[2,"设备管理员"],[3,"设备用户"]]});
	//var rootNode=new Ext.tree.AsyncTreeNode({id : 'root_001',text : '设备',draggable : false,expanded : true});
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
	
/*	var leftTree=new Ext.tree.TreePanel({
								id:'tree_panle_id',
								title:'设备',
								collapsible:false,
								checkModel : 'cascade',
								//region:'west',
						       // layout:'fit',
						        //tbar:new Ext.Toolbar({border:true,items:[{text:'设备正常'},{text:'设备异常'}]}),
						        root: rootNode,
						        autoWidth:true, 
						        width:500,
						        clearOnLoad:true,
						        height:400, 
						        //animate:true,//开启动画效果 
						        draggable:false,//不允许子节点拖动  
						        loader:new Ext.tree.TreeLoader({dataUrl:datapath.queryDeviceTreePortURL}),
						        autoLoad:false,
						     	autoScroll:true,
						        frame: true,
						     	border:false
						    });	*/	
	//-----------------------
	userForm=new Ext.FormPanel({
		id:'userSetForm',
	//region:'center',
		//autoWidth:true,
		autoHeight:true,
		renderTo:'formArea',
		collapsed:false,collapsible:true,
		title:'设备用户管理',
	frame:true,
	buttonAlign:'center',
//	layout:'form',//default is form
	padding:'5 0 5 0',
	width:400,
	labelAlign:'right',
	defaults:{padding:'5 15 5 5',labelWidth:80,msgTarget:'title'},
	items:[
			
			{id:'oneP',xtype:'combo',
			fieldLabel:'一层设备',  width:140,
			emptyText:'请选择设备',
			 hiddenName:'firstDevice',
			 store:firstDeviceStore,
			 triggerAction:'all',editable:false,allowBlank:false,
			 valueField:'field3',displayField:'field121' ,
			 listeners:{select:function(){
			 	Ext.getCmp("userCombo").reset(); 
			 }}
			},
			{xtype:'combo',fieldLabel:'用户类别',name:'userType',hiddenName:'userType',emptyText:'选择用户类别',allowBlank:false,
			store:typeVals,valueField:'value',displayField:'text',
			editable:false,mode:'local',width:140,triggerAction:'all',listeners:{
			 	select:function(){  
										    try{  
										 		var firsetEquipmentId = Ext.getCmp("oneP").getValue();  
  										 	  	var utype = this.value;
  										 	  	//alert(firsetEquipmentId);
  										 	  	if(!Ext.isEmpty(firsetEquipmentId)&&!Ext.isEmpty(utype)){
										 	 		var userCombo = Ext.getCmp("userCombo");
										 	 		userCombo.reset(); 
										 		 	userCombo.store.removeAll();  
											 	 userCombo.store.load({
												 	 params:{"deviceID":firsetEquipmentId,
												 	 "utype":utype,
												 	 callback:function(){},
												 	 add:false}});
											 		//userForm.getForm().findField("field11").setValue(this.value);
  										 	  	}
											     } catch(ex){
									 			  Ext.MessageBox.alert("错误","数据加载失败。");  
									 		  }   
									  }  
								}}, 
			{name:'field12',xtype:'combo',allowBlank:false,
				fieldLabel:'设备用户',id:'userCombo', hiddenName:'field12',
				triggerAction:'all',mode:'local',
				width:140,emptyText:'先选设备再选用户',
				editable:false,
				readyOnly:true,
				store:new Ext.data.Store({
								      proxy:new Ext.data.HttpProxy({ url: datapath.queryUserURL}),
									   reader:	new Ext.data.JsonReader(
									   	{totalProperty:'total',root:'array',idProperty:'field12'},
										[{name:'field1'},{name:'field12'},{name:'field3'},{name:'field4'},{name:'field6'},{name:'field7'},{name:'field8'},{name:'field9'},{name:'field10'}])
										,listeners:{load:function(a,b,c){//数据加载后，自动的初始化 选择第一项
										 	var temcombo=Ext.getCmp("userCombo");
											var  vf=temcombo.valueField;
											var rec=temcombo.store.getAt(0);
											if(rec){
												temcombo.setValue(rec.get(vf));
												loadformData(rec);
											}
										}}
								    }),valueField:'field12',displayField:'field3',
								    listeners:{
								    	select:function(combo,record,index){
								    		//clearCheckBox();
								    		loadformData(record);
								    	}
								    }
			},
							{name:'field3',xtype:'textfield',fieldLabel:'登录名',allowBlank:false,maxLength:10,invalidText:'最大长度10位'},
							{name:'field4',xtype:'textfield',fieldLabel:'密码'  ,inputType: 'password',size:8,allowBlank:false,maxLength:8},
							{name:'field7',xtype:'textfield',fieldLabel:'电话号码',maxLength:16,regex: /^[0-9]{1,16}$/,regexText:'最长不可超过16位!'},
							{name:'field9',xtype:'textfield',fieldLabel:'短信号码',maxLength:16,regex: /^[0-9]{1,16}$/,regexText:'最长不可超过16位!'}	,
							{name:'field6',xtype:'checkbox',boxLabel :'禁用此用户',inputValue:1,height:23},							
							{name:'field8',xtype:'checkbox',boxLabel :'禁用电话',inputValue:1,height:23,hidden:true},
							{name:'field10',xtype:'checkbox',boxLabel :'禁用短信',inputValue:1,height:23,hidden:true},
							{name:'field1',xtype:'textfield',fieldLabel :'用户序号',hidden:true}
		  ],
		  buttons:[{xtype:'button',text:'保存',align:'center',width:100,handler:function(){
							  	if(userForm.getForm().isValid()){
							  	//openMask('userSetForm','正在提交...');
							  		pingBi();
								  userForm.getForm().submit({ url: datapath.table1SaveURL,
										                    success: function(f, o){
										                    	//closeMask();					                    
																if(o.result.code==300){
																	refreshUtils(o.result.returnVal,null);
																}else if(o.result.code>=900){
																	//closeMask();
																	quXiaoPingBi();
																 Ext.Msg.alert('提示',o.result.message);
																}
										                    },failure:function(f, o,x){
										                    	//closeMask();
										                    	quXiaoPingBi();
																Ext.Msg.alert("失败",o.result.message);
															}
										                });
							  	}else Ext.Msg.alert("提示","&nbsp;数据不合法！ &nbsp;");
		  }}/*,
		  			
		  				{xtype:'button',text:'重置密码(123456)',align:'center',width:100,handler:function(){
		  			//console.log(userForm.getForm().findField("pwdTemp"));
		  			//.setValue("123456");
		  			}}*/
		  			]
	});	
	
	/***
	 * 如果是管理员则允许修改密码
	 */
	if(gobal_user_level==1){
	adminForm=new Ext.FormPanel({
	id:'adminResetForm',
	title:'修改管理员密码',
	renderTo:'adminResetPwd', 
	collapsible:true,
	collapsed:false,
	frame:true,
	layout:'form',
	padding:'5 0 5 0',
	width:400,
	height:150,
	labelAlign:'right',
	defaults:{padding:'5 15 5 5',labelWidth:80,msgTarget:'title'},
	items : [{
				name : 'pwd',
				xtype : 'textfield',
				fieldLabel : '旧密码',
				inputType : 'password',
				size : 8,
				allowBlank : false,
				regex: /^[0-9a-zA-Z-]{8}$/,regexText:'管理员密码是8位长度的数字与字母组合!',
				maxLength : 8
			}, {
				name : 'newPwd',
				xtype : 'textfield',
				fieldLabel : '新密码',
				inputType : 'password',
				size : 8,
				allowBlank : false,
				regex: /^[0-9a-zA-Z-]{8}$/,regexText:'管理员密码是8位长度的数字与字母组合!',
				maxLength : 8
			}, {
				name : 'confirmPwd',
				xtype : 'textfield',
				fieldLabel : '确认密码',
				inputType : 'password',
				size : 8,
				allowBlank : false,
				regex: /^[0-9a-zA-Z-]{8}$/,regexText:'管理员密码是8位长度的数字与字母组合!',
				maxLength : 8
			}],
	buttonAlign:'center',buttons:[{text:'保存',handler:function(){
			 var oldpwd = adminForm.getForm().findField("pwd").getValue();
			 var newpwd = adminForm.getForm().findField("newPwd").getValue();
			 var newpwd2 = adminForm.getForm().findField("confirmPwd").getValue();
			if(adminForm.getForm().isValid()){
		  	 	if(newpwd==newpwd2){
				  adminForm.getForm().submit({url: datapath.resetAdminPwdURL,waitMsg:'正在保存....',
						                    success: function(f, o){
												if (o.result.success) {
													Ext.Msg.alert('提示', '操作成功！');
													adminForm.getForm().reset();
												} else {
													Ext.Msg.alert('提示',o.result.message);
												}
						                    },failure:function(f, o,x){
												Ext.Msg.alert("失败",(o.result?o.result.message:"访问服务器失败！"));
											}
						                });
		  	 	}else Ext.Msg.alert("提示","新密码与确认密码不相符,请重新输入.")
		}else{ Ext.Msg.alert("提示","数据格式不合法,请重新输入！ &nbsp;");}
		}}]
	});		
	
	
	}	
	
	
	
	
});

/***
 * 
 * @param {} leftTree 树对象
 * @param {} usercode 当前用户编码
 * @param {} first    一层设备呈
 */
var setUserRight=function(leftTree, usercode,first){
	 Ext.Ajax.request({
	url:datapath.queryUserRight,
	params:{"userCode":usercode,"firstLayerID":first},
	success: function(a,b){
		   	   var b = Ext.util.JSON.decode(a.responseText);                       
				if(b.success){
					var t9=b.returnVal;
					for (var i = 0; i < t9.children.length; i++) {
						var x=t9.children[i];		
						if(x.leaf){
							if(x.checked)$("[value="+x.id+"]").attr("checked","checked");
						}else{
							for (var j= 0; j < x.children.length; j++) {
								 var t10=x.children[j];
								if(t10.leaf&&t10.checked){
									$("[value="+t10.id+"]").attr("checked","checked");
								}
							}
						}
						 
					}
				}else{
					Ext.Msg.alert('提示',b.message);
				}
				
		   	},failure:function(a){
		   		Ext.Msg.alert("提示","与服务器连接失败");
	
	}
	});

}
var showThisHideOther = function(leftTree, nodeID) {
	var node;
	for (var i = 0; i < leftTree.root.childNodes.length; i++) {
		node = leftTree.getNodeById(leftTree.root.childNodes[i].id);
		if (node != null) {
			//console.log(nodeID+":"+node.id);
			if (nodeID==node.id) {
				node.getUI().show();
				node.expand(true,false);
				//node.expandAll()
			} else {
				node.getUI().hide();
			}
		}

	}
	// leftTree.getNodeById(nodeID);

}
var loadformData=function(rec){
	//userForm
	var tempForm= Ext.getCmp('userSetForm');
	var vals={'field1':rec.get("field1"),'field3':rec.get("field3"),'field4':rec.get("field4"),'field9':rec.get("field9"),'field7':rec.get("field7"),
				'field6':(rec.get("field6")),'field8':(rec.get("field8")==1),'field10':(rec.get("field10")==1)};
	tempForm.getForm().setValues(vals);
	//tempForm.getForm().findField("pwdTarget").setValue('0');
	//tempForm.getForm().findField("pwdTemp").setValue('******');
};
var myMask=null;
//启动遮掩
var  openMask=function(eid,etip){
	myMask=Ext.getCmp(eid); //找到你想从哪个控件上开始遮掩
    myMask.el.mask(etip, 'x-mask-loading');  
};
var  closeMask=function(){
	if(myMask!=null)
		myMask.el.unmask();
	myMask=null;
};
