package com.example.mealtime1.models;

import com.example.mealtime1.Images.LargeImage;
import com.example.mealtime1.Images.RegularImage;
import com.example.mealtime1.Images.SmallImage;
import com.example.mealtime1.Images.Thumbnail;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RootImageModel {
    @SerializedName("LARGE")
    @Expose()
    private LargeImage largeImage;
    @SerializedName("REGULAR")
    @Expose()
    private RegularImage regularImage;
    @SerializedName("SMALL")
    @Expose()
    private SmallImage smallImage;
    @SerializedName("THUMBNAIL")
    @Expose()
    private Thumbnail thumbnail;

    public RootImageModel(LargeImage largeImage, RegularImage regularImage, SmallImage smallImage, Thumbnail thumbnail) {
        this.largeImage = largeImage;
        this.regularImage = regularImage;
        this.smallImage = smallImage;
        this.thumbnail = thumbnail;
    }

    public LargeImage getLargeImage() {
        return largeImage;
    }

    public RegularImage getRegularImage() {
        return regularImage;
    }

    public SmallImage getSmallImage() {
        return smallImage;
    }

    public Thumbnail getThumbnail() {
        return thumbnail;
    }
}
