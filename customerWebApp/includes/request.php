      <div>
      <form id="offerForm">
        <div class="form-group">
          <label for="offerCompany">Company</label>
          <input class="form-control" id="offerCompany" placeholder="Company">
        </div>

        <div class="form-group">
          <label for="offerEmail">Name</label>
          <input class="form-control" id="offerName" placeholder="Name">
        </div>

        <div class="form-group">
          <label for="offerEmail">Email address</label>
          <input type="email" class="form-control" id="offerEmail" placeholder="Email">
        </div>

        <div class="form-group">
          <label for="offerEmail">Phone Number</label>
          <input type="email" class="form-control" id="offerPhoneNumber" placeholder="Phone Number">
        </div>

        <div class="form-group">
          <label>Address</label>
          <textarea class="form-control" rows="3" id="offerAddress"></textarea>
        </div>

        <div class="form-group">
          <label>Solution Type</label>
          <div class="radio">
            <label>
              <input type="radio" name="offerSolution" id="offerSolution1" value="standard" checked>
              Standard solution
            </label>
          </div>
          <div class="radio">
            <label>
              <input type="radio" name="offerSolution" id="offerSolution2" value="individual">
              Individual solution
            </label>
          </div>
        </div>

        <div class="form-group">
          <label>Insurance</label>
          <div class="radio">
            <label>
              <input type="radio" name="offerInsurance" id="offerInsuranceA" value="A" checked>
              Insurance Package A
            </label>
          </div>
          <div class="radio">
            <label>
              <input type="radio" name="offerInsurance" id="offerInsuranceB" value="B">
              Insurance Package B
            </label>
          </div>
          <div class="radio">
            <label>
              <input type="radio" name="offerInsurance" id="offerInsuranceC" value="C">
              Insurance Package C
            </label>
          </div>
        </div>

        <div class="form-group">
          <label>Additional Information</label>
          <textarea class="form-control" rows="3" id="offerAdditionalInfo"></textarea>
        </div>

        <div class="clearfix">
          <button type="submit" class="btn btn-primary pull-right">Send Request</button>
        </div>
      </form>
      </div>

      <script>
        $(window).ready(function() {
          $( "#offerForm" ).submit(function( event ) {
            var json = { "messageName" : "NewRentalAgreementRequest",
              "businessKey" : null,
              "correlationKeys" : {},
              "processVariables" : {
                  "name" : {"value" : "", "type": "String"},
                  "company" : {"value" : "", "type": "String"},
                  "email" : {"value" : "", "type": "String"},
                  "phoneNumber" : {"value" : "", "type": "String"},
                  "address" : {"value" : "", "type": "String"},
                  "solutionType" : {"value" : "", "type": "String"},
                  "insurancePack" : {"value" : "", "type": "String"},
                  "additionalInfo" : {"value" : "", "type": "String"}
              }
            };



            json.processVariables.name.value = $('#offerName').val();
            json.processVariables.company.value = $('#offerCompany').val();
            json.processVariables.email.value = $('#offerEmail').val();
            json.processVariables.phoneNumber.value = $('#offerPhoneNumber').val();
            json.processVariables.address.value = $('#offerAddress').val();
            json.processVariables.solutionType.value = $('input[name=offerSolution]:checked').val();
            json.processVariables.insurancePack.value = $('input[name=offerInsurance]:checked').val(); // = "" bei solutionType=individual
            json.processVariables.additionalInfo.value = $('#offerAdditionalInfo').val();
            
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