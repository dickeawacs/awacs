Ext.onReady(function(){
var iconArrays=new Ext.data.SimpleStore(
      {
       fields:['text','value'],
       data:[['手机','1.png'],['打印机','2.png'],['键盘','3.png'],['探测设备','4.png'],['仪表','5.png'],['播音器','6.png'],['红','red.png'],['黄','yellow.png'],['绿','green.png']]
      });
	var datapath={	
		querydeviceTreeURL:"editMap!deviceTreePort.action",
		queryDefaultUrl:"viewOnlyMap.action",
		updateDefaultShowUrl:'updateShowMap.action',
		savePointUrl:'editMap!saveMapPoint.action',
		viewAllMapURL:"viewAllMaps.action",
		delMapUrl:"delMap.action",
		delPointUrl:"editMap!delMapPoint.action",
		fileUploadURL:"editMap!uploadFile.action",
		updateNameUrl:"editMap!updateMapName.action"
		};
 var fileReadWin;
 var fileuploadform;
	var canEdit=false;
	var defaultMap={};
	var failureFunction=function(a){ 
					 Ext.Msg.show({
		            title: "错误",
		            msg: "与服务器断开连接！",
		            minWidth: 300,
		            modal: true,
		            icon: Ext.Msg.INFO,
		            buttons: Ext.Msg.OK
		        });
				};
	
	/***
	 * 点对象
	 */
     function pointObj(cfg) {
		this.point=function(cfg) {
			if(cfg){
			this.color = cfg.color || this.color;
			this.equiid = cfg.equiid || this.equiid;
			this.equiparent = cfg.equiparent || this.equiparent;
			this.height = cfg.height || this.height;
			this.isshow = cfg.isshow || this.isshow;
			this.leftpx = cfg.leftpx || this.leftpx;
			this.mapid = cfg.mapid || this.mapid;
			this.pointid = cfg.pointid || this.pointid;
			this.toppx = cfg.toppx || this.toppx;
			this.width = cfg.width || this.width;
			this.zindex = cfg.zindex || this.zindex;
			this.port = cfg.port || this.port;
			this.ptype = cfg.ptype || this.ptype;
			this.name=cfg.name || this.name;
			}
			return this;
		};
		/**
		 * 返回参数
		 */
		this.getParams=function(head){
			return {
				"point.color" : this.color,
				"point.equiid" : this.equiid,
				"point.equiparent" : this.equiparent,
				"point.height" : this.height,
				"point.isshow" : this.isshow,
				"point.leftpx" : this.leftpx,
				"point.mapid" : this.mapid,
				"point.pointid" : this.pointid,
				"point.toppx" : this.toppx,
				"point.width" : this.width,
				"point.zindex" : this.zindex,
				"point.port" : this.port,
				"point.ptype" : this.ptype,
				"point.name" : this.name
			};
		};
		this.color = '';
		this.equiid = 0;
		this.equiparent = 0;
		this.height = 50;
		this.isshow = 1;
		this.leftpx = 0;
		this.mapid = 0;
		this.pointid = 0;
		this.toppx = 0;
		this.width = 0;
		this.zindex = 0;
		this.port =1;
		this.ptype=1;
		this.name="";
		
		this.setZindex = function(zindex) {
			this.zindex = zindex;
			return this;
		};
		this.setWidth = function(width) {
			this.width = width;
			return this;
		};
		this.setToppx = function(toppx) {
			this.toppx = toppx;
			return this;
		};
		this.setPointid = function(pointid) {
			this.pointid = pointid;
			return this;
		};
		this.setMapid = function(mapid) {
			this.mapid = mapid;
			return this;
		};
		this.setLeftpx = function(leftpx) {
			this.leftpx = leftpx;
			return this;
		};
		this.setIsshow = function(isshow) {
			this.isshow = isshow;
			return this;
		};
		this.setHeight = function(height) {
			this.height = height;
			return this;
		};
		this.setEquiparent = function(equiparent) {
			this.equiparent = equiparent;
			return this;
		};
		this.setEquiid = function(equiid) {
			this.equiid = equiid;
			return this;
		};
		this.setColor = function(color) {
			this.color = color;
			return this;
		};
		this.setPort = function(a) {
			this.port = a;
			return this;
		};
		
		this.setPtype = function(b) {
			this.ptype = b;
			return this;
		};
		this.setName = function(b) {
			this.name = b;
			return this;
		};
		
		
		this.point(cfg);
		
	};
	
	var leftTree=new Ext.tree.TreePanel({
								 title:'设备树',
								 id:'etree',
								 region:'west',
						         layout:'fit',
						        root: new Ext.tree.AsyncTreeNode({id : '0',text : '设备',draggable : false,expanded : true}),
						        width:200, 
						        autoHeight:false,
						       // height:500, 
						        animate:true,//开启动画效果 
						        //draggable:false,//不允许子节点拖动  
						        loader:new Ext.tree.TreeLoader({dataUrl:datapath.querydeviceTreeURL}) ,
						     	autoScroll:true,
						        enableDD:true,//允许
						        ddGroup    : 'treeToGridDDGroup',
						        frame: true,
						         listeners:{beforemovenode:function(){
						         	 /***
						         	  * 禁止当前树的节点在自己身上移动 
						         	  */
						         	return false;
						         }
						          }
						    });	
 
var selectNodeTemp;
var treeMenu = new Ext.menu.Menu({id:'Tree_Menu',items:[{text:'查找图标',handler:function(node,a,e){
	if(selectNodeTemp&&selectNodeTemp.attributes.id&&selectNodeTemp.attributes.parentid){
		var tempId=selectNodeTemp.attributes.id;
		var tempPanteId=selectNodeTemp.attributes.parentid;
		var one,two,ptype,port,pp;
		pp=tempId.split('_');
		one=pp[1];//二层设备	
		two=pp[2];//二层设备		
		ptype=pp[3];//类型
		port=pp[4];//端口号 
		//=(tempId).substring(tempId.indexOf("_")+1);
		//one=(tempPanteId).substring(tempPanteId.indexOf("_")+1);
		//alert(one);alert(two)
		defaultMap.select(one,two,ptype,port);
	}

}}]});
leftTree.on("contextmenu",function(node,e)
        {
        	if(node&&node.id&&node.id.indexOf("port_")>-1){
        		var pp=node.id.split('_');
        		if(pp.length==3){
            			//node.select();
			        	selectNodeTemp=node;
			            e.preventDefault(); 
			            node.select();//选中右键点击的节点
			            //定位菜单的显示位置
			            treeMenu.showAt(e.getPoint());
        		}
        	}
        });


						    
 /**调用文件分析方法 **/
/*var  readFile=function(excelID){
	Ext.Ajax.request({url:datapath.fileReadURL,params:{fileID:excelID},
	success:function(response,options){
		var result = Ext.util.JSON.decode(response.responseText); 
		if(result.success){
			msg('成功','处理完成');
		Ext.Msg.alert('成功','处理完成',function(){
			if(fileReadWin!=null)fileReadWin.close();
		})
			tradeStore.reload();//刷新列表
		}else{
			Ext.Msg.alert('失败',result.message);
		}
	}
	});
};	*/					    
var  points=new Array();

		var nt=new Ext.Toolbar();//主功能按钮
		nt.add('-');
		nt.add({text:'地图列表'});
		nt.add('-');
		nt.add({text:'发布地图'});
		nt.add('-');

		 /**********
  	   * 创建地图列表 
  	   */
	 var tradeTable,tradeMenu,tradeStore;
	 var NameForm,NameWin;
	 
	  { 
			tradeMenu=new Ext.menu.Menu({id:'trade_Menu',items:[ 
								{text:'编辑地图',handler:function(){
									var sm=tradeTable.getSelectionModel(); 
									var mapid=sm.getSelected().get("mapid");
									if(mapid!=defaultMap.mapid){
										loadMapPoints(mapid);
									}else{
									
										
									}
									
								} },
								{text:'重命名',handler:function(){
									var sm=tradeTable.getSelectionModel(); 
									var mapid=sm.getSelected().get("mapid");
									var mapname=sm.getSelected().get("mapname");
									 NameForm=new Ext.form.FormPanel({
										id:'formName'
										,items:[new Ext.form.TextField({
														name:'defaultMap.mapname',
														fieldLabel:'地图名称',allowBlank:false,
														value:mapname})
												,new Ext.form.TextField({
														name:'defaultMap.mapid',
														fieldLabel:'地图',allowBlank:false,//hidden:true,
														value:mapid})
												]
										,buttonAlign:'center'
										,buttons: [{  text:'保存',handler:function(){
											                if(NameForm.getForm().isValid()){
												                NameForm.getForm().submit({
												                    url: datapath.updateNameUrl,
												                    waitMsg: '正在处理',
												                    success: function(theForm, o,r){
												                     
												                    /*	if(r.success){
																		 		Ext.Msg.alert('成功','发布地图成功！',function(){
																		 			tradeStore.reload();
																				});
																		 	
																		 }else{
																		 Ext.Msg.alert('失败','发布地图失败！');
																		 	
																		 }*/
																		  Ext.Msg.alert("成功","保存成功!");
																			tradeStore.reload();//刷新列表
																			NameWin.close();
												                    },failure:function(a,b,c,d){ 
												                    	 Ext.Msg.alert("失败","保存失败!");
																	}
												                });
											                	}
											       	}
									        }],
							        frame:true });
									  NameWin=new Ext.Window({
													title : '重命名',
													floating : true,
													width : 600,
													height : 200,
													closable : true,
													layout : 'fit',
													bodyBorder : false,
													constrain : true, 
													items : [NameForm] 
												});
									NameWin.show();
									
								}
								},
								{text:'发布地图',handler:function(){
									var sm=tradeTable.getSelectionModel(); 
									var mapid=sm.getSelected().get("mapid");
									Ext.Ajax.request({method: 'POST',url:datapath.updateDefaultShowUrl,params:{"defaultMap.mapid":mapid},
									success:function(d){
										var r=Ext.util.JSON.decode(d.responseText);
										 if(r.success){
										 		Ext.Msg.alert('成功','发布地图成功！',function(){
										 			tradeStore.reload();
												});
										 	
										 }else{
										 Ext.Msg.alert('失败','发布地图失败！');
										 	
										 }
									
										
									},failure:failureFunction});
									
									
								}},
								{text:'删除',handler:function(){
									var sm=tradeTable.getSelectionModel(); 
									var mapid=sm.getSelected().get("mapid");
									Ext.Ajax.request({method: 'POST',url:datapath.delMapUrl,params:{"defaultMap.mapid":mapid},
									success:function(d){
										var r=Ext.util.JSON.decode(d.responseText);
										 if(r.success){
										 		Ext.Msg.alert('成功','删除地图成功！',function(){
										 			tradeStore.reload();
												});
										 	
										 }
									
										
									},failure:failureFunction});
									
									
								}
								}]
								});	
		 	tradeStore=new Ext.data.Store({
						    	proxy: new Ext.data.HttpProxy({method: 'POST' ,url:datapath.viewAllMapURL,params:{}}),
							    reader:new Ext.data.JsonReader({totalProperty:'total',root:'array'},
							    [{name:'mapname'},
							    {name:'isuse',hidden:true},
							    {name:'mapid',hidden:true}, 
							    {name:'urlpath',hidden:true}]) 
						    });    
    		tradeColumnModel=new Ext.grid.ColumnModel([
					    {header:'地图名称',dateIndex:'mapname',width:190,renderer:function(val,cell,record,rowIndex,columnIndex,store){
					    	if( record.data.isuse==1)return val+"(<span style='color:red;font-weight:bold;'>发布</span>)";
					    	else return val;
					    	}},
					    {header:'url',dataIndex:'urlpath',width:100,hidden:true}, 
					    {header:'编号' ,dataIndex:'mapid',hidden:true}
					    ]);
			tradeTable=new Ext.grid.GridPanel({
						title:'地图',
						id:'maplist',
						width:300,
						height:450,
					 	closable:true,
						store:tradeStore,
						cm:tradeColumnModel,
						//buttons:[{text:'hello'}],
						tbar : new Ext.Toolbar({items:[
							{text:'上传地图',style:'font-weight: bold;',handler:function(){
							 		fileuploadform=new Ext.form.FormPanel({ 
				        			fileUpload: true,
				        			frame: true,
				        			layout:'form', 
				        			   defaults: {anchor: '80%',allowBlank: false,msgTarget: 'side'},
							        items: [
							        	new Ext.form.TextField({
							        		name:'otherName',
							        		fieldLabel:'地图名'
							        	}),
							        	
							        	{xtype: 'fileuploadfield',
							        	//image/gif,image/jpeg,image/jpe,image/jpg,image/png,image/bmp
							        	fileType:"image/*",
							        	id: 'form-file',
							        	emptyText: '选择文件',
							        	fieldLabel: '地图',
							        	buttonText:' ',
							        	name: 'image',
							        	buttonCfg: {iconCls: 'upload-icon'}
							        	}
							        	],
							        buttonAlign:'center',
							        buttons: [{
							            text: '保存',
							            handler: function(){
							                if(fileuploadform.getForm().isValid()){
								                fileuploadform.getForm().submit({
								                    url: datapath.fileUploadURL,
								                    waitMsg: '正在上传地图,请稍等...',
								                    success: function(fileuploadform, o){
								                    		
														    Ext.Msg.alert("成功","上传成功!");
															tradeStore.reload();//刷新列表
															fileReadWin.close();
								                    },failure:function(a,b,c,d){ 
								                    	 Ext.Msg.alert("失败","上传失败!");
													}
								                });
							                }
							            }
							        },{ text: '取消',handler: function(){fileReadWin.close();}}]
							    });
								 fileReadWin = new Ext.Window({
													title : '上传地图',
													floating : true,
													width : 600,
													height : 200,
													closable : true,
													layout : 'fit',
													bodyBorder : false,
													constrain : true, 
													items : [fileuploadform] 
												});
									 
							
						fileReadWin.show();
				
							
						
							
							
							
						}
						}]}),
						sm:new Ext.grid.RowSelectionModel({singleSelect:true})
					}); 
			tradeTable.on("rowcontextmenu",function(grid,rowIndex,e){
					e.preventDefault();
					tradeTable.getSelectionModel().selectRow(rowIndex);
					tradeMenu.showAt(e.getXY());
				}); 
	};
	
	
	/****
	 * ----------地图列表 创建完毕 
	 */
		
	 var 	winPanel=new Ext.Viewport({layout:'border',items:[
	 	{region:'north',id:'map-north',height:30,html:'555',items:[{title:'paneld',id:"map-name"}]},
	 	{region:'west',id:'map-west',width:200,layout:'accordion',layoutConfig:{titleCoollapse:true,animate:true,activeOnTop:false}
	 	,items:[leftTree,tradeTable]},
	 	{region:'center',id:'map-center' ,split:true,border:true,autoScroll:true,
	 	items:[new Ext.Panel({id:'mapdiv',margins:'1 1 1 1',layout:'absolute',height:800,width:800})]
	 	
	 	}
	 	]});

	 	var firstGridDropTargetEl= Ext.get('mapdiv');
	 	   var firstGridDropTarget = new Ext.dd.DropTarget(firstGridDropTargetEl, {
                ddGroup    : 'treeToGridDDGroup',
                listeners:{ afterDragDrop :function(target, e, id){
                		/*console.log(target);
						console.log(e);
						console.log(id);*/
                
                
                }},
                notifyDrop : function(ddSource, e, data){
                	/****
                	 * 在这里判断是不是已经在当前地图类的数据，若是则只改变坐标，若不是则新增 
                	 * 
                	 */
                
                	if(ddSource&&ddSource.dragData&&ddSource.dragData.node&&ddSource.dragData.node.attributes){
                		/***
                		 * 
                		 */
                	//	console.log(ddSource.dragData.node.attributes);
                	var tempId=ddSource.dragData.node.attributes.id;
                	var text=ddSource.dragData.node.attributes.text;
                	var tempPanteId=ddSource.dragData.node.attributes.parentid;
                	//排除一层设备的插入
                	if(tempId.toString().indexOf("port")>-1){
                		var one,two,ptype,port,pp;
                		pp=tempId.split('_');
                		one=pp[1];
                		two=pp[2];
                		ptype=pp[3];
                		port=pp[4];
                		//one=(tempPanteId).substring(tempPanteId.indexOf("_")+1);
                		var xy=e.getXY();
                		var op = {
							x : (xy[0]),
							y : (xy[1]),
							equiparent:one,
							equiid:two,
							ptype:ptype,
							port:port
						};
                		op=formateXY(op);
                		var newPoint=new pointObj();
                		newPoint.setLeftpx(op.x);
                		newPoint.setToppx(op.y)
                		newPoint.setEquiid(op.equiid);
                		newPoint.setEquiparent(op.equiparent);
                		newPoint.setMapid(defaultMap.mapid);
                		newPoint.setPtype(op.ptype);
                		newPoint.setPort(op.port);
                		newPoint.setName(text);
                		newPoint.setPtype(ptype);
                		var hasObj=defaultMap.getPoint(op.equiparent,op.equiid,op.ptype,op.port);
                		if(hasObj!=null&&hasObj&&hasObj.pointid){
                			defaultMap.selectPoint(hasObj);
                		}else{                		
                			createPoint(newPoint);
                		}
                	
                		
                		
                		 
                		
                		
                		return true;
                	}else {
                		return false;
                	}
                	}else {
                		return true;
                	}
                }
        });
        /**************
	 * 拖动处理函数：
	 */ 	
	 	
		var renderHander = function(cmp) {
		new Ext.dd.DragSource(cmp.body.dom, {
			    ddGroup: 'treeToGridDDGroup',			
                afterDragDrop:function(target, e, id){
                	var my=Ext.get(this.getEl());
                	var parent=my.findParent(".x-abs-layout-item");//"x-abs-layout-item"
                	if(parent){
						var obj=Ext.get(parent);
						var tag=e.getXY();
						if(tag[0]>200&&tag[1]>30&&
						(tag[0]+obj.getWidth())<defaultMap.width&&(tag[1]+obj.getHeight())<defaultMap.height){
							var tempOption=defaultMap.getPointById(obj.id);
							//console.log(tempOption);
							if(tempOption){
							obj.moveTo(tag[0],tag[1]);
							//defaultMap.
							tempOption.leftpx=tag[0]-200;
                			tempOption.toppx=tag[1]-30;
                			tempOption.getParams=function(head){
								return {
									"point.color" : this.color,
									"point.equiid" : this.equiid,
									"point.equiparent" : this.equiparent,
									"point.height" : this.height,
									"point.isshow" : this.isshow,
									"point.leftpx" : this.leftpx,
									"point.mapid" : this.mapid,
									"point.pointid" : this.pointid,
									"point.toppx" : this.toppx,
									"point.width" : this.width,
									"point.zindex" : this.zindex,
									"point.port" : this.port,
									"point.ptype" : this.ptype,
									"point.icon" : this.icon
								};
                			};
							createPoint(tempOption);
							}
							//Ext.Ajax.request({url:'',params:{"point.left":tag[0],"point.width":tag[1]},success:function(){},failure:failureFunction})
						}else{
						return false;
						}
							/***
							 * 在这里写ajax。保存至数据库
							 */                	
                	}
                	return true;
                } 
		});
	};
/**	分屏上的按钮：
 * 
 */
var getTools= [{
				id: 'search',
				qtip: '编辑',
				handler: function(event, toolEl, panel) {
										searchConditionWin.show();	
				}	
			},{	id: 'close',
				qtip: '删除',
				handler: function(event, toolEl, panel) {
					getDefault(panel.id);
			    }
			 }
		];
		/*****/
        
        var mapPanel= Ext.getCmp('mapdiv');
        var loadMapPoints=function(mapId){
        	mapId=mapId||-1;
        	var queryCondition={};
        	if(mapId>0){
        		queryCondition={"defaultMap.mapid":mapId};
        	}
        	/***
        	 * 查询
        	 */
        	Ext.Ajax.request({method: 'POST',url:datapath.queryDefaultUrl,params:queryCondition,
			success:function(a){
				defaultMap=Ext.util.JSON.decode(a.responseText);
				defaultMap.add=addMapPoint;
				defaultMap.getPoint=getMapPoint;
				defaultMap.select=selectPointBy;
				defaultMap.selectPoint=selectPoint;
				defaultMap.remove=removePoint;
				defaultMap.update=updatePoint;
				defaultMap.getPointById=getPointById;
				if(defaultMap&&defaultMap.mapid&&defaultMap.mapid>0){
					Ext.getCmp("map-name").setTitle(defaultMap.mapname);
					//mapPanelx-panel-body
					var xx="url("+defaultMap.urlpath+")";
					mapPanel.body.dom.style.backgroundImage=xx;
					//console.log(mapPanel);
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
					canEdit=true;
				}else{
				    canEdit=false;
				    Ext.Msg.alert("提示","请选择地图");
				}			
			 },failure:failureFunction
			});
			 
        }
        
        
var selectPointTemp;
var pointMenu = new Ext.menu.Menu({id:'point_Menu',items:[{text:'删除',handler:function(node,a,e){}}]});
        /***
         * 创建一个点 from tree
         */
       var createPoint=function(obj){ 
       	if(!obj)return ;
       var parameters=obj.getParams();
       Ext.Ajax.request({method: 'POST',url:datapath.savePointUrl,params:parameters,
			success:function(a){
				result=Ext.util.JSON.decode(a.responseText);
				if(result.success.toString()=="true"&&result.returnVal){
					if(!isNaN(result.returnVal)&&parseInt(result.returnVal)>0){
						obj.pointid=result.returnVal;
						//为真则是创建
						if(defaultMap.add(obj))
						{	
							buildPoint(obj);
						}
					}else{				    
				    Ext.Msg.alert("提示","不能创建图标 . "+(result.message||""));
					}			
					}else{				    
				    Ext.Msg.alert("提示","不能创建图标 . "+(result.message||""));
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
       var imgWin;
       var pointMenu=new Ext.menu.Menu({id:'point_Menu',items:[ 
								{text:'编辑',handler:function(a,b,c){
										/*console.log(a);
		   	console.log(b);
		   	console.log(e);
									alert(1);*/
									
								}}]});
//图标
// tempimgset=
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
			tools:[{id:'gear',handler:function(a,b,c){
						var tempOption=defaultMap.getPointById(c.id);
						var defVal=tempOption.icon;
					/*	if(tempOption.icon=="red.png"){
						defVal="红色"
						}else if(tempOption.icon=="yellow.png"){
						defVal="黄色"
						}
						else if(tempOption.icon=="green.png"){
						defVal="绿色"
						}*/
				  imgWin=new Ext.Window({title:'设置显示图标',width:400,height:150,layout:'fit',items:[
				
				new Ext.Panel({ id:"imgset",
					layout:'form',frame:true,
					items:[new Ext.form.TextField({id:"changePK",hidden:true,value:c.id}),
						  new Ext.form.ComboBox({id:'changeIcon',fieldLabel:'选择图标',
						store:iconArrays
						,value:defVal,
						   mode:'local',
				      triggerAction:'all',
				      hideTrigger:false,
				      allowBlank:false,
				      listWidth:150,
				      width:150,
				      editable:false,
				      valueField:'value',
				      displayField:'text'
						}) 
					],buttonAlign:'center',
					buttons:[{text:'保存',handler:function(){
						var tempImgId=c.id;//Ext.getCmp("changePK").getValue();
						if(tempImgId){
							tempOption.getParams=function(head){
								return {
									"point.color" : this.color,
									"point.equiid" : this.equiid,
									"point.equiparent" : this.equiparent,
									"point.height" : this.height,
									"point.isshow" : this.isshow,
									"point.leftpx" : this.leftpx,
									"point.mapid" : this.mapid,
									"point.pointid" : this.pointid,
									"point.toppx" : this.toppx,
									"point.width" : this.width,
									"point.zindex" : this.zindex,
									"point.port" : this.port,
									"point.ptype" : this.ptype,
									"point.icon" : this.icon
									
								};
                			};
						var tempimgset=Ext.getCmp("changeIcon");
						var val=tempimgset.getValue();
						tempOption.icon=val;
						/*var tag=false;
						if(val=='红色'&&tempOption.icon!="red.ico"){
							tempOption.icon="red.ico";tag=true;
						}else if(val=='绿色'&&tempOption.icon!="green.ico"){
							tempOption.icon="green.ico";tag=true;
						}
						else if(val=='黄色'&&tempOption.icon!="yellow.ico"){
							tempOption.icon="yellow.ico";tag=true;
							
						} else if(val==""&&tempOption.icon!=""&&tempOption.icon!=null){
								tempOption.icon="";tag=true;
						}
						
						if(tag){
						}else{
						imgWin.close();
						}*/
							defaultMap.update(tempOption);
					}
					}},{text:'取消',handler:function(){imgWin.close();}}]
					
				})
				]});
				imgWin.show();
				
				
			}},{id:'close',tip:'删除',handler:function(a,b,c){
				Ext.Msg.show({
				   title:'删除？',
				   msg: '您确定要删除此图标?',
				   buttons: Ext.Msg.YESNO,
				   fn: function(pk){
				    	if(pk=='yes'){
				   			defaultMap .remove(c);
				    	}
				   },
				   animEl: 'elId',
				   icon: Ext.MessageBox.QUESTION
				});
			}}],
			html: '<div style="font-size:12px;text-align:center;" >'+img+tpit.name+'</div>',
			id:tpit.pointid,
			x:tpit.leftpx,y:tpit.toppx,
			width:100,
			height:100,
			draggable: true,				
			listeners: {render:renderHander}
				});
		ppoint.on("contextmenu",function(obj,e)
        {
        	e.preventDefault();
        });
		   mapPanel.add(ppoint);
		  
		/*   ppoint.on( "contextmenu",function(griad,rowIndex,e){
		   	console.log(a);
		   	console.log(b);
		   	console.log(e);
					e.preventDefault();
					//this.getSelectionModel().selectRow(rowIndex);
					pointMenu.showAt(e.getXY());
				} )*/
		}
	 	mapPanel.doLayout();
		}
					
       	
       	}; 
/*        
       var buildMapPoint=function(op){ 
       	op=formateXY(op);
       	var mapOption=new Ext.Panel({
       	tools:[{id:'close'}],  title:op.title, 
       	x:op.x,y:op.y,width:50,height:50,
       	id:op.id||getKey(),html:"<b>"+op.title+"</b>",
       	listeners: {render:renderHander}
       	});
       	 mapPanel.add(mapOption);
       
        mapPanel.doLayout();
       };*/
       
       
       var map_west=null,map_north=null,map_center=null;
       /***
        * @author CHENDINGKAI  2013-12-1
        * 处理图标的坐标位置
        * 
        */
       var formateXY=function(op){
       		if(map_west==null){
       			map_west=Ext.getCmp('map-west');
       		}
       		if(map_north==null){
       			map_north=Ext.getCmp('map-north');
       		}
       		if(map_center==null){
       			map_center=Ext.getCmp('map-center');
       		}
       		op.x=op.x-map_west.getWidth()+map_center.body.dom.scrollLeft;
       		op.y=op.y-map_north.getHeight()+map_center.body.dom.scrollTop;
       	return op;
       }
       
        
      
       var id_seq=9999999
       var getKey=function(id){
       id=id||id_seq++;
       return "point_"+id_seq;
       }
       
       /***
        * 地图的point添加函数，去重
        * 如果是重复的对象则返回false,若是创建 的则返回true
        */
  	  var addMapPoint=function(obj){
  	  	for(var i=0;i<this.points.length;i++){
  	  		if(obj.pointid==this.points[i].pointid){
  	  			this.points[i]=obj;
  	  			return false;
  	  			
  	  		}
  	  	}
  	  	this.points.push(obj);
  	  	return true;
  	  }
  	  /***
  	   * 删除点 
  	   */
  	  var removePoint=function(obj){
  	  	var tempPoint=this.getPointById(obj.id);
  	  	if(tempPoint.pointid){
  	  		Ext.Ajax.request({url:datapath.delPointUrl,params:{"point.pointid":tempPoint.pointid},success:function(a){
				var result=Ext.util.JSON.decode(a.responseText);
				if(result.success.toString()=="true"){
					defaultMap.points.remove(tempPoint);
					 mapPanel.remove(obj);
				}else{
					Ext.Msg.alert("失败","删除失败！",function(){});
				}
  	  			
  	  		}})
  	  		
  	  		
  	  	}
  	  
  	  }
  	   /***
  	   * 修改点 
  	   */
  	  var updatePoint=function(obj){ 
       	if(!obj||!obj.pointid)return ;
       var parameters=obj.getParams();
       Ext.Ajax.request({method: 'POST',url:datapath.savePointUrl,params:parameters,
			success:function(a){
				result=Ext.util.JSON.decode(a.responseText);
				if(result.success.toString()=="true"&&result.returnVal){
					if(!isNaN(result.returnVal)&&parseInt(result.returnVal)>0){
						obj.pointid=result.returnVal;
						//为真则是创建
						if(defaultMap.add(obj))
						{	
							buildPoint(obj);
						}else{
							rebuildPoint(obj);
						}
						imgWin.close();
					}else{				    
				    Ext.Msg.alert("提示","图标修改失败 . "+(result.message||""));
					}			
					}else{				    
				    Ext.Msg.alert("提示","图标修改失败 . "+(result.message||""));
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
       	 
  	  }
  	  
  	  /***
  	   * 根据一层设备与二层设备编号查找point
  	   */
  	   var getMapPoint=function(one,two,ptype,port){
  	  	for(var i=0;i<this.points.length;i++){
  	  		if(this.points[i].equiid==two&&this.points[i].equiparent==one
  	  			&&this.points[i].port==port&&this.points[i].ptype==ptype){
  	  			return this.points[i];
  	  		}
  	  	}
  	  	return null;
  	  };
      
  	  
  	  
  	  /***
  	   * 根据ID或根据一层设备与二层设备编号 查找对应的图标点
  	   * 有则返回此对象，无则返回 空
  	   */
  	  var getPointById=function(id){
  	  	for(var i=0;i<this.points.length;i++){
  	  		if(id==this.points[i].pointid){
  	  			return this.points[i];
  	  		}
  	  	} 
  	  	return {};
  	  } 
  	  
  	  /**
  	   * 根据一层设备号与二层设备号查找对应的设备，并选中
  	   */
	 var selectPointBy=function(one,two,ptype,port){
	 	var point=this.getPoint(one,two,ptype,port);
	 	if(point!=null){
	 		this.selectPoint(point);
	 	}
	 
	 } 
	 var rebuildPoint=function(targetPoint){
	 	if(targetPoint&&targetPoint.pointid){
	 		var tempPointP=Ext.getCmp(targetPoint.pointid);
	 		
	 		var timg="";
	 		if(targetPoint.name&&targetPoint.name!=''){
	 		}
			else{
				targetPoint.name='设备'+targetPoint.equiparent+'-'+targetPoint.equiid+(targetPoint.ptype==1?"输入":"输出")+"端口"+targetPoint.port;
			}
			
	 		if(targetPoint.icon&&targetPoint.icon!=''){timg="<img width=48 height=48 src='pub/images/"+targetPoint.icon+"'/><br/>";}
	 		tempPointP.body.dom.innerHTML='<div style="font-size:12px;text-align:center;">'+timg+targetPoint.name+'</div>'
	 	}
	 }
	 
	 /***
	  * 选择点
	  */
  	  var selectPoint=function(targetPoint){
  	  	//console.log('选择点');
  	  	if(targetPoint&&targetPoint.pointid){
  	  		var panel=Ext.getCmp(targetPoint.pointid);
  	  		if(panel){
		 		panel.el.focus();
  	  			//alert("document.getElementById('"+panel.el.id+"').focus();");
		 		//window.setTimeout("document.getElementById('"+panel.el.id+"').focus();",1000);
		 		//document.getElementById(panel.el.id).select();
  	  		}
  	  	}
  	  
  	  }
  	 
	 	  	  /******************** init ***********/
  	  
	 var init=function(){
      /***
       * 加载默认的地图 
       */
	loadMapPoints();
	/***
	 * 加载数据列表 
	 */
	 tradeStore.reload();
	 }
  	  
  	  init();
  	  

});