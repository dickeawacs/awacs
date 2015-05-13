Ext.onReady(function() {
			Ext.state.Manager.setProvider(new Ext.state.CookieProvider());
			
			var treeloader=new Ext.tree.TreeLoader({dataUrl : "query!queryT9.action"}); 			
		    var root=new Ext.tree.AsyncTreeNode({ 
		            id:"myrootid",//根节点id 
		            draggable:false, 
		            expanded :true, 
		            text:"后台管理" 
		      });    
		      //生成树形面板 
		      var tree=new Ext.tree.TreePanel({ 
		        renderTo:'Tree', 
		        root:root,//定位到根节点        
		        width:350, 
		        height:300, 
		        animate:true,//开启动画效果 
		        draggable:false,//不允许子节点拖动  
		        loader:treeloader,
		        autoScroll:true
		 
		     }); 

   
			     //tree.setRootNode(root);   
			    /*  tree.on('beforeload', 
			     function(node){ 
			     tree.loader.dataUrl='query!queryT10.action?field2='+node.id; //定义子节点的Loader 
			  });   */
			// new Ext.Panel({title:"hello",items:[tree]});
			 tree.render();  
			 root.expand(); 

		/*	var mytree = Ext.tree.TreePanel({
						id:'mytree',
						draggable:false,
						height : 600,
						width : 500,
						title : 'hello Tree',
						loader : new Ext.tree.TreeLoader({
									dataUrl : "query!queryT9.action"
								})
					});*/
			/*var treeWin = new Ext.Window({
						title : 'tree',
						width : 500,
						height : 600,
						layout : 'fit',
						floating : true,
						constrain:true,
						modal:true,
						items : [mytree]
					});
			treeWin.show();*/

		});