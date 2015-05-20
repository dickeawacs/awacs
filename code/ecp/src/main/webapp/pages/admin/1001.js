var winPanel, leftTree,rightForm,rightForm_top,rightform_tab,rightform_tab_inputTab,rightform_tab_outputTab;
var firstDeviceStore,secondDeviceStore;
var inputTag="rightform_tab_inputTab",outputTag="rightform_tab_outputTab";
var myevent="";//fs:选择一层设备后刷新,sf:'修改二层设备后刷新'
var fselect=-1,sselect=0;//fselect 一层设备被选中项，sselect 二层设备被选中项，  它们是在保存数据后需要刷新时使用
var portNums=[[1,"1"],[2,"2"],[3,"3"],[4,"4"],[5,"5"],[6,"6"],[7,"7"],[8,"8"]];
var  inPortComStore,outPortComStore;
//主窗口 ，          设备树，       右侧表单      设备基本信息       输入输出选项卡   输入端口选项卡                           输出端口选项卡
var datapath={	
	queryInPortURL:"cache/inport.action",
	queryOutPortURL:"cache/outport.action",
	queryUserURL:"t1Query!getUser.action",
		querydeviceTreeURL:"editQ!deviceTree.action",
		querySecondDevice:'cache/secondStore.action',//equipmentFid'editQ!second.action',
		queryFirstDevice:'cache/firstStore.action',//;'editQ!first.action',
		saveDeviceURL:'admin!saveDevice.action'
		,saveInPorURL:'admin2!inPort.action'
		,saveoutPorURL:'admin2!outPort.action'
		,saveCascadePortURL:'admin2!cascadePort.action'
		,queryEquipmentInfoURL:"findEquipmentOnly.action"
		};
		
var portGroup=new Array();
/*portGroup[inputTag+"_1"]={
		pn:"field105"//输入名称
		,pp:"field17"//输入属性
		,pu:'field25' //二级用户编号
		,pc:'field33'//输入跟随信息   跟随      格式[二层设备地址(标号) , 端口号, 跟随禁止][（1-128）                        ,（1-8）,0/1]
		,cos:[]//CascadeOutput 联动输出 
}*/
//输入1
portGroup[inputTag+"_1"]=	{pn:"field105",	pp:"field17",	pu:'field25',	pc:'field33',cos:[],sound:[]};
portGroup[inputTag+"_1"].sound["trigger"]="field122";portGroup[inputTag+"_1"].sound["recovery"]="field131";
//输入2
portGroup[inputTag+"_2"]=	{pn:"field106",	pp:"field18",	pu:'field26',	pc:'field34',cos:[],sound:[]};
portGroup[inputTag+"_2"].sound["trigger"]="field123";portGroup[inputTag+"_2"].sound["recovery"]="field132"; 
//输入3
portGroup[inputTag+"_3"]=	{pn:"field107",	pp:"field19",	pu:'field27',	pc:'field35',cos:[],sound:[]};
portGroup[inputTag+"_3"].sound["trigger"]="field124";portGroup[inputTag+"_3"].sound["recovery"]="field133";
//输入4
portGroup[inputTag+"_4"]=	{pn:"field108",	pp:"field20",	pu:'field28',	pc:'field36',cos:[],sound:[]};
portGroup[inputTag+"_4"].sound["trigger"]="field125";portGroup[inputTag+"_4"].sound["recovery"]="field134";
//输入5
portGroup[inputTag+"_5"]=	{pn:"field109",	pp:"field21",	pu:'field29',	pc:'field37',cos:[],sound:[]};
portGroup[inputTag+"_5"].sound["trigger"]="field126";portGroup[inputTag+"_5"].sound["recovery"]="field135";
//输入6
portGroup[inputTag+"_6"]=	{pn:"field110",	pp:"field22",	pu:'field30',	pc:'field38',cos:[],sound:[]};
portGroup[inputTag+"_6"].sound["trigger"]="field127";portGroup[inputTag+"_6"].sound["recovery"]="field136";
//输入7
portGroup[inputTag+"_7"]=	{pn:"field111",	pp:"field23",	pu:'field31',	pc:'field39',cos:[],sound:[]};
portGroup[inputTag+"_7"].sound["trigger"]="field128";portGroup[inputTag+"_7"].sound["recovery"]="field137";
//输入8
portGroup[inputTag+"_8"]=	{pn:"field112",	pp:"field24",	pu:'field32',	pc:'field40',cos:[],sound:[]};
portGroup[inputTag+"_8"].sound["trigger"]="field129";portGroup[inputTag+"_8"].sound["recovery"]="field138";
//输入1联动输出8个	
portGroup[inputTag+"_1"].cos[inputTag+"_1_cascade1"]=	"field41";
portGroup[inputTag+"_1"].cos[inputTag+"_1_cascade2"]=	"field42";
portGroup[inputTag+"_1"].cos[inputTag+"_1_cascade3"]=	"field43";
portGroup[inputTag+"_1"].cos[inputTag+"_1_cascade4"]=	"field44";
portGroup[inputTag+"_1"].cos[inputTag+"_1_cascade5"]=	"field45";
portGroup[inputTag+"_1"].cos[inputTag+"_1_cascade6"]=	"field46";
portGroup[inputTag+"_1"].cos[inputTag+"_1_cascade7"]=	"field47";
portGroup[inputTag+"_1"].cos[inputTag+"_1_cascade8"]=	"field48";
//输入2联动输出8个	
portGroup[inputTag+"_2"].cos[inputTag+"_2_cascade1"]=	"field49";
portGroup[inputTag+"_2"].cos[inputTag+"_2_cascade2"]=	"field50";
portGroup[inputTag+"_2"].cos[inputTag+"_2_cascade3"]=	"field51";
portGroup[inputTag+"_2"].cos[inputTag+"_2_cascade4"]=	"field52";
portGroup[inputTag+"_2"].cos[inputTag+"_2_cascade5"]=	"field53";
portGroup[inputTag+"_2"].cos[inputTag+"_2_cascade6"]=	"field54";
portGroup[inputTag+"_2"].cos[inputTag+"_2_cascade7"]=	"field55";
portGroup[inputTag+"_2"].cos[inputTag+"_2_cascade8"]=	"field56";
//输入3联动输出8个	
portGroup[inputTag+"_3"].cos[inputTag+"_3_cascade1"]=	"field57";
portGroup[inputTag+"_3"].cos[inputTag+"_3_cascade2"]=	"field58";
portGroup[inputTag+"_3"].cos[inputTag+"_3_cascade3"]=	"field59";
portGroup[inputTag+"_3"].cos[inputTag+"_3_cascade4"]=	"field60";
portGroup[inputTag+"_3"].cos[inputTag+"_3_cascade5"]=	"field61";
portGroup[inputTag+"_3"].cos[inputTag+"_3_cascade6"]=	"field62";
portGroup[inputTag+"_3"].cos[inputTag+"_3_cascade7"]=	"field63";
portGroup[inputTag+"_3"].cos[inputTag+"_3_cascade8"]=	"field64";
//输入4联动输出8个	
portGroup[inputTag+"_4"].cos[inputTag+"_4_cascade1"]=	"field65";
portGroup[inputTag+"_4"].cos[inputTag+"_4_cascade2"]=	"field66";
portGroup[inputTag+"_4"].cos[inputTag+"_4_cascade3"]=	"field67";
portGroup[inputTag+"_4"].cos[inputTag+"_4_cascade4"]=	"field68";
portGroup[inputTag+"_4"].cos[inputTag+"_4_cascade5"]=	"field69";
portGroup[inputTag+"_4"].cos[inputTag+"_4_cascade6"]=	"field70";
portGroup[inputTag+"_4"].cos[inputTag+"_4_cascade7"]=	"field71";
portGroup[inputTag+"_4"].cos[inputTag+"_4_cascade8"]=	"field72";
//输入5联动输出8个	
portGroup[inputTag+"_5"].cos[inputTag+"_5_cascade1"]=	"field73";
portGroup[inputTag+"_5"].cos[inputTag+"_5_cascade2"]=	"field74";
portGroup[inputTag+"_5"].cos[inputTag+"_5_cascade3"]=	"field75";
portGroup[inputTag+"_5"].cos[inputTag+"_5_cascade4"]=	"field76";
portGroup[inputTag+"_5"].cos[inputTag+"_5_cascade5"]=	"field77";
portGroup[inputTag+"_5"].cos[inputTag+"_5_cascade6"]=	"field78";
portGroup[inputTag+"_5"].cos[inputTag+"_5_cascade7"]=	"field79";
portGroup[inputTag+"_5"].cos[inputTag+"_5_cascade8"]=	"field80";
//输入6联动输出8个	
portGroup[inputTag+"_6"].cos[inputTag+"_6_cascade1"]=	"field81";
portGroup[inputTag+"_6"].cos[inputTag+"_6_cascade2"]=	"field82";
portGroup[inputTag+"_6"].cos[inputTag+"_6_cascade3"]=	"field83";
portGroup[inputTag+"_6"].cos[inputTag+"_6_cascade4"]=	"field84";
portGroup[inputTag+"_6"].cos[inputTag+"_6_cascade5"]=	"field85";
portGroup[inputTag+"_6"].cos[inputTag+"_6_cascade6"]=	"field86";
portGroup[inputTag+"_6"].cos[inputTag+"_6_cascade7"]=	"field87";
portGroup[inputTag+"_6"].cos[inputTag+"_6_cascade8"]=	"field88";
//输入7联动输出8个	
portGroup[inputTag+"_7"].cos[inputTag+"_7_cascade1"]=	"field89";
portGroup[inputTag+"_7"].cos[inputTag+"_7_cascade2"]=	"field90";
portGroup[inputTag+"_7"].cos[inputTag+"_7_cascade3"]=	"field91";
portGroup[inputTag+"_7"].cos[inputTag+"_7_cascade4"]=	"field92";
portGroup[inputTag+"_7"].cos[inputTag+"_7_cascade5"]=	"field93";
portGroup[inputTag+"_7"].cos[inputTag+"_7_cascade6"]=	"field94";
portGroup[inputTag+"_7"].cos[inputTag+"_7_cascade7"]=	"field95";
portGroup[inputTag+"_7"].cos[inputTag+"_7_cascade8"]=	"field96";
//输入8联动输出8个	
portGroup[inputTag+"_8"].cos[inputTag+"_8_cascade1"]=	"field97";
portGroup[inputTag+"_8"].cos[inputTag+"_8_cascade2"]=	"field98";
portGroup[inputTag+"_8"].cos[inputTag+"_8_cascade3"]=	"field99";
portGroup[inputTag+"_8"].cos[inputTag+"_8_cascade4"]=	"field100";
portGroup[inputTag+"_8"].cos[inputTag+"_8_cascade5"]=	"field101";
portGroup[inputTag+"_8"].cos[inputTag+"_8_cascade6"]=	"field102";
portGroup[inputTag+"_8"].cos[inputTag+"_8_cascade7"]=	"field103";
portGroup[inputTag+"_8"].cos[inputTag+"_8_cascade8"]=	"field104";
//输出1-8  pn 输出1名称            pp  暂无     pu 输出隶属属性
portGroup[outputTag+"_1"]=	{pn:"field113",	pp:"nofield",	pu:'field122',sound:[]	};
portGroup[outputTag+"_1"].sound["conns"]="field139";portGroup[outputTag+"_1"].sound["clos"]="field147";
portGroup[outputTag+"_2"]=	{pn:"field114",	pp:"nofield",	pu:'field123',sound:[]	};
portGroup[outputTag+"_2"].sound["conns"]="field140";portGroup[outputTag+"_2"].sound["clos"]="field148";
portGroup[outputTag+"_3"]=	{pn:"field115",	pp:"nofield",	pu:'field124',sound:[]	};
portGroup[outputTag+"_3"].sound["conns"]="field141";portGroup[outputTag+"_3"].sound["clos"]="field149";
portGroup[outputTag+"_4"]=	{pn:"field116",	pp:"nofield",	pu:'field125',sound:[]	};
portGroup[outputTag+"_4"].sound["conns"]="field142";portGroup[outputTag+"_4"].sound["clos"]="field150";
portGroup[outputTag+"_5"]=	{pn:"field117",	pp:"nofield",	pu:'field126',sound:[]	};
portGroup[outputTag+"_5"].sound["conns"]="field143";portGroup[outputTag+"_5"].sound["clos"]="field151";
portGroup[outputTag+"_6"]=	{pn:"field118",	pp:"nofield",	pu:'field127',sound:[]	};
portGroup[outputTag+"_6"].sound["conns"]="field144";portGroup[outputTag+"_6"].sound["clos"]="field152";
portGroup[outputTag+"_7"]=	{pn:"field119",	pp:"nofield",	pu:'field128',sound:[]	};
portGroup[outputTag+"_7"].sound["conns"]="field145";portGroup[outputTag+"_7"].sound["clos"]="field153";
portGroup[outputTag+"_8"]=	{pn:"field120",	pp:"nofield",	pu:'field129',sound:[]	};
portGroup[outputTag+"_8"].sound["conns"]="field146";portGroup[outputTag+"_8"].sound["clos"]="field154";




Ext.onReady(function(){
	Ext.QuickTips.init();
/****
 * 根据一层设备号加载设备下的用户
 */
var refreshUserStore=function (firsetEquipmentId){
userStore.load({params:{"deviceID":firsetEquipmentId,"utype":"3","forPort":"1",callback:function(){}, 	 add:false}});
}
	
	/***
	 * 输入界面 构建
	 */
	var inputPortViewBuilder=function(ele_id){
		return new Ext.TabPanel({
					id:ele_id,
                    border: false, // already wrapped so don't add another border
                    activeTab: 0, // second tab initially active
                    title: '输入',                    
                   //	height:600,
                    autoHeight:true,
                   	frame:true,
                    items: [inputPortElementBuilder(ele_id+"_1",'输入端口1','1'),
	                        inputPortElementBuilder(ele_id+"_2",'输入端口2','2'),
	                        inputPortElementBuilder(ele_id+"_3",'输入端口3','4'),
	                        inputPortElementBuilder(ele_id+"_4",'输入端口4','8'),
	                        inputPortElementBuilder(ele_id+"_5",'输入端口5','16'),
	                        inputPortElementBuilder(ele_id+"_6",'输入端口6','32'),
	                        inputPortElementBuilder(ele_id+"_7",'输入端口7','64'),
	                        inputPortElementBuilder(ele_id+"_8",'输入端口8','128')
                    	  ]
                    ,listeners:{
                    	tabchange:function(parentPanel,thisTab){
                    		var tabID=thisTab.getId();
                    		var xx=new String();
                    		var sdc=Ext.getCmp('secondDeviceCombo');
                    		if(!Ext.isEmpty(sdc.getValue())){
	                    		var record=sdc.getStore().getById(sdc.getValue());
	                    		if(!Ext.isEmpty(record)){
	                    		
	                    		//otherDataLoad
	                    		setSecondInfo(record);
	                    		}
                    		}
                    	}
                    }
                });		
	};
		/***
	 * 输出界面 构建
	 */
	var outputPortViewBuilder=function(ele_id){
		return new Ext.TabPanel({
					id:ele_id,
                    border: false, // already wrapped so don't add another border
                    activeTab: 0, // second tab initially active
                    title: '输出',                    
                   //	height:500,
                    autoHeight:true,
                   	frame:true,
                    items: [outputPortElementBuilder(ele_id+"_1",'输出端口1','1'),
	                        outputPortElementBuilder(ele_id+"_2",'输出端口2','2'),
	                        outputPortElementBuilder(ele_id+"_3",'输出端口3','4'),
	                        outputPortElementBuilder(ele_id+"_4",'输出端口4','8'),
	                        outputPortElementBuilder(ele_id+"_5",'输出端口5','16'),
	                        outputPortElementBuilder(ele_id+"_6",'输出端口6','32'),
	                        outputPortElementBuilder(ele_id+"_7",'输出端口7','64'),
	                        outputPortElementBuilder(ele_id+"_8",'输出端口8','128')
                    	  ]
                   	  ,listeners:{
                    	tabchange:function(parentPanel,thisTab){
                    		var tabID=thisTab.getId();
                    		var xx=new String();
                    		var sdc=Ext.getCmp('secondDeviceCombo');
                    		if(!Ext.isEmpty(sdc.getValue())){
	                    		var record=sdc.getStore().getById(sdc.getValue());
	                    		if(!Ext.isEmpty(record)){
	                    			setSecondInfo(record);
	                    		//otherDataLoad(record);
	                    		}
                    		}
                    	}
                    
                    }
                });		
	};
	 // var tempSecondIdCmp =null,tempFristIdCmp=null;  
	
	  inPortComStore=new Ext.data.Store({  proxy:new Ext.data.HttpProxy({ url: datapath.queryInPortURL}),
									   reader:	new Ext.data.JsonReader(
									   	{totalProperty:'total',root:'array',idProperty:'id'},
										[{name:'id'},{name:'name'}])
										,listeners:{ 
										load:function(store,records,options){ 
											initInputStore_target.setValue(pub_in_port);
										}
										}
								    });
	  /**
	   * new Ext.data.SimpleStore({fields:['value','text'],
					data:[['1','输入1'],	['2','输入2'],	['4','输入3'],	['8','输入4'],	['16','输入5'],	['32','输入6'],	['64','输入7'],	['128','输入8']]});
	var  outPortComStore=new Ext.data.SimpleStore({fields:['value','text'],
					data:[['1','输出1'],	['2','输出2'],	['4','输出3'],	['8','输出4'],	['16','输出5'],	['32','输出6'],	['64','输出7'],	['128','输出8']]});
					**/
					
	   outPortComStore=new Ext.data.Store({  proxy:new Ext.data.HttpProxy({ url: datapath.queryOutPortURL}),
									   reader:	new Ext.data.JsonReader(
										{totalProperty:'total',root:'array',idProperty:'id'},
										[{name:'id'},{name:'name'}])
										,listeners:{ 
										load:function(store,records,options){ 
											initOutputStore_target.setValue(pub_out_port);
										
										}
										}
								    });
	   inputProComStore=new Ext.data.SimpleStore({fields:['value','text'],
					data:[['0','屏蔽输入'],	['1','普通输入'],	['2','立即防区'],	['3','24小时防区'],	['4','火警']]});
	//动态加载---------/
	/*var  userStore=new Ext.data.SimpleStore({fields:['value','text'],
					data:[['0','0'],	['1','1'],	['2','2'],	['3','3'],	['4','4'],	['5','5'],	['6','6'],	['7','7'],	['8','8'],	['9','9'],	['10','10']
					,	['11','11'],	['12','12'],	['13','13'],	['14','14'],	['15','15'],	['16','16']]});
					*/
	var userStore=	new Ext.data.Store({  proxy:new Ext.data.HttpProxy({ url: datapath.queryUserURL}),
									   reader:	new Ext.data.JsonReader(
									   	{totalProperty:'total',root:'array',idProperty:'field12'},
										[{name:'field1'},{name:'field12'},{name:'field3'}
										,{name:'field4'},{name:'field6'},{name:'field7'}
										,{name:'field8'},{name:'field9'},{name:'field10'}])
								    });
	var soundStore=new Ext.data.SimpleStore({fields:["value",'text']
					,data:[['1','滴滴音'],['2','鸡鸣音'],['3','砍材音'],['4','sing the song'],['5','等你一万年'],['6','千千厥歌'],['7','智能ACD'],['8','急救音'],['9','警笛音']]});
	//输入触发提示音				
	var soundStore_cf=new Ext.data.SimpleStore({fields:["value",'text']
					,data:[['1','触发提示音']]});
	//输入恢复提示音	
	var soundStore_hf=new Ext.data.SimpleStore({fields:["value",'text']
					,data:[['2','恢复提示音']]});
	//输出断开
	var soundStore_srdk=new Ext.data.SimpleStore({fields:["value",'text']
					,data:[['3','输出断开提示音']]});
	//输出闭合 
	var soundStore_srbh=new Ext.data.SimpleStore({fields:["value",'text']
					,data:[['4','输出闭合提示音']]});
							
	var cascadeStore=new Ext.data.SimpleStore({fields:["value",'text']
					,data:[['1','禁止联动,只能手动操作'],['2','任何状态下触发闭合未触发断开'],['3','任何状态下触发断开未触发闭合'],['4','布防状态下触发闭合未触发断开']
					,['5','布防状态下触发断开未触发闭合'],['6','撤防状态下触发闭合未触发断开'],['7','撤防状态下触发断开未触发闭合']]});					
					
/**

	 * 输入端口  构建
	 */
	var inputPortElementBuilder=function(ele_id,tabname,theNum){
		var ipe1= new Ext.FormPanel({id:ele_id+"_top",collapsible:true,title:'端口信息',buttonAlign:'center',autoHeight:true,layout:'form',defaults:{labelAlign:'right'},frame:true,items:[
		  {xtype:'fieldset',layout:'column',title:'端口设置',margins:'5 5 5 5',padding:'5 5 5 5',items:[
			{xtype:'fieldset',layout:'form',border:false, width:300,items:[
				{xtype:'textfield',fieldLabel:'输入名称',name:'inputName',maxLength:12}
				,{xtype:'combo',fieldLabel:'触发提示音',name:'trigger',hiddenName:'trigger',store:soundStore_cf,valueField:'value',displayField:'text',editable:false,mode:'local',width:100,triggerAction:'all'}
			]},
			{xtype:'fieldset',layout:'form',border:false,width:250,items:[
				{xtype:'combo',fieldLabel:'输入属性',name:'inputP',hiddenName:'inputP',store:inputProComStore,valueField:'value',displayField:'text',editable:false,mode:'local',width:100,triggerAction:'all'}
				,{xtype:'combo',fieldLabel:'恢复提示音',name:'recovery',hiddenName:'recovery',store:soundStore_hf,valueField:'value',displayField:'text',editable:false,mode:'local',width:100,triggerAction:'all'}
			]},
			{xtype:'fieldset',layout:'form',border:false,width:350,items:[
				{xtype:'combo',fieldLabel:'对应二级用户',hiddenName:'userId',store:userStore,valueField:'field12',displayField:'field3',editable:false,mode:'local',width:150,triggerAction:'all'}
				,{xtype:'textfield',name:'inputNumTag',hidden:true,value:""+theNum}
			]}
			]},
		
		{xtype:'fieldset',layout:'column',title:'跟随输入',items:[
			{xtype:'fieldset',layout:'form',border:false,width:300,items:[
			{xtype:'combo',width:150,fieldLabel:'二层设备',hiddenName:'targetSecondID',editable:false,mode:'local',triggerAction:'all',
			store:secondDeviceStore,valueField:'field4',displayField:'field121',listeners:{select:function(combo,record,index){  
										   try{  
										   	//选择交叉设备后，去加载端口
										  	 	if(record&&record.data&&record.data.field3&&(record.data.field4+"")!="")
											 	 initInputStore(record.data.field3,record.data.field4)
											   } catch(ex){
									 		  } 
									  }  }
			}]}
			,{xtype:'fieldset',layout:'form',border:false,width:300,
			items:[{xtype:'combo',width:300,fieldLabel:'输入端口',hiddenName:"targetInPort",editable:false,mode:'local',width:100,triggerAction:'all',
			store:inPortComStore,valueField:'id',displayField:'name'		
			}]}
			,{xtype:'fieldset',layout:'form',border:false,width:300,items:[{xtype:'checkbox',column:.33,boxLabel:'跟随禁止',name:'targetDisable',inputValue:'1',
							listeners:{check:function(){
									bform=ipe1.getForm();
									 if(this.checked){
											//bform.findField("targetFirstID").hide();
											bform.findField("targetSecondID").hide();
											bform.findField("targetInPort").hide();
										}else{
											//bform.findField("targetFirstID").show();
											bform.findField("targetSecondID").show();
											bform.findField("targetInPort").show();
										}  
								}
							}
							}
			                                                               ,{xtype:'textfield',name:'targetFirstID',hidden:true,value:''}]}
			]}
		],buttons:[{text:'保存',handler:function(a,b,c){
				/*console.log(a);
				console.log(b);
				console.log(ipe1);*/
			//alert('yes');
			//return;
			if(hasDevice()){
				var ta=ipe1.getForm().findField("targetSecondID").getValue();
				var tb=ipe1.getForm().findField("targetInPort").getValue();
				/*if(ta==''||tb==0||tb==''){
					 Ext.Msg.alert("警告","请选择跟随端口！");
					return;
				}*/
			if(ipe1.getForm().isValid()){
		  	 	pingBi();
				  ipe1.getForm().submit({ url: datapath.saveInPorURL,params:getDeviceID(),
						                    success: function(f, o){
												if(o.result.code==300){
													refreshUtils(o.result.returnVal,null,function(){
														Ext.Msg.alert('提示','操作成功！');
														refreshSecondDevice();
													});
												}else if(o.result.code>=900){
													quXiaoPingBi();
												 	Ext.Msg.alert('提示',(o.result.message&&o.result.message!="")?o.result.message:"操作失败");
												}
						                    },failure:function(f, o,x){
						                    	quXiaoPingBi();
												Ext.Msg.alert("失败",(o.result?o.result.message:"访问服务器失败！"));
											}
						                });
		}else Ext.Msg.alert("提示","数据格式不合法,请重新输入！ &nbsp;");
			}else Ext.Msg.alert("警告","请选择需要操作的二层设备,否则无法执行操作！") 
			 
		}}]});
		var ipe2=portCascadetViewBuilder(ele_id,theNum);		
		return new Ext.Panel({id:ele_id,buttonAlign:'center',autoHeight:true,layout:'form',defaults:{labelAlign:'right'},frame:true,title:tabname,items:[ipe1,ipe2]});
		
	
	};
		/**
	 * 输出端口  构建
	 */
	var outputPortElementBuilder=function(ele_id,tabname,theNum){
	var outForm= new Ext.FormPanel({id:ele_id,layout:'form',buttonAlign:'center',defaults:{labelAlign:'right'},autoHeight:true,frame:true,title:tabname,
	items:[{xtype:'fieldset',layout:'column',title:'输出端口信息',items:[
		{xtype:'fieldset',column:.3,border:false,items:[
			{xtype:'textfield',fieldLabel:'输出名称',name:'outName'},
			{xtype:'combo',fieldLabel:'闭合提示音', hiddenName:'connSound',store:soundStore_srbh,valueField:'value',displayField:'text',editable:false,mode:'local',width:100,triggerAction:'all'}
		]},
		{xtype:'fieldset',column:.3,border:false,items:[
		//{xtype:'textfield',fieldLabel:'输出属性',name:'outP'},
		{xtype:'combo',fieldLabel:'断开提示音', hiddenName:'closeSound',store:soundStore_srdk,valueField:'value',displayField:'text',editable:false,mode:'local',width:100,triggerAction:'all'}
		,{xtype:'textfield',name:'outputNumTag',hidden:true, value:''+theNum }
		]}
		]}]
	,buttons:[{text:'保存',handler:function(){	
			if(outForm.getForm().isValid()){
		  	  pingBi();
			  outForm.getForm().submit({ url: datapath.saveoutPorURL,params:getDeviceID(),
			                    success: function(f, o){
									if(o.result.code==300){
										refreshUtils(o.result.returnVal,null,function(){
											Ext.Msg.alert('提示','操作成功！');
											refreshSecondDevice();
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
	}}	
	]});
	return outForm;
	};
	/***
	 * 联动设置  构建
	 */
	var portCascadetViewBuilder=function(ele_id,inputNum){
		return new Ext.TabPanel({
					id:ele_id+'_cascade',
                    border: false, // already wrapped so don't add another border
                    activeTab: 0, // second tab initially active
                    //title: '联动设置',                    
                   	autoHeight:true, 
                   	frame:true,
                    items: [portCascadetElementBuilder(ele_id+"_cascade1",'联动输出1','1',inputNum),
	                        portCascadetElementBuilder(ele_id+"_cascade2",'联动输出2','2',inputNum),
	                        portCascadetElementBuilder(ele_id+"_cascade3",'联动输出3','3',inputNum),
	                        portCascadetElementBuilder(ele_id+"_cascade4",'联动输出4','4',inputNum),
	                        portCascadetElementBuilder(ele_id+"_cascade5",'联动输出5','5',inputNum),
	                        portCascadetElementBuilder(ele_id+"_cascade6",'联动输出6','6',inputNum),
	                        portCascadetElementBuilder(ele_id+"_cascade7",'联动输出7','7',inputNum),
	                        portCascadetElementBuilder(ele_id+"_cascade8",'联动输出8','8',inputNum)
                    	  ]
        	 	   ,listeners:{
                    	tabchange:function(parentPanel,thisTab){
                    		var tabID=thisTab.getId();
                    		var xx=new String();
                    		var sdc=Ext.getCmp('secondDeviceCombo');
                    		if(!Ext.isEmpty(sdc.getValue())){
	                    		var record=sdc.getStore().getById(sdc.getValue());
	                    		if(!Ext.isEmpty(record)){
	                    		setSecondInfo(record);//(record);otherDataLoad(record);
	                    		}
                    		}
                    	}
                    
                    }
                });		
	};
	/***
	 *  构建联动输出tab
	 */
var portCascadetElementBuilder=function(ele_id,tabName,theNum,inputNum){
	var myCascade=new Ext.FormPanel({id:ele_id,title:tabName,buttonAlign:'center',autoHeight:true
	,layout:'column',defaults:{labelAlign:'right',labelWidth:80},frame:true,items:[
		//{xtype:'fieldset',layout:'column',items:[
				{xtype:'fieldset',layout:'form',border:false,width:200,items:[
					{xtype:'combo',width:90,fieldLabel:'二层设备',hiddenName:'cascadeSecondCode',editable:false,mode:'local',triggerAction:'all',
					store:secondDeviceStore,valueField:'field4',displayField:'field121',listeners:{select:function(combo,record,index){  
										   try{  
										   	//选择交叉设备后，去加载端口
										  	 	if(record&&record.data&&record.data.field3&&(record.data.field4+"")!="")
											 	 initOutputStore(record.data.field3,record.data.field4)
											   } catch(ex){
									 		  } 
									  }  }
				}
				]},
				{xtype:'fieldset',layout:'form',border:false,width:180,items:[
					{xtype:'combo',fieldLabel:'输出端口',hiddenName:'cascadeOutPort',store:outPortComStore,editable:false
					,mode:'local',width:70,triggerAction:'all',valueField:'id',displayField:'name'}
				]},
				{xtype:'fieldset',layout:'form',border:false,width:300,items:[
					{xtype:'combo',fieldLabel:'联动属性',width:180,hiddenName:'cascadeP',store:cascadeStore,
					valueField:'value',displayField:'text',editable:false,mode:'local',triggerAction:'all'}
				]},
				{xtype:'fieldset',layout:'form',border:false,width:180,items:[
					{xtype:'checkbox',boxLabel:'联动禁止',width:70,name:'cascadeDisable',inputValue:'1'
					,listeners:{check:function(){
									bform=myCascade.getForm();
									 if(this.checked){
											//bform.findField("targetFirstID").hide();
											bform.findField("cascadeSecondCode").hide();
											bform.findField("cascadeOutPort").hide();
											bform.findField("cascadeP").hide();
										}else{
											//bform.findField("targetFirstID").show();
											bform.findField("cascadeSecondCode").show();
											bform.findField("cascadeOutPort").show();
											bform.findField("cascadeP").show();
										}  
								}
							}
					}
					,{xtype:'textfield',name:'cascadeTag',hidden:true,value:''+theNum}
					,{xtype:'textfield',name:'inputNumTag',hidden:true,value:''+inputNum}
					
				]}
				//]}
		],buttons:[{text:'保存',handler:function(){
			//var t_csc=myCascade.getForm().findField("cascadeSecondCode").getValue();
			var t_cop=myCascade.getForm().findField("cascadeOutPort").getValue();
			var t_ccp=myCascade.getForm().findField("cascadeP").getValue();
			var t_cd=myCascade.getForm().findField("cascadeDisable").getValue();
		/*	alert(t_csc);
			alert(t_cop);
			alert(t_ccp);*/
			if(!t_cd&&(t_cop==0||t_ccp==0)){
				Ext.Msg.alert('提示','请选择端口及属性.');
			return ;
			}
			
			if(myCascade.getForm().isValid()){
			  	 	pingBi();
				  myCascade.getForm().submit({ url: datapath.saveCascadePortURL,params:getDeviceID(),
                    success: function(f, o){
						if(o.result.code==300){
							refreshUtils(o.result.returnVal,null,function(){
								Ext.Msg.alert('提示','操作成功！');
								refreshSecondDevice();
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
		}}]});
		return myCascade;
};
	
	
	/*leftTree=new Ext.tree.TreePanel({
								title:'设备树',
								collapsible:true,
								region:'west',
						        layout:'fit',
						        root: new Ext.tree.AsyncTreeNode({id : 'root_001',text : '设备',draggable : false,expanded : true}),
						        width:200, 
						       // height:500, 
						        animate:true,//开启动画效果 
						        draggable:false,//不允许子节点拖动  
						        loader:new Ext.tree.TreeLoader({dataUrl:datapath.querydeviceTreeURL}) ,
						     	autoScroll:true,
						        frame: true
						   		,listeners:{
						        	load:function(){//当是二层设备保存时刷新了树，就需要找到对应的一层设备并展开树
						        		 if(myevent=="sf") lockTreeNode(this,"first_"+fselect);
									  }
						        } 
						    });	*/
firstDeviceStore=new  Ext.data.Store({
								      proxy:new Ext.data.HttpProxy({ url: datapath.queryFirstDevice}),
									   reader:	new Ext.data.JsonReader(
									   	{totalProperty:'total',root:'array',idProperty:'field3'},
										[{name:'field3'}//设备地址
										,{name:'field121'}//设备名称
										])
										,listeners:{
						        	load:function(){
						        		if(myevent=="sf")//当二层设备修改后，需要刷新一层设备，此时需要默认选中之前的一层设备
						        		{
						        		  Ext.getCmp("firstDeviceCombo").setValue(fselect);
										  Ext.getCmp("firstDeviceCombo").hiddenField.value=fselect;
						        		}
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
		if(myevent=="fs"){//一层设置选择后，刷新了二层设备，这时候需要默认选中第一个设备 
		 Ext.getCmp("secondDeviceCombo").setValue(record[0].data.field4);
		 Ext.getCmp("secondDeviceCombo").hiddenField.value=record[0].data.field4;
		 setSecondInfo(record[0]);
		}else if(myevent=="sf"){
		//二层保存后，需要默认选择之前被修改的项
		 Ext.getCmp("secondDeviceCombo").setValue(sselect);
		 Ext.getCmp("secondDeviceCombo").hiddenField.value=sselect;
		 for(var i=0;i<record.length;i++){
		 	if(record[i].data.field4==sselect){
		 	setSecondInfo(reocrd[i]);
		 	break;
		 	}
		 }
		 
		}
	}
	}
	});
	rightForm_top=new Ext.FormPanel({
	id:'deviceBascInfo',
	layout:'column',
	buttonAlign:'center',
	padding:'5 0 5 0',
	title:'设备信息',
	collapsible:true,
	border:false,
	frame:true,
	defaults:{border:false,labelAlign:'right',margins:'5 5 5 5'},
	items:[new Ext.form.FieldSet({layout:'form',width:300,items:[{id:'firstDeviceCombo',hiddenName:'firstDevice',xtype:'combo',fieldLabel:'一层设备',  width:140,emptyText:'请选择设备'
	,store:firstDeviceStore,triggerAction:'all',editable:false,valueField:'field3',displayField:'field121',
							  listeners:{// 百美善
									  select:function(combo,record,index){  
										   try{  
										   		fselect=this.value;
										   		myevent="fs";//当一层设备被选中时，要将事件设置为fs，
										 	    var userCombo = Ext.getCmp("secondDeviceCombo");  
										 		 userCombo.store.removeAll();  
											 	 userCombo.store.load({params:{"equipmentFid":this.value}});
											 	 //lockTreeNode(leftTree,"first_"+this.value);//同时展开左侧对应的树节点
											 	 refreshUserStore(this.value);//切换一层设备就要刷新用户下拉框数据
											 	 rightform_tab.show();
											   } catch(ex){
									 			  Ext.MessageBox.alert("错误","数据加载失败。");
									 		 
									 		  } 
									  }  
								}   
							},{xtype:'textfield',name:'deviceName',id:'deviceNameVal',fieldLabel:'设备名称',maxLength:12,allowBlank:false},
								{xtype:'textfield',editable:false,readOnly :true,name:'inCount',inputName:"inCount",id:"inCountCmp",fieldLabel:'输入个数',
								width:80},{xtype:'checkbox',name:'deviceDisable',boxLabel:'禁用此设备',inputValue:'1'}]}),
							
		   			new Ext.form.FieldSet({layout:'form',width:300,items:[{id:'secondDeviceCombo',hiddenName:'secondDevice',xtype:'combo',fieldLabel:'二层设备',triggerAction:'all',mode:'local',width:140,emptyText:'先选一层设备',editable:false,
								store:secondDeviceStore,valueField:'field4',displayField:'field121',
								    listeners:{ 
								    	select:function(combo,record,index){ 
								    		sselect=this.value;
								    		if(rightform_tab_inputTab)rightform_tab_inputTab.activate(0);
											if(rightform_tab_outputTab)rightform_tab_outputTab.activate(0);
								    		setSecondInfo(record);
								    	}
								    }
							}
							,{xtype:'textfield',name:'dtype',fieldLabel:'设备属性',readOnly:true}
							,{xtype:'textfield',editable:false,name:'outCount', id:"outCountCmp", fieldLabel:'输出个数',width:80,readOnly :true}
							]})
					/*,new Ext.form.FieldSet({layout:'form',width:250,items:[
						
						//,{xtype:'textfield',name:'deviceLayer',hidden:true}
						//,{xtype:'textfield',name:'deviceTypeBak',hidden:true}
						//,{xtype:'textfield',name:'firstIndex',hidden:true}
						//,{xtype:'textfield',name:'secondIndex',hidden:true}
					]})*/
		  ]		 
		  ,buttons:[ {xtype:'button',text: ' 保 存 ',handler:function(){
							  	if((rightForm_top.getForm().findField("secondDevice").getValue()+"")==""){Ext.Msg.alert('提示','请选择设备 ！'); return ;}
							  	{
							  		var str=rightForm_top.getForm().findField("deviceName").getValue();
								      var   myLen = 0; 
								        for (var i = 0; i < str.length; i++) {
								            if (str.charCodeAt(i) > 0 && str.charCodeAt(i) < 128)
								                myLen++;
								            else
								                myLen += 2;
								        }
									if(myLen>12){
										Ext.Msg.alert("警告","设备名称不得超过12个字（一个中文占2字，英文点一个）！");
										return ;
									}	
									if(myLen==0){
										Ext.Msg.alert("警告","设备名称必须填写！");
										return ;
									}	  
							  }
															  	
							  	if(rightForm_top.getForm().isValid()){
							  	  pingBi();
								  rightForm_top.getForm().submit({ url: datapath.saveDeviceURL,
										                    success: function(f, o){
																if(o.result.code==300){
																	refreshUtils(o.result.returnVal/*,null,function(){
																		Ext.Msg.alert('提示','操作成功！');
																		//refreshDeviceData();
																	}*/);
																}else if(o.result.code>=900){
																	quXiaoPingBi();
																 	Ext.Msg.alert('提示',o.result.message);
																}
										                    },failure:function(f, o,x){
										                    	quXiaoPingBi();
																Ext.Msg.alert("失败",o.result.message);
															}
										                });
							  	}else Ext.Msg.alert("提示","数据格式不合法,请重新输入！ &nbsp;");
		  }}]
	});		
	
	rightform_tab_inputTab=inputPortViewBuilder(inputTag+"");
	rightform_tab_outputTab= outputPortViewBuilder(outputTag+"");
	rightform_tab=new Ext.TabPanel({
                    border: false, // already wrapped so don't add another border
                    activeTab: 0, // second tab initially active
                    tabPosition: 'top',         
                   	//height:700,
                    autoHeight:true,
                    items: [rightform_tab_inputTab,rightform_tab_outputTab]
                    ,listeners:{
                    	tabchange:function(parentPanel,thisTab){
                    		var tabID=thisTab.getId();
                    		var xx=new String();
                    		var sdc=Ext.getCmp('secondDeviceCombo');
                    		if(!Ext.isEmpty(sdc.getValue())){
	                    		var record=sdc.getStore().getById(sdc.getValue());
	                    		if(!Ext.isEmpty(record)){
	                    		setSecondInfo(record);//otherDataLoad(record);
	                    		}
                    		}
                    	}
                    
                    }
                }); 
					    
	rightForm=new Ext.Panel({
								autoScroll:true,
								region:'center',
								items:[rightForm_top,rightform_tab],
								layout:'form'
								});	//leftTree,
	winPanel=new Ext.Viewport({layout:'border',items:[rightForm]});//leftTree,
	rightform_tab.hide();

var loadformDataForFirst=function(rec){
	var tempForm= Ext.getCmp('deviceBascInfo');
	tempForm.findfield("").clearValue();
	var vals={'deviceName':null,'deviceType':"",'deviceDisable':false,'deviceLayer':'','deviceTypeBak':""};
	tempForm.getForm().setValues(vals);
	rightform_tab.hide();
};
var loadformDataForSecond=function(rec){
	var tempForm= Ext.getCmp('deviceBascInfo');													//130 的值 1=开启，2=屏蔽
	var vals={'deviceName':rec.get("field121"),'dtype':rec.get("dtype"),'deviceDisable':(rec.get("field130")==2),'inCount':rec.get("inCount"),'outCount':rec.get("outCount")};
	tempForm.getForm().setValues(vals);
	rightform_tab.show();
};
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
/**操作成功后强制刷新  设备树与下拉设置**/
var  refreshDeviceData=function(){
	  
	 /* leftTree.getLoader().load(leftTree.getRootNode(),function(treeNode){
	   treeNode.expand(true,false);
	   lockTreeNode (leftTree, "first_"+fselect);
	  });*/
	    myevent="sf"
	  	secondDeviceStore.reload();
	  	rightForm_top.getForm().findField("secondDevice").setRawValue(rightForm_top.getForm().findField("deviceNameVal").getValue());
	  	Ext.getCmp("secondDeviceCombo").hiddenField.value=sselect;
	  //var tag=rightForm_top.getForm().findField("deviceLayer").getValue();
/*	  if(tag=='first'){
	  	 firstDeviceStore.reload();
	  	 rightForm_top.getForm().findField("firstDevice").setRawValue(rightForm_top.getForm().findField("deviceNameVal").getValue());
	  	 Ext.getCmp("firstDeviceCombo").hiddenField.value=rightForm_top.getForm().findField("firstIndex").getValue();
	  }
	  else if(tag=='second'){*/
	  //}
}
/**操作成功后强制刷新  设备树与下拉设置**/
var  refreshSecondDevice=function(){
	  	//secondDeviceStore.reload();
}
/***
 * 格式化数据
 */
var otherDataLoad=function(rec){
	//rightform_tab.activate(0);
	var atab=rightform_tab.getActiveTab();
	//var outOne=atab.getActiveTab();
		//输入界面
		if(atab.getId()==inputTag+""){
			inputPortDataFormat(rec,atab);
			//输出界面 
		}else if(atab.getId()==outputTag+""){
				outputPortDataFormat(rec,atab);
		}
		
			//根据输出与输出控制 。显示输入输出控制面板
	  	{
	  		//alert(record.get("outCount")+"**"+record.get("inCount"));
	  		 var ovs=Ext.getCmp("outCountCmp").getValue();
	  		 var ivs=Ext.getCmp("inCountCmp").getValue();
	  		 //alert(ovs+"|"+ivs);
	  		 if(ovs==''){
  		 		for(var i=1;i<=8;i++){
					//rightform_tab_outputTab.hideTabStripItem(outputTag+"_"+i+"_top");
					rightform_tab_outputTab.hideTabStripItem((i-1));
  		 		}
	  		 }else if(atab.getId()==outputTag+""){
	  		 	var ivsI=window.parseInt(ovs);
	  		 	for(var i=1;i<=8;i++){
	  		 		if(ivsI>=i){
						//rightform_tab_outputTab.unhideTabStripItem(outputTag+"_"+i+"_top");
						rightform_tab_outputTab.unhideTabStripItem((i-1));
	  		 		}else{
	  		 			//rightform_tab_outputTab.hideTabStripItem(outputTag+"_"+i+"_top");
	  		 			rightform_tab_outputTab.hideTabStripItem((i-1));
	  		 		}
  		 		}
	  		 }
	  		 if(ivs==''){
	  		 	for(var i=1;i<=8;i++){
	  		 		//rightform_tab_inputTab.hideTabStripItem(inputTag+"_"+i+"_top");
	  		 		rightform_tab_inputTab.hideTabStripItem((i-1));
	  		 	}
	  		 }else if(atab.getId()==inputTag+""){
	  		 	var ivsI=window.parseInt(ivs);
	  			for(var i=1;i<=8;i++){
	  				if(ivsI>=i){
						//rightform_tab_inputTab.unhideTabStripItem(inputTag+"_"+i+"_top");
						rightform_tab_inputTab.unhideTabStripItem((i-1));
	  				}else{
	  					//rightform_tab_inputTab.hideTabStripItem(inputTag+"_"+i+"_top");
	  					rightform_tab_inputTab.hideTabStripItem((i-1));
	  				}
  		 		}
	  		 }
	  	}	

};
// 跟随输入 值      联动输出 值 
var pub_in_port,pub_out_port;
var inputPortDataFormat=function(rec,atab){
	var tabid=atab.getActiveTab().getId();
	if(tabid!=null&&tabid!=""){
	var tempIndex=portGroup[tabid];
	if(tempIndex!=null){
	var bform=Ext.getCmp(tabid+"_top").getForm();
		bform.findField("inputName").setValue(rec.get(tempIndex.pn));
		bform.findField("inputP").setValue(rec.get(tempIndex.pp));
		bform.findField("userId").setValue(rec.get(tempIndex.pu));
		
		bform.findField("trigger").setValue(rec.get(tempIndex.sound["trigger"]));
		bform.findField("recovery").setValue(rec.get(tempIndex.sound["recovery"]));
		
		
		var cross=""+rec.get(tempIndex.pc);
		if(cross!=null&&cross!=""){
		var cinfo=cross.split(",")
		bform.findField("targetFirstID").setValue(cinfo[0]);
		bform.findField("targetSecondID").setValue(cinfo[1]);
		bform.findField("targetInPort").setValue(cinfo[2]);
		
		pub_in_port=cinfo[2];
		
		bform.findField("targetDisable").setValue(cinfo[2]=="0"?true:false);
		}
		//cos
		var cascadeTab=Ext.getCmp(tabid+"_cascade").getActiveTab();
		var cascadeIndex=""+tempIndex.cos[cascadeTab.getId()];
		var cascadeInfo=rec.get(cascadeIndex);
		if(cascadeInfo!=null&&cascadeInfo!=""){
	    var casInfo=cascadeInfo.split(",")
		var cform=cascadeTab.getForm();
			//cform.findField("cascadeFirstCode").setValue(casInfo[0]);
			cform.findField("cascadeSecondCode").setValue(casInfo[1]);
			cform.findField("cascadeOutPort").setValue(casInfo[2]);
			cform.findField("cascadeP").setValue(casInfo[3]);
			cform.findField("cascadeDisable").setValue((casInfo[4]=="1"?true:false));
			pub_out_port=casInfo[2];
		}
	}
	}
	
	

};
/***
 * 输出信息格式化
 * @param {} rec
 * @param {} atab
 */
var outputPortDataFormat=function(rec,atab){
	var tabid=atab.getActiveTab().getId();
	if(tabid!=null&&tabid!=""){
	var tempIndex=portGroup[tabid];
	if(tempIndex!=null){
	var bform=Ext.getCmp(tabid).getForm();
		bform.findField("outName").setValue(rec.get(tempIndex.pn));
		bform.findField("connSound").setValue(rec.get(tempIndex.sound["conns"]));
		bform.findField("closeSound").setValue(rec.get(tempIndex.sound["clos"]));
	}
	}
};
var  getDeviceID=function(){
//		Ext.Msg.alert("fd:"+Ext.getCmp("firstDeviceCombo").getValue()+", sd:"+Ext.getCmp("secondDeviceCombo").getValue());
	var f_d=Ext.getCmp("firstDeviceCombo").getValue();
	var s_d=Ext.getCmp("secondDeviceCombo").getValue();
	return {"firstDevice":f_d,"secondDevice":s_d};
}
var hasDevice=function(){
			var f_d=Ext.getCmp("firstDeviceCombo").getValue();
			var s_d=Ext.getCmp("secondDeviceCombo").getValue();
			return (!Ext.isEmpty(f_d)&&!Ext.isEmpty(s_d));
}
/**
 *当选择二层设备时，自动将数据加载至
 *
 *2014.2.15  需要查询出来，再用
 * **/
var setSecondInfo=function(record){
	if(record&&record.data&&record.data.field3&&(record.data.field4>-1))
	 Ext.Ajax.request({
	url:datapath.queryEquipmentInfoURL,
	params:{"field3":record.data.field3,"field4":record.data.field4},
	success: function(a){
		   	   var b = Ext.util.JSON.decode(a.responseText);                       
				if(b.success){
					  temp_equipment_data=b.returnVal;
					    temp_equipment_data.get=function(name){
					   	return this[name];
					   } 
					//  var x= tempData.get("field3");
					loadformDataForSecond(temp_equipment_data);
					otherDataLoad(temp_equipment_data);
					initStore();
				}else{
					Ext.Msg.alert('提示',b.message);
				}
				
		   	},failure:function(a){
		   		Ext.Msg.alert("提示","连接失败");
	
	}
	});
};
/**
 * 当前的二层设备的详细信息缓存 
 */
var temp_equipment_data=null;
//加载二层设备完成后，调用 此方法 ，将跟随与联动   的  输入端口与输出端口的候选项查出来
var initStore=function(){
//获取一层设备与二层设备ID.
	var target,equipmentFid,equipmentSid;
	//确定目前的选项卡
	var atab=rightform_tab.getActiveTab();
	//输入界面
		if(atab.getId()==inputTag+""){
								//输入tab
							var tabid=atab.getActiveTab().getId();
						if(tabid!=null&&tabid!=""){
								var bform=Ext.getCmp(tabid+"_top").getForm();
								initInputStore_target=bform.findField("targetInPort");
								
								equipmentFid =bform.findField("targetFirstID").getValue();
								equipmentSid =bform.findField("targetSecondID").getValue();
								initInputStore(equipmentFid,equipmentSid);
								
								//联动tab
								var cascadeTab=Ext.getCmp(tabid+"_cascade").getActiveTab();
								var cform=cascadeTab.getForm();
								initOutputStore_target=	cform.findField("cascadeOutPort");
								equipmentSid=cform.findField("cascadeSecondCode").getValue();
								//cform.findField("cascadeOutPort").setValue(casInfo[2]);
								initOutputStore(equipmentFid,equipmentSid);
						}
			
		}
}
/**
 * 
 */
var  initInputStore_target=null;
var initInputStore=function(equipmentFid,equipmentSid){
	if(equipmentFid>0&&equipmentSid>-1){
		inPortComStore.load({ params:{"equipmentFid":equipmentFid,"equipmentSid":equipmentSid}});
	}
};
/**
 * 
 */

var initOutputStore_target=null;
var initOutputStore=function(equipmentFid,equipmentSid){	
	if(equipmentFid>0&&equipmentSid>-1){
		outPortComStore.load({ params:{"equipmentFid":equipmentFid,"equipmentSid":equipmentSid}});
	}
};
});