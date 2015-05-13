Ext.QuickTips.init();
Ext.onReady(function() {
	var mainPanel = new Ext.form.FormPanel({
		 
		id : "leftform",
		title : 'ATS系统设置', 
		renderTo:'atsetingID',
		width:400,
		height:150,
		frame:true,
		items : [{	xtype : 'numberfield',
					hiddenName : 'portsetting',
					name : 'portsetting',
					maxLength : 5,
					minValue : 0,
					allowBlank : false,
					id : 'portsetting',
					fieldLabel : '系统侦听端口'
				},{	xtype : 'textfield',
					hiddenName : 'ipsetting',
					name : 'ipsetting',
					maxLength : 15,allowBlank : false,
					regex:/^([1-9]{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])$/,
					regexText:'请输入正确的IP地址!',
					id : 'ipsetting',
					fieldLabel : '系统侦听IP'
				} , {
					html : '请注意：系统设置修改后，需要重启服务器才能生效 ！'
				}],
		buttons : {
			buttonAlign : 'center',
			items : [{
				text : '保存',
				handler : function() {
					var f_d_r = Ext.getCmp("portsetting").getValue();
					var tipMsg = "", isvalid = false;
					
					
					if (!mainPanel.getForm().isValid()) {						
						tipMsg = "数据格式有误！";
					}else if(!Ext.isEmpty(f_d_r)) {
						isvalid = true;
						tipMsg = "确认修改系统设置? 它可能导致设备无法链接到服务器！";
					}
					var okDo = function(OK) {
						if (OK.toUpperCase() == 'YES') {
							mainPanel.getForm().submit({
								waitMsg:'正在保存',
								url : "event/atssetting.action",
								success : function(f, o) {
									if (o.result.success) {
									} else {
										Ext.Msg.alert('提示',!Ext.isEmpty(o.result.message)? o.result.message: "操作失败！");
									}
								},
								failure : function(f, o, x) {
									if (o.result
											&& !Ext.isEmpty(o.result.message)) {
										Ext.Msg.alert("失败", o.result.message);
									} else
										Ext.Msg.alert("失败", "链接服务器失败");
								}
							});

						}

					}

					if (isvalid) {
						Ext.MessageBox.confirm("提示", tipMsg, okDo);
					} else {
						Ext.Msg.alert("提示",tipMsg);

					}
				}
			}

			]
		}
	});
/*	var viewp = new Ext.Viewport({
				layout : 'form',
				items : [mainPanel]
			})*/
function loadSetings(){
 Ext.Ajax.request({
		url:"event/loadSetting.action",
		success:function(a,b){
	   	   var b = Ext.util.JSON.decode(a.responseText);                       
			if(b.success){ 
				Ext.getCmp("portsetting").setValue(b.returnVal[0]);
				Ext.getCmp("ipsetting").setValue(b.returnVal[1]);
			}
		},
		failure:function(r,o){
		}
	
	}); 
};
loadSetings();
});
