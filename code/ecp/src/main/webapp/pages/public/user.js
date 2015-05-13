Ext.onReady(function(){
//Ext.state.Manager.setProvider(new Ext.state.CookieProvider());
/***********************************************公共变量声明******************************************************************************/
var myinit,treeHeightSet;//初始化界面数据
var openTrades;//打开一个清单列表
var work_Tab;//主工作区的选项卡区
var todayStore,todayRecord,todayCM;//今天列表的数据源
var devicesTreeLoad,devicesTree,devicesTreeRoot;//设置绔树
var table4Store,table4Table,table4ColumnModel;
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
	queryDefaultUrl:"viewOnlyMap.action",
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
		actionChaKanURL:'pub!chakan.action'
		
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
					var ss=obj.seconds;
					re=year+"-"+month+"-"+date+" "+hh+":"+mm+":"+ss;
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
var autoRefreshTime=60000;
/***
 * 
 */
var autoRefreshTable=function(){
	 if(isautoRefresh){
	 	
		var oxx=Ext.getCmp('tab_default'); 
			oxx.el.mask('正在加载', 'x-mask-loading'); 
			table4Store.reload({callback :function(){
				//alert('load end');
				oxx.el.unmask(); 
				//autoRefreshTable.defer(autoRefreshTime);
			}});
	 }
}
var atsPlayerCount=0;
var atsPlayer=function(){
	if(sound&&atsPlayerCount>0)
	document.getElementById("atsPlayer").controls.play();
}
/***
 * 地图 
 */
var rt;  
var loadMap=function() {
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
	 		
	 var loadMapPoints=function(){
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
			if(tpit.icon&&tpit.icon!=''){img="<img width=48 height=48 src='pub/images/"+tpit.icon+"'/><br/>";}
		var ppoint=new Ext.Panel({
			html: '<div style="font-size:12px;text-align:center;" >'+img+tpit.name+'</div>',
			id:tpit.pointid,
			x:tpit.leftpx,y:tpit.toppx,
			width:100,
			height:100,
			draggable: false
				});
		   mapPanel.add(ppoint);
		}
	 	mapPanel.doLayout();
		}
       	}; 
       	loadMapPoints();
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
	 		tempPointP.body.dom.innerHTML='<div style="font-size:12px;text-align:center;">'+timg+eventName+'</div>'
	 }
		
		//mapPanel.doLayout();
	};
	
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
 	
 	table4Store.load();
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
	 atsPlayer();
	loadMap();
 } 
/**********************************************************界面构建****************************************************************************/    

/**加载事件信息列表**/
 table4Store=new Ext.data.Store({
    	proxy: new Ext.data.HttpProxy({method: 'POST' ,url:datapath.table4DataURL+"?td="+(new Date().getTime()),params:{}}),
	    reader:new Ext.data.JsonReader({totalProperty:'total',root:'array'},
	    [{name:'eventId'},
	     {name:'equipmentFid'},
	     {name:'equipmentSid'},
     	 {name:'eventDesc'},
	     {name:'eventTerminal'},
 		 {name:'eventTime'},
     	 {name:'eventType'},
	     {name:'processBy'},
	     {name:'processDesc'},
	     {name:'processTime'},
	     {name:'processUid'},
	     {name:'port'},
	     {name:'ptype'}]) ,
	     listeners:{load:function(a,b,c){//数据加载后，自动的初始化 选择第一项
	     		/**
	     		 * 判断是不是大于起点值
	     		 */ 
	     	  for(var i=0;i<a.data.items.length;i++){
	     	  		if(a.data.items[i].data.ptype>0&&a.data.items[i].data.port>0){
	     	  			loadEventPoint({equiparent:a.data.items[i].data.equipmentFid,equiid:a.data.items[i].data.equipmentSid,port:a.data.items[i].data.port,ptype:a.data.items[i].data.ptype},a.data.items[i].data.eventDesc)
	     	  			//return;
	     	  		}
	     	  }
	     	  
				autoRefreshTable.defer(autoRefreshTime);
		}}
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
    {header:'事件描述',dataIndex:'eventDesc',width:400},
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
						var oxx=Ext.getCmp('tab_default'); 
						oxx.el.mask('正在加载', 'x-mask-loading'); 
						table4Store.reload({callback :function(){
							oxx.el.unmask(); 
						}});
				}})
			,'-',new Ext.form.Checkbox({id:"autoRefreshYss",boxLabel:'自动刷新',checked:true
			,listeners:{check:function(){isautoRefresh=this.checked;if(this.checked)autoRefreshTable.defer(autoRefreshTime);}}})
			,'-','事件类型:',new Ext.form.ComboBox({id:'eventTypeChange', width:100,emptyText:'选择类型',store:[
				[0,"所有"],[1,"管理员操作"],[2,"用户操作"],[3,"报警"],[4,"设备故障"],[5,"端口状态"]]
				,triggerAction:'all',editable:false
				,listeners:{select:function(combo){}				
				}})
				,'-','事件时间:',new Ext.form.DateField({id:'event_begin'}),'至',new Ext.form.DateField({id:'event_end'}),'-'
				,'处理人',new Ext.form.TextField({id:'event_exec',maxLength:'5',width:100,listeners:{change:function(){Ext.Msg.alert(this.getValue());}}})
				,'-',new Ext.Button({id:"eventSearch",title:'仅仅筛选本地数据',text:'<b>筛选</b>',handler:function(){
					var etc=Ext.getCmp('eventTypeChange').getValue();
					var ebt=Ext.getCmp('event_begin').getValue();
					var eet=Ext.getCmp('event_end').getValue();
					var ee=Ext.getCmp('event_exec').getValue();
					//Ext.Msg.alert('cdk',etc);
				}})
				,'->',new Ext.Button({text:'静音',iconCls:'sound_open',handler:function(){
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
				
			}}),'-',new Ext.Button({text:'导出excel',icon:'../pub/images/btn/topfile1.gif'})]),
		store:table4Store,
		cm:table4ColumnModel,
		sm:new Ext.grid.RowSelectionModel({singleSelect:true}),
		listeners:{
			dbclick:function(a,b,c){
				// console.log(a)
				// alert('click me row');
			}
		},
		 bbar: new Ext.PagingToolbar({  
            pageSize:20,  
            store: table4Store,  
            displayInfo: true,  
            displayMsg: '显示条 {0} - {1}条,共{2}条',  
            emptyMsg: "正在加载...",  
            items:[  
                '-', {  
                pressed: true,  
                enableToggle:true,  
                text: 'Show Preview',  
                cls: 'x-btn-text-icon details',  
                toggleHandler: function(btn, pressed){  
                    var view = vts.getView();  
                    view.showPreview = pressed;  
                    view.refresh();  
                }  
            }]}) 
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
btools.add('<img src="../pub/images/logo/systemLogo.gif">');
btools.add('->');
btools.add('<img src="../pub/images/btn/user_orange.png" sytle="border:0px;witdh:16px;height:16px;"/>&nbsp;&nbsp;&nbsp;');
btools.add(App_User_Name+'&nbsp;&nbsp;&nbsp;');
btools.add('-');
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
						refreshUtils(b.returnVal,null,function(){
							Ext.getCmp("bufang_btn").disabled=true;
							Ext.getCmp("chefang_btn").disabled=false;
							Ext.getCmp("chakan_btn").disabled=false;
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
						refreshUtils(b.returnVal,null,function(){
							Ext.getCmp("bufang_btn").disabled=false;
							Ext.getCmp("chefang_btn").disabled=true;
								Ext.getCmp("chakan_btn").disabled=false;
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
    		new Ext.Button({id:'chakan_btn',text:'<img src="../pub/images/btn/chuli.gif"  alt="查看"/>',height:80, width:70,handler:function(){
    		
    			
     Ext.Ajax.request({
		   url: datapath.actionChaKanURL+"?td="+(new Date().getTime()),
		   success: function(a,b){
		   	   var b = Ext.util.JSON.decode(a.responseText);                       
				if(b.success){
					if(b.code==300){
						refreshUtils(b.returnVal,null,function(){
							Ext.getCmp("bufang_btn").disabled=false;
							Ext.getCmp("chefang_btn").disabled=false;
							Ext.getCmp("chakan_btn").disabled=true;
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
var rfi=0;
/***
 * 自动刷新树
 */
var autoRefreshTree=function(cmd){
					if(cmd==-1||rfi==cmd){
			  	   		devicesTree.getLoader().load(devicesTree.getRootNode(),function(treeNode){
				   			treeNode.expand(true,false);
			  	   		});
			  	   		window.setTimeout("autoRefreshTree("+(++rfi)+")",60000);
					}
		};
devicesTree= new Ext.tree.TreePanel({
		ats:true,
		title:'设备树',
		border:false,
		tbar:new Ext.Toolbar({border:false,items:[new Ext.Button({text:'刷新设备树 ',handler:function(){autoRefreshTree(-1);}}),'|',new Ext.Button({text:'设备状态',iconCls:'helpicon',handler:function(){
			var pre_state_help_id="pre_state_help";
			var  preStatTab=Ext.getCmp(pre_state_help_id);
			if(preStatTab==""||preStatTab==undefined){
				work_Tab.insert(work_Tab.items.length,createMainTab(pre_state_help_id,'设备状态',datapath.preStateHelpURL+"?td="+(new Date().getTime())));
				work_Tab.activate(pre_state_help_id);
			}else {work_Tab.activate(pre_state_help_id);}
		}
		})]}),
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
        frame: true
        
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

