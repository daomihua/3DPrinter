
//-------------------------------- 生产订单详情 ----------------------------------------

angular.module('PrinterApp')
.controller('OrderDetailCtrl', ['$scope','$rootScope', '$stateParams','OrderService',
function($scope,$rootScope,$stateParams,OrderService){
	$scope.orderid=$stateParams.orderID;
	(new OrderService()).$queryOrder({key:$scope.orderid}).then(function(resp){
		$scope.order = resp.data;
		console.log(resp.msg);
	});

	//支付订单
	$scope.payOrder=function(){
		var check=confirm('确定要支付此订单吗？');
		if(check){
			(new OrderService()).$updateOrder({key1:4,key2:$scope.order.orderID})
			.then(function(resp){
				if(resp.state){
					alert('付款成功！');
					$rootScope.back();
				}
			});
		}
		
	};
	
	//取消订单
	$scope.cancelOrder=function(){
		(new OrderService()).$updateOrder({key1:0,key2:$scope.order.orderID})
		.then(function(resp){
			if(resp.state){
				alert('订单取消！');
				$rootScope.back();
			}
		});
	};

}]);


//------------------------------- 设计订单详情 --------------------------------

angular.module('PrinterApp')
.controller('DOrderDetailCtrl', ['$scope','$rootScope','$http', '$stateParams','OrderService',
function($scope,$rootScope,$http,$stateParams,OrderService){
	$scope.orderid=$stateParams.orderid;
	
	(new OrderService()).$queryDOrder({key:$scope.orderid}).then(function(resp){
		$scope.order = resp.data;
		console.log(resp.msg);
	});

	//设计订单付款
	$scope.payOrder=function(){
		var check=confirm('确定要支付此订单吗？');
		if(check){
			(new OrderService({
				'dorderID':$scope.orderid,
				'state':'待设计'
			})).$userUpdateOrder().then(function(resp){
				if(resp.state){
					alert('订单付款成功');
					$scope.orders.splice(index,1);
				}
			});
		}
		
	};
	
	//取消设计订单
	$scope.cancelOrder=function(index,id){
		(new OrderService({
			'dorderID':$scope.orderid,
			'state':'已取消'
		})).$userUpdateOrder().then(function(resp){
			if(resp.state){
				alert('订单取消成功');
				$scope.orders.splice(index,1);
			}
		});
	};
	
	//下载设计文档
	$scope.download=function(file){
		var order={
    			designFile:file
    	};
		$http({
    	    url: 'rest/orders/design/download',
    	    method: "POST",
    	    data: order, //this is your json data string
    	    headers: {
    	       'Content-type': 'application/json'
    	    },
    	    responseType: 'arraybuffer'
    	}).success(function (data, status, headers, config) {
    	    var blob = new Blob([data], {type: "application/zip"});
    	    var objectUrl = URL.createObjectURL(blob);
    	    window.open(objectUrl);
    	}).error(function (data, status, headers, config) {
    		alert('该文件暂时无法提供相关下载');
    	});
	}

}]);