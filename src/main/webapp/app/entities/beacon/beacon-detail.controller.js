(function() {
    'use strict';

    angular
        .module('mappingBugApp')
        .controller('BeaconDetailController', BeaconDetailController);

    BeaconDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Beacon', 'Company', 'Campaign'];

    function BeaconDetailController($scope, $rootScope, $stateParams, entity, Beacon, Company, Campaign) {
        var vm = this;

        vm.beacon = entity;

        var unsubscribe = $rootScope.$on('mappingBugApp:beaconUpdate', function(event, result) {
            vm.beacon = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
