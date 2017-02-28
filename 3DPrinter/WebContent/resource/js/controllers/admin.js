
//----------------------------- 添加管理员 -----------------------------------//
angular.module('PrinterApp')
.controller('addAdminCtrl', ['$scope','$state','AdminService','RoleService',
 function($scope,$state,AdminService,RoleService){
    (new RoleService()).$queryRoles().then(function(resp){
        $scope.roles = resp.data;
        console.log($scope.roles);
    });
    $scope.chooseRole;
    
    //添加管理员
    $scope.addAdmin= function(){
        if($scope.validate()){
            (new AdminService({
                'adName':$scope.adName,
                'adSex':$scope.adSex,
                'adPhone':$scope.adPhone,
                'adEmail':$scope.adEmail,
                'roleID':$scope.chooseRole.roleID,
                'adQQ':$scope.adQQ
            })).$addAdmin().then(function(resp){
                $scope.state = resp.state;
                alert(resp.msg);
            });
        } 
        if($scope.state)  
            $state.go('admin.addadmin');    
    };

    $scope.validate=function(){
        if($scope.adName==null) 
            $scope.show1=true;
        else $scope.show1=false;
        if($scope.adPhone==null) 
            $scope.show2=true;
        else 
        {  
            $scope.show2=false;
            if((/^(((13[0-9]{1})|(15[0-9]{1})|(18[0-9]{1}))+\d{8})$/).test($scope.adPhone) 
            && ($scope.adPhone.length === 11))
                $scope.show4=false;
            else
                $scope.show4=true;
        }
        if($scope.chooseRole==null) 
            $scope.show3=true;
        else{$scope.show3=false;}
        if(!$scope.show1 && !$scope.show2 && !$scope.show3 && !$scope.show4){
            return true;
        }else 
            return false;
    };

}]);

//---------------------------------删除管理员---------------------------------
angular.module('PrinterApp')
.controller('deleteAdminCtrl', ['$scope','$state','AdminService',
function($scope,$state,AdminService){

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
        console.log(pagedData);
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
               (new AdminService()).$queryAdmins().then(function(resp){
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
               (new AdminService()).$queryAdmins().then(function(resp){
                    $scope.setPagingData(resp.data,page,pageSize);  
                });
           }
       }, 100);
   };

    $scope.getPagedDataAsync($scope.pagingOptions.pageSize, $scope.pagingOptions.currentPage);

    $scope.$watch('pagingOptions', function (newVal, oldVal) {
        if (newVal !== oldVal && newVal.currentPage !== oldVal.currentPage) {
          $scope.getPagedDataAsync($scope.pagingOptions.pageSize, $scope.pagingOptions.currentPage,$scope.filterOptions.filterText);
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
            {field:'adName',displayName:'用户名',enableCellEdit: false},
            {field:'adPhone', displayName:'联系电话',enableCellEdit: false}, 
            {field:'adSex',displayName:'性别',enableCellEdit: false},
            {field:'adEmail',displayName:'电子邮件',enableCellEdit: false},
            {field:'roleName',displayName:'角色',enableCellEdit: false},
            {field: "",width: 100, enableCellEdit: false,  
                    cellTemplate: '<button type="button" class="btn btn-edit"  ng-click="toEditPage()" >编辑</button>'  
            }
        ],
        showSelectionCheckbox: true,  
        enableRowSelection: true,
        selectedItems:$scope.selectedRows,//$scope.selectedRows
        multiSelect:true,
        enablePinning: true,  
        enablePaging: true, 
        pagingOptions: $scope.pagingOptions, 
        filterOptions: $scope.filterOptions,
        showFooter: true,  
        enableCellSelection: true
    };

    //跳转至编辑页面
    $scope.toEditPage = function(){
        var id=$scope.selectedRows[0].adID;
        $state.go('admin.editeadmin', {adID:id}); 
    };

    //删除所选数据
    $scope.deleteAdmins = function(){
        var count=$scope.selectedRows.length;
        for (var i = $scope.selectedRows.length - 1; i >= 0; i--) {
            console.log($scope.selectedRows[i].adID);
            (new AdminService()).$deleteAdmins({ key:$scope.selectedRows[i].adID }).then(function(resp){
                if(resp.state){
                    count--;
                    if( count==0 ){
                        alert("管理员删除成功!");
                        $scope.getPagedDataAsync($scope.pagingOptions.pageSize, $scope.pagingOptions.currentPage, $scope.filterOptions.filterText);
                    }
                }
            });
        }
        
    };
    
}]);

//--------------------------------------编辑角色-----------------------------//
angular.module('PrinterApp')
.controller('editeAdminCtrl', ['$scope', '$state', '$stateParams','AdminService','RoleService',
function($scope, $state, $stateParams,AdminService,RoleService){
    
    $scope.id = $stateParams.adID;
    $scope.chooseRole={
    		roleID:'',
    		roleName:''
    };

    (new RoleService()).$queryRoles().then(function(resp){
        $scope.roles = resp.data;
    });

    (new AdminService()).$queryAdmin({key:$scope.id}).then(function(resp){
            if(resp.state){
                $scope.admin = resp.data;
                $scope.chooseRole.roleName=$scope.admin.roleName;
            }else{
                console.log('get data error');
            }   
    });
    

    $scope.saveAdmin = function(){
    	console.log($scope.validate());
        if($scope.validate()){
            (new AdminService({
            	'adID':$scope.id,
                'adName':$scope.admin.adName,
                'adSex':$scope.admin.adSex,
                'adPassword':$scope.admin.adPassword,
                'adPhone':$scope.admin.adPhone,
                'adEmail':$scope.admin.adEmail,
                'roleID':$scope.chooseRole.roleID,
                'adQQ':$scope.admin.adQQ
            })).$updateAdmin().then(function(resp){
                if(resp.state){
                	 alert( resp.msg);
                     $state.go('admin.deleteadmin');
                }else{
                	alert( resp.msg);
                }
               
            });
        }
    };

    $scope.checkPhone=function(){
    	if($scope.admin.adPhone==null || $scope.admin.adPhone==undefined){
    		$scope.show2=true;
    	}else
    	if(!(/^(((13[0-9]{1})|(15[0-9]{1})|(18[0-9]{1}))+\d{8})$/).test($scope.admin.adPhone) 
                && ($scope.admin.adPhone.length === 11)){
    		$scope.show2=false;
    		$scope.show4=true;
    	}
          
        else
           {
        	$scope.show2=false;
   		 	$scope.show4=false;
           }
    };
    
    $scope.validate=function(){
    	if($scope.admin.adPhone==null || $scope.admin.adPhone==undefined){
    		$scope.show2=true;
    	}else
    	if(!(/^(((13[0-9]{1})|(15[0-9]{1})|(18[0-9]{1}))+\d{8})$/).test($scope.admin.adPhone) 
                && ($scope.admin.adPhone.length === 11)){
    		$scope.show2=false;
    		$scope.show4=true;
    	} 
        else
           {
        	$scope.show2=false;
   		 	$scope.show4=false;
           }
        if($scope.admin.adName==null) 
            $scope.show1=true;
        else $scope.show1=false;
        if($scope.chooseRole==null) 
            $scope.show3=true;
        else{$scope.show3=false;}
        if(!$scope.show1 && !$scope.show2 && !$scope.show3 && !$scope.show4){
            return true;
        }else 
            return false;
    };

}]);


//-------------------------- 统计 ---------------------------------
angular.module('PrinterApp')
.controller('TongjiCtrl',['$scope','$http','AdminService',
function($scope,$http,AdminService){
	(new AdminService()).$getTongJi().then(function(resp){
		$scope.data=resp;
		$scope.tongji1=$scope.data.tongji1;
		$scope.tongji2=$scope.data.tongji2;
	});
	//打印统计表单
	$scope.printTble=function(){
//		console.log($scope.html);
		$http({
    	    url: 'rest/admins/print/word',
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






