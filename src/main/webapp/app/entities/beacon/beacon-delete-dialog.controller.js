(function() {
    'use strict';

    angular
        .module('mappingBugApp')
        .controller('BeaconDeleteController',BeaconDeleteController);

    BeaconDeleteController.$inject = ['$uibModalInstance', 'entity', 'Beacon'];

    function BeaconDeleteController($uibModalInstance, entity, Beacon) {
        var vm = this;

        vm.beacon = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Beacon.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
