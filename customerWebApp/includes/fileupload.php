      <div>
        
        <div class="row">
          
          <!--
          <div class="col-sm-6">
            <div class="form-group">
              <label for="offerCompany">Customer Number</label>
              <input class="form-control" id="liabilityCustomerNo" placeholder="Customer #">
            </div>
          </div>
          -->

          <div class="col-sm-12">
            <div class="form-group">
              <label for="offerEmail">Contract Number</label>
              <input class="form-control" id="liabilityContractNo" placeholder="Contract #">
            </div>
          </div>
        </div>
        

        

        <div class="form-group">
          <label>Contract File</label>
          <div id="fileList">
            <img id="loader" style="display: none;" src="assets/img/loader.gif" alt="Uploading..."/>
          </div>
        </div>

        <div class="clearfix">
        <form id="imageform" method="post" enctype="multipart/form-data" action="ajaxpdf.php">
            <span class="btn btn-default fileinput-button">
                <span>Add File</span>
                <input type="file" name="fileupload" id="fileupload" data-role="none" />
            </span>
        </form>
        </div>

        <div class="clearfix">
          <button id="submitForm" type="submit" class="btn btn-primary pull-right">Send Contract</button>
        </div>
      
      </div>

      <script>

      var contractfile = '';

        $(window).ready(function() {

        $('#fileupload').on('change', function() { 
            $("#loader").show();
            $("#imageform").ajaxForm({  
                //target: '#preview',
                success: function(filename) {
                  $('#loader').hide();
                  $('#fileList').append('<p>' +  filename + '</p>');
                  $('#imageform').remove();
                  contractfile = filename;
                },
                error: function() { alert('error'); }
            }).submit();
            
        });


          $( "#submitForm" ).click(function( event ) {
            var json = { "messageName" : "RentalAgreementContract",
              "businessKey" : null,
              "correlationKeys" : {
                "contractNoBVIS" : {"value" : "", "type": "Long"}
              },
              "processVariables" : {
                  //"customerNoBVIS" : {"value" : "", "type": "Long"},
                  "contract" : {"value" : "", "type": "String"}
              }
            };

            json.correlationKeys.contractNoBVIS.value = $('#liabilityContractNo').val();
            //json.processVariables.customerNoBVIS.value = $('#liabilityCustomerNo').val();
            json.processVariables.contract.value = contractfolder + contractfile;
            
            console.log(json);

            $.ajax({
              url:camundaserver,
              type:"POST",
              data:JSON.stringify(json),
              contentType:"application/json; charset=utf-8",
              dataType:"json",
              success: function() {
                console.log('success');
                window.location = "index.php?page=fileupload-success";
              }
            });

            event.preventDefault();
          });
        });
      </script>