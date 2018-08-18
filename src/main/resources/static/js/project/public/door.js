
app.controller('qmsCtrl',['$scope','$http','$filter','$window','$timeout','$q'
    ,function ($scope,$http,$filter,$window,$timeout,$q) {
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
                    }else if(response.status==404){
                        $scope.receptionEmpty=true;
                    }
                    $scope.endGetData();
                }
            },function(response){
                $scope.receptionEmpty=true;
                $scope.endGetData();
            });
        
        $scope.getReceptionForDoor=function () {
            $http.get(preUrl+"/door/current-reception",{params:{doorId:doorId}})
                .then(function (response) {
                    if(response!=null && response!='undefined'){
                        if(response.status==200){
                            $scope.item=response.data;
                        }else if(response.status==204){
                            $scope.receptionEmpty=true;
                            $scope.statusGetReception="Không có đối tượng tiếp đón phù hợp!";
                        }else if(response.status==404){
                            $scope.receptionEmpty=true;
                            $scope.statusGetReception="Có lỗi xảy ra, hãy thử lại sau!";
                        }
                        $scope.endGetData();
                    }
                },function(response){
                    $scope.receptionEmpty=true;
                    $scope.statusGetReception="Có lỗi xảy ra, hãy thử lại sau!";
                    $scope.endGetData();
                });
        };
        
        $scope.missNumber=function () {
            $scope.object={doorId:doorId,status:3,code:$scope.item.code};
            $http({
                method : 'POST',
                url : preUrl+"/door/confirm-reception",
                data : $scope.object,
                headers : { 'Content-Type': 'application/x-www-form-urlencoded' }
            }).then(function (response) {
                if(response!=null && response!='undefined'){
                    if(response.status==200){
                        $scope.item=response.data;
                    }else if(response.status==204){
                        $scope.receptionEmpty=true;
                        $scope.statusGetReception="Không có đối tượng tiếp đón phù hợp!";
                    }else if(response.status==404){
                        $scope.receptionEmpty=true;
                        $scope.statusGetReception="Có lỗi xảy ra, hãy thử lại sau!";
                    }
                    $scope.endGetData();
                }
            },function(response){
                $scope.receptionEmpty=true;
                $scope.statusGetReception="Có lỗi xảy ra, hãy thử lại sau!";
                $scope.endGetData();
            });
            // $http.post(preUrl+"/door/confirm-reception",JSON.stringify(object))
            //     .then(function (response) {
            //         if(response!=null && response!='undefined'){
            //             if(response.status==200){
            //                 $scope.item=response.data;
            //             }else if(response.status==204){
            //                 $scope.receptionEmpty=true;
            //                 $scope.statusGetReception="Không có đối tượng tiếp đón phù hợp!";
            //             }else if(response.status==404){
            //                 $scope.receptionEmpty=true;
            //                 $scope.statusGetReception="Có lỗi xảy ra, hãy thử lại sau!";
            //             }
            //             $scope.endGetData();
            //         }
            //     },function(response){
            //         $scope.receptionEmpty=true;
            //         $scope.statusGetReception="Có lỗi xảy ra, hãy thử lại sau!";
            //         $scope.endGetData();
            //     });
        };

        $scope.preGetData=function () {
            $scope.messageStatus="";
            $scope.statusGetReception="";
            var miss = angular.element( document.querySelector( '#miss' ) );
            var done = angular.element( document.querySelector( '#done' ) );
            var next = angular.element( document.querySelector( '#next' ) );
            miss.addClass('disabled');
            done.addClass('disabled');
            next.addClass('disabled');
        };
        $scope.endGetData=function () {
            var miss = angular.element( document.querySelector( '#miss' ) );
            var done = angular.element( document.querySelector( '#done' ) );
            var next = angular.element( document.querySelector( '#next' ) );
            miss.removeClass('disabled');
            done.removeClass('disabled');
            next.removeClass('disabled');
        }
    }]);