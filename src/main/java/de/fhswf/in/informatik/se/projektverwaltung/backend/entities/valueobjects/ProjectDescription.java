package de.fhswf.in.informatik.se.projektverwaltung.backend.entities.valueobjects;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class ProjectDescription {

	private String title;
	private String sketch;
	private String descriptionBackground;

	@Column(length = 10485760)
	private byte[] descriptionProjectContent;

	public ProjectDescription(String title, String sketch, String descriptionBackground, byte[] descriptionProjectContent) {
		this.title = title;
		this.sketch = sketch;
		this.descriptionBackground = descriptionBackground;
		this.descriptionProjectContent = descriptionProjectContent;
	}

	public ProjectDescription() {
	}

	public String getTitle() {
		return title;
	}

	public String getSketch() {
		return sketch;
	}

	public String getDescriptionBackground() {
		return descriptionBackground;
	}

	public byte[] getDescriptionProjectContent() {
		return descriptionProjectContent;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setSketch(String sketch) {
		this.sketch = sketch;
	}

	public void setDescriptionBackground(String descriptionBackground) {
		this.descriptionBackground = descriptionBackground;
	}

	public void setDescriptionProjectContent(byte[] descriptionProjectContent) {
		this.descriptionProjectContent = descriptionProjectContent;
	}
}