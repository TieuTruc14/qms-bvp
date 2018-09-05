
app.controller('qmsCtrl',['$scope','$http','transformRequestFormPost',function ($scope,$http,transformRequestFormPost) {
        $scope.confirmPassword="";
        $scope.dataLoaded=false;
        $scope.object={username:'',password:'',fullname:'',description:''};
        $http.get(preUrl+"/admin/system/user/list")
            .then(function (response) {
                if(response!=null && response!='undefined' && response.status==200){
                    $scope.page=response.data;
                    $scope.page.pageList=getPageList($scope.page);
                    $scope.page.pageCount=getPageCount($scope.page);
                }
            });

        $scope.loadPage=function (pageNumber) {
            if(pageNumber>=1){
                $http.get(preUrl+"/admin/system/user/list", {params: {p:pageNumber,username:$scope.username}})
                    .then(function (response) {
                        $scope.page=response.data;
                        $scope.page.pageList=getPageList($scope.page);
                        $scope.page.pageCount=getPageCount($scope.page);
                    });
            }
        };

        $scope.addClick=function () {
            $scope.messageError='';
            $scope.messageStatus='';
            $scope.item= $scope.object;
        };
        $scope.editClick=function (user) {
            // $scope.item=user;
            $scope.item={id:user.id,disable:user.disable,fullname:user.fullname,description:user.description};
        };

        $scope.addItem=function () {
            if($scope.validateAdd()){
                $http({
                    method : 'POST',
                    url : preUrl+"/admin/system/user/edit",
                    data : $scope.item,
                    headers : { 'Content-Type': 'application/x-www-form-urlencoded; charset=utf-8' },
                    transformRequest: transformRequestFormPost
                }).then(function (response) {
                    if(response!=null && response!='undefined'){
                        if(response.status==200 && response.data!=null){
                            if(response.data==1){
                                $scope.messageStatus='Thêm mới thành công!';
                            }else if(response.data==0){
                                $scope.messageError='Thêm mới thất bại!';
                            }else if(response.data==3){
                                $scope.messageError='Tên tài khoản đã tồn tại!';
                            }else if(response.data==2){
                                $scope.messageError='Thông tin không phù hợp!';
                            }
                        }
                    }
                },function(response){
                    $scope.messageError="Có lỗi xảy ra, hãy thử lại sau!";
                });
            }
        };

        $scope.editItem=function () {
            if($scope.validateEdit()){
                $http({
                    method : 'PUT',
                    url : preUrl+"/admin/system/user/edit",
                    data : $scope.item,
                    headers : { 'Content-Type': 'application/x-www-form-urlencoded; charset=utf-8' },
                    transformRequest: transformRequestFormPost
                }).then(function (response) {
                    if(response!=null && response!='undefined'){
                        if(response.status==200 && response.data!=null){
                            if(response.data==1){
                                $scope.messageStatus='Cập nhật thành công!';
                            }else if(response.data==0){
                                $scope.messageError='Cập nhật thất bại!';
                            }else if(response.data==3){
                                $scope.messageError='Tài khoản không tồn tại!';
                            }else if(response.data==2){
                                $scope.messageError='Thông tin không phù hợp!';
                            }
                        }
                    }
                },function(response){
                    $scope.messageError="Có lỗi xảy ra, hãy thử lại sau!";
                });
            }
        };

        $scope.validateAdd=function () {
            $scope.messageError='';
            $scope.messageStatus='';
            if($scope.item.username.length==0){
                $scope.messageError='Tài khoản không được trống';
                return false;
            }
            if($scope.item.password.length<1){
                $scope.messageError='Mật khẩu không được trống';
                return false;
            }
            if($scope.item.password!=$scope.confirmPassword){
                $scope.messageError='Xác nhận mật khẩu không đúng';
                return false;
            }
            if($scope.item.fullname.length==0){
                $scope.messageError='Họ tên không được trống';
                return false;
            }
            return true;
        };

        $scope.validateEdit=function () {
            $scope.messageError='';
            $scope.messageStatus='';
            if($scope.item.fullname.length==0){
                $scope.messageError='Họ tên không được trống';
                return false;
            }
            return true;
        };

    }]);