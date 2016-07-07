(function() {
    'use strict';

    angular
        .module('mappingBugApp')
        .controller('ApplicationDetailController', ApplicationDetailController);

    ApplicationDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Application', 'Company', 'Campaign'];

    function ApplicationDetailController($scope, $rootScope, $stateParams, entity, Application, Company, Campaign) {
        var vm = this;

        vm.application = entity;

        var unsubscribe = $rootScope.$on('mappingBugApp:applicationUpdate', function(event, result) {
            vm.application = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
