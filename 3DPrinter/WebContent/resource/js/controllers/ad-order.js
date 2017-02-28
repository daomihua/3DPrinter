/*------------------------------ 代加工商查看生产订单列表 ---------------------------*/
angular.module('PrinterApp')
.controller('OrderCtrl', ['$scope','$state', 'OrderService',
function($scope,$state,OrderService){
	
	$scope.selectedState = {
       value: "2"
   };
	
	$scope.filterOptions = {
		       filterText: "",
		       useExternalFilter: true
		   };
	$scope.searchtext;

    $scope.totalServerItems = 0;
    $scope.pagingOptions = {
        pageSizes: [10, 20, 30],
        pageSize: 10,
        currentPage: 1
    };  
    $scope.setPagingData = function(data, page, pageSize){  
        var pagedData = data.slice((page - 1) * pageSize, page * pageSize);
        $scope.myData = pagedData;
        $scope.totalServerItems = data.length;
        if (!$scope.$$phase) {
            $scope.$apply();
        }
    };

    $scope.getPagedDataAsync = function (pageSize, page,state,searchText) {
       setTimeout(function () {
           var data;
           if (searchText) {
               var ft = searchText.toLowerCase();
               (new OrderService({
                 	'state':state
                 })).$queryOrdersByStateAD({key:state}).then(function(resp){
                    if(resp.state){
                        data = resp.data.filter(function(item) {
                           return JSON.stringify(item).toLowerCase().indexOf(ft) != -1;
                        });
                        $scope.setPagingData(data,page,pageSize); 
                    }else{
                        console.log('get data error');
                    }   
                });           
           } else {
        	   (new OrderService({
                  	'state':state
                  })).$queryOrdersByStateAD({key:state}).then(function(resp){
                       $scope.setPagingData(resp.data,page,pageSize);  
                   });
           }
       }, 100);
   };

   $scope.getPagedDataAsync($scope.pagingOptions.pageSize, $scope.pagingOptions.currentPage,$scope.selectedState.value,$scope.filterOptions.filterText);

    $scope.$watch('pagingOptions', function (newVal, oldVal) {
        if (newVal !== oldVal && newVal.currentPage !== oldVal.currentPage) {
          $scope.getPagedDataAsync($scope.pagingOptions.pageSize, $scope.pagingOptions.currentPage,$scope.selectedState.value,$scope.filterOptions.filterText);
        }
    }, true);
    
    $scope.$watch('filterOptions', function (newVal, oldVal) {
        if (newVal !== oldVal) {
          $scope.getPagedDataAsync($scope.pagingOptions.pageSize, $scope.pagingOptions.currentPage, $scope.selectedState.value,$scope.filterOptions.filterText);
        }
    }, true);

    $scope.selectedRows=[];
//已取消
	$scope.gridOptions = {
        data: 'myData',
       columnDefs: [
	     	{field:'orderID', displayName:'订单编号'},
	     	{field:'userName', displayName:'用户'}, 
	     	{field:'orderPrice', displayName:'总价'}, 
	     	{field:'designer', displayName:'设计师'}, 
	     	{field:'orderState', displayName:'状态'},
	     	{field:'orderDate',displayName:'下单时间',cellFilter:'date:"yyyy-MM-dd HH:mm:ss"'},
	     	{field: "",width: 100, 
	           		cellTemplate: '<button type="button" class="btn btn-edit"  ng-click="toOrderDetail()" >查看</button>'  
	         }
	     ],
	     showSelectionCheckbox: true, 
        enableRowSelection: true,
        selectedItems:$scope.selectedRows,//$scope.selectedRows
        multiSelect:false,
        enablePaging: true, 
        pagingOptions: $scope.pagingOptions, 
        filterOptions: $scope.filterOptions,
        showFooter: true,  
    };

    //查询
    $scope.search=function(){
    	console.log($scope.selectedState.value);
    	$scope.getPagedDataAsync($scope.pagingOptions.pageSize, $scope.pagingOptions.currentPage,$scope.selectedState.value,$scope.filterOptions.filterText);
    }
    
    //查看订单详情
    $scope.toOrderDetail=function(){
    	var id=$scope.selectedRows[0].orderID;
    	$state.go('admin.orderdetail',{orderID:id});
    }

}]);

/*--------------------------------- 代价工商、设计师查看生产订单详情 -----------------------------*/
angular.module('PrinterApp')
.controller('DetailCtrl', ['$scope','$rootScope','$http','$state','$stateParams','OrderService', 'OrderItemService',
function($scope,$rootScope,$http,$state,$stateParams,OrderService,OrderItemService){
	
	$scope.orderid=$stateParams.orderID;
	var count=0;
	var sum=0;
	
	(new OrderService({
		'orderID':$scope.orderid
	})).$queryOrder({key:$scope.orderid}).then(function(resp){
		$scope.order = resp.data;
		$scope.ordernum=$scope.order.items.length;
		if($scope.order.orderState=='待报价'){
			$scope.edite=true;
		}
		console.log(resp.msg);
		console.log($scope.ordernum);
	});
	
	//加工商确认报价
	$scope.checkPrice=function(price,modelID){
		var p=parseFloat(price);
		if(isNaN(p)){
			alert("请输入数字");
		}else
		if(price!=null && price!=undefined){
			(new OrderItemService({
				'price':price,
				'modelID':modelID,
				'orderID':$scope.orderid
			})).$putPrice().then(function(resp){
				if(resp.state){
					count++;
					sum+=p;
				}	
				alert(resp.msg);
			});
		}else{
			alert("请输入报价");
		}
	};
	
	//加工商确认报价订单
	$scope.checkOrder=function(){
		if(count==$scope.ordernum){
			(new OrderService({
				'orderID':$scope.orderid,
				'price':sum
			})).$offerOrder({ key1:$scope.orderid,key2:sum}).then(function(resp){
				alert(resp.msg);
			});
		}else{
			alert("请重新补全报价");
			$state.go('admin.orderdetail',{orderID:$scope.orderid});
		}
	};
	
	//下载模型文件
	$scope.download=function(file){
    	var m={
    			modelName:file
    	};
    	$http({
    	    url: 'rest/models/download',
    	    method: "POST",
    	    data: m,
    	    headers: {
    	       'Content-type': 'application/json'
    	    },
    	    responseType: 'arraybuffer'
    	}).success(function (data, status, headers, config) {
    	    var blob = new Blob([data], {type: "application/zip"});
    	    var objectUrl = URL.createObjectURL(blob);
    	    window.open(objectUrl);
    	    model.download+=1;
    	}).error(function (data, status, headers, config) {
    	    //upload failed
    	});
	};
	
}]);


/*
		设计订单部分
*/
/*------------------------- 设计师查看个人接收的设计订单 -----------------------*/
angular.module('PrinterApp')
.controller('DesignOrderCtrl', ['$scope','$rootScope','$state','OrderService', 
function($scope,$rootScope,$state,OrderService){
	$scope.selectedState = {
		 value: ""
	};
	var designer=null;
	var showprice=true;
//	if($rootScope.currentAdmin.roleName=='设计师'){
	var admin= JSON.parse(sessionStorage.getItem("Admin"));
		designer=admin.adName;
		showprice=false;
		console.log(admin);
		console.log(designer);
//	}
	
	
	$scope.filterOptions = {
		       filterText: "",
		       useExternalFilter: true
		   };
	$scope.searchtext;

    $scope.totalServerItems = 0;
    $scope.pagingOptions = {
        pageSizes: [10, 20, 30],
        pageSize: 10,
        currentPage: 1
    };  
    $scope.setPagingData = function(data, page, pageSize){  
        var pagedData = data.slice((page - 1) * pageSize, page * pageSize);
        $scope.myData = pagedData;
        $scope.totalServerItems = data.length;
        if (!$scope.$$phase) {
            $scope.$apply();
        }
    };

    $scope.getPagedDataAsync = function (pageSize, page,state,searchText) {
       setTimeout(function () {
           var data;
           if (searchText) {
               var ft = searchText.toLowerCase();
               (new OrderService({
                 	'state':state,
                 	'designer':designer
                 })).$queryDesignOrders().then(function(resp){
                    if(resp.state){
                        data = resp.data.filter(function(item) {
                           return JSON.stringify(item).toLowerCase().indexOf(ft) != -1;
                        });
                        $scope.setPagingData(data,page,pageSize); 
                    }else{
                        console.log('get data error');
                    }   
                });           
           } else {
        	   (new OrderService({
        		   'state':state,
                	'designer':designer
                  })).$queryDesignOrders().then(function(resp){
                	  data=resp.data;
                       $scope.setPagingData(resp.data,page,pageSize);
                       console.log(resp.data);
                   });
           }
       }, 100);
   };

   $scope.getPagedDataAsync($scope.pagingOptions.pageSize, $scope.pagingOptions.currentPage,$scope.selectedState.value,$scope.filterOptions.filterText);

    $scope.$watch('pagingOptions', function (newVal, oldVal) {
        if (newVal !== oldVal && newVal.currentPage !== oldVal.currentPage) {
          $scope.getPagedDataAsync($scope.pagingOptions.pageSize, $scope.pagingOptions.currentPage,$scope.selectedState.value,$scope.filterOptions.filterText);
        }
    }, true);
    
    $scope.$watch('filterOptions', function (newVal, oldVal) {
        if (newVal !== oldVal) {
          $scope.getPagedDataAsync($scope.pagingOptions.pageSize, $scope.pagingOptions.currentPage, $scope.selectedState.value,$scope.filterOptions.filterText);
        }
    }, true);

    $scope.selectedRows=[];

	$scope.gridOptions = {
        data: 'myData',
       columnDefs: [
	     	{field:'dorderID', displayName:'订单编号'},
	     	{field:'userName', displayName:'用户'}, 
	     	{field:'userPrice', displayName:'用户报价',visable:showprice}, 
	     	{field:'designer', displayName:'设计师'}, 
	     	{field:'state', displayName:'状态'},
	     	{field:'dorderDate',displayName:'下单时间',cellFilter:'date:"yyyy-MM-dd HH:mm:ss"'},
	     	{field: "",width: 100, 
	           		cellTemplate: '<button type="button" class="btn btn-edit"  ng-click="toOrderDetail()" >查看</button>'  
	         }
	     ],
	    showSelectionCheckbox: true, 
        enableRowSelection: true,
        selectedItems:$scope.selectedRows,//$scope.selectedRows
        multiSelect:false,
        enablePaging: true, 
        pagingOptions: $scope.pagingOptions, 
        filterOptions: $scope.filterOptions,
        showFooter: true,  
    };


    //
    $scope.search=function(){
    	console.log($scope.selectedState.value);
    	$scope.getPagedDataAsync($scope.pagingOptions.pageSize, $scope.pagingOptions.currentPage,$scope.selectedState.value,$scope.filterOptions.filterText);
    };
    
    $scope.toOrderDetail=function(){
    	var id=$scope.selectedRows[0].dorderID;
    	$state.go('admin.dorderdetail',{orderid:id});
    };
}]);


/*----------------------- 加工商、设计师查看设计订单详情 ----------------------*/
angular.module('PrinterApp')
.controller('DOrderDetailCtrl', ['$scope','$rootScope','$http','$state','$stateParams','OrderService', 
function($scope,$rootScope,$http,$state,$stateParams,OrderService){
	var id=$stateParams.orderid;
	(new OrderService()).$queryDOrder({key:id})
	.then(function(resp){
		$scope.order=resp.data;
		console.log(resp.msg);
	});
	
	//设计师下载需求文档
	$scope.download=function(file){
    	var order={
    			demandFile:file
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
    };
	
	//设计师编辑订单内容
	$scope.changeOrder=function(order){
		var isback=confirm('确定要修改此订单吗？');
		if(isback){
			(new OrderService({
				'dorderID':id,
				'demand':$scope.order.demand,
				'demandFile':$scope.order.demandFile
			})).$updateDOrder().then(function(resp){
				alert(resp.msg);
				if(resp.state)
					$rootScope.back();
			});
		}
	};
	
	//设计使确认订单
	$scope.checkOrder=function(){
		var isback=confirm('确定要确认此订单吗？');
		if(isback){
			(new OrderService()).$checkDOrder({key:id}).then(function(resp){
				alert(resp.msg);
			});
		}
	};
	
	//设计师返回设计文件
	$scope.backOrder=function(){
		var isback=confirm('确定要返回此订单的设计文件吗？');
		if(isback){
			(new OrderService({
				'dorderID':id,
				'designFile':$scope.order.designFile
			})).$backDOrder().then(function(resp){
				alert(resp.msg);
			});
		}
	};
	
	//代价工商确认报价
	$scope.checkPrice=function(){
		
		var price=$scope.order.proPrice;
		console.log($rootScope.currentAdmin);
		if(price==null || price==undefined)
			alert("请输入报价");
		else if(!isNaN(price)){
			var ischeck=confirm('确认报价为￥'+price+"?");
			if(ischeck){
				(new OrderService({
					'proPrice':price,
					'dorderID':id,
					'process':$rootScope.currentAdmin.adName
				})).$checkDOderPrice().then(function(resp){
					alert(resp.msg);
				});
			}
		}else
			alert("报价必须为数字 ");
	};
	
	//打印表格
	$scope.print=function(){
		console.log($scope.html);
		$http({
    	    url: 'rest/orders/print/word',
    	    method: "POST",
    	    data: $scope.html, //this is your json data string
    	    headers: {
    	       'Content-type': 'application/json'
    	    },
    	    responseType: 'arraybuffer'
    	}).success(function (data, status, headers, config) {
    	    var blob = new Blob([data], {type: "application/msword"});
    	    var objectUrl = URL.createObjectURL(blob);
    	    window.open(objectUrl);
    	}).error(function (data, status, headers, config) {
    		alert('该文件暂时无法提供相关下载');
    	});
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

//上传设计文档
angular.module("PrinterApp")
	.controller('DesignFileCtrl',function($scope,$http,baneFileFactory){
		    var type=[".doc",".docx",".stl",".jgp"];
			$scope.imgType=type;
			$scope.fileTypes=type;
			$scope.data={"a":"1","b":"2"};
			$scope.onSelect=function(files,error,dataForm){
				console.log(dataForm[0]);
				baneFileFactory.uploadDataForm("rest/orders/design/file",dataForm[0]).then(function(resp){
					alert(resp.data.msg);
					$scope.order.designFile = resp.data.filename;
					console.log($scope.order.designFile);
				});
			};	
});