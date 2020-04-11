/*
 * 
 * */

layui.use(['element','jquery','layer'], function(){
  var element = layui.element;
  var $=layui.jquery;
  var layer=layui.layer;
  
  $('#pile-message').on('click',function(){
	  //console.log('点击了充电桩详情标签');
	  //调用pilelist.js中的方法pileTable()
	  layui.pileTable();
  });
  
});