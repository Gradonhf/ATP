{application,rabbitmq_jsonrpc,
             [{description,"Rabbit JSON-RPC"},
              {vsn,"3.1.0"},
              {modules,[rabbit_jsonrpc]},
              {registered,[]},
              {mod,{rabbit_jsonrpc,[]}},
              {env,[{listener,[{port,15670}]},{redirect_old_port,true}]},
              {applications,[kernel,stdlib,rabbitmq_web_dispatch,
                             rfc4627_jsonrpc]}]}.
