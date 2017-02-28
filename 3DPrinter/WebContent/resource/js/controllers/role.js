//---------------------------添加角色----------------------------//
angular.module('PrinterApp')
.controller('addRoleCtrl', ['$scope','$rootScope','AdminService','RoleService', 
function($scope,$rootScope,AdminService,RoleService){
	//获取所有权限
	(new AdminService()).$queryAthorities({key:0})
		.then(function(resp){
			if(resp.state){
				$scope.authos = resp.data;			
			}else{
				console.log('get data error');
			}	
		});

	//   分配权限
	$scope.selected = [];
    $scope.selectedTags = [];
 
    //将选中的权限id加入数组中
    var updateSelected = function(action,id,name){
         if(action == 'add' && $scope.selected.indexOf(id) == -1){
             $scope.selected.push(id);
             $scope.selectedTags.push(name);
         }
         if(action == 'remove' && $scope.selected.indexOf(id)!=-1){
             var idx = $scope.selected.indexOf(id);
             $scope.selected.splice(idx,1);
             $scope.selectedTags.splice(idx,1);
         }
     };
 
     $scope.updateSelection = function($event, id){
         var checkbox = $event.target;
         var action = (checkbox.checked?'add':'remove');
         updateSelected(action,id,checkbox.name);
         console.log($scope.selected);
     };
 
     $scope.isSelected = function(id){
         return $scope.selected.indexOf(id)>=0;
     };

     //添加角色
     $scope.addRole = function(){

        if( $scope.selected.length==0 ){
            alert("请选择角色权限");
        }else if( $scope.roleName!=null ){
            (new RoleService({
                'roleName':$scope.roleName,
                'roleIntro':$scope.roleIntro
            })).$addRole().then(function(resp){
                $scope.state = resp.state;
                $scope.msg = resp.msg;
                $scope.roleid = resp.data.roleID;
                if(resp.state){
                    for (var i = 0;i<$scope.selected.length; i++) {
                    	console.log('autho:'+$scope.selected[i]+'  role:'+$scope.roleid);
                        (new RoleService({
                            'roleID':$scope.roleid,
                            'authorityID':$scope.selected[i]
                        })).$distribute().then(function(resp){
                            $scope.distribute = resp.rs;
                            console.log(resp.msg);
                        });
                    }
                }
            });
        	
        }
        if( $scope.state && $scope.distribute!=0 ){
            alert("角色添加成功！");
            $rootScope.back();
        }

     };

}]);


//-----------------------------------删除角色----------------------------//
angular.module('PrinterApp')
.controller('deleteRoleCtrl', ['$scope','$http','$state','RoleService', 
function($scope,$http,$state,RoleService){

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
               (new RoleService()).$queryRoles().then(function(resp){
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
               (new RoleService()).$queryRoles().then(function(resp){
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
        	{field:'roleID',enableCellEdit: false},
        	{field:'roleName', displayName:'角色名',enableCellEdit: false}, 
        	{field:'roleIntro',displayName:'角色描述',enableCellEdit: false},
        	{field: "",width: 100, enableCellEdit: false,  
              		cellTemplate: '<button type="button" class="btn btn-edit"  ng-click="toEditPage()" >编辑</button>'  
            }
        ],
        showSelectionCheckbox: true,  
        enableRowSelection: true,
        selectedItems:$scope.selectedRows,
        multiSelect:true,
        // enablePinning: true,  
        enablePaging: true, 
        pagingOptions: $scope.pagingOptions, 
        filterOptions: $scope.filterOptions,
        showFooter: true,  
        // enableColumnResize: false,  
        enableCellSelection: true
        // enableCellEditOnFocus: true
    };

    //跳转至编辑页面
    $scope.toEditPage = function(){
		var index=$scope.selectedRows[0].roleID;
    	$state.go('admin.editrole', {roleid:index}); 
    };

}]);


//--------------------------------------编辑角色-----------------------------//
angular.module('PrinterApp')
.controller('editRoleCtrl', ['$scope', '$state', '$stateParams','RoleService','AdminService',
function($scope, $state, $stateParams,RoleService,AdminService){
	
    $scope.roleid = $stateParams.roleid;

	(new RoleService({
		'roleID':$scope.roleid
	   })).$queryRole( { key:$scope.roleid } ).then(function(resp){
			if(resp.state){
                $scope.role = resp.data;
                $scope.roleName = $scope.role.roleName;
                $scope.roleIntro = $scope.role.roleIntro;
			}else{
				console.log('get data error');
			}	
	});

	//查询当前角色的权限
    (new AdminService().$queryAthorities({key:$scope.roleid})).then(function(resp){
        $scope.authority=resp.data;
    });
    //查询所有权限
    (new AdminService().$queryAthorities({key:0})).then(function(resp){
        $scope.authos=resp.data;
    });
    
    
    //  分配权限
	$scope.selected = [];
    $scope.selectedTags = [];
 
    //将选中的权限id加入数组中
    var updateSelected = function(action,id,name){
         if(action == 'add' && $scope.selected.indexOf(id) == -1){
             $scope.selected.push(id);
             $scope.selectedTags.push(name);
         }
         if(action == 'remove' && $scope.selected.indexOf(id)!=-1){
             var idx = $scope.selected.indexOf(id);
             $scope.selected.splice(idx,1);
             $scope.selectedTags.splice(idx,1);
         }
     };
 
     $scope.updateSelection = function($event, id){
         var checkbox = $event.target;
         var action = (checkbox.checked?'add':'remove');
         updateSelected(action,id,checkbox.name);
         console.log($scope.selected);
     };
 
     $scope.isSelected = function(id){
         return $scope.selected.indexOf(id)>=0;
     };
     
    
    //保存角色修改
	$scope.saveRole = function(){
		console.log('aaa');
        (new RoleService({
            'roleID':$scope.roleid,
            'roleName':$scope.roleName,
            'roleIntro':$scope.roleIntro
        })).$updateRole().then(function(resp){
            $scope.state = resp.state;
            $scope.msg = resp.msg;
            if(resp.state){
            	deleteautho();
            }
            console.log($scope.selected);
        });
        
        function deleteautho(){
        	if($scope.state && $scope.selected.length>0){
        		
                (new RoleService()).$deleteAuthority({key:$scope.roleid}).then(function(resp){
                	$scope.del=resp.state;
                	distribute();
                });
                
            }
   
        }
        
        function distribute(){
        	var count=0;
        	for(var i=0;i<$scope.selected.length;i++){
        		(new RoleService({
        			'roleID':$scope.roleid,
        			'authorityID':$scope.selected[i]
        		})).$distribute().then(function(resp){
        			if(resp.rs>0) count++;
        			if(count==$scope.selected.length){
                    	console.log("权限重新分配成功");
                    	alert("角色修改成功");
                    	$state.go('admin.deleterole');
                    }
        		});
        	};      	      	
        }

    };

}]);