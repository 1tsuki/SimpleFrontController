package com.astrider.sfc.model.vo;

import com.astrider.sfc.helper.annotation.Column;
import com.astrider.sfc.helper.annotation.Valid;

public class RecipeVo extends BaseVo {
    private static final long serialVersionUID = -5161857920063792063L;

    @Column("recipe_name")
    @Valid(isNotNull=true, isNotBlank=true, isMaxLength=true, maxLength=64)
    private String recipeName;
    @Column("description")
    @Valid(isMaxLength=true, maxLength=512)
    private String description;
    @Column("image_url")
    @Valid(isMaxLength=true, maxLength=512)
    private String imageUrl;
    @Column("estimated_time")
    @Valid(isMax=true, max=999, isMin=true, min=0)
    private int estimatedTime;
    @Column("is_deleted")
    private boolean isDeleted;

    public String getRecipeName() {
        return recipeName;
    }
    public void setRecipeName(String recipeName) {
        this.recipeName = recipeName;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getImageUrl() {
        return imageUrl;
    }
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
    public int getEstimatedTime() {
        return estimatedTime;
    }
    public void setEstimatedTime(int estimatedTime) {
        this.estimatedTime = estimatedTime;
    }
    public boolean isDeleted() {
        return isDeleted;
    }
    public void setDeleted(boolean isDeleted) {
        this.isDeleted = isDeleted;
    }
}
