(function() {
    'use strict';
    angular
        .module('mappingBugApp')
        .factory('Beacon', Beacon);

    Beacon.$inject = ['$resource'];

    function Beacon ($resource) {
        var resourceUrl =  'api/beacons/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
