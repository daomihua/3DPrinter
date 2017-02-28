angular.module('PrinterApp')
.controller('EditorCtrl', function($scope,$rootScope,$http,baneFileFactory,CaseService) {
	$scope.c={};

 	$scope.c.caseContent = '<h4 style="color:#B8B7B7;">在这里编辑案例</h4>';

	(new CaseService()).$getCategory().then(function(resp){
		$scope.list = resp.data;
	  	console.log($scope.list);
	 });

	//上传案例缩略图
	var type=[".jpg",".jpge",".png",".gif"];
	$scope.imgType=type;
	$scope.data={"a":"1","b":"2"};
	$scope.onSelect=function(files,error,dataForm){
		console.log(dataForm[0]);
		baneFileFactory.uploadDataForm("rest/cases/upload",dataForm[0]).then(function(resp){
			if(resp.data.state){
				alert('缩略图上传成功！');
				$scope.c.caseImg=resp.data.url;
			}
		});
	};
	
	//发布案例
	$scope.publishCase= function(){
		console.log($scope.c);
		if($scope.c.caseTile==null){
			alert("请输入案例标题");
		}else if($scope.c.caseContent==null){
			alert("请选输入案例内容");
		}else if($scope.c.category==null){
			alert("请选择案例类型");
		}else if($scope.c.caseImg==null){
			alert("请上传缩略图");
		}
		else{
			console.log($scope.c.category.category);
			(new CaseService({
				'caseContent':$scope.c.caseContent,
				'caseTitle':$scope.c.caseTile,
				'category':$scope.c.category.category,
				'publisher':$rootScope.currentAdmin.adName,
				'caseImg':$scope.c.caseImg
			})).$addCase().then(function(resp){
				alert(resp.msg);
				$scope.c.caseContent=null;
			});
		}
	};

});


angular.module("PrinterApp")
.controller('addCaseImgCtrl',function($scope,$http,baneFileFactory){
	var type=[".jpg",".jpge",".png",".gif"];
		$scope.imgType=type;
		$scope.data={"a":"1","b":"2"};
		$scope.onSelect=function(files,error,dataForm){
			console.log(dataForm[0]);
			baneFileFactory.uploadDataForm("rest/cases/upload",dataForm[0]).then(function(resp){
				alert('图片路径为：'+resp.data.url);
//				$scope.c.caseImg=resp.data.url;
			});
		};
		
});
