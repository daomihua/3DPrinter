/*------------------ 设计师列表页 -------------------------*/
angular.module('PrinterApp')
.controller('DeginerCtrl', ['$scope', 'AdminService',
function($scope,AdminService){
	(new AdminService()).$queryDesigners().then(function(resp){
		$scope.designers=resp.data;
	});
	
}]);

/*-------------------- 设计师个人主页 --------------------------------*/
angular.module('PrinterApp')
.controller('OneDeginerCtrl', ['$scope','$stateParams', 'AdminService', 'OrderItemService',
function($scope,$stateParams,AdminService,OrderItemService){

	var admin_id=$stateParams.adID;
	console.log(admin_id);
	(new AdminService()).$queryAdmin({key:admin_id}).then(function(resp){
		$scope.designer=resp.data;
	});

	(new OrderItemService()).$getItems({key:admin_id}).then(function(resp){
		$scope.items=resp.data;
	});
	
	$scope.showimg=function(model){
		$scope.show.title=model.title;
		$scope.show.image=model.modelPic;
	};

}]);

/*-------------------------------- 用户提交设计订单 -------------------------*/
angular.module('PrinterApp')
.controller('UserDesignCtrl', ['$scope','$rootScope','$stateParams','OrderService',
function($scope,$rootScope,$stateParams,OrderService){
	$scope.order={};
	$scope.order.designer=$stateParams.designer;
	
	$scope.subOrder=function(){
		console.log($scope.order.demand+"---"+$scope.order.demandFile);
		if(!(/^(((13[0-9]{1})|(15[0-9]{1})|(18[0-9]{1}))+\d{8})$/).test($scope.order.phone) 
	            && ($scope.order.phone.length !== 11))
			alert("请输入有效的电话号码");
		else if(isNaN(Number($scope.order.userPrice)))
			alert("报价必须是数字");
		else if(($scope.order.demand!=null || $scope.order.demand!=undefined) 
			|| ($scope.order.demandFile!=null || $scope.order.demandFile!=undefined))
		{
			console.log($scope.order);
			$scope.order.userName=$rootScope.currentUser.userName;
			(new OrderService({
				'designer':$scope.order.designer,
				'userName':$scope.order.userName,
				'demand':$scope.order.demand,
				'demandFile':$scope.order.demandFile,
				'userPrice':$scope.order.userPrice,
				'phone':$scope.order.phone
			})).$addDesignOrder().then(function(resp){
				alert(resp.msg);
			});
		}else{
			alert("需求描述和需求文档至少要提交一份");
		}
	};

}]);

//上传需求文档
angular.module("PrinterApp")
	.controller('DemandFileCtrl',function($scope,$http,baneFileFactory){
		    var type=[".doc",".docx",".txt",".jgp"];
			$scope.imgType=type;
			$scope.fileTypes=type;
			$scope.data={"a":"1","b":"2"};
			$scope.onSelect=function(files,error,dataForm){
				console.log(dataForm[0]);
				baneFileFactory.uploadDataForm("rest/orders/design/demand",dataForm[0]).then(function(resp){
					alert(resp.data.msg);
					$scope.order.demandFile = resp.data.filename;
					console.log($scope.order.demandFile);
				});
			};
			
			
		});
