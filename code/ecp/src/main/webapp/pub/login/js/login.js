function loginUtils() {
	this.CharacterRegex =new RegExp("^[^`~!@#$%^&*+=|{}\":;',\\[\\].<>/?~\(\)！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]+$");
	this.messageExp = ".loginMessage";
	this.nameMaxLen=0;
	this.pwdMaxLen=0;
	//this.successForward="loginhhy!open0.action";
	this.successForward="userMain.action";
	this.loginActionURL="login!login.action";

	// 添加到收藏夹
	this.AddFavorite = function(sURL, sTitle) {
		try {
			window.external.addFavorite(sURL, sTitle);
		} catch (e) {
			try {
				window.sidebar.addPanel(sTitle, sURL, "");
			} catch (e) {
				alert("加入收藏失败，请使用Ctrl+D进行添加");
			}
		}
	}
	// 设置为主页
	this.SetHome = function(obj, vrl) {
		try {
			obj.style.behavior = 'url(#default#homepage)';
			obj.setHomePage(vrl);
		} catch (e) {
			if (window.netscape) {
				try {
					netscape.security.PrivilegeManager
							.enablePrivilege("UniversalXPConnect");
				} catch (e) {
					alert("此操作被浏览器拒绝！\n请在浏览器地址栏输入'about:config'并回车\n然后将[signed.applets.codebase_principal_support]设置为'true'");
				}
				var prefs = Components.classes['@mozilla.org/preferences-service;1']
						.getService(Components.interfaces.nsIPrefBranch);
				prefs.setCharPref('browser.startup.homepage', vrl);
			}
		}
	}
	this.ResetPwd = function() {
		alert('正在打开界面');
	}
	this.msg = function(message) {
		$(this.messageExp).html(message);
	}
	this.msgClear = function() {
		this.msg("");
	}
	
	this.doLogin = function(nameID, pwdID) {
		var name = jQuery.trim($("#" + nameID).val());
		var pwd = jQuery.trim($("#" + pwdID).val());
		if (name == "" || name == "undefined") {
			this.msg("请输入登录名.");return false;
		}
		if (!this.CharacterRegex.test(name)) {
			this.msg("登录名不合法.");return false;
		}
		if(name.length<this.nameMaxLen){
			this.msg("登录名至少为"+this.nameMaxLen+"位长度.");return false;
		}
		if (pwd == "" || pwd == "undefined") {
			this.msg("请输密码.");return false;
		}
		if (!this.CharacterRegex.test(pwd)) {
			this.msg("密码不合法.");return false;
		}
		if(pwd.length<this.pwdMaxLen){
			this.msg("密码至少为"+this.pwdMaxLen+"位长度.");return false;
		}
		//window.location.href ="/ats/pages/80001.jsp";
		this.msg("正在登录中...");
		$("button").attr("disabled","disabled");
		$.post(this.loginActionURL, {"pwd" :pwd,"name" : name,"lastTime" : (new Date()).getTime()}, 
		function(ajaxResult) {
					var result = eval(ajaxResult);
					//var result = resultobj[0]
					if (result.success) {
						loginObj.msg(result.message);
						window.location.href = result.returnVal;
					} else {
						loginObj.msg(result.code + ":" + result.message);
					}
					$("button").removeAttr("disabled");
				}, "json");

	}

}
var loginObj = new loginUtils();
