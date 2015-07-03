      <div>
      <form id="offerForm">
        <div class="form-group">
          <label for="offerCompany">Customer Number</label>
          <input class="form-control" id="liabilityCustomerNo" placeholder="Customer #">
        </div>

        <div class="form-group">
          <label for="offerEmail">Contract Number</label>
          <input class="form-control" id="liabilityContractNo" placeholder="Contract #">
        </div>

        <p>Image upload goes here ...</p>

        <div class="form-group">
          <label>Claim Details</label>
          <textarea class="form-control" rows="3" id="liabilityClaimDetails"></textarea>
        </div>

        <div class="clearfix">
          <button type="submit" class="btn btn-primary pull-right">Send Request</button>
        </div>
      </form>
      </div>

      <script>
        $(window).ready(function() {
          $( "#offerForm" ).submit(function( event ) {
            var json = { "messageName" : "NewLiabilityCase",
              "businessKey" : null,
              "correlationKeys" : {},
              "processVariables" : {
                  "customerNoBVIS" : {"value" : "", "type": "Long"},
                  "contractNoBVIS" : {"value" : "", "type": "Long"},
                  "imageCount" : {"value" : "", "type": "Integer"},
                  "claimDetails" : {"value" : "", "type": "String"},
                  "image_1": {"value" : "", "type": "String"},
                  "image_2": {"value" : "", "type": "String"}
              }
            };



            json.processVariables.customerNoBVIS.value = $('#liabilityCustomerNo').val();
            json.processVariables.contractNoBVIS.value = $('#liabilityContractNo').val();
            json.processVariables.claimDetails.value = $('#liabilityClaimDetails').val();
            json.processVariables.imageCount.value = $('#offerEmail').val();
            
            console.log(json);

            $.post( "http://localhost:8080/engine-rest/engine/default/message", json)
              .done(function() {
                console.log('success');
              })
              .fail(function() {
                console.log('error');
              });

            event.preventDefault();
          });
        });
      </script>