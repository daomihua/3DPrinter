angular.module('PrinterApp')
.factory('RoleService', ['$resource', function($resource){

	var RESTurl='rest/roles';
	 // return $resource('',{},{
	 // 	'queryRoles':{ method:'GET', url:RESTurl },
	 //  	'queryRole':{ method:'GET', params: { key: '@key' }, url:RESTurl+'/role?roleID=:key' },
	 // 	'addRole':{ method:'POST', url:RESTurl+'/addrole'},
	 //  	'updateRole':{ method:'POST', url:RESTurl+'/updaterole'},
	 //  	'deleteRoles':{ method:'DELETE', params: { key: '@key' }, url:RESTurl+'/delete?roleID=:key' },
	 //  	'distribute':{ method:'POST', url:RESTurl+'/distribute'}
	 // });

	// TEST URL
	
	return $resource('',{},{
		'queryRoles':{ method:'GET', url:'resource/js/role.json' },
		'queryRole':{ method:'GET', params: { key: '@key' }, url:'resource/js/role.json' },
		'addRole':{ method:'POST', url:RESTurl+'/addrole'},
		'updateRole':{ method:'POST', url:RESTurl+'/updaterole'},
		'deleteRoles':{ method:'DELETE', params: { key: '@key' }, url:RESTurl+'/delete?roleid=:key' },
		'distribute':{ method:'POST', url:RESTurl+'/distribute'}
	});

}]);