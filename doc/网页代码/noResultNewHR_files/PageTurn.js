function val_num(field) {
  if (field.value=="")
  {
   alert("错误！\n\n请输入正确数字！");
   field.focus();    
   field.select();
   return false;
  }  
  else if (field.value.indexOf(" ")>=0)
  {
   alert("错误！\n\n请输入正确数字！\n勿包含空格！");
   field.focus();    
   field.select();
   return false;
  }
  else if (isNaN(field.value)) 
  { 
   alert("错误！\n\n请输入正确数字！");
   field.focus();    
   field.select();
   return false;
  }
  else if (field.value.indexOf('.')>=0)
  {
   alert("错误！\n\n请输入整数！")
   field.focus();    
   field.select();
   return false;
  }
  else if (parseInt(field.value)<=0)
  {
   alert("错误！\n\n请输入正确数字！");
   field.focus();    
   field.select();
   return false;
  }
  return true;
}

if (typeof (Sys) != "undefined") Sys.Application.notifyScriptLoaded();