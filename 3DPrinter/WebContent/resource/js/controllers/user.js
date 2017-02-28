/*------------------------ 新用户注册 -----------------------------*/
angular.module('PrinterApp')
.controller('RegisterCtrl', ['$scope', '$rootScope','$state','UserService',
function($scope,$rootScope,$state,UserService){

	$scope.validate=function(){
		if( !$scope.show1 && !$scope.show2 && !$scope.show3 && !$scope.show4 && !$scope.show5){
			return true;
		}else
			return false;
	};

	$scope.check=function(){
		if($scope.checkpwd == null)
			$scope.show3=true;
		else if($scope.password != $scope.checkpwd){
			$scope.show3=false;
			$scope.show4=true;
		}else{
			$scope.show3=false;
			$scope.show4=false;
		}
	};

	$scope.register = function(){
		if( !$scope.show1 && !$scope.show2 && !$scope.show3 && !$scope.show4 && !$scope.show5){
			(new UserService({
				'userName':$scope.userName,
				'password':$scope.password,
				'email':$scope.email
			})).$addUser().then(function(resp){
				alert(resp.msg);
				if(resp.state){
					$rootScope.isLogin=resp.state;
					$rootScope.currentUser=resp.data;
					$rootScope.isLogin = true;
					$state.go('index.home');
					alert(resp.msg);
					// $rootScope.back();
				}
			});
		}else if($scope.userName=='' || $scope.password=='' || $scope.email==''){
			console.log($scope.check);
			alert('请完善你的信息');
		}
	};
	
}]);

/*--------------------------- 个人中心管理 ----------------------------*/
angular.module('PrinterApp')
.controller('PersonCtrl', ['$scope', '$rootScope','UserService',
function($scope,$rootScope,UserService){

	var user = JSON.parse(sessionStorage.getItem("User"));;

	(new UserService()).$queryUser({key:user.userID}).then(function(resp){
		$scope.user=resp.data;
		$scope.checkpwd=$scope.user.password;
	});

	$scope.save = function(){
		if( !$scope.show2 && !$scope.show3 && !$scope.show4 && !$scope.show5){
			console.log("aaa");
			(new UserService({
				'userID':$scope.user.userID,
				'realName':$scope.user.realName,
				'password':$scope.user.password,
				'email':$scope.user.email,
				'sex':$scope.user.sex,
				'address':$scope.user.address,
				'phone':$scope.user.phone,
				'postCode':$scope.user.postCode
			})).$updateUser().then(function(resp){
				alert(resp.msg);
				if(resp.state){
					sessionStorage.setItem("User",JSON.stringify(resp.data));
				}
			});
		}else if($scope.userName=='' || $scope.password=='' || $scope.email==''){
			console.log($scope.userName);
			alert('请完善你的信息');
		}
		console.log($scope.userName);
	};

	$scope.check=function(){
		console.log($scope.user.password);
		console.log($scope.checkpwd);
		if($scope.checkpwd == null)
			$scope.show3=true;
		else if($scope.user.password != $scope.checkpwd){
			$scope.show3=false;
			$scope.show4=true;
		}else{
			$scope.show3=false;
			$scope.show4=false;
		}
			
	};

}]);

/*-------------------------- 工艺文件管理 --------------------------------*/
angular.module('PrinterApp')
.controller('ProcessFileCtrl', ['$scope','$rootScope', '$http','ModelService','baneFileFactory',
function($scope,$rootScope,$http,ModelService,baneFileFactory){

	$scope.filterOptions = {
       filterText: "",
       useExternalFilter: true
   };

	$scope.totalServerItems = 0;
    $scope.pagingOptions = {
        pageSizes: [10, 20, 30],
        pageSize: 10,
        currentPage: 1
    };  
    $scope.setPagingData = function(data, page, pageSize){  
        var pagedData = data.slice((page - 1) * pageSize, page * pageSize);
//        console.log(pagedData);
        $scope.myData = pagedData;
        $scope.totalServerItems = data.length;
        if (!$scope.$$phase) {
            $scope.$apply();
        }
    };

    $scope.getPagedDataAsync = function (pageSize, page, searchText) {
       setTimeout(function () {
           var data;
           if (searchText) {
               var ft = searchText.toLowerCase();
               (new ModelService()).$queryModels().then(function(resp){
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
               (new ModelService()).$queryModels().then(function(resp){
                    $scope.setPagingData(resp.data,page,pageSize);  
                });
           }
       }, 100);
   };

    $scope.getPagedDataAsync($scope.pagingOptions.pageSize, $scope.pagingOptions.currentPage);

    $scope.$watch('pagingOptions', function (newVal, oldVal) {
        if (newVal !== oldVal && newVal.currentPage !== oldVal.currentPage) {
          $scope.getPagedDataAsync($scope.pagingOptions.pageSize, $scope.pagingOptions.currentPage, $scope.filterOptions.filterText);
        }
    }, true);

    $scope.$watch('filterOptions', function (newVal, oldVal) {
       if (newVal !== oldVal) {
         $scope.getPagedDataAsync($scope.pagingOptions.pageSize, $scope.pagingOptions.currentPage, $scope.filterOptions.filterText);
       }
   }, true);

    $scope.selectedRows=[];

	$scope.gridOptions = {
        data: 'myData',
        columnDefs: [
        	{field:'modelName', displayName:'文件名',enableCellEdit: false}, 
        	{field:'uploadDate',displayName:'上传日期',enableCellEdit: false},
        	{field: "",width: 150, enableCellEdit: false,  
              		cellTemplate: '<button type="button" class="btn btn-warning s-btn" ng-click="download()">下载</button>'+
              		'<button type="button" class="btn btn-info s-btn" ng-transclude show-upload-model>分享</button>'
            }
        ],
        showSelectionCheckbox: true,  
        enableRowSelection: true,
        selectedItems:$scope.selectedRows,//$scope.selectedRows
        multiSelect:true,
        // enablePinning: true,  
        enablePaging: true, 
        pagingOptions: $scope.pagingOptions, 
        filterOptions: $scope.filterOptions,
        showFooter: true
    };

    //下载工艺文件
    $scope.download=function(index){
    	$scope.selectedRows[0].modelName;
    	console.log(index);
    	var model={
    			modelID:$scope.selectedRows[0].modelID,
    			modelName:$scope.selectedRows[0].modelName
    	};
    	$http({
    	    url: 'rest/models/download',
    	    method: "POST",
    	    data: model,
    	    headers: {
    	       'Content-type': 'application/json'
    	    },
    	    responseType: 'arraybuffer'
    	}).success(function (data, status, headers, config) {
    	    var blob = new Blob([data], {type: "application/zip"});
    	    var objectUrl = URL.createObjectURL(blob);
    	    window.open(objectUrl);
    	}).error(function (data, status, headers, config) {
    	    alert("该文件暂时不提供下载");
    	});
    };
  
    //删除所选数据
    $scope.deleteModels = function(){
        var count=$scope.selectedRows.length;
        for (var i = $scope.selectedRows.length - 1; i >= 0; i--) {
            console.log($scope.selectedRows[i].adID);
            (new ModelService()).$deleteModel({ key:$scope.selectedRows[i].modelID }).then(function(resp){
                if(resp.state){
                    count--;
                }
                if( count==0 ){
                    alert("模型文件删除成功!");
                    $scope.getPagedDataAsync($scope.pagingOptions.pageSize, $scope.pagingOptions.currentPage, $scope.filterOptions.filterText);
                }
            });
        }  
    };
    

}]);

//------------------------- 用户分享模型 -------------------------------//
angular.module('PrinterApp')
.controller('ModelUploadCtrl',
function($scope,$rootScope,$http,ModelService,baneFileFactory){
	$scope.model={};
	
	var type=[".jpg",".jpge",".png",".gif"];
	$scope.imgType=type;
	$scope.data={"a":"1","b":"2"};
	$scope.onSelect=function(files,error,dataForm){
		console.log(dataForm[0]);
		baneFileFactory.uploadDataForm("rest/models/upload",dataForm[0]).then(function(resp){
			alert(resp.data.msg);
			$scope.model.modelPic = resp.data.url;
			console.log($scope.model.modelPic);
		});
	};

	 $http({
	    	method:'GET',url:'resource/js/category.json'
	    }).then(function(resp){
	    	$scope.list=resp.data.data;
	    	console.log($scope.list);
	    });
	 
	//分享文件至模型库
	$scope.shareModel=function(){
		$scope.model.modelID=$scope.selectedRows[0].modelID;
		$scope.model.category=$scope.modelcategory.category;
		$scope.model.title=$scope.mtitle;
		
		console.log($scope.model);

		if($scope.modelcategory==null && $scope.modelcategory==undefined)
    		$scope.show=true;
    	else
    		$scope.show=false;
    	
    	if(!$scope.show1)
    		if(!$scope.show)
    		if($scope.model.modelPic==null && $scope.model.modelPic==undefined){
    			alert("请上传图片");
    		}
    		else{
    			console.log($scope.modelcategory.caregory);
    			(new ModelService({
    	        	'modelID':$scope.model.modelID,
    	        	'modelPic':$scope.model.modelPic,
    	        	'title':$scope.model.title,
    	        	'share':true,
    	        	'category':$scope.model.category
    	        })).$shareModels().then(function(resp){
    	        		alert(resp.msg);
    	        });
    		}
	};

});

//-----------------------3D模型库-----------------------------//
angular.module('PrinterApp')
.controller('LibraryCtrl', ['$scope','$rootScope','$http', 'CaseService','ModelService',
function($scope,$rootScope,$http,CaseService,ModelService){
  $scope.ca2="全部";
  $scope.show={};
  
  (new CaseService()).$getCategory().then(function(resp){
    $scope.category=resp.data;
  });
  (new ModelService()).$queryLibModels().then(function(resp){
    $scope.models=resp.data;
    console.log($scope.models);
  });
  
    //分类查看
    $scope.selectCategory=function(category){
		console.log(category);
		$scope.ca=category;
		if(category==null|| category==undefined)
			$scope.ca2='全部';
		else
			$scope.ca2=category;
	};
	
	//查看最新/最热
	$scope.change=function(value){
		if(value=='uploadDate'){
			$scope.od1='uploadDate';
			$scope.od2=true;	
//			console.log($scope.od1+"+"+$scope.od2);
		}else if(value=='download'){
			$scope.od1='download';
			$scope.od2=true;
//			console.log($scope.od1+"+"+$scope.od2);
		}
	};
	
	//下载模型文件
	$scope.download=function(model){
    	var m={
    			modelID:model.modelID,
    			modelName:model.modelName
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
    
    $scope.showImg=function(model){
    	console.log(model);
    	$scope.show.title=model.title;
    	$scope.show.image=model.modelPic;
    };

}]);