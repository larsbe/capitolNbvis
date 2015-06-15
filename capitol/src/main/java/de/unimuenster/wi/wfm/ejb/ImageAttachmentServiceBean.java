package de.unimuenster.wi.wfm.ejb;

import java.util.Collection;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import de.unimuenster.wi.wfm.entitiy.ImageAttachment;

@Stateless
public class ImageAttachmentServiceBean {
	
	@PersistenceContext
	protected EntityManager em;
	
	public ImageAttachment createImageAttachment(ImageAttachment image) {
		em.persist(image);
		em.flush();
		return image;
	}
	
	public Collection<ImageAttachment> getImageAttachments() {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<ImageAttachment> cq = cb.createQuery(ImageAttachment.class);
		Root<ImageAttachment> rootEntry = cq.from(ImageAttachment.class);
		return em.createQuery(cq.select(rootEntry)).getResultList();
	}
	
	public ImageAttachment getImageAttachment(long id) {
		ImageAttachment image = em.find(ImageAttachment.class, id);
		if(image == null)
			throw new IllegalArgumentException(String.format("ImageAttachment with ID %s not found", id));
		return image;
	}
	
	public ImageAttachment editImageAttachment(ImageAttachment image) {
		em.merge(image);
		return getImageAttachment(image.getId());
	}
}

