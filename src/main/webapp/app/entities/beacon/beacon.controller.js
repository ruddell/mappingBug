(function() {
    'use strict';

    angular
        .module('mappingBugApp')
        .controller('BeaconController', BeaconController);

    BeaconController.$inject = ['$scope', '$state', 'Beacon'];

    function BeaconController ($scope, $state, Beacon) {
        var vm = this;
        
        vm.beacons = [];

        loadAll();

        function loadAll() {
            Beacon.query(function(result) {
                vm.beacons = result;
            });
        }
    }
})();
