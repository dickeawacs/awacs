var winPanel, leftTree,rightForm,rightForm_top,rightform_tab,rightform_tab_inputTab,rightform_tab_outputTab;
//主窗口 ，          设备树，       右侧表单      设备基本信息       输入输出选项卡   输入端口选项卡                           输出端口选项卡
var datapath={	
		quitURL:'login!quit.action',
		table9DataURL:"editQ!devices.action",
		table10DataURL:"query!queryT10.action",
		table4DateURL:'query!queryT4.action'
		};
Ext.onReady(function(){

	/***
	 * 输入界面 构建
	 */
	var inputPortViewBuilder=function(ele_id){
		return new Ext.TabPanel({
                    border: false, // already wrapped so don't add another border
                    activeTab: 0, // second tab initially active
                    title: '输入',                    
                   	height:400, 
                   	frame:true,
                    items: [inputPortElementBuilder(ele_id+"_1",'输入端口1'),
	                        inputPortElementBuilder(ele_id+"_2",'输入端口2'),
	                        inputPortElementBuilder(ele_id+"_3",'输入端口3'),
	                        inputPortElementBuilder(ele_id+"_4",'输入端口4'),
	                        inputPortElementBuilder(ele_id+"_5",'输入端口5'),
	                        inputPortElementBuilder(ele_id+"_6",'输入端口6'),
	                        inputPortElementBuilder(ele_id+"_7",'输入端口7'),
	                        inputPortElementBuilder(ele_id+"_8",'输入端口8')
                    	  ]
                });		
	};
		/***
	 * 输出界面 构建
	 */
	var outputPortViewBuilder=function(ele_id){
		return new Ext.TabPanel({
                    border: false, // already wrapped so don't add another border
                    activeTab: 0, // second tab initially active
                    title: '输出',                    
                   	height:400, 
                   	frame:true,
                    items: [outputPortElementBuilder(ele_id+"_1",'输出端口1'),
	                        outputPortElementBuilder(ele_id+"_2",'输出端口2'),
	                        outputPortElementBuilder(ele_id+"_3",'输出端口3'),
	                        outputPortElementBuilder(ele_id+"_4",'输出端口4'),
	                        outputPortElementBuilder(ele_id+"_5",'输出端口5'),
	                        outputPortElementBuilder(ele_id+"_6",'输出端口6'),
	                        outputPortElementBuilder(ele_id+"_7",'输出端口7'),
	                        outputPortElementBuilder(ele_id+"_8",'输出端口8')
                    	  ]
                });		
	};
	/**
	 * 输入端口  构建
	 */
	var inputPortElementBuilder=function(ele_id,tabname){
	return new Ext.Panel({id:ele_id,layout:'column',defaults:{labelAlign:'right'},frame:true,title:tabname,items:[
		{xtype:'fieldset',column:.5,title:'端口设置',margins:'5 5 5 5',padding:'5 5 5 5',items:[{xtype:'textfield',fieldLabel:'端口名'},{xtype:'textfield',fieldLabel:'端口属性'},{xtype:'textfield',fieldLabel:'对应二级用户'},{xtype:'textfield',fieldLabel:'触发提示音'},{xtype:'textfield',fieldLabel:'恢复提示音'}]},
		{xtype:'fieldset',column:.5,title:'交叉输入',items:[{xtype:'textfield',fieldLabel:'端口名'},{xtype:'textfield',fieldLabel:'二层设置标识'},{xtype:'textfield',fieldLabel:'输入端口'},{xtype:'combo',fieldLabel:'交叉禁止'}]}
		]});
	};
		/**
	 * 输出端口  构建
	 */
	var outputPortElementBuilder=function(ele_id,tabname){
	return new Ext.Panel({id:ele_id,layout:'form',margins:'5 5 5 5',padding:'5 5 5 5',defaults:{labelAlign:'right'},frame:true,title:tabname,
	items:[{xtype:'textfield',fieldLabel:'端口名'},
		{xtype:'textfield',fieldLabel:'端口属性'},
		{xtype:'textfield',fieldLabel:'闭合提示音'},
		{xtype:'textfield',fieldLabel:'断开提示音'},
		{xtype:'button',text:'保存',width:'100',align:'center'}
		]});
	};
	/***
	 * 联动设置  构建
	 */
	var portCascadetBuilder=function(ele_id){};
	
	leftTree=new Ext.tree.TreePanel({
								title:'设备树',
								region:'west',
						        layout:'fit',
						        tbar:new Ext.Toolbar({border:false,items:[{text:'设备正常'},{text:'设备异常'}]}),
						        root: new Ext.tree.AsyncTreeNode({id : '0',text : '设备',draggable : false,expanded : true}),
						        width:200, 
						       // height:500, 
						        animate:true,//开启动画效果 
						        draggable:false,//不允许子节点拖动  
						        loader:new Ext.tree.TreeLoader({dataUrl:datapath.table9DataURL}) ,
						     	autoScroll:true,
						        frame: true
						    });	
 					    
	rightForm_top=new Ext.Panel({
	layout:'column',
	padding:'5 0 5 0',
	defaults:{border:false,labelAlign:'right',margins:'5 5 5 5'},
	items:[{layout:'form',column:.5,items:[{xtype:'combo',fieldLabel:'一层设备'},{xtype:'textfield',fieldLabel:'设备名称'},{xtype:'textfield',fieldLabel:'输入端口数量'}]},
		   {layout:'form',column:.5,items:[{xtype:'combo',fieldLabel:'二层设备'},{xtype:'textfield',fieldLabel:'设备属性'},{xtype:'textfield',fieldLabel:'输出端口数量'},{xtype:'checkbox',boxLabel:'禁用此设备'},{xtype:'button',text: ' 保 存 '}]}
		  ]	
	});		
	
	rightform_tab_inputTab=inputPortViewBuilder("rightform_tab_inputTab");
	rightform_tab_outputTab= outputPortViewBuilder("rightform_tab_outputTab");
	rightform_tab=new Ext.TabPanel({
                    border: false, // already wrapped so don't add another border
                    activeTab: 0, // second tab initially active
                    tabPosition: 'top',         
                   	height:400,                    
                    items: [rightform_tab_inputTab,rightform_tab_outputTab]
                }); 
					    
	rightForm=new Ext.FormPanel({
								title:'设备信息',
								region:'center',
								items:[rightForm_top,rightform_tab],
								layout:'form'
								});	//leftTree,
	winPanel=new Ext.Viewport({layout:'border',items:[leftTree,rightForm]});
	
});