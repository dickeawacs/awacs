var autoRefreshTree;//自动 刷新 树方法  
var split_time_for_tree=5*60*1000;
var autoRefreshTree_count=-1;
var autoRefreshTime=3500;//列表刷新时间
Ext.onReady(function(){
	 Ext.Ajax.timeout = 9000000; 
//Ext.state.Manager.setProvider(new Ext.state.CookieProvider());
/***********************************************公共变量声明******************************************************************************/
var myinit,treeHeightSet;//初始化界面数据
var openTrades;//打开一个清单列表
var work_Tab;//主工作区的选项卡区
var todayStore,todayRecord,todayCM;//今天列表的数据源
var devicesTreeLoad,devicesTree,devicesTreeRoot;//设置绔树

var table4Store,table4Table,table4ColumnModel,eventStore;
var datepath;//使用的url 集
var readFile;// 文件分析ajax
var msg,confirmsg;//消息提示框
var btools=new Ext.Toolbar();//主功能按钮
var btbtn1;//上传
var actionPlan;//业务主功能区
var hkgrid;
var ppgrid;
var updatetable4Status;//
var table4Menu;//汇款菜单
var controlMenu,beforMenu;//批次菜单 e
var updateControlStatus;//修改批次 状态
var clickCotrolInfo;//批次点击自动装载信息
var defaultMap=null;
datapath={	
		openHistoryURL:"event/openHistory.action",
		exportDataURL:"filedExport.action",
		queryDefaultUrl:"viewCurrentOnlyMap.action",
		quitURL:'login!quit.action',
		queryDeviceTreeURL:"editQ!treeStatus.action",//"editQ!deviceTree.action",
		table10DataURL:"query!queryT10.action",
		table4DataURL:'query!queryT4.action',
		portSetingURL:'admin!portseting.action',
		sysSetingURL:'admin!sysSeting.action',
		sysToolURL:'tool!page.action',
		sysResetURL:'sysManagement!sysMngment.action',
		//sysResetURL:'sysManagement!reset.action',
		userSetingURL:'admin!userSeting.action',
		preStateHelpURL:'pub!stateHelp.action',
		preOutputSetingURL:'admin!outputHelp.action',
		actionBuFangURL:'pub!bufang.action',
		actionCeFangURL:'pub!cefang.action',
		actionChaKanURL:'pub!chakan.action',
		AtsSeting:'atsSettingPage.action'
		
		};
			
			
/*****************************************************************公共方法**************************************************************************/		
var formateDate=function(obj,type){
	var re="";
	type=type||2;
		if(obj!=null){
			try{
				if(type==1){
					var year=obj.year+1900;
					var month=obj.month+1;
					var date=obj.date;
					re=year+"-"+month+"-"+date;
				}
				else if(type==2){
					var year=obj.year+1900;
					var month=obj.month+1;
					var date=obj.date;
					var hh=obj.hours;
					var mm=obj.minutes;
					var ss=new String(obj.seconds);
					re=year+"-"+month+"-"+date+" "+hh+":"+mm+":"+(ss.length==1?('0'+ss):ss);
				}
			}catch(e){
				
			};
		}
	
return re;
}
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
    /***
     * title:弹出框的标题
     * message:弹出框的显示内容
     * fn:点击确定按钮后的执行事件
     */
confirmsg= function(title, message,fn){
        Ext.Msg.confirm(title,message,fn);
    }
treeHeightSet=function(){

};
//是否自动加载
var isautoRefresh=true;

var eventMaxId=0;
/***
 * 
 */
var autoRefreshTable=function(execLoad){
	execLoad=execLoad||false;
	var tempRecord=table4Store.getAt(0);
	if(tempRecord){
	 	  eventMaxId=tempRecord.get("eventId");
	} 
	 if(isautoRefresh||execLoad){
			eventStore.baseParams={"eventMaxId":eventMaxId};
			eventStore.load();
	 }
}
var atsPlayerCount=0;
var atsPlayer=function(info){
	if(sound){
		atsPlayerCount++
		if(atsPlayerCount==1){
			return ;
		}
    	var typeVal=info.eventTypeVal;
    	
    	try{
    	//警报
    	if((typeVal>7&&typeVal<13)||typeVal==14||typeVal==16||typeVal==18||typeVal==19||typeVal==20){
    		document.getElementById("atsPlayer1").controls.play();
    	}
    	//恢复
    	else{
    		// if(typeVal==21||typeVal==17||typeVal==15||typeVal==13)
    		document.getElementById("atsPlayer2").controls.play();
    	} 
    	}catch(e){
    		
    	}
    	
    	
    	
    	
    	/*
		if(info.eventTypeVal==23)
		document.getElementById("atsPlayer1").controls.play();
		else if(info.eventTypeVal==24)
		document.getElementById("atsPlayer2").controls.play();
		else if(info.eventTypeVal==25)
		document.getElementById("atsPlayer3").controls.play();
		else if(info.eventTypeVal==26)
		document.getElementById("atsPlayer4").controls.play();*/
	}
}
/***
 * 地图 
 */
var rt;  
var map_refresh;
var loadMapPoints;
var loadMap=function() {
	  map_refresh={id:'refresh',handler:function(a,b,c){
						loadMapPoints();				
						
					}};
	var rt_width=400,rt_height=200;
	  var mapPanel= new Ext.Panel({id:'mapdiv',margins:'1 1 1 1',layout:'absolute',height:800,width:800});
	  	rt = new Ext.Window({
					id:"map-name",
					title : "名称",
					height : rt_height,
					frame : true,
					width : rt_width,
					layout : "border",
					maximizable:true,
					closeAction : "hide",
					closable:false,
					autoScroll:true,
					tools:[map_refresh],
					//modal : false,
					items : [{region:'center',id:'map-center' ,split:true,border:true,autoScroll:true,items:[mapPanel]}]
	 		});
	   	var   x   =  0;// document.body.clientWidth-400;
        var   y   =   document.body.clientHeight;
        
     /*   rt.minimize = function miniReadInfo(){
           if(!mini){
               rt.setPagePosition(x,y);
               mini=true;
           }else{
               rt.setPagePosition(x,y);
               mini=false;
           }
        }*/
	 		rt.show();
        	rt.setPagePosition(x,y-rt_height);
	 		
	   loadMapPoints=function(){
        	var queryCondition={};
        	Ext.Ajax.request({method: 'POST',url:datapath.queryDefaultUrl,params:queryCondition,
			success:function(a){
				defaultMap=Ext.util.JSON.decode(a.responseText);
				if(defaultMap&&defaultMap.mapid&&defaultMap.mapid>0){
					rt.setTitle(defaultMap.mapname);
					var xx="url("+defaultMap.urlpath+")";
					mapPanel.body.dom.style.backgroundImage=xx;
					//删除所有地图中的点
					mapPanel.removeAll(true);
					if(defaultMap.points&&Ext.isArray(defaultMap.points)){						
						buildPoint(defaultMap.points);
					}else{
						defaultMap.points=[];
					}
					if(defaultMap.width&&defaultMap.height&&defaultMap.width>0&&defaultMap.height){
						mapPanel.body.dom.style.width=defaultMap.width+'px';
						mapPanel.body.dom.style.height=defaultMap.height+'px';
					}else{
						defaultMap.width=1280;
						defaultMap.height=1024;
						mapPanel.setSize(1280,1024);
					}
				}else{
				}			
			 },failure:function(a){ 
					 Ext.Msg.show({
		            title: "错误",
		            msg: "与服务器断开连接！",
		            minWidth: 300,
		            modal: true,
		            icon: Ext.Msg.INFO,
		            buttons: Ext.Msg.OK
		        });
				}
			});
        };
	  var buildPoint=function(obj){
       	var points=new Array();
       	if(!Ext.isArray(obj)){
       		points.push(obj);
       	}else{
       		points=obj;
       	}
		if(points.length>0){ 
		for(var i=0;i<points.length;i++){
			var tpit=points[i];
			var img="";
			if(tpit.name&&tpit.name!=''){}
			else{tpit.name='设备'+tpit.equiparent+'-'+tpit.equiid+(tpit.ptype==1?"输入":"输出")+"端口"+tpit.port;}
			if(tpit.icon&&tpit.icon!=''){img="<img width=32 height=32 src='pub/images/"+tpit.icon+"'/><br/>";}
		var ppoint=new Ext.Panel({
			html: '<div style="font-size:12px;text-align:center;position:relative;width:100%;" >'+img+choseNight(tpit.status)+tpit.name+'</label></div>',
			id:tpit.pointid,
			style:" background-color:transparent;width:100%;",
			bodyStyle:' background-color:transparent;width:100%;',
			border:false, 
			x:tpit.leftpx,y:tpit.toppx,
			width:150,
			height:70,
			draggable: false
				});
		   mapPanel.add(ppoint);
		}
	 	mapPanel.doLayout();
		}
       	}; 
       	//loadMapPoints();
	};
	
	/***
	 * 根据事件控制一个图标的显示状态
	 * option: {equiid:1,equiparent:2,port:3,ptype:4}
	 * eventName:事件名称
	 */
	var loadEventPoint=function(option,eventName){
		var targetPoint=findPoint(option);
	 	if(targetPoint&&targetPoint.pointid){
	 		var tempPointP=Ext.getCmp(targetPoint.pointid);
	 		var timg="";
	 		if(targetPoint.icon&&targetPoint.icon!=''){timg="<img width=48 height=48 src='pub/images/"+targetPoint.icon+"'/><br/>";}
	 		tempPointP.body.dom.innerHTML='<div style="font-size:12px;text-align:center;position:relative;width:100%;" >'+timg+choseNight(targetPoint.status)+targetPoint.name+'</label></div>'
	 }
		
		//mapPanel.doLayout();
	};
	var choseNight=function(tv){
		tv=tv||-1
		switch(tv){
		case 1:  return '<label style="height:16px;font-size:12px;background-color:#fff;padding-left:18px; width:100%;background-repeat:no-repeat;background-position:left;background-image: url(pub/images/green.png);">';
		case 2:  return '<label style="height:16px;font-size:12px;background-color:#fff;padding-left:18px;width:100%;background-repeat:no-repeat;background-position:left;background-image: url(pub/images/red.png);">';
		case 3:  return '<label style="height:16px;font-size:12px;background-color:#fff;padding-left:18px;width:100%;background-repeat:no-repeat;background-position:left;background-image: url(pub/images/yellow.png);">';
		case -1:  return '<label style="height:16px;font-size:12px;background-color:#fff;padding-left:18px;width:100%;background-repeat:no-repeat;background-position:left;background-image: url(pub/images/black.png);">';
		 
		}
		 return "";
	}
	/***
	 * {equiid:1,equiparent:2,port:3,ptype:4}
	 */
var showMapPoint=function(option){
	//如果地图没有展开，则可以强制展开
	 	var tempPoint=findPoint(option);
		if(tempPoint!=null){
	  	  	if(tempPoint.pointid){
	  	  		var panel=Ext.getCmp(tempPoint.pointid);
	  	  		if(panel){
			 		panel.el.focus();
			 		{//全屏
					 	rt.maximize() ;
					}
	  	  		}
	  	  	} 
		} 
}
/**
 * {equiid:1,equiparent:2,port:3,ptype:4}
 */
 var findPoint=function(option){
 	if(defaultMap!=null&&defaultMap&&defaultMap.points&&defaultMap.points.length>0){
   	for(var i=0;i<defaultMap.points.length;i++){
  	  		var temxp=defaultMap.points[i];
  	  		 
  	  		if(defaultMap.points[i].equiid==option.equiid
  	  			&&defaultMap.points[i].equiparent==option.equiparent
  	  			&&defaultMap.points[i].port==option.port
  	  			&&defaultMap.points[i].ptype==option.ptype){
  	  			return  defaultMap.points[i];
  	  		}
  	  	}  
 	}
  	  	return null;
 }
 /**初始化加载**/
 myinit=function(){
 	
 	eventStore.load();
 	treeHeightSet();
 	//devicesTreeLoad.load();
	//devicesTreeRoot.expand();
	 {
				var pre_out_help_id="pre_out_help";
				var  preStatTab=Ext.getCmp(pre_out_help_id);
				if(preStatTab==""||preStatTab==undefined){
					work_Tab.insert(work_Tab.items.length,new Ext.Panel({title :'设备输出控制',id : pre_out_help_id,closable : false,
					html:'<iframe src="'+datapath.preOutputSetingURL+"?td="+(new Date().getTime())+'" style="width:100%;height:100%;border:0px;"></iframe>'}));
				}
	}
	// atsPlayer();
	loadMap();
	validateSys();
 } 
/**********************************************************界面构建****************************************************************************/    
var maxLine=20
eventStore= new Ext.data.JsonStore({
			url : datapath.table4DataURL+"?td="+(new Date().getTime()),
			root : 'array',
			fields : ['eventId','equipmentFid','equipmentSid','eventDesc','eventTerminal',
 		 	 'eventTime', 'eventType', 'eventTypeVal',  'processBy', 'processDesc', 'processTime',
	    	 'processUid','port','ptype','onReady'],
			remoteSort : false,
			listeners:{load: function(store_, records_, options_) {
				if(records_.length>0){
					var sc=table4Store.getCount();
					var rc=records_.length;
					var ac=sc+rc;
					if(rc>maxLine){
						table4Store.clear();
					}else if(ac>=maxLine){
					var begin=maxLine-rc;
					for(var i=begin;i<sc;i++)
						table4Store.removeAt(begin);
					}
					table4Store.add(records_);
					table4Store.sort('eventId','DESC');
					callMapAndPalyer(store_);
					loadMapPoints();//map_refresh.click();
			}
	 			if(isautoRefresh){ autoRefreshTable.defer(autoRefreshTime);}
			}}
		}); 
/**
 * 调用地图变更与开启音效 
 * */
var callMapAndPalyer=function (a){//数据加载后，自动的初始化 选择第一项
	     	var tempOne=null;
	     		 //判断是不是大于起点值
	     	  for(var i=0;i<a.data.items.length;i++){
	     	  		/*if(a.data.items[i].data.ptype>0&&a.data.items[i].data.port>0){
	     	  			loadEventPoint({equiparent:a.data.items[i].data.equipmentFid,equiid:a.data.items[i].data.equipmentSid,port:a.data.items[i].data.port,ptype:a.data.items[i].data.ptype,state:a.data.items[i].data.state},a.data.items[i].data.eventDesc)
	     	  			//return;
	     	  		}*/
	     	  			//if(a.data.items[i].data.onReady==1){
	     	  				
	     	  					tempOne=a.data.items[i].data;
	     	  					atsPlayer(tempOne); 
								break;	     	  			
	     	  			//}
	     	  }
	     	 
		}; 
 		
/**加载事件信息列表**/
 table4Store=new Ext.data.Store({
 		remoteSort : false,
    	proxy: new Ext.data.HttpProxy({method: 'POST' ,url:datapath.table4DataURL+"?td="+(new Date().getTime()),params:{}}),
	    reader:new Ext.data.JsonReader({totalProperty:'total',root:'array'},
	    [{name:'eventId'},
	     {name:'equipmentFid'},
	     {name:'equipmentSid'},
     	 {name:'eventDesc'},
	     {name:'eventTerminal'},
 		 {name:'eventTime'},
     	 {name:'eventType'},
     	 {name:'eventTypeVal'},
	     {name:'processBy'},
	     {name:'processDesc'},
	     {name:'processTime'},
	     {name:'processUid'},
	     {name:'port'},
	     {name:'ptype'},
	     {name:'onReady'}
	     ])
	     /**,
	     listeners:{load:function(a,b,c){//数据加载后，自动的初始化 选择第一项
	     	var tempOne=null;
	     		 //判断是不是大于起点值
	     	  for(var i=0;i<a.data.items.length;i++){
	     	  		if(a.data.items[i].data.ptype>0&&a.data.items[i].data.port>0){
	     	  			loadEventPoint({equiparent:a.data.items[i].data.equipmentFid,equiid:a.data.items[i].data.equipmentSid,port:a.data.items[i].data.port,ptype:a.data.items[i].data.ptype},a.data.items[i].data.eventDesc)
	     	  			//return;
	     	  		}
	     	  			if(a.data.items[i].data.onReady==1){
	     	  				if(tempOne==null){
	     	  					tempOne=a.data.items[i].data;
	     	  				}
	     	  			
	     	  			}
	     	  }
	     	  if(tempOne!=null){
				atsPlayer(tempOne);    	  	
	     	  } 
		},
		loadbefore:function(a,b,c){
			
		
		}
		}**/
	     
	     
    }); 
    
    
    
    table4ColumnModel=new Ext.grid.ColumnModel([
    {header:'序号',dataIndex:'eventId',width:40,hidden:true},
    {header:'事件分类',dataIndex:'eventType',width:80,renderer:function(val){
	switch(val){
		case 1:return "管理员操作";
		case 2:return "用户操作";
		case 3:return "报警 ";
		case 4:return "设备故障";
		case 5:return "端口状态";
		default:return val;
	}
	}},
    {header:'事件时间',dataIndex:'eventTime',width:120,renderer:function(val){return formateDate(val);}},
    {header:'事件描述',dataIndex:'eventDesc',width:400,renderer:function(val,b,c){
    	var typeVal=c.get('eventTypeVal');
    	if((typeVal>7&&typeVal<13)||typeVal==14||typeVal==16||typeVal==18||typeVal==19||typeVal==20){
    		/**
    		 * 红色，黑字
    		 */
    		return '<font style="background-color:red;color:black;">'+val+'</font>';
    	}
    	else if((typeVal>21&&typeVal<27)){
    	/**
    	 * 黄底，黑字
    	 */
    		return '<font style="background-color:yellow;color:black;">'+val+'</font>';
    	}else if(typeVal==21||typeVal==17||typeVal==15||typeVal==13){
    	/***
    	 * 绿底，白字
    	 */
    	return '<font style="background-color:green;color:whilte;">'+val+'</font>';
    	}else{
    		return val;
    	}
    	
    	
    }},
    {header:'终端描述',dataIndex:'eventTerminal',width:200},
    {header:'处理人',dataIndex:'processBy',width:100},
    {header:'处理时间',dataIndex:'processTime',width:120,renderer:function(val){return formateDate(val,2);}},
    {header:'备注',dataIndex:'processDesc',width:100},
    {header:'一层设备序号' ,dataIndex:'equipmentFid',hidden:true},
     {header:'二层设备序号' ,dataIndex:'equipmentSid',hidden:true},
     {header:'端口类型' ,dataIndex:'ptype',hidden:true},
     {header:'端口号号' ,dataIndex:'port',hidden:true}
    ]);

    var sound=true;
	var vts=new Ext.grid.GridPanel({
		width:300,
		height:450,
		//title:'事件信息',
	//		closable:true,
		tbar:new Ext.Toolbar([
			'-',new Ext.Button({id:"eventsRefresh",iconCls:'x-tbar-loading',handler:function(){
						autoRefreshTable(true);//eventStore.load();
						//var oxx=Ext.getCmp('tab_default'); 
						//oxx.el.mask('正在加载', 'x-mask-loading'); 
						/*table4Store.reload({callback :function(){
							//oxx.el.unmask(); 
						}});*/
				}})
			,'-',new Ext.form.Checkbox({id:"autoRefreshYss",boxLabel:'自动刷新',checked:true
			,listeners:{check:function(){isautoRefresh=this.checked;if(this.checked)autoRefreshTable.defer(autoRefreshTime);}}})
			,'-',new Ext.Button({text:'查看历史事件'//,icon:'../pub/images/btn/topfile1.gif',
			,handler:function(){ 
					var historyDataId="workArea_Tab_historydata";
					var historyDataTab=Ext.getCmp(historyDataId);
					if(historyDataTab==""||historyDataTab==undefined){
							work_Tab.insert(work_Tab.items.length,createMainTab(historyDataId,'历史事件',datapath.openHistoryURL+"?td="+(new Date().getTime()))); 
						work_Tab.activate(historyDataId);
					}else { 
						work_Tab.activate(historyDataId);	
					} 	

			}})				 
				,'->',new Ext.Button({text:'声音',iconCls:'sound_open',handler:function(){
			//	console.log(this);
			if(sound){
				sound=false;
				this.setText('静音');
				this.setIconClass('sound_mute');
				
			}else{			
				this.setText('声音');
				this.setIconClass('sound_open');
				sound=true;
			}
				
			}}),'-',new Ext.Button({text:'导出excel',icon:'../pub/images/btn/topfile1.gif',
			handler:function(){
				window.location.href=datapath.exportDataURL;
			}})]),
		store:table4Store,
		cm:table4ColumnModel,
		sm:new Ext.grid.RowSelectionModel({singleSelect:true}),
		listeners:{
			dbclick:function(a,b,c){
			}
		} 
	}); 
	var vtsMenu=new Ext.menu.Menu({id:"eventGridMenu",items:[{
		text:'查看地图位置',handler:function(a,b,c){
			Ext.menu.MenuMgr.hideAll();
			var rows=vts.getSelectionModel().getSelections();
			
			        	if(rows&&rows[0]&&rows[0].get('port')>0){
			        		var one,two,t,p;
			        		one=rows[0].get('equipmentFid');
			        		two=rows[0].get('equipmentSid');
			        		t=rows[0].get('ptype');
			        		p=rows[0].get('port');
				        	showMapPoint({equiid:two,equiparent:one,port:p,ptype:t});
			        	}
		}		
	}]});
	vts.on("rowcontextmenu",function(gridp,rowindex,e){
			            e.preventDefault(); 
			        	gridp.getSelectionModel().selectRow(rowindex);
			        	var rows=gridp.getSelectionModel().getSelections();
			        	if(rows&&rows[0]&&rows[0].get('port')>0){
			            	//定位菜单的显示位置
			            	vtsMenu.showAt(e.getPoint());
			        	}
	})
table4Table=new Ext.Panel({id:'tab_default', layout:'fit',height:450,title:'事件记录',items:[vts]});
 

//功能按钮构建       开始............ 
//btools.add('<img src="../pub/images/logo/systemLogo.gif">');
//font-weight:bold;
btools.add('<div style="width:283px;height:57px;font-size:45;color:#063257;padding-left:10px;"> '+App_System_Name+'</div>');
btools.add('->');
btools.add('<img src="../pub/images/btn/user_orange.png" sytle="border:0px;witdh:16px;height:16px;"/>&nbsp;&nbsp;&nbsp;');
btools.add(App_User_Name+'&nbsp;&nbsp;&nbsp;');
btools.add('-');
/***--------系统设置菜单 */
var smenu1=new Ext.menu.Menu({items:[/*{text:'地图配置1',handler:function(){
 window.open ('editMap!editMap.action','newwindow','height=100,width=400,top=0,left=0,toolbar=no,menubar=no,scrollbars=no, resizable=no,location=no, status=no') ;
	
}},*/
{
text:'地图配置',handler:function(){
 var defaultName="hello  map!";
   var sheight = screen.height-70;
  var swidth = screen.width-10;
 //var winoption ="dialogHeight:"+sheight+"px;dialogWidth:"+ swidth +"px;status:yes;scroll:yes;resizable:yes;center:yes";
 var reset=window.showModalDialog("editMap!editMap.action?x="+new Date().getTime(),defaultName,"dialogWidth="+swidth+"px;status:no;dialogHeight="+sheight+"px");
 if(reset==1){
 	//alert('true');
 }
	
}
}
,{text:'用户设置',handler:function(){
	var user_set_id="workArea_Tab_user_set";
	var userTab=Ext.getCmp(user_set_id);
	if(userTab==""||userTab==undefined){
			work_Tab.insert(work_Tab.items.length,createMainTab(user_set_id,'用户设置',datapath.userSetingURL+"?td="+(new Date().getTime()))); 
		work_Tab.activate(user_set_id);
	}else { 
	work_Tab.activate(user_set_id);	
	} 	
}},{text:'端口设置',handler:function(){
	var port_set_id="workArea_Tab_port_set";
	var portTab=Ext.getCmp(port_set_id);
	if(portTab==""||portTab==undefined){
		work_Tab.insert(work_Tab.items.length,createMainTab(port_set_id,'端口设置',datapath.portSetingURL+"?td="+(new Date().getTime())));
		work_Tab.activate(port_set_id);
	}else {work_Tab.activate(port_set_id);}
	
}},{text:'设备设置',handler:function(){
	var system_set_id="workArea_Tab_system_set";
	var sysTab=Ext.getCmp(system_set_id);
	if(sysTab==""||sysTab==undefined){
		work_Tab.insert(work_Tab.items.length,new Ext.Panel({
				title : '设备设置',
				id : system_set_id,
				closable : true,
				html:'<iframe src="'+datapath.sysSetingURL+"?td="+(new Date().getTime())+'" style="width:100%;height:100%;border:0px;"></iframe>'
			}
			));
		
		work_Tab.activate(system_set_id);
	}else { 
		work_Tab.activate(system_set_id);
	
	}
	

	
	
}}/*,{text:'系统工具',handler:function(){
	var system_set_id="workArea_Tab_system_tool";
	var sysTab=Ext.getCmp(system_set_id);
	if(sysTab==""||sysTab==undefined){
		work_Tab.insert(work_Tab.items.length,new Ext.Panel({
				title : '系统工具',
				id : system_set_id,
				closable : true,
				html:'<iframe src="'+datapath.sysToolURL+"?td="+(new Date().getTime())+'" style="width:100%;height:100%;border:0px;"></iframe>'
			}
			));
		
		work_Tab.activate(system_set_id);
	}else { 
		work_Tab.activate(system_set_id);
	
	}
	

	
	
}
}*/,{text:'设备数据重置',handler:function(){
	var system_set_id="reset_tool";
	var sysTab=Ext.getCmp(system_set_id);
	if(sysTab==""||sysTab==undefined){
		work_Tab.insert(work_Tab.items.length,new Ext.Panel({
				title : '设备数据重置',
				id : system_set_id,
				closable : true,
				html:'<iframe src="'+datapath.sysResetURL+"?td="+(new Date().getTime())+'" style="width:100%;height:100%;border:0px;"></iframe>'
			}
			));
		
		work_Tab.activate(system_set_id);
	}else { 
		work_Tab.activate(system_set_id);
	
	}
	

	
	
}
}]});
if(gobal_user_level==1||gobal_user_level==2){
	if(gobal_user_level==1){
	smenu1.add({text:'ATS系统设置',handler:function(){
	var ats_set_id="workArea_Tab_ats_set";
	var atssetTab=Ext.getCmp(ats_set_id);
	if(atssetTab==""||atssetTab==undefined){
		work_Tab.insert(work_Tab.items.length,new Ext.Panel({
				title : 'ATS系统设置',
				id : ats_set_id,
				closable : true,
				html:'<iframe src="'+datapath.AtsSeting+"?td="+(new Date().getTime())+'" style="width:100%;height:100%;border:0px;"></iframe>'
			}
			));
		
		work_Tab.activate(ats_set_id);
	}else { 
		work_Tab.activate(ats_set_id);
	
	}
}})
		
	}
	btools.add({text:'设置',menu:smenu1});
	btools.add('-');
}

btools.add({text:'退出',handler:function(){
	 confirmsg("警告","您确认要退出当前系统？",function(isok){
		if(isok=='yes'){
     Ext.Ajax.request({
		   url:datapath.quitURL+"?td="+(new Date().getTime()),
		   success: function(a,b){
		   	   var b = Ext.util.JSON.decode(a.responseText);                       
				if(b.success){
					window.location.href="";
				}else{
					Ext.Msg.alert('提示',b.message);
				}
		   	},
		   failure: function(a,b){} 
	});
		
		}
	 
	 	
	 }); 
	 
	
}});
btools.add('-');
btools.add({text:'帮助'});
btools.add('-');
	 
    todayRecord = new Ext.Panel({
    title:'操作区',
    height:120,
    collapsible:true,
    region:'north',
    layout:'table',
    frame:true,
    padding:'2 2 2 2',
    layoutConfig:{columns:3},
	defaults:{margins:'4 4 4 4',align:'right'},
    items:[ new Ext.Button({id:'bufang_btn',text:'<img src="../pub/images/btn/bufang.gif"  alt="布防"/>',height:80,width:70,handler:function(){
     Ext.Ajax.request({
		   url: datapath.actionBuFangURL+"?td="+(new Date().getTime()),
		   success: function(a,b){
		   	   var b = Ext.util.JSON.decode(a.responseText);                       
				if(b.success){
					if(b.code==300){
						pingBi();
						refreshUtils(b.returnVal,null,function(){ 
						Ext.Msg.alert('提示','布防成功！');
						});
					/*	function(){
							//Ext.getCmp("bufang_btn").disabled=true;
							//Ext.getCmp("chefang_btn").disabled=false;
							//Ext.getCmp("chakan_btn").disabled=false;
						}*/
					}else if(b.code==900){
						msg('提示',b.message);
					}
					
				}else{
					msg('提示',b.message);
				}
				
		   	},
		   failure: function(a,b){
		   
		   },
		  /* headers: {
		       'my-header': 'foo'
		   },*/
		   params: { foo: 'bar' }
	});

    
    }}),
    		new Ext.Button({id:'chefang_btn',text:'<img src="../pub/images/btn/chefang.gif"  alt="撤防"/>',height:80,width:70,handler:function(){
    		
    			
     Ext.Ajax.request({
		   url: datapath.actionCeFangURL+"?td="+(new Date().getTime()),
		   success: function(a,b){
		   	   var b = Ext.util.JSON.decode(a.responseText);                       
				if(b.success){
					if(b.code==300){
						pingBi();
						refreshUtils(b.returnVal,null,function(){ 
						Ext.Msg.alert('提示','撤防成功！');
						});
					}else if(b.code==900){
						msg('提示',b.message);
					}
					
				}else{
					msg('提示',b.message);
				}
				
		   	},
		   failure: function(a,b){
		   
		   },
		   params: { foo: 'bar' }
	});

    
    
    		}}),
    		new Ext.Button({id:'chakan_btn',text:'<img src="../pub/images/btn/chuli.gif"  alt="处理"/>',height:80, width:70,handler:function(){
    		
    			
     Ext.Ajax.request({
		   url: datapath.actionChaKanURL+"?td="+(new Date().getTime()),
		   success: function(a,b){
		   	   var b = Ext.util.JSON.decode(a.responseText);                       
				if(b.success){
					if(b.code==300){
						pingBi();
						refreshUtils(b.returnVal,null,function(){ 
						Ext.Msg.alert('提示','处理成功！');
						});
					}else if(b.code==900){
						msg('提示',b.message);
					}
					
				}else{
					msg('提示',b.message);
				}
				
		   	},
		   failure: function(a,b){
		   
		   },
		   params: { foo: 'bar' }
	});

    
    
    		}})
    ]
    });
    /*
     new Ext.Button({id:'bufang_btn',height:70,width:65,html:creatButton('bufang','title="布防"'),handler:function(){}}),
    		new Ext.Button({text:' ',height:70,width:65,html:creatButton('chefang'," title='撤防' ")}),
    		new Ext.Button({text:' ',height:70, width:65,html:creatButton('chuli'," title='处理'  ")})*/
    
    
/*  new Ext.Button({text:'设备状态说明', width:65}),
    new Ext.Button({text:'设备输出说明', width:65})*/
	/*ctCls:'bufang',itemCls:'bufang',iconCls:'bufang',baseCls:'bufang',*/
    //defaultType: 'button',
    //baseCls: 'x-plain',
    //cls: 'btn-panel',
	//bodyStyle:'padding:5px',
//定义根节点的Loader 
devicesTreeLoad=new Ext.tree.TreeLoader({dataUrl:datapath.queryDeviceTreeURL+"?td="+(new Date().getTime())}); 
//异步加载根节点 
	devicesTreeRoot = new Ext.tree.AsyncTreeNode({
				id : '0',
				text : '设备树',
				draggable : false,
				expanded : true
			}); 
/***
 * 自动刷新树
 */

autoRefreshTree=function(cmd){
					if(cmd==-1){
			  	   		devicesTree.getLoader().load(devicesTree.getRootNode(),function(treeNode){
				   			treeNode.expand(true,false);
			  	   		});
					}else if(cmd==0){
			  	  	 	window.setTimeout("autoRefreshTree(1)",split_time_for_tree);
					}else if(cmd==1){
						devicesTree.getLoader().load(devicesTree.getRootNode(),function(treeNode){
				   			treeNode.expand(true,false);
			  	   		});
			  	   		window.setTimeout("autoRefreshTree(1)",split_time_for_tree);
					}
		};
devicesTree= new Ext.tree.TreePanel({
		ats:true,
		title:'设备树',
		border:false,
		tbar:new Ext.Toolbar({border:false,items:[new Ext.Button({text:'刷新设备树 ',iconCls:'x-tbar-loading'
		,handler:function(){
			autoRefreshTree(-1);}
		
		})
		/*,'|'
		,new Ext.Button({text:'设备状态',iconCls:'helpicon',handler:function(){
			var pre_state_help_id="pre_state_help";
			var  preStatTab=Ext.getCmp(pre_state_help_id);
			if(preStatTab==""||preStatTab==undefined){
				work_Tab.insert(work_Tab.items.length,createMainTab(pre_state_help_id,'设备状态',datapath.preStateHelpURL+"?td="+(new Date().getTime())));
				work_Tab.activate(pre_state_help_id);
			}else {work_Tab.activate(pre_state_help_id);}
		}
		})*/
		]}),
	    region:'center',
        layout:'fit',
        //root:devicesTreeRoot,//定位到根节点
        root: devicesTreeRoot,
        width:300, 
        height:500, 
        animate:true,//开启动画效果 
        draggable:false,//不允许子节点拖动  
        loader:devicesTreeLoad,
     	autoScroll:true,
        frame: true,
        listeners:{
        	load:function(){
        		if(autoRefreshTree_count<0){
        			autoRefreshTree(0);
        			autoRefreshTree_count++;
        		}
        	}
        }
        /*
        root:devicesTreeRoot,
        loader:devicesTreeLoad*///,             
       // dataUrl:datapath.queryDeviceTreeURL
    });
/**
 * 添加workTab
 * */
work_Tab= new Ext.TabPanel({
            	id:'workArea_Tab',
                region: 'center', // a center region is ALWAYS required for border layout
                deferredRender: false,
                ableclose:true,
                activeTab: 0,     // first tab initially active
                items: [table4Table]
            })
                
/************--------------------***主界面架设*****--------------------------------******/
var viewport = new Ext.Viewport({
            layout: 'border',
            items: [{
                region: 'north',
                //title:'ats系统',
                //title:'${appInfo.webAppName}',
               //split: true,
               // collapsible: true,
                margins: '0 0 0 0',
                height:65, // give north and south regions a height
                //html:'asdfasd',
                items:[btools]//actionPlan
            }/*, {
                region: 'south',
                contentEl: 'south',
                split: true,
                height: 100,
                minSize: 100,
                maxSize: 200,
                collapsible: true,
                title: '信息',
                margins: '0 0 0 0'
            }*/, {
                region: 'west',
                id: 'west-panel', // see Ext.getCmp() below
                //title: '操作纪录',
                split: true,
                collapsible: true,
                width: 250, // give east and west regions a width
                minSize: 175,
                maxSize: 400,
                margins: '1 1 1 1',
                layout: 'border', // specify layout manager for items
                items: [todayRecord,devicesTree]
                // this TabPanel is wrapped by another Panel so the title will be applied
              /*  new Ext.TabPanel({
                    border: false, // already wrapped so don't add another border
                    activeTab: 0, // second tab initially active
                    tabPosition: 'top',
                  	
                    height:400,
                    items: [{
                        title: '操作区',
                        autoScroll: false,
                        items: todayRecord
                        
                    },{
                        title: '设备树2',
                        autoScroll: true,
                        items: devicesTree
                        
                    }]
                })*/
            },work_Tab]
        });
   myinit();
   
    });
    
    
var creatButton=function(cname,other){
return "<tbody class='x-btn-small x-btn-icon-small-left'>" +
		"<tr><td class='x-btn-tl'><i>&nbsp;</i></td><td class='x-btn-tc'></td><td class='x-btn-tr'><i>&nbsp;</i></td></tr>" +
		"<tr><td class='x-btn-ml'><i>&nbsp;</i></td><td class='"+cname+"'  "+other+"><em class='' unselectable='on'><button class=' x-btn-text' id='ext-gen109' type='button'></button></em></td><td class='x-btn-mr'><i>&nbsp;</i></td></tr>" +
		"<tr><td class='x-btn-bl'><i>&nbsp;</i></td><td class='x-btn-bc'></td><td class='x-btn-br'><i>&nbsp;</i></td></tr>" +
	  "</tbody>";
};
/***
 * 创建一个主工作区tab
 */
var createMainTab=function(tabid,tabname,iframUrl){
	iframUrl+="?td2="+(new Date().getTime());
return new Ext.Panel({
				title :tabname,
				id : tabid,
				closable : true,
				html:'<iframe src="'+iframUrl+'" style="width:100%;height:100%;border:0px;overflow:auto"></iframe>'
			});
};
/**
 * 验证系统是否可用
 */
var  validateSys=function(){
  Ext.Ajax.request({
		   url: "../cache/atsNormal.action"+"?td="+(new Date().getTime()),
		   success: function(a,b){
		   	   var b = Ext.util.JSON.decode(a.responseText);                       
				if(b.success){
				 
				}else{
					Ext.Msg.alert("系统错误",b.message);
				}
				
		   	} 
	});
}
