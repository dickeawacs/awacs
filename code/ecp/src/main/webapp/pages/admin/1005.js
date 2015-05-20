var winPanel, leftTree,rightForm,right_bottom;
var firstDeviceStore,secondDeviceStore ;
var  portGridStore;
var portGridCM;
var  portGrid;
var  funChange,funOpen,funClose;
//主窗口 ，          设备树，       右侧表单      设备基本信息       输入输出选项卡   输入端口选项卡                           输出端口选项卡
var datapath={	
		cacheOutPortURL:'cache/outport.action',
		quitURL:'login!quit.action',
		querydeviceTreeURL:"cache/treeStore.action",
		table10DataURL:"query!queryT10.action",
		table4DateURL:'query!queryT4.action',
		querySecondDevice:'cache/secondStore.action',
		//querySecondDevice:'editQ!second.action',
		//queryFirstDevice:'editQ!first.action',
		queryFirstDevice:'cache/firstStore.action',
		//queryCDURL:"editQ!getCD.action",
		changOutURL:"admin!changOut.action"
		};
Ext.onReady(function(){
	firstDeviceStore=new  Ext.data.Store({
								      proxy:new Ext.data.HttpProxy({ url: datapath.queryFirstDevice}),
									   reader:	new Ext.data.JsonReader(
									   	{totalProperty:'total',root:'array',idProperty:'field3'},
										[{name:'field3'}//设备地址
										,{name:'field121'}//设备名称
										])
										,listeners:{
						        	load:function(){ 
						        		 // Ext.getCmp("firstDeviceCombo").setValue(fselect);
										 // Ext.getCmp("firstDeviceCombo").hiddenField.value=fselect;
									   }
						        }
								    }); 
								    
	secondDeviceStore=new Ext.data.Store({
								      proxy:new Ext.data.HttpProxy({ url: datapath.querySecondDevice}),
									   reader:	new Ext.data.JsonReader(
									   	{totalProperty:'total',root:'array',idProperty:'field4'},
										[{name:'field3'},{name:'field4'}//设备地址
										,{name:'field121'}//设备名称
										,{name:'field130'}//禁用设备 屏蔽
										,{name:'field5'},{name:'field6'},{name:'field7'},{name:'field8'},{name:'field9'},{name:'field10'}
										,{name:'field17'}	,{name:'field18'}	,{name:'field19'}	,{name:'field20'}	,{name:'field21'}	,{name:'field22'}	,{name:'field23'}	,{name:'field24'}	,{name:'field25'}	,{name:'field26'}	
,{name:'field27'}	,{name:'field28'}	,{name:'field29'}	,{name:'field30'}	,{name:'field31'}	,{name:'field32'}	,{name:'field33'}	,{name:'field34'}	,{name:'field35'}	,{name:'field36'}	
,{name:'field37'}	,{name:'field38'}	,{name:'field39'}	,{name:'field40'}	,{name:'field41'}	,{name:'field42'}	,{name:'field43'}	,{name:'field44'}	,{name:'field45'}	,{name:'field46'}	
,{name:'field47'}	,{name:'field48'}	,{name:'field49'}	,{name:'field50'}	,{name:'field51'}	,{name:'field52'}	,{name:'field53'}	,{name:'field54'}	,{name:'field55'}	,{name:'field56'}	
,{name:'field57'}	,{name:'field58'}	,{name:'field59'}	,{name:'field60'}	,{name:'field61'}	,{name:'field62'}	,{name:'field63'}	,{name:'field64'}	,{name:'field65'}	,{name:'field66'}	
,{name:'field67'}	,{name:'field68'}	,{name:'field69'}	,{name:'field70'}	,{name:'field71'}	,{name:'field72'}	,{name:'field73'}	,{name:'field74'}	,{name:'field75'}	,{name:'field76'}	
,{name:'field77'}	,{name:'field78'}	,{name:'field79'}	,{name:'field80'}	,{name:'field81'}	,{name:'field82'}	,{name:'field83'}	,{name:'field84'}	,{name:'field85'}	,{name:'field86'}	
,{name:'field87'}	,{name:'field88'}	,{name:'field89'}	,{name:'field90'}	,{name:'field91'}	,{name:'field92'}	,{name:'field93'}	,{name:'field94'}	,{name:'field95'}	,{name:'field96'}	
,{name:'field97'}	,{name:'field98'}	,{name:'field99'}	,{name:'field100'}	,{name:'field101'}	,{name:'field102'}	,{name:'field103'}	,{name:'field104'}	,{name:'field105'}	,{name:'field106'}	
,{name:'field107'}	,{name:'field108'}	,{name:'field109'}	,{name:'field110'}	,{name:'field111'}	,{name:'field112'}	,{name:'field113'}	,{name:'field114'}	,{name:'field115'}	,{name:'field116'}	
,{name:'field117'}	,{name:'field118'}	,{name:'field119'}	,{name:'field120'}	,{name:'field121'}	,{name:'field122'}	,{name:'field123'}	,{name:'field124'}	,{name:'field125'}	,{name:'field126'}	,{name:'field127'}	,{name:'field128'}
,{name:'field129'}	,{name:'field130'}	,{name:'field131'}	,{name:'field132'}	,{name:'field133'}	,{name:'field134'}	,{name:'field135'}	,{name:'field136'}	,{name:'field137'},{name:'field138'}
,{name:'field139'}	,{name:'field140'}	,{name:'field141'}	,{name:'field142'}	,{name:'field143'}	,{name:'field144'}	,{name:'field145'},{name:'field146'}	,{name:'field147'}
,{name:'field148'}	,{name:'field149'}	,{name:'field150'}	,{name:'field151'}	,{name:'field152'}	,{name:'field153'}	,{name:'field154'}
	])
	,listeners:{load:function(store,record,opts){
	/*	 Ext.getCmp("secondDeviceCombo").setValue(record[0].data.field4);
		 Ext.getCmp("secondDeviceCombo").hiddenField.value=record[0].data.field4;
		 loadInfo(record[0].data.field3,record[0].data.field4);*/
		 }
	
	}
	});
	leftTree=new Ext.tree.TreePanel({
								title:'设备树',
								region:'west',
						        layout:'fit',
						        root: new Ext.tree.AsyncTreeNode({id : '0',text : '设备',draggable : false,expanded : true}),
						        width:200, 
						       // height:500, 
						        animate:true,//开启动画效果 
						        draggable:false,//不允许子节点拖动  
						        loader:new Ext.tree.TreeLoader({dataUrl:datapath.querydeviceTreeURL}) ,
						     	autoScroll:true,
						        frame: true
						    });	
	rightForm=new Ext.FormPanel({
		tbar:[{text:'刷新',handler:function(){
		window.location.reload();
		}}],
	height:130,frame:true,region:'north',items:[
	new Ext.form.FieldSet({title:'设备',layout:'column',defaults:{border:false,labelAlign:'right'},items:[
	new Ext.form.FieldSet({width:240,items:[{xtype:'combo',width:100,fieldLabel:'一层设备',
	id:'firstDeviceCombo',hiddenName:'firstDevice',emptyText:'请选择设备'
	,store:firstDeviceStore,triggerAction:'all',editable:false,valueField:'field3',displayField:'field121',
							  listeners:{// 百美善
									  select:function(combo,record,index){  
										   try{  
										   		fselect=this.value;
										   		myevent="fs";//当一层设备被选中时，要将事件设置为fs，
										 	    var userCombo = Ext.getCmp("secondDeviceCombo");  
										 		 userCombo.store.removeAll();  
											 	 userCombo.store.load({params:{"equipmentFid":this.value}});
											 	 lockTreeNode(leftTree,"first_"+this.value);//同时展开左侧对应的树节点
											 	// rightform_tab.show();
											   } catch(ex){
									 			  Ext.MessageBox.alert("错误","数据加载失败。");  
									 		  } 
									  }  
								}   
							
	
	},{fieldLabel:'闭合 ',html:'<div class="green"></div>'}]})
   ,new Ext.form.FieldSet({width:240,items:[{id:'secondDeviceCombo',width:100,hiddenName:'secondDevice',xtype:'combo',fieldLabel:'二层设备',triggerAction:'all',mode:'local',width:100,emptyText:'先选一层设备',editable:false,
								store:secondDeviceStore,valueField:'field4',displayField:'field121',
								    listeners:{ 
								    	select:function(combo,record,index){
								    		
								    		var equipmentFid=Ext.getCmp("firstDeviceCombo").getValue();
								    		var equipmentSid=this.value;
								    		if(equipmentFid!=""&&equipmentFid!=null&&equipmentSid>-1){
								    			loadInfo(equipmentFid,equipmentSid);
								    		}
								    		
								    		//loadInfo(Ext.getCmp("firstDeviceCombo").getValue(),this.value);
								    		//sselect=this.value;
								    		//setSecondInfo(record);
								    	}
								    }
							},{fieldLabel:'断开',html:'<div class="white"></div>'}]})
   ,new Ext.form.FieldSet({width:280,items:[{html:'<div  class=x-form-item style="width:150px;" align="center"  ><div class=blank ></div></div>'}]})
	]})	
/*	,new Ext.form.FieldSet({title:'输出控制',layout:'column',defaults:{border:false,labelAlign:'right'},items:[
		new Ext.form.FieldSet({width:280,items:[{xtype:'textfield',fieldLabel:'输出一',readOnly :true,name:'op1'},{xtype:'textfield',fieldLabel:'输出二',readOnly :true,name:'op2'},{xtype:'textfield',fieldLabel:'输出三',readOnly :true,name:'op3'},{xtype:'textfield',fieldLabel:'输出四',readOnly :true,name:'op4'}
		,{xtype:'textfield',fieldLabel:'输出五',readOnly :true,name:'op5'},{xtype:'textfield',fieldLabel:'输出六',readOnly :true,name:'op6'},{xtype:'textfield',fieldLabel:'输出七',readOnly :true,name:'op7'},{xtype:'textfield',fieldLabel:'输出八',readOnly :true,name:'op8'}]}),
		new Ext.form.FieldSet({width:280,defaults:{border:false,labelAlign:'center'},items:[{html:'<div class=x-form-item style="width:150px;" align="center" ><div class="black" id="op_slider_1"></div></div>'}
		,{html:'<div class=x-form-item style="width:150px;" align="center" ><div class="black"id="op_slider_2"></div></div>'},{html:'<div class=x-form-item style="width:150px;" align="center" ><div class="black" id="op_slider_3"></div></div>'}
		,{html:'<div class=x-form-item style="width:150px;" align="center" ><div class="black" id="op_slider_4"></div></div>'},{html:'<div class=x-form-item style="width:150px;" align="center" ><div class="black" id="op_slider_5"></div></div>'}
		,{html:'<div class=x-form-item style="width:150px;" align="center" ><div class="black" id="op_slider_6"></div></div>'},{html:'<div class=x-form-item style="width:150px;" align="center" ><div class="black" id="op_slider_7"></div></div>'}
		,{html:'<div class=x-form-item style="width:150px;" align="center" ><div class="black" id="op_slider_8"></div></div>'}]}),
		new Ext.form.FieldSet({width:150,items:[buildSlider(1),buildSlider(2),buildSlider(3),buildSlider(4),buildSlider(5),
		buildSlider(6),buildSlider(7),buildSlider(8)]})
	]})*/
	]});
	   portGridStore=new Ext.data.Store({
    	proxy: new Ext.data.HttpProxy({
		    	method: 'POST' ,
		    	storeId:'index',
		    	url:datapath.cacheOutPortURL+"?td="+(new Date().getTime())
		    	,params:{}}),
	    reader:new Ext.data.JsonReader(
	    		{totalProperty:'total',root:'array'},
			    [{name:'id'},
			     {name:'equipmentFid'},
			     {name:'equipmentSid'},
		     	 {name:'index'},
			     {name:'name'},
		 		 {name:'port'},
		     	 {name:'type'},
			     {name:'asName'}
			     ]) ,
	     listeners:{}
    });  
	  portGridCM=new Ext.grid.ColumnModel([
    {header:'序号',dataIndex:'id',width:40,hidden:true},
    {header:'端口',dataIndex:'asName',width:100,renderer:function(v,obj,r){obj.style=obj.style+"font-size:12px;";return v;}},
    {header:'状态',dataIndex:'port',width:100,renderer:function(v,obj,r){
    	v=r.get("port");
    	if(v==0){
    		obj.style=obj.style+"background-color:000000;color:white;font-size:12px;";
    		return "断开";
    	}else{
			obj.style=obj.style+"background-color:green;color:white;font-size:12px;";
    		return "闭合";
    		}
    }},
    {header:'名称',dataIndex:'name',width:300,renderer:function(v,obj,r){obj.style=obj.style+"font-size:12px;";return v;}},
    {header:'一层设备',dataIndex:'equipmentFid',width:80,hidden:true},
    {header:'二层设备',dataIndex:'equipmentSid',width:100,hidden:true},
    {header:'端口顺序',dataIndex:'index',width:400,hidden:true},
    {header:'操作',width:100,renderer:function(v,obj,r){
    	v=r.get("port");
    	if(v==0){
    		return "<input type='button' style='height:25px;' value='闭合' onclick=funOpen("+r.get("equipmentFid")+","+r.get("equipmentSid")+","+r.get("index")+") />";
    	}else{
    		return "<input type='button' style='height:25px;' value='断开' onclick=funClose("+r.get("equipmentFid")+","+r.get("equipmentSid")+","+r.get("index")+") />";
    		}
    }},
    {header:'输出',dataIndex:'type',width:100,hidden:true}
    ]);
  portGrid=new Ext.grid.GridPanel({
	id:'outputGrid',
	title:'输出端口'
	,store:portGridStore
	,sm:new Ext.grid.RowSelectionModel({singleSelect:true})
	,cm:portGridCM
	,region:'center'
});
	winPanel=new Ext.Viewport({layout:'border',items:[leftTree,rightForm,portGrid]});

	/***
	 * 闭合
	 */
	funOpen=function(fid,sid,port){
			if(fid==""||(sid+"")==""||port==""){
			Ext.MessageBox.alert("提示","1请选择设备");
			return false;
			}
			funChange(1,fid,sid,port);
	
	};/**end funOpen*/
	
	/***
	 * 断开
	 */
	funClose=function(fid,sid,port){
			if(fid==""||(sid+"")==""||port==""){
			Ext.MessageBox.alert("提示","请选择设备");
			return false;
			}
			funChange(0,fid,sid,port);
	};/**end funOpen*/
/****
 * type,fid,sid,port
 */
 funChange=function(type,fid,sid,port){   
			if(fid==""||(sid+"")==""||port==""){
			Ext.MessageBox.alert("提示","请选择设备");
			return false;
			}
			pingBi();
			Ext.Ajax.request({url:datapath.changOutURL,
					params:{"firstLayerID":fid,"secondLayerID":sid,"out":port,"sw":type},
					success: function(a,b){
						   	   var b = Ext.util.JSON.decode(a.responseText);                       
								if(b.success){
									var key=b.returnVal; 
									if(b.code==300){
										refreshTime=2000;
										refreshUtils(b.returnVal,null,function(){
											portGridStore.reload();
										/*	var  records=portGrid.getSelectionModel().getSelections();
											  if(records.length>0) {
											  		console.log(records);
											  		//v=r.get("port");
    	if(v==0){
    		obj.style=obj.style+"background-color:000000;color:white;font-size:12px;";
    		return "闭合";
    	}else{
			obj.style=obj.style+"background-color:green;color:white;font-size:12px;";
    		return "断开";
    		}
                         				   		 
                        						}
											*/
											Ext.Msg.alert('提示','操作成功！'); 
										});
									}else if(b.code>=900){
										quXiaoPingBi();
									 	Ext.Msg.alert('提示',b.message);
									 	//nochange=true;
									 	//obj.setValue(obj.getValue()==1?0:1);
									}
			                    
								}else{
									/*nochange=true;
									obj.setValue(obj.getValue()==1?0:1);*/
									Ext.Msg.alert('提示',b.message);
								}
						   	},failure:function(a){
						   		nochange=true;
						   		obj.setValue(obj.getValue()==1?0:1);
						   		Ext.Msg.alert("提示","与服务器连接失败");
					}
			});
 };/**end funChange*/
 
	
});

var nochange=false;

var buildSlider=function(id){
return new Ext.Slider({
id:'slider_'+id,
width: 50,
height:26,
increment: 1,
minValue: 0,
disabled:false,
maxValue: 1,
listeners:{
beforechange:function(a,b){
	if(nochange)return true;
var one,two,id;
one=Ext.getCmp("firstDeviceCombo").getValue();
two=Ext.getCmp("secondDeviceCombo").getValue();
if(one==""||((sid+"")!=""&&two>-1)){
//Ext.MessageBox.alert("提示","请选择设备");
return false;
}
},
change:function(obj,val){
		var sli=Ext.get("op_"+obj.id);
	if(val==1){
		sli.removeClass(['black','white']).addClass('green');
	}			
	else{
		sli.removeClass(['black','green']).addClass('white');
	}

	//alert(nochange);
	if(nochange){
	nochange=false;return;}
var one,two,id;
one=Ext.getCmp("firstDeviceCombo").getValue();
two=Ext.getCmp("secondDeviceCombo").getValue();
var idstr=new String(obj.id);
id=idstr.substring(idstr.indexOf("_")+1);
if(one==""||((two+"")!=""&&two>-1)){
Ext.MessageBox.alert("提示","请选择设备");
return false;
}

pingBi();

Ext.Ajax.request({url:datapath.changOutURL,
		params:{"firstLayerID":one,"secondLayerID":two,"out":id,"sw":val},
		success: function(a,b){
			   	   var b = Ext.util.JSON.decode(a.responseText);                       
					if(b.success){
						var key=b.returnVal; 
						if(b.code==300){
							refreshUtils(b.returnVal,null,function(){
								Ext.Msg.alert('提示','操作成功！'); 
							});
						}else if(b.code>=900){
							quXiaoPingBi();
						 	Ext.Msg.alert('提示',b.message);
						 	nochange=true;
						 	obj.setValue(obj.getValue()==1?0:1);
						}
                    
					}else{
						nochange=true;
						obj.setValue(obj.getValue()==1?0:1);
						Ext.Msg.alert('提示',b.message);
					}
			   	},failure:function(a){
			   		nochange=true;
			   		obj.setValue(obj.getValue()==1?0:1);
			   		Ext.Msg.alert("提示","与服务器连接失败");
		
		}
})

	
}
}
}); 
};

var loadInfo=function(one,two){	
	portGridStore.load({params:{equipmentFid:one,equipmentSid:two}});
	
	/*
Ext.Ajax.request({url:datapath.queryCDURL,
		params:{"firstLayerID":one,"secondLayerID":two},
		success: function(a,b){
			   	   var b = Ext.util.JSON.decode(a.responseText);                       
					if(b.success){
						var first=b.returnVal; 
						if(first!=null&&first!=""){
							rightForm.getForm().findField("op1").setValue(first.field113);
							setOPinfo(first.field155,1);
							rightForm.getForm().findField("op2").setValue(first.field114);
							setOPinfo(first.field156,2);
							rightForm.getForm().findField("op3").setValue(first.field115);
							setOPinfo(first.field157,3);
							rightForm.getForm().findField("op4").setValue(first.field116);
							setOPinfo(first.field158,4);
							rightForm.getForm().findField("op5").setValue(first.field117);
							setOPinfo(first.field159,5);
							rightForm.getForm().findField("op6").setValue(first.field118);
							setOPinfo(first.field160,6);
							rightForm.getForm().findField("op7").setValue(first.field119);
							setOPinfo(first.field161,7);
							rightForm.getForm().findField("op8").setValue(first.field120);
							setOPinfo(first.field162,8);
						}else{
						setOPinfo(8,1);
						setOPinfo(8,2);
						setOPinfo(8,3);
						setOPinfo(8,4);
						setOPinfo(8,5);
						setOPinfo(8,6);
						setOPinfo(8,7);
						setOPinfo(8,8);
						
						}
					}else{
						Ext.Msg.alert('提示',b.message);
					}
			   	},failure:function(a){
			   		Ext.Msg.alert("提示","与服务器连接失败");
		
		}
})*/
};

var setOPinfo=function(val,index){
	nochange=true;
	var sli=Ext.get("op_slider_"+index);
	if(val==1){
		Ext.getCmp("slider_"+index).setValue(1);
		sli.removeClass(['black','white']).addClass('green');
		Ext.getCmp("slider_"+index).disabled=false;
	}
	else if(val==0){
		Ext.getCmp("slider_"+index).setValue(0);
		sli.removeClass(['black','green']).addClass('white');
		Ext.getCmp("slider_"+index).disabled=false;
	}
	else{
		Ext.getCmp("slider_"+index).setValue(0);
		sli.removeClass(['white','green']).addClass('black');
		Ext.getCmp("slider_"+index).disabled=true;
	}
	nochange=false;
}


var lockTreeNode = function(leftTree, nodeID) {	
	var node;
	for (var i = 0; i < leftTree.root.childNodes.length; i++) {
		node = leftTree.getNodeById(leftTree.root.childNodes[i].id);
		if (node != null) {
			//console.log(nodeID+":"+node.id);
			if (nodeID==node.id) {
				//node.getUI().show();
				node.expand(true,false);
				
			} else {
				node.collapse(false);
			}
		}

	}
}