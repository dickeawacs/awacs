Ext.onReady(function(){
 var toolsPanel=new Ext.Panel({
	id:"tools"
	,renderTo:'tooldiv'
	,buttonAlign:'center'
	,buttons:[{text:'清空',handler:function(){
     Ext.Ajax.request({
		   url:'tool!clearSequnce.action',
		   success: function(a,b){
		   	   var b = Ext.util.JSON.decode(a.responseText);                       
				if(b.success){
					Ext.Msg.alert('提示',b.message); 
				}else{
					Ext.Msg.alert('提示',b.message);
				}
				
		   	},
		   failure: function(a,b){
		   		
		   } 
	});
	//toolsPanel.render();
    }}/*,{ text:'准备数据',handler:function(){
    	alert(1);
     Ext.Ajax.request({
		   url:'sysManagement!loadBase.action',
		   success: function(a,b){
		   	   var b = Ext.util.JSON.decode(a.responseText);                       
				if(b.success){
					Ext.Msg.alert('提示',b.message); 
				}else{
					Ext.Msg.alert('提示',b.message);
				}
		   	},
		   failure: function(a,b){		   		
		   } 
	});
	toolsPanel.render();

    
    
    }
    }
    */
    ]
	

})
	
});
    
    
 
 