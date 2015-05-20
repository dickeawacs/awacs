Ext.onReady(function(){
	var defaultMap=null;
datapath={	
		exportDataURL:"../filedExport.action",
		table4DataURL:'../event/history.action'		
		};
		
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
/**加载事件信息列表**/
 var table4Store=new Ext.data.Store({
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
	     {name:'ptype'}]) ,
	     listeners:{beforeload:function(thiz,b,c){//数据加载后，自动的初始化 选择第一项
	     	//thiz.baseParams["loginNo"] =
	     /*	console.log(thiz);
	     	console.log(b);
	     	console.log(c);*/
			var etc=Ext.getCmp('eventTypeChange').getValue();
			var ebt=Ext.getCmp('event_begin').getValue();
			var eet=Ext.getCmp('event_end').getValue();
			var ee=Ext.getCmp('event_exec').getValue();
			if(etc=="0"){
				thiz.baseParams["eventTypeChange"] ="";
			}
			thiz.baseParams["eventTypeChange"] =etc;
			//if(ebt!=""){
			thiz.baseParams["event_begin"] =ebt;
			//}
			//if(eet!=""){
			thiz.baseParams["event_end"] =eet;
			//}
			//if(ee!=""){
			thiz.baseParams["event_exec"] =ee;
			//}
					//console.log(table4Store);
				//	table4Store.load({params:{eventTypeChange:etc,event_begin:ebt,event_end:eet,event_exec:ee}});  
					
	     	
	     	/*  for(var i=0;i<a.data.items.length;i++){
	     	  		if(a.data.items[i].data.ptype>0&&a.data.items[i].data.port>0){
	     	  			loadEventPoint({equiparent:a.data.items[i].data.equipmentFid,equiid:a.data.items[i].data.equipmentSid,port:a.data.items[i].data.port,ptype:a.data.items[i].data.ptype},a.data.items[i].data.eventDesc)
	     	  		}
	     	  } */
		}}
    });   
var table4ColumnModel=new Ext.grid.ColumnModel([
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
    {header:'终端描述',dataIndex:'eventTerminal',width:150},
    {header:'处理人',dataIndex:'processBy',width:100},
    {header:'处理时间',dataIndex:'processTime',width:120,renderer:function(val){return formateDate(val,2);}},
    {header:'备注',dataIndex:'processDesc',width:100},
    {header:'一层设备序号' ,dataIndex:'equipmentFid',hidden:true},
     {header:'二层设备序号' ,dataIndex:'equipmentSid',hidden:true},
     {header:'端口类型' ,dataIndex:'ptype',hidden:true},
     {header:'端口号号' ,dataIndex:'port',hidden:true}
    ]);
var vts=new Ext.grid.GridPanel({
		width:300,
		height:450,
		//title:'事件信息',
	//		closable:true,
		tbar:new Ext.Toolbar([ 
			 '-','事件类型:',new Ext.form.ComboBox({id:'eventTypeChange', width:100,emptyText:'选择类型',store:[
				[0,"所有"],[1,"管理员操作"],[2,"用户操作"],[3,"报警"],[4,"设备故障"],[5,"端口状态"]]
				,triggerAction:'all',editable:false
				,listeners:{select:function(combo){}				
				}})
				,'-','事件时间:',new Ext.form.DateField({id:'event_begin',format:"Y-m-d"}),'至',new Ext.form.DateField({id:'event_end',format:"Y-m-d"}),'-'
				,'处理人',new Ext.form.TextField({id:'event_exec',maxLength:'5',width:100,listeners:{change:function(){Ext.Msg.alert(this.getValue());}}})
				,new Ext.Button({id:"eventSearch",title:'仅仅筛选本地数据',text:'<b>筛选</b>',handler:function(){
					//Ext.getCmp('event_begin').getValue().reset()
					
					//var etc=Ext.getCmp('eventTypeChange').getValue();
					//var ebt=Ext.getCmp('event_begin').getValue();
					//var eet=Ext.getCmp('event_end').getValue();
					//var ee=Ext.getCmp('event_exec').getValue();
					//console.log(table4Store);
					//{params:{eventTypeChange:etc,event_begin:ebt,event_end:eet,event_exec:ee}}
					table4Store.load();  
					//Ext.Msg.alert('cdk',etc);
				}})
				,'->',new Ext.form.Checkbox({id:'exportAll',value:'1'}),'导出全部','-',new Ext.Button({text:'导出excel',icon:'../pub/images/btn/topfile1.gif',
			handler:function(){
				
				var eea=Ext.getCmp('exportAll').getValue();
				var etc=Ext.getCmp('eventTypeChange').getValue();
			var ebt=Ext.getCmp('event_begin').getValue();
			var eet=Ext.getCmp('event_end').getValue();
			var ee=Ext.getCmp('event_exec').getValue();
			var queryCondition="?td="+new Date().getTime();//{"eventTypeChange":"","event_begin":"","event_end":"","event_exec":""};
			
					queryCondition+="&exportAll="+eea;
				if(etc!="0"){
					queryCondition+="&eventTypeChange="+etc;
				}else {
					queryCondition+="&eventTypeChange=";
				}
				
					queryCondition+="&event_begin="+ebt;
					queryCondition+="&event_end="+eet;
					queryCondition+="&event_exec="+ee;
					
			window.location.href=datapath.exportDataURL+queryCondition;
					/*Ext.Ajax.request({method: 'POST',url:datapath.exportDataURL,params:queryCondition,
					success:function(a){}
					,failure:function(a){ 
					 Ext.Msg.show({
		            title: "错误",
		            msg: "与服务器断开连接！",
		            minWidth: 300,
		            modal: true,
		            icon: Ext.Msg.INFO,
		            buttons: Ext.Msg.OK
		        });
				}	
					});*/
			}})]),
		store:table4Store,
		cm:table4ColumnModel,
		sm:new Ext.grid.RowSelectionModel({singleSelect:true}),
		listeners:{
		},
		 bbar: new Ext.PagingToolbar({  
            pageSize:20,  
            store: table4Store,  
            displayInfo: true,  
            displayMsg: '显示第 {0}条到{1}条记录,共{2}条',  
            emptyMsg: "没有记录"/*,  
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
            }]*/}) 
	}); 
	
	
//var table4Table=new Ext.Panel({id:'tab_default', layout:'fit',height:450,title:'事件记录',items:[vts]});
var viewWork=new Ext.Viewport({layout:'fit',items:[vts]})
   /**初始化加载**/
 myinit=function(){
 //	table4Table.render("historyView");
 	
 } 
 
 myinit();
 
 ////// end;
	
});