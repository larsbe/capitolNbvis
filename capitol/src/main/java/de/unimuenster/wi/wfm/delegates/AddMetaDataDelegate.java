package de.unimuenster.wi.wfm.delegates;

import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;

import javax.ejb.EJB;
import javax.inject.Named;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import de.unimuenster.wi.wfm.ejb.ImageAttachmentServiceBean;
import de.unimuenster.wi.wfm.ejb.LiabilityCaseServiceBean;
import de.unimuenster.wi.wfm.entitiy.ImageAttachment;
import de.unimuenster.wi.wfm.entitiy.LiabilityCase;
import de.unimuenster.wi.wfm.util.Constants;

@Named
public class AddMetaDataDelegate implements JavaDelegate {
	
	@EJB
	private LiabilityCaseServiceBean liabilityCaseService;
	
	@EJB
	private ImageAttachmentServiceBean imageAttachmentService;
	
	public void execute(DelegateExecution delegateExecution) throws Exception {

		Map<String, Object> variables = delegateExecution.getVariables();
		
		LiabilityCase claim = liabilityCaseService.getLiabilityCase((Long) variables.get("caseID"));

		for (Iterator<ImageAttachment> iterator = claim.getImages().iterator(); iterator.hasNext();) {
			
			ImageAttachment img = iterator.next();
					
			String urlPrefix = "http://capitol.jonasgerlach.de/AddMetadataToImage.php?imgUrl=";
			String txtPrefix = "&text=";
			
			Date dateNow = new Date();
			SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy/MM/dd");
	        String date = DATE_FORMAT.format(dateNow);
	        
	        String query = date + " - " + img.getDescription() + " - " + Constants.IMG_METADATA_LIABILITYCLAIM_ABBR + "#" + claim.getId() + " - " + Constants.COMPANY_NAME;
			
			img.setFilePath(urlPrefix + img.getFilePath() + txtPrefix +  URLEncoder.encode(query, "UTF-8"));
			imageAttachmentService.editImageAttachment(img);
		}
		
		delegateExecution.removeVariable("hasImages");
		
	}

}
