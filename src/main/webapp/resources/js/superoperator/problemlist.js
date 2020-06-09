/**
 * 
 */
layui.use(['jquery','table','layer','element'],function(){
	var $=layui.jquery;
	var form=layui.form;
	var table=layui.table;
	var element=element;
	
	//先把样式写出来
	var html=''+
	'<div class="layui-container ze-palette">'+
		'<div class="layui-row">'+
			'<table id="problem-table" class="layui-table" lay-filter="problemtable"></table>'+
		'</div>'+
	'</div>'+
	'<script type="text/html" id="barDemo">'+
	'<a class="layui-btn layui-btn-xs detail" lay-event="start">处理问题</a>'+
	/*'<a class="layui-btn layui-btn-xs layui-btn-danger detail" lay-event="stop">禁用</a>'+
	'<a class="layui-btn layui-btn-xs layui-btn-warm detail" lay-event="operation">分配管理员</a>'+*/
	'</script>';
	
	//需要将方法暴露出来才可以让别的js文件中的layui.use()进行调用
	layui.define(['table','layer'],function(exports){
		var table=layui.table;
		var layer=layui.layer;
		//var pileList;/*将table从后台获取的数据保存起来，在点击查看详情的时候，将数据拿出来进行显示即可*/
		//可以定义多个exports
		//区域表格
		exports('problemTable',function(){
			//先将样式插入进去，搜索框的样式
			$('.layui-body').html(html);
			//获取该管理员下的所有充电桩信息列表
			var problemUrl='/zhou/superoperator/getproblemlist';
			problemListTable(table,problemUrl);
			//对操作栏的按钮进行事件监听
			watchAreaList(table,layer);
			
		});
	});
	
	function problemListTable(table,problemUrl){
		table.render({
			elem:'#problem-table',
			url:problemUrl,
			method:'get',
			request:{
				pageName:'pageIndex',
				limitName:'pageSize'
			 },
			 cols:[[ //表头
				  {type: 'checkbox', fixed: 'left'},
				  {fixed:'left',title:'序号',align:'center',width:80,type:'numbers'},
			      {field:'problemTitle',title:'问题名称',align:'center',width:200},
			      {field:'problemEnableStatus',title:'状态',align:'center',width:80,templet:function(data){
			    	  if(data.problemEnableStatus==1){
			    		  return '<span style="color:#1E9FFF">已审核</span>';
			    	  }else{
			    		  return '<span style="color:red">未审核</span>';
			    	  }
			      }},
			      {field:'createTime',title:'提交时间',align:'center',width:200,templet:function(data){
			    	  return new Date(data.createTime).Format("yyyy-MM-dd");
			      }},
			      {field:'createTime',title:'处理时间',align:'center',width:200,templet:function(data){
			    	  return new Date(data.lastEditTime).Format("yyyy-MM-dd");
			      }},
			      {fixed: 'right',title:'操作',align:'center',toolbar:'#barDemo'}
			    ]],
		    page: { //支持传入 laypage 组件的所有参数（某些参数除外，如：jump/elem） - 详见文档
				layout: ['count', 'prev', 'page', 'next', 'skip'], //自定义分页布局
		        limit:5, //一页显示多少条
		        groups:2,//只显示 2 个连续页码
		        first: "首页", //不显示首页
		        last: "尾页", //不显示尾页
		    },
			parseData:function(data){
				return{
					'code':0,
					'msg':'',
					'count':data.count,
					'data':data.problemList
				};
			},
		});
	}
	
	var windowOpen;
	//操作栏中按钮实施监听
	function watchAreaList(table,layer){
		table.on('tool(problemtable)',function(obj){
			var problemList=obj.data;
			var problemId=problemList.problemId;
			
			switch(obj.event){
			case 'start':
				console.log('点击了启用按钮');
				//layer.msg('问题id为:'+problemList.problemId);
				//显示出弹窗，为超级管理员提供填写意见文本框
				var problem='<div style="width:590px;height:500px;">'+
				'<form class="layui-form" lay-filter="updateProblemForm" method="post" enctype="multipart/form-data" action="">'+
				  '<div class="layui-form-item layui-form-text">'+
				    '<label class="layui-form-label">问题描述</label>'+
				    '<div class="layui-input-block" style="width:400px;">'+
				      '<textarea readonly="readonly" name="desc" class="layui-textarea">'+problemList.problemDesc+'</textarea>'+
				    '</div>'+
				  '</div>'+
				  
				  '<div class="layui-form-item layui-form-text">'+
				  	'<label class="layui-form-label">图片描述</label>'+
				  	'<div class="layui-input-block" style="width:400px;">'+
				  		'<div id="problem-images" class="layui-container">'+
				  			
				  		'</div>'+  
				  	'</div>'+
				  '</div>'+
				  	
				  '<div class="layui-form-item layui-form-text">'+
				    '<label class="layui-form-label">反馈</label>'+
				    '<div class="layui-input-block">'+
				      '<textarea id="problem-salve" name="problemsalvedesc" class="layui-textarea" style="width:400px;"></textarea>'+
				    '</div>'+
				  '</div>'+
				  
				  '<div class="layui-form-item">'+
				    '<div class="layui-input-block">'+
				      '<button id="update-problem" class="layui-btn" lay-submit lay-filter="updateProblem">立即提交</button>'+
				      '<button type="reset" class="layui-btn layui-btn-primary">重置</button>'+
				    '</div>'+
				  '</div>'+
				'</form>'+
				'</div>';
				//根据id获取问题相关图片
				var problemImgUrl='/zhou/superoperator/getproblemimglist?problemId='+problemId;
				$.getJSON(problemImgUrl,function(data){
					if(data.success){
						var problemImgList=data.problemImgList;
						var imgHtml='';
						problemImgList.map(function(item,index){
							imgHtml+=''+
							'<div class="layui-row">'+
				  				'<img src="'+item.problemImgLink+'"/>'+
				  			'</div>';
						});
						$('#problem-images').html(imgHtml);
						console.log('地址:'+problemImgList[0].problemImgLink);
						modifyProblemState(problemId);
					}else{
						layer.msg("error:"+data.errMsg);
					}
				});
				windowOpen=layer.open({
					type:1,//默认的信息框比较好
					area:['600px','600px'],
					anim:1,//弹出方式
					title:'问题处理意见',//跟的是充电桩的名字
					content:problem,//充电桩的详细信息，包含所有信息
				});
				//formData.append('enableStatus',1);
				//
				break;
			}
		});
	}
	
	function modifyProblemState(problemId){
		var formData=new FormData();
		formData.append('problemId',problemId);
		formData.append('enableStatus',1);
		form.on('submit(updateProblem)',function(data){
			var problem={};//定义json
			//获取表单数据
			var problemData=form.val('updateProblemForm');
			var problemSalveDesc=problemData.problemsalvedesc;
			if(problemSalveDesc==undefined||problemSalveDesc==''){
				layer.msg('请填写反馈意见');
				return false;
			}
			problem.problemSalve=problemSalveDesc;
			formData.append('problem',JSON.stringify(problem));
			$.ajax({
				url:'/zhou/superoperator/modifyproblem',
				type:'POST',
				data:formData,
				contentType:false,
				processData:false,
				cache:false,//是否启用cache缓存
				success:function(data){
					if(data.success){
						layer.msg('反馈成功');
						layer.close(windowOpen);
						$('#problem-message').click();
					}else{
						layer.msg('反馈失败'+data.errMsg);
					}
				}
			});
			return false;
		});
	}
});





