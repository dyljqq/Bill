Lay.package(function (using, L){
  var $A = using("LL.Net.Ajax")
  var rootApi = '/api'

  L.infs = {
    hello: {
      url: rootApi + '/v1/public/hello',
      type: 'get'
    },
    bill: {
      add: {
        url: rootApi + '/v1/bill/add',
        type: 'post'
      },
      query: {
        url: rootApi + '/v1/bill/query',
        type: 'post'
      },
      delete: {
        url: rootApi + '/v1/bill/delete',
        type: 'post'
      }
    }
  }

  L.call = function(inf, params, success, error){
        params = params || {};
        $A.request({
            url: inf.url,
            type: inf.type,
            data: params,
            timeout: 30 * 1000,
            success: function (result)
            {
                success(result.code, result.data);
            },
            error: error
        });
    };
})
