/**
 * 
 */

/*layui.config({
  base: '/resources/js/common/' //你存放新模块的目录，注意，不是layui的模块目录
}).use('index');
*/
layui.use(['element','layer'], function(){
	var element = layui.element;
	var layer=layui.layer;
	var $=layui.jquery;
	var pileId=getQueryString('pileId');
	asd();
	function asd(){
		var electricityMapUrl='/zhou/operator/getpilebyid?pileId='+pileId;
		$.getJSON(electricityMapUrl,function(data){
			if(data.success){
				alert(data.success);
			  }else{
				  alert(data.success);
			  }
		 });
	 }
});