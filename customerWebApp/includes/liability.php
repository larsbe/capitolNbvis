      <div>
        
        <div class="row">
          <div class="col-sm-6">
            <div class="form-group">
              <label for="offerEmail">Contract Number</label>
              <input class="form-control" id="liabilityContractNo" placeholder="Contract #">
            </div>
          </div>

          <div class="col-sm-6">
            <div class="form-group">
              <label for="offerEmail">License Number</label>
              <input class="form-control" id="liabilityLicenseNo" placeholder="License #">
            </div>
          </div>
        </div>
        

        

        <div class="form-group">
          <label>Photos</label>
          <div id="imagelist">
            <img id="loader" style="display: none;" src="assets/img/loader.gif" alt="Uploading..."/>
          </div>
        </div>

        <div class="clearfix">
        <form id="imageform" method="post" enctype="multipart/form-data" action="ajaximage.php">
            <span class="btn btn-default fileinput-button">
                <span>Add Photo</span>
                <input type="file" name="fileupload" id="fileupload" data-role="none" />
            </span>
        </form>
        </div>


            <div class="form-group">
              <label for="offerEmail">Estimate of Cost</label>
              <div class="input-group">
                <div class="input-group-addon">€</div>
                <input class="form-control" id="liabilityEstCosts" placeholder="0.00">
              </div>
            </div>

        
        <div class="form-group">
          <label>Claim Details</label>
          <textarea class="form-control" rows="3" id="liabilityClaimDetails"></textarea>
        </div>

        <div class="clearfix">
          <button id="submitForm" type="submit" class="btn btn-primary pull-right">Submit Case</button>
        </div>
      
      </div>

      <script>
      var imagearray = [];

        $(window).ready(function() {

        $('#fileupload').on('change', function() { 
            $("#loader").show();
            $("#imageform").ajaxForm({  
                //target: '#preview',
                success: function(url) {
                  imagearray.push(url);
                  //$('#imagelist').append('<div class="thumb" style="background-image: url(\'uploads/' + url + '\')"></div>');
                  $('#loader').hide();
                  $('<div class="thumb" style="background-image: url(\'uploads/' + url + '\')"></div>').insertBefore('#loader');
                },
                error: function() { alert('error'); }
            }).submit();
            
        });


          $( "#submitForm" ).click(function( event ) {
            var json = { "messageName" : "NewLiabilityCase",
              "businessKey" : null,
              "correlationKeys" : {},
              "processVariables" : {
                  "contractNoBVIS" : {"value" : "", "type": "Long"},
                  "imageCount" : {"value" : "", "type": "Integer"},
                  "claimDetails" : {"value" : "", "type": "String"},
                  "estCosts" : {"value" : "", "type": "String"},
                  "licenseNumber":{"value" : "", "type": "String"}
              }
            };

            $.each(imagearray, function(i, val) {
              i++;
              json.processVariables["image_" + i] = { value: imagefolder + val, type: "String"};

            });

            json.processVariables.contractNoBVIS.value = $('#liabilityContractNo').val();
            json.processVariables.claimDetails.value = $('#liabilityClaimDetails').val();
            json.processVariables.estCosts.value = $('#liabilityEstCosts').val();
            json.processVariables.licenseNumber.value = $('#liabilityLicenseNo').val();
            json.processVariables.imageCount.value = imagearray.length;
            
            console.log(json);

            $.ajax({
              url:camundaserver,
              type:"POST",
              data:JSON.stringify(json),
              contentType:"application/json; charset=utf-8",
              dataType:"json",
              success: function() {
                console.log('success');
                window.location = "index.php?page=liability-success";
              }
            });

            event.preventDefault();
          });
        });
      </script>