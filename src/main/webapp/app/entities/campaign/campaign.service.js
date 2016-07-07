(function() {
    'use strict';
    angular
        .module('mappingBugApp')
        .factory('Campaign', Campaign);

    Campaign.$inject = ['$resource'];

    function Campaign ($resource) {
        var resourceUrl =  'api/campaigns/:id';

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
