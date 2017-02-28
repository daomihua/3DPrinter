//------------------------------------- 管理员个人中心 --------------------------
angular.module('PrinterApp')
.controller('SelfCtrl', ['$scope','$rootScope', 'AdminService',
function($scope,$rootScope,AdminService){
	$rootScope.currentAdmin = JSON.parse(sessionStorage.getItem("Admin"));
	var id=$rootScope.currentAdmin.adID;
	
	(new AdminService()).$queryAdmin({key:id}).then(function(resp){
		$scope.user=resp.data;
		$scope.checkpwd=$scope.user.adPassword;
	});
	

	//保存个人信息修改
	$scope.saveSelf=function(){
		if($scope.validate()){
            (new AdminService({
            	'adID':$scope.user.adID,
                'adName':$scope.user.adName,
                'adSex':$scope.user.adSex,
                'adPassword':$scope.user.adPassword,
                'adPhone':$scope.user.adPhone,
                'adEmail':$scope.user.adEmail,
                'portrait':$scope.user.portrait,
                'roleID':$scope.user.roleID,
                'adQQ':$scope.user.adQQ
            })).$updateAdmin().then(function(resp){
                alert( resp.msg);
               
            });
        }
	};
	$scope.check=function(){
		console.log($scope.user.adPassword);
		console.log($scope.checkpwd);
		if($scope.checkpwd == $scope.user.adPassword)
			$scope.show6=false;
		else
			$scope.show6=true;
	}
	$scope.validate=function(){
        if($scope.user.adName==null) 
            $scope.show1=true;
        else $scope.show1=false;
        if($scope.user.adPhone==null) 
            $scope.show2=true;
        else 
        {  
            $scope.show2=false;
            if((/^(((13[0-9]{1})|(15[0-9]{1})|(18[0-9]{1}))+\d{8})$/).test($scope.user.adPhone) 
            && ($scope.user.adPhone.length === 11))
                $scope.show4=false;
            else
                $scope.show4=true;
        }
        if($scope.user.adPassword==null) 
            $scope.show5=true;
        else if($scope.checkpwd==null)
        	$scope.show6=true;
        else if($scope.checkpwd!=$scope.user.adPassword)
        	{
        		$scope.show5=false;
        		$scope.show6=true;
        	}
        	else{$scope.show5=false;
        		$scope.show6=false;}
        
        if((/^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/).test($scope.user.adEmail))
        	$scope.show7=false;
        else 
        	$scope.show7=true;
        
        if($scope.user.adQQ!=null)
        	$scope.show8=false;
        else
        	$scope.show8=true;

        if(!$scope.show1 && !$scope.show2 && !$scope.show3 && !$scope.show4 && !$scope.show6 && !$scope.show7
        		&& !$scope.show8){
            return true;
        }else 
            return false;
    };
}]);
//上传头像
angular.module("PrinterApp")
.controller('addPortraitCtrl',function($scope,$state,$http,baneFileFactory){
		var type=[".jpg",".png"];
		$scope.imgType=type;
		$scope.fileTypes=type;
		$scope.data={"a":"1","b":"2"};
		$scope.onSelect=function(files,error,dataForm){
			console.log(dataForm[0]);
			baneFileFactory.uploadDataForm("rest/admins/upload",dataForm[0]).then(function(resp){
				alert(resp.data.url);
				$scope.user.portrait=resp.data.url;
			});
		};
		
});