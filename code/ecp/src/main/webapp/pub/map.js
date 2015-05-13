Ext.onReady(function() {
			Ext.QuickTips.init();
			var mapL, mapR, mapP,gpn="map_group",mapRset={width:1027,height:800};
			var mapZone,dataStore,colorStore;
			//dataStore=new Ext.data.Store({});
			dataStore=new Ext.data.SimpleStore({ fields : ['text', 'value'],data : [['1', '1'], ['2', '2'], ['3', '3']]});
			colorStore=new Ext.data.SimpleStore({ fields : ['text', 'value'],data : [['red', 'red'], ['white', 'white'], ['green', 'green']]});
			
			
			/***
			 *  表单树
			 */
			mapL = new Ext.form.FormPanel({
						id : "mapR",
						width : 350,
						autoHeight : true,
						defaults:{defaultType:"textField"},
						layout:'form',
						items : [new Ext.form.ComboBox({
							name:"one",id:"one",fieldLabel:"一层设备",store:dataStore,triggerAction:'all',mode:'local', allowBlank:false,valueField : 'value',  displayField : 'text'
						}),new Ext.form.ComboBox({
							name:"two",id:"two",fieldLabel:"二层设备",store:dataStore,triggerAction:'all',mode:'local', allowBlank:false,valueField : 'value',  displayField : 'text'					
						}),{name:"name",fieldLabel:"设备名称",allowBlank:false,editable:true}
						,{name:"width",fieldLabel:"宽度",allowBlank:false,value:"50"}
						,{name:"height",fieldLabel:"高度",allowBlank:false,value:"50"}
						,new Ext.form.ComboBox({
							name:"color",id:"color",fieldLabel:"颜色",allowBlank:false,value:"red",store:colorStore,triggerAction:'all',mode:'local', valueField : 'value',  displayField : 'text'					
						})
						],
						buttons : [{
									text : "创建图标",
									handler : function() {
										var p=mapR.getForm.getValues();
										var pointPanel=new Ext.Panel({
											id:formateID(p.one,p.two),
											width:p.width,
											height:p.height,
											html:"<div style='width:"+p.width+";height:"+p.height+";backgroud:"+p.color+"'>"+p.name+"</div>"
											,listeners:{
											 render:function(el){
											 	var point=new Ext.dd.DragSource(el.id,{group:gpn});
											 		point.onInvalidDrop=function(e){
											 			Ext.get(this.id).moveTo(startPos[0],startPos[1],true);
											 		}
											 }
											}
										});
										//将panel加入到右边的显示区
										mapR.add(pointPanel);
										//加载
										mapR.doLayout();
										
									}
								}, {
									text : "定位",
									handler : function() {
									}
								}, {
									text : "保存",
									handler : function() {
									}
								}]

					});
			//创建右边的区域
			mapR=new Ext.Panel({
			id:"mapR",
			width:mapRset.width,
			height:mapRset.height,
			listeners:{
				render:function(el){
					mapZone=new Ext.dd.DropZone(el.id,{group:gpn});
				}
			}
				
			});
			/**
			 * 页面主体
			 */
			mapP = new Ext.Panel({
						layout : 'column',
						frame : true,
						autoScroll : false,
						items : [mapL, mapR]
					})
		});
/**
 * 组合 ID
 * 
 * @param {} one
 * @param {} two
 * @return {}
 */		
function formateID(one,two){
	return "00"+one+"00"+two;
}
/***
 * 解析ID
 * @param {} ids
 * @return {}
 */
function unformateID(ids){
	return {one:ids.subtring(0,3),two:ids.substring(3)}
}