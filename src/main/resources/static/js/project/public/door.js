
app.controller('qmsCtrl',['$scope','$http','transformRequestFormPost' ,function ($scope,$http,transformRequestFormPost) {
        $scope.doorId=doorId;
        $scope.item="";
        $scope.messageStatus="";
        $scope.statusGetReception="";
        $scope.receptionEmpty=false;
        var main=angular.element( document.querySelector('#main'));
        main.removeClass('hidden');

        $http.get(preUrl+"/door/current-reception",{params:{doorId:doorId}})
            .then(function (response) {
                if(response!=null && response!='undefined'){
                    if(response.status==200){
                        $scope.item=response.data;
                    }else if(response.status==204){
                        $scope.receptionEmpty=true;
                    }
                    $scope.endGetData();
                }
            },function(response){
                $scope.receptionEmpty=true;
                $scope.endGetData();
            });
        
        $scope.getReceptionForDoor=function () {
            $scope.statusGetReception="";
            $http.get(preUrl+"/door/get-reception",{params:{doorId:doorId}})
                .then(function (response) {
                    if(response!=null && response!='undefined'){
                        if(response.status==200){
                            $scope.item=response.data;
                            $scope.receptionEmpty=false;
                        }else if(response.status==204){
                            $scope.receptionEmpty=true;
                            $scope.statusGetReception="Không có đối tượng tiếp đón phù hợp!";
                        }
                        $scope.endGetData();
                    }
                },function(response){
                    $scope.receptionEmpty=true;
                    $scope.statusGetReception="Có lỗi xảy ra, hãy thử lại sau!";
                    $scope.endGetData();
                });
        };
        
        $scope.confirmReception=function (status) {
            //status:  1-da tiep don xong, 2- tiep don xong va lay so tiep theo,3-bo qua,4-bo qua va lay so tiep theo
            $scope.object={doorId:doorId,status:status,code:$scope.item.code};
            $scope.statusGetReception="";
            $http({
                method : 'POST',
                url : preUrl+"/door/confirm-reception",
                data : $scope.object,
                headers : { 'Content-Type': 'application/x-www-form-urlencoded; charset=utf-8' },
                transformRequest: transformRequestFormPost
            }).then(function (response) {
                if(response!=null && response!='undefined'){
                    if(response.status==200){
                        $scope.item=response.data;
                    }else if(response.status==202){
                        $scope.receptionEmpty=true;
                        $scope.statusGetReception="Thực hiện thành công!";
                    }else if(response.status==204){
                        $scope.receptionEmpty=true;
                        $scope.statusGetReception="Chưa có đối tượng tiếp đón phù hợp!";
                    }
                    if(response.status!=200){
                        $scope.item="";
                    }
                    $scope.endGetData();

                }
            },function(response){
                $scope.receptionEmpty=true;
                $scope.statusGetReception="Có lỗi xảy ra, hãy thử lại sau!";
                $scope.endGetData();
            });
        };


        $scope.preGetData=function () {
            $scope.messageStatus="";
            $scope.statusGetReception="";
            var miss = angular.element( document.querySelector( '#miss' ) );
            var miss_next = angular.element( document.querySelector( '#miss-next' ) );
            var done = angular.element( document.querySelector( '#done' ) );
            var done_next = angular.element( document.querySelector( '#done-next' ) );
            miss.addClass('disabled');
            miss_next.addClass('disabled');
            done.addClass('disabled');
            done_next.addClass('disabled');
        };
        $scope.endGetData=function () {
            var miss = angular.element( document.querySelector( '#miss' ) );
            var miss_next = angular.element( document.querySelector( '#miss-next' ) );
            var done = angular.element( document.querySelector( '#done' ) );
            var done_next = angular.element( document.querySelector( '#done-next' ) );
            miss.removeClass('disabled');
            miss_next.removeClass('disabled');
            done.removeClass('disabled');
            done_next.removeClass('disabled');
        }
    }]);