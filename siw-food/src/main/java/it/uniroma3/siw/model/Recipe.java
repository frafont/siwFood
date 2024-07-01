package it.uniroma3.siw.model;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Transient;

@Entity
public class Recipe {
	
	@Id
	@GeneratedValue( strategy= GenerationType.AUTO)
	
	private Long id;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id=id;
	}
	
	@ManyToOne
	public Cook cuoco; 
	
	@OneToMany(mappedBy="recipe")
	public List<RecipeIngredient> ingredienti;
	
	public List<RecipeIngredient> getIngredients() {
		return this.ingredienti;
	}
	
	public void setCook(Cook c) {
		this.cuoco=c;
	}
	
	public Cook getCook() {
		return this.cuoco;
	}
	
	@Transient
	private MultipartFile immagine;
	
	private String name;
	
	private String urlImage;
	
	private String description;
	
	private Integer tempo;
	
	private String type;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUrlImage() {
		return urlImage;
	}
	public void setUrlImage(String url_image) {
		this.urlImage = url_image;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Integer getTempo() {
		return tempo;
	}
	public void setTempo(Integer tempo) {
		this.tempo = tempo;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}


	
	

}
