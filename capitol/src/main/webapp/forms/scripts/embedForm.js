var embedForm = function(pIFrameId, pTaskId, pExtFormId) {
      		
	var prefix = "/capitol/" + pExtFormId + ".jsf?taskId=";
	var suffix = "&callbackUrl=http://localhost:8080/camunda/app/tasklist/default/#/";
	var loc = prefix + pTaskId + suffix;     
	
	$('.form-pane .form-actions').hide();
	
	var $myIframe = $(pIFrameId);
		var myIframe = $myIframe[0];
	
	var MutationObserver = window.MutationObserver || window.WebKitMutationObserver;
	
	myIframe.addEventListener('load', function() {
	  setIframeHeight();
	
	  var target = myIframe.contentDocument.body;
	
	  var observer = new MutationObserver(function(mutations) {
	    setIframeHeight();
	  });
	
	  var config = {
	    attributes: true,
	    childList: true,
	    characterData: true,
	    subtree: true
	  };
	  observer.observe(target, config);
	});
	
	myIframe.src = encodeURI(loc);
	
	function setIframeHeight() {
	  $myIframe.height('auto');
	  var newHeight = $('html', myIframe.contentDocument).height();
	  $myIframe.height(newHeight + 250);
	}
	      	
};
