
var refreshTime=5000;
var utilspath={resultURL:'result!readResetInfo.action'};
var tempVals=new Array();
var  infos=new Array();
var refreshUtils=function(key,params,fn,mc,max){
	var rv=key+"_key";
	tempVals[rv]={k:key,p:params,f:fn,m:mc||0,"max":max||200};
	doitre(rv);
};
/****
 * 
 * @param {} rv
 */
var doitre=function(rv){
	var mc=tempVals[rv].m;
	var max=tempVals[rv].max;
 	if(isNaN(mc))mc=0;else mc++;
 	if(isNaN(max))max=10;  	
	if(mc<max){	
		try{
			 
			tempVals[rv].m=mc;
			window.setTimeout("dataprocess('"+rv+"')",refreshTime);
		}catch(e){
			quXiaoPingBi();
			Ext.Msg.alert(e.message);
		}
	}
	else{  
		quXiaoPingBi();
		Ext.Msg.confirm("提示","获取响应超时，是否仍然继续等待？",function(abc){
			if(abc.toString().toUpperCase()=='YES'||abc.toString().toUpperCase()=='OK'){
					tempVals[rv].m=0;
					pingBi();
				 window.setTimeout("dataprocess('"+rv+"')",refreshTime);
				
				}else{
					tempVals[rv]=null;
				}
		});
	}
};
/****
 * 结果查询处理
 * @param {} rv
 */
var  dataprocess=function(rv){
 	var key, params,fn,mc;
	key=tempVals[rv].k;
	params=tempVals[rv].p;
	fn=tempVals[rv].f;
	mc=tempVals[rv].m;
	Ext.Ajax.request({url:utilspath.resultURL,params:{"key":key},
		success:function(a){
			var ok=false;
			var b=Ext.util.JSON.decode(a.responseText);
			if(b&&b.returnVal&&Ext.isArray(b.returnVal)){
			///对象的第一位是结束标记，第二位是命令类型，第三位 成功与失败,第四位 设备（一层编号---二层编号 ）
				for(var i=0;i<b.returnVal.length;i++)
				{
					if(b.returnVal[i].a&&b.returnVal[i].a==700){
						infos.push(formatinfo(b.returnVal[i])); 
						ok=true;
						quXiaoPingBi(); 
						
						if(b.returnVal[i].c&&b.returnVal[i].c==1)
						{
							 //Ext.Msg.alert("操作成功",infos.join("<br/>"));
							Ext.Msg.alert("操作成功","操作成功!");
								
						}
						else{
							 Ext.Msg.alert("操作失败",infos.join("<br/>"));
						}
						infos=new Array();
						 break;
					}
				}
			}
			if(!ok){
			doitre(rv);}
			
			
			
			
			/*
				if(b.code==900){
						tempVals[rv]=null;
						mc=0;		
						quXiaoPingBi();
						if(Ext.isFunction(fn))					
							fn(params);	
						else Ext.Msg.alert("操作成功",b.message);
				}else if(b.code==300) {
					doitre(rv);
				}else if(b.code==901){
					tempVals[rv]=null;
					mc=0;
					quXiaoPingBi();
					Ext.Msg.alert("操作失败",b.message);
					
				}
			
		*/},failure:function(a){
			tempVals[rv]=null;
			mc=0;
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

var pingBi=function(){
Ext.Msg.wait('正在处理','请等待...');
};
var quXiaoPingBi=function(){
Ext.MessageBox.hide();
};
var codes={"500":"报警","700":"重置结束：","0":"重置失败！","1":"重置成功！"}
var types={"0":""}
var formatinfo=function(o){
return codes[o.a]+""+types[o.b]+"|"+codes[o.c]+"|"+(codes[o.d]||o.d)+"|";
}