/**
 * Created by Admin on 8/13/2018.
 */
app.controller('qmsCtrl',['$scope','$http'
    ,function ($scope,$http) {
        $scope.dataLoaded=false;
        $scope.page=page;
        $http.get(preUrl+"/admin/category/area/page")
            .then(function (response) {
                if(response!=null && response!='undefined' && response.status==200){
                    $scope.page=response.data;
                    $scope.page.pageCount=getPageCount($scope.page);
                    $scope.page.pageList=getPageList($scope.page);
                }
            });
        $scope.loadPage=function (pageNumber) {
            if(pageNumber>=1){
                $http.get(preUrl+"/admin/category/area/page", {params: {p:pageNumber,name:$scope.name}})
                    .then(function (response) {
                        $scope.page=response.data;
                        $scope.page.pageList=getPageList($scope.page);
                        $scope.page.pageCount=getPageCount($scope.page);
                    });
            }
        };
        /*----------------------------------------------------------------------------------*/
        var object={id:null,name:"",description:"",prefix:"",loudspeaker_times:"",disable:false};
        $scope.item={id:null,name:"",description:"",prefix:"",loudspeaker_times:"",disable:false};
        $scope.messageStatus="";
        $scope.messageError="";
        $scope.addClick=function () {
            $scope.item=object;
        };
        $scope.editClick=function (item) {
            $scope.item=item;
        };
        $scope.addItem=function () {
            if(!$scope.validate()){
                $scope.enableBtn();
            }else{
                var data=JSON.parse(JSON.stringify($scope.item));
                $http.post(preUrl+"/admin/category/area/add",data,{headers: {'Content-Type': 'application/json;charset=utf-8;'}})
                    .then(function (response) {
                        if(response!=null && response!='undefined' && response.status==200){
                            $scope.messageStatus="Thêm thành công!";
                            $scope.loadPage(1);

                        }
                        $scope.enableBtn();
                    },function(response){
                        if(response.status==403){
                            $scope.messageError="Không đủ quyền thực hiện!";
                        }else if(response.status==404){
                            $scope.messageError="Có lỗi xảy ra, hãy thử lại sau!";
                        }else if(response.status=409){
                            $scope.messageError="Tên hoặc tiền tố đã bị trùng lặp dữ liệu!";
                        }else if(response.status==417){
                            $scope.messageError="Thông tin gửi lên server chưa đầy đủ!";
                        }else{$scope.messageError="Có lỗi xảy ra, hãy thử lại sau!";}
                        $scope.enableBtn();
                    });
            }

        };

        $scope.editItem=function () {
            if(!$scope.validate()){
                $scope.enableBtn();
            }else{
                $http.put(preUrl+"/admin/category/area/edit",JSON.stringify($scope.item))
                    .then(function (response) {
                        if(response!=null && response!='undefined' ){
                            if(response.status==200){
                                $scope.messageStatus="Thêm thành công!";
                                $scope.loadPage(1);
                            }else if(response.status==403){
                                $scope.messageError="Không đủ quyền thực hiện!";
                            }else if(response.status==404){
                                $scope.messageError="Có lỗi xảy ra, hãy thử lại sau!";
                            }else if(response.status=409){
                                $scope.messageError="Tên hoặc tiền tố đã bị trùng lặp dữ liệu!";
                            }else if(response.status==417){
                                $scope.messageError="Thông tin gửi lên server chưa đầy đủ!";
                            }else{$scope.messageError="Có lỗi xảy ra, hãy thử lại sau!";}
                        }
                        $scope.enableBtn();
                    },function(response){
                        $scope.messageError="Có lỗi xảy ra, hãy thử lại sau!";
                        $scope.enableBtn();
                    });
            }

        };
        
        $scope.validate=function () {
            $scope.messageStatus="";
            $scope.messageError="";
            $scope.disableBtn();
            if($scope.item.name=="" || $scope.item.prefix=="" || !($scope.item.loudspeaker_times>=0)){
                $scope.messageError="Điền đầy đủ thông tin đối tượng.";
                return false;
            }
            return true;
        };

        $scope.disableBtn=function () {
            var btnCancel = angular.element( document.querySelector( '#btnCancel' ) );
            var btnAdd = angular.element( document.querySelector( '#btnAdd' ) );
            var btnCancelEdit = angular.element( document.querySelector( '#btnCancelEdit' ) );
            var btnEdit = angular.element( document.querySelector( '#btnEdit' ) );
            btnCancel.addClass('disabled');
            btnAdd.addClass('disabled');
            btnCancelEdit.addClass('disabled');
            btnEdit.addClass('disabled');
        };

        $scope.enableBtn=function () {
            var btnCancel = angular.element( document.querySelector( '#btnCancel' ) );
            var btnAdd = angular.element( document.querySelector( '#btnAdd' ) );
            var btnCancelEdit = angular.element( document.querySelector( '#btnCancelEdit' ) );
            var btnEdit = angular.element( document.querySelector( '#btnEdit' ) );
            btnCancel.removeClass('disabled');
            btnAdd.removeClass('disabled');
            btnCancelEdit.removeClass('disabled');
            btnEdit.removeClass('disabled');
        };

    }]);