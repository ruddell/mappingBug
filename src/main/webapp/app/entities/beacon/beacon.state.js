(function() {
    'use strict';

    angular
        .module('mappingBugApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('beacon', {
            parent: 'entity',
            url: '/beacon',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Beacons'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/beacon/beacons.html',
                    controller: 'BeaconController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('beacon-detail', {
            parent: 'entity',
            url: '/beacon/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Beacon'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/beacon/beacon-detail.html',
                    controller: 'BeaconDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Beacon', function($stateParams, Beacon) {
                    return Beacon.get({id : $stateParams.id}).$promise;
                }]
            }
        })
        .state('beacon.new', {
            parent: 'beacon',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/beacon/beacon-dialog.html',
                    controller: 'BeaconDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                name: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('beacon', null, { reload: true });
                }, function() {
                    $state.go('beacon');
                });
            }]
        })
        .state('beacon.edit', {
            parent: 'beacon',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/beacon/beacon-dialog.html',
                    controller: 'BeaconDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Beacon', function(Beacon) {
                            return Beacon.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('beacon', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('beacon.delete', {
            parent: 'beacon',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/beacon/beacon-delete-dialog.html',
                    controller: 'BeaconDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Beacon', function(Beacon) {
                            return Beacon.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('beacon', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
