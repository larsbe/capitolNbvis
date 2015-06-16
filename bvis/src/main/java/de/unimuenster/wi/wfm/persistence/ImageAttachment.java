package de.unimuenster.wi.wfm.persistence;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class ImageAttachment extends AbstractEntity {
	
	private static final long serialVersionUID = 1L;
	
	protected String filePath;
	protected String description;
	
	@ManyToOne
	protected LiabilityCase liabilityCase;
	
	private int width;
	private int height;
	
	public ImageAttachment() {
		super();
	}
	
	public String getFilePath() {
		return filePath;
	}
	
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}

	public LiabilityCase getLiabilityCase() {
		return liabilityCase;
	}

	public void setLiabilityCase(LiabilityCase liabilityCase) {
		this.liabilityCase = liabilityCase;
	}
	
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	
}
