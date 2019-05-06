
//弹出模式对话框
function ShowModelDialog(sPath, sParameters, sOptions) 
{ 
	var arr = new Array(); 
	arr[0]=sPath; 
	arr[1]=sParameters; 
    try
    {
	    return window.showModalDialog("/HR/WebPub/QryControl/JS/PopModule.aspx", arr,sOptions); 
    }
    catch(err)  
    {
        alert("您的IE浏览器阻止了弹出窗口的显示，请开启以便系统正常运行！\r\n如果您不知如何设置，请与系统管理员联络。");  
    }
} 

//弹出日期选择框
function BrowseDate(txtDate) 
{ 
	var sPath = "/HR/WebPub/QryControl/SelectDate.aspx?date="+ txtDate.value; txtDate.value=ShowModelDialog(sPath, "",""); 
}

//流程中，弹出手工选择审批人的界面
function SelNodeDealer(strExeid,StartNodeCode,Nodecode,Memo)
{
	var url = "/HR/eWorkFlow/SelNodeDealer.aspx?Flag=1&ExeID=" + strExeid+"&StartNodeCode="+StartNodeCode+"&NodeCode="+Nodecode+"&Memo="+Memo; 
	var sReturn = ShowModelDialog(url,"", "dialogHeight:500px; dialogWidth: 700px; ");
    if(sReturn==null) 
	{
        alert("您没有选择审批人（或者你的IE设置阻止了选择审批人窗口的弹出）！\r\n如果是没有弹出选择界面，请修改好IE设置后，到审批中心再次处理！");
    }
    var hid=document.getElementById('HidOptionNoSel')	 
    if (hid==null) return
	if(sReturn==null) 
	{
	    hid.value = "false";
        //alert("false");
	    document.forms[0].submit();
	}
    else
    {
        hid.value = sReturn;
        //alert(sReturn);
	    document.forms[0].submit();
    }
}

//批量选择审批人中，弹出手工选择审批人的界面,区别在于打开的界面中，只返回，不直接操作
function SelNodeDealer2(strExeid,StartNodeCode,Nodecode,Memo)
{
	var url = "/HR/eWorkFlow/SelNodeDealer.aspx?Flag=2&ExeID=" + strExeid+"&StartNodeCode="+StartNodeCode+"&NodeCode="+Nodecode+"&Memo="+Memo; 
	var sReturn = ShowModelDialog(url,"", "dialogHeight:500px; dialogWidth: 700px; ");
    if(sReturn==null) 
	{
        alert("您没有选择审批人（或者你的IE设置阻止了选择审批人窗口的弹出）！\r\n如果是没有弹出选择界面，请修改好IE设置后，到审批中心再次处理！");
    }
    var hid=document.getElementById('HidOptionNoSel')	 
    if (hid==null) return
	if(sReturn!=null) 
	{
	    
        hid.value = sReturn;
	    document.forms[0].submit();
    }
}

//提交申请，或者审批过程中，遇到多节点需要选择审批人时，弹出批量选择审批人界面
function ShowWaitSelDealer()
{
	var url = "/HR/eWorkFlow/BatchSelDealer.aspx"; 
	var sReturn = ShowModelDialog(url,"", "dialogHeight:550px; dialogWidth: 800px;resizable:yes;");
    var hid=document.getElementById('HidOptionNoSel')	 
    if (hid==null) return
	if(sReturn==null) 
	{
	    hid.value = "false";
	    document.forms[0].submit();
	}
    else
    {
        hid.value = sReturn;
	    document.forms[0].submit();
    }
}

//在桌面上，弹出批量选择审批人界面
function ShowWaitSelDealerOnDesktop()
{
	var url = "/HR/eWorkFlow/BatchSelDealer.aspx"; 
	var sReturn = ShowModelDialog(url,"", "dialogHeight:600px; dialogWidth: 900px;resizable:yes;");
    var hid=document.getElementById('hidRefresh')	 
    if (hid==null) return
	if(sReturn!=null) 
	{
        hid.value = sReturn;
	    document.forms[0].submit();
    }
}

//取得文本的长度，一个文字占二个宽度
function strlength(str) {
	var l = str.length;
	var n = l;
	for (var i = 0; i < l; i++) { if (str.charCodeAt(i) < 0 || str.charCodeAt(i) > 255) n++; }
	return n;
}
//字符变动时，更新提示
function changebyte(txbNote) {
	var value = txbNote.value;
	var length = 1000;
	var l = strlength(value);
	if (l <= length) { if (document.all != null) document.all("byte").innerText = "审批意见还可以输入 " + (length - l) + " 个字符（一个汉字占2个字符）"; }
	else { document.all("byte").innerText = "审批意见超出了 " + (l - length) + " 个字符（一个汉字占2个字符）"; }
	return true;
}

//点确认时，检查字符是否超出
function checkOnSubmit(txbNote) {
	changebyte(txbNote);
	var str = document.all("byte").innerText;
	if (str.indexOf("超出了 ") > -1) {
		alert(str);
		return false;
	}
    document.getElementById("btn_yes").style.display= "none";//禁用按钮，避免多次点击,注意这里不能用disable否则不会回传。
	return true;
}
