      <div>
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

        <div class="form-group" id="carType">
          <label>Car Type</label>
          <div class="radio">
            <label>
              <input type="radio" name="offerCarType" id="offerCarType1" value="bmw5" checked>
              BMW 5
            </label>
          </div>
          <div class="radio">
            <label>
              <input type="radio" name="offerCarType" id="offerCarType2" value="audia3">
              Audi A3 etron
            </label>
          </div>
          <div class="radio">
            <label>
              <input type="radio" name="offerCarType" id="offerCarType3" value="audia3">
              Nissan micra
            </label>
          </div>
        </div>

        <div class="form-group" id="insurance">
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
          <button id="submitForm" type="submit" class="btn btn-primary pull-right">Send Request</button>
        </div>
      </div>

      <script>

        $(document).on('change', '#offerForm', function() {
            if($('#offerSolution1').is(':checked')) {
              $('#carType').show();
              $('#insurance').show();
            }

            if($('#offerSolution2').is(':checked')) {
              $('#carType').hide();
              $('#insurance').hide();
            }
          });

        $(window).ready(function() {       

          $( "#submitForm" ).click(function( event ) {

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
                  "carType" : {"value" : "", "type": "String"},
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
            if($('#offerSolution1').is(':checked')) {
              json.processVariables.carType.value = $('input[name=offerCarType]:checked').val();
              json.processVariables.insurancePack.value = $('input[name=offerInsurance]:checked').val();
            }
            json.processVariables.additionalInfo.value = $('#offerAdditionalInfo').val();
            
            console.log(json);

            $.ajax({
              url:serveradress,
              type:"POST",
              data:JSON.stringify(json),
              contentType:"application/json; charset=utf-8",
              dataType:"json",
              success: function() {
                console.log('success');
                window.location = "index.php?page=request-success";
              }
            });

            event.preventDefault();
          });
        });
      </script>